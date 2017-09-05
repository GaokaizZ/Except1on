package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.service.IRegionService;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.util.Constant;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:12:19
 * @see org.springrain.sinova.service.impl.Region
 */
@Service("regionService")
public class RegionServiceImpl extends BaseSpringrainServiceImpl implements IRegionService {


    @Override
	public String  save(Object entity ) throws Exception{
		Region region=(Region) entity;
		return super.save(region).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	Region region=(Region) entity;
		return super.saveorupdate(region).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		Region region=(Region) entity;
		return super.update(region);
    }

    @Override
	public Region findRegionById(Object id) throws Exception{
		return super.findById(id,Region.class);
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
	 * 查询地市
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Region> findRegion(String regionName)
			throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		Finder finder = Finder.getSelectFinder(Region.class);
		finder.append("where state =:state ").setParam("state", Constant.ONE);
		logger.info("------regionName==="+regionName);
		if(StringUtils.isNotBlank(regionName)){
			finder.append(" and region_name like :regionName");
			finder.setParam("regionName", "%"+regionName+"%");
			
		}
		regionList = super.queryForList(finder, Region.class);
		if (CollectionUtils.isEmpty(regionList)) {
			return null;
		}
		
		return regionList;
	}
	
	//根据地市编码来查询地市信息
	@Override
	public List<Region> findRegionByRegionCode(String regionCode)
			throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		Finder finder = Finder.getSelectFinder(Region.class);
		finder.append("where state =:state ").setParam("state", Constant.ONE);
		logger.info("------regionCode==="+regionCode);
		if(StringUtils.isNotBlank(regionCode)){
			finder.append(" and region_code like :regionCode");
			finder.setParam("regionCode", "%"+regionCode+"%");
			
		}
		regionList = super.queryForList(finder, Region.class);
		if (CollectionUtils.isEmpty(regionList)) {
			return null;
		}
		
		return regionList;
	}
	
	//查询全部地市
	@Override
	public List<Region> findRegionList() throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		Finder finder = Finder.getSelectFinder(Region.class);
		finder.append("where state =:state ").setParam("state", Constant.ONE);
		logger.info("------查询全部地市start===");
		regionList = super.queryForList(finder, Region.class);
		if (CollectionUtils.isEmpty(regionList)) {
			return null;
		}
		return regionList;
	}
}
