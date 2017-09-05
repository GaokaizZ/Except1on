package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.RegionBusiness;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.system.service.IBaseSpringrainService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:15:03
 * @see org.springrain.sinova.service.UserBusiness
 */
public interface IUserBusinessService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UserBusiness findUserBusinessById(Object id) throws Exception;

	/**
	 * 根据业务ID或用户ID查询关系表
	 * @param userId
	 * @param busId
	 * @return
	 * @throws Exception
	 */
    List<UserBusiness> findUserBusinessByubId(String userId,String busId) throws Exception;
    
	/**批量更新
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Integer updateUserBusiness(UserBusiness userBusiness,String busIds) throws Exception; 

	void deleteByBusId(String busId)throws Exception;
}
