package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Page;
import org.springrain.system.entity.User;
import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.Qrcode;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:39
 * @see org.springrain.sinova.service.Qrcode
 */
public interface IQrcodeService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Qrcode findQrcodeById(Object id) throws Exception;
	
	/**
     * save qrcode以及关系表
     * @param qrcode
     * @return
     * @throws Exception
     */
     String saveQrcode(Qrcode qrcode) throws Exception;

	Qrcode findQrcodeByFlowNo(Object flowNo) throws Exception;

	List<Qrcode> findQrcodeByBelongUser(Qrcode qrcode, Page page) throws Exception;
	
	User findWorkNoByPhone(Object phone) throws Exception;
	
	List<Qrcode> findQrcodeByQrcodeName(Qrcode qrcode, Page page) throws Exception;
}
