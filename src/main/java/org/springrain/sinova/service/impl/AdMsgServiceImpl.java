package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.AdMsg;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.service.IAdMsgService;
import org.springrain.system.service.BaseSpringrainServiceImpl;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-06-25 10:57:10
 * @see org.springrain.sinova.service.impl.AdMsg
 */
@Service("adMsgService")
public class AdMsgServiceImpl extends BaseSpringrainServiceImpl implements IAdMsgService {


//    @Override
//	public String  save(Object entity ) throws Exception{
//		AdMsg adMsg=(AdMsg) entity;
//		return super.save(adMsg).toString();
//	}
//
//    @Override
//	public String  saveorupdate(Object entity ) throws Exception{
//    	AdMsg adMsg=(AdMsg) entity;
//		return super.saveorupdate(adMsg).toString();
//	}
//
//	@Override
//    public Integer update(IBaseEntity entity ) throws Exception{
//		AdMsg adMsg=(AdMsg) entity;
//		return super.update(adMsg);
//    }

    @Override
	public AdMsg findAdMsgById(Object id) throws Exception{
		return super.findById(id,AdMsg.class);
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
	public Map<String,AdMsg> findAdMsgByUser(String userId,Page page) throws Exception {
		Finder finder = Finder.getSelectFinder(AdMsg.class);
		finder.append(" where 1=1 ");
		if (StringUtils.isNotBlank(userId)) {
			finder.append("and owner =:owner ");
			finder.setParam("owner", userId);
		}
		List<AdMsg> l = this.queryForList(finder, AdMsg.class, page);
		Map<String,AdMsg> m = new TreeMap<String,AdMsg>();
		if(l != null && !l.isEmpty()){
			for (AdMsg adMsg : l) {
				m.put(adMsg.getKey(), adMsg);
			}
		}
		return m;
	}

}
