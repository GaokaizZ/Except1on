package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.SpecialQrcodeDTO;
import org.springrain.sinova.dto.SpecialTreeDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.SpecialQrcode;
import org.springrain.system.service.IBaseSpringrainService;

/**
 * @author Administrator
 *
 */
public interface ISpecialQrcodeService extends IBaseSpringrainService{
	
	 List<Business> findBusByUserId(String userId) throws Exception;
	 
	 List<Goods> findGoodsbyUserId(String userId) throws Exception;
	 
	 List<SpecialQrcodeDTO> findSpecialQrcodeDTO(SpecialQrcodeDTO specialQrcode, Page page) throws Exception;
	 
	 List<Business> findBus(SpecialQrcodeDTO specialQrcode, Page page) throws Exception;
	 
	 void deleteByBelongUserId(String userId) throws Exception;
	 
	 List<SpecialTreeDTO> findTypeBusDTO(SpecialQrcodeDTO specialQrcode) throws Exception;
	 List<SpecialTreeDTO> findBusTreeDTO(SpecialQrcodeDTO specialQrcode) throws Exception;
	 List<SpecialTreeDTO> findGoodsTreeDTO(SpecialQrcodeDTO specialQrcode) throws Exception;
	 
	 SpecialQrcode findByFeeCode(SpecialQrcode specialQrcode) throws Exception;
}
