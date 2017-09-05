package org.springrain.system.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IRoleService;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.service.IUserService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2013-07-29 11:36:48
 * @see org.springrain.springrain.service.impl.UserRole
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends BaseSpringrainServiceImpl implements IUserRoleService {

	@Resource
	private IRoleService roleService;
	@Resource
	private IUserService userService;
	
	@Override
	public String save(Object entity) throws Exception {
		UserRole userRole = (UserRole) entity;
		return (String) super.save(userRole);
	}

	@Override
	public String saveorupdate(Object entity) throws Exception {
		UserRole userRole = (UserRole) entity;
		return super.saveorupdate(userRole).toString();
	}

	@Override
	public Integer update(IBaseEntity entity) throws Exception {
		UserRole userRole = (UserRole) entity;
		return super.update(userRole);
	}

	@Override
	public UserRole findUserRoleById(Object id) throws Exception {
		return super.findById(id, UserRole.class);
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object o) throws Exception {
		return super.findListDataByFinder(finder, page, clazz, o);
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * 
	 * @param finder
	 *            为空则只查询 clazz表
	 * @param ftlurl
	 *            类表的模版宏
	 * @param page
	 *            分页对象
	 * @param clazz
	 *            要查询的对象
	 * @param o
	 *            querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl, Page page, Class<T> clazz, Object o) throws Exception {
		return super.findDataExportExcel(finder, ftlurl, page, clazz, o);
	}

	@Override
	public List<Role> findUserRoleByUserId(Object userid,Page page) throws Exception {
		Finder finder = new Finder();
		finder.append("select r.id as id,r.name as name,r.code as code,r.pid as pid,r.remark as remark,r.state as state,r.grade as grade,r.role_type as roleType from t_role r left join t_user_role ur on r.id=ur.ROLE_ID where 1=1 ");
		if(StringUtils.isNotBlank(userid+"")){
			finder.append("and ur.USER_ID =:userid ");
			finder.setParam("userid",userid);
		}
		return roleService.queryForList(finder,Role.class,page);
	}

	@Override
	public List<User> findUserByRoleId(Object roleId,User user,UserOffice userOffice,Page page) throws Exception {
		Finder finder = new Finder();
		finder.append("select u.id as id,u.name as name,u.account as account,u.workno as workno,u.state as state ,rg.region_name AS description,o.office_name as address ");
		finder.append("from t_user u ,t_user_role ur,t_role r,t_user_office uo,t_region rg ,t_office o where 1=1 and u.state = '1' ");
		finder.append("and u.id = ur.user_Id ");
		finder.append("and ur.role_Id = r.id ");
		finder.append("and u.id = uo.user_id ");
		finder.append("and uo.region_code = rg.region_code ");
		finder.append("and uo.office_code = o.office_code ");
		if(StringUtils.isNotBlank(roleId+"")){
			finder.append("and ur.role_Id =:roleId ");
			finder.setParam("roleId",roleId);
		}
		if(user!=null && StringUtils.isNotBlank(user.getName())){
			finder.append("AND u.name =:name ");
			finder.setParam("name",user.getName());
		}
		if(user!=null && StringUtils.isNotBlank(user.getAccount())){
			finder.append("AND u.account =:account ");
			finder.setParam("account",user.getAccount());
		}
		if(user!=null && StringUtils.isNotBlank(user.getState())){
			finder.append("AND u.state =:state ");
			finder.setParam("state",user.getState());
		}
		if(userOffice!=null && StringUtils.isNotBlank(userOffice.getRegionCode())){
			finder.append("AND rg.region_code =:region_code ");
			finder.setParam("region_code",userOffice.getRegionCode());
			logger.info("userOffice.getRegionCode()"+userOffice.getRegionCode());
		}
		if(userOffice!=null && StringUtils.isNotBlank(userOffice.getOfficeCode())){
			finder.append("AND o.office_code =:office_code ");
			finder.setParam("office_code",userOffice.getOfficeCode());
		}
		logger.info("sql:"+finder.getSql());
		return userService.queryForList(finder,User.class,page);
	}

	@Override
	//@CacheEvict(value = GlobalStatic.cacheKey, allEntries = true)
	@CacheEvict(value = GlobalStatic.qxCacheKey, allEntries = true)
	public boolean boundUserRole(String roleId,UserOffice userOffice) throws Exception {
		boolean flag = false;
		if (StringUtils.isNotBlank(userOffice.getUserId()) && StringUtils.isNotBlank(roleId)) {
			
			//init
			Finder finder = new Finder();
			finder.append("select ur.id as id,u.id as user_id from t_user u, t_user_role ur, t_user_office uo ");
			finder.append("where u.id = ur.user_id ");
			finder.append("and u.id = uo.user_id ");
			finder.append("and ur.role_id =:roleId ");
			finder.setParam("roleId",roleId);
			
			if(StringUtils.isNotBlank(userOffice.getRegionCode())){
				finder.append("and uo.region_code =:regionCode ");
				finder.setParam("regionCode",userOffice.getRegionCode());
			}
			if(StringUtils.isNotBlank(userOffice.getCountyCode())){
				finder.append("and uo.county_code =:county_code ");
				finder.setParam("county_code",userOffice.getCountyCode());
			}
			if(StringUtils.isNotBlank(userOffice.getOfficeCode())){
				finder.append("and uo.office_code =:office_code ");
				finder.setParam("office_code",userOffice.getOfficeCode());
			}
			List<UserRole> urdmps =  this.queryForList(finder,UserRole.class,null);
			for (UserRole userRole : urdmps) {
				Role dmpRole = roleService.findIdByRoleCode("ROLE_OFFICE");
				userRole.setRoleId(dmpRole.getId());
				super.update(userRole, true);
				//this.deleteById(userRole.getId(), UserRole.class);
			}
			//bound
			UserRole ur = new UserRole();
			ur.setUserId(userOffice.getUserId());
			List<UserRole> urs = this.findListDataByFinder(null, null, UserRole.class, ur);
			if (urs != null && !urs.isEmpty()) {
				ur = urs.get(0);
				ur.setRoleId(roleId);
				super.update(ur, true);
			} else {
				ur.setRoleId(roleId);
				this.save(ur);
			}
			
			
			
			
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

}
