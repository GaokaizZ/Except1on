package org.springrain.sinova.service;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.ExecuteTime;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:17:37
 * @see org.springrain.sinova.service.ExecuteTime
 */
public interface IExecuteTimeService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ExecuteTime findExecuteTimeById(Object id) throws Exception;


	void addExcuteTime(String className,String methodName,String mobile,String account,String ixml,String oxml) throws Exception;
}
