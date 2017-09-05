package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.GoodsAct;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.IGoodsActService;
import org.springrain.system.service.BaseSpringrainServiceImpl;

@Service("goodsActService")
public class GoodsActServiceImpl extends BaseSpringrainServiceImpl implements IGoodsActService{

	private static final Logger logger = LoggerFactory.getLogger(GoodsActServiceImpl.class);
	
	@Override
	public String  save(Object entity ) throws Exception{
		GoodsAct goodsAct=(GoodsAct) entity;
		return super.save(goodsAct).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	GoodsAct goodsAct=(GoodsAct) entity;
		return super.saveorupdate(goodsAct).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		GoodsAct goodsAct=(GoodsAct) entity;
		return super.update(goodsAct);
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

	/* 
	 * 根据商品id查询对应业务
	 */
	@Override
	public Business findBusbyGoodsId(String goodsId) throws Exception {
		logger.info("*************商品关联***findBusbyGoodsId***goodsId=="+goodsId);
		if(StringUtils.isBlank(goodsId)){
			return null;
		}
		Finder finder = new Finder();
		finder.append(" select b.group_id from ").append(Finder.getTableName(Business.class)).append(" b, ");
		finder.append(Finder.getTableName(Goods.class)).append(" g ");
		finder.append(" where g.bus_id = b.id and g.id =:goodsId ");
		finder.setParam("goodsId", goodsId);
		return queryForObject(finder, Business.class);
	}

	/* 
	 * 根据业务groupId查找对应商品
	 */
	@Override
	public List<Goods> findGoodsByGroupId(String goodsId,String groupId, String goodsName, String feeCode, String busName, String relation, Page page) throws Exception {
		logger.info("*************商品关联***findGoodsByGroupId***groupId=="+groupId);
		if(StringUtils.isBlank(groupId)){
			return null;
		}
		String str = "'"+groupId+"'"+",'100'";
		Finder finder = new Finder();
		finder.append(" select g.* from ").append(Finder.getTableName(Goods.class)).append(" g, ");
		finder.append(Finder.getTableName(Business.class)).append(" b ");
		finder.append(" where g.bus_id = b.id and  b.bus_type = '200' and g.up_down='1' and g.state='1' and b.up_down='1' and b.state='1' ");
		finder.append(" and b.group_id in("+str +")  ");
		if(StringUtils.isNotBlank(goodsName)){
			finder.append(" and g.goods_name like :goodsName ");
			finder.setParam("goodsName", "%"+goodsName.trim()+"%");
		}
		if(StringUtils.isNotBlank(feeCode)){
			finder.append(" and g.fee_code like :feeCode ");
			finder.setParam("feeCode", "%"+feeCode.trim()+"%");
		}
		if(StringUtils.isNotBlank(busName)){
			finder.append(" and b.id =:busId ");
			finder.setParam("busId", busName);
		}
		logger.info("查询是否关联标示"+relation);
		if("1".equals(relation)){
			finder.append(" and  g.id  in ( select ga.bus_id from  T_GOODS_ACT ga where ga.group_id =:groupId and ga.goods_id=:goodsId) ");
			finder.setParam("groupId", groupId);
			finder.setParam("goodsId", goodsId);
		}
		if("0".equals(relation)){
			finder.append(" and  g.id not in ( select ga.bus_id from  T_GOODS_ACT ga where ga.group_id =:groupId and ga.goods_id=:goodsId ) ");
			finder.setParam("groupId", groupId);
			finder.setParam("goodsId", goodsId);
		}
		
		finder.append(" order by g.fee_code  ");
		return queryForList(finder, Goods.class, page);
	}

	@Override
	public List<Business> findBus() throws Exception {
		Finder finder = new Finder();
		finder.append(" select b.* from ").append(Finder.getTableName(Business.class)).append(" b ");
		finder.append(" where b.state='1' and b.up_down='1' and b.bus_type='200' ");
		return queryForList(finder, Business.class);
	}

	@Override
	public List<GoodsAct> findGoodsAct(String goodsId, String groupId) throws Exception {
		if(StringUtils.isBlank(groupId)){
			return null;
		}
		if(StringUtils.isBlank(goodsId)){
			return null;
		}
		Finder finder = new Finder();
		finder.append(" select ga.* from ").append(Finder.getTableName(GoodsAct.class)).append(" ga ");
		finder.append(" where ga.goods_id=:goodsId  and ga.group_id=:groupId ");
		finder.setParam("goodsId", goodsId);
		finder.setParam("groupId", groupId);
		return queryForList(finder, GoodsAct.class);
	}

	@Override
	public List<Goods> findGoodsById(String goodsId, String regionCode, String mobile) throws Exception {
		Finder finder = new Finder();
		finder.append(" select g.* from ").append(Finder.getTableName(Goods.class)).append(" g, ");
		finder.append(Finder.getTableName(GoodsAct.class)).append(" ga ");
		finder.append(" where g.id = ga.bus_id and g.up_down = '1' and g.state = '1' ");
		finder.append(" and g.fee_code not in(select fee_code from t_accept_record where mobile=:mobile )" );
		finder.append(" and ga.goods_id =:goodsId and ga.group_id=:regionCode order by g.fee_code ");
		finder.setParam("goodsId", goodsId);
		finder.setParam("regionCode", regionCode);
		finder.setParam("mobile", mobile);
		return queryForList(finder, Goods.class);
	}

	@Override
	public UserOffice findOwnByWorkno(String workno) throws Exception {
		Finder finder = new Finder();
		finder.append(" select uo.region_code as regionCode, uo.id as id from t_user u, t_user_office uo ");
		finder.append(" where u.id = uo.user_id and u.workno=:workno ");
		finder.setParam("workno", workno);
		return queryForObject(finder, UserOffice.class);
	}

	
}
