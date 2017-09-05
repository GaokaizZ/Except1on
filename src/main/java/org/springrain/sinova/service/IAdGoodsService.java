package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.AdGoods;
import org.springrain.sinova.entity.Goods;
import org.springrain.system.service.IBaseSpringrainService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-06-25 10:55:22
 * @see org.springrain.sinova.service.AdGoods
 */
public interface IAdGoodsService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AdGoods findAdGoodsById(Object id) throws Exception;
	
	/**
	 * 绑定商品
	 * @param userId
	 * @param goodsIds
	 * @return
	 * @throws Exception
	 */
	boolean saveAdGoods(String userId,List<String> goodsIds) throws Exception;
	
	boolean deleteAdGoods(String userId,String busIds,String goodsIds,String feeCode) ;
	
	/**
	 * 查询当前用户绑定的推荐商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<Goods> findGoodsByUserId(String id) throws Exception;

}
