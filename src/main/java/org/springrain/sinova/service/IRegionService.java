package org.springrain.sinova.service;

import java.util.List;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.Region;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:12:19
 * @see org.springrain.sinova.service.Region
 */
public interface IRegionService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Region findRegionById(Object id) throws Exception;
	
	/**
	 * 查询地市
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<Region> findRegion(String regionName) throws Exception;
	
	/**
	 * 根据城市编码来查询地市
	 * @param 
	 * @return
	 * @throws Exception
	 */
	List<Region> findRegionByRegionCode(String regionCode) throws Exception;
	
	/**
	 * 王玉军
	 * 查询全部地市
	 * @param 
	 * @return
	 * @throws Exception
	 */
	List<Region> findRegionList() throws Exception;

}
