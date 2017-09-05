package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.util.Constant;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;

import org.springrain.sinova.dto.OfficeUserDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.RegionBusiness;
import org.springrain.sinova.service.IRegionBusinessService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:14:14
 * @see org.springrain.sinova.service.impl.RegionBusiness
 */
@Service("regionBusinessService")
public class RegionBusinessServiceImpl extends BaseSpringrainServiceImpl implements IRegionBusinessService {


    @Override
	public String  save(Object entity ) throws Exception{
		RegionBusiness regionBusiness=(RegionBusiness) entity;
		return super.save(regionBusiness).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	RegionBusiness regionBusiness=(RegionBusiness) entity;
		return super.saveorupdate(regionBusiness).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		RegionBusiness regionBusiness=(RegionBusiness) entity;
		return super.update(regionBusiness);
    }

    @Override
	public RegionBusiness findRegionBusinessById(Object id) throws Exception{
		return super.findById(id,RegionBusiness.class);
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
	public List<Business> findListDataByRegionBusiness(Finder finder, Page page, RegionBusiness regionBusiness,Business business) throws Exception {
		
		//regionBusiness.setRegionId("0edf5169e2074ebc8665b0094549dca2");
		
		if(finder == null){
			finder = new Finder();
			finder.append("SELECT ");
			finder.append("tb1.ID as id,tb1.BUS_CODE as busCode,tb1.BUS_NAME as busName,tb1.DEFAULT_REWARD as defaultReward, tb1.REMARK as remark,tb1.SORT as sort,tb1.STATE as state,tb1.UP_DOWN as upDown ");
			finder.append("FROM T_BUSINESS tb1 ");
			finder.append("LEFT JOIN ");
			finder.append("T_REGION_BUSINESS tb2 ");
			finder.append("ON　tb1.id = tb2.bus_Id ");
			finder.append("WHERE　1=1 ");
			
			if(StringUtils.isNotBlank(regionBusiness.getRegionId())){
				finder.append("AND tb2.region_Id = :region_Id ");
				finder.setParam("region_Id", regionBusiness.getRegionId());
			}
			if(StringUtils.isNotBlank(business.getBusCode())){
				finder.append("AND tb1.bus_Code like :bus_Code ");
				finder.setParam("bus_Code", business.getBusCode().trim()+"%");
			}
			if(StringUtils.isNotBlank(business.getBusName())){
				finder.append("AND tb1.bus_Name = :bus_Name ");
				finder.setParam("bus_Name", business.getBusName().trim());
			}
			if(StringUtils.isNotBlank(business.getUpDown())){
				finder.append("AND tb1.up_Down = :up_Down ");
				finder.setParam("up_Down", business.getUpDown().trim());
			}
			if(StringUtils.isNotBlank(business.getBusType())){
				finder.append("AND tb1.bus_type = :bus_type ");
				finder.setParam("bus_type", business.getBusType().trim());
			}
		}
		return super.queryForList(finder, Business.class, page);
	}


	@Override
	public Integer updateRegionBusiness(RegionBusiness regionBusiness, String busIds) throws Exception {
		Integer count = 0;
		List<RegionBusiness> rbss = super.findListDataByFinder(null, null, RegionBusiness.class, regionBusiness);
		for (RegionBusiness rbs : rbss) {
			super.deleteByEntity(rbs);
		}
		String[] arrayIds = busIds.split(",");
		for (String busId : arrayIds) {
			Business business = super.findById(busId, Business.class);
			if(business != null){
				RegionBusiness newRegionBusiness = new RegionBusiness();
				newRegionBusiness.setBusId(busId);
				newRegionBusiness.setRegionId(regionBusiness.getRegionId());
				super.save(newRegionBusiness);
				count++;
			}
		}
		return count;
	}
	
	//根据活动ID来查询地市
	@Override
	public List<RegionBusiness> findRegionBusinessByBusId(String actId) throws Exception {
		Finder finder = new Finder();
		finder.append(" select rb.* from ").append(Finder.getTableName(RegionBusiness.class));
		finder.append(" t_region_business rb");
		finder.append(" where rb.bus_id = :actId");
		finder.setParam("actId", actId);
		return super.queryForList(finder, RegionBusiness.class);
	}

}

