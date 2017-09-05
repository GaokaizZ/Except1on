package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.GoodsAct;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.system.service.IBaseSpringrainService;

public interface IGoodsActService extends IBaseSpringrainService{

	Business findBusbyGoodsId(String goodsId) throws Exception;
	
	List<Goods> findGoodsByGroupId(String goodsId, String groupId, String goodsName, String feeCode, String busName, String relation, Page page) throws Exception;
	
	List<Business> findBus() throws Exception;
	
	List<GoodsAct> findGoodsAct(String goodsId, String groupId) throws Exception;
	
	List<Goods> findGoodsById(String goodsId, String regionCode, String mobile)throws Exception;
	
	UserOffice findOwnByWorkno(String workno) throws Exception;
}
