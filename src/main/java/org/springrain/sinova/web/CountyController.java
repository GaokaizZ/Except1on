package  org.springrain.sinova.web;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;

import org.springrain.sinova.entity.County;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.service.ICountyService;
import org.springrain.sinova.service.IOfficeService;
import org.springrain.sinova.service.IRegionService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:12:53
 * @see org.springrain.sinova.web.County
 */
@Controller
@RequestMapping(value = "/county" )
public class CountyController extends BaseController {
	@Resource
	private ICountyService countyService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IOfficeService officeService;

	private String listurl = "/sinova/county/countyList";

	/**
	* 进入county Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param county
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:12:53
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, County county) throws Exception {
		ReturnDatas returnObject = listjson(request, model, county);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	* json数据,为APP提供数据
	*
	* @param request
	* @param model
	* @param county
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:12:53
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, County county) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		String countyCode = request.getParameter("countyCode");
		String countyName = request.getParameter("countyName");
		String state = request.getParameter("state");
		model.addAttribute("countyCode",countyCode);
		model.addAttribute("countyName",countyName);
		Finder finder = Finder.getSelectFinder(County.class);
		finder.append("where 1 = 1 ");
		//查询条件支持模糊查询
		if(StringUtils.isNotBlank(countyCode)){
			finder.append(" and county_code like :countyCode").setParam("countyCode", "%"+countyCode+"%");
		}
		if(StringUtils.isNotBlank(countyName)){
			finder.append(" and county_name like :countyName").setParam("countyName", "%"+countyName+"%");
		}
		if(StringUtils.isNotBlank(state)){
			finder.append(" and state =:state ").setParam("state", state);
		}
		List listData = new ArrayList();
		List<County> datas = countyService.queryForList(finder, County.class, page);
		if(datas != null && datas.size()>0){
			for (int i = 0; i < datas.size(); i++) {
				County countys = datas.get(i);
				String codes = countys.getRegionCode();
				Region regions = new Region();
				regions.setRegionCode(codes);
				List<Region> regionData = regionService.findListDataByFinder(null, null, Region.class, regions);
				for (int j = 0; j < regionData.size(); j++) {
					Region re = regionData.get(j);
					countys.setRegionCode(re.getRegionName());
				}
				listData.add(countys);
			}
		}
		returnObject.setPage(page);
		returnObject.setData(datas);
		
		return returnObject;
	}
	
	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ReturnDatas returnObject = lookjson(model, request, response);
		 model.addAttribute(GlobalStatic.returnDatas, returnObject);
		 return "/sinova/county/countyLook";
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
 			County county = countyService.findCountyById(id);
 			returnObject.setData(county);
 		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
    	return returnObject;
    }
	/**
	 * 添加跳页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/beforeSave")
	public String beforeSave(HttpServletRequest request,Model model) throws Exception{
		//查询已经存在的地市
		Finder finder = Finder.getSelectFinder(Region.class);
		List<Region> dataRegion = regionService.queryForList(finder, Region.class);
		model.addAttribute("dataRegion",dataRegion);
		return "/sinova/county/addCounty";
	}
	/**
	* 新增/修改save操作,返回json格式数据
	*
	* @param model
	* @param county
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:12:53
	*/
	@RequestMapping("/save")
	@ResponseBody
	public ReturnDatas savecounty(Model model, County county, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		County countys = new County();
		List<County> datas = countyService.findListDataByFinder(null, null, County.class, countys);
		List listTemp = new ArrayList();
		List listDemp = new ArrayList();
		try {
			String regionCode = request.getParameter("regionCode");
			String countyCode = request.getParameter("countyCode");
			String countyName = request.getParameter("countyName");
			String state = request.getParameter("state");
			if(null == datas){
				if(StringUtils.isBlank(regionCode)||StringUtils.isBlank(countyCode)||StringUtils.isBlank(countyName)||StringUtils.isBlank(state)){
					logger.info("============资料不全============");
					response.getOutputStream().print("1");
				}else{
					String id = county.getId();
					if (StringUtils.isBlank(id)) {
						county.setId(null);
					}
					county.setRegionCode(regionCode);
					county.setCountyCode(countyCode);
					county.setCountyName(countyName);
					county.setState(state);
					countyService.saveorupdate(county);
					response.getOutputStream().print("0");
					logger.info("============添加成功==============");
				}
			}
			if(null != datas || datas.size()>0){
				for (int i = 0; i < datas.size(); i++) {
					County county1 = datas.get(i);
					String code = county1.getCountyCode();
					listTemp.add(code);
					County county2 = datas.get(i);
					String name = county2.getCountyName();
					listDemp.add(name);
				}
				if(StringUtils.isBlank(regionCode)||StringUtils.isBlank(countyCode)||StringUtils.isBlank(countyName)||StringUtils.isBlank(state)){
					logger.info("============区县名称为空============");
					response.getOutputStream().print("1");
				}else{
					if(listTemp.contains(countyCode)||listDemp.contains(countyName)){
						logger.info("============填写资料相同==============");
						response.getOutputStream().print("2");
					}else{
						String id = county.getId();
						if (StringUtils.isBlank(id)) {
							county.setId(null);
						}
						county.setRegionCode(regionCode);
						county.setCountyCode(countyCode);
						county.setCountyName(countyName);
						county.setState(state);
						countyService.saveorupdate(county);
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
			County county = countyService.findCountyById(id);
			Region region = new Region();
			region.setRegionCode(county.getRegionCode());
			List<Region> regionData = regionService.findListDataByFinder(null, null, Region.class, region);
			if(regionData != null && regionData.size()>0){
				Region regions = regionData.get(0);
				model.addAttribute("regions", regions);
			}
			model.addAttribute("county", county);
			return "/sinova/county/updateCounty";
		}
		return null;
	}
	/**
	 * 修改区县
	 * @throws Exception 
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		try {
			String id = request.getParameter("id");
			String countyName = request.getParameter("countyName");
			String state = request.getParameter("state");
			County county = countyService.findCountyById(id);
			county.setCountyName(countyName);
			county.setState(state);
			if(StringUtils.isBlank(countyName)){
				response.getOutputStream().print("1");
				logger.info("============区县名称为空============");
			}else{
				response.getOutputStream().print("0");
				countyService.saveorupdate(county);
				logger.info("============区县修改成功============");
				
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
		return "/sinova/county/countyCru";
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
				countyService.deleteById(id, County.class);
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
	* @version  2015-01-09 09:12:53
	 * @throws IOException 
	*/
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {
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
					County county = countyService.findCountyById(ida);
					Office office = new Office(); 
					office.setCountyCode(county.getCountyCode());
					List<Office> listOffice = officeService.findListDataByFinder(null, null, Office.class, office);
					if(listOffice != null &&listOffice.size()>0){
						response.getOutputStream().print("2");
					}else{
						countyService.deleteByIds(ids, County.class);
						response.getOutputStream().print("0");
					}
				}
			}
			
		} catch (Exception e) {
			response.getOutputStream().print("1");
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}

	/**
	 *  上传文件
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String,String> upload(@RequestParam MultipartFile uploadFile, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String info = "";
		File destFile = null;

		try {
			String uploadDirPath = request.getSession().getServletContext().getRealPath("/upload");
			File dir = new File(uploadDirPath);
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			destFile = new File(uploadDirPath + "/" + uploadFile.getOriginalFilename());
			FileCopyUtils.copy(uploadFile.getBytes(), destFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			info = "上传失败,文件处理异常.";
		}

		try {
			info = countyService.saveImportExcelFile(destFile,County.class);
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
			info = e.getMessage();
		}
		if (StringUtils.isBlank(info)) {
			info = "数据导入成功....";
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg", info);
		return map;
	}
	
}
