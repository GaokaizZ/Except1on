package org.springrain.sinova.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.DateUtils;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;

import org.springrain.sinova.entity.ExecuteTime;
import org.springrain.sinova.service.IExecuteTimeService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:17:37
 * @see org.springrain.sinova.service.impl.ExecuteTime
 */
@Service("executeTimeService")
public class ExecuteTimeServiceImpl extends BaseSpringrainServiceImpl implements IExecuteTimeService {


    @Override
	public String  save(Object entity ) throws Exception{
		ExecuteTime executeTime=(ExecuteTime) entity;
		return super.save(executeTime).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	ExecuteTime executeTime=(ExecuteTime) entity;
		return super.saveorupdate(executeTime).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		ExecuteTime executeTime=(ExecuteTime) entity;
		return super.update(executeTime);
    }

    @Override
	public ExecuteTime findExecuteTimeById(Object id) throws Exception{
		return super.findById(id,ExecuteTime.class);
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
	public void addExcuteTime(String className, String methodName,String mobile, String account,String ixml,
			String oxml) throws Exception {
		ExecuteTime excuteTime = new ExecuteTime();
		excuteTime.setClassName(className);
		excuteTime.setMethodName(methodName);
		excuteTime.setMobile(mobile);
		excuteTime.setInputParam(ixml);
		excuteTime.setOutputParam(oxml);
		excuteTime.setCallTime(DateUtils.formatDateTime(new Date()));
		this.save(excuteTime);
	}

}
