package org.springrain.sinova.service;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.QrcodeBusiness;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:15:29
 * @see org.springrain.sinova.service.QrcodeBusiness
 */
public interface IQrcodeBusinessService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	QrcodeBusiness findQrcodeBusinessById(Object id) throws Exception;



}
