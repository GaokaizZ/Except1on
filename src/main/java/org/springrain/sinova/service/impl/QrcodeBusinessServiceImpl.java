package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;

import org.springrain.sinova.entity.QrcodeBusiness;
import org.springrain.sinova.service.IQrcodeBusinessService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:15:29
 * @see org.springrain.sinova.service.impl.QrcodeBusiness
 */
@Service("qrcodeBusinessService")
public class QrcodeBusinessServiceImpl extends BaseSpringrainServiceImpl implements IQrcodeBusinessService {


    @Override
	public String  save(Object entity ) throws Exception{
		QrcodeBusiness qrcodeBusiness=(QrcodeBusiness) entity;
		return super.save(qrcodeBusiness).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	QrcodeBusiness qrcodeBusiness=(QrcodeBusiness) entity;
		return super.saveorupdate(qrcodeBusiness).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		QrcodeBusiness qrcodeBusiness=(QrcodeBusiness) entity;
		return super.update(qrcodeBusiness);
    }

    @Override
	public QrcodeBusiness findQrcodeBusinessById(Object id) throws Exception{
		return super.findById(id,QrcodeBusiness.class);
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

}
