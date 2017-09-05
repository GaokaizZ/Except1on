package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.DateUtils;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.AcceptRecordDTO;
import org.springrain.sinova.dto.RotateDTO;
import org.springrain.sinova.entity.Rotate;
import org.springrain.sinova.service.IRotateService;
import org.springrain.system.service.BaseSpringrainServiceImpl;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:16:10
 * @see org.springrain.sinova.service.impl.UserOffice
 */
@Service("rotateService")
public class RotateServiceImpl extends BaseSpringrainServiceImpl implements IRotateService {


    @Override
	public String  save(Object entity ) throws Exception{
    	Rotate roate=(Rotate) entity;
		return super.save(roate).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	Rotate roate=(Rotate) entity;
		return super.saveorupdate(roate).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		Rotate roate=(Rotate) entity;
		return super.update(roate);
    }

    @Override
	public Rotate findRotateById(Object id) throws Exception{
		return super.findById(id,Rotate.class);
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
	 * 根据Rotate对象查找
	 * @param Rotate
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Rotate> findRoateByDTO(RotateDTO rotate,Page page)
			throws Exception {
		Finder finder = new Finder();
		finder.append("select t.* from t_rotate t where 1=1 ");
		
		//按条件查询时使用
		if(StringUtils.isNotBlank(rotate.getMobile())){
			finder.append("and t.mobile = :mobile ");
			finder.setParam("mobile",rotate.getMobile());
		}
		if(StringUtils.isNotBlank(rotate.getState())){
			finder.append("and t.state = :state ");
			finder.setParam("state",rotate.getState());
		}
		
		if(StringUtils.isNotBlank(rotate.getPrize())){
			finder.append("and t.prize = :prize ");
			finder.setParam("prize",rotate.getPrize());
		}
		
		if(StringUtils.isNotBlank(rotate.getGroupId())){
			finder.append("and t.groupId = :groupId ");
			finder.setParam("groupId",rotate.getGroupId());
		}
		
		if(StringUtils.isNotBlank(rotate.getBatCode())){
			finder.append("and t.BAT_CODE = :batCode ");
			finder.setParam("batCode",rotate.getBatCode());
		}
		
		if(null!=rotate.getBeginTime() && null!=rotate.getEndTime()){
			finder.append("and t.create_time between :beginTime and :endTime ");
			finder.setParam("beginTime",rotate.getBeginTime());
			finder.setParam("endTime",rotate.getEndTime());
		}else if(null!=rotate.getBeginTime() && null==rotate.getEndTime()){
			finder.append("and t.create_time >= :beginTime ");
			finder.setParam("beginTime",rotate.getBeginTime());
		}else if(null==rotate.getBeginTime() && null!=rotate.getEndTime()){
			finder.append("and t.create_time <= :endTime ");
			finder.setParam("endTime",rotate.getEndTime());
		}
		finder.append("order by t.create_time desc ");
		return super.queryForList(finder, Rotate.class, page);
	}
	
	/**
	 * 查询抽奖参与总人数
	 * @return Integer  抽奖参与总人数
	 * @throws Exception
	 */
	@Override
	public Integer getAllCount()
			throws Exception {
		int count=0;
		Finder finder = new Finder();
		finder.append("select count(0) as id  from t_rotate t ");
		List<Rotate> list=super.queryForList(finder, Rotate.class);
		if(list != null && list.size()>0){
			Rotate dto = list.get(0);
			count = Integer.parseInt(dto.getId());
		}
		return count;
	}
	public List<RotateDTO> findRoateListByDTO(RotateDTO rotate,Page page)
			throws Exception {
		String bDate=" 00:00:00";
		String eDate=" 23:59:59";
		Finder finder = new Finder();
		finder.append("select t.* from t_rotate t where 1=1 ");
		
		//按条件查询时使用
		if(StringUtils.isNotBlank(rotate.getMobile())){
			finder.append("and t.mobile = :mobile ");
			finder.setParam("mobile",rotate.getMobile().trim());
		}
		if(StringUtils.isNotBlank(rotate.getState())){
			finder.append("and t.state = :state ");
			finder.setParam("state",rotate.getState());
		}
		
		if(StringUtils.isNotBlank(rotate.getPrize())){
			finder.append("and t.prize = :prize ");
			finder.setParam("prize",rotate.getPrize());
		}
		
		if(StringUtils.isNotBlank(rotate.getGroupId())){
			finder.append("and t.groupId = :groupId ");
			finder.setParam("groupId",rotate.getGroupId());
		}
		
		if(StringUtils.isNotBlank(rotate.getBatCode())){
			finder.append("and t.BAT_CODE = :batCode ");
			finder.setParam("batCode",rotate.getBatCode());
		}
//		else{
////			finder.append("and t.BAT_CODE = '2' ");
//			finder.append("and t.BAT_CODE = 'accept' ");
//		}
		
		if(StringUtils.isNotBlank(rotate.getLotteryed())){
			finder.append("and t.lotteryed = :lotteryed ");
			finder.setParam("lotteryed",rotate.getLotteryed());
		}

		if(null!=rotate.getBeginTime() && null!=rotate.getEndTime()){
			logger.info("beginDate=="+rotate.getBeginTime()+"=====endDate=="+rotate.getEndTime());
			String bTmpDate=DateUtils.formatDateTime(rotate.getBeginTime(),"yyyy-MM-dd")+bDate;
			String eTmpDate=DateUtils.formatDateTime(rotate.getEndTime(),"yyyy-MM-dd")+eDate;
			Date btime=DateUtils.parseDateTime(bTmpDate);
			Date etime=DateUtils.parseDateTime(eTmpDate);
			finder.append(" and t.CREATE_TIME between :beginTime and :endTime ");
			finder.setParam("beginTime", btime);
			finder.setParam("endTime", etime);
		}else if(null!=rotate.getBeginTime() && null==rotate.getEndTime()){
			logger.info("beginDate=="+rotate.getBeginTime()+"=====endDate=="+rotate.getEndTime());
			String bTmpDate=DateUtils.formatDateTime(rotate.getBeginTime(),"yyyy-MM-dd")+bDate;
			Date btime=DateUtils.parseDateTime(bTmpDate);
			finder.append(" and t.CREATE_TIME >= :beginTime ");
			finder.setParam("beginTime", btime);
		}else if(null==rotate.getBeginTime() && null!=rotate.getEndTime()){
			logger.info("beginDate=="+rotate.getBeginTime()+"=====endDate=="+rotate.getEndTime());
			String eTmpDate=DateUtils.formatDateTime(rotate.getEndTime(),"yyyy-MM-dd")+eDate;
			Date etime=DateUtils.parseDateTime(eTmpDate);
			finder.append(" and t.CREATE_TIME <=:endTime ");
			finder.setParam("endTime", etime);
		}
		finder.append("order by t.create_time desc ");
		return super.queryForList(finder, RotateDTO.class, page);
	}

	@Override
	public List<RotateDTO> findRoateListByAcceptId(String acceptId)
			throws Exception {
		Finder finder = new Finder();
		finder.append("select t.* from t_rotate t where 1=1 ");
		
		//按条件查询时使用
		if(StringUtils.isNotBlank(acceptId)){
			finder.append("and t.ACCEPT_ID = :acceptId ");
			finder.setParam("acceptId",acceptId);
		}
		finder.append("order by t.create_time desc ");
		return super.queryForList(finder, RotateDTO.class);
	}

	@Override
	public List<Rotate> findFailRoate() throws Exception {
		Finder finder = new Finder();
		finder.append("select t.* from t_rotate t where 1=1 ");
		finder.append("and t.ACCEPT_ID<>' ' and t.bat_code='accept' and t.PRIZE<>'0' and t.LOTTERYED='1' ");
		finder.append("and t.id not in (select a.rotate_id from t_accept_record a where a.rotate_id is not null group by a.rotate_id) ");
		//按条件查询时使用
		finder.append("order by t.create_time desc ");
		return super.queryForList(finder, Rotate.class);
	}

	@Override
	public Integer getLotteryCount() throws Exception {
		int count=0;
		Finder finder = new Finder();
		finder.append("select count(0) as id  from t_rotate t where t.accept_id is not null");
		List<Rotate> list=super.queryForList(finder, Rotate.class);
		if(list != null && list.size()>0){
			Rotate dto = list.get(0);
			count = Integer.parseInt(dto.getId());
		}
		return count;
	}

	
	
}
