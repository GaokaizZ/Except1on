package org.springrain.sinova.web;

import java.io.IOException;
import java.net.URLDecoder;
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
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.RegionBusiness;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IRegionBusinessService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.IUserBusinessService;
import org.springrain.sinova.util.Const;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.util.Constant;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2015-01-09 09:14:14
 * @see org.springrain.sinova.web.RegionBusiness
 */
@Controller
@RequestMapping(value = "/regionbusiness")
public class RegionBusinessController extends BaseController {
	@Resource
	private IRegionBusinessService regionBusinessService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IBusinessService businessService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
	private IUserBusinessService userBusinessService;

	private String listurl = "/sinova/regionbusiness/regionbusinessList";

	/**
	 * 进入regionbusiness Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param regionBusiness
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:14:14
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Region region) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		//获取当前登录用户的Id
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(Finder.getTableName(UserRole.class)).append(" where user_id =:userId");
		finders.setParam("userId", userId);
		//查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders, UserRole.class);
		for (int i = 0; i < dataz.size(); i++) {
			UserRole role = dataz.get(i);
			String roleId = role.getRoleId();
			//超级管理员
			if("admin".equals(roleId)){
				//查询全部有效地市
				String regionCode = request.getParameter("regionCode");
				String regionName = request.getParameter("regionName");
				model.addAttribute("regionCode",regionCode);
				model.addAttribute("regionName",regionName);
				Finder finder = Finder.getSelectFinder(Region.class);
				finder.append(" where 1 = 1 and state = 1 ");
				if(StringUtils.isNotBlank(regionCode)){
					finder.append(" and region_code like :regionCode").setParam("regionCode", "%"+regionCode+"%");
				}
				if(StringUtils.isNotBlank(regionName)){
					finder.append(" and region_name like :regionName").setParam("regionName", "%"+regionName+"%");
				}
				List<Region> datas = regionService.queryForList(finder, Region.class, page);
				returnObject.setPage(page);
				returnObject.setData(datas);
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return listurl;
			}else{
				UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
				if (userOfficeDTO == null) {
					logger.warn("当前用户地市区县信息为空，返回空地市列表");
					model.addAttribute(GlobalStatic.returnDatas, returnObject);
					return listurl;
				}
				region.setState(Constant.ONE);// 有效地市

				// 如果不是全地市，则只查询当前用户所在地市
				if (!StringUtils.equals(Const.REGION_ALL, userOfficeDTO.getRegionCode())) {
					region.setRegionCode(userOfficeDTO.getRegionCode());
				}
				List<Region> datas = regionService.findListDataByFinder(null, page, Region.class, region);
				returnObject.setQueryBean(region);
				returnObject.setPage(page);
				returnObject.setData(datas);

				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return listurl;
			}
		}
		return null;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param regionBusiness
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:14:14
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, RegionBusiness regionBusiness) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		Page page = newPage(request);
		List<RegionBusiness> datas = regionBusinessService.findListDataByFinder(null, page, RegionBusiness.class, regionBusiness);
		returnObject.setQueryBean(regionBusiness);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	/**
	 * 查询地市已经有的业务返回页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/buslist")
	public String buslist(HttpServletRequest request, Model model, RegionBusiness regionBusiness, Business business) throws Exception {
		ReturnDatas returnObject = buslistjson(request, model, regionBusiness, business);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		
		return "/sinova/regionbusiness/businessList";
	}
	
	/**
	 * 查询地市已经有的业务
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/buslist/json")
	@ResponseBody
	public ReturnDatas buslistjson(HttpServletRequest request, Model model, RegionBusiness regionBusiness, Business business ) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		Page page = newPage(request);
		String regionName = request.getParameter("regionName");
		if(StringUtils.isNotBlank(regionName)){
			String name = URLDecoder.decode(regionName, "UTF-8");
			model.addAttribute("regionName", name);
		}
		String regionId = request.getParameter("regionId");
		//绑定业务界面查询已经绑定的业务
		String busCode = request.getParameter("busCode");
		String busName = request.getParameter("busName");
		List<Business> datas = businessService.findBusinessByRegionId(regionId, busName, busCode, page);
		returnObject.setQueryBean(business);
		returnObject.setPage(page);
		returnObject.setData(datas);
		if(StringUtils.isNotBlank(busName)&& !"undefined".equals(busName)){
			model.addAttribute("busName", busName);
		}
		if(StringUtils.isNotBlank(busCode)&& !"undefined".equals(busCode)){
			model.addAttribute("busCode", busCode);
		}
		if(StringUtils.isNotBlank(regionId)&& !"undefined".equals(regionId)){
			model.addAttribute("regionId", regionId);
		}
		return returnObject;
	}
	
	/**
	 * 跳转添加未分配业务列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/businessPage")
	public String businessTree(HttpServletRequest request, Model model, Business business) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String regionId = request.getParameter("regionId");
		String regionName = request.getParameter("regionName");
		String name = URLDecoder.decode(regionName, "UTF-8");
		model.addAttribute("regionId", regionId);
		model.addAttribute("regionName", name);
		Page page = newPage(request);
		List<Business> datas = businessService.findBusRegion(regionId ,page);
		returnObject.setQueryBean(business);
		returnObject.setPage(page);
		returnObject.setData(datas);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/regionbusiness/businessPage";
	}
	
	/**
	 * 添加未分配业务到指定地市
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ReturnDatas saveupdate(Model model, RegionBusiness regionBusiness, HttpServletRequest request, HttpServletResponse response,String busIds) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String records = request.getParameter("records");
		String regionId = request.getParameter("regionId");
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
		}
		
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
		}
		try {
			for(int i = 0; i<rs.length; i++){
				
				String id = regionBusiness.getId();
				if(StringUtils.isNotBlank(id)){
					regionBusiness.setId(null);
				}
				String busid = rs[i];
				regionBusiness.setBusId(busid);
				regionBusiness.setRegionId(regionId);
				regionBusinessService.save(regionBusiness);
			}
			response.getOutputStream().print("0");
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}
	
	
	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/regionbusiness/regionbusinessLook";
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
			RegionBusiness regionBusiness = regionBusinessService.findRegionBusinessById(id);
			returnObject.setData(regionBusiness);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}

	/**
	 * 新增/修改save操作,返回json格式数据
	 * 
	 * @param model
	 * @param regionBusiness
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <Auto generate>
	 * @version 2015-01-09 09:14:14
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, RegionBusiness regionBusiness, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		try {
			java.lang.String id = regionBusiness.getId();
			if (StringUtils.isBlank(id)) {
				regionBusiness.setId(null);
			}
			regionBusinessService.saveorupdate(regionBusiness);

		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/regionbusiness/regionbusinessCru";
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
				regionBusinessService.deleteById(id, RegionBusiness.class);
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
	 * @version 2015-01-09 09:14:14
	 */
	@RequestMapping("/deletemore")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model, HttpServletResponse response)throws IOException {
		String records = request.getParameter("records");
		
		if (StringUtils.isBlank(records)) {
			response.getOutputStream().print("1");
		}

		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			response.getOutputStream().print("1");
		}
		try {
			List<String> ids = Arrays.asList(rs);
			//先查找出用户业务关系
			Finder finder = new Finder();
			finder.append("select ub.id from t_user_business ub where ub.bus_id in( select rb.bus_id from t_region_business rb where rb.id in (:ids))");
			finder.setParam("ids", ids);
			List<UserBusiness> userBus = userBusinessService.queryForList(finder, UserBusiness.class);
			logger.info("----------查找出用户业务关系------------------");
			//删除地市业务关系
			regionBusinessService.deleteByIds(ids, RegionBusiness.class);
			//删除地市业务关系的同时删除用户业务关系
			if(userBus.size()>0){
				for(int i=0; i<userBus.size(); i++){
					String userBusId = userBus.get(i).getId();
					userBusinessService.deleteById(userBusId, UserBusiness.class );
					logger.info("-----------删除用户业务关系-------------");
				}
			}
			response.getOutputStream().print("0");
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}

}
