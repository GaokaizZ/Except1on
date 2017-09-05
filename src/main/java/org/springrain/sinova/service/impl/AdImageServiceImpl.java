package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;

import org.springrain.sinova.entity.AdImage;
import org.springrain.sinova.service.IAdImageService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-05-05 10:37:53
 * @see org.springrain.sinova.service.impl.AdImage
 */
@Service("adImageService")
public class AdImageServiceImpl extends BaseSpringrainServiceImpl implements IAdImageService {


    @Override
	public String  save(Object entity ) throws Exception{
		AdImage adImage=(AdImage) entity;
		return super.save(adImage).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	AdImage adImage=(AdImage) entity;
		return super.saveorupdate(adImage).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		AdImage adImage=(AdImage) entity;
		return super.update(adImage);
    }

    @Override
	public AdImage findAdImageById(Object id) throws Exception{
		return super.findById(id,AdImage.class);
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
	public AdImage findAdImageByUserId(String userId) throws Exception {
		Finder finder = Finder.getSelectFinder(AdImage.class);
		finder.append("where owner=:userId");
		finder.setParam("userId", userId);
		return super.queryForObject(finder, AdImage.class);
	}

}
