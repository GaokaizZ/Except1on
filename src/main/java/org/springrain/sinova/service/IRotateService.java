package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.RotateDTO;
import org.springrain.sinova.entity.Rotate;
import org.springrain.system.service.IBaseSpringrainService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2015-01-09 09:16:10
 * @see org.springrain.sinova.service.UserOffice
 */
public interface IRotateService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Rotate findRotateById(Object id) throws Exception;

	 
	
	/**
	 * 根据Rotate对象查找
	 * @param Rotate
	 * @return
	 * @throws Exception
	 */
	List<Rotate> findRoateByDTO(RotateDTO rotate, Page page) throws Exception;
	
	/**
	 * 查询抽奖参与总人数
	 * @return Integer  抽奖参与总人数
	 * @throws Exception
	 */
	Integer getAllCount() throws Exception;
	
	List<RotateDTO> findRoateListByDTO(RotateDTO rotate, Page page) throws Exception;
	
	/**
	 * 根据业务办理id查询抽奖信息
	 * @param acceptId
	 * @return
	 * @throws Exception
	 */
	List<RotateDTO> findRoateListByAcceptId(String acceptId) throws Exception;



	List<Rotate> findFailRoate() throws Exception;



	Integer getLotteryCount() throws Exception;
}
