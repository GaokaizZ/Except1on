package org.springrain.sinova.service;

import java.util.Map;

import org.springrain.frame.util.Page;
import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.AdMsg;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-06-25 10:57:10
 * @see org.springrain.sinova.service.AdMsg
 */
public interface IAdMsgService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AdMsg findAdMsgById(Object id) throws Exception;
	
	public Map<String,AdMsg> findAdMsgByUser(String userId,Page page) throws Exception ;



}
