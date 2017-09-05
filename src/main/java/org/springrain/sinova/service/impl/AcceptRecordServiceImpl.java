package org.springrain.sinova.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;

import org.springrain.sinova.entity.AcceptRecord;
import org.springrain.sinova.service.IAcceptRecordService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:17:04
 * @see org.springrain.sinova.service.impl.AcceptRecord
 */
@Service("acceptRecordService")
public class AcceptRecordServiceImpl extends BaseSpringrainServiceImpl implements IAcceptRecordService {


    @Override
	public String  save(Object entity ) throws Exception{
		AcceptRecord acceptRecord=(AcceptRecord) entity;
		return super.save(acceptRecord).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	AcceptRecord acceptRecord=(AcceptRecord) entity;
		return super.saveorupdate(acceptRecord).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		AcceptRecord acceptRecord=(AcceptRecord) entity;
		return super.update(acceptRecord);
    }

    @Override
	public AcceptRecord findAcceptRecordById(Object id) throws Exception{
		return super.findById(id,AcceptRecord.class);
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
	public List<AcceptRecord> validateLottery(String mobile) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime())+" 00:00:00";
        Finder finder = new Finder();
        finder.append("select a.* from t_accept_record a,t_business b where a.bus_id=b.id and a.mobile=:mobile and b.bus_type='main' and a.datetime between to_date(:sttime,'yyyy-mm-dd hh24:mi:ss') and sysdate");
        finder.setParam("mobile", mobile);
        finder.setParam("sttime", first);
        finder.append(" order by a.datetime");
		return super.queryForList(finder, AcceptRecord.class);
	}

}
