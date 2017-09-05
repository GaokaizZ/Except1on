package org.springrain.sinova.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.DateUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.RotateDTO;
import org.springrain.sinova.entity.Rotate;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.LimitInfo;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.S4035IntCfmRequest;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.S4035IntCfmResponse;
import org.springrain.sinova.service.IExecuteTimeService;
import org.springrain.sinova.service.IRotateService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.Util;
import org.springrain.system.util.Constant;


@Controller
@RequestMapping(value = "/rotate")
public class RotateController extends BaseController{
	@Resource
	private ISitechInterface sitechInterFace;
	
	@Resource
	private IRotateService rotateServiceImpl;
	
	@Resource
	private IExecuteTimeService executeTimeService;
	
	private static int ROTATE_COUNT=9999;
	
	//抽奖活动编码_测试
	//public static final String ACT_ID = "201503231534386754";
	//抽奖活动编码_生产
	public static final String ACT_ID = "201503303543322162";
	//抽奖活动编码_生产测试
	//public static final String ACT_ID = "201503303544075712";
		
	//抽奖档次编码_测试
	//public static final String MEAN_ID = "201503231534386774";
	
	//抽奖档次编码_生产
	public static final String MEAN_ID = "201503303543322190";
	//抽奖档次编码_生产测试
	//public static final String MEAN_ID = "201503303544075726";
	
	public static final String  BAT_CODE="2";
	
	//商城工号
	public static final String LOGIN_NO = "ll1868";

	
	/**
	 * 首页的映射
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) throws Exception {
		String mobile = (String) request.getSession().getAttribute("USER_MOBILE");
		logger.info("RotateController=index=======Start=="+mobile);
		if(StringUtils.isBlank(mobile) ){
			this.autoGetPhone(request);
			mobile = (String) request.getSession().getAttribute("USER_MOBILE");
		}
		String groupId=request.getParameter("groupId");
		if(StringUtils.isNotBlank(groupId)){
			request.getSession().setAttribute("GROUP_ID", groupId);
		}
		if(StringUtils.isNotBlank(mobile)){
			//String result="actEnd";//结束
			String result=this.isEnable(request, mobile);
			request.setAttribute("isAble", result);
		}
		int allCount=rotateServiceImpl.getAllCount();
		request.setAttribute("allCount", allCount);
		logger.info("RotateController=index=======END=="+mobile);
		return "/sinova/rotate/index";
	}
	
	/**
	 * 奖项设置
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hdgz")
	public String hdgz() throws Exception {
		return "/sinova/rotate/hdgz";
	}
	
	/**
	 * 活动结束
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/actEnd")
	public String actEnd() throws Exception {
		return "/sinova/rotate/actEnd";
	}
	
	/**
	 * 开始抽奖
	 * @param request
	 * @param mobile
	 * @throws Exception 
	 */
	@RequestMapping(value = "/startPlay")
	@ResponseBody
	public ReturnDatas startPlay(HttpServletRequest request,RotateDTO rotate) throws Exception{
		logger.info("RotateController.startPlay() ... start ...");
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		HttpSession session=request.getSession();
		String mobile = (String) session.getAttribute("USER_MOBILE");
		String groupId = (String) session.getAttribute("GROUP_ID");
		
		/*returnObject.setMessage("本期抽奖活动已结束！");
	    returnObject.setStatus("actEnd");*/
		
		if(StringUtils.isNotBlank(mobile)){
			String playKey = (String) session.getAttribute("playing"+mobile);
			if(StringUtils.isBlank(playKey)){
				session.setAttribute("playing"+mobile, mobile);
//				logger.info("RotateController=startPlay=======Start=="+mobile);
				logger.info("RotateController=startPlay=======mobile="+mobile+"===groupId="+groupId);
				String result="";
				try {
					result=this.isEnable(request, mobile);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("判断手机号合法性异常："+e.getMessage());
				}
				
				logger.info(mobile+"手机号是否合法"+result);
				if("overTop".equals(result)){
					returnObject.setMessage("本期抽奖活动期间每人最多中奖二次！");
					logger.info("【本期抽奖活动期间每人最多中奖二次！】"+mobile);
				}else if("noFirst".equals(result)){
					returnObject.setMessage("本期抽奖活动每人每天只能参与一次！");
					logger.info("【本期抽奖活动每人每天只能参与一次！】"+mobile);
				}else if("able".equals(result)){
					String status="";
					String message=""; 
					S4035IntCfmResponse resp= beginRoate(request,mobile);
					executeTimeService.addExcuteTime("RotateController", "s4035intCfm",mobile,"", resp.getInXml(), resp.getOutXml());
					if(StringUtils.isBlank(resp.getReturnCode())){
						//超时
						status="timeOut";
						message="网络异常，若中奖将以短信通知！";
						logger.info("【网络异常，若中奖将以短信通知！】"+mobile+" status："+status);
					}else{
						if("0".equals(resp.getReturnCode())){
							if("Y".equals(resp.getPassFlag())){
								if( null!=resp.getLuckInfo()){
									if("Y".equals(resp.getLuckInfo().getAwardFlag()) && StringUtils.isNotBlank(resp.getLuckInfo().getAwardRank())){
										//中奖
										status=resp.getLuckInfo().getAwardRank();
										logger.info("RotateController=startPlay===iswinning==mobile"+mobile+"==status="+status);
									}else{
										//未中奖
										status="0";
										message="恭喜您获得4G大容量SIM卡一张，请您到就近的营业厅免费领取！";
										logger.info("RotateController=startPlay===unwinning==mobile"+mobile+"==status="+status);
									}
								}else{
									//可能已中奖，未知错误 winningError
									status="timeOut";
									message="网络异常，若中奖将以短信通知！";
									logger.info("RotateController=startPlay===winningError==mobile"+mobile+"==status="+status);
								}
							}else{
								//每天每人只能参加一次，或已达最大参加人数
								if(null!=resp.getMeanAll()){
									Map<String, LimitInfo> map=resp.getMeanAll().getLimitInfo();
									if(null!=resp.getMeanAll().getLimitInfo()&& resp.getMeanAll().getLimitInfo().size()>0){
										LimitInfo limitOne=map.get("1");
										LimitInfo limitTow=map.get("2");
										if(null!=limitTow && "N".equals(limitTow.getPassFlag())){
											//达到活动上线
											status="limitTop";
											message="本期抽奖活动已结束！";
											logger.info("RotateController=startPlay===limitTopErrorLimitInfo==mobile"+mobile+"==status="+status);
										}else if(null!=limitOne && "N".equals(limitOne.getPassFlag())){
											//每天每人只能参加一次
											status="noFirst";
											message="本期抽奖活动每人每天只能参与一次！";
											logger.info("RotateController=startPlay===noFirstErrorLimitInfo==mobile"+mobile+"==status="+status);
										}else{
											//不满足参加抽奖活动条件，未知错误notEnoughErrorLimitInfoOther
											status="notEnough";
											message="不满足参加本期抽奖活动条件！";
											logger.info("RotateController=startPlay===notEnoughErrorLimitInfoOther==mobile"+mobile+"==status="+status);
										}
									}else{
										//不满足参加抽奖活动条件，未知错误notEnoughErrorLimitInfoNUll
										status="notEnough";
										message="不满足参加本期抽奖活动条件！";
										logger.info("RotateController=startPlay===notEnoughErrorLimitInfoNUll==mobile"+mobile+"==status="+status);
									}
								}else{
									//不满足参加抽奖活动条件，未知错误notEnoughErrorMeanAllNull
									status="notEnough";
									message="不满足参加本期抽奖活动条件！";
									logger.info("RotateController=startPlay===notEnoughErrorMeanAllNull==mobile"+mobile+"==status="+status);
								}
							}
						}else{
							//未中奖,接口报错interfaceError
							status="sysError";
							message="参与人数过多，请稍候再试！";
							logger.info("RotateController=startPlay===interfaceError==mobile"+mobile+"==status="+status);
						}
					}
					result=status;
					returnObject.setMessage(message);
					logger.info("RotateController=startPlay===able====mobile="+mobile+"==theResult0="+status+"==theResult1="+message);
					if("sysError".equals(status) || "notEnough".equals(status) || "limitTop".equals(status) || "noFirst".equals(status)){
						//不记录数据库
						logger.info("RotateController=startPlay===notInsert==mobile"+mobile+"==status="+status);
					}else{
						logger.info("RotateController=startPlay===beginInsert==mobile"+mobile+"==status="+status);
						Rotate saveDto=new Rotate();
						saveDto.setCreateTime(new Date());
						saveDto.setGroupId(groupId);
						saveDto.setBatCode(BAT_CODE);
						saveDto.setActId(ACT_ID);
						saveDto.setMeanId(MEAN_ID);
						String inXml=resp.getInXml();
						String outXml=resp.getOutXml();
						if(StringUtils.isNotBlank(inXml) && inXml.length()>3000){
							inXml=inXml.substring(0,3000);
						}
						
						if(StringUtils.isNotBlank(outXml) && outXml.length()>3000){
							outXml=outXml.substring(0,3000);
						}
						saveDto.setInXml(inXml);
						saveDto.setOutXml(outXml);
						saveDto.setMobile(mobile);
						saveDto.setPrize(status);
						if("0".equals(status)){
							saveDto.setState("0");//未中奖
						}else if("timeOut".equals(status)){
							saveDto.setState("3");//超时
						}else{
							saveDto.setState("1");//中奖
						}
						try {
							rotateServiceImpl.save(saveDto);
						} catch (Exception e) {
							logger.info("RotateController=startPlay===save==mobile"+mobile+"==sysError="+e.getMessage());
						}
						logger.info("RotateController=startPlay===endinInsert==mobile"+mobile+"==status="+status);
					}
					
				}else if("sysError".equals(result)){
					returnObject.setMessage("参与人数过多，请稍候再试！");
				}else if("unLogin".equals(result)){
					returnObject.setMessage("未登录，请先刷新页面，登录后再进行抽奖！");
				}
				returnObject.setStatus(result);
				session.removeAttribute("playing"+mobile);
				logger.info("RotateController=startPlay===removeAttribute==mobile"+mobile+"==result="+result);
			}else{
				returnObject.setMessage("正在抽奖，请稍候！");
				returnObject.setStatus("repeat");
			}
		}else{
			returnObject.setMessage("未登录，请先刷新页面，登录后再进行抽奖！");
			returnObject.setStatus("unLogin");
		}
	 
		logger.info("RotateController=startPlay=======End=="+mobile);
		return returnObject;
	}
	
	
	/**
	 * 判断是否可参加抽奖
	 * @param request
	 * @param mobile
	 * @return
	 */
	
	public String isEnable(HttpServletRequest request,String mobile){
		logger.info("RotateController=isEnable=======Start=="+mobile);
		RotateDTO rotate=new RotateDTO();
		String result="";
//		logger.info("RotateController=isEnable=======mobile="+mobile);
		if(StringUtils.isNotBlank(mobile)){
			Page page = newPage(request);
			page.setPageSize(999999);
			rotate.setMobile(mobile);
			rotate.setBatCode(BAT_CODE);
			try {
				List<Rotate> list=rotateServiceImpl.findRoateByDTO(rotate, page);
//				logger.info("RotateController=isEnable=======mobile="+mobile+"==list="+list);
				if(null!=list && list.size()>0){
					int count=0;
//					logger.info("RotateController=isEnable=======mobile="+mobile+"==listSize="+list.size());
					for (int i = 0; i < list.size(); i++) {
						Rotate temp=list.get(i);
						if("1".equals(temp.getState()) || "3".equals(temp.getState())){
							count++;
						}
					}
					//判断是否还可以抽奖
					if(count>=ROTATE_COUNT){
						//抽奖次数已达最大数
						result="overTop";
					}else{
						//获取最新参与抽奖时间
						Rotate rotateTmp=list.get(0);
						Date nowDate=new Date();
						Date joinDate=rotateTmp.getCreateTime();
						String newDate=DateUtils.formatDateTime(nowDate,"yyyy-MM-dd");
						String oldDate=DateUtils.formatDateTime(joinDate,"yyyy-MM-dd");
//						logger.info("RotateController=isEnable=======mobile="+mobile+"==oldDate="+oldDate+"==newDate="+newDate);
						if(newDate.equals(oldDate)){
							//一天只能参与一次
							result="noFirst";
//							logger.info("RotateController=isEnable===listnotNull====mobile="+mobile+"==noFirst=");
						}else{
							//调用抽奖接口，进行抽奖
							result="able";
//							logger.info("RotateController=isEnable===listnotNull====mobile="+mobile+"==able=");
						}
					}
				}else{
					//从未参与过，可以抽奖
					//调用抽奖接口，进行抽奖
//					logger.info("RotateController=isEnable===listNULL====mobile="+mobile+"==able=");
					result="able";
				}
				
			} catch (Exception e) {
				result="sysError";
				logger.info("RotateController=isEnable=======mobile="+mobile+"==sysError="+e.getMessage());
				logger.info("【警告】:判断手机号异常");
			}
		}else{
			result="unLogin";
		}
		logger.info("RotateController.isEnable() "+mobile + " result:" + result);
		return result;
	}
	
	
	public S4035IntCfmResponse beginRoate(HttpServletRequest request,String mobile){
		S4035IntCfmRequest req=new S4035IntCfmRequest();
		req.setServiceNo(mobile);
		req.setMasterServId("1001");
		req.setProvinceGroup("10011");
		Date date=new Date();
		String now=DateUtils.formatDateTime(date,"yyyyMMdd");
		req.setOpenTime(now);
		req.setLoginNo(LOGIN_NO);
		req.setActId(ACT_ID);
		req.setMeansId(MEAN_ID);
		req.setChannelType("11");
		return this.sitechInterFace.s4035intCfm(req);
		/*
		String[] result1={"5","2元100M和阅读客户端纯流量"};
		String[] result2={"4","5元500M咪咕音乐客户端纯流量"};
		String[] result3={"3","10元1G和视频客户端纯流量"};
		String[] result4={"2","5元500M和动漫客户端流量+《每日一笑》"};
		String[] result5={"1","5元500M和地图客户端流量+手机导航包月"};
		String[] result6={"0","谢谢参与"};
		String[] result7={"timeOut","超时"};
		List<String[]> resultAll=new ArrayList<String[]>();
		resultAll.add(result1);
		resultAll.add(result2);
		resultAll.add(result3);
		resultAll.add(result4);
		resultAll.add(result5);
		resultAll.add(result6);
		resultAll.add(result7);
		int max=7;
        int min=0;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        logger.info("ssssssssssssssssssss=="+s);
        //s=6;
		return resultAll.get(s);*/
	}
	
	/**
	 * 登录操作
	 * @param request
	 * @param mobile
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public ReturnDatas login(HttpServletRequest request){
		logger.info("RotateController===login==Start");
		String result="";
		String mobile=request.getParameter("mobilePhone");
		String vCode=request.getParameter("vCode");
		logger.info("RotateController===login==vCode="+vCode+"===mobile="+mobile);
		String sCode = (String) request.getSession().getAttribute(Const.RAND_IMAGE_VALIDATE);
		String sendMobile = (String) request.getSession().getAttribute("SEND_MOBILE");
		try {
			String str=this.sitechInterFace.sQUserBase(mobile);
			//String str="1:0";//测试写死，上线注释
			if(StringUtils.isNotBlank(str)){
				String[] strs=str.split(":");
				if(Constant.ONE.equals(strs[0])){
					
					if(StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(sendMobile) && mobile.equals(sendMobile)){
						if(StringUtils.isNotBlank(vCode) && StringUtils.isNotBlank(sCode) && vCode.equals(sCode)){
							request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
							HttpSession session=request.getSession();
	        				session.setAttribute("USER_MOBILE", mobile);
	        				session.setAttribute("USER_TYPR", Constant.ZERO);
	    					session.setMaxInactiveInterval(30*60);
							//request.getSession().setAttribute("USER_MOBILE", mobile);
							logger.info("RotateController===login==vCode=SUCC"+"===mobile="+mobile);
							result=Constant.ONE;
						}else{
							logger.info("RotateController===login==vCode=ERROR"+"===mobile="+mobile);
							result=Constant.ZERO;
						}
					}else{
						logger.info("RotateController===login==mobile=ERROR"+"===mobile="+mobile);
						result=Constant.ZERO;
					}
				}else{
					logger.info("RotateController===login==mobile=ERROR"+"===mobile="+mobile);
					result="3";
				}
			}else{
				logger.info("RotateController===login==sQUserBase=ERROR"+"===mobile="+mobile);
				result="3";
			}
		}catch (Exception e) {
			logger.info("RotateController===login==System=ERROR="+e.getMessage()+"===mobile="+mobile);
			result="4";
		}
		logger.info("RotateController===login==End"+"===mobile="+mobile);
		return new ReturnDatas(result,"");
	}
	/**
	 * 下发短信
	 * @param request
	 * @param mobile
	 */
	@RequestMapping("/sendMess")
	@ResponseBody
	public ReturnDatas sendMess(HttpServletRequest request){
		String mobile=request.getParameter("account");
		String result="";
		logger.info("RotateController===sendMess==Start");
		logger.info("RotateController===sendMess===mobile="+mobile);
		try {
			String str="";
			try {
				str=this.sitechInterFace.sQUserBase(mobile);
			} catch (Exception e) {
				logger.error("RotateController===sendMess==sQUserBase==ERROR"+e.getMessage()+"===mobile="+mobile);
			}
			
			//str="1:0";//测试写死，上线注释
			if(StringUtils.isNotBlank(str)){
				String[] strs=str.split(":");
				if(Constant.ONE.equals(strs[0])){
					request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
					String vCode = Util.getrannumber();
					logger.info("RotateController==sendMess=" +vCode);
					String con = Const.SEND_VERIFICATION_CODE_ROTATE + vCode + "。";
					this.sitechInterFace.smsSendInfo(mobile,con);
					HttpSession session=request.getSession();
					session.setAttribute(Const.RAND_IMAGE_VALIDATE, vCode);
					session.setAttribute("SEND_MOBILE", mobile);
					session.setMaxInactiveInterval(5*60);
					result=Constant.ONE;
				}else{
					logger.info("RotateController==sendMess=mobile=ERROR"+"===mobile="+mobile);
					result="3";
				}
			}else{
				logger.info("RotateController==sendMess=sQUserBase=ERROR"+"===mobile="+mobile);
				result="3";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result=Constant.ZERO;
			logger.error("RotateController===sendMess==System==ERROR"+e.getMessage()+"===mobile="+mobile);
		}
		logger.info("RotateController===sendMess==END"+"===mobile="+mobile);
		return new ReturnDatas(result,"");
	}
	
	public void autoGetPhone(HttpServletRequest request){
		//获取IP
		String ip = request.getHeader("x-forwarded-for");
		logger.info("RotateController=autoGetPhone==x-forwarded-for="+ip);
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.info("RotateController=autoGetPhone==Proxy-Client-IP="+ip);
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            logger.info("RotateController=autoGetPhone==WL-Proxy-Client-IP="+ip);
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            logger.info("RotateController=autoGetPhone==getRemoteAddr="+ip);
        }
        String phoneNo ="";
        //117.136.37.140 117.136.37
        if(StringUtils.isNotBlank(ip) && !"unknown".equals(ip)){
        	if(ip.indexOf("223.104.14.")>-1 || ip.indexOf("117.136.4.")>-1 || ip.indexOf("117.136.37.")>-1){
        		//获取手机号
        		String REQUEST_HEADER_PHONE_NO = "x-up-calling-line-id";
        		logger.info("RotateController=autoGetPhone==x-up-calling-line-id="+REQUEST_HEADER_PHONE_NO);
        		String header = request.getHeader(REQUEST_HEADER_PHONE_NO);
        		logger.info("RotateController=autoGetPhone==header="+header);
        		//header="8615666779618";//测试
        		if(StringUtils.isNotBlank(header)){
        			phoneNo = header.trim();
        			logger.info("RotateController=autoGetPhone==phoneNo="+phoneNo);
        			if(StringUtils.isNotBlank(phoneNo)){
        				phoneNo = phoneNo.startsWith("86") ? phoneNo.substring(2, phoneNo.length()) : phoneNo;
        				String str="";
        				try {
        					str=this.sitechInterFace.sQUserBase(phoneNo);
        					if(StringUtils.isNotBlank(str)){
        						String[] strs=str.split(":");
        						if(Constant.ONE.equals(strs[0])){
        							HttpSession session=request.getSession();
                    				session.setAttribute("USER_MOBILE", phoneNo);
                    				session.setAttribute("USER_TYPR", Constant.ONE);
                					session.setMaxInactiveInterval(30*60);
        						}else{
        							logger.info("autoGetPhone=feachUserBaseError");
        						}
        					}else{
        						logger.info("autoGetPhone==feachUserBase===str=="+str);
        					}
            				logger.info("RotateController=autoGetPhone==phoneNo="+phoneNo);
        				} catch (Exception e) {
        					logger.error("RotateController===autoGetPhone==sQUserBase==ERROR"+e.getMessage()+"===mobile="+phoneNo);
        				}
        			}
        		}
        	}else{
        		//HttpSession session=request.getSession();
				//session.removeAttribute("USER_MOBILE");
        	}
        }
	}
	
	
	public static void main(String[] args) {
		 String phoneNo="8615135077835";
		 phoneNo = phoneNo.startsWith("86") ? phoneNo.substring(2, phoneNo.length()) : phoneNo;
		 System.out.println(phoneNo);
	}
}
