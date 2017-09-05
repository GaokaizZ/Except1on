package org.springrain.sinova.service;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.AdImage;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-05-05 10:37:53
 * @see org.springrain.sinova.service.AdImage
 */
public interface IAdImageService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AdImage findAdImageById(Object id) throws Exception;

	/**
	 * 根据userId查询广告位信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	AdImage findAdImageByUserId(String userId) throws Exception;

}
