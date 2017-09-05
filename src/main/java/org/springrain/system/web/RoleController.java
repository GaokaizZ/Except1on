package org.springrain.system.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.system.entity.Menu;
import org.springrain.system.entity.Role;
import org.springrain.system.service.IRoleService;
import org.springrain.system.service.IUserRoleMenuService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2013-07-29 11:36:46
 * @see org.springrain.springrain.web.Role
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleController extends BaseController {
	@Resource
	private IRoleService roleService;
	@Resource
	private IUserRoleMenuService userRoleMenuService;

	private String listurl = "/system/role/roleList";

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Role role) throws Exception {
		ReturnDatas returnObject = listjson(request, model, role);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody ReturnDatas listjson(HttpServletRequest request, Model model, Role role) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		String roleCode = request.getParameter("code");
		String roleName = request.getParameter("name");
		Finder finder = new Finder("select * from t_role where 1=1 ");
		if(StringUtils.isNotBlank(roleCode)){
			finder.append(" and code like :roleCode").setParam("roleCode", "%"+roleCode+"%");
			model.addAttribute("roleCode", roleCode);
		}
		if(StringUtils.isNotBlank(roleName)){
			finder.append(" and name like :roleName").setParam("roleName", "%"+roleName+"%");
			model.addAttribute("roleName", roleName);
		}

		List<Role> datas = roleService.queryForList(finder, Role.class, page);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	/**
	 * 新增 跳页
	 * 
	 */
	@RequestMapping(value = "/beforeSave")
	public String beforeSave(HttpServletRequest request, Model model) throws Exception{
		logger.info("========添加用户角色跳页========");
		return "/system/role/addRole";
	}
	/**
	 * 修改 操作
	 * 
	 */
	@RequestMapping("/save")
	public @ResponseBody ReturnDatas save(Role role, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			Role roles = new Role();
			String id = request.getParameter("id");
			String code = request.getParameter("code");
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			Finder finders = new Finder("select * from t_role");
			List<Role> datas = roleService.queryForList(finders, Role.class, null);
			List listDemp = new ArrayList();
			List listTemp = new ArrayList();
			for (int i = 0; i < datas.size(); i++) {
				Role ro = datas.get(i);
				listDemp.add(ro.getName());
				listTemp.add(ro.getCode());
			}
			if(listDemp.contains(name)||listTemp.contains(code)){
				response.getOutputStream().print("1");
				}else{
					if(StringUtils.isBlank(id)){
						roles.setId(null);
					}
					roles.setCode(code);
					roles.setName(name);
					roles.setState("1");
					roles.setRemark(remark);
					String strMenuIds = request.getParameter("menuIds");
					if (StringUtils.isBlank(strMenuIds)) {
						role.setMenus(null);
					} else {
						String[] menuIds = strMenuIds.split(",");
						if (ArrayUtils.isNotEmpty(menuIds)) {
							List<Menu> menus = new ArrayList<Menu>();
							for (String s : menuIds) {
								if (StringUtils.isBlank(s)) {
									continue;
								}
								Menu m = new Menu();
								m.setId(s);
								menus.add(m);
							}
							roles.setMenus(menus);
						}
					}
					roleService.saveorupdateRole(roles);
					response.getOutputStream().print("0");
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
		String roleId = request.getParameter("roleId");
		if(StringUtils.isNotBlank(roleId)){
			Role role = userRoleMenuService.findRoleAndMenu(roleId);
			List<Menu> menus = role.getMenus();
			StringBuilder sb = new StringBuilder(); 
			StringBuilder sf = new StringBuilder(); 
			if(menus != null && menus.size()>0){
				for (int i = 0; i < menus.size(); i++) {
					Menu menu = menus.get(i);
					if (i < menus.size() - 1) {  
			               sb.append(menu.getName() + ","); 
			               sf.append(menu.getId()+",");
			           } else {  
			               sb.append(menu.getName());
			               sf.append(menu.getId());
			           }
				}
			}
			model.addAttribute("role", role);
			model.addAttribute("menuNames", sb.toString());
			model.addAttribute("menuIds", sf.toString());
			return "/system/role/updateRole";
		}
		return null;
	}
	/**
	 * 修改 操作
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody ReturnDatas saveorupdate(Role role, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			Finder finders = new Finder("select * from t_role");
			List<Role> datas = roleService.queryForList(finders, Role.class, null);
			List list = new ArrayList();
			for (int i = 0; i < datas.size(); i++) {
				Role ro = datas.get(i);
				list.add(ro.getName());
			}
			Role roles = roleService.findRoleById(id);
			String roleName = roles.getName();
			if(roleName.equals(name)){
				roles.setName(name);
				roles.setRemark(remark);
				String strMenuIds = request.getParameter("menuIds");
				if (StringUtils.isBlank(strMenuIds)) {
					role.setMenus(null);
				} else {
					String[] menuIds = strMenuIds.split(",");
					if (ArrayUtils.isNotEmpty(menuIds)) {
						List<Menu> menus = new ArrayList<Menu>();
						for (String s : menuIds) {
							if (StringUtils.isBlank(s)) {
								continue;
							}
							Menu m = new Menu();
							m.setId(s);
							menus.add(m);
						}
						roles.setMenus(menus);
					}
				}
				roleService.saveorupdateRole(roles);
				response.getOutputStream().print("0");
			}else{
				if(list.contains(name)){
					response.getOutputStream().print("1");
				}else{
					roles.setName(name);
					roles.setRemark(remark);
					String strMenuIds = request.getParameter("menuIds");
					if (StringUtils.isBlank(strMenuIds)) {
						role.setMenus(null);
					} else {
						String[] menuIds = strMenuIds.split(",");
						if (ArrayUtils.isNotEmpty(menuIds)) {
							List<Menu> menus = new ArrayList<Menu>();
							for (String s : menuIds) {
								if (StringUtils.isBlank(s)) {
									continue;
								}
								Menu m = new Menu();
								m.setId(s);
								menus.add(m);
							}
							roles.setMenus(menus);
						}
					}
					roleService.saveorupdateRole(roles);
					response.getOutputStream().print("0");
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
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody ReturnDatas destroy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("roleId");
			if (StringUtils.isNotBlank(id)) {
				roleService.deleteById(id, Role.class);
				response.getOutputStream().print("0");
			} else {
				response.getOutputStream().print("1");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}

	/**
	 * 删除多条记录
	 * 
	 */
	@RequestMapping("/delete/more")
	public @ResponseBody ReturnDatas delMultiRecords(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
		}
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			roleService.deleteByIds(ids, Role.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_ALL_SUCCESS);
	}
}