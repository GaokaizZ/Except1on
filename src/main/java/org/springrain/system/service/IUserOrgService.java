package org.springrain.system.service;

import java.util.List;

import org.springrain.system.entity.Org;
import org.springrain.system.entity.User;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2013-07-06 15:28:18
 * @see org.springrain.springrain.service.TuserOrg
 */
public interface IUserOrgService extends IBaseSpringrainService {

	
	/**
	 * 根据组织Id 查找组织下的所有人员
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<User> findUserByOrgId(String orgId) throws Exception;

	
	/**
	 * 根据组织ID,查找组织下(包括所有子组织)的人员
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<User> findAllUserByOrgId(String orgId) throws Exception;
	
	/**
	 * 根据用户ID 查找所在的组织
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<Org> findOrgByUserId(String userId) throws Exception;
	/**
	 * 根据组织ID 查找主管
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	User findManagerByOrgId(String orgId) throws Exception;
	
	
	
}
