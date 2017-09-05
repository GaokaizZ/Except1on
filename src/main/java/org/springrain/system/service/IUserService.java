package org.springrain.system.service;

import java.util.List;

import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.User;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2013-07-06 16:03:00
 * @see org.springrain.springrain.service.User
 */
public interface IUserService extends IBaseSpringrainService {
	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveUser(User entity) throws Exception;

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	Integer updateUser(User entity) throws Exception;

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	User findUserById(Object id) throws Exception;
	
	
	/**
	 * 查询全部人员
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<User> findAllUser() throws Exception;
	
	/**
	 * 查询全省营业员
	 * @return
	 * @throws Exception
	 */
	List<User> findThisRoleUser(Role role,String flag) throws Exception;

	void updateRoleUser(String userId, String roleIds) throws Exception;

	/**
	 * 初始化数据4A:bossNo:姓名:地市：所属：电话
	 * fanjunjiang:daaa06:范俊江:长治:内部:13935563370
	 * 
	 * @param sss
	 * @author frank
	 */
	void initSysUser(String s);

	/**
	 * 登录验证
	 * 
	 * @param u
	 * @return
	 * @author frank
	 */
	boolean validateUser(User u);
	
	/**
	 * 判断工号是否可用 
	 * @param u
	 * @return
	 */
	boolean availableUser(User u);

	/**
	 * 删除用户
	 * @param id
	 */
	void deleteUserById(String id) throws Exception;

	/**
	 * 根据用户id查询拥有的业务
	 * @param userCode
	 * @return
	 */
	List<Business> findBusiness(String userCode) throws Exception;
	

}
