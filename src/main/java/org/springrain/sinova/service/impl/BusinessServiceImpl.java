package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder; 
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.FrontListDTO;
import org.springrain.sinova.dto.TreeBusinessGoodsDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.sinova.service.IAdGoodsService;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IGoodsService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.system.entity.Role;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.util.Constant;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:13
 * @see org.springrain.sinova.service.impl.Business
 */
@Service("businessService")
public class BusinessServiceImpl extends BaseSpringrainServiceImpl implements IBusinessService {

	private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
	@Resource
	private IGoodsService goodsService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IAdGoodsService adGoodsService;
	
    @Override
	public String  save(Object entity ) throws Exception{
		Business business=(Business) entity;
		return super.save(business).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	Business business=(Business) entity;
		return super.saveorupdate(business).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		Business business=(Business) entity;
		return super.update(business);
    }
	
    @Override
	public Business findBusinessById(Object id) throws Exception{
		return super.findById(id,Business.class);
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

	/**
     * 根据userId查找对应的业务
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
	public List<Business> findBusinessByUserId(UserBusiness userBusiness, Business business, Page page ) throws Exception {
        if (StringUtils.isBlank(userBusiness.getUserId())) {
            return null;
        }
        Finder	finder = new Finder("select ub.id as id, b.id as busId, b.bus_code as busCode, b.up_down as upDown, b.bus_name as busName  ");
		finder.append(" from t_user_business ub, t_business b where ub.bus_id = b.id and b.state='1' ");
		finder.append(" and ub.user_id = :userId ");
		
		finder.setParam("userId", userBusiness.getUserId());
		
		if(StringUtils.isNotBlank(business.getBusName())&& !"undefined".equals(business.getBusName())){
			finder.append(" and b.bus_name like :busName");
			finder.setParam("busName", "%"+business.getBusName()+"%");
		}
		if(StringUtils.isNotBlank(business.getBusCode())&& !"undefined".equals(business.getBusCode())){
			finder.append(" and b.bus_code like :busCode");
			finder.setParam("busCode", "%"+business.getBusCode()+"%");
		}
        
        return super.queryForList(finder, Business.class, page);
    }
    
    /* 
     * 查找用户未绑定的业务
     */
    @Override
	public List<Business> findBusNot(UserBusiness userBusiness,String userIds, Page page, Business business) throws Exception {
    	if (StringUtils.isBlank(userBusiness.getUserId())) {
            return null;
        }
    	
    	Finder	finder = new Finder(" select b.* from t_business b, t_region_business rb, t_region r, t_user_office uo  ");
		finder.append(" where  r.region_code= uo.region_code and r.id=rb.region_id and rb.bus_id = b.id and b.up_down='1' and b.state='1' ");
		finder.append(" and uo.user_id=:userId ");
		
		if(StringUtils.isNotBlank(business.getBusCode())){
			finder.append(" and b.bus_code like :busCode").setParam("busCode", "%"+business.getBusCode()+"%");
		}
		if(StringUtils.isNotBlank(business.getBusName())){
			finder.append(" and b.bus_name like :busName").setParam("busName", "%"+business.getBusName()+"%");
		}
		
		if(userBusiness != null){
			finder.append(" and b.id not in (select ub.bus_id from t_user_business ub where  ub.user_id=:userIds ) ");
			finder.setParam("userIds", userIds);
		}
		finder.append(" order by b.id ");
		finder.setParam("userId", userBusiness.getUserId());
		
    	return super.queryForList(finder, Business.class, page);
	}

    
    /**
     * 根据regionId查找对应的业务
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
	public List<Business> findBusinessByRegionId(String regionId, String busName, String busCode, Page page ) throws Exception {
        if (StringUtils.isBlank(regionId)) {
            return null;
        }
        Finder	finder = new Finder("select ub.id as id, b.id as busId, b.bus_code as busCode, b.up_down as upDown, b.bus_name as busName  ");
		finder.append(" from t_region_business ub, t_business b where ub.bus_id = b.id and b.state='1' ");
		finder.append(" and ub.region_id = :regionId ");
		
		finder.setParam("regionId", regionId);
		
		if(StringUtils.isNotBlank(busName)&& !"undefined".equals(busName)){
			finder.append(" and b.bus_name like :busName");
			finder.setParam("busName", "%"+busName+"%");
		}
		if(StringUtils.isNotBlank(busCode)&& !"undefined".equals(busCode)){
			finder.append(" and b.bus_code like :busCode");
			finder.setParam("busCode", "%"+busCode+"%");
		}
        
        return super.queryForList(finder, Business.class, page);
    }
    
    /* 
     * 查找地市未绑定的业务
     */
    @Override
	public List<Business> findBusRegion(String regionId, Page page) throws Exception {
    	if (StringUtils.isBlank(regionId)) {
            return null;
        }
    	Finder	finder = new Finder("select distinct b.id as id, b.id as busId, b.bus_code as busCode, b.up_down as upDown, b.bus_name as busName  ");
		finder.append(" from  t_business b where  b.up_down='1' and b.state='1' ");
		finder.append(" and  b.id not in (select ub.bus_id as busId from t_region_business ub where ub.region_id = :regionId) ");
		finder.setParam("regionId", regionId);
		
    	return super.queryForList(finder, Business.class, page);
	}
    
	@Override
	public FrontListDTO getFrontListDTOByBusCodeAndWorknoAndFeeCode(String busCode,String workno,String feeCode) throws Exception {
		Finder finder = new Finder();
		finder.append("select b.id as busId,b.bus_code as busCode,b.bus_name as busName,g.id as goodsId,g.goods_name as goodsName,b.state as busState,b.up_down as busUpDown,g.state as goodsState,g.up_down as goodsUpDown,g.fee_code as goodsFeeCode,b.bus_type as busType,g.second_category as secondCategory ")
			  .append("from t_business b,t_goods g,t_user u,t_user_business ub ")
			  .append("where b.id = g.bus_id and b.id = ub.bus_id and ub.user_id = u.id ")
			  .append("and b.bus_code = :busCode ")
			  .append("and u.workno = :workno ")
			  .append("and g.fee_code = :feeCode");
		logger.info("查询商品对象sql:{}", finder.getSql());
		
		finder.setParam("busCode", busCode);
		finder.setParam("workno",workno);
		finder.setParam("feeCode",feeCode);
		
		return super.queryForObject(finder,FrontListDTO.class);
	}
	
	/* 全业务二维码查询列表
	 * 
	 */
	@Override
	public FrontListDTO getFrontListDTOByQyw(String busCode, String workno, String feeCode) throws Exception {
		Finder finder = new Finder();
		finder.append("select b.id as busId,b.bus_code as busCode,b.bus_name as busName,g.id as goodsId,g.goods_name as goodsName,b.state as busState,b.up_down as busUpDown,g.state as goodsState,g.up_down as goodsUpDown,g.fee_code as goodsFeeCode,b.bus_type as busType,g.second_category as secondCategory ");
		finder.append("from t_business b,t_goods g ")
		.append(" where b.id = g.bus_id ")
		.append(" and b.bus_code = :busCode ")
		.append(" and g.fee_code = :feeCode ");
		logger.info("全业务查询商品对象sql:{}", finder.getSql());
		
		finder.setParam("busCode", busCode);
		finder.setParam("feeCode", feeCode);
		return super.queryForObject(finder,FrontListDTO.class);
	}

	
	@Override
	public void deleteBusMore(Object ids[] ) throws Exception{
		for (int i = 0; i < ids.length; i++) {
			logger.info("ids[i]==="+ids[i]);
			Business bus=findById(ids[i], Business.class);
			if(null!=bus){
				Goods good=new Goods();
				good.setBusId(null!=ids[i]?ids[i].toString():"");
				List<Goods> list=goodsService.findGoodsByGood(good);
				for (int j = 0; j < list.size(); j++) {
					Goods goodTemp=list.get(j);
					goodTemp.setState(Constant.ZERO);
					goodTemp.setUpDown(Constant.ZERO);
					goodsService.update(goodTemp);
				}
				bus.setState(Constant.ZERO);
				bus.setUpDown(Constant.ZERO);
				update(bus);
			}
		}
    }
	
	@Override
	public void upDown(Object id,Object upDown) throws Exception{
		String ids=id.toString();
		String[] rs = ids.split(",");
		for (int i = 0; i < rs.length; i++) {
			String idCurr=rs[i];
			Business bus=findById(idCurr, Business.class);//根据id获取业务对象
			//在这里记录一下操作日志
			
			bus.setUpDown(null!=upDown?upDown.toString():"");
			if(Constant.ZERO.equals(upDown)){
				Goods goodTemp=new Goods();
				goodTemp.setBusId(null!=idCurr?idCurr.toString():"");
				List<Goods> goodList=goodsService.findGoodsByGood(goodTemp);
				if(null!=goodList && !goodList.isEmpty()){
					 for (int j = 0; j < goodList.size(); j++) {
						Goods goods=goodList.get(j);
						goods.setUpDown(null!=upDown?upDown.toString():"");
						goodsService.saveorupdate(goods);
						logger.info("==GoodsUpDown=="+goods.getGoodsName());
					}
				}
			}
			update(bus);
		}
		//下架 清空微信推荐
		if(!StringUtils.equals("1",upDown.toString())){
			adGoodsService.deleteAdGoods(null, id.toString(), null, null);
		}
	}

	/**
     * 根据userId，查找对应的业务以及产品
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<TreeBusinessGoodsDTO> findTreeByUserId(String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            logger.error("根据用户查询对应的业务以及商品的userId为null");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" select b.bus_code   as businessCode,b.bus_name   as businessName,g.fee_code   as goodsCode,g.goods_name as goodsName ");
        sb.append(" from t_business b, t_goods g, t_user_business ub where 1 = 1 and b.id = g.bus_id and ub.bus_id = b.id");
        sb.append(" and b.state = :state and b.up_down = :updown  and g.state= :state1 and g.up_down = :updown1 ");
        sb.append(" and ub.user_id =:userId and b.bus_type <> 'wapgouji' order by b.bus_code desc ");

        Finder finder = new Finder(sb);
        finder.setParam("state", Constant.ONE);
        finder.setParam("updown", Constant.ONE);
        finder.setParam("state1", Constant.ONE);
        finder.setParam("updown1", Constant.ONE);
        finder.setParam("userId", userId);

        return super.queryForList(finder, TreeBusinessGoodsDTO.class);
    }
    /**
     * 根据地市编码，查找对应的业务以及产品
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<TreeBusinessGoodsDTO> findTreeByRegion(String regionCode) throws Exception {
        if (StringUtils.isBlank(regionCode)) {
            logger.error("*******findTreeByRegion************regionCode=null**");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" select b.bus_code   as businessCode,b.bus_name   as businessName,g.fee_code   as goodsCode,g.goods_name as goodsName ");
        sb.append(" from t_business b, t_goods g, t_region_business ub, t_region r where b.id = g.bus_id and ub.bus_id = b.id ");
        sb.append(" and b.state = :state and b.up_down = :updown  and g.state= :state1 and g.up_down = :updown1 ");
        sb.append(" and ub.region_id = r.id and r.region_code=:regionCode order by b.bus_code desc ");

        Finder finder = new Finder(sb);
        finder.setParam("state", Constant.ONE);
        finder.setParam("updown", Constant.ONE);
        finder.setParam("state1", Constant.ONE);
        finder.setParam("updown1", Constant.ONE);
        finder.setParam("regionCode", regionCode);

        return super.queryForList(finder, TreeBusinessGoodsDTO.class);
    }
	@Override
	public List<Business> findByBus(Business bus) throws Exception{
		Finder finder = new Finder("SELECT r.* from ").append(Finder.getTableName(Business.class)).append(" r where 1=1 ");
		
		if (StringUtils.isNotBlank(bus.getState())) {
			finder.append(" and r.STATE=:state ");
			finder.setParam("state", bus.getState());
		}
		
		if (StringUtils.isNotBlank(bus.getBusCode())) {
			finder.append(" and r.BUS_CODE=:busCode ");
			finder.setParam("busCode", bus.getBusCode());
		}
		if(StringUtils.isNotBlank(bus.getId())){
			finder.append(" and r.ID not in (:id) ");
			finder.setParam("id", bus.getId());
		}
		if(StringUtils.isNotBlank(bus.getBusName())){
			finder.append(" and r.bus_name = :busName").setParam("busName", bus.getBusName());
		}
		if(StringUtils.isNotBlank(bus.getBusType())){
			finder.append(" and r.bus_type = :busType").setParam("busType", bus.getBusType());
		}
		if(StringUtils.isNotBlank(bus.getUpDown())){
			finder.append(" and r.up_down = :upDown").setParam("upDown", bus.getUpDown());
		}
		finder.append(" order by r.id ");
		return super.queryForList(finder, Business.class);
	}
	
	@Override
    public List<Business> findListDataByIds(String ids) throws Exception{
		Finder finder = new Finder("SELECT r.* from ").append(Finder.getTableName(Business.class)).append(" r where r.state= :state ");
		finder.setParam("state", Constant.ONE);
		if (StringUtils.isNotBlank(ids)) {
			finder.append(" and r.id in (:ids) ");
			finder.setParam("ids", ids);
		}
		finder.append(" order by r.sort");
		
		return super.queryForList(finder,Business.class);
	}

	
	@Override
	public List<Business> findAllBus() throws Exception {
		Finder finder = new Finder();
		finder.append(" select b.* from ").append(Finder.getTableName(Business.class));
		finder.append(" b , t_region_business rb");
		finder.append(" where b.id = rb.bus_id and b.state = :state");
		finder.setParam("state", Constant.ONE);
		finder.append(" and b.up_down = :upDown ");
		finder.setParam("upDown", Constant.ONE);
		finder.append(" order by b.bus_code desc ");
		return super.queryForList(finder, Business.class);
	}

	@Override
	public List<Goods> findGoodsByBus() throws Exception {
		Finder finder = new Finder();
		finder.append(" select g.* from ").append(Finder.getTableName(Goods.class)).append(" g where g.state =:state ").setParam("state", Constant.ONE);
		finder.append(" and g.up_down = :upDown ").setParam("upDown", Constant.ONE);
		finder.append(" and g.bus_id in ( select id from t_business where state ='1' and up_down ='1') order by g.fee_code ");
		return super.queryForList(finder, Goods.class);
	}

	@Override
	public FrontListDTO getFrontListDTOByBusCodeAndWorknoAndFeeCodeAndBusType(
			String busCode, String workno, String feeCode, String busType) throws Exception {
		Finder finder = new Finder();
		finder.append("select b.id as busId,b.bus_code as busCode,b.bus_name as busName,g.id as goodsId,g.goods_name as goodsName,b.state as busState,b.up_down as busUpDown,g.state as goodsState,g.up_down as goodsUpDown,g.fee_code as goodsFeeCode,b.bus_type as busType,g.second_category as secondCategory ")
			  .append("from t_business b,t_goods g,t_user u,t_user_business ub ")
			  .append("where b.id = g.bus_id and b.id = ub.bus_id and ub.user_id = u.id ")
			  .append("and b.bus_code = :busCode ")
			  .append("and u.workno = :workno ")
			  .append("and g.fee_code = :feeCode");
		logger.info("查询商品对象sql:{}", finder.getSql());
		
		finder.setParam("busCode", busCode);
		finder.setParam("workno",workno);
		finder.setParam("feeCode",feeCode);
		String bus_type = "200";
		if("4g".equals(busType)) {
			finder.append(" and b.bus_type != :busType ").setParam("busType", bus_type);
		} else {
			finder.append(" and b.bus_type = :busType ").setParam("busType", busType);
		}
		return super.queryForObject(finder,FrontListDTO.class);
	}

	@Override
	public List<FrontListDTO> getFrontListDTOByWorkNo(String workno, String busType)
			throws Exception {
		Finder finder = new Finder();
		finder.append("select b.id as busId,b.bus_code as busCode,b.bus_name as busName,g.id as goodsId,g.goods_name as goodsName,b.state as busState,b.up_down as busUpDown,g.state as goodsState,g.up_down as goodsUpDown,g.fee_code as goodsFeeCode,b.bus_type as busType,g.second_category as secondCategory ")
			  .append("from t_business b,t_goods g,t_user u,t_user_business ub ")
			  .append("where b.id = g.bus_id and b.id = ub.bus_id and ub.user_id = u.id ")
			  .append("and u.workno = :workno");
		logger.info("查询商品对象sql:{}", finder.getSql());
		
		finder.setParam("workno",workno);
		String bus_type = "200";
		if (null!=busType && "".equals(busType)) {
			if("4g".equals(busType)) {
				finder.append(" and b.bus_type != :busType ").setParam("busType", bus_type);
			} else {
				finder.append(" and b.bus_type = :busType ").setParam("busType", busType);
			}
		}
		finder.append(" order by b.id");
		return super.queryForList(finder,FrontListDTO.class);
	}

	//业务展示实现
	@Override
	public  List<Business> queryForBusinessList(HttpServletRequest request, Class<Business> clazz,
			Page page) throws Exception {
		UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
		Role role = SessionUser.getShiroUser().getRole();
		//查询条件
		String busCode = request.getParameter("busCode");
		String busName = request.getParameter("busName");
		String upDown = request.getParameter("upDown");
		String busType = request.getParameter("busType");
		Finder finder = Finder.getSelectFinder(Business.class);
		finder.append("where 1 = 1 ");
		
		if(StringUtils.isNotBlank(busCode)){
			finder.append(" and bus_code like :busCode").setParam("busCode", "%"+busCode+"%");
		}
		if(StringUtils.isNotBlank(busName)){
			finder.append(" and bus_name like :busName").setParam("busName", "%"+busName+"%");
		}
		
		if(StringUtils.isNotBlank(upDown)){
			finder.append(" and up_down = :upDown").setParam("upDown", upDown);
		}
		
		if ("ROLE_SUPERMAN".equals(role.getCode())){//登录上来的是超级管理员
			
			//查询地市列表
			Region region = new Region();
			List<Region> datar = regionService.findListDataByFinder(null, null,
					Region.class, region);
			
			Region region2 = new Region();
			region2.setRegionCode("100");
			region2.setRegionName("全省");
			datar.add(region2);
			
			request.setAttribute("datar", datar);
			
			
			
			if(StringUtils.isNotBlank(busType)){
				finder.append(" and bus_type = :busType").setParam("busType", busType);
				logger.info("查询的类型是"+busType);
				if("200".equals(busType)){//说明查询的是营销活动
					String searchCity = request.getParameter("searchCity");
					request.setAttribute("searchCity", searchCity);
					logger.info("查询的地市是"+searchCity);
					if(StringUtils.isNotBlank(searchCity)){
						if(!"choose".equals(searchCity)){
							finder.append(" and group_id = :searchCity").setParam("searchCity",searchCity);
						}
					}
				}
			}
		}else {// 如果登录上来的是地市管理员
			
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
			
			if(StringUtils.equals(Constant.ROLE_REGION_MANAGER, role.getCode())){
				finder.append(" and bus_type = :busType").setParam("busType", "200");
				String searchCity = request.getParameter("searchCity");
				if(StringUtils.isNotBlank(searchCity)){
					//输入条件查询的地市
					request.setAttribute("searchCity", searchCity);
					if(!"choose".equals(searchCity)){
						finder.append(" and group_id = :searchCity").setParam("searchCity",searchCity);
					}else {
						finder.append(" and (  group_id = '100'");
						finder.append(" or group_id = '"+userOfficeDTO.getRegionCode()+"' )");
						
					}
				}
			}
		}
		
		finder.append(" and state = :state").setParam("state", "1");
		finder.append(" order by bus_name");
		
		
		
		
		return  super.queryForList(finder, Business.class, page);
	}

	//业务表查询重构
	@Override
	public List<Business> queryForList(HttpServletRequest request, Model model,
			Goods goods) throws Exception {
		UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
		Role role = SessionUser.getShiroUser().getRole();
		
		Finder finder = new Finder();
		finder.append("select r.* from ").append(Finder.getTableName(Business.class)).append(" r where r.state= :state and r.up_down='1' ");
		finder.setParam("state", Constant.ONE);
		//如果登录上来的是超级管理员
		if(StringUtils.equals("admin", role.getId())){
			String busType = request.getParameter("busType"); 
			if(StringUtils.isNotBlank(busType)){
				request.setAttribute("busType", busType);
				finder.append(" and r.bus_type = :busType").setParam("busType", busType);
				if("200".equals(busType)){
					String searchCity = request.getParameter("searchCity");
					if(StringUtils.isNotBlank(searchCity)){
						request.setAttribute("searchCity",searchCity);
						finder.append(" and r.group_id = :searchCity").setParam("searchCity", searchCity);
					}
				}
			}
		}
		// 如果登录上来的是地市管理员
		if(StringUtils.equals(Constant.ROLE_REGION_MANAGER, role.getCode())){
			finder.append(" and r.bus_type = :busType").setParam("busType", "200");
			String searchCity = request.getParameter("searchCity");
			if(StringUtils.isNotBlank(searchCity)){
				request.setAttribute("searchCity",searchCity);
				finder.append(" and r.group_id = :searchCity").setParam("searchCity", searchCity);
			}else{
				finder.append(" and (  r.group_id = '100'");
				finder.append(" or r.group_id = '"+userOfficeDTO.getRegionCode()+"' )");
			}
			request.setAttribute("regionCode", userOfficeDTO.getRegionCode());
		}
		finder.append(" order by r.sort");
		return  super.queryForList(finder, Business.class);
	}

	@Override
	public List<Business> searchBelongBusiness(HttpServletRequest request,Model model) throws Exception {
		Finder finder = new Finder();
		finder.append("select r.* from ").append(Finder.getTableName(Business.class)).append(" r where r.state= :state and r.up_down='1' ");
		finder.setParam("state", Constant.ONE);
		String busType = request.getParameter("busType"); 
		if(StringUtils.isNotBlank(busType)){
			finder.append(" and r.bus_type = :busType").setParam("busType", busType);
		}
		String searchCity = request.getParameter("searchCity");
		if(StringUtils.isNotBlank(searchCity)){
			finder.append(" and r.group_id = :searchCity").setParam("searchCity", searchCity);
		}
		
		finder.append(" order by r.sort");
		return  super.queryForList(finder, Business.class);
	}

}
