package org.springrain.sinova.service;

import java.util.List;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.County;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:12:53
 * @see org.springrain.sinova.service.County
 */
public interface ICountyService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	County findCountyById(Object id) throws Exception;

	/**
	 * 根据地市，查找该地市下的区县
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<County> findCountyByRegionCode(String regionName, String countyName, String regionCode) throws Exception;
	
	
}
