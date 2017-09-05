package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.ExternalQrcodeDTO;
import org.springrain.sinova.entity.ExternalQrcode;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.IExternalQrcodeService;
import org.springrain.system.entity.User;
import org.springrain.system.service.BaseSpringrainServiceImpl;

@Service("externalQrcodeService")
public class ExternalQrcodeServiceImpl extends BaseSpringrainServiceImpl
		implements IExternalQrcodeService {


	@Override
	public String save(Object entity) throws Exception {
		ExternalQrcode exqrcode = (ExternalQrcode) entity;
		return super.save(exqrcode).toString();
	}

	@Override
	public String saveorupdate(Object entity) throws Exception {
		ExternalQrcode exqrcode = (ExternalQrcode) entity;
		return super.saveorupdate(exqrcode).toString();
	}

	@Override
	public Integer update(IBaseEntity entity) throws Exception {
		ExternalQrcode exqrcode = (ExternalQrcode) entity;
		return super.update(exqrcode);
	}

	@Override
	public ExternalQrcode findExternalQrcodeById(Object id) throws Exception {
		return super.findById(id, ExternalQrcode.class);
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object o) throws Exception {
		return super.findListDataByFinder(finder, page, clazz, o);
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
	public List<ExternalQrcode> findExternalQrcode(ExternalQrcode exQrcode, Page page)
			throws Exception {
		Finder finder = new Finder();
		finder.append(" select distinct spare2 as spare2,ex_name as exName, belong_user as belongUser, image_url as imageUrl, image_name as imageName ,create_time as createTime from t_externalqrcode ");
		
		if(StringUtils.isBlank(exQrcode.getBelongUser())){
			finder.append(" where 1=1 ");
		}else{
			finder.append(" where belong_user =:belongUser ");
			finder.setParam("belongUser", exQrcode.getBelongUser());
		}
		
		if(StringUtils.isNotBlank(exQrcode.getExName())){
			finder.append(" and ex_name like :exName ");
			finder.setParam("exName", "%"+exQrcode.getExName()+"%");
		}
		
		finder.append(" order by create_time desc");
		
		
		return super.queryForList(finder, ExternalQrcode.class, page);
	}

	@Override
	public List<ExternalQrcodeDTO> findByUser(ExternalQrcodeDTO exQrcode,
			Page page) throws Exception{
		if(StringUtils.isBlank(exQrcode.getBelongUser())){
			logger.info("************批量外链点击量报表*********findByUser**用户为空*******");
			return null;
		}
		Finder finder = new Finder();
		finder.append("select e.ex_name as exName, e.ex_url as exUrl, e.spare1 as spare1, e.spare3 as spare3,  ");
		finder.append(" u.name as userName, o.office_name as officeName, r.region_name as regionName from ");
		finder.append(Finder.getTableName(ExternalQrcode.class)).append(" e, ");
		finder.append(Finder.getTableName(User.class)).append(" u, ");
		finder.append(Finder.getTableName(Region.class)).append(" r, ");
		finder.append(Finder.getTableName(Office.class)).append(" o, ");
		finder.append(Finder.getTableName(UserOffice.class)).append(" uo ");
		finder.append(" where e.belong_user=u.workno and u.id=uo.user_id and uo.office_code=o.office_code and uo.region_code=r.region_code ");
		if("admin".equals(exQrcode.getBelongUser())){
			
		}else{
			finder.append(" and e.belong_user =:belongUser ");
			finder.setParam("belongUser", exQrcode.getBelongUser());
		}
		if(StringUtils.isNotBlank(exQrcode.getExName())){
			finder.append(" and e.ex_name like :exName ");
			finder.setParam("exName", "%"+exQrcode.getExName().trim()+"%");
		}
		if(StringUtils.isNotBlank(exQrcode.getUserName())){
			finder.append(" and u.name like :userName ");
			finder.setParam("userName", "%"+exQrcode.getUserName().trim()+"%");
		}
		finder.append(" order by e.create_time desc, e.spare1  ");
		return queryForList(finder, ExternalQrcodeDTO.class, page);
	}

	
	

}
