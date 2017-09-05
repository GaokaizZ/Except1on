package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.AdGoods;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.service.IAdGoodsService;
import org.springrain.sinova.service.IGoodsService;
import org.springrain.system.service.BaseSpringrainServiceImpl;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-06-25 10:55:22
 * @see org.springrain.sinova.service.impl.AdGoods
 */
@Service("adGoodsService")
public class AdGoodsServiceImpl extends BaseSpringrainServiceImpl implements IAdGoodsService {

	@Resource
	private IGoodsService goodsService;
	
    @Override
	public String  save(Object entity ) throws Exception{
		AdGoods adGoods=(AdGoods) entity;
		return super.save(adGoods).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	AdGoods adGoods=(AdGoods) entity;
		return super.saveorupdate(adGoods).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		AdGoods adGoods=(AdGoods) entity;
		return super.update(adGoods);
    }

    @Override
	public AdGoods findAdGoodsById(Object id) throws Exception{
		return super.findById(id,AdGoods.class);
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object o) throws Exception{
		return super.findListDataByFinder(finder,page,clazz,o);
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o) throws Exception {
		 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
	}

	@Override
	public boolean saveAdGoods(String userId, List<String> goodsIds) throws Exception {
		logger.info("操作绑定关系");
		Finder finder = Finder.getDeleteFinder(AdGoods.class);
		finder.append("where 1=1 ");
		finder.append("and owner=:owner");
		finder.setParam("owner", userId);
		this.update(finder);
		
		for (String goodsId: goodsIds) {
			AdGoods ad = new AdGoods();
			ad.setOwner(userId);
			ad.setGoodsId(goodsId);
			ad.setState("1");
			this.save(ad);
		}
		return true;
	}


	@Override
	public List<Goods> findGoodsByUserId(String id) throws Exception {
		Finder finder = new Finder();
		finder.append("select t1.*  from t_goods t1,t_ad_goods t2 where  t1.fee_code = t2.goods_id and t2.state = '1' ");
		if(StringUtils.isNotBlank(id)){
			finder.append(" and t2.owner =:owner");
			finder.setParam("owner",id);
		}
		finder.setOrderSql(" order by t1.id asc ");
		Page p = new Page();
		p.setOrder( "t1.id");
		return goodsService.queryForList(finder, Goods.class,p);
	}

	@Override
	public boolean deleteAdGoods(String userId,String busIds,String goodsIds,String feeCode) {
		try {
			Finder finder = Finder.getDeleteFinder(AdGoods.class);
			finder.append("where 1=1 ");
			
			if(StringUtils.isNotBlank(userId)){
				finder.append("and owner=:owner ");
				finder.setParam("owner", userId);
			}
			if(StringUtils.isNotBlank(busIds)){
				String[] rs = busIds.split(",");
				List<String> css = Arrays.asList(rs);
				String ids = "";
				for(int i=0;i<css.size();i++){
					ids = "'"+css.get(i)+"',";
				}
				ids = ids.substring(0, ids.length()-1);
				finder.append("and goods_id in (select fee_code from t_goods where id in ("+ids+") ) ");
				
			}
			if(StringUtils.isNotBlank(goodsIds)){
				String[] rs = goodsIds.split(",");
				List<String> css = Arrays.asList(rs);
				String ids = "";
				for(int i=0;i<css.size();i++){
					ids = "'"+css.get(i)+"',";
				}
				ids = ids.substring(0, ids.length()-1);
				finder.append(" and goods_id in  (select t2.fee_code  from t_business t1,t_goods t2  where t1.id = t2.bus_id and t1.id in ("+ids+"));");
			}
			if(StringUtils.isNotBlank(feeCode)){
				finder.append("and goods_id=:feeCode");
				finder.setParam("feeCode", feeCode);
			}
			this.update(finder);
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
