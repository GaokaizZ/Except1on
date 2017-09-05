package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.Region;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.util.Constant;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;

import org.springrain.sinova.entity.County;
import org.springrain.sinova.service.ICountyService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:12:53
 * @see org.springrain.sinova.service.impl.County
 */
@Service("countyService")
public class CountyServiceImpl extends BaseSpringrainServiceImpl implements ICountyService {


    @Override
	public String  save(Object entity ) throws Exception{
		County county=(County) entity;
		return super.save(county).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	County county=(County) entity;
		return super.saveorupdate(county).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		County county=(County) entity;
		return super.update(county);
    }

    @Override
	public County findCountyById(Object id) throws Exception{
		return super.findById(id,County.class);
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
	 * 根据地市，查找该地市下的区县
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<County> findCountyByRegionCode(String regionName, String countyName, String regionCode)
			throws Exception {
		List<County> countyList = new ArrayList<County>();
		Finder finder = new Finder();
		finder.append(" select c.* from ").append(Finder.getTableName(County.class)).append(" c, ");
		finder.append(" t_region r ");
		finder.append("where c.state =:state ").setParam("state", Constant.ONE);
		finder.append(" and c.region_code = r.region_code  ");
		logger.info("---------regionName-====--"+regionName+"----countyName=="+countyName);
		if(StringUtils.isNotBlank(regionCode)){
			finder.append(" and r.region_code like :regionCode ").setParam("regionCode", "%"+regionCode+"%");
		}
		if(StringUtils.isNotBlank(countyName)){
			finder.append(" and c.county_name like :countyName ").setParam("countyName", "%"+countyName+"%");
		}
		if(StringUtils.isNotBlank(regionName)){
			finder.append(" and r.region_name like :regionName ").setParam("regionName", "%"+regionName+"%");
		}
		finder.append(" order by c.id ");
		countyList = super.queryForList(finder, County.class);
		if (CollectionUtils.isEmpty(countyList)) {
			return null;
		}
		
		return countyList;
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

}
