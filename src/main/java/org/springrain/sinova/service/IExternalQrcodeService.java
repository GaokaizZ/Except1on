package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.ExternalQrcodeDTO;
import org.springrain.sinova.entity.ExternalQrcode;
import org.springrain.system.service.IBaseSpringrainService;

public interface IExternalQrcodeService extends IBaseSpringrainService{
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ExternalQrcode findExternalQrcodeById(Object id) throws Exception;
	
	List<ExternalQrcode> findExternalQrcode(ExternalQrcode exQrcode, Page page) throws Exception;

	List<ExternalQrcodeDTO> findByUser(ExternalQrcodeDTO exQrcode, Page page) throws Exception;
	
	

}
