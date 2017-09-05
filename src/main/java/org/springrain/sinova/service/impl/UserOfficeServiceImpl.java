package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.OfficeUserDTO;
import org.springrain.sinova.dto.UserNameDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.system.entity.DicData;
import org.springrain.system.entity.User;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.util.Constant;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:16:10
 * @see org.springrain.sinova.service.impl.UserOffice
 */
@Service("userOfficeService")
public class UserOfficeServiceImpl extends BaseSpringrainServiceImpl implements IUserOfficeService {


    @Override
	public String  save(Object entity ) throws Exception{
		UserOffice userOffice=(UserOffice) entity;
		return super.save(userOffice).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	UserOffice userOffice=(UserOffice) entity;
		return super.saveorupdate(userOffice).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		UserOffice userOffice=(UserOffice) entity;
		return super.update(userOffice);
    }

    @Override
	public UserOffice findUserOfficeById(Object id) throws Exception{
		return super.findById(id,UserOffice.class);
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
	 * 
	 * @description 根据用户ID查询用户地市、区县、营业厅、岗位信息 <br/>
	 *              用户必须有效
	 * @date 2015年1月10日 下午6:42:40 <br/>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserOfficeDTO findUserOfficeDTOById(Object id) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select r.region_code as regionCode, r.region_name as regionName, c.county_code as countyCode, c.county_name as countyName, o.office_code as officeCode, office_name as officeName, o.address as address, uo.post as post ");
		sb.append("from t_user u, t_region r, t_county c, t_office o, t_user_office uo ");
		sb.append("where u.id = uo.user_id and uo.region_code = r.region_code and uo.county_code = c.county_code and uo.office_code = o.office_code and u.state = :state and u.id = :id ");


		Finder finder = new Finder(sb);
		finder.setParam("state", Constant.ONE);
		finder.setParam("id", id);

		return super.queryForObject(finder, UserOfficeDTO.class);
	}
    /**
     * 根据营业厅管理员的userId查找该营业厅的营业员
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<User> findUserByUserId(String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select u.id, u.name from t_user_office uo, t_user u ,t_user_role tur,t_role tr ");
        sb.append("where ");
        sb.append("uo.user_id = u.id ");
        sb.append("and tur.user_id = u.id ");
        sb.append("and tur.role_id = tr.id ");
        sb.append("and tr.code != :code ");
        sb.append("and u.state =:state  ");
        sb.append("and uo.office_code = (select uo.office_code from t_user_office uo ");
        sb.append("where uo.user_id = :userId) ");
        
        Finder finder = new Finder(sb);
        finder.setParam("code", Constant.ROLE_REGION_MANAGER);
        finder.setParam("state", Constant.ONE);
        finder.setParam("userId", userId);
        
        return super.queryForList(finder, User.class);
    }

	/* 
	 * 查询营业厅中的用户,根据营业厅编号/用户工号
	 * (non-Javadoc)
	 * @see org.springrain.sinova.service.IUserOfficeService#findOfficeUserDTOByOffice(java.lang.Object, java.lang.Object, org.springrain.frame.util.Page)
	 */
	@Override
	public List<OfficeUserDTO> findOfficeUserDTOByOffice(OfficeUserDTO officeUserDTO,Page page)
			throws Exception {
		Finder finder = new Finder();
		finder.append("select uo.id as id, u.name as userName, u.account as account, u.id as userId, r.region_code as regionCode, r.region_name as regionName, c.county_code as countyCode, c.county_name as countyName, o.office_code as officeCode, o.office_name as officeName, uo.post as post ");
		finder.append("from t_user u, t_region r, t_county c, t_office o, t_user_office uo ");
		finder.append("where u.id = uo.user_id and uo.region_code = r.region_code and uo.county_code = c.county_code and uo.office_code = o.office_code and u.state = :state and o.office_code = :officeCode ");
		
		//按条件查询时使用
		if(StringUtils.isNotBlank(officeUserDTO.getAccount())){
			finder.append("and u.account = :account");
			finder.setParam("account", officeUserDTO.getAccount());
		}

		finder.setParam("officeCode", officeUserDTO.getOfficeCode());
		finder.setParam("state", Constant.ONE);

		return super.queryForList(finder, OfficeUserDTO.class, page);
	}

	/* 
	 * 查询未分配营业厅的用户
	 * (non-Javadoc)
	 * @see org.springrain.sinova.service.IUserOfficeService#findUserNotInOffice(org.springrain.frame.util.Page)
	 */
	@Override
	public List<UserNameDTO> findUserNotInOffice(Page page, UserNameDTO userNameDTO) throws Exception {
		
		Finder finder = new Finder();
		finder.append("select distinct u.id as id, u.id as userId, u.account as account, u.name as userName ");
		finder.append("from t_user u ");
		finder.append("where u.id not in(select tu.id from t_user tu inner join t_user_office uo on tu.id = uo.user_id) ");
		finder.append(" and u.state = :state");
		
		finder.setParam("state", Constant.ONE);
		//条件查询用
		if(StringUtils.isNotBlank(userNameDTO.getAccount())){
			finder.append(" and u.account like :account");
			finder.setParam("account", "%"+userNameDTO.getAccount()+"%");
		}
		if(StringUtils.isNotBlank(userNameDTO.getUserName())){
			finder.append(" and u.name like :name");
			//String name = new String(userNameDTO.getUserName().getBytes("ISO-8859-1"), "utf-8");
			finder.setParam("name", "%"+userNameDTO.getUserName()+"%");
		}
		
		return super.queryForList(finder, UserNameDTO.class, page);
	}

	@Override
	public List<DicData> findListDicData() throws Exception {

		Finder finder = Finder.getSelectFinder(DicData.class).append("  where typekey='post' and state='1'  ");
		
		return super.queryForList(finder, DicData.class);
	}
	
	//查询全省的营业员
	
		@Override
		public List<UserOffice> findUserOfficeByRegionCode(String regionCode) throws Exception {
				List<UserOffice> userOfficeList = new ArrayList<UserOffice>();
				Finder finder = Finder.getSelectFinder(UserOffice.class);
				finder.append("where region_code =:regionCode ").setParam("regionCode", regionCode);
				logger.info("------查询全部地市start===");
				userOfficeList = super.queryForList(finder, UserOffice.class);
				if (CollectionUtils.isEmpty(userOfficeList)) {
					return null;
				}
				return userOfficeList;
		
		}

	/** 
	 * 根据地市编码查询用户
	 */
	@Override
	public List<User> findOfficeUserDTOByOffice(UserOffice userOffice) throws Exception {
		Finder finder = new Finder();
		finder.append("select tu.id as id,tu.name as name,tu.account as account,tu.workno as workno,tu.cardno as cardno ");
		finder.append("from t_user tu left join T_USER_OFFICE tuo on tu.id=tuo.USER_ID where 1=1 ");
		finder.append("and tu.account != 'admin' ");
		
		//条件查询用
		if(StringUtils.isNotBlank(userOffice.getRegionCode())){
			finder.append("and tuo.REGION_CODE=:regionCode ");
			finder.setParam("regionCode", userOffice.getRegionCode());
		}
		return super.queryForList(finder, User.class, null);
	}
	
	/** 
	 * 根据地市编码查询用户
	 */
	@Override
	public List<User> findOfficeUserDTOByOffice(UserOffice userOffice,Page page) throws Exception {
		Finder finder = new Finder();
		finder.append("select tu.id as id,tu.name as name,tu.account as account,tu.workno as workno,tu.cardno as cardno ");
		finder.append("from t_user tu left join T_USER_OFFICE tuo on tu.id=tuo.USER_ID where 1=1 ");
		finder.append("and tu.account != 'admin' ");
		
		//条件查询用
		if(StringUtils.isNotBlank(userOffice.getRegionCode())){
			finder.append("and tuo.REGION_CODE=:regionCode ");
			finder.setParam("regionCode", userOffice.getRegionCode());
		}
		if(StringUtils.isNotBlank(userOffice.getCountyCode())){
			finder.append("and tuo.COUNTY_CODE=:countyCode ");
			finder.setParam("countyCode", userOffice.getCountyCode());
		}
		if(StringUtils.isNotBlank(userOffice.getOfficeCode())){
			finder.append("and tuo.OFFICE_CODE=:officeCode ");
			finder.setParam("officeCode", userOffice.getOfficeCode());
		}
		logger.info("4"+finder.getSql());
		return super.queryForList(finder, User.class, page);
	}

	@Override
	public List<User> findOfficeUserDTOByOffice(String dicRoleCode,User user, UserOffice userOffice, Page page) throws Exception {
		Finder finder = new Finder();
		finder.append("select tu.id as id,tu.name as name,tu.account as account,tu.workno as workno,tu.cardno as cardno ,tr.name as createUser ");
		finder.append("from t_user tu " );
		finder.append("left join T_USER_OFFICE tuo on tu.id=tuo.USER_ID " );
		finder.append("left join T_USER_ROLE tur on tu.id=tur.USER_ID " );
		finder.append("left join T_ROLE tr on tur.ROLE_ID=tr.ID " );
		finder.append("where 1=1 ");
		finder.append("and tu.account != 'admin' ");
		
		if(StringUtils.isNotBlank(user.getId())){
			finder.append("and tu.id != :id ");
			finder.setParam("id", user.getId());
		}
		//条件查询用
		if(StringUtils.isNotBlank(userOffice.getRegionCode())){
			finder.append("and tuo.REGION_CODE=:regionCode ");
			finder.setParam("regionCode", userOffice.getRegionCode());
		}
		if(StringUtils.isNotBlank(userOffice.getCountyCode())){
			finder.append("and tuo.COUNTY_CODE=:countyCode "); 
			finder.setParam("countyCode", userOffice.getCountyCode());
		}
		if(StringUtils.isNotBlank(userOffice.getOfficeCode())){
			finder.append("and tuo.OFFICE_CODE=:officeCode ");
			finder.setParam("officeCode", userOffice.getOfficeCode());
		}
		if(StringUtils.isNotBlank(user.getAccount())){
			finder.append("and tu.ACCOUNT=:account ");
			finder.setParam("account", user.getAccount());
		}
		if(StringUtils.isNotBlank(user.getName())){
			finder.append("and tu.NAME like :name ");
			finder.setParam("name", user.getName()+"%");
		}
		
		//ROLE_REGIONER 地市管理员  dicRoleCode
		//ROLE_OFFICE_MANAGE 营业厅管理员  dicRoleCode
		if(StringUtils.isNotBlank(dicRoleCode)){
			if("ROLE_SUPERMAN".equals(dicRoleCode)){//如果是地市管理员
				finder.append("and tr.code not in  ('ROLE_SUPERMAN') ");
			}
			
			if("REGION_MANAGER".equals(dicRoleCode)){//如果是地市管理员
				finder.append("and tr.code not in  ('ROLE_SUPERMAN','REGION_MANAGER') ");
			}
			
			if("ROLE_OFFICE_MANAGE".equals(dicRoleCode)){//如果是营业厅管理员
				finder.append("and tr.code not in  ('ROLE_SUPERMAN','REGION_MANAGER','ROLE_OFFICE_MANAGE') ");
			}
		}
		finder.append(" order by tr.code desc ");
		
		return super.queryForList(finder, User.class, page);
	}

	@Override
	public UserOffice findUserOfficeDTOByUserId(Object id) throws Exception {
		UserOffice userOffice = new UserOffice();
		userOffice.setUserId(id+"");
		List<UserOffice> uos = this.findListDataByFinder(null, null, UserOffice.class, userOffice);
		if(uos!= null && !uos.isEmpty()){
			return uos.get(0);
		}else{
			return null;
		}
	}
	
	

	
}
