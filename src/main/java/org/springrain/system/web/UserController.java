package org.springrain.system.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.shiro.ShiroUser;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.SecUtils;
import org.springrain.sinova.dto.UserNameDTO;
import org.springrain.sinova.entity.County;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.sinova.service.ICountyService;
import org.springrain.sinova.service.IOfficeService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.sinova.service.impl.CountyServiceImpl;
import org.springrain.sinova.util.Const;
import org.springrain.system.entity.Org;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IDicDataService;
import org.springrain.system.service.IRoleService;
import org.springrain.system.service.IUserRoleMenuService;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.service.IUserService;
import org.springrain.system.util.Constant;

/**
 * 用户管理Controller,PC和手机浏览器用ACE自适应,APP提供JSON格式的数据接口
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2014-06-26 11:36:47
 * @see org.springrain.springrain.web.User
 */
@Controller
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {
	@Resource
	private IUserService userService;
	@Resource
	private IUserOfficeService userOfficeService;
	@Resource
	private IRegionService regionService;
	@Resource
	private ICountyService countyService;
	@Resource
	private IOfficeService officeService;
	@Resource
	private IUserRoleMenuService userRoleMenuService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IDicDataService dicDataService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private ISitechInterface sitechInterFace;

	private String listurl = "/system/user/userList";
	private String rolelisturl = "/system/user/userRoleList";

	@RequestMapping("/init")
	public String test(HttpServletRequest request, Model model) throws Exception {
		/*
		 * String 大同 = "yuansuipeng:jaamyT:运城:原随鹏:渠道:13800000000"; 大同 = ""; userService.initSysUser(大同);
		 */
		return listurl;
	}
	
	/**
	 * 列表数据,区分权限查询用户
	 * 
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userRoleList")
	public String userRoleList(HttpServletRequest request, Model model, User user) throws Exception {
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String state = request.getParameter("state");
		String regionCode = request.getParameter("regionCode");
		String dicRoleCode = request.getParameter("dicRoleCode");
		
		ReturnDatas returnObject = new ReturnDatas();
		Page page = newPage(request);
		User userDTO = new User();
		userDTO.setAccount(account);
		userDTO.setName(name);
		userDTO.setState(state);
		UserOffice userOfficeDTO = new UserOffice();
		userOfficeDTO.setRegionCode(regionCode);
		
		//查询当前用户的营业厅关系
		UserOffice uo = userOfficeService.findUserOfficeDTOByUserId(SessionUser.getShiroUser().getId());
		//查询当前用户下的地市
		List<Region> rs = regionService.findRegion("");
		model.addAttribute("regions", rs);
		//查询当前用户的地市下的区县
		if(uo != null){
			if(StringUtils.equals(dicRoleCode, Constant.ROLE_OFFICE_MANAGE)){//营业厅管理员
				userOfficeDTO.setOfficeCode(uo.getOfficeCode());
			}else{
				userOfficeDTO.setRegionCode(uo.getRegionCode());
			}
			List<County> cs = countyService.findCountyByRegionCode("", "", uo.getRegionCode());
			model.addAttribute("counties", cs);
			List<Office> os = officeService.findOfficeByRegionCode(uo.getRegionCode(),uo.getCountyCode(),"");
			model.addAttribute("offices", os);
		}
		//根据权限查询
//		Role thisRole = SessionUser.getShiroUser().getRole();
//		logger.info(dicDataService.findDicByCode(thisRole.getCode()));
//		List<User> datas = userRoleService.findUserByRoleId((StringUtils.isBlank(dicDataService.findDicByCode(thisRole.getCode())))?"":dicDataService.findDicByCode(thisRole.getCode()), userDTO, userOfficeDTO, page);// 地市管理员
//		returnObject.setQueryBean(user);
//		returnObject.setPage(page);
//		returnObject.setData(datas);
		
		//根据链接查询
		
		logger.info(dicDataService.findDicByCode(dicRoleCode));
		List<User> datas = userRoleService.findUserByRoleId((StringUtils.isBlank(dicDataService.findDicByCode(dicRoleCode)))?"":dicDataService.findDicByCode(dicRoleCode), userDTO, userOfficeDTO, page);// 地市管理员
		returnObject.setQueryBean(user);
		returnObject.setPage(page);
		returnObject.setData(datas);
		
		model.addAttribute("dicRoleCode", dicRoleCode);
		model.addAttribute("state", state);
		model.addAttribute("user", userDTO);
		model.addAttribute("userOffice", uo);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return rolelisturl;
	}
	
	/**
	 * 增加权限
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userRoleList/befBoundUser")
	public String befBoundUser(HttpServletRequest request, Model model,User user) throws Exception {
		
		String dicRoleCode = request.getParameter("dicRoleCode");
		String regionCode = request.getParameter("regionCode");
		String countyCode = request.getParameter("countyCode");
		String officeCode = request.getParameter("officeCode");
		
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		user.setId(SessionUser.getShiroUser().getId());
		UserOffice userOffice = new UserOffice();
		userOffice.setRegionCode(regionCode);
		userOffice.setCountyCode(countyCode);
		userOffice.setOfficeCode(officeCode); 
		List<User> datas = userOfficeService.findOfficeUserDTOByOffice(SessionUser.getShiroUser().getRole().getCode(),user,userOffice,page);
		returnObject.setQueryBean(datas);
		returnObject.setPage(page);
		returnObject.setData(datas);
		model.addAttribute("dicRoleCode", dicRoleCode);
		model.addAttribute("regionCode", regionCode);
		model.addAttribute("countyCode", countyCode);
		model.addAttribute("officeCode", officeCode);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/user/boundUser";
	}
	
	@RequestMapping("/userRoleList/boundUser")
	@ResponseBody
	public ReturnDatas boundUser(HttpServletRequest request, Model model,UserOffice uo) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
//		String userId = request.getParameter("userId");
		String dicRoleCode = request.getParameter("dicRoleCode");
//		String regionCode = request.getParameter("regionCode");
//		String countyCode = request.getParameter("countyCode");
//		String officeCode = request.getParameter("officeCode");
		
		
		String roleId = dicDataService.findDicByCode(dicRoleCode);
		
		boolean b = userRoleService.boundUserRole(roleId,uo);
		if(b){
			logger.info("绑定角色成功");
			return new ReturnDatas(Constant.ONE, "成功");
		}else{
			returnObject.setStatus(ReturnDatas.ERROR);
			return new ReturnDatas(Constant.ZERO, "绑定失败");
		}
	}
	
	
	@RequestMapping("/userRoleList/searchCounty")
	@ResponseBody
	public List<County> searchCounty(HttpServletRequest request,HttpSession session, Model model) throws Exception {
		String regionCode = request.getParameter("regionCode");
		List<County> cs = countyService.findCountyByRegionCode("", "", regionCode);
		model.addAttribute("counties", cs);
		return cs;
	}
	@RequestMapping("/userRoleList/searchOffice")
	@ResponseBody
	public List<Office> searchOffice(HttpServletRequest request,HttpSession session, Model model) throws Exception {
		String regionCode = request.getParameter("regionCode");
		String countyCode = request.getParameter("countyCode");
		String officeCode = request.getParameter("officeCode");
		List<Office> os = officeService.findOfficeByRegionCode(regionCode,countyCode,officeCode);
		model.addAttribute("offices", os);
		return os;
	}
	
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, User user) throws Exception {
		ReturnDatas returnObject = listjson(request, model, user);
		Page page = newPage(request);
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String state = request.getParameter("state");
		model.addAttribute("account", account);
		model.addAttribute("name", name);
		Finder finder = Finder.getSelectFinder(User.class);
		finder.append("where 1 = 1 ");
		if(StringUtils.isNotBlank(account)){
			finder.append(" and account like :account").setParam("account", "%"+account+"%");
		}
		if(StringUtils.isNotBlank(name)){
			finder.append(" and name like :name").setParam("name", "%"+name+"%");
		}
		if(StringUtils.isNotBlank(state)){
			finder.append(" and state = :state").setParam("state", state);
		}
		List<User> datas = userService.queryForList(finder, User.class, page);
		returnObject.setQueryBean(user);
		returnObject.setPage(page);
		returnObject.setData(datas);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody ReturnDatas listjson(HttpServletRequest request, Model model, User user) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		List<User> datas = userService.findListDataByFinder(null, page, User.class, user);
		returnObject.setQueryBean(user);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 导出Excle格式的数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param user
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model, User user) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);
		File file = userService.findDataExportExcel(null, listurl, page, User.class, user);
		String fileName = "user" + GlobalStatic.excelext;
//		downFile(response, file, fileName, true);
		return;
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/user/userLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody ReturnDatas lookjson(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			User user = userService.findUserById(id);
			returnObject.setData(user);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}

	/**
	 * 添加跳页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/gotoAdd")
	public String beforeAdd(HttpServletRequest request, Model model) throws Exception{
		//查询角色
		Page page = newPage(request);
		Role role = new Role();
		List<Role> roleList = roleService.findListDataByFinder(null, page, Role.class, role);
		if(null!=roleList && roleList.size()>0){
			model.addAttribute("rlist", roleList);
		}
		return "/system/user/beforeAddUser";
	}
	
	/**
	 * 新增 操作,返回json格式数据
	 * 
	 */
	@RequestMapping("/save")
	public @ResponseBody ReturnDatas saveorupdate(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String id = user.getId();
			String password = user.getPassword();

			if (StringUtils.isBlank(password)) {
				user.setPassword(null);
			} else {
				user.setPassword(SecUtils.encoderByMd5With32Bit(password));
			}

			String str_orgIds = request.getParameter("orgIds");
			String[] orgIds = null;
			List<Org> listOrg = null;

			if (StringUtils.isNotBlank(str_orgIds)) {
				orgIds = str_orgIds.split(",");
			}
			if (orgIds != null && orgIds.length > 0) {
				Set<String> set = new HashSet<String>();
				for (String s : orgIds) {
					if (StringUtils.isBlank(s)) {
						continue;
					}
					set.add(s);
				}
				listOrg = new ArrayList<Org>();
				for (String s2 : set) {
					Org org = new Org();
					org.setId(s2);
					listOrg.add(org);
				}

			}

			user.setUserOrgs(listOrg);

			String roleId = request.getParameter("roleIds");
			String[] roleIds = {};
			if(StringUtils.isNotBlank(roleId)){
				roleIds = roleId.split(",");
			}
			List<Role> listRole = null;
			if (roleIds != null && roleIds.length > 0) {
				/*Set<String> set = new HashSet<String>();
				for (String s : roleIds) {
					if (StringUtils.isBlank(s)) {
						continue;
					}
					set.add(s);
				}
				listRole = new ArrayList<Role>();
				for (String s2 : set) {
					Role role = new Role();
					role.setId(s2);
					listRole.add(role);
				}*/
				listRole = new ArrayList<Role>();
				for(int i=0;i<roleIds.length;i++){
					Role role = new Role();
					role.setId(roleIds[i]);
					listRole.add(role);
				}
			}

			user.setUserRoles(listRole);

			if (StringUtils.isBlank(id)) {
				user.setId(null);
				//查询用户表
				User users = new User();
				List<User> datas = userService.findListDataByFinder(null, null, User.class, users);
				List listTemp = new ArrayList();
				List listTemp2 = new ArrayList();
				String account = request.getParameter("account");//用户帐号
				String name = request.getParameter("name");//用户名字
				String workno = request.getParameter("workno");//用户名字
				String mobile = request.getParameter("mobile");//手机号
				String state = request.getParameter("state");//是否有效
				if(StringUtils.isNotBlank(mobile)){
					//验证手机号是否为山西移动用户
					String flag = checkPhone(mobile);
					if(Constant.ZERO.equals(flag)){
						response.getOutputStream().print("9");
						response.getOutputStream().flush();
						response.getOutputStream().close();
						return returnObject;
					}else if(Constant.ONE_NEGATIVE.equals(flag)){
						response.getOutputStream().print("99");
						response.getOutputStream().flush();
						response.getOutputStream().close();
						return returnObject;
					}
				}
				
				if(datas == null){
					if(StringUtils.isBlank(account)||StringUtils.isBlank(name)||StringUtils.isBlank(state)){
						response.getOutputStream().print("1");
						logger.info("============输入用户资料不全============");
					}else{
						user.setAccount(account);
						user.setName(name);
						user.setWorkno(workno);
						user.setMobile(mobile);
						user.setState(state);
						userService.saveUser(user);
						response.getOutputStream().print("0");
						logger.info("============添加成功==============");
					}
				}
				if(datas != null && datas.size()>0){
					for (int i = 0; i < datas.size(); i++) {
						User userTemp = datas.get(i);
						String accountTemp = userTemp.getAccount();
						listTemp.add(accountTemp);
					}
					for(int j=0;j<datas.size();j++){
						User userTemp2 = datas.get(j);
						String worknoTemp = userTemp2.getWorkno();
						listTemp2.add(worknoTemp);
					}
					if(StringUtils.isBlank(account)||StringUtils.isBlank(name)||StringUtils.isBlank(state)){
						response.getOutputStream().print("1");
						logger.info("============输入用户资料不全============");
					}else{
						if(listTemp.contains(account)){
							response.getOutputStream().print("2");
							logger.info("============输入用户帐号相同============");
						}else if(listTemp2.contains(workno)){
							response.getOutputStream().print("2");
							logger.info("============输入用户工号相同============");
						}else{
							user.setAccount(account);
							user.setName(name);
							user.setWorkno(workno);
							user.setMobile(mobile);
							user.setState(state);
							userService.saveUser(user);
							response.getOutputStream().print("0");
							logger.info("============添加成功==============");
						}
					}
				}

			}
//			else{
//				userService.updateUser(user);
//			}

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
		Page page = newPage(request);
		if(StringUtils.isNotBlank(id)){
			User user = userService.findUserById(id);
			if(null!=user){
				//查询所有角色
				Finder finder = new Finder("select * from t_role order by id desc");
				List<Role> roleList = roleService.queryForList(finder, Role.class);
				List<Role> resuList = new ArrayList<Role>();
				if(null!=roleList && roleList.size()>0){
					model.addAttribute("rList", roleList);
					//查询该用户对应的角色
					List<Role> list = userRoleMenuService.findRoleByUserId(id);
					if(null!=list && list.size()>0){
						model.addAttribute("list", list);
						for(int i=0;i<roleList.size();i++){
							Role r1 = roleList.get(i);
							String flag = "0";
							for(int j=0;j<list.size();j++){
								Role r2 = list.get(j);
								if(r1.getId().equals(r2.getId())){
									flag = "1";
								}
							}
							r1.setRemark(flag);
							resuList.add(r1);
						}
					}
				}
				model.addAttribute("resuList", resuList);
				model.addAttribute("user", user);
			}
		}
		
		return "/system/user/beforeUpdateUser";
	}
	
	/**
	 * 修改 操作,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody ReturnDatas update(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String id = user.getId();
			String password = user.getPassword();

			if (StringUtils.isBlank(password)) {
				user.setPassword(null);
			} else {
				user.setPassword(SecUtils.encoderByMd5With32Bit(password));
			}

			String str_orgIds = request.getParameter("orgIds");
			String[] orgIds = null;
			List<Org> listOrg = null;

			if (StringUtils.isNotBlank(str_orgIds)) {
				orgIds = str_orgIds.split(",");
			}
			if (orgIds != null && orgIds.length > 0) {
				Set<String> set = new HashSet<String>();
				for (String s : orgIds) {
					if (StringUtils.isBlank(s)) {
						continue;
					}
					set.add(s);
				}
				listOrg = new ArrayList<Org>();
				for (String s2 : set) {
					Org org = new Org();
					org.setId(s2);
					listOrg.add(org);
				}

			}

			user.setUserOrgs(listOrg);

			//修改数据之前先删除中间表userRole中原先user对应数据
			String uid = request.getParameter("id");
			UserRole userRole = new UserRole();
			userRole.setUserId(uid);
			List<UserRole> urList = userRoleService.findListDataByFinder(null, null, UserRole.class, userRole);
			if(null!=urList && urList.size()>0){
				for(int i=0;i<urList.size();i++){
					UserRole ur = urList.get(i);
					if(null!=ur){
						userRoleService.deleteById(ur.getId(), UserRole.class);
					}
				}
			}
			
			String roleId = request.getParameter("roleIds");
			String[] roleIds = {};
			if(StringUtils.isNotBlank(roleId)){
				roleIds = roleId.split(",");
			}
			List<Role> listRole = null;
			if (roleIds != null && roleIds.length > 0) {
				/*Set<String> set = new HashSet<String>();
				for (String s : roleIds) {
					if (StringUtils.isBlank(s)) {
						continue;
					}
					set.add(s);
				}
				listRole = new ArrayList<Role>();
				for (String s2 : set) {
					Role role = new Role();
					role.setId(s2);
					listRole.add(role);
				}*/
				listRole = new ArrayList<Role>();
				for(int i=0;i<roleIds.length;i++){
					Role role = new Role();
					role.setId(roleIds[i]);
					listRole.add(role);
				}
			}

			user.setUserRoles(listRole);

			if (StringUtils.isBlank(id)) {
				user.setId(null);
			}
			String userName = user.getName();
			String userPhone = user.getMobile();
			if(StringUtils.isBlank(userName)||StringUtils.isBlank(userPhone)){
				response.getOutputStream().print("1");
				logger.info("============数据为空============");
			}else{
				if(StringUtils.isNotBlank(userPhone)){
					//验证手机号是否为山西移动用户
					String flag = checkPhone(userPhone);
					if(Constant.ZERO.equals(flag)){
						response.getOutputStream().print("9");
						response.getOutputStream().flush();
						response.getOutputStream().close();
						return returnObject;
					}else if(Constant.ONE_NEGATIVE.equals(flag)){
						response.getOutputStream().print("99");
						response.getOutputStream().flush();
						response.getOutputStream().close();
						return returnObject;
					}
				}
				
				response.getOutputStream().print("0");
				userService.updateUser(user);
				logger.info("============修改用户成功==============");
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
		return "/system/user/userCru";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody ReturnDatas destroy(HttpServletRequest request) throws Exception {
		// 执行删除
		try {
			java.lang.String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
//				userService.deleteById(id, User.class);
				userService.deleteUserById(id);
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
	 */
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model,HttpServletResponse response) throws IOException{
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
            userService.deleteByIds(ids, User.class);
            response.getOutputStream().print("0");
        	} catch (Exception e) {
        		response.getOutputStream().print("1");
        }
        response.getOutputStream().flush();
		response.getOutputStream().close();
        return null;
	}

	/**
	 * 验证手机号是否为山西移动用户
	 * @param request
	 * @param model
	 * @return
	 */
	
	public String checkPhone(String mobile){
		logger.info("checkPhone");
		if(StringUtils.isNotBlank(mobile)){
			try {
				String code = sitechInterFace.isSXUser(mobile);
				if(Constant.ONE.equals(code)){
					return Constant.ONE;
				}else{
					return Constant.ZERO;
				}
			} catch (Exception e) {
				logger.error("checkPhone error" + e.getLocalizedMessage());
				return Constant.ONE_NEGATIVE;
			}
		}else{
			logger.info("checkMobiele  fail");
			return Constant.ONE_NEGATIVE;
		}
	}
	
	
}
