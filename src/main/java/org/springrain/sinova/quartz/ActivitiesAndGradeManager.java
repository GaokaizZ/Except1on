package org.springrain.sinova.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springrain.frame.common.BaseLogger;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.RegionBusiness;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.sinova.interfaces.sitech.sGetAction.MarketingActionResponse;
import org.springrain.sinova.interfaces.sitech.sGetAction.MarkingActionInfo;
import org.springrain.sinova.interfaces.sitech.sGetAction.WsGetActionRequest;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.MeanContent;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.WsGetSaleMeansContentReponse;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.req.WsGetSaleMeansContentRequest;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IGoodsService;
import org.springrain.sinova.service.IRegionBusinessService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.IUserBusinessService;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.system.entity.User;
import org.springrain.system.service.IUserService;

@Component
public class ActivitiesAndGradeManager extends BaseLogger {
	// 查询标示
	private static String FLAG5 = "5";
	private static String FLAG3 = "3";
	// 省份标示
	private static String PROVINCE = "10011";
	// 省份标示
	private static String CHANNELTYPE = "11";
	// 是否登录标示 Y未登录 N已经登录
	private static String LOGINFLAG = "Y";
	@Resource
	private IRegionService regionService;
	@Resource
	private ISitechInterface sitechInterFace;
	@Resource
	private IGoodsService goodsService;
	@Resource
	private IBusinessService businessService;
	@Resource
	private IUserBusinessService userBusinessService;
	@Resource
	private IRegionBusinessService regionBusinessService;
	@Resource
	private IUserOfficeService userOfficeService;


	/**
	 * 初始化活动档次数据，每天晚上1点开始跑定时器
	 */

//	@Scheduled(cron = "0 0/3 * * * ? ")//每3分钟执行一次
//	@Scheduled(cron = "0 0 0/1 * * ? ")//每3小时执行一次
//	@Scheduled(cron = "0 30 22  * * ? ")//每天晚上8点55分开始执行
	@Scheduled(cron = "0 0 0 * * ? ")//每天晚上8点55分开始执行
	public void initActivitiesAndGrade() {
		logger.info("【提示：定时器处理活动】+ initActivitiesAndGrade() start ...");
		Realm r = new IniRealm();
		DefaultSecurityManager s = new DefaultSecurityManager(r);
		SecurityUtils.setSecurityManager(s);
		
		// 拼接地市code
		List<String> reginCodeList = makeReginList();
		try {
			//操作活动
			WsGetActionRequest request = new WsGetActionRequest();
			
			request.setChnType(CHANNELTYPE);
			// 查询全省以及地市的活动之后进行入库的操作，在入库之前要查询 查询的条件是BusType为200 这就是营销活动
			Business business = new Business();
			business.setBusType("200");
			List<Business> businesses4DB = businessService.findByBus(business);
			java.util.Map<String, Business> busMap4DB = new HashMap<String, Business>();
			if (businesses4DB.size() > 0) {
				for (Business bus : businesses4DB) {
					//下架所有活动
					bus.setUpDown("0");
					businessService.update(bus);
					//活动入map
					busMap4DB.put(bus.getBusCode(), bus);
					logger.info("库中活动" + bus.getBusCode() + " " + bus.getBusName());
				}
			}
			logger.info("【查询数据库的活动结果的长度】" + busMap4DB.size());
			// 遍历全省和地市查询地市活动先入表T_BUSINESS业务表
			for (int i = 0; i < reginCodeList.size(); i++) {
				if(reginCodeList.get(i) == "100"){
					request.setFlag(FLAG5);
				}else{
					request.setFlag(FLAG3);
				}
				logger.info("==========【城市编码】===========" + reginCodeList.get(i));
				request.setRegionCode(getReginCode(reginCodeList.get(i)));
				MarketingActionResponse marketingActionResponse = sitechInterFace.queryWsGetAction(request);
				List<MarkingActionInfo> actions = marketingActionResponse.getActList();
				if(null!=actions && actions.size()>0 ){
					this.makeBusiness(actions, reginCodeList.get(i), busMap4DB);
				}
			}
			
			
			//1.操作有效的档次,遍历查询出来的活动操作档次
			makeGoods();
			
			
			//2.建立活动档次的有效无效关系
			makeActionMeansState();
			
			//3.把已经下架的营销活动下的档次全部下架
			downActionMeansState();
			
			//4.遍历无效活动将t_user_bussiness中数据剔除
			removeInvalidUserBusiness();
			
			//5.绑定当前地市用户与活动关系关系
			
				reginCodeList = new ArrayList<String>();
				 reginCodeList.add("10034"); // 太原
				 reginCodeList.add("10035"); // 大同
				 reginCodeList.add("10036"); // 阳泉
				 reginCodeList.add("10037"); // 长治
				 reginCodeList.add("10038"); // 晋城
				 reginCodeList.add("10039"); // 朔州
				 reginCodeList.add("10040"); // 忻州
				 reginCodeList.add("10041"); // 晋中
				 reginCodeList.add("10042"); // 临汾
				 reginCodeList.add("10043"); // 运城
				 reginCodeList.add("10044"); // 吕梁
				try {
					for (int i = 0; i < reginCodeList.size(); i++) {
						makeUserBusiness(reginCodeList.get(i));
					}
				} catch (Exception e) {
					logger.info("【警告：4绑定当前地市用户与活动关系关系】");
					e.printStackTrace();
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【警告：定时器处理活动档次异常】");

		}

	}

	private void makeUserBusiness(String reginCode) throws Exception {
		
		logger.info("绑定当前地市用户与活动关系关系" );

		List<Business> bs = null;
		List<User> us = null;
		
		//1 查询当前地市的所有活动
		Region region = new Region();
		region.setRegionCode(reginCode);
		List<Region> regions = regionBusinessService.findListDataByFinder(null, null, Region.class, region);
		if(regions != null && !regions.isEmpty()){
			RegionBusiness regionBusiness = new RegionBusiness();
			logger.info("当前操作的id是："+regions.get(0).getId());
			regionBusiness.setRegionId(regions.get(0).getId());
			Business business = new Business();
			business.setBusType("200");
			business.setUpDown("1");
			bs = regionBusinessService.findListDataByRegionBusiness(null, null, regionBusiness, business);
		}else{
			logger.info("【警告：无法查询到地市编码对应的数据】"+reginCode);
		}
		logger.info("bu"+(bs== null?"0":bs.size()));
		//2查询当前地市的所有营业员
		UserOffice userOffice = new UserOffice();
		userOffice.setRegionCode(reginCode);
		us = userOfficeService.findOfficeUserDTOByOffice(userOffice);
		logger.info("us"+(us== null?"0":us.size()));
		//3绑定
		for (User user : us) {
			for (Business business : bs) {
				
				UserBusiness ub = new UserBusiness();
				ub.setBusId(business.getId());
				ub.setUserId(user.getId());
				try {
					List<UserBusiness> userBusinesses =  userBusinessService.findListDataByFinder(null, null, UserBusiness.class, ub);
					if(userBusinesses.isEmpty()){
						//绑定不存在的关系
						logger.info("绑定关系" + ub.getBusId() + " " + ub.getUserId());
						userBusinessService.save(ub); 
					}else{
							logger.info("不绑定关系" + ub.getBusId() + " " + ub.getUserId());
							logger.info("关系存在，不进行绑定");
					}
				} catch (Exception e) {
					logger.info("【警告：绑定用户与业务异常】");
					e.printStackTrace();
				}
			}
		}
	}

	
	private void removeInvalidUserBusiness() throws Exception {
		logger.info("遍历无效活动将t_user_bussiness中数据剔除");
		Business queryBus= new Business();
		queryBus.setBusType("200");
		queryBus.setUpDown("0");
		List<Business> businessesList = businessService.findByBus(queryBus);
		for (Business business:businessesList) {
			userBusinessService.deleteByBusId(business.getId());
		}
	}

	private void makeActionMeansState() throws Exception {
		logger.info("建立活动档次的有效无效关系");
		Business queryBus= new Business();
		queryBus.setBusType("200");
		queryBus.setUpDown("1");
		List<Business> businessesList = businessService.findByBus(queryBus);
		for (Business business:businessesList) {
			Goods goods = new Goods();
			goods.setBusId(business.getId());
			goods.setUpDown("1");
			List<Goods> goodsList = goodsService.findByGood(goods);
			if (null==goodsList || goodsList.size()==0) {
				business.setUpDown("0");
				businessService.update(business);
			}
		}
	}
	
	//把已经下架的营销活动下的档次全部下架
	private void downActionMeansState() throws Exception {
		logger.info("把已经下架的营销活动下的档次全部下架");
		Business queryBus= new Business();
		queryBus.setBusType("200");
		queryBus.setUpDown("0");
		List<Business> businessesList = businessService.findByBus(queryBus);
		logger.info("得到的下架状态的活动长度是"+businessesList.size());
		for (Business business:businessesList) {
			Goods goods = new Goods();
			goods.setBusId(business.getId());
			List<Goods> goodsList = goodsService.findByGood(goods);
			if (null!=goodsList && goodsList.size()>0) {
				logger.info("查询已经下架的活动下的档次的长度"+goodsList.size());
				for(Goods goods2 : goodsList){
					goods2.setUpDown("0");
					goodsService.update(goods2);
				}
			}
		}
	}

	//查询营销活动为上架状态的所有活动
	private void makeGoods() throws Exception {
		//查询营销活动为上架状态的所有活动
		Business business = new Business();
		business.setBusType("200");
		business.setUpDown("1");
		List<Business> businesses4DB = businessService.findByBus(business);
		logger.info("档次信息的查询的时候查询活动表得到的长度是" + businesses4DB.size());
		logger.info("根据活动去查询档次开始start()");
		if(businesses4DB.size()>0){
			for (Business bus : businesses4DB) {
				WsGetSaleMeansContentRequest request = new WsGetSaleMeansContentRequest();
				request.setProvinceCode(PROVINCE);
				request.setActId(bus.getBusCode());// 活动ID
				request.setChannelType(CHANNELTYPE);// 渠道类型
				request.setCustGroupId(bus.getGroupCustId());// 集团custid
				request.setFalg(LOGINFLAG);
				WsGetSaleMeansContentReponse response = sitechInterFace.queryWsGetSaleMeansContent(request);
				//在这里要对返回的数据进行一下判断，如果正确返回才可以往下走
				List<MeanContent> meanList = response.getMeansList();
				logger.info("++++++++++++根据活动去查询档次得到的列表长度 ++++++++"+meanList.size());
				if(null!=meanList && meanList.size()>0){
					Goods good = new Goods();
					good.setBusId(bus.getId());
					List<Goods> goodList = goodsService.findByGood(good);
					this.makeGoodsUtil(goodList,meanList,bus.getId());
				}
					
			}
		}
		
		logger.info("根据活动去查询档次开结束end()");
	}
	
	// 地市列表
	private List<String> makeReginList() {
		List<String> reginCodeList;
		reginCodeList = new ArrayList<String>();
		 reginCodeList.add("100"); //全省
		 reginCodeList.add("10034"); // 太原
		 reginCodeList.add("10035"); // 大同
		 reginCodeList.add("10036"); // 阳泉
		 reginCodeList.add("10037"); // 长治
		 reginCodeList.add("10038"); // 晋城
		 reginCodeList.add("10039"); // 朔州
		 reginCodeList.add("10040"); // 忻州
		 reginCodeList.add("10041"); // 晋中
		 reginCodeList.add("10042"); // 临汾
		 reginCodeList.add("10043"); // 运城
		 reginCodeList.add("10044"); // 吕梁
		 return reginCodeList;
	}
	
	
	// 查询营销活动传入region_code的map表
	private static String getReginCode(String key) {
		Map<String , String> reginCodeMap = new HashMap<String , String>();
		reginCodeMap.put("100", "100");// 全省
		reginCodeMap.put("10034", "11");// 太原
		reginCodeMap.put("10035", "12");// 大同
		reginCodeMap.put("10036", "13");// 阳泉
		reginCodeMap.put("10037", "14");// 长治
		reginCodeMap.put("10038", "15");// 晋城
		reginCodeMap.put("10039", "16");// 朔州
		reginCodeMap.put("10040", "17");// 忻州
		reginCodeMap.put("10041", "18");// 晋中
		reginCodeMap.put("10042", "19");// 临汾
		reginCodeMap.put("10043", "20");// 运城
		reginCodeMap.put("10044", "21");// 吕梁
		return reginCodeMap.get(key);
	}

	// 活动上架下架之前的准备工作
	public void makeBusiness(List<MarkingActionInfo> actions, String regionId, java.util.Map<String, Business> busMap4DB) throws Exception {
		// 新增活动上架
		List<Business> addBusinesses = new ArrayList<Business>();
		// 原来有的活动上架
		List<Business> updateBusinesses = new ArrayList<Business>();
		if (busMap4DB.size() > 0) {//说明已经有数据插入
			// 这是从库里面查询出来的
			// 数据库表里存在的活动ID和查询出来的活动ID来进行匹配
			for (MarkingActionInfo action : actions) {
				if (busMap4DB.containsKey(action.getActID())) {//表里已经有这个活动了
					logger.info("更新活动" + action.getActID());
					Business bus = busMap4DB.get(action.getActID());
					bus.setUpDown("1");
					bus.setStartTime(action.getStartDate());//开始时间
					bus.setEndTime(action.getEndDate());//结束时间
					bus.setGroupCustId(action.getCustGroupID());//查询活动返回的groupId
					bus.setRemark(action.getMktDiction());//活动内容
					bus.setBusName(action.getActName());
					updateBusinesses.add(bus);
				} else {// 新增
					logger.info("新增活动" + action.getActID());
					Business bus = new Business();
					bus.setBusCode(action.getActID());//活动ID
					bus.setBusName(action.getActName());
					bus.setStartTime(action.getStartDate());//开始时间
					bus.setEndTime(action.getEndDate());//结束时间
					bus.setRemark(action.getMktDiction());//活动内容
					bus.setGroupCustId(action.getCustGroupID());//查询活动返回的groupId
					bus.setState("1");
					bus.setUpDown("1");
					bus.setBusType("200");
					// 添加到新增活动上架的列表中
					addBusinesses.add(bus);
					busMap4DB.put(action.getActID(), bus);
				}
			}

		} else {
			// 从表里查询营销活动没有则把查询出来的活动全部插入进去
			for (MarkingActionInfo action : actions) {
				Business bus = new Business();
				bus.setBusCode(action.getActID());//活动ID
				bus.setBusName(action.getActName());
				bus.setStartTime(action.getStartDate());//开始时间
				bus.setEndTime(action.getEndDate());//结束时间
				bus.setGroupCustId(action.getCustGroupID());//查询活动返回的groupId
				bus.setRemark(action.getMktDiction());//活动内容
				bus.setState("1");
				bus.setUpDown("1");
				bus.setBusType("200");
				addBusinesses.add(bus);
				busMap4DB.put(action.getActID(), bus);
			}
		}

		// 把得到的新的活动数据添加
		this.addAction(addBusinesses, regionId);
		// 修改原来的活动状态
		this.updateAction(updateBusinesses);
	}

	
	// 新增活动上架
	public void addAction(List<Business> upBusinesses, String regionId) throws Exception {

		// 业务不支持批量保存，只能循环保存
		if (upBusinesses.size() > 0) {
			logger.info("新增活动上架的长度" + upBusinesses.size());
			for (Business business : upBusinesses) {
				businessService.save(business);
			}
		}
		
		if ("100".equals(regionId)) {
			// 说明是全省添加
			List<Region> regions = regionService.findRegionList(); 
//			List<User> users = userService.findAllUser(); 
			for (Business business : upBusinesses) { 
				//全省新增活动和地市关联
				for (Region region : regions) { 
					RegionBusiness regionBusiness = new RegionBusiness(); 
					regionBusiness.setBusId(business.getId()); 
					regionBusiness.setRegionId(region.getId());
					regionBusinessService.save(regionBusiness); 
				} 
				
			}
		 } else {
			 // 说明是地市添加 
			 List<Region> regions = regionService .findRegionByRegionCode(regionId); 
			 for (Business business : upBusinesses) { 
				 //地市新增活动和地市关联 
				 RegionBusiness regionBusiness = new RegionBusiness(); 
				 regionBusiness.setBusId(business.getId()); 
				 regionBusiness.setRegionId(regions.get(0).getId()); 
				 regionBusinessService.save(regionBusiness); // 地市新增活动和营业员进行关联
			 } 
		}
		 
	}

	// 修改原来有的活动状态
	public void updateAction(List<Business> upBusinesses) throws Exception {
		for (Business business : upBusinesses) {
			businessService.update(business);
		}

	}

	// 操作档次信息
	public void makeGoodsUtil(List<Goods> goodList, List<MeanContent> meanList, String businessId) throws Exception {
		// 修改档次状态
		List<Goods> updateGoods = new ArrayList<Goods>();
		// 新增档次上架
		List<Goods> addGoods = new ArrayList<Goods>();
		logger.info("档次入表开始====start()");
		if (goodList.size() > 0) {//说明活动对应的档次有数据
			logger.info("档次表里有数据进入这里！！！！！");
			// 这是从库里面查询出来的
			java.util.Map<String, Goods> goodsMap = new HashMap<String, Goods>();
			for (Goods goods : goodList) {
				goodsMap.put(goods.getFeeCode(), goods);
			}
			// 把从表里查询出来的活动ID作为一个数组
			Set<String> keySet = goodsMap.keySet();
			// 数据库表里存在的档次ID和查询出来的档次ID来进行匹配
			for (MeanContent meanContent : meanList) {
				logger.info("当前档次(报文)"+meanContent.getMeansId() + ","+meanContent.getMeansName());
				if (keySet.contains(meanContent.getMeansId())) {
					logger.info("库里已经有档次进入这里");
					//在这里要判断该档次是否符合条件
					if (null != meanContent.getA00()) {// 说明有A00元素
						logger.info("不操作操作的档次A00"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA01()) {// 说明有A01元素
						logger.info("不操作操作的档次A01"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (null != meanContent.getA02()) {// 说明有A02元素
						logger.info("不操作操作的档次A02"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA04()) {// 说明有A04元素
						logger.info("不操作操作的档次A04"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA05()) {// 说明有A05元素
						logger.info("不操作操作的档次isA05"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (null != meanContent.getA07()) {// 说明有A07元素
						logger.info("不操作操作的档次getA07"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (meanContent.isA09()) {// 说明有A09元素
						logger.info("不操作操作的档次isA09"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (null != meanContent.getA10()) {// 说明有A10元素
						logger.info("不操作操作的档次getA10"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (null != meanContent.getA11()) {// 说明有A11元素
						logger.info("不操作操作的档次getA11"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA12()) {// 说明有A12元素
						logger.info("不操作操作的档次isA12"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA14()) {// 说明有A14元素
						logger.info("不操作操作的档次isA14"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA15()) {// 说明有A15元素
						logger.info("不操作操作的档次isA15"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA16()) {// 说明有A16元素
						logger.info("不操作操作的档次isA16"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					/*if (meanContent.isA17()) {// 说明有A17元素
						logger.info("不操作操作的档次"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA18()) {// 说明有A18元素
						logger.info("不操作操作的档次"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}*/
					if (meanContent.isA19()) {// 说明有A19元素
						logger.info("不操作操作的档次isA19"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA20()) {// 说明有A20元素
						logger.info("不操作操作的档次isA20"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA24()) {// 说明有A24元素
						logger.info("不操作操作的档次isA24"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA25()) {// 说明有A25元素
						logger.info("不操作操作的档次isA25"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA26()) {// 说明有A26元素
						logger.info("不操作操作的档次isA26"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA28()) {// 说明有A28元素
						logger.info("不操作操作的档次isA28"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA29()) {// 说明有A29元素
						logger.info("不操作操作的档次isA29"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA30()) {// 说明有A30元素
						logger.info("不操作操作的档次isA30"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA35()) {// 说明有A35元素
						logger.info("不操作操作的档次isA35"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA38()) {// 说明有A38元素
						logger.info("不操作操作的档次isA38"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA39()) {// 说明有A39元素
						logger.info("不操作操作的档次isA39"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA40()) {// 说明有A40元素
						logger.info("不操作操作的档次isA40"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA41()) {// 说明有A41元素
						logger.info("不操作操作的档次isA41"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA42()) {// 说明有A42元素
						logger.info("不操作操作的档次isA42"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA43()) {// 说明有A43元素
						logger.info("不操作操作的档次isA43"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (null != meanContent.getB02()) {// 说明有B02元素
						logger.info("不操作操作的档次getB02"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isB06()) {// 说明有B06元素
						logger.info("不操作操作的档次isB06"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (null != meanContent.getB10()) {// 说明有B10元素
						logger.info("不操作操作的档次getB10"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isB25()) {// 说明有B25元素
						logger.info("不操作操作的档次isB25"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					Goods goods = goodsMap.get(meanContent.getMeansId());
					// 判断是否下架，如果是下架，改为上架
					if (!goods.getUpDown().equals("1")) {
						logger.info("");
						goods.setGoodsName(meanContent.getMeansName());
						goods.setUpDown("1");
						updateGoods.add(goods);
					}else {
						//如果是上架状态，则要与查询出来的档次名称保持一致
						goods.setGoodsName(meanContent.getMeansName());
						updateGoods.add(goods);
					}
					logger.info("存在操作的档次"+meanContent.getMeansId() + "," +meanContent.getMeansName());
					// 从查询数据库中的map出删除
					goodsMap.remove(meanContent.getMeansId());
				} else {//说明这是新增的档次
					logger.info("新增档次进入这里");
					//在这里要判断该档次是否符合条件
					if (null != meanContent.getA00()) {// 说明有A00元素
						logger.info("不操作操作的档次A00"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA01()) {// 说明有A01元素
						logger.info("不操作操作的档次A01"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (null != meanContent.getA02()) {// 说明有A02元素
						logger.info("不操作操作的档次A02"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA04()) {// 说明有A04元素
						logger.info("不操作操作的档次A04"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA05()) {// 说明有A05元素
						logger.info("不操作操作的档次isA05"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (null != meanContent.getA07()) {// 说明有A07元素
						logger.info("不操作操作的档次getA07"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (meanContent.isA09()) {// 说明有A09元素
						logger.info("不操作操作的档次isA09"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (null != meanContent.getA10()) {// 说明有A10元素
						logger.info("不操作操作的档次getA10"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (null != meanContent.getA11()) {// 说明有A11元素
						logger.info("不操作操作的档次getA11"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA12()) {// 说明有A12元素
						logger.info("不操作操作的档次isA12"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA14()) {// 说明有A14元素
						logger.info("不操作操作的档次isA14"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA15()) {// 说明有A15元素
						logger.info("不操作操作的档次isA15"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA16()) {// 说明有A16元素
						logger.info("不操作操作的档次isA16"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					/*if (meanContent.isA17()) {// 说明有A17元素
						logger.info("不操作操作的档次"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA18()) {// 说明有A18元素
						logger.info("不操作操作的档次"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}*/
					if (meanContent.isA19()) {// 说明有A19元素
						logger.info("不操作操作的档次isA19"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA20()) {// 说明有A20元素
						logger.info("不操作操作的档次isA20"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA24()) {// 说明有A24元素
						logger.info("不操作操作的档次isA24"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA25()) {// 说明有A25元素
						logger.info("不操作操作的档次isA25"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA26()) {// 说明有A26元素
						logger.info("不操作操作的档次isA26"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA28()) {// 说明有A28元素
						logger.info("不操作操作的档次isA28"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA29()) {// 说明有A29元素
						logger.info("不操作操作的档次isA29"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA30()) {// 说明有A30元素
						logger.info("不操作操作的档次isA30"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA35()) {// 说明有A35元素
						logger.info("不操作操作的档次isA35"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA38()) {// 说明有A38元素
						logger.info("不操作操作的档次isA38"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA39()) {// 说明有A39元素
						logger.info("不操作操作的档次isA39"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA40()) {// 说明有A40元素
						logger.info("不操作操作的档次isA40"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA41()) {// 说明有A41元素
						logger.info("不操作操作的档次isA41"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA42()) {// 说明有A42元素
						logger.info("不操作操作的档次isA42"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isA43()) {// 说明有A43元素
						logger.info("不操作操作的档次isA43"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (null != meanContent.getB02()) {// 说明有B02元素
						logger.info("不操作操作的档次getB02"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isB06()) {// 说明有B06元素
						logger.info("不操作操作的档次isB06"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					
					if (null != meanContent.getB10()) {// 说明有B10元素
						logger.info("不操作操作的档次getB10"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					if (meanContent.isB25()) {// 说明有B25元素
						logger.info("不操作操作的档次isB25"+meanContent.getMeansId() + "," +meanContent.getMeansName());
						continue;//跳出这次循环，但是这个档次对应的活动还有其他的档次，所以不能把其对应的活动状态置为下架状态
					}
					logger.info("新增操作的档次"+meanContent.getMeansId() + "," +meanContent.getMeansName());
					Goods goods = new Goods();
					goods.setGoodsDesc("200");
					goods.setFeeCode(meanContent.getMeansId());
					goods.setGoodsName(meanContent.getMeansName());
					goods.setBusId(businessId);
					goods.setState("1");
					goods.setUpDown("1");
					// 添加到新增档次上架表里
					addGoods.add(goods);
				}
			}
			
			// 把剩下的档次添加到下架档次列表里
			Iterator<String> it = goodsMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				Goods goods = goodsMap.get(key);
				// 下架
				goods.setUpDown("0");
				updateGoods.add(goods);
			}
		} else {
			// 从表里查询营销活动档次的数据，没有则把查询出来的档次全部插入进去
			logger.info("===============档次表里没有数据进入这里==============");
			for (MeanContent meanContent : meanList) {
				if (null != meanContent.getA11()) {// 说明没有A11活动
					continue;
				}
				Goods goods = new Goods();
				goods.setGoodsDesc("200");
				goods.setFeeCode(meanContent.getMeansId());
				goods.setGoodsName(meanContent.getMeansName());
				goods.setBusId(businessId);
				goods.setState("1");
				goods.setUpDown("1");
				// 添加到新增档次上架表里
				addGoods.add(goods);
			}

			logger.info("===============最后得到的档次长度是==============" + addGoods.size());

			// 判断一下Goods的长度，如果长度为0，对应的活动就要下架
			if (addGoods.size() == 0) {
				Business business = businessService.findBusinessById(businessId);
				business.setUpDown("0");
				businessService.update(business);
			}

		}
		this.updateGoods(updateGoods);
		this.addGoods(addGoods);
	}

	// 原来有的档次状态修改
	public void updateGoods(List<Goods> goodsList) throws Exception {
		for (Goods goods : goodsList) {
			goodsService.update(goods);
		}
	}

	// 新增档次入库
	public void addGoods(List<Goods> goodsList) throws Exception {
		if (goodsList.size() > 0) {// 说明有档次符合条件
			for(Goods goods : goodsList){
				goodsService.save(goods);
			}
			
		}
	}

}
