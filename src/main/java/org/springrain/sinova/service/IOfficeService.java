package org.springrain.sinova.service;

import java.util.List;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.Office;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:01
 * @see org.springrain.sinova.service.Office
 */
public interface IOfficeService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Office findOfficeById(Object id) throws Exception;
	
	/**
	 * 通过地市编码查询
	 * @param officeCode
	 * @return
	 * @throws Exception
	 */
	List<Office> findOfficeByRegionCode(String regionCode,String countyCode,String officeCode) throws Exception;



}
