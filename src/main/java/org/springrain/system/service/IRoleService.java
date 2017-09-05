package org.springrain.system.service;

import java.util.List;

import org.springrain.system.entity.Role;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2013-07-06 16:02:59
 * @see org.springrain.springrain.service.Role
 */
public interface IRoleService extends IBaseSpringrainService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveRole(Role entity) throws Exception;
	/**
	 * 修改或者保存,根据id是否为空判断
	 * @param entity
	 * @return
	 * @throws Exception
	 */
    String saveorupdateRole(Role entity) throws Exception;
	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateRole(Role entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Role findRoleById(Object id) throws Exception;
	/**
	 * 根据id查询name
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	String findNameById(String roleId)throws Exception;
	
	/**通过code查询id
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	Role findIdByRoleCode(String code)throws Exception;
	
	/**
	 * 
	 * @description 删除多条角色用户记录 <br/>
	 * @date 2015年1月19日 下午8:53:33 <br/>
	 * @param ids
	 * @throws Exception
	 */
	public void deleteMultiRoleUserRecords(List<String> ids) throws Exception;
	
	
	/**
	 * 
	 * @description 保存角色用户记录 <br/>
	 * @date 2015年1月19日 下午8:53:33 <br/>
	 * @param roleId
	 * @param ids
	 * @throws Exception
	 */
	public void savewaituserList(String roleId, List<String> ids) throws Exception;
	
}
