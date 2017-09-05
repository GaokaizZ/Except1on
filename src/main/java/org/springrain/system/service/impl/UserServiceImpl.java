package org.springrain.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MD5Utils;
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.County;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.RegionBusiness;
import org.springrain.sinova.entity.SpecialQrcode;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.interfaces.IIsoftstoneInterface;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.sinova.interfaces.isoftstone.customaryInfo.rep.CustomaryInfoResponse;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.rep.MobileLogonResponse;
import org.springrain.sinova.interfaces.sitech.sgrouptree.rep.SGroupTreeResponse;
import org.springrain.sinova.interfaces.sitech.sloginno.rep.SLoginNoQryResponse;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.ICountyService;
import org.springrain.sinova.service.IOfficeService;
import org.springrain.sinova.service.IRegionBusinessService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.ISpecialQrcodeService;
import org.springrain.sinova.service.IUserBusinessService;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.system.entity.Org;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserOrg;
import org.springrain.system.entity.UserRole;
import org.springrain.system.exceptions.BaseRuntimeException;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IRoleService;
import org.springrain.system.service.IUserOrgService;
import org.springrain.system.service.IUserRoleMenuService;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.service.IUserService;
import org.springrain.system.util.Constant;



/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2013-07-06 16:03:00
 * @see org.springrain.springrain.service.impl.User
 */
@Service("userService")
public class UserServiceImpl extends BaseSpringrainServiceImpl implements IUserService {

	@Resource
	private IUserOrgService userOrgService;
	@Resource
	private IUserRoleMenuService userRoleMenuService;
	@Resource
	private IOfficeService officeService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IRegionBusinessService regionBusinessService;
	@Resource
	private ICountyService countyService;
	@Resource
	private IUserOfficeService userOfficeService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IUserBusinessService userBuesinessService;
	@Resource
	private IBusinessService buesinessService;
	@Resource
	private ISitechInterface sitechInterFace;//boss接口
	@Resource
	private IIsoftstoneInterface softstoneInterface;//4A接口
	@Resource
	private ISpecialQrcodeService specialQrcodeService;
	@Override
	public String saveUser(User entity) throws Exception {
		String id = super.save(entity).toString();
		updateUserInfo(entity);

		return id;
	}

	@Override
	@CacheEvict(value = GlobalStatic.qxCacheKey, allEntries = true)
	public Integer updateUser(User entity) throws Exception {
		Integer update = super.update(entity, true);
		updateUserInfo(entity);
		
		return update;
	}

	private void updateUserInfo(User user) throws Exception {
		String userId = user.getId();
		Finder userOrgFinder = Finder.getDeleteFinder(UserOrg.class).append(" WHERE user_id=:userId ");
		userOrgFinder.setParam("userId", userId);
		super.update(userOrgFinder);

		Finder userRoleFinder = Finder.getDeleteFinder(UserRole.class).append(" WHERE user_id=:userId ");
		userRoleFinder.setParam("userId", userId);
		super.update(userRoleFinder);

		List<Org> listOrg = user.getUserOrgs();
		if (CollectionUtils.isNotEmpty(listOrg)) {
			for (Org org : listOrg) {
				UserOrg uo = new UserOrg();
				uo.setUserId(userId);
				uo.setOrgId(org.getId());
				
				super.save(uo);
			}
		}

		List<Role> listRole = user.getUserRoles();
		if (CollectionUtils.isNotEmpty(listRole)) {
			for (Role role : listRole) {
				UserRole ur = new UserRole();
				ur.setUserId(userId);
				ur.setRoleId(role.getId());
				
				super.save(ur);
			}
		}
		
	}

	@Override
	public User findUserById(Object id) throws Exception {

		User u = super.findById(id, User.class);
		String userId = u.getId();

		List<Org> listOrg = userOrgService.findOrgByUserId(userId);
		u.setUserOrgs(listOrg);
		
		List<Role> roleByUserId = userRoleMenuService.findRoleByUserId(userId);
		u.setUserRoles(roleByUserId);

		return u;
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
	public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object o) throws Exception {
		User user = (User) o;
		// ==执行分页查询
		// user.setFrameTableAlias("tu");
		// finder=Finder.getSelectFinder(User.class,"tu.*,tg.name as gradeName ").append(" tu,").append(Finder.getTableName(DicData.class)).append(" tg WHERE tu.gradeId=tg.id and tg.typekey='grade' ");

		finder = Finder.getSelectFinder(User.class).append(" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, user);
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);

		return queryForList;
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * 
	 * @param finder
	 *            为空则只查询 clazz表
	 * @param ftlurl
	 *            类表的模版宏
	 * @param page
	 *            分页对象
	 * @param clazz
	 *            要查询的对象
	 * @param o
	 *            querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl, Page page, Class<T> clazz, Object o) throws Exception {
		return super.findDataExportExcel(finder, ftlurl, page, clazz, o);
	}

	@Override
	@Caching(evict = { @CacheEvict(value = GlobalStatic.cacheKey, key = "'findRoleByUserId_'+#userId"), @CacheEvict(value = GlobalStatic.cacheKey, key = "'getRolesAsString_'+#userId"), @CacheEvict(value = GlobalStatic.cacheKey, key = "'findUserByRoleId_'+#userId"), @CacheEvict(value = GlobalStatic.cacheKey, key = "'findAllRoleAndMenu'") })
	public void updateRoleUser(String userId, String roleId) throws Exception {
		// 删除
		Finder finder = Finder.getDeleteFinder(UserRole.class).append(" WHERE user_id=:userId");

		finder.setParam("userId", userId);
		this.update(finder);

		// 添加
		String[] roleIds = roleId.split(",");

		if (ArrayUtils.isNotEmpty(roleIds)) {
			for (String str : roleIds) {
				if (StringUtils.isBlank(str)) {
					continue;
				}
				UserRole ur = new UserRole();
				ur.setUserId(userId);
				ur.setRoleId(str);

				super.save(ur);
			}
		}

	}
	
	@Override
	public void initSysUser(String s) {
		String[] arrayss = s.toString().split(";");
		for (String string : arrayss) {
			logger.info("这个:" + string);
			String[] array = string.split(":");
			for (int j = 0; j < array.length; j++) {
				try {
					String FourA = array[0];
					String bossNo = array[1];
					String 姓名 = array[2];
					String 地市 = array[3];
					String 所属 = array[4];
					String 电话 = array[5];
					String 营业厅编码;
					String 营业厅名称;
					String 区县编码;
					String 区县名称;
					String 营业厅地址;
					String 地市编码;
					String 地市名称;
					
					User userFilter = new User();
					userFilter.setDescription(bossNo);
					List<User> usersFilter = this.findListDataByFinder(null, null, User.class, userFilter);
					logger.info("usersFilter" + usersFilter.isEmpty() + "usersFilter" + usersFilter.size());
					if (usersFilter != null && !usersFilter.isEmpty() && usersFilter.size() != 0) {
						logger.info("当前用户存在【"+bossNo+"】");
					}else{
						logger.info("没有这个用户【"+bossNo+"】");
						SLoginNoQryResponse sLoginNoQryRes = sitechInterFace.searchOffice(bossNo);
						if (StringUtils.equals(sLoginNoQryRes.getReturnCode(), "0")) {
							营业厅编码 = sLoginNoQryRes.getOutData().getData().getGroupId();
							营业厅名称 = sLoginNoQryRes.getOutData().getData().getGroupName();

							SGroupTreeResponse sGroupTreeRes = sitechInterFace.searchOfficeDetail("ll1868", 营业厅编码);
							logger.info("searchOfficeDetail" + sGroupTreeRes.getReturnCode());
							logger.info("searchOfficeDetail营业厅编码" + 营业厅编码 + " searchOfficeDetail营业厅名称 " + 营业厅名称);
							if (StringUtils.equals(sGroupTreeRes.getReturnCode(), "0")) {
								区县编码 = sGroupTreeRes.getOutData().getBusiInfo().getParentGroupId();
								区县名称 = sGroupTreeRes.getOutData().getBusiInfo().getpRegionName();
								营业厅地址 = sGroupTreeRes.getOutData().getBusiInfo().getTownAddress();

								SGroupTreeResponse sGroupTreeRes2 = sitechInterFace.searchOfficeDetail("ll1868", 区县编码);
								if (StringUtils.equals(sGroupTreeRes2.getReturnCode(), "0")) {
									地市编码 = sGroupTreeRes2.getOutData().getBusiInfo().getParentGroupId();
									地市名称 = "";
									if (StringUtils.equals(地市编码, "10034")) {
										地市名称 = "太原";
									} else if (StringUtils.equals(地市编码, "10035")) {
										地市名称 = "大同";
									} else if (StringUtils.equals(地市编码, "10036")) {
										地市名称 = "阳泉";
									} else if (StringUtils.equals(地市编码, "10037")) {
										地市名称 = "长治";
									} else if (StringUtils.equals(地市编码, "10038")) {
										地市名称 = "晋城";
									} else if (StringUtils.equals(地市编码, "10039")) {
										地市名称 = "朔州";
									} else if (StringUtils.equals(地市编码, "10040")) {
										地市名称 = "忻州";
									} else if (StringUtils.equals(地市编码, "10041")) {
										地市名称 = "晋中";
									} else if (StringUtils.equals(地市编码, "10042")) {
										地市名称 = "临汾";
									} else if (StringUtils.equals(地市编码, "10043")) {
										地市名称 = "运城";
									} else if (StringUtils.equals(地市编码, "10044")) {
										地市名称 = "吕梁";
									}

									logger.info("searchOfficeDetail营业厅编码" + 营业厅编码 + " 营业厅名称 " + 营业厅名称 + "区县编码" + 区县编码 + " 区县名称" + 区县名称 + "地市编码:" + 地市编码 + "地市名称:" + 地市名称);

									Office offices = new Office();
									offices.setOfficeCode(营业厅编码);
									List<Office> odatas = officeService.findListDataByFinder(null, null, Office.class, offices);
									if (odatas.isEmpty() && odatas.size() == 0) {
										offices = new Office();
										offices.setOfficeCode(营业厅编码);
										offices.setOfficeName(营业厅名称);
										offices.setAddress(营业厅地址);
										offices.setRegionCode(地市编码);
										offices.setCountyCode(区县编码);
										offices.setState("1");
										officeService.save(offices);
									}

									Region region = new Region();
									region.setRegionCode(地市编码);
									List<Region> rdatas = regionService.findListDataByFinder(null, null, Region.class, region);
									if (rdatas.isEmpty() && rdatas.size() == 0) {
										region = new Region();
										region.setRegionCode(地市编码);
										region.setRegionName(地市名称);
										region.setState("1");
										regionService.save(region);
									}

									County county = new County();
									county.setCountyCode(区县编码);
									logger.info("区县编码区县编码:" + 区县编码);
									List<County> cdatas = countyService.findListDataByFinder(null, null, County.class, county);
									logger.info("区县编码区县编码:" + cdatas.size());
									if (cdatas.isEmpty() && cdatas.size() == 0) {
										county = new County();
										county.setCountyCode(区县编码);
										county.setCountyName(区县名称);
										county.setRegionCode(地市编码);
										county.setState("1");
										countyService.save(county);
									}

									User user = new User();
									user.setAccount(FourA);
									user.setDescription(bossNo);
									logger.info("ssssssssssssssssss" + odatas.isEmpty() + "   " + odatas.size());
									List<User> udatas = this.findListDataByFinder(null, null, User.class, user);
									if (udatas.isEmpty() && udatas.size() == 0) {
										user = new User();
										user.setName(姓名);
										user.setAccount(FourA);
										user.setMobile(电话);
										user.setDescription(bossNo);
										user.setWorkno(bossNo);
										user.setAddress(所属);
										user.setState("1");
										user.setPassword(MD5Utils.encoderByMd5With32Bit("111111"));
										user.setCreateDate(new Date());
										this.save(user);
										//logger.info("user11122" + user.getId());
									}

									user = new User();
									user.setAccount(FourA);
									user.setDescription(bossNo);
									List<User> udatas1 = this.findListDataByFinder(null, null, User.class, user);
									User user1 = udatas1.get(0);

									UserOffice userOffice = new UserOffice();
									userOffice.setUserId(user1.getId());
									List<UserOffice> uodatas = userOfficeService.findListDataByFinder(null, null, UserOffice.class, userOffice);
									logger.info("wwwwwwwwwww" + user1.getId());
									logger.info(uodatas.size() + "");

									if (StringUtils.isNotBlank(user1.getId()) && uodatas.isEmpty() && uodatas.size() == 0) {
										userOffice.setCountyCode(区县编码);
										userOffice.setOfficeCode(营业厅编码);
										userOffice.setRegionCode(地市编码);
										userOffice.setUserId(user1.getId());
										userOfficeService.save(userOffice);
										
										UserRole userRole = new UserRole();
										userRole.setUserId(user1.getId());
										userRole.setRoleId("65ed2b1f2e8c40ebb123013ddc5531f4");
										userRoleService.save(userRole);
									}
									
									UserBusiness userBusiness = new UserBusiness();
									userBusiness.setUserId(user1.getId());
									List<UserBusiness> userBusinesses = userBuesinessService.findListDataByFinder(null, null, UserBusiness.class, userBusiness);
									if (userBusinesses.isEmpty() && userBusinesses.size() == 0) {
										userBusiness = new UserBusiness();
										userBusiness.setUserId(user1.getId());
										userBusiness.setBusId("17895c2fafed47debd62786517729bf0");
										userBuesinessService.save(userBusiness);
										userBusiness = new UserBusiness();
										userBusiness.setUserId(user1.getId());
										userBusiness.setBusId("2ea000ab9d2e4f47aeaa789365bc2cca");
										userBuesinessService.save(userBusiness);
										userBusiness = new UserBusiness();
										userBusiness.setUserId(user1.getId());
										userBusiness.setBusId("3731ff20659042c39421f27481e431f7");
										userBuesinessService.save(userBusiness);
										userBusiness = new UserBusiness();
										userBusiness.setUserId(user1.getId());
										userBusiness.setBusId("61fb81d6ec3049ea8aa413fb7a802dd1");
										userBuesinessService.save(userBusiness);
										userBusiness = new UserBusiness();
										userBusiness.setUserId(user1.getId());
										userBusiness.setBusId("76045e31c4e14c5782a4ccc89e9b776f");
										userBuesinessService.save(userBusiness);
										userBusiness = new UserBusiness();
										userBusiness.setUserId(user1.getId());
										userBusiness.setBusId("989b573a802a4521aaabbfdbef08b2a7");
										userBuesinessService.save(userBusiness);
										userBusiness = new UserBusiness();
										userBusiness.setUserId(user1.getId());
										userBusiness.setBusId("c0019e9b836d4eddb9f6e34aa19a64c6");
										userBuesinessService.save(userBusiness);
										userBusiness = new UserBusiness();
										userBusiness.setUserId(user1.getId());
										userBusiness.setBusId("ff8080813ba37872013ba416d20e0083");
										userBuesinessService.save(userBusiness);
									}
									

									logger.info("营业厅编码yytbm" + 营业厅编码 + " 营业厅名称 yytmc" + 营业厅名称 + "区县编码qxbm" + 区县编码 + " 区县名称qxmc" + 区县名称 + "地市编码:" + 地市编码 + "地市名称:" + 地市名称);

								} else {
									logger.info("bossno:" + bossNo + "存在问题3");
								}
							} else {
								logger.info("bossno:" + bossNo + "存在问题2");
							}
						} else {
							logger.info("bossno:" + bossNo + "存在问题1");
						}
					}
				} catch (Exception e) {
					// logger.info("问题存在第"+i+"行");
					e.printStackTrace();
				}

			}
		}

	}

	/** 
	 * 通过4a查询工号，营业厅区县地市信息
	 */
	@Override
	public boolean validateUser(User u) {
		boolean vaildateFlag = false;
		
		logger.info("validateUser() start ..."+ " "+u.getAccount());
		CustomaryInfoResponse customaryInfoResponse = softstoneInterface.search4A2BossNo(u.getAccount());
		if(StringUtils.equals(customaryInfoResponse.getRetCode(), "00")){
			u.setAccount(customaryInfoResponse.getRetMsg().getUid());
			u.setWorkno(customaryInfoResponse.getRetMsg().getBossAcc());
			u.setName(customaryInfoResponse.getRetMsg().getName());
			u.setMobile(customaryInfoResponse.getRetMsg().getPhoneNum());
			u.setState(StringUtils.equals(customaryInfoResponse.getRetMsg().getStatus(), "0")? "1":"0");
			u.setPassword(MD5Utils.encoderByMd5With32Bit(u.getPassword()));
			
			SLoginNoQryResponse sLoginNoQryRes = sitechInterFace.searchOffice(u.getWorkno()); 
			if (StringUtils.equals(sLoginNoQryRes.getReturnCode(), "0")) {
				String 营业厅编码;
				String 营业厅名称;
				String 区县编码;
				String 区县名称;
				String 营业厅地址;
				String 地市编码;
				String 地市名称;
				
				营业厅编码 = sLoginNoQryRes.getOutData().getData().getGroupId();
				营业厅名称 = sLoginNoQryRes.getOutData().getData().getGroupName();

				SGroupTreeResponse sGroupTreeRes = sitechInterFace.searchOfficeDetail("ll1868", 营业厅编码);
				logger.info("searchOfficeDetail" + sGroupTreeRes.getReturnCode());
				logger.info("searchOfficeDetail营业厅编码" + 营业厅编码 + " searchOfficeDetail营业厅名称 " + 营业厅名称);
				if (StringUtils.equals(sGroupTreeRes.getReturnCode(), "0")) {
					区县编码 = sGroupTreeRes.getOutData().getBusiInfo().getParentGroupId();
					区县名称 = sGroupTreeRes.getOutData().getBusiInfo().getpRegionName();
					营业厅地址 = sGroupTreeRes.getOutData().getBusiInfo().getTownAddress();

					SGroupTreeResponse sGroupTreeRes2 = sitechInterFace.searchOfficeDetail("ll1868", 区县编码);
					if (StringUtils.equals(sGroupTreeRes2.getReturnCode(), "0")) {
						地市编码 = sGroupTreeRes2.getOutData().getBusiInfo().getParentGroupId();
						地市名称 = "";
						if (StringUtils.equals(地市编码, "10034")) {
							地市名称 = "太原";
						} else if (StringUtils.equals(地市编码, "10035")) {
							地市名称 = "大同";
						} else if (StringUtils.equals(地市编码, "10036")) {
							地市名称 = "阳泉";
						} else if (StringUtils.equals(地市编码, "10037")) {
							地市名称 = "长治";
						} else if (StringUtils.equals(地市编码, "10038")) {
							地市名称 = "晋城";
						} else if (StringUtils.equals(地市编码, "10039")) {
							地市名称 = "朔州";
						} else if (StringUtils.equals(地市编码, "10040")) {
							地市名称 = "忻州";
						} else if (StringUtils.equals(地市编码, "10041")) {
							地市名称 = "晋中";
						} else if (StringUtils.equals(地市编码, "10042")) {
							地市名称 = "临汾";
						} else if (StringUtils.equals(地市编码, "10043")) {
							地市名称 = "运城";
						} else if (StringUtils.equals(地市编码, "10044")) {
							地市名称 = "吕梁";
						}else{
							logger.info("警告：【无匹配地市编码】" + 地市编码);
							return vaildateFlag;
						}
						//更新数据库
						vaildateFlag = updateUserSomeThing4DB(u,营业厅编码, 营业厅名称,区县编码,区县名称,营业厅地址,地市编码,地市名称);
					}else{
						logger.info("警告：【调用SGroupTree异常2】");
					}
				}
			}else{
				logger.info("警告：【调用SGroupTree异常1】");
			}
			
		}else{
			logger.info("警告：【通过4A查询bossno错误】");
			throw new BaseRuntimeException("通过4A查询bossno错误");
		}
		return vaildateFlag;
	}



	/**
	 * 操作数据库更新用户信息
	 * @param u
	 * @param 营业厅编码
	 * @param 营业厅名称
	 * @param 区县编码
	 * @param 区县名称
	 * @param 营业厅地址
	 * @param 地市编码
	 * @param 地市名称
	 * @return
	 */
	private boolean updateUserSomeThing4DB(User u,String 营业厅编码, String 营业厅名称,String 区县编码,String 区县名称,String 营业厅地址,String 地市编码,String 地市名称) {
		logger.info("更新数据库");
		boolean updateUserSomeThing4DB = false;
		try {
			User userFilter = new User();
			userFilter.setAccount(u.getAccount());
			List<User> usersFilter = this.findListDataByFinder(null, null, User.class, userFilter);
			logger.info("usersFilter" + usersFilter.isEmpty() + "usersFilter" + usersFilter.size());
			if (usersFilter != null && !usersFilter.isEmpty() && usersFilter.size() != 0) {
				logger.info("提示：当前用户(4A)存在【"+u.getAccount()+"】");
				//更新用户的关联信息
				u.setId(usersFilter.get(0).getId());
				
				
				updateUserSomeThing4DB = updateUserSomething(u,营业厅编码, 营业厅名称, 区县编码, 区县名称, 营业厅地址, 地市编码, updateUserSomeThing4DB);
			}else{
				logger.info("提示：当前用户(4A)不存在【"+u.getAccount()+"】");
				//新增用户的关联信息
				updateUserSomeThing4DB = addUserSomething(u, 营业厅编码, 营业厅名称, 区县编码, 区县名称, 营业厅地址, 地市编码);
			}
		} catch (Exception e) {
			logger.info("警告：【操作数据库异常】");
			e.printStackTrace();
		}
		return updateUserSomeThing4DB;
	}

	private boolean addUserSomething(User u, String 营业厅编码, String 营业厅名称, String 区县编码, String 区县名称, String 营业厅地址, String 地市编码) throws Exception {
		
		User user = new User();
		user.setName(u.getName());
		user.setAccount(u.getAccount());
		user.setMobile(u.getMobile());
		user.setWorkno(u.getWorkno());
		user.setState(u.getState());
		user.setPassword(u.getPassword());
//		logger.info("u.getPassword()"+u.getPassword());
//		logger.info("u.getPassword()"+MD5Utils.encoderByMd5With32Bit(u.getPassword()));
		
		
		user.setCreateDate(new Date());
		user.setAddress(u.getAddress());
		user.setState("1");
		Object o = this.save(user);
		logger.info("【创建用户：新增用户】"+o);
		
		Office office = new Office();
		office.setOfficeCode(营业厅编码);
		List<Office> odatas = officeService.findListDataByFinder(null, null, Office.class, office);
		if (odatas.isEmpty() && odatas.size() == 0) {
			office = new Office();
			office.setOfficeCode(营业厅编码);
			office.setOfficeName(营业厅名称);
			office.setAddress(营业厅地址);
			office.setRegionCode(地市编码);
			office.setCountyCode(区县编码);
			office.setState("1");
			officeService.save(office);
			logger.info("【创建用户：新增营业厅操作】");
		}else{
			office = odatas.get(0);
			office.setOfficeCode(营业厅编码);
			office.setOfficeName(营业厅名称);
			office.setAddress(营业厅地址);
			office.setRegionCode(地市编码);
			office.setCountyCode(区县编码);
			office.setState("1");
			officeService.update(office);
			logger.info("【创建用户：更新营业厅操作】");
		}
		
		County county = new County();
		county.setCountyCode(区县编码);
		List<County> cdatas = countyService.findListDataByFinder(null, null, County.class, county);
		if (cdatas.isEmpty() && cdatas.size() == 0) {
			county = new County();
			county.setCountyCode(区县编码);
			county.setCountyName(区县名称);
			county.setRegionCode(地市编码);
			county.setState("1");
			countyService.save(county);
		}
		
		if(o != null){
			String userId = (String)o;
			UserOffice userOffice = new UserOffice();
			userOffice.setUserId(userId);
			List<UserOffice> uodatas = userOfficeService.findListDataByFinder(null, null, UserOffice.class, userOffice);
			logger.info("wwwwwwwwwww" + userId);
			logger.info(uodatas.size() + "");
			
			if (StringUtils.isNotBlank(userId) && uodatas.isEmpty() && uodatas.size() == 0) {
				userOffice.setCountyCode(区县编码);
				userOffice.setOfficeCode(营业厅编码);
				userOffice.setRegionCode(地市编码);
				userOffice.setUserId(userId);
				userOffice.setPost("2002");
				userOfficeService.save(userOffice);
				
				UserRole userRole = new UserRole();
				userRole.setUserId(userId);
				userRole.setRoleId("65ed2b1f2e8c40ebb123013ddc5531f4");
				userRoleService.save(userRole);
			}
			
			UserBusiness userBusiness = new UserBusiness();
			userBusiness.setUserId(userId);
			List<UserBusiness> userBusinesses = userBuesinessService.findListDataByFinder(null, null, UserBusiness.class, userBusiness);
			if (userBusinesses.isEmpty() && userBusinesses.size() == 0) {
				List<Business> businesses = buesinessService.findListDataByFinder(null, null, Business.class, new Business()); 
				for (Business business : businesses) {
					if(!StringUtils.equals(business.getBusType(), "200")){
						userBusiness = new UserBusiness();
						userBusiness.setUserId(userId);
						userBusiness.setBusId(business.getId());
						userBuesinessService.save(userBusiness);
					}
				}
			}
		}
		return true;
	}

	private boolean updateUserSomething(User nowUser,String 营业厅编码, String 营业厅名称, String 区县编码, String 区县名称, String 营业厅地址, String 地市编码, boolean updateUserSomeThing4DB) throws Exception {
		if(this.update(nowUser) == 1){
			UserOffice userOffice = new UserOffice();
			userOffice.setUserId(nowUser.getId());
			List<UserOffice> nowUserOffices = userOfficeService.findListDataByFinder(null, null, UserOffice.class, userOffice);
			if ( !nowUserOffices.isEmpty() && nowUserOffices.size() != 0) {
				UserOffice nowUserOffice = nowUserOffices.get(0);
				if(StringUtils.equals(nowUserOffice.getOfficeCode(), 营业厅编码)){
					updateUserSomeThing4DB = true;
				}else{
					nowUserOffice.setOfficeCode(营业厅编码);
					nowUserOffice.setCountyCode(区县编码);
					nowUserOffice.setRegionCode(地市编码);
					if(userOfficeService.update(nowUserOffice)  == 1){
						
						Office office = new Office();
						office.setOfficeCode(营业厅编码);
						List<Office> odatas = officeService.findListDataByFinder(null, null, Office.class, office);
						if (odatas.isEmpty() && odatas.size() == 0) {
							logger.info("提示：【增加营业厅】");
							office = new Office();
							office.setOfficeCode(营业厅编码);
							office.setOfficeName(营业厅名称);
							office.setAddress(营业厅地址);
							office.setRegionCode(地市编码);
							office.setCountyCode(区县编码);
							office.setState("1");
							officeService.save(office);
						}
						
						County county = new County();
						county.setCountyCode(区县编码);
						List<County> cdatas = countyService.findListDataByFinder(null, null, County.class, county);
						if (cdatas.isEmpty() && cdatas.size() == 0) {
							logger.info("提示：【增加区县】");
							county = new County();
							county.setCountyCode(区县编码);
							county.setCountyName(区县名称);
							county.setRegionCode(地市编码); 
							county.setState("1");
							countyService.save(county);
						}
						updateUserSomeThing4DB = true;
						
					}else{
						logger.info("警告：【操作营业厅用户关系异常】");
						throw new BaseRuntimeException("操作营业厅用户关系异常");
					}
				}
			}else{
				userOffice = new UserOffice();
				userOffice.setUserId(nowUser.getId());
				List<UserOffice> uodatas = userOfficeService.findListDataByFinder(null, null, UserOffice.class, userOffice);
				
				if (StringUtils.isNotBlank(nowUser.getId()) && uodatas.isEmpty() && uodatas.size() == 0) {
					userOffice.setCountyCode(区县编码);
					userOffice.setOfficeCode(营业厅编码);
					userOffice.setRegionCode(地市编码);
					userOffice.setUserId(nowUser.getId());
					userOffice.setPost("2002");
					userOfficeService.save(userOffice);
				}
				
				Office office = new Office();
				office.setOfficeCode(营业厅编码);
				List<Office> odatas = officeService.findListDataByFinder(null, null, Office.class, office);
				if (odatas.isEmpty() && odatas.size() == 0) {
					logger.info("提示：【增加营业厅】");
					office = new Office();
					office.setOfficeCode(营业厅编码);
					office.setOfficeName(营业厅名称);
					office.setAddress(营业厅地址);
					office.setRegionCode(地市编码);
					office.setCountyCode(区县编码);
					office.setState("1");
					officeService.save(office);
				}
				
				County county = new County();
				county.setCountyCode(区县编码);
				List<County> cdatas = countyService.findListDataByFinder(null, null, County.class, county);
				if (cdatas.isEmpty() && cdatas.size() == 0) {
					logger.info("提示：【增加区县】");
					county = new County();
					county.setCountyCode(区县编码);
					county.setCountyName(区县名称);
					county.setRegionCode(地市编码);
					county.setState("1");
					countyService.save(county);
				}
				
				updateUserSomeThing4DB = true;
				
			}
			
			UserRole userRole = new UserRole();
			userRole.setUserId(nowUser.getId());
			List<UserRole> userRoles = userRoleService.findListDataByFinder(null, null, UserRole.class, userRole);
			if (userRoles.isEmpty() && userRoles.size() == 0) {
				logger.info("提示：【增加营业员权限】");
				userRole = new UserRole();
				userRole.setUserId(nowUser.getId());
				userRole.setRoleId("65ed2b1f2e8c40ebb123013ddc5531f4");
				userRoleService.save(userRole);
			}
			
			
			logger.info("绑定业务和地市、用户");
			Business dmpd = new Business();
			dmpd.setState("1");
			dmpd.setUpDown("1");
			List<Business> businesses = buesinessService.findListDataByFinder(null, null, Business.class,dmpd ); 
			if(businesses != null && !businesses.isEmpty()){
				for (Business business : businesses) {
					if(!StringUtils.equals(business.getBusType(), "200")){
						logger.info("business"+business.getId());
						UserBusiness userBusiness = new UserBusiness();
						userBusiness.setUserId(nowUser.getId());
						userBusiness.setBusId(business.getId());
						List<UserBusiness> userBusinesses = userBuesinessService.findListDataByFinder(null, null, UserBusiness.class, userBusiness);
						if(userBusinesses==null || userBusinesses.isEmpty()){
							logger.info("userBusiness"+business.getId());
							userBusiness = new UserBusiness();
							userBusiness.setUserId(nowUser.getId());
							userBusiness.setBusId(business.getId());
							userBuesinessService.save(userBusiness);
						}
						Region r = new Region();
						r.setRegionCode(地市编码);
						List<Region> rs = regionService.findListDataByFinder(null, null, Region.class, r);
						if(rs!= null && !rs.isEmpty()){
							String regionId = rs.get(0).getId();
							RegionBusiness rb = new RegionBusiness();
							rb.setBusId(business.getId());
							rb.setRegionId(regionId);
							List<RegionBusiness> rbs = regionBusinessService.findListDataByFinder(null, null, RegionBusiness.class, rb);
							if(rbs == null || rbs.isEmpty()){
								logger.info("userBusiness"+business.getId());
								rb = new RegionBusiness();
								rb.setBusId(business.getId());
								rb.setRegionId(regionId);
								regionBusinessService.save(rb);
							}
						}
					}
				}
			}
			
			logger.info("营销活动业务绑定用户");
			Business activityBusiness = new Business();
			activityBusiness.setBusType("200");
			activityBusiness.setUpDown("1");
			activityBusiness.setState("1");
//			UserOfficeDTO userOfficeDTO = userOfficeService.findUserOfficeDTOByUserId(id)
			List<Business> activityBusinessList = buesinessService.findListDataByFinder(null, null, Business.class,dmpd ); 
			if(activityBusinessList != null && !activityBusinessList.isEmpty()){
				for (Business business : businesses) {
					if(StringUtils.equals(business.getBusType(), "200")){
						//先说全省
						if(StringUtils.equals(business.getGroupId(), "100")){
							//先查询一下是否绑定，如果没有绑定，就进行绑定
							UserBusiness userBusiness = new UserBusiness();
							userBusiness.setUserId(nowUser.getId());
							userBusiness.setBusId(business.getId());
							List<UserBusiness> userBusinesses = userBuesinessService.findListDataByFinder(null, null, UserBusiness.class, userBusiness);
							if(userBusinesses==null || userBusinesses.isEmpty()){
								logger.info("用户业务不存在，需要进行绑定nowUser.getId():"+nowUser.getId()+"business.getId():"+business.getId());
								userBusiness = new UserBusiness();
								userBusiness.setUserId(nowUser.getId());
								userBusiness.setBusId(business.getId());
								userBuesinessService.save(userBusiness);
							}
							logger.info("用户业务存在，不需要进行绑定");
						}
						//后说地市
						if(StringUtils.equals(business.getGroupId(), 地市编码)){
							//先查询一下是否绑定，如果没有绑定，就进行绑定
							UserBusiness userBusiness = new UserBusiness();
							userBusiness.setUserId(nowUser.getId());
							userBusiness.setBusId(business.getId());
							List<UserBusiness> userBusinesses = userBuesinessService.findListDataByFinder(null, null, UserBusiness.class, userBusiness);
							if(userBusinesses==null || userBusinesses.isEmpty()){
								logger.info("用户业务不存在，需要进行绑定nowUser.getId():"+nowUser.getId()+"business.getId():"+business.getId());
								userBusiness = new UserBusiness();
								userBusiness.setUserId(nowUser.getId());
								userBusiness.setBusId(business.getId());
								userBuesinessService.save(userBusiness);
							}
							logger.info("用户业务存在，不需要进行绑定");
						}
					}
				}
			}
			
			
			//同步专属业务
			logger.info("提示：【同步专属业务开始】");
			String userId = SessionUser.getUserId();
			List<Business> busdata = specialQrcodeService.findBusByUserId(userId);
			List<Goods> goodsdata = specialQrcodeService.findGoodsbyUserId(userId);
			SpecialQrcode specQrcode = new SpecialQrcode();
			specQrcode.setBelongUser(userId);
			List<SpecialQrcode> dataDTO = specialQrcodeService.findListDataByFinder(null, null, SpecialQrcode.class, specQrcode);
			//专属业务
			if(dataDTO.size()==0 && busdata.size()>0){
				logger.info("提示：【第一次进入专属业务，保存用户已绑定业务到专属业务表】");
				for(int i=0; i<goodsdata.size();i++){
					if(StringUtils.isNotBlank(specQrcode.getId())){
						specQrcode = new SpecialQrcode();
					}
					specQrcode.setBelongUser(userId);
					specQrcode.setBusId(goodsdata.get(i).getBusId());
					specQrcode.setFeeCode(goodsdata.get(i).getFeeCode());
					specQrcode.setType("1");
					specialQrcodeService.save(specQrcode);
				}
				logger.info("提示：【第一次进入专属业务，保存用户已绑定业务到专属业务表结束】");
			}else{
			//用户有新増的业务
				try {
					logger.info("提示：【用户有新増的业务】");
					Map<String,SpecialQrcode> map = new HashMap<String,SpecialQrcode>();
					Map<String,Goods> map2 = new HashMap<String,Goods>();
					for(int i=0; i<dataDTO.size();i++){
						map.put(dataDTO.get(i).getFeeCode(), dataDTO.get(i));
					}
					for (int j = 0; j < goodsdata.size(); j++) {
						Goods good= goodsdata.get(j);
						map2.put(good.getFeeCode(),good);
						boolean flag=map.containsKey(good.getFeeCode());
						if(!flag){
							SpecialQrcode	specQrcode1 = new SpecialQrcode();
							specQrcode1.setBelongUser(userId);
							specQrcode1.setBusId(good.getBusId());
							specQrcode1.setFeeCode(good.getFeeCode());
							specQrcode1.setType("1");
							specialQrcodeService.save(specQrcode1);
						}
					}
					for (Map.Entry<String, SpecialQrcode> entry : map.entrySet()) {
						   if(!map2.containsKey(entry.getKey())){
							   SpecialQrcode specQrcode1=entry.getValue();
							   specialQrcodeService.deleteByEntity(specQrcode1);
						   }
					}
					logger.info("提示：【用户有新増的业务，保存到专属业务表结束】");
				} catch (Exception e) {
					logger.info("提示：【用户有新増的业务，保存到专属业务表失败】");
				}
			}
			
			
			logger.info("提示：【更新用户成功】");
		}else{
			logger.info("警告：【更新用户失败】");
			updateUserSomeThing4DB = false;
		}
		return updateUserSomeThing4DB;
	}
	/**
	 * 判断工号是否可用
	 */
	@Override
	public boolean availableUser(User u) {
		boolean vaildateFlag = false;
		
		logger.info("validateUser() start ...");
		CustomaryInfoResponse customaryInfoResponse = softstoneInterface.search4A2BossNo(u.getAccount());
		if (StringUtils.equals(customaryInfoResponse.getRetCode(), "00") &&
				StringUtils.equals(customaryInfoResponse.getRetMsg().getStatus(), "0")) {
			vaildateFlag = true;
		} else {
			logger.info("工号不可用");
		}
		
		return vaildateFlag;
	}

	//查询全省的营业员
	
	@Override
	public List<User> findAllUser() throws Exception {
			List<User> userList = new ArrayList<User>();
			Finder finder = Finder.getSelectFinder(User.class);
			finder.append("where state =:state ").setParam("state", Constant.ONE);
			logger.info("------查询全部地市start===");
			userList = super.queryForList(finder, User.class);
			if (CollectionUtils.isEmpty(userList)) {
				return null;
			}
			return userList;
	
	}

	@Override
	public void deleteUserById(String id) throws Exception {
		User u = super.findById(id, User.class);
		String deleteSql = "where USER_ID in(select id from T_USER where account=:userAaccount)";
		//删除用户与角色关系
		logger.info("删除t_user_role信息==========start");
		Finder userRolefinder = Finder.getDeleteFinder(UserRole.class).append(deleteSql);
		userRolefinder.setParam("userAaccount", u.getAccount());
		this.update(userRolefinder);
		logger.info("删除t_user_role信息==========end");
		
		//删除用户和营业厅关系
		logger.info("删除t_user_office信息==========start");
		Finder userOfficefinder = Finder.getDeleteFinder(UserOffice.class).append(deleteSql);
		userOfficefinder.setParam("userAaccount", u.getAccount());
		this.update(userOfficefinder);
		logger.info("删除t_user_office信息==========end");
		
		//删除用户和活动关系
		logger.info("删除t_user_business信息==========start");
		Finder userBusinessfinder = Finder.getDeleteFinder(UserBusiness.class).append(deleteSql);
		userBusinessfinder.setParam("userAaccount", u.getAccount());
		this.update(userBusinessfinder);
		logger.info("删除t_user_business信息==========end");
		
		//删除用户
		logger.info("删除t_user信息==========start");
		this.deleteById(id, User.class);
		logger.info("删除t_user信息==========end");
	}

	@Override
	public List<Business> findBusiness(String userCode) throws Exception {
		List<Business> busList = new ArrayList<Business>();
		Finder finder = new Finder();
		finder.append("select a.* from t_business a where a.id in (select b.bus_id from t_user_business b where b.user_id=:userId)");
		finder.setParam("userId", userCode);
		busList = super.queryForList(finder, Business.class);
		if (CollectionUtils.isEmpty(busList)) {
			return null;
		}
		return busList;
	}

	@Override
	public List<User> findThisRoleUser(Role role,String flag) throws Exception {
		Finder finder = new Finder();
		finder.append("select t1.id as id,t1.name,t1.MOBILE from t_user t1 ,t_user_role t2 ,t_role t3 ");
		//第一次
		if(StringUtils.equals(flag, "1")){
//			finder.append(", (select distinct(belong_user) from t_special_qrcode) t4 ");
			finder.append(",  (select distinct (belong_user) from t_qrcode) t4 ");
		}
		finder.append("where 1=1 ");
		finder.append("and t1.state = 1 ");
		finder.append("and t1.id = t2.user_id ");
		finder.append("and t2.role_id = t3.id ");
		//第一次
		if(StringUtils.isNotBlank(role.getCode())){
			finder.append("and t3.code =:code ");
			finder.setParam("code",role.getCode());
		}
		if(StringUtils.equals(flag, "1")){
//			finder.append("and t4.belong_user = t1.id ");
			finder.append("and t4.belong_user = t1.workno ");
		}
		Page p = new Page();
		p.setPageSize(50000);
		List<User> us =  this.queryForList(finder,User.class,p);
		return us;
	}

}
