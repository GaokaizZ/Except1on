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
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.service.ICountyService;
import org.springrain.sinova.service.IRegionService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:12:19
 * @see org.springrain.sinova.web.Region
 */
@Controller
@RequestMapping(value = "/region")
public class RegionController extends BaseController {
	@Resource
	private IRegionService regionService;
	@Resource
	private ICountyService countyService;

	private String listurl = "/sinova/region/regionList";

	/**
	* 进入region Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param region
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:12:19
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Region region) throws Exception {
		ReturnDatas returnObject = listjson(request, model, region);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	* json数据,为APP提供数据
	*
	* @param request
	* @param model
	* @param region
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:12:19
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, Region region) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		String regionCode = request.getParameter("regionCode");
		String regionName = request.getParameter("regionName");
		String state = request.getParameter("state");
		model.addAttribute("regionCode",regionCode);
		model.addAttribute("regionName",regionName);
		Finder finder = Finder.getSelectFinder(Region.class);
		finder.append(" where 1 = 1 ");
		if(StringUtils.isNotBlank(regionCode)){
			finder.append(" and region_code like :regionCode").setParam("regionCode", "%"+regionCode+"%");
		}
		if(StringUtils.isNotBlank(regionName)){
			finder.append(" and region_name like :regionName").setParam("regionName", "%"+regionName+"%");
		}
		if(StringUtils.isNotBlank(state)){
			finder.append(" and state =:state ").setParam("state", state);
		}
		List<Region> datas = regionService.queryForList(finder, Region.class, page);
		returnObject.setQueryBean(region);
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
		 return "/sinova/region/regionLook";
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
 			Region region = regionService.findRegionById(id);
 			returnObject.setData(region);
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
	public String beforeSave(HttpServletRequest request, Model model) throws Exception{
		logger.info("==================添加跳页========");
		return "/sinova/region/addRegion";
	}
	/**
	* 新增/修改save操作,返回json格式数据
	*
	* @param model
	* @param region
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:12:19
	*/
	@RequestMapping("/save")
	@ResponseBody
	public ReturnDatas saveregion(Model model, Region region, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		Region regiona = new Region();
		List<Region> datas = regionService.findListDataByFinder(null, null, Region.class, regiona);
		List listTemp = new ArrayList();
		List listDemp = new ArrayList();
		try {
			String regionCode = request.getParameter("regionCode");
			String regionName = request.getParameter("regionName");
			String state = request.getParameter("state");
			if(null == datas){
				if(StringUtils.isBlank(regionCode)||StringUtils.isBlank(regionName)||StringUtils.isBlank(state)){
					logger.info("============资料不全============");
					response.getOutputStream().print("1");
				}else{
					java.lang.String id = region.getId();
					if (StringUtils.isBlank(id)) {
						region.setId(null);
					}
					region.setRegionCode(regionCode);
					region.setRegionName(regionName);
					region.setState(state);
					regionService.saveorupdate(region);
					response.getOutputStream().print("0");
					logger.info("============添加成功==============");
				}
			}
			if(null != datas || datas.size()>0){
				for (int i = 0; i < datas.size(); i++) {
					Region region1 = datas.get(i);
					String code = region1.getRegionCode();
					listTemp.add(code);
					Region region2 = datas.get(i);
					String name = region2.getRegionName();
					listDemp.add(name);
				}
				if(StringUtils.isBlank(regionCode)||StringUtils.isBlank(regionName)||StringUtils.isBlank(state)){
					logger.info("============地市名称为空============");
					response.getOutputStream().print("1");
				}else{
					if(listTemp.contains(regionCode)||listDemp.contains(regionName)){
						logger.info("============填写资料相同==============");
						response.getOutputStream().print("2");
					}else{
						java.lang.String id = region.getId();
						if (StringUtils.isBlank(id)) {
							region.setId(null);
						}
						region.setRegionCode(regionCode);
						region.setRegionName(regionName);
						region.setState(state);
						regionService.saveorupdate(region);
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
			Region region = regionService.findRegionById(id);
			model.addAttribute("region", region);
			return "/sinova/region/updateRegion";
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
			String regionName = request.getParameter("regionName");
			String state = request.getParameter("state");
			Region region = regionService.findRegionById(id);
			region.setRegionName(regionName);
			region.setState(state);
			
			if(StringUtils.isBlank(regionName)||StringUtils.isBlank(state)){
				response.getOutputStream().print("1");
				logger.info("============地市名称或者有效为空==============");
			}else{
				response.getOutputStream().print("0");
				regionService.saveorupdate(region);
				logger.info("============修改地市成功==============");
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
		return "/sinova/region/regionCru";
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
				regionService.deleteById(id, Region.class);
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
	* @version  2015-01-09 09:12:19
	*/
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model, HttpServletResponse response)throws IOException {
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
					Region region = regionService.findRegionById(ida);
					County county = new County();
					county.setRegionCode(region.getRegionCode());
					List<County> listCounty = countyService.findListDataByFinder(null, null, County.class, county);
					if(listCounty != null && listCounty.size()>0){
						response.getOutputStream().print("2");
					}else{
						regionService.deleteByIds(ids, Region.class);
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
			info = regionService.saveImportExcelFile(destFile,Region.class);
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
