package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.util.Constant;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;

import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.service.IAdGoodsService;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IGoodsService;
import org.springrain.sinova.service.IRegionService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:24
 * @see org.springrain.sinova.service.impl.Goods
 */
@Service("goodsService") 
public class GoodsServiceImpl extends BaseSpringrainServiceImpl implements IGoodsService {
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);
	
	@Resource
	private IBusinessService businessService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IAdGoodsService adGoodsService;

    @Override
	public String  save(Object entity ) throws Exception{
		Goods goods=(Goods) entity;
		return super.save(goods).toString();
	}
    
    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	Goods goods=(Goods) entity;
		return super.saveorupdate(goods).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		Goods goods=(Goods) entity;
		return super.update(goods);
    }

    @Override
	public Goods findGoodsById(Object id) throws Exception{
		return super.findById(id,Goods.class);
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
	public List<Goods> findGoodsByGood(Goods good) throws Exception {
		if (StringUtils.isBlank(good.getBusId())) {
			return null;
		}
		Finder finder = new Finder("SELECT r.* from ").append(Finder.getTableName(Goods.class)).append("  r where r.BUS_ID=:busId and r.state=:state order by r.id");
		finder.setParam("busId", good.getBusId());
		finder.setParam("state", Constant.ONE);
		return super.queryForList(finder, Goods.class);
	}
	
	@Override
	public void deleteBusMore(Object ids[] ) throws Exception{
		for (int i = 0; i < ids.length; i++) {
			logger.info("ids[i]==="+ids[i]);
			Goods good=findById(ids[i], Goods.class);
			if(null!=good){
				good.setState(Constant.ZERO);
				good.setUpDown(Constant.ZERO);
				update(good);
			}
		}
    }
	
	@Override
	public String upDown(Object id,Object upDown) throws Exception{
		String ids=id.toString();
		String failGoods="上架失败产品：";
		String succGoods="</br>上架成功产品：";
		String[] rs = ids.split(",");
		for (int i = 0; i < rs.length; i++) {
			String idCurr=rs[i];
			Goods good=findById(idCurr, Goods.class);//根据id获取业务对象
			if(Constant.ONE.equals(upDown)){
				Business bus=businessService.findBusinessById(null!=good.getBusId()?good.getBusId():"");
				if(Constant.ONE.equals(bus.getUpDown()) && Constant.ONE.equals(bus.getState()) ){
					good.setUpDown(null!=upDown?upDown.toString():"");
					succGoods+=good.getGoodsName()+",";
				}else{
					failGoods+=good.getGoodsName()+",";
				}
			}else if(Constant.ZERO.equals(upDown)){
				good.setUpDown(null!=upDown?upDown.toString():"");
			}
			update(good);
		}
		failGoods=failGoods.substring(0, failGoods.length()-1);
		succGoods=succGoods.substring(0, succGoods.length()-1);
		
		//下架 清空微信推荐
		if(!StringUtils.equals("1",upDown.toString())){
			adGoodsService.deleteAdGoods(null,null, id.toString(), null);
		}
		
		return failGoods+succGoods;
	}
	@Override
	public List<Goods> findByGood(Goods good) throws Exception{
		Finder finder = new Finder("SELECT * from ").append(Finder.getTableName(Goods.class)).append("  where state=:state ");
		finder.setParam("state", Constant.ONE);
		if (StringUtils.isNotBlank(good.getGoodsName())) {
			finder.append(" and goods_name=:goodsName ");
			finder.setParam("goodsName", good.getGoodsName());
		}
		if (StringUtils.isNotBlank(good.getFeeCode())) {
			finder.append(" and FEE_CODE=:feeCode ");
			finder.setParam("feeCode", good.getFeeCode());
		}
		if(StringUtils.isNotBlank(good.getId())){
			finder.append(" and ID not in (:id) ");
			finder.setParam("id", good.getId());
		}
		if(StringUtils.isNotBlank(good.getBusId())){
			finder.append(" and BUS_ID =:busId ");
			finder.setParam("busId", good.getBusId());
		}
		if(StringUtils.isNotBlank(good.getUpDown())){
			finder.append(" and UP_DOWN =:updown ");
			finder.setParam("updown", good.getUpDown());
		}
		finder.append(" order by id ");
		return super.queryForList(finder, Goods.class);
	}

	//自己实现的查询
	public  List<Goods> queryForList(HttpServletRequest request,Goods goods, Page page) throws Exception {
		UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
		Role role = SessionUser.getShiroUser().getRole();
		
		Finder finder = new Finder();
		finder.append("select g.id as id,g.goods_Name as goodsName,g.fee_Code as feeCode,g.BUS_ID as busId, ");
		finder.append("g.FEE_STANDARD as feeStandard,g.EFFECT_WAY as effectWay,g.transact_Way as transactWay, ");
		finder.append("g.up_Down as upDown ,b.group_Id as detailDesc from t_goods g,t_business b ");
		finder.append("where b.id = g.bus_id and g.state='1' and b.state='1' and b.up_down='1' ");
		
		
		//如果登录上来的是超级管理员
		if(StringUtils.equals("admin", role.getId())){
			
			//查询地市列表
			Region region = new Region();
			List<Region> datar = regionService.findListDataByFinder(null, null,
					Region.class, region);
			
			Region region2 = new Region();
			region2.setRegionCode("100");
			region2.setRegionName("全省");
			datar.add(region2);
			
			request.setAttribute("datar", datar);
			
			
			String busType = request.getParameter("busType"); 
			if(StringUtils.isNotBlank(busType)){
				request.setAttribute("busType", busType);
				finder.append(" and b.bus_type = :busType").setParam("busType", busType);
				if("200".equals(busType)){
					String searchCity = request.getParameter("searchCity");
					if(StringUtils.isNotBlank(searchCity)){
						request.setAttribute("searchCity",searchCity);
						finder.append(" and b.group_id = :searchCity").setParam("searchCity", searchCity);
					}
				}
			}
		}
		
		// 如果登录上来的是地市管理员
		if(StringUtils.equals(Constant.ROLE_REGION_MANAGER, role.getCode())){
			//查询地市列表
			Region region = new Region();
			List<Region> tempList = regionService.findListDataByFinder(null, null,
					Region.class, region);
			List<Region> reallyList = new ArrayList<Region>();
			for (Region regionString : tempList) {
				if(userOfficeDTO.getRegionCode().equals(regionString.getRegionCode())){
					Region temp = new Region();
					temp.setRegionCode(regionString.getRegionCode());
					temp.setRegionName(regionString.getRegionName());
					reallyList.add(temp);
				}
			}
			
			Region region2 = new Region();
			region2.setRegionCode("100");
			region2.setRegionName("全省");
			reallyList.add(region2);
			
			request.setAttribute("datar", reallyList);
			
			//该地市管理员所属的地市编码
			request.setAttribute("regionCode", userOfficeDTO.getRegionCode());
			
			request.setAttribute("busType", "200");
			
			
			finder.append(" and b.bus_type = :busType").setParam("busType", "200");
			
			String searchCity = request.getParameter("searchCity");
			if(StringUtils.isNotBlank(searchCity)){
				request.setAttribute("searchCity",searchCity);
				finder.append(" and b.group_id = :searchCity").setParam("searchCity", searchCity);
			}else{
				finder.append(" and (  b.group_id = '100'");
				finder.append(" or b.group_id = '"+userOfficeDTO.getRegionCode()+"' )");
			}
		}
		
		if(StringUtils.isNotBlank(goods.getGoodsName())){
			finder.append(" and g.goods_name like :goodsName ");
			finder.setParam("goodsName", "%"+goods.getGoodsName()+"%");
		}
		if(StringUtils.isNotBlank(goods.getFeeCode())){
			finder.append(" and g.fee_code like :feeCode ");
			finder.setParam("feeCode", "%"+goods.getFeeCode()+"%");
		}
		if(StringUtils.isNotBlank(goods.getBusId())){
			finder.append(" and g.bus_id =:busId ");
			finder.setParam("busId", goods.getBusId());
		}
		if(StringUtils.isNotBlank(goods.getUpDown())){
			finder.append(" and g.up_down =:upDown ");
			finder.setParam("upDown", goods.getUpDown());
		}
		finder.append(" order by g.goods_name ,g.id ");
		return super.queryForList(finder, Goods.class,page);
	}
}
