package org.springrain.sinova.service;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.Brand;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 08:57:50
 * @see org.springrain.sinova.service.Brand
 */
public interface IBrandService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Brand findBrandById(Object id) throws Exception;



}
