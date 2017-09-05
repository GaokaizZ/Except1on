package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.system.entity.User;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.util.Constant;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;

import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.service.IOfficeService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:01
 * @see org.springrain.sinova.service.impl.Office
 */
@Service("officeService")
public class OfficeServiceImpl extends BaseSpringrainServiceImpl implements IOfficeService {


    @Override
	public String  save(Object entity ) throws Exception{
		Office office=(Office) entity;
		return super.save(office).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	Office office=(Office) entity;
		return super.saveorupdate(office).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		Office office=(Office) entity;
		return super.update(office);
    }

    @Override
	public Office findOfficeById(Object id) throws Exception{
		return super.findById(id,Office.class);
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
	public List<Office> findOfficeByRegionCode(String regionCode,String countyCode,String officeCode) throws Exception {
		Finder finder = Finder.getSelectFinder(Office.class).append(" where 1=1 ");
		if(StringUtils.isNotBlank(regionCode)){
			finder.append(" and REGION_CODE like :regionCode ");
			finder.setParam("regionCode", regionCode);
		}
		if(StringUtils.isNotBlank(countyCode)){
			finder.append(" and COUNTY_CODE like :countyCode ");
			finder.setParam("countyCode", countyCode);
		}
		if(StringUtils.isNotBlank(officeCode)){
			finder.append(" and REGION_CODE like :officeCode ");
			finder.setParam("officeCode", regionCode);
		}
		return super.queryForList(finder, Office.class);
	}


}
