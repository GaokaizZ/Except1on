
package  org.springrain.sinova.web;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;

import org.springrain.sinova.entity.County;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.ICountyService;
import org.springrain.sinova.service.IOfficeService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.IUserOfficeService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:01
 * @see org.springrain.sinova.web.Office
 */
@Controller
@RequestMapping(value = "/office")
public class OfficeController extends BaseController {
	@Resource
	private IOfficeService officeService;
	@Resource
	private IRegionService regionService;
	@Resource
	private ICountyService countyService;
	@Resource
	private IUserOfficeService userOfficeService;
	
	private String listurl = "/sinova/office/officeList";

	/**
	* 进入office Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param office
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:13:01
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Office office) throws Exception {
		ReturnDatas returnObject = listjson(request, model, office);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	* json数据,为APP提供数据
	*
	* @param request
	* @param model
	* @param office
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:13:01
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, Office office) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		String officeCode = request.getParameter("officeCode");
		String officeName = request.getParameter("officeName");
		String state = request.getParameter("state");
		model.addAttribute("officeCode",officeCode);
		model.addAttribute("officeName",officeName);
		Finder finder = Finder.getSelectFinder(Office.class);
		finder.append("where 1 = 1 ");
		//条件模糊查询
		if(StringUtils.isNotBlank(officeCode)){
			finder.append(" and office_code like :officeCode").setParam("officeCode", "%"+officeCode+"%");
		}
		if(StringUtils.isNotBlank(officeName)){
			finder.append(" and office_name like :officeName").setParam("officeName", "%"+officeName+"%");
		}
		if(StringUtils.isNotBlank(state)){
			finder.append(" and state =:state ").setParam("state", state);
		}
		List listData = new ArrayList();
		List<Office> datas = officeService.queryForList(finder, Office.class, page);
		if(datas != null && datas.size()>0){
			for (int i = 0; i < datas.size(); i++) {
				Office officea = datas.get(i);
				String regionCodes = officea.getRegionCode();
				String countyCodes = officea.getCountyCode();
				Region region = new Region();
				region.setRegionCode(regionCodes);
				List<Region> regionData = regionService.findListDataByFinder(null, null, Region.class, region);
				for (int j = 0; j < regionData.size(); j++) {
					Region re = regionData.get(j);
					officea.setRegionCode(re.getRegionName());
				}
				County county = new County();
				county.setCountyCode(countyCodes);
				List<County> countyData = countyService.findListDataByFinder(null, null, County.class, county);
				for (int j = 0; j < countyData.size(); j++) {
					County ct = countyData.get(j);
					officea.setCountyCode(ct.getCountyName());
				}
				listData.add(officea);
			}
		}
		returnObject.setPage(page);
		returnObject.setData(listData);
		return returnObject;
	}
	
	//根据地市查询区县
	@RequestMapping("/countylist")
	@ResponseBody
	public ReturnDatas listcounty(HttpServletRequest request, Model model, Office office, Region region) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String regionCode = request.getParameter("regionCode");
		logger.info("============地市编号========"+regionCode);
		List<County> dataCounty = countyService.findCountyByRegionCode(null,null,regionCode);
		returnObject.setData(dataCounty);
		return returnObject;
	}
	

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ReturnDatas returnObject = lookjson(model, request, response);
		 model.addAttribute(GlobalStatic.returnDatas, returnObject);
		 return "/sinova/office/officeLook";
	}
	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody
    public ReturnDatas lookjson(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
 	    java.lang.String id = request.getParameter("id");
 	    if (StringUtils.isNotBlank(id)) {
 			Office office = officeService.findOfficeById(id);
 			returnObject.setData(office);
 		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
    	return returnObject;
    }

	/**
	 * 新增营业厅跳页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/beforeSave")
	public String beforeSave(HttpServletRequest request,Model model) throws Exception{
		//查询已经存在的地市
		Finder finder = Finder.getSelectFinder(Region.class);
		List<Region> dataRegion = regionService.queryForList(finder, Region.class);
		//查询全部区县
		List<County> dataCounty = countyService.findCountyByRegionCode(null,null,null);
		model.addAttribute("dataRegion",dataRegion);
		model.addAttribute("dataCounty",dataCounty);
		return "/sinova/office/addOffice";
	}
	/**
	* 新增save操作,返回json格式数据
	*
	* @param model
	* @param office
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:13:01
	*/
	
	@RequestMapping("/save")
	@ResponseBody
	public ReturnDatas saveoffice(Model model, Office office, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		Office offices = new Office();
		List<Office> datas = officeService.findListDataByFinder(null, null, Office.class, offices);
		List listTemp = new ArrayList();
		List listDemp = new ArrayList();
		try {
			String regionCode = request.getParameter("regionCode");
			String countyCode = request.getParameter("countyCode");
			String officeCode = request.getParameter("officeCode");
			String officeName = request.getParameter("officeName");
			String address = request.getParameter("address");
			String state = request.getParameter("state");
			if(null == datas){
				if(StringUtils.isBlank(regionCode)||StringUtils.isBlank(countyCode)||StringUtils.isBlank(officeCode)||StringUtils.isBlank(officeName)||StringUtils.isBlank(address)||StringUtils.isBlank(state)){
					logger.info("============资料不全============");
					response.getOutputStream().print("1");
				}else{
					String id = office.getId();
					if (StringUtils.isBlank(id)) {
						office.setId(null);
					}
					office.setRegionCode(regionCode);
					office.setCountyCode(countyCode);
					office.setOfficeCode(officeCode);
					office.setOfficeName(officeName);
					office.setOfficeName(officeName);
					office.setAddress(address);
					officeService.saveorupdate(office);
					response.getOutputStream().print("0");
					logger.info("============添加成功==============");
				}
			}
			if(null != datas || datas.size()>0){
				for (int i = 0; i < datas.size(); i++) {
					Office office1 = datas.get(i);
					String code = office1.getOfficeCode();
					listTemp.add(code);
					Office office2 = datas.get(i);
					String name = office2.getOfficeName();
					listDemp.add(name);
				}
				if(StringUtils.isBlank(regionCode)||StringUtils.isBlank(countyCode)||StringUtils.isBlank(officeCode)||StringUtils.isBlank(officeName)||StringUtils.isBlank(address)||StringUtils.isBlank(state)){
					logger.info("============资料不全============");
					response.getOutputStream().print("1");
				}else{
					if(listTemp.contains(officeCode)||listDemp.contains(officeName)){
						logger.info("============填写资料相同==============");
						response.getOutputStream().print("2");
					}else{
						String id = office.getId();
						if (StringUtils.isBlank(id)) {
							office.setId(null);
						}
						office.setRegionCode(regionCode);
						office.setCountyCode(countyCode);
						office.setOfficeCode(officeCode);
						office.setOfficeName(officeName);
						office.setOfficeName(officeName);
						office.setAddress(address);
						officeService.saveorupdate(office);
						response.getOutputStream().print("0");
						logger.info("============添加成功==============");
					}
				}
			}

		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return returnObject;
	}
	
	/**
	 * 修改跳页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/beforeUpdate")
	public String beforeUpdate(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Office office = officeService.findOfficeById(id);
			Region region = new Region();
			region.setRegionCode(office.getRegionCode());
			List<Region> regionData = regionService.findListDataByFinder(null, null, Region.class, region);
			if(regionData != null && regionData.size()>0){
				Region regions = regionData.get(0);
				model.addAttribute("regions", regions);
			}
			County county = new County();
			county.setCountyCode(office.getCountyCode());
			List<County> countyData = countyService.findListDataByFinder(null, null, County.class, county);
			if(countyData != null && countyData.size()>0){
				County countys = countyData.get(0);
				model.addAttribute("countys",countys);
			}
			model.addAttribute("office", office);
			return "/sinova/office/updateOffice";
		}
		return null;
	}
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		try {
			String id = request.getParameter("id");
			String officeName = request.getParameter("officeName");
			String address = request.getParameter("address");
			String state = request.getParameter("state");
			Office office = officeService.findOfficeById(id);
			office.setOfficeName(officeName);
			office.setAddress(address);
			office.setState(state);
			if(StringUtils.isBlank(officeName)||StringUtils.isBlank(address)||StringUtils.isBlank(state)){
				response.getOutputStream().print("1");
				logger.info("============数据为空============");
			}else{
				response.getOutputStream().print("0");
				officeService.saveorupdate(office);
				logger.info("============修改营业厅成功==============");
			}
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return returnObject;
	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/office/officeCru";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ReturnDatas destroy(HttpServletRequest request) throws Exception {
		// 执行删除
		try {
			java.lang.String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				officeService.deleteById(id, Office.class);
				return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_SUCCESS);
			} else {
			    return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
	}

	/**
	* 删除多条记录
	*
	* @param request
	* @param model
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:13:01
	*/
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model, HttpServletResponse response) throws IOException{
		String records = request.getParameter("records");
		try {
			if (StringUtils.isBlank(records)) {
				response.getOutputStream().print("1");
			}
		
			String[] rs = records.split(",");
			if (rs == null || rs.length < 1) {
				response.getOutputStream().print("1");
			}
		
			List<String> ids = Arrays.asList(rs);
			if(ids != null && ids.size()>0){
				for (int i = 0; i < ids.size(); i++) {
					String ida = ids.get(i);
					Office of = officeService.findOfficeById(ida);
					UserOffice userOffice = new UserOffice();
					userOffice.setOfficeCode(of.getOfficeCode());
					List<UserOffice> udatas = userOfficeService.findListDataByFinder(null, null, UserOffice.class, userOffice);
					for (int j = 0; j < udatas.size(); j++) {
						UserOffice usero = udatas.get(i);
						String idu = usero.getId();
						userOfficeService.deleteById(idu, UserOffice.class);
					}
				}
				officeService.deleteByIds(ids, Office.class);
				response.getOutputStream().print("0");
			}
			
		} catch (Exception e) {
			response.getOutputStream().print("1");
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}

}
