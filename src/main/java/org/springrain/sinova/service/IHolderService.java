package org.springrain.sinova.service;

import java.util.List;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.Holder;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-04-20 11:59:16
 * @see org.springrain.sinova.service.Holder
 */
public interface IHolderService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Holder findHolderById(Object id) throws Exception;


	List<Holder> findByHolder(Holder holder) throws Exception;
}
