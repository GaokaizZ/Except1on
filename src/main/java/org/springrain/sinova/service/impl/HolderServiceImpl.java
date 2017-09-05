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

import org.springrain.sinova.entity.Holder;
import org.springrain.sinova.service.IHolderService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-04-20 11:59:16
 * @see org.springrain.sinova.service.impl.Holder
 */
@Service("holderService")
public class HolderServiceImpl extends BaseSpringrainServiceImpl implements IHolderService {


    @Override
	public String  save(Object entity ) throws Exception{
		Holder holder=(Holder) entity;
		return super.save(holder).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	Holder holder=(Holder) entity;
		return super.saveorupdate(holder).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		Holder holder=(Holder) entity;
		return super.update(holder);
    }

    @Override
	public Holder findHolderById(Object id) throws Exception{
		return super.findById(id,Holder.class);
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
	public List<Holder> findByHolder(Holder holder) throws Exception {
		Finder finder = new Finder("SELECT * from ").append(Finder.getTableName(Holder.class)).append("  where state=:state ");
		finder.setParam("state", Constant.ONE);
		if (StringUtils.isNotBlank(holder.getToken())) {
			finder.append(" and token=:token ");
			finder.setParam("token", holder.getToken());
		}
		if (StringUtils.isNotBlank(holder.getSourceNo())) {
			finder.append(" and SOURCE_NO=:sourceNo ");
			finder.setParam("sourceNo", holder.getSourceNo());
		}
		finder.append(" order by id ");
		return super.queryForList(finder, Holder.class);
	}

}
