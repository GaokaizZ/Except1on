package org.springrain.sinova.service;

import java.util.List;

import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.AcceptRecord;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:17:04
 * @see org.springrain.sinova.service.AcceptRecord
 */
public interface IAcceptRecordService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AcceptRecord findAcceptRecordById(Object id) throws Exception;

	/**
	 * 验证是否能抽奖
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	List<AcceptRecord> validateLottery(String mobile) throws Exception;
}
