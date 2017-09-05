package org.springrain.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserRole;
/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2013-07-29 11:36:48
 * @see org.springrain.springrain.service.UserRole
 */
public interface IUserRoleService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */ 
	UserRole findUserRoleById(Object id) throws Exception;
	
	List<Role> findUserRoleByUserId(Object id,Page page) throws Exception;
	
	List<User> findUserByRoleId(Object id,User user,UserOffice userOffice,Page page) throws Exception;
	
	boolean boundUserRole(String roleId,UserOffice userOffice) throws Exception;
	
}
