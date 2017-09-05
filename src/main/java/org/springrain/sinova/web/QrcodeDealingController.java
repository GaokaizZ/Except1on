package org.springrain.sinova.web;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.BusinessGoodsDTO;
import org.springrain.sinova.dto.FrontListDTO;
import org.springrain.sinova.dto.RotateDTO;
import org.springrain.sinova.dto.SpecialQrcodeDTO;
import org.springrain.sinova.dto.UserBusinessDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.AcceptRecord;
import org.springrain.sinova.entity.AdImage;
import org.springrain.sinova.entity.AdMsg;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.entity.Rotate;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.sinova.interfaces.sitech.s4000cfmb.BusiInfo;
//import org.springrain.sinova.interfaces.sitech.s4000cfmb.New_S4000Cfm_BReruest;
import org.springrain.sinova.interfaces.sitech.s4000cfmb.S4000Cfm_BReruest;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.S4035IntCfmRequest;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.S4035IntCfmResponse;
import org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.S4035IntChkResponse;
import org.springrain.sinova.interfaces.sitech.s4035IntChk.req.S4035IntChkRequest;
import org.springrain.sinova.service.IAcceptRecordService;
import org.springrain.sinova.service.IAdGoodsService;
import org.springrain.sinova.service.IAdImageService;
import org.springrain.sinova.service.IAdMsgService;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IGoodsActService;
import org.springrain.sinova.service.IGoodsService;
import org.springrain.sinova.service.IQrcodeService;
import org.springrain.sinova.service.IRotateService;
import org.springrain.sinova.service.ISpecialQrcodeService;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.sinova.util.AutoGetPhone;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.Des;
import org.springrain.sinova.util.LotteryUtils;
import org.springrain.sinova.util.SortList;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.DicData;
import org.springrain.system.entity.User;
import org.springrain.system.service.IDicDataService;
import org.springrain.system.service.IUserService;
import org.springrain.system.util.Base64Util;
import org.springrain.system.util.Constant;
import org.springrain.system.util.DES;
import org.springrain.system.util.DESPlus;
import org.springrain.system.util.PropertyFileCache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/qrcodedealing")
public class QrcodeDealingController extends BaseController {
	@Resource
	private IBusinessService businessService;
	@Resource
	private ISitechInterface sitechInterFace;
	@Resource
	private IGoodsService goodsService;
	@Resource
	private IQrcodeService qrcodeService;
	@Resource
	private IAcceptRecordService acceptRecordService;
	@Resource
	private IUserService userService;
	@Resource
	private ISpecialQrcodeService specialQrcodeService;
	@Resource
	private IAdImageService adImageService;
	@Resource
	private IUserOfficeService userOfficeService;
	@Resource
	private IDicDataService dicDataService;
	@Resource
	private IRotateService rotateService;
	@Resource
	private IAdMsgService adMsgService;
	@Resource
	private IAdGoodsService adGoodsService;
	@Resource
	private IGoodsActService goodsActService;
//	@Resource
//	private IAcceptFailService acceptFailService;
	

	private String listurl = "/sinova/frontqrcode/businessList";
	private String errorUrl = "/sinova/frontqrcode/handleError";
	private String codeErr = "/sinova/frontqrcode/qrcodeError";
	private String allListurl = "/sinova/frontqrcode/allList";
	private String specEmpty = "/sinova/frontqrcode/specEmpty";
	private String weidianurl = "/sinova/frontqrcode/weidian";
	private String lotteryurl = "/sinova/frontqrcode/lottery";
	
	private PropertyFileCache pfCache = PropertyFileCache.getInstance();
	private static HashMap<String,String> acceptMap = new HashMap<String,String>();
	
	/**
	 * 扫码跳页(单业务/业务列表)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forBusiness") 
	public String forBusiness(HttpServletRequest request, Model model) throws Exception{
		//获取二维码链接中的UUID
		String flowNo = request.getParameter("param");
		logger.info("二维码主键是========"+flowNo);
		//将参数存入session
		request.getSession().setAttribute(Const.QRCODE_PARAM_STR, flowNo);
		
		String jsonString = "";
		Qrcode qrcode = new Qrcode();
		if(StringUtils.isNotBlank(flowNo)){
			qrcode = qrcodeService.findQrcodeByFlowNo(flowNo);
			if(null!=qrcode && "1".equals(qrcode.getState())){//判断二维码是否有效
				jsonString = this.getJsonString(qrcode.getFlowNo());
				if (specEmpty.equals(jsonString)) {
					return specEmpty;
				}
			}else{
				logger.info("无效的二维码！");
				return codeErr;
			}
		}else{
			logger.info("获取二维码参数失败");
			return errorUrl;
		}
         
		//将链接中的json字符串转换成bean对象
		UserBusinessDTO userBusinessDTO = JSONObject.parseObject(jsonString, UserBusinessDTO.class);
		
		//获取营业员工号和业务集合
		String userCode = userBusinessDTO.getUserCode();
		
		List<FrontListDTO> gList = new ArrayList<FrontListDTO>();
		
		//用户不存在
		Finder finder = Finder.getSelectFinder(User.class);
		finder.append(" where workno = :workno ");
		finder.setParam("workno", userCode);
		User user = userService.queryForObject(finder, User.class);
		if(user == null){
			logger.info("用户不存在，提示无效的二维码！");
			return codeErr;
		}else{
			if(Constant.ZERO.equals(user.getState())){
				logger.info("用户无效，提示无效的二维码！");
				return codeErr;
			}
			//验证工号用户状态是否可用
			if (!userService.availableUser(user)) {
				logger.info("用户无效，提示无效的二维码！");
				return codeErr;
			}
		}
		List<BusinessGoodsDTO> list = userBusinessDTO.getBusGoodsList();
		
		
		
		//判断是否是专属二维码
		if ("1".equals(qrcode.getType())) {
			logger.info("获取店长推荐{[]}");
	    	List<Goods> goods = adGoodsService.findGoodsByUserId(user.getId());
	    	model.addAttribute("goods", goods);
	    	
	    	logger.info("获取店长信息{[]}");
	    	Map<String,AdMsg> msgs = adMsgService.findAdMsgByUser(user.getId(), new Page());
	    	boolean b = false;
	    	for (String key : msgs.keySet()) {
	    		if(StringUtils.equals(msgs.get(key).getIsPub(), "1")){
	    			b = true;
	    		}
			}
	    	if(b){
	    		model.addAttribute("msgs", msgs);
	    	}
	    	logger.info("获取店长workno{[]}" + userCode);
	    	model.addAttribute("workno", userCode);
			
			//查询是否有广告位
			AdImage adImage = adImageService.findAdImageByUserId(user.getId());
			if (null !=adImage && null!=adImage.getImage() && !"".equals(adImage.getImage())) {
				model.addAttribute("adImage",adImage);
			}
			
			List<DicData> dicList = dicDataService.findListDicData("bustype");
			Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
			Map<String,DicData> typeMap = new TreeMap<String,DicData>(com);
			Map<String,DicData> displayMap = new TreeMap<String,DicData>(com);
			if (null!=dicList && dicList.size()!=0) {
				List<FrontListDTO> fldList = businessService.getFrontListDTOByWorkNo(userCode,null);
				for (DicData dicData : dicList) {
					if ("1".equals(dicData.getState())) {
						List<FrontListDTO> vaList = new ArrayList<FrontListDTO>();
						//查询该用户下的所有该类型活动及档次(业务及商品)
//						String flag = this.getGoodsList(list,vaList,dicData.getRemark(),userCode);
						String flag = this.getGoodsList(list,vaList,dicData.getRemark(),fldList);
						if (vaList.size()!=0) {
							typeMap.put(dicData.getName(), dicData);
						} else {
							if ("4ghuanka".equals(dicData.getRemark())) {
								typeMap.put(dicData.getName(), dicData);
							} else {
								displayMap.put(dicData.getName(), dicData);
							}
//							displayMap.put(dicData.getName(), dicData);
						}
					}
				}
				model.addAttribute("typeMap", typeMap);
				model.addAttribute("displayMap", displayMap);
			}
			
			UserOfficeDTO userOfficeDto = userOfficeService.findUserOfficeDTOById(user.getId());
			if (null!=userOfficeDto) {
				model.addAttribute("wdName",userOfficeDto.getOfficeName()+"营业员"+user.getName()+"的店铺");
			}
			StringBuffer paramSb = new StringBuffer(512);
			StringBuffer urlSb = new StringBuffer(512);
			//工号 姓名加密
			DES m = new DES("bbx_link_encry_secret");
			String userName = java.net.URLEncoder.encode(user.getName(), "GBK");
			String recommend = "mobile:"+user.getMobile()+",loginNo:"+qrcode.getBelongUser()+",loginName:"+userName+",channel:24";
			String param = m.encrypt(recommend);
			logger.info("加密的字符串====="+recommend+"----------userName===="+user.getName());
			Finder finders = new Finder();
			finders.append("select * from ").append(Finder.getTableName(DicData.class));
			finders.append(" where code='wapgouji' ");
			DicData dicDatawap = dicDataService.queryForObject(finders, DicData.class);
			paramSb.append("wapgouji="+param);
			urlSb.append("wapgouji="+dicDatawap.getRemark());
			/*
			 * 4g预约换卡参数及链接
			*/
			String p =Util.getSendTime()+Util.getrannumber().substring(0, 5)+"|"+user.getWorkno()+"|kuaisao|400401";
			String phoneNo = (String) request.getSession().getAttribute("USER_MOBILE");
			if(StringUtils.isBlank(phoneNo)){
				AutoGetPhone.autoGetPhone(request,sitechInterFace);//流量自动获取手机号
				phoneNo = (String) request.getSession().getAttribute("USER_MOBILE");
			}
			if(StringUtils.isNotBlank(phoneNo)){
				phoneNo = phoneNo.startsWith("86") ? phoneNo.substring(2, phoneNo.length()) : phoneNo;
				p=p+"|"+phoneNo;
			} else {
				p=p+"|";
			}
			Des desObj = new Des();
			logger.info("参数为========="+p);
			String hkparam = desObj.strEnc(p,"sitech", null, null);
			logger.info("加密字符串======"+hkparam);
			
			Finder hkFinder = new Finder();
			hkFinder.append("select * from ").append(Finder.getTableName(DicData.class));
			hkFinder.append(" where code='4ghuanka' ");
			DicData dicDatahk = dicDataService.queryForObject(hkFinder, DicData.class);
			
			paramSb.append(",4ghuanka="+hkparam);
			urlSb.append(",4ghuanka="+dicDatahk.getRemark());
			
			model.addAttribute("otherparam", paramSb.toString());
			model.addAttribute("otherurl", urlSb.toString()); 
			
//			model.addAttribute("param", param);
//			model.addAttribute("wapUrl", dicDatawap.getRemark());
			
			model.addAttribute("flowNo",flowNo);
			model.addAttribute("random", Math.random());
			return weidianurl;
		}
		
		//处理单业务单商品状态 跳页到商品详情页
		if(null!=list && list.size()==1){
			BusinessGoodsDTO busDto = list.get(0);
			String bCode = busDto.getBusinessCode();
			List<String> pList = busDto.getGoodsList();
			if(pList!=null && pList.size()==1){
				String fCode = pList.get(0);
				FrontListDTO fDto = businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCode(bCode, userCode,fCode);
				if(null!=fDto){
					if(Constant.ZERO.equals(fDto.getBusState())){
						logger.info("[{}]业务为无效状态,业务编码为[{}]",fDto.getBusName(),fDto.getBusCode());
						return codeErr; 
					}
					if(Constant.ZERO.equals(fDto.getBusUpDown())){
						logger.info("[{}]业务为下架状态,业务编码为[{}]",fDto.getBusName(),fDto.getBusCode());
						return codeErr;
					}
					if(Constant.ZERO.equals(fDto.getGoodsState())){
						logger.info("[{}]商品为无效状态,商品资费代码为[{}]",fDto.getGoodsName(),fDto.getGoodsFeeCode());
						return codeErr;
					}
					if(Constant.ZERO.equals(fDto.getGoodsUpDown())){
						logger.info("[{}]商品为下架状态,商品资费代码为[{}]",fDto.getGoodsName(),fDto.getGoodsFeeCode());
						return codeErr;
					}
					
					String goodsId = fDto.getGoodsId();
					if(null!=goodsId){
						Goods g = goodsService.findGoodsById(goodsId);
						if(null!=g){
							model.addAttribute("goods",g);
							model.addAttribute("busCode",bCode);
							model.addAttribute("feeCode",fCode);
							model.addAttribute("opId",userCode);
							model.addAttribute("param", flowNo);
							//判断是否是营销活动
							if ("200".equals(fDto.getBusType())) {
								Business onlyBus = new Business();
								onlyBus.setBusCode(bCode);
								List<Business> nBusList = businessService.findByBus(onlyBus);
								if (null!=nBusList && nBusList.size()!=0) {
									onlyBus = nBusList.get(0);
									gList.add(businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCodeAndBusType(bCode, userCode,fCode,"200"));
									model.addAttribute("business",onlyBus);
									model.addAttribute("goodsList",gList);
									model.addAttribute("busType", "200");
									return "/sinova/frontqrcode/yhhdDetail";
								}else {
									return errorUrl;
								}
							} else {
								model.addAttribute("busType", "4g");
								return "/sinova/frontqrcode/businessDetail";
							}
						}
					}
				}else{
					logger.info("未能查出符合条件的商品对象");
					return codeErr;
				}
			}
		}
		
		/*
		 * 判断是否需要显示的活动类型 
		 */
		List<String> typeList = this.getListType(user.getId(),list);
		if (typeList.size()==0) return codeErr;
		
		if (typeList.size()>1) {
			List<DicData> dicList = dicDataService.findListDicData("bustype");
			Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
			Map<String,DicData> typeMap = new TreeMap<String,DicData>(com);
			if (null!=dicList && dicList.size()!=0) {
				List<FrontListDTO> fldList = businessService.getFrontListDTOByWorkNo(userCode,null);
				for (DicData dicData : dicList) {
					if ("1".equals(dicData.getState())) {
						List<FrontListDTO> vaList = new ArrayList<FrontListDTO>();
						String flag = this.getGoodsList(list,vaList,dicData.getRemark(),fldList);
						if (vaList.size()!=0) {
							typeMap.put(dicData.getName(), dicData);
						} 
					}
				}
				model.addAttribute("typeMap", typeMap);
			}
			model.addAttribute("flowNo",flowNo);
			return allListurl;
		}
		List<FrontListDTO> fldList = businessService.getFrontListDTOByWorkNo(userCode,typeList.get(0));
		String flag = this.getGoodsList(list,gList,typeList.get(0),fldList);
		if ("only".equals(flag)) {
			model.addAttribute("flag",flag);
		}
		
		if ("200".equals(typeList.get(0))) {
			Map<String,String> map = new HashMap<String,String>();
			this.getHdMap(map,gList);
			model.addAttribute("goodsList2",map);
			model.addAttribute("tname","4G营销活动");
		} else {
			Map<String,List<FrontListDTO>> map = new HashMap<String,List<FrontListDTO>>();
			this.get4gMap(map,gList);
			if(null!=map){
				for(Map.Entry<String,List<FrontListDTO>> entry: map.entrySet()) {
					List<FrontListDTO> listTemp=entry.getValue();
					SortList<FrontListDTO> sortList = new SortList<FrontListDTO>(); 
					sortList.Sort(listTemp, "getGoodsFeeCode", null);
				}
			}else{
				return codeErr;
			}
			model.addAttribute("goodsList",map);
			List<DicData> dicList = dicDataService.findListDicData("bustype");
			for (DicData dicData : dicList) {
				if (typeList.get(0).equals(dicData.getRemark())) {
					model.addAttribute("tname",dicData.getName());
				}
			}
		}
		
		//将参数存入session
		request.getSession().setAttribute(Const.QRCODE_PARAM_STR, flowNo);
		model.addAttribute("param",flowNo);
		model.addAttribute("opId", userBusinessDTO.getUserCode());
		return listurl;
	}
	
	

	@RequestMapping("/businessList")
	public String businessList(HttpServletRequest request, Model model) throws Exception{
		String busType = request.getParameter("busType");
		String flowNo = request.getParameter("flowNo");
		logger.info("busType====="+busType);
		logger.info("flowNo====="+flowNo);
		String jsonString = this.getJsonString(flowNo);
		
		//将链接中的json字符串转换成bean对象
		UserBusinessDTO userBusinessDTO = JSONObject.parseObject(jsonString, UserBusinessDTO.class);
		
		//获取营业员工号和业务集合
		String userCode = userBusinessDTO.getUserCode();
		List<BusinessGoodsDTO> list = userBusinessDTO.getBusGoodsList();
		List<FrontListDTO> gList = new ArrayList<FrontListDTO>();
	
		//处理单业务单商品状态 跳页到商品详情页
		if(null!=list && list.size()==1){
			BusinessGoodsDTO busDto = list.get(0);
			String bCode = busDto.getBusinessCode();
			List<String> pList = busDto.getGoodsList();
			if(pList!=null && pList.size()==1){
				String fCode = pList.get(0);
				FrontListDTO fDto = businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCode(bCode, userCode,fCode);
				if(null!=fDto){
					if(Constant.ZERO.equals(fDto.getBusState())){
						logger.info("[{}]业务为无效状态,业务编码为[{}]",fDto.getBusName(),fDto.getBusCode());
						return codeErr; 
					}
					if(Constant.ZERO.equals(fDto.getBusUpDown())){
						logger.info("[{}]业务为下架状态,业务编码为[{}]",fDto.getBusName(),fDto.getBusCode());
						return codeErr;
					}
					if(Constant.ZERO.equals(fDto.getGoodsState())){
						logger.info("[{}]商品为无效状态,商品资费代码为[{}]",fDto.getGoodsName(),fDto.getGoodsFeeCode());
						return codeErr;
					}
					if(Constant.ZERO.equals(fDto.getGoodsUpDown())){
						logger.info("[{}]商品为下架状态,商品资费代码为[{}]",fDto.getGoodsName(),fDto.getGoodsFeeCode());
						return codeErr;
					}
					
					String goodsId = fDto.getGoodsId();
					if(null!=goodsId){
						Goods g = goodsService.findGoodsById(goodsId);
						if(null!=g){
							model.addAttribute("goods",g);
							model.addAttribute("busCode",bCode);
							model.addAttribute("feeCode",fCode);
							model.addAttribute("opId",userCode);
							model.addAttribute("param", flowNo);
							//判断是否是营销活动
							if ("200".equals(fDto.getBusType())) {
								Business onlyBus = new Business();
								onlyBus.setBusCode(bCode);
								List<Business> nBusList = businessService.findByBus(onlyBus);
								if (null!=nBusList && nBusList.size()!=0) {
									onlyBus = nBusList.get(0);
									gList.add(businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCodeAndBusType(bCode, userCode,fCode,"200"));
									model.addAttribute("business",onlyBus);
									model.addAttribute("goodsList",gList);
									model.addAttribute("busType", "200");
									return "/sinova/frontqrcode/yhhdDetail";
								}else {
									return errorUrl;
								}
							} else {
								model.addAttribute("busType", "4g");
								return "/sinova/frontqrcode/businessDetail";
							}
						}
					}
				}else{
					logger.info("未能查出符合条件的商品对象");
					return codeErr;
				}
			}
		}
		List<FrontListDTO> fldList = businessService.getFrontListDTOByWorkNo(userCode,busType);
		String flag = this.getGoodsList(list,gList,busType,fldList);
		if ("only".equals(flag)) {
			model.addAttribute("flag",flag);
		}
		
		if ("200".equals(busType)) {
			Map<String,String> map = new HashMap<String,String>();
			this.getHdMap(map,gList);
			model.addAttribute("goodsList2",map);
			model.addAttribute("tname","4G营销活动");
		} else {
			Map<String,List<FrontListDTO>> map = new HashMap<String,List<FrontListDTO>>();
			this.get4gMap(map,gList);
			if(null!=map){
				for(Map.Entry<String,List<FrontListDTO>> entry: map.entrySet()) {
					List<FrontListDTO> listTemp=entry.getValue();
					SortList<FrontListDTO> sortList = new SortList<FrontListDTO>(); 
					sortList.Sort(listTemp, "getGoodsFeeCode", null);
				}
			}else{
				return codeErr;
			}
			model.addAttribute("goodsList",map);
			List<DicData> dicList = dicDataService.findListDicData("bustype");
			for (DicData dicData:dicList) {
				if (dicData.getRemark().equals(busType)) {
					model.addAttribute("tname",dicData.getName());
				}
			}
		}
		
		//将参数存入session
		request.getSession().setAttribute(Const.QRCODE_PARAM_STR, flowNo);
		model.addAttribute("opId", userBusinessDTO.getUserCode());
		model.addAttribute("param",flowNo);
		return listurl;
	}
	
	private void getHdMap(Map<String, String> map, List<FrontListDTO> gList) {
		for(int i=0;i<gList.size();i++){
			FrontListDTO fDto = gList.get(i);
			String key = fDto.getBusName();
			String busCode = fDto.getBusCode();
			map.put(key, busCode);
		}
	}

	private void get4gMap(Map<String, List<FrontListDTO>> map,
			List<FrontListDTO> gList) {
		//处理查出的结果集gList 封装为map key为业务名称 value为商品集合
		List<FrontListDTO> tempList = null;
		for(int i=0;i<gList.size();i++){
			FrontListDTO fDto = gList.get(i);
			String key = fDto.getBusName();
			tempList = map.get(key);
			if(null==tempList){
				tempList = new ArrayList<FrontListDTO>();
				tempList.add(fDto);
				map.put(key, tempList);
			}else{
				tempList.add(fDto);
				map.put(key, tempList);
			}
		}
	}
	/*
	private Map<String,List<FrontListDTO>> getQrcodeMap(List<FrontListDTO> gList) {
		Map<String,List<FrontListDTO>> map = new HashMap<String,List<FrontListDTO>>();
		//处理查出的结果集gList 封装为map key为业务名称 value为商品集合
		List<FrontListDTO> tempList = null;
		for(int i=0;i<gList.size();i++){
			FrontListDTO fDto = gList.get(i);
			String key = fDto.getBusName();
			tempList = map.get(key);
			if(null==tempList){
				tempList = new ArrayList<FrontListDTO>();
				tempList.add(fDto);
				map.put(key, tempList);
			}else{
				tempList.add(fDto);
				map.put(key, tempList);
			}
		}
		return map;
	}*/
	
//	private String getGoodsList(List<BusinessGoodsDTO> list, List<FrontListDTO> gList, String type,
//			String userCode) throws Exception {
//		String flag = " ";
//		for(int i=0;i<list.size();i++){
//			BusinessGoodsDTO businessGoodsDTO = list.get(i);
//			//获取业务编码
//			String busCode = businessGoodsDTO.getBusinessCode();
//			List<String> sList = businessGoodsDTO.getGoodsList();
//			if(null!=sList){
//				for(int j=0;j<sList.size();j++){
//					String fc = sList.get(j);
//					//查询该营业员下可办理的商品对象集合
//					FrontListDTO dto = businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCodeAndBusType(busCode, userCode,fc,type);
//					if(null!=dto){
//						if(Constant.ZERO.equals(dto.getBusState())){
//							logger.info("[{}]业务为无效状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
//						}
//						if(Constant.ZERO.equals(dto.getBusUpDown())){
//							logger.info("[{}]业务为下架状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
//						}
//						if(Constant.ZERO.equals(dto.getGoodsState())){
//							logger.info("[{}]商品为无效状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
//						}
//						if(Constant.ZERO.equals(dto.getGoodsUpDown())){
//							logger.info("[{}]商品为下架状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
//						}
//						if(Constant.ONE.equals(dto.getBusState()) && Constant.ONE.equals(dto.getBusUpDown()) && Constant.ONE.equals(dto.getGoodsState()) && Constant.ONE.equals(dto.getGoodsUpDown())){
//								gList.add(dto);
//						}
//					}
//					
//				}
//			}else{//只选择业务 未选择商品 则一级菜单为业务名称链接
//				Business bus = new Business();
//				bus.setBusCode(busCode);
//				bus.setState(Constant.ONE);
//				List<Business> bList = businessService.findByBus(bus);
//				if(null!=bList && bList.size()>0){
//					Business b = bList.get(0);//理论上只会返回一个对象
//					if(null!=b){
//						//定义一个封装对象来存放这种情况的对象
//						FrontListDTO bDto = new FrontListDTO();
//						bDto.setBusId(b.getId());
//						bDto.setBusCode(b.getBusCode());
//						bDto.setBusName(b.getBusName());
//						bDto.setBusState(b.getBusType());
//						bDto.setBusUpDown(b.getUpDown());
//						bDto.setBusType(b.getBusType());
//						gList.add(bDto);
//					}
//				}
//				flag = "only";
//			}
//		}
//		logger.info(type+"====="+gList.size());
//		return flag;
//	}
	
	/**
	 * @param list
	 * @param gList
	 * @param type
	 * @param fldList
	 * @return
	 * @throws Exception
	 */
	private String getGoodsList(List<BusinessGoodsDTO> list, List<FrontListDTO> gList, String type, List<FrontListDTO> fldList
			) throws Exception {
		String flag = " ";
		for(int i=0;i<list.size();i++){
			BusinessGoodsDTO businessGoodsDTO = list.get(i);
			//获取业务编码
			String busCode = businessGoodsDTO.getBusinessCode();
			List<String> sList = businessGoodsDTO.getGoodsList();
			if(null!=sList){
				for(int j=0;j<sList.size();j++){
					String fc = sList.get(j);
					for (FrontListDTO dto:fldList) {
						if (type.equals(dto.getBusType()) && fc.equals(dto.getGoodsFeeCode())){
							if(Constant.ZERO.equals(dto.getBusState())){
								logger.info("[{}]业务为无效状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
							}
							if(Constant.ZERO.equals(dto.getBusUpDown())){
								logger.info("[{}]业务为下架状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
							}
							if(Constant.ZERO.equals(dto.getGoodsState())){
								logger.info("[{}]商品为无效状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
							}
							if(Constant.ZERO.equals(dto.getGoodsUpDown())){
								logger.info("[{}]商品为下架状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
							}
							if(Constant.ONE.equals(dto.getBusState()) && Constant.ONE.equals(dto.getBusUpDown()) && Constant.ONE.equals(dto.getGoodsState()) && Constant.ONE.equals(dto.getGoodsUpDown())){
									gList.add(dto);
							}
							break;
						}
					}
				}
			}else{//只选择业务 未选择商品 则一级菜单为业务名称链接
				Business bus = new Business();
				bus.setBusCode(busCode);
				bus.setState(Constant.ONE);
				List<Business> bList = businessService.findByBus(bus);
				if(null!=bList && bList.size()>0){
					Business b = bList.get(0);//理论上只会返回一个对象
					if(null!=b){
						//定义一个封装对象来存放这种情况的对象
						FrontListDTO bDto = new FrontListDTO();
						bDto.setBusId(b.getId());
						bDto.setBusCode(b.getBusCode());
						bDto.setBusName(b.getBusName());
						bDto.setBusState(b.getBusType());
						bDto.setBusUpDown(b.getUpDown());
						bDto.setBusType(b.getBusType());
						gList.add(bDto);
					}
				}
				flag = "only";
			}
		}
		return flag;
	}

	private List<String> getListType(String userId,List<BusinessGoodsDTO> list) throws Exception {
		List<Business> busList = userService.findBusiness(userId);
		List<String> busCodeList = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			BusinessGoodsDTO businessGoodsDTO = list.get(i);
			busCodeList.add(businessGoodsDTO.getBusinessCode());
		}
		List<String> typeList = new ArrayList<String>();
//		List<Business> hList = new ArrayList<Business>();
		if (null!=busList) {
			for (Business business:busList) {
				if (busCodeList.contains(business.getBusCode())) {
					if (!typeList.contains(business.getBusType())) {
						typeList.add(business.getBusType());
					}
				}
			}
		}
		return typeList;
	}

	
	private String getJsonString(String flowNo) throws Exception {
		String jsonString = "";
		Qrcode qrcode = qrcodeService.findQrcodeByFlowNo(flowNo);
		//截取flowNo后6位
		if("qyw999".equals(qrcode.getFlowNo().substring(qrcode.getFlowNo().length()-6, qrcode.getFlowNo().length())) && qrcode.getFlowNo().length()==38){
			logger.info("********全业务二维码*****************");
			//查询用户全部业务
			 List<Business> buss = businessService.findAllBus();
	         List<Goods> goods = businessService.findGoodsByBus();
	         String business = "";
	         String stro = "";
	         String busStr1 = "";
	         if(buss.size()>0 && goods.size()>0){
	         	for(int i=0; i<buss.size(); i++){
	         		for(int j=0; j<goods.size(); j++){
	         			if(j==0){
	         				stro = buss.get(i).getBusCode()+":"+",";
	         			}
	         			if(goods.get(j).getBusId().equals(buss.get(i).getId())){
	         				busStr1 = busStr1 + goods.get(j).getFeeCode()+":"+buss.get(i).getBusCode()+",";
	         			}
	         		}
	         		business = business + stro + busStr1;
	         		stro = "";
	         		busStr1 = "";
	         	}
	         }
	     
	         UserBusinessDTO ubDto =null;
	         if(StringUtils.isNotEmpty(business)&&StringUtils.isNotEmpty(qrcode.getBelongUser())){
	            ubDto= userBusiness(business,qrcode.getBelongUser());
	         }
	         
			String param = JSON.toJSONString(ubDto);
			jsonString = param;
		}else if("1".equals(qrcode.getType())){//判断是不是专属二维码
			logger.info("********专属业务二维码*****************");
			
			SpecialQrcodeDTO specialQrcode = new SpecialQrcodeDTO();
			User user = new User();
			user.setWorkno(qrcode.getBelongUser());
			User userDto = userService.queryForObject(user);
			if(userDto == null){
				logger.info("用户不存在，提示无效的二维码！");
				return codeErr;
			}
			specialQrcode.setBelongUser(userDto.getId());
			specialQrcode.setType("1");
			List<Business> busDto = specialQrcodeService.findBus(specialQrcode, null);
			List<SpecialQrcodeDTO> datasqc = specialQrcodeService.findSpecialQrcodeDTO(specialQrcode, null);
			if(busDto == null || busDto.size()<1 ){
				logger.info("*************当前二维码没有任何商品******************");
				List<Business> buss = businessService.findAllBus();
		         List<Goods> goods = businessService.findGoodsByBus();
		         String business = "";
		         String stro = "";
		         String busStr1 = "";
		         if(buss.size()>0 && goods.size()>0){
		         	for(int i=0; i<buss.size(); i++){
		         		for(int j=0; j<goods.size(); j++){
		         			if(j==0){
		         				stro = buss.get(i).getBusCode()+":"+",";
		         			}
		         			if(goods.get(j).getBusId().equals(buss.get(i).getId())){
		         				busStr1 = busStr1 + goods.get(j).getFeeCode()+":"+buss.get(i).getBusCode()+",";
		         			}
		         		}
		         		business = business + stro + busStr1;
		         		stro = "";
		         		busStr1 = "";
		         	}
		         }
		     
		         UserBusinessDTO ubDto =null;
		         if(StringUtils.isNotEmpty(business)&&StringUtils.isNotEmpty(qrcode.getBelongUser())){
		            ubDto= userBusiness(business,qrcode.getBelongUser());
		         }
		         
				String param = JSON.toJSONString(ubDto);
				jsonString = param;
//				return specEmpty;
			}else{
				
			
				if(datasqc==null || datasqc.size()<1){
					logger.info("*************当前二维码没有任何商品******************");
					return specEmpty;
				}
				 String business = "";
		         String stro = "";
		         String busStr1 = "";
		         if(busDto.size()>0 && datasqc.size()>0){
		         	for(int i=0; i<busDto.size(); i++){
		         		for(int j=0; j<datasqc.size(); j++){
		         			if(j==0){
		         				stro = busDto.get(i).getBusCode()+":"+",";
		         			}
		         			if(datasqc.get(j).getBusId().equals(busDto.get(i).getId())){
		         				busStr1 = busStr1 + datasqc.get(j).getFeeCode()+":"+busDto.get(i).getBusCode()+",";
		         			}
		         		}
		         		business = business + stro + busStr1;
		         		stro = "";
		         		busStr1 = "";
		         	}
		         }
		     
		         UserBusinessDTO ubDto =null;
		         if(StringUtils.isNotEmpty(business)&&StringUtils.isNotEmpty(qrcode.getBelongUser())){
		            ubDto= userBusiness(business,qrcode.getBelongUser());
		         }
		         
				String param = JSON.toJSONString(ubDto);
				jsonString = param;
			}	
		}else{
			logger.info("********普通二维码*****************");
			jsonString = qrcode.getParamStr();
		}
		return jsonString;
	}

	/**
	 * 业务详情页面
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/businessDetail")
	public String businessDetail(HttpServletRequest request, Model model) throws Exception{
		logger.info("param="+request.getParameter("param"));
		String param = Base64Util.toStringHex(request.getParameter("param"));//获取参数
		String[] params = param.split(",");
		Map<String,String> paramMap = new HashMap<String,String>();
		for (String p:params) {
			String[] s = p.split(":");
			paramMap.put(s[0], s[1]);
		}
		
		String busType = paramMap.containsKey("busType")?paramMap.get("busType"):"";//活动类型
		String goodsId = paramMap.containsKey("goodsId")?paramMap.get("goodsId"):"";//商品id
		String busCode = paramMap.containsKey("busCode")?paramMap.get("busCode"):"";//业务编码 
		String feeCode = paramMap.containsKey("feeCode")?paramMap.get("feeCode"):"";//资费代码
		String opId = paramMap.containsKey("opId")?paramMap.get("opId"):"";//营业员工号
		String flowNo = (String) request.getSession().getAttribute(Const.QRCODE_PARAM_STR);
		if (StringUtils.isBlank(flowNo)) {
			flowNo =  paramMap.containsKey("flowNo")?paramMap.get("flowNo"):"";
		}
		String detailurl = "";
		
		Business onlyBus = new Business();
		onlyBus.setBusCode(busCode);
		List<Business> nBusList = businessService.findByBus(onlyBus);
		if ("200".equals(busType) && null!=nBusList && nBusList.size()!=0) {
			onlyBus = nBusList.get(0);
			
			List<FrontListDTO> gList = new ArrayList<FrontListDTO>();
			String jsonString = this.getJsonString(flowNo);
			//将链接中的json字符串转换成bean对象
			UserBusinessDTO userBusinessDTO = JSONObject.parseObject(jsonString, UserBusinessDTO.class);
			List<BusinessGoodsDTO> list = userBusinessDTO.getBusGoodsList();
			for(int i=0;i<list.size();i++){
				BusinessGoodsDTO businessGoodsDTO = list.get(i);
				if (busCode.equals(businessGoodsDTO.getBusinessCode())) {
					List<String> sList = businessGoodsDTO.getGoodsList();
					if(null!=sList){
						for(int j=0;j<sList.size();j++){
							String fc = sList.get(j);
							//查询该营业员下可办理的商品对象集合
							FrontListDTO dto = businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCodeAndBusType(busCode, opId,fc,busType);
							if(null!=dto){
								if(Constant.ZERO.equals(dto.getBusState())){
									logger.info("[{}]业务为无效状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
								}
								if(Constant.ZERO.equals(dto.getBusUpDown())){
									logger.info("[{}]业务为下架状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
								}
								if(Constant.ZERO.equals(dto.getGoodsState())){
									logger.info("[{}]商品为无效状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
								}
								if(Constant.ZERO.equals(dto.getGoodsUpDown())){
									logger.info("[{}]商品为下架状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
								}
								if(Constant.ONE.equals(dto.getBusState()) && Constant.ONE.equals(dto.getBusUpDown()) && Constant.ONE.equals(dto.getGoodsState()) && Constant.ONE.equals(dto.getGoodsUpDown())){
										gList.add(dto);
								}
							}
							
						}
					}
				}
			}
			model.addAttribute("business",onlyBus);
			model.addAttribute("goodsList",gList);
			detailurl = "/sinova/frontqrcode/yhhdDetail";
		} else {
			if(StringUtils.isNotBlank(goodsId)){
				Goods goods = goodsService.findGoodsById(goodsId);
				if(null!=goods){
					model.addAttribute("goods",goods);
					model.addAttribute("feeCode",feeCode);
				}else{
					logger.info("没有查出结果");
					model.addAttribute("goods",goods);
					return errorUrl;
				}
			}
			detailurl = "/sinova/frontqrcode/businessDetail";
		}
		model.addAttribute("busType", busType);
		model.addAttribute("busCode",busCode);
		model.addAttribute("opId",opId);
		model.addAttribute("param", flowNo);
		return detailurl;
	}
	
	/**
	 * 业务详情页面办理按钮
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dealingBusiness")
	public String dealingBusiness(HttpServletRequest request, Model model) throws Exception{
		request.getSession().removeAttribute(Const.RESUBMIT_DATE_TAG);//清除重复提交标识
		String goodsId = request.getParameter("goodsId");//商品id
		String busCode = request.getParameter("busCode");//业务编码 
		String feeCode = request.getParameter("feeCode");//资费代码
		String opId = request.getParameter("opId");//营业员工号
		String busType = request.getParameter("busType");//营业员工号
		String flowNo = (String) request.getSession().getAttribute(Const.QRCODE_PARAM_STR);
		
		model.addAttribute("goodsId", goodsId);
		model.addAttribute("busCode",busCode);
		model.addAttribute("feeCode",feeCode);
		model.addAttribute("opId",opId);
		model.addAttribute("busType",busType);
		model.addAttribute("param", flowNo);
		
		//读取配置文件 获取得到手机号开关 (0为关,1为开)
		String marker = pfCache.get(Const.SYSTEM_FILE, "getPhoneSwitch");
		logger.info("获取手机号开关：[{}]",marker);
		if("1".equals(marker)){//开
			if(StringUtils.isNotBlank(goodsId)){
				Goods goods = goodsService.findGoodsById(goodsId);
				if(null!=goods){
					model.addAttribute("goodsId",goodsId);
					model.addAttribute("goods",goods);
				}else{
					logger.info("没有查出结果");
					model.addAttribute("goods",goods);
					return errorUrl;
				}
			}
			//获取手机号
			String phoneNo = (String) request.getSession().getAttribute("USER_MOBILE");
			if(StringUtils.isBlank(phoneNo)){
				AutoGetPhone.autoGetPhone(request,sitechInterFace);//流量自动获取手机号
				phoneNo = (String) request.getSession().getAttribute("USER_MOBILE");
			}
			if(StringUtils.isNotBlank(phoneNo)){
				phoneNo = phoneNo.startsWith("86") ? phoneNo.substring(2, phoneNo.length()) : phoneNo; 
				logger.info("获取到的手机号码为:{}", phoneNo);
				
				//加密手机号
				DESPlus m = new DESPlus("y1h2x3x4");
				String backPhone = m.Encode(phoneNo);
				logger.info("encode:[{}]",backPhone);
				
				model.addAttribute("backP", backPhone);
				model.addAttribute("phone", phoneNo);
				
				String cDate = new Date().toString();
				model.addAttribute("cDate", cDate);
				request.getSession().setAttribute(Const.RESUBMIT_DATE_TAG, new Date().toString());
				return "sinova/frontqrcode/handleLast";
			}else{
				logger.info("===没有获取到手机号===");
				return "sinova/frontqrcode/fillPhone";
			}
		
		}else{//关
			logger.info("===获取手机号开关为关闭状态===");
			return "sinova/frontqrcode/fillPhone";
		}
	}
	
	/**
	 * 输入手机号
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/valetPhone")
	public String valetPhone(HttpServletRequest request, Model model) throws Exception{
		
		String phone = request.getParameter("phone");
		String opId = request.getParameter("opId");
		String type = request.getParameter("type");
		String goodsId = request.getParameter("goodsId");
		String busCode = request.getParameter("busCode");
		String feeCode = request.getParameter("feeCode");
		String flowNo = (String) request.getSession().getAttribute(Const.QRCODE_PARAM_STR);
		//关联商品--------------------------start
		String flag = request.getParameter("flag");
		String goodsName = "";
		if("RELATION".equals(flag)){
			logger.info("关联商品一键办理，获取短信验证码。");
			try{
				if (StringUtils.isNotBlank(goodsId)) {
					feeCode = "";
					busCode = "";
					String[] rs = goodsId.split(",");
					if (rs != null && rs.length > 1) {
						List<String> ids = Arrays.asList(rs);
						for(int i=0; i<ids.size(); i++){
							Goods goods = goodsService.findGoodsById(ids.get(i));
							if(null!=goods){
								Business business = businessService.findBusinessById(goods.getBusId());
								if(null != business){
									if(i == ids.size()-1){
										busCode = busCode + business.getBusCode();
										goodsName = goodsName + goods.getGoodsName();
										feeCode = feeCode + goods.getFeeCode();
									}else{
										busCode =  busCode + business.getBusCode()+",";
										goodsName = goodsName + goods.getGoodsName()+",";
										feeCode = feeCode + goods.getFeeCode()+",";
									}
								}
							}else{
								logger.info("关联商品,没有查出结果");
								return errorUrl;
							}
						}
						model.addAttribute("goodsName", goodsName);
						model.addAttribute("flag", flag);
						String cDate = new Date().toString();
						model.addAttribute("cDate", cDate);
						request.getSession().setAttribute(Const.RESUBMIT_DATE_TAG, new Date().toString());
					}
					
				}
			}catch(Exception e){
				logger.info("关联商品,没有查出结果");
				return errorUrl;
			}
		}else{//普通办理
			if(StringUtils.isNotBlank(goodsId)){
				Goods goods = goodsService.findGoodsById(goodsId);
				if(null!=goods){
					model.addAttribute("goods",goods);
				}else{
					logger.info("没有查出结果");
					model.addAttribute("goods",goods);
					return errorUrl;
				}
			}
		}
//		if(StringUtils.isNotBlank(type) && Constant.ONE.equals(type)){
		request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
//		}
//		if(null == request.getSession().getAttribute(Const.RAND_IMAGE_VALIDATE)){
			this.sendVCode(request, phone);
//		}
		
		//加密手机号
		DESPlus m = new DESPlus("y1h2x3x4");
		String backPhone = "";
		if(StringUtils.isNotBlank(phone)){
			backPhone = m.Encode(phone);
			logger.info("encode:[{}]",backPhone);
			model.addAttribute("backP", backPhone);
		}
		
		model.addAttribute("phone", phone);
		model.addAttribute("opId", opId);
		model.addAttribute("goodsId", goodsId);
		model.addAttribute("busCode",busCode);
		model.addAttribute("feeCode",feeCode);
		model.addAttribute("param", flowNo);
		
		String mins = "";
		if("RELATION".equals(flag)){
			logger.info("关联商品一键办理，获取短信验证码倒计时。");
		}else{
			mins = (String) request.getSession().getAttribute(phone+"Session");
		}
		
		if(StringUtils.isBlank(mins)){
			request.getSession().setAttribute(phone+"Session", new Date().getTime()+"");
			mins = "30";
		}else{
			Long l = ((new Date()).getTime() - Long.valueOf(mins));
			if(l<30000){
				l = ( 30000 - l)/1000;
			}else{
				l = 30L;
			}
			mins = l+"";
		}
		model.addAttribute("countDown", mins);
		
		if("RELATION".equals(flag)){
			logger.info("关联商品一键办理，获取短信验证码结束。");
			return "sinova/frontqrcode/relationCode";
		}else{
			return "sinova/frontqrcode/fillValidateCode";
		}
	}
	
	/**
	 * 输入营业员工号
	 * @param request
	 * @param model
	 * @return
	 */
	/*@RequestMapping("/fillCode")
	public String fillCode(HttpServletRequest request, Model model){
		String code = request.getParameter("code");
		String phone = request.getParameter("phone");
		if(StringUtils.isNotBlank(code)){
			
			model.addAttribute("phone",phone);
			return "sinova/frontqrcode/blqr2";
		}else{
			logger.info("===没有获取到营业员工号===");
			model.addAttribute("phone", phone);
			return "sinova/frontqrcode/blqr";
		}
		
	}*/
	
	/**
	 * 验证手机验证码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkVCode")
	@ResponseBody
	public ReturnDatas checkVCode(HttpServletRequest request, Model model){
//		return new ReturnDatas(Constant.ONE,"");
		if(null == request.getSession().getAttribute(Const.RAND_IMAGE_VALIDATE)){
			return new ReturnDatas(Constant.ONE_NEGATIVE,"");
		}
		
		String vCode = request.getParameter("vCode");
		String sCode = (String) request.getSession().getAttribute(Const.RAND_IMAGE_VALIDATE);
		
		if(StringUtils.isNotBlank(vCode) && StringUtils.isNotBlank(sCode) && vCode.equals(sCode)){
			request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
			return new ReturnDatas(Constant.ONE,"");
		}
		return new ReturnDatas(Constant.ZERO,"");
	}
	
	/**
	 * 验证手机号是否为山西移动用户
	 * @param request
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/checkPhone")
	@ResponseBody
	public ReturnDatas checkPhone(HttpServletRequest request, Model model){
		String mobile = request.getParameter("mobile");
		logger.info("checkPhone");
//		return new ReturnDatas(Constant.ONE,"");

		if(StringUtils.isNotBlank(mobile)){
			try {
				return new ReturnDatas(sitechInterFace.isSXUser(mobile),"");
			} catch (Exception e) {
				logger.error("checkPhone error" + e.getLocalizedMessage());
				return new ReturnDatas(Constant.ONE_NEGATIVE,"");
			}
			
		}else{
			logger.info("checkMobiele  fail");
			return new ReturnDatas(Constant.ONE_NEGATIVE,"");
		}
		
	}
	
		/**
	 * 发送短信验证码
	 * @param request
	 * @param mobile
	 */
	public void sendVCode(HttpServletRequest request,String mobile){
		logger.info("       sendVCode          ");
		request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
		
		String vCode = Util.getrannumber();
		logger.info("vCode = " +vCode);
		String con = Const.SEND_VERIFICATION_CODE + vCode + "。";
		try {
			this.sitechInterFace.smsSendInfo(mobile,con);
			request.getSession().setAttribute(Const.RAND_IMAGE_VALIDATE, vCode);
			request.getSession().setMaxInactiveInterval(5*60);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sendVCode    "+e.getMessage());
		}
		
	}
	
	/**
	 * 办理业务前确认
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/beforeSubmitBusiness")
	public String beforeSubmitBusiness(HttpServletRequest request, Model model) throws Exception{
		
		String phone = request.getParameter("phone");
		String opId = request.getParameter("opId");
		String goodsId = request.getParameter("goodsId");
		String busCode = request.getParameter("busCode");//业务编码 
		String feeCode = request.getParameter("feeCode");//资费代码
		String backP = request.getParameter("backP");//手机号对比
		String flowNo = (String) request.getSession().getAttribute(Const.QRCODE_PARAM_STR);
		if(StringUtils.isNotBlank(goodsId)){
			Goods goods = goodsService.findGoodsById(goodsId);
			if(null!=goods){
				model.addAttribute("goods",goods);
			}else{
				logger.info("没有查出结果");
				return errorUrl;
			}
		}
		
		String cDate = new Date().toString();
		model.addAttribute("cDate", cDate);
		request.getSession().setAttribute(Const.RESUBMIT_DATE_TAG, new Date().toString());
		model.addAttribute("phone", phone);
		model.addAttribute("opId", opId);
		model.addAttribute("goodsId", goodsId);
		model.addAttribute("busCode",busCode);
		model.addAttribute("feeCode",feeCode);
		model.addAttribute("backP", backP);
		model.addAttribute("param", flowNo);
		
		return "sinova/frontqrcode/handleLast";
	}
	
	/*
	 * 业务办理
	 */
	@RequestMapping("/submitBusiness")
	public String submitBusiness(HttpServletRequest request, Model model) throws Exception{
		
		String phone = request.getParameter("phone");
		String opId = request.getParameter("opId");
		String goodsId = request.getParameter("goodsId");
		String busCode = request.getParameter("busCode");//业务编码 
		String feeCode = request.getParameter("feeCode");//资费代码
		String backP = request.getParameter("backP");//手机号对比
		String flag = request.getParameter("flag");//根据标识判断是否是一键办理
		//新增
		String developNo = request.getParameter("developNo");//推荐工号
		
		String flowNo = (String) request.getSession().getAttribute(Const.QRCODE_PARAM_STR);
		String cDate = request.getParameter("cDate");//对比时间标识
		String sDate = (String) request.getSession().getAttribute(Const.RESUBMIT_DATE_TAG);
		if(StringUtils.isNotBlank(goodsId) && StringUtils.isNotBlank(opId) && StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(busCode) && StringUtils.isNotBlank(feeCode) && StringUtils.isNotBlank(backP)){
		if("RELATION".equals(flag)){
			logger.info("一键办理关联商品，开始校验商品！");
			Goods goods = new Goods();
			String[] rs = goodsId.split(",");
			String[] bus = busCode.split(",");
			String[] fee = feeCode.split(",");
			if (rs != null && rs.length > 1 && bus != null && bus.length>1 && fee != null && fee.length >1) {
				List<String> ids = Arrays.asList(rs);
				List<String> buss = Arrays.asList(bus);
				List<String> fees = Arrays.asList(fee);
				for(int i=0; i<ids.size(); i++){
					goodsId = ids.get(i);
					feeCode = fees.get(i);
					busCode = buss.get(i);
					
					FrontListDTO dto = businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCode(busCode, opId, feeCode);
					if(null!=dto){
						if(Constant.ONE.equals(dto.getBusState()) && Constant.ONE.equals(dto.getBusUpDown()) && Constant.ONE.equals(dto.getGoodsState()) && Constant.ONE.equals(dto.getGoodsUpDown())){
							
							goods = goodsService.findGoodsById(goodsId);
							if(null!=goods){
								if(cDate.equals(sDate)){
									//清除重复提交标识
									request.getSession().removeAttribute(Const.RESUBMIT_DATE_TAG);
									//进行正常业务流程
									//解密手机号 对比
									DESPlus m = new DESPlus("y1h2x3x4");
									String tempPhone = m.Decode(backP).toString().trim();
									logger.info("decode:[{}]",tempPhone);
									if(tempPhone.equals(phone)){
										logger.info("符合办理条件，执行办理动作");
										logger.info("phone:[{}]",phone);
										logger.info("tempPhone:[{}]",tempPhone);

									}else{
										logger.info("对比手机号失败!");
										model.addAttribute("goods",goods);
										return errorUrl;
									}
								}else{
									//进行防重复提交处理流程
									logger.info("重复提交");
									return "sinova/frontqrcode/handleResubmit";
								}
								
							}else{
								logger.info("没有查出结果");
								model.addAttribute("goods",goods);
								return errorUrl;
								
							}
						}else{
							if(Constant.ZERO.equals(dto.getBusState())){
								logger.info("[{}]业务为无效状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
							}
							if(Constant.ZERO.equals(dto.getBusUpDown())){
								logger.info("[{}]业务为下架状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
							}
							if(Constant.ZERO.equals(dto.getGoodsState())){
								logger.info("[{}]商品为无效状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
							}
							if(Constant.ZERO.equals(dto.getGoodsUpDown())){
								logger.info("[{}]商品为下架状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
							}
							
							return errorUrl;
						}
					}else{
						logger.info("未能查出符合条件的商品对象");
						return errorUrl;
					}
				}
				//校验完成，开始办理
				String goodsFail = "";//失败的商品名称
				String goodsSucc = "";//成功的商品名称
				for(int i=0; i<ids.size(); i++){
					logger.info("关联商品校验成功，开始办理！");
					goodsId = ids.get(i);
					feeCode = fees.get(i);
					busCode = buss.get(i);
					FrontListDTO dto = businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCode(busCode, opId, feeCode);
					String mess = "";
			    if ("200".equals(dto.getBusType())){//其他                                                                           
					/*
					 * 判断是否是营销活动，如果是营销活动，需要进行验证
					 */
				    S4035IntChkRequest sRequest = new S4035IntChkRequest();
					sRequest.setActID(dto.getBusCode());
					sRequest.setMeansID(dto.getGoodsFeeCode());
					sRequest.setPhoneNo(phone);
					sRequest.setLoginNO(opId);
					S4035IntChkResponse sResponse = sitechInterFace.s4035intChk(sRequest, dto.getGoodsName());
					if (!"succ".equals(sResponse.getReturnCode())) {
						goodsFail = goodsFail + dto.getGoodsName()+ "、";
						break;
					}
				
					/*
					 * 办理业务
					 */
			        S4035IntCfmRequest request1 = new S4035IntCfmRequest();
					request1.setServiceNo(phone);
				    request1.setMasterServId("1001");
					request1.setProvinceGroup("10011");
					request1.setOpenTime(new SimpleDateFormat("yyyyMMdd").format(new Date()));
					request1.setLoginNo(opId);
					request1.setActId(dto.getBusCode());
					request1.setMeansId(dto.getGoodsFeeCode());
					request1.setChannelType("11");//渠道编号
					request1.setRecommendNo("");
					S4035IntCfmResponse resp = this.sitechInterFace.s4035intCfm(request1);
					mess = ("0".equals(resp.getReturnCode())?"1:":"0:")+resp.getDetailMsg()+":"+resp.getCreateAccept();
				}
				
					try {
						String[] result = mess.split(":");
						logger.info("submitBusiness interface resultCode = " + result[0]);
						logger.info("submitBusiness interface resultMess = " + result[1]);
						logger.info("submitBusiness interface resultFlowNo = " + result[2]);
						if(Constant.ONE.equals(result[0])){
							try{
								//保存数据到受理记录表
								AcceptRecord acceptRecord = new AcceptRecord();
								acceptRecord.setAccount(opId);//账号
								acceptRecord.setMobile(phone);
								acceptRecord.setOrderId(result[2]);//流水号
								acceptRecord.setBusId(dto.getBusId());
								acceptRecord.setFeeCode(feeCode);//资费代码
								acceptRecord.setDatetime(new Date());//受理时间
								String acceptId = (String) acceptRecordService.save(acceptRecord);
								
								model.addAttribute("acceptId", acceptId);
								request.getSession().setAttribute("blphone", phone);
								
								Finder finder = Finder.getSelectFinder(User.class);
								finder.append(" where workno =:workno ");
								finder.setParam("workno", opId);
								User user = userService.queryForObject(finder ,User.class);
								String phoneNo = user.getMobile();
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
								String date = df.format(new Date());
								this.sitechInterFace.smsSendInfo(phoneNo,"您好，手机号为"+phone+"的客户在"+date+"成功办理"+dto.getBusName()+"业务!");
								
								goodsSucc = goodsSucc + dto.getGoodsName()+" 、";
							}catch(Exception e){
								logger.info("保存数据到受理记录表异常");
								logger.info("异常信息:{}",e.getMessage());
								goodsFail = goodsFail + dto.getGoodsName()+ "、";
								break;
							}
						}
						if(Constant.ZERO.equals(result[0])){
							//保存数据到受理记录表
							/*AcceptFail acceptFail = new AcceptFail();
							acceptFail.setWorkno(opId);//账号
							acceptFail.setMobile(phone);
							acceptFail.setBusId(dto.getBusId());
							acceptFail.setFeeCode(feeCode);//资费代码
							acceptFail.setDatetime(new Date());//受理时间
							acceptFail.setMessage(result[1]);
							acceptFailService.save(acceptFail);*/
								
							goodsFail = goodsFail + dto.getGoodsName()+ "、";
							break;
						}
					} catch (Exception e) {
						logger.error("submitBusiness interface "+e.getMessage());
						goodsFail = goodsFail + dto.getGoodsName()+ "、";
						break;
					}
				}	
				request.getSession().removeAttribute(Const.QRCODE_PARAM_STR);
				if(StringUtils.isNotBlank(goodsSucc)){
					model.addAttribute("goodsSucc", goodsSucc.substring(0,goodsSucc.length()-1));
				}
				goods.setEffectWay("");
				if(StringUtils.isNotBlank(goodsFail)){
					model.addAttribute("goodsFail", goodsFail.substring(0,goodsFail.length()-1));
				}
				model.addAttribute("goods", goods);
				model.addAttribute("phone", phone);
				model.addAttribute("param", flowNo);
				return "sinova/frontqrcode/relationSuccess";
			} 
			//一键校验办理---------------end
		}else{
				String phoneNo = "";//客户手机号
				FrontListDTO dto = businessService.getFrontListDTOByBusCodeAndWorknoAndFeeCode(busCode, opId, feeCode);
				if(null!=dto){
					if(Constant.ONE.equals(dto.getBusState()) && Constant.ONE.equals(dto.getBusUpDown()) && Constant.ONE.equals(dto.getGoodsState()) && Constant.ONE.equals(dto.getGoodsUpDown())){
						
						Goods goods = goodsService.findGoodsById(goodsId);
						if(null!=goods){
							if(cDate.equals(sDate)){
								//清除重复提交标识
								request.getSession().removeAttribute(Const.RESUBMIT_DATE_TAG);
								//进行正常业务流程
								//解密手机号 对比
								DESPlus m = new DESPlus("y1h2x3x4");
								String tempPhone = m.Decode(backP).toString().trim();
								logger.info("decode:[{}]",tempPhone);
								if(tempPhone.equals(phone)){
									logger.info("符合办理条件，执行办理动作");
									logger.info("phone:[{}]",phone);
									logger.info("tempPhone:[{}]",tempPhone);
									String mess = "";
									//判断业务类型 调接口办理业务
									if("main".equals(dto.getBusType())){//主套餐
										S4000Cfm_BReruest re = new S4000Cfm_BReruest();
										BusiInfo binfo = new BusiInfo();
										binfo.setProdPrcId(goods.getFeeCode());
										binfo.setOperateType("U");
										re.setLoginNO(opId);
										re.setServiceNo(phone);
										re.setLoginPwd("");
										re.setOpCode("4000");//操作代码
										re.setReConfirm("0");
										re.setMasterServId("1001");
										re.setProvinceGroup("10011");
										re.setBusiInfo(binfo);
										mess = this.sitechInterFace.s4000cfm(re);
										//接口改造
										/*New_S4000Cfm_BReruest re = new New_S4000Cfm_BReruest();
										re.setLoginNo(opId);
										re.setServiceNo(phone);
										re.setLoginPwd("");
										re.setProdPrcId(goods.getFeeCode());
										re.setOperateType("U");
										re.setOpCode("4000");//操作代码
										re.setMasterServId("1001");
										re.setProvinceGroup("10011");
										re.setDevelopNo("");//推荐工号
										mess = this.sitechInterFace.new_s4000cfm(re);*/
									}else if("flow".equals(dto.getBusType()) || //数据流量
											 "characteristic".equals(dto.getBusType())|| //移动特色
											 "message".equals(dto.getBusType())|| //短彩信 
											 "servicesetting".equals(dto.getBusType())||//服务设置
											 "familybusiness".equals(dto.getBusType())//家庭业务
											){
										S4000Cfm_BReruest re = new S4000Cfm_BReruest();
										BusiInfo binfo = new BusiInfo();
										binfo.setProdPrcId(goods.getFeeCode());
										binfo.setOperateType("A");
										re.setLoginNO(opId);
										re.setServiceNo(phone);
										re.setLoginPwd("");
										re.setOpCode("");
										re.setReConfirm("0");
										re.setMasterServId("1001");
										re.setProvinceGroup("10011");
										re.setBusiInfo(binfo);
										mess = this.sitechInterFace.s4000cfm(re);
										//接口改造
										/*New_S4000Cfm_BReruest re = new New_S4000Cfm_BReruest();
										re.setLoginNo(opId);
										re.setServiceNo(phone);
										re.setLoginPwd("");
										re.setProdPrcId(goods.getFeeCode());
										re.setOperateType("A");
										re.setOpCode("4000");//操作代码
										re.setMasterServId("1001");
										re.setProvinceGroup("10011");
										re.setDevelopNo("");//推荐工号
										mess = this.sitechInterFace.new_s4000cfm(re);*/
									}else if("200".equals(dto.getBusType())){//其他
										/*
										 * 判断是否是营销活动，如果是营销活动，需要进行验证
										 */
										S4035IntChkRequest sRequest = new S4035IntChkRequest();
										sRequest.setActID(dto.getBusCode());
										sRequest.setMeansID(dto.getGoodsFeeCode());
										sRequest.setPhoneNo(phone);
										//sRequest.setPhoneNo("13903490096");
										sRequest.setLoginNO(opId);
	//									sRequest.setLoginNO("fhhh0M");
										S4035IntChkResponse sResponse = sitechInterFace.s4035intChk(sRequest, dto.getGoodsName());
										if (!"succ".equals(sResponse.getReturnCode())) {
											return errorUrl;
										}
										/*
										 * 办理业务
										 */
										S4035IntCfmRequest request1 = new S4035IntCfmRequest();
										request1.setServiceNo(phone);
										//request1.setServiceNo("13903490096");
									    request1.setMasterServId("1001");
										request1.setProvinceGroup("10011");
										request1.setOpenTime(new SimpleDateFormat("yyyyMMdd").format(new Date()));
										request1.setLoginNo(opId);
										//request1.setLoginNo("fhhh0M");
										request1.setActId(dto.getBusCode());
										request1.setMeansId(dto.getGoodsFeeCode());
										//request1.setChannelType("11");//渠道编号
										request1.setChannelType("0");//渠道编号
										request1.setRecommendNo("");
										S4035IntCfmResponse resp = this.sitechInterFace.s4035intCfm(request1);
										mess = ("0".equals(resp.getReturnCode())?"1:":"0:")+resp.getDetailMsg()+":"+resp.getCreateAccept();
									}
									
									try {
										
										String[] result = mess.split(":");
										logger.info("submitBusiness interface resultCode = " + result[0]);
										logger.info("submitBusiness interface resultMess = " + result[1]);
										logger.info("submitBusiness interface resultFlowNo = " + result[2]);
										if(Constant.ONE.equals(result[0])){
											try{
												//保存数据到受理记录表
												AcceptRecord acceptRecord = new AcceptRecord();
												acceptRecord.setAccount(opId);//账号
												acceptRecord.setMobile(phone);
												acceptRecord.setOrderId(result[2]);//流水号
												acceptRecord.setBusId(dto.getBusId());
												acceptRecord.setFeeCode(feeCode);//资费代码
												acceptRecord.setDatetime(new Date());//受理时间
												String acceptId = (String) acceptRecordService.save(acceptRecord);
												
												model.addAttribute("acceptId", acceptId);
												request.getSession().setAttribute("blphone", phone);
												
												Finder finder = Finder.getSelectFinder(User.class);
												finder.append(" where workno =:workno ");
												finder.setParam("workno", opId);
												User user = userService.queryForObject(finder ,User.class);
												phoneNo = user.getMobile();
												SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
												String date = df.format(new Date());
												this.sitechInterFace.smsSendInfo(phoneNo,"您好，手机号为"+phone+"的客户在"+date+"成功办理"+dto.getBusName()+"业务!");
												
												request.getSession().removeAttribute(Const.QRCODE_PARAM_STR);
											}catch(Exception e){
												logger.info("保存数据到受理记录表异常");
												logger.info("异常信息:{}",e.getMessage());
												return errorUrl;
											}
											
											//判断主套餐是否抽过奖
											String busType = this.businessService.findBusinessById(dto.getBusId()).getBusType();
											if ("main".equals(busType)) {
												//当前自然月是否办理过主套餐
										        List<AcceptRecord> accList = this.acceptRecordService.validateLottery(phone);
										        if (accList!=null && accList.size()>1) {
										        	logger.info("当前自然月办理过主套餐，不允许重复抽奖");
										        	model.addAttribute("canlottery", "false");
										        } else {
										        	model.addAttribute("canlottery", "true");
										        }
											} else if ("200".equals(busType)) {
												logger.info("营销活动不允许抽奖！");
									        	model.addAttribute("canlottery", "false");
											} else {
												model.addAttribute("canlottery", "true");
											}
											
											model.addAttribute("goods", goods);
											model.addAttribute("phone", phone);
											model.addAttribute("param", flowNo);
											model.addAttribute("opId", opId);
											//关联商品---------------------------------------new
											try {
												logger.info("办理业务成功，查询是否有关联商品");
												UserOffice userOffice = goodsActService.findOwnByWorkno(opId);
												if(userOffice != null){
													String regionCode = userOffice.getRegionCode();
													List<Goods> goodsDto = goodsActService.findGoodsById(dto.getGoodsId(), regionCode, phoneNo);
													if(goodsDto != null && goodsDto.size()>0){
														logger.info("该商品有关联商品");
														model.addAttribute("goodsDto", goodsDto);
														request.getSession().setAttribute(Const.QRCODE_PARAM_STR, flowNo);
													}
												}
											} catch (Exception e) {
												logger.info("********查询关联商品失败**************");
											}
											return "sinova/frontqrcode/handleSuccess";
										}
										if(Constant.ZERO.equals(result[0])){
	//										//保存数据到受理记录表
											/*AcceptFail acceptFail = new AcceptFail();
											acceptFail.setWorkno(opId);//账号
											acceptFail.setMobile(phone);
											acceptFail.setBusId(dto.getBusId());
											acceptFail.setFeeCode(feeCode);//资费代码
											acceptFail.setDatetime(new Date());//受理时间
											acceptFail.setMessage(result[1]);
											acceptFailService.save(acceptFail);*/
											
											model.addAttribute("goods", goods);
											model.addAttribute("phone", phone);
											model.addAttribute("param", flowNo);
											model.addAttribute("failMsg", result[1]);
											return "sinova/frontqrcode/handleFail";
										}
									} catch (Exception e) {
										logger.error("submitBusiness interface "+e.getMessage());
										model.addAttribute("goods",goods);
										return errorUrl;
									}
								}else{
									logger.info("对比手机号失败!");
									model.addAttribute("goods",goods);
									return errorUrl;
								}
							}else{
								//进行防重复提交处理流程
								logger.info("重复提交");
								return "sinova/frontqrcode/handleResubmit";
							}
						}else{
							logger.info("没有查出结果");
							model.addAttribute("goods",goods);
							return errorUrl;
							
						 }
					}else{
						if(Constant.ZERO.equals(dto.getBusState())){
							logger.info("[{}]业务为无效状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
						}
						if(Constant.ZERO.equals(dto.getBusUpDown())){
							logger.info("[{}]业务为下架状态,业务编码为[{}]",dto.getBusName(),dto.getBusCode());
						}
						if(Constant.ZERO.equals(dto.getGoodsState())){
							logger.info("[{}]商品为无效状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
						}
						if(Constant.ZERO.equals(dto.getGoodsUpDown())){
							logger.info("[{}]商品为下架状态,商品资费代码为[{}]",dto.getGoodsName(),dto.getGoodsFeeCode());
						}
						
						return errorUrl;
					}
				}else{
					logger.info("未能查出符合条件的商品对象");
					return errorUrl;
				}
			}
		}
		
		return errorUrl;
	}
	
	/**
	 * 跳转到抽奖页面
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/beforeLottery")
	public String beforeLottery(HttpServletRequest request, Model model) throws Exception{
		String flowNo = request.getParameter("flowNo");
		String acceptId = request.getParameter("acceptId");
		String phone = request.getParameter("phone");
		String opId = request.getParameter("opId");
		String blphone = (String) request.getSession().getAttribute("blphone");
		Integer lotteryCount = rotateService.getLotteryCount();
		model.addAttribute("lotteryCount", lotteryCount);
		//如果手里记录id为空，返回错误页面
		if(StringUtils.isBlank(acceptId)) {
			logger.info("受理记录id为空");
			model.addAttribute("canlottery", "false");
			model.addAttribute("flowNo", flowNo);
			return lotteryurl;
		}
		//查询是否存在该业务办理记录
		AcceptRecord accept = acceptRecordService.findAcceptRecordById(acceptId);
		if (accept==null) {
			logger.info("受理记录不存在");
			model.addAttribute("canlottery", "false");
			model.addAttribute("flowNo", flowNo);
			return lotteryurl;
		}
		
		//如果手机号不存在，提示先办理
		if(StringUtils.isBlank(blphone) || !phone.equals(blphone)) {
			logger.info("手机号错误，session中不存在，或与session中不一致");
			model.addAttribute("canlottery", "false");
			model.addAttribute("flowNo", flowNo);
			return lotteryurl; 
		}
		
		//判断主套餐是否抽过奖
		String BusType = this.businessService.findBusinessById(accept.getBusId()).getBusType();
		if ("main".equals(BusType)) {
			//当前自然月是否办理过主套餐
	        List<AcceptRecord> accList = this.acceptRecordService.validateLottery(phone);
	        if (accList!=null && accList.size()>1) {
	        	logger.info("当前自然月办理过主套餐，不允许重复抽奖");
	        	model.addAttribute("canlottery", "false");
				model.addAttribute("flowNo", flowNo);
				return lotteryurl; 
	        }
		}
		
		/*
		 * 查询是否已经抽过奖
		 * 如果抽过，页面显示中奖信息，如果未抽过，页面可以进行抽奖
		 */
		String prizeImg = "";
		List<RotateDTO> rotateList = rotateService.findRoateListByAcceptId(acceptId);
		if (rotateList!=null && rotateList.size()!=0) {
			RotateDTO rotate = rotateList.get(0);
			model.addAttribute("rotateId", rotate.getId());
			model.addAttribute("isaccept", rotate.getLotteryed());
			prizeImg = this.getPrizeImg(rotate.getPrize());
			model.addAttribute("prizeImg", prizeImg);
			model.addAttribute("prizeName", rotate.getJinpin());
		} else {
			//加入memcached
//			boolean flag = CacheFactory.getCache().add(acceptId, phone);
//			if (flag) {
			
			if (!acceptMap.containsKey(acceptId)) {
				String prize = LotteryUtils.lottery();
				acceptMap.put(acceptId, prize);
				logger.info("抽奖结果===="+prize);
				//抽奖记录
				Rotate rotate = new Rotate();
				rotate.setCreateTime(new Date());
				rotate.setGroupId("10011");
				rotate.setBatCode("accept");
				rotate.setMobile(blphone);
				rotate.setPrize(prize.split("_")[0]);
				rotate.setAcceptId(acceptId);
				rotate.setLotteryed("0");	
				if (!"0".equals(prize)) {
					rotate.setState("1");//中奖
					rotate.setMeanId(prize.split("_")[2]);
				} else {//未中奖
					rotate.setState("0");//未中奖
				}
				prizeImg = this.getPrizeImg(prize.split("_")[0]);
				String rotateId = (String) rotateService.save(rotate);
				model.addAttribute("rotateId", rotateId);
				model.addAttribute("isaccept", "0");
				model.addAttribute("prizeImg", prizeImg);
				model.addAttribute("prizeName", "0".equals(prize)?"谢谢参与":prize.split("_")[1]);
			} else {
				logger.info("正在抽奖中");
			}
//			}
		}
		model.addAttribute("phone", phone);
		model.addAttribute("opId", opId);
		model.addAttribute("canlottery", "true");
		
		return lotteryurl;
	}
	
	private String getPrizeImg(String prize) {
		String prizeImg = "";
		switch (Integer.parseInt(prize)) {
		case 1:
			prizeImg = "10mb.gif";
			break;
		case 2:
			prizeImg = "50mb.gif";
			break;
		case 3:
			prizeImg = "100mb.gif";
			break;
		case 4:
			prizeImg = "500mb.gif";
			break;
		case 5:
			prizeImg = "10fz.gif";
			break;
		case 6:
			prizeImg = "50fz.gif";
			break;
		case 7:
			prizeImg = "100fz.gif";
			break;
		case 8:
			prizeImg = "500fz.gif";
			break;
		default:
			prizeImg = "xx.gif";
			break;
		}
		return prizeImg;
	}



	/**
	 * 抽奖业务办理
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lottery")
	@ResponseBody
	public ReturnDatas lottery(HttpServletRequest request, Model model) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		String opId = request.getParameter("opId");
		String rotateId = request.getParameter("rotateId");
		if(StringUtils.isBlank(opId)) {
			logger.info("工号为空");
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("工号为空");
			return returnObject;
		}
		if(StringUtils.isBlank(rotateId)) {
			logger.info("抽奖记录id为空");
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("抽奖记录id为空");
			return returnObject;
		}
		Rotate rotate = rotateService.findRotateById(rotateId);
		if (rotate==null) {
			logger.info("抽奖记录不存在");
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("抽奖记录不存在");
			return returnObject;
		}
		if ("1".equals(rotate.getLotteryed())) {
			logger.info("该奖项已经办理，不能重复办理");
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("该奖项已经办理，不能重复办理");
			return returnObject;
		}
		String phone = rotate.getMobile();
		String feeCode = rotate.getMeanId();
		
		rotate.setLotteryed("1");
		rotateService.update(rotate);
		if (!"0".equals(rotate.getPrize())) {
			logger.info("中奖");
			//调用斯特奇接口抽奖
			S4000Cfm_BReruest re = new S4000Cfm_BReruest();
			BusiInfo binfo = new BusiInfo();
			binfo.setProdPrcId(feeCode);
			binfo.setOperateType("A");
			re.setLoginNO(opId);
			re.setServiceNo(phone);
			re.setLoginPwd("");
			re.setOpCode("");
			re.setReConfirm("0");
			re.setMasterServId("1001");
			re.setProvinceGroup("10011");
			re.setBusiInfo(binfo);
			String mess = this.sitechInterFace.s4000cfm(re);
			//办理记录
			String[] result = mess.split(":");
			logger.info("submitBusiness interface resultCode = " + result[0]);
			logger.info("submitBusiness interface resultMess = " + result[1]);
			logger.info("submitBusiness interface resultFlowNo = " + result[2]);
			if(Constant.ONE.equals(result[0])){
					logger.info("办理成功");
					//保存数据到受理记录表
					AcceptRecord acceptRecord = new AcceptRecord();
					acceptRecord.setAccount(opId);//账号
					acceptRecord.setMobile(phone);
					acceptRecord.setOrderId(result[2]);//流水号
					acceptRecord.setFeeCode(feeCode);//资费代码
					acceptRecord.setDatetime(new Date());//受理时间
					acceptRecord.setRotateId(rotateId);
					acceptRecordService.save(acceptRecord);
			}
			if(Constant.ZERO.equals(result[0])){
				logger.info("办理失败");
				//保存数据到受理记录表
//				AcceptFail acceptFail = new AcceptFail();
//				acceptFail.setWorkno(opId);//账号
//				acceptFail.setMobile(phone);
//				acceptFail.setFeeCode(fee_code);//资费代码
//				acceptFail.setDatetime(new Date());//受理时间
//				acceptFail.setMessage(result[1]);
//				acceptFailService.save(acceptFail);
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage("正在办理中，请以短信提示为准！");
			}
		}
		request.getSession().removeAttribute("blphone");
		
		
		return returnObject;
	}
	
	
	 /**
     * 根据tree数据转为UserBusinessDTO,
     * 返回UserBusinessDTO格式数据
     * 
     * @param business
     * @param workNo
     * @throws Exception
     * @author <yyb>
     */
    @SuppressWarnings({ "unchecked" })
    public UserBusinessDTO userBusiness(String business,String workNo){
        UserBusinessDTO businessDto = new UserBusinessDTO();
        businessDto.setUserCode(workNo);
        String[] businessTemp =business.split(",");
        List list = new ArrayList();
        BusinessGoodsDTO goodstemp = null;
        String flag="";
        List<String> listTemp=null;
        for (int i = 0; i < businessTemp.length; i++) {
            String busTemp =businessTemp[i];
            if(StringUtils.isNotBlank(busTemp)){
                
                String id=busTemp.substring(0,busTemp.indexOf(":"));
                String pid =busTemp.substring(busTemp.indexOf(":")+1);
                if(!"".equals(flag)&&!id.equals(flag)&&StringUtils.isBlank(pid)){
                    goodstemp.setGoodsList(listTemp);
                    list.add(goodstemp);
                }
                
                if(StringUtils.isNotBlank(id)&& StringUtils.isBlank(pid)){
                    goodstemp=new BusinessGoodsDTO();
                    goodstemp.setBusinessCode(id);
                    listTemp = new ArrayList();
                    flag =id;
                } 
                if((StringUtils.isNotEmpty(flag)&&StringUtils.isNotEmpty(pid))||flag.equals(pid)){
                    listTemp.add(id);
                }
                
            }
        }
        if(businessTemp.length>0){
            goodstemp.setGoodsList(listTemp);
            list.add(goodstemp);
        }
        businessDto.setBusGoodsList(list);
        return businessDto;
    }
}
