package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.RegionBusiness;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2015-01-09 09:14:14
 * @see org.springrain.sinova.service.RegionBusiness
 */
public interface IRegionBusinessService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	RegionBusiness findRegionBusinessById(Object id) throws Exception;
	
	/**
	 * 根据活动ID来查找
	 * 王玉军
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	List<RegionBusiness> findRegionBusinessByBusId(String actId) throws Exception;

	/**
	 * 根据中间表，业务表查询所需业务
	 * @param finder
	 * @param page
	 * @param regionBusiness
	 * @param business
	 * @return
	 * @throws Exception
	 */
	List<Business> findListDataByRegionBusiness(Finder finder, Page page, RegionBusiness regionBusiness,Business business)  throws Exception;
	
	/**批量更新
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Integer updateRegionBusiness(RegionBusiness regionBusiness,String busIds) throws Exception; 
	
	
	

}
