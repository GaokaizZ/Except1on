package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.RegionBusiness;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.sinova.service.IUserBusinessService;
import org.springrain.system.service.BaseSpringrainServiceImpl;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:15:03
 * @see org.springrain.sinova.service.impl.UserBusiness
 */
@Service("userBusinessService")
public class UserBusinessServiceImpl extends BaseSpringrainServiceImpl implements IUserBusinessService {

	private static final Logger logger = LoggerFactory.getLogger(UserBusinessServiceImpl.class);

    @Override
	public String  save(Object entity ) throws Exception{
		UserBusiness userBusiness=(UserBusiness) entity;
		return super.save(userBusiness).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	UserBusiness userBusiness=(UserBusiness) entity;
		return super.saveorupdate(userBusiness).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		UserBusiness userBusiness=(UserBusiness) entity;
		return super.update(userBusiness);
    }

    @Override
	public UserBusiness findUserBusinessById(Object id) throws Exception{
		return super.findById(id,UserBusiness.class);
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
	 * 根据业务ID或用户ID查询关系表
	 * @param userId
	 * @param busId
	 * @return
	 * @throws Exception
	 */
	public List<UserBusiness> findUserBusinessByubId(String userId,String busId) throws Exception {
		if (StringUtils.isBlank(busId) && StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("userId and busId is empty");
		}

		Finder finder = new Finder();
		finder.append("select ub.* ")
			  .append("from t_user_business ub ")
			  .append("where 1=1 ");
		if(StringUtils.isNoneBlank(userId)){
			finder.append(" and ub.user_id=:userId ");
			finder.setParam("userId", userId);
		}
		if(StringUtils.isNoneBlank(busId)){
			finder.append(" and ub.bus_id=:busId ");
			finder.setParam("busId", busId);
		}
		logger.info("查询业务对象sql:{}", finder.getSql());
		return super.queryForList(finder, UserBusiness.class);
	}

	@Override
	public Integer updateUserBusiness(UserBusiness userBusiness, String busIds) throws Exception {
		Integer count = 0;
		List<UserBusiness> ubss = super.findListDataByFinder(null, null, UserBusiness.class, userBusiness);
		for (UserBusiness ubs : ubss) {
			super.deleteByEntity(ubs);
		}
		String[] arrayIds = busIds.split(",");
		for (String busId : arrayIds) {
			Business business = super.findById(busId, Business.class);
			if(business != null){
				UserBusiness newUserBusiness = new UserBusiness();
				newUserBusiness.setBusId(busId);
				newUserBusiness.setUserId(userBusiness.getUserId());
				super.save(newUserBusiness);
				count++;
			}
		}
		return count;
	}

	@Override
	public void deleteByBusId(String busId)throws Exception {
		// TODO Auto-generated method stub
		Finder userBusinessfinder = Finder.getDeleteFinder(UserBusiness.class).append("where bus_id=:busId");
		userBusinessfinder.setParam("busId", busId);
		this.update(userBusinessfinder);
	}

}
