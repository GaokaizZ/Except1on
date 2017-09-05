package org.springrain.sinova.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.service.IBaseSpringrainService;

import org.springrain.sinova.entity.Goods;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:24
 * @see org.springrain.sinova.service.Goods
 */
public interface IGoodsService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	
	public List<Goods> queryForList(HttpServletRequest request,Goods goods, Page page) throws Exception;
	
	
	Goods findGoodsById(Object id) throws Exception;

    List<Goods> findGoodsByGood(Goods good) throws Exception;
    
    void deleteBusMore(Object ids[] ) throws Exception;
    
    String upDown(Object id,Object upDown) throws Exception;
    
    List<Goods> findByGood(Goods good) throws Exception;

}
