package org.springrain.sinova.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.BusinessGoodsDTO;
import org.springrain.sinova.dto.SpecialQrcodeDTO;
import org.springrain.sinova.dto.UserBusinessDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IQrcodeService;
import org.springrain.sinova.service.ISpecialQrcodeService;
import org.springrain.sinova.util.AutoGetPhone;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.User;
import org.springrain.system.service.IUserService;
import org.springrain.system.util.Constant;
import org.springrain.system.util.PropertyFileCache;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/qrcodeSales")
public class QrcodeSalesController extends BaseController{

	@Resource
	private IQrcodeService qrcodeService;
	@Resource
	private ISitechInterface sitechInterFace;
	@Resource
	private IBusinessService businessService;
	@Resource
	private ISpecialQrcodeService specialQrcodeService;
	@Resource
	private IUserService userService;
	
	
	private PropertyFileCache pfCache = PropertyFileCache.getInstance();
	
	
	/**
	 * 自动获取手机号
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getPhoneNo")
	public String getPhone(HttpServletRequest request, Model model) throws Exception{
		request.getSession().removeAttribute(Const.RESUBMIT_DATE_TAG);//清除重复提交标识
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String flag = null;
		
		String isHaveNo = request.getParameter("workno");
	
		logger.info("---isHaveNo-----"+isHaveNo);
		if(StringUtils.isNotBlank(isHaveNo)){
			Page page = newPage(request);
			page.setPageSize(8);
			User user = new User();
			user.setWorkno(isHaveNo);
			String isName ="";
			if(request.getMethod().toUpperCase().equals("GET")){
				isName = new String(request.getParameter("userName").getBytes("ISO-8859-1"),"utf-8");
			}else{
				isName = request.getParameter("userName");
			}
			logger.info("-----getPhoneNo:::isName="+isName);
			if(StringUtils.isNotBlank(isName)){
				user.setName(isName);
			}
			Qrcode qrcode = new Qrcode();
			String qrcodeName = request.getParameter("qrcodeName");
			if(StringUtils.isNotBlank(qrcodeName)){
				logger.info("---qrcodeName----"+qrcodeName+"---isHaveNo---"+isHaveNo);
				qrcode.setBelongUser(isHaveNo);
				qrcode.setQrcodeName(qrcodeName);
				List<Qrcode> datas = qrcodeService.findQrcodeByQrcodeName(qrcode, page);
				flag = "1";
				model.addAttribute("user", user);
				model.addAttribute("flag", flag);
				model.addAttribute("qrcodeName", qrcodeName);
				returnObject.setPage(page);
				returnObject.setData(datas);
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				
				return "/sinova/qrcodeSales/listQrcode";
			}
			
			qrcode.setBelongUser(isHaveNo);
			List<Qrcode> datas = qrcodeService.findQrcodeByBelongUser(qrcode, page); 
			
			flag = "1";
			model.addAttribute("user", user);
			model.addAttribute("flag", flag);
			returnObject.setPage(page);
			returnObject.setData(datas);
			
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return "/sinova/qrcodeSales/listQrcode";
		}
		
		//读取配置文件 获取得到手机号开关 (0为关,1为开)
		String marker = pfCache.get(Const.SYSTEM_FILE, "getPhoneSwitch");
		logger.info("获取手机号开关：[{}]",marker);
		if("1".equals(marker)){//开
			
			String phoneNo = (String) request.getSession().getAttribute("USER_MOBILE");
			if(StringUtils.isBlank(phoneNo) ){
				AutoGetPhone.autoGetPhone(request,sitechInterFace);//流量自动获取手机号
				phoneNo = (String) request.getSession().getAttribute("USER_MOBILE");
			}
			if(StringUtils.isNotBlank(phoneNo)){
				phoneNo = phoneNo.startsWith("86") ? phoneNo.substring(2, phoneNo.length()) : phoneNo; 
				logger.info("获取到的手机号码为:{}", phoneNo);

					
				Page page = newPage(request);
				page.setPageSize(8);
				User user = new User();
				user.setPhone(phoneNo);
				user = qrcodeService.findWorkNoByPhone(phoneNo);
				if(user==null){
					model.addAttribute(GlobalStatic.returnDatas, returnObject);
					return "/sinova/qrcodeSales/qrcodeError";
				}
				try {
					String workno = user.getWorkno();
				} catch (Exception e) {
					model.addAttribute(GlobalStatic.returnDatas, returnObject);
					return "/sinova/qrcodeSales/qrcodeError";
				}
				
				Qrcode qrcode = new Qrcode();
				String qrcodeName = request.getParameter("qrcodeName");
				if(StringUtils.isNotBlank(qrcodeName)){
					qrcode.setQrcodeName(qrcodeName);
					List<Qrcode> datas = qrcodeService.findQrcodeByQrcodeName(qrcode, page);
					flag = "1";
					model.addAttribute("user", user);
					model.addAttribute("flag", flag);
					model.addAttribute("qrcodeName", qrcodeName);
					returnObject.setPage(page);
					returnObject.setData(datas);
					model.addAttribute(GlobalStatic.returnDatas, returnObject);
					
					return "/sinova/qrcodeSales/listQrcode";
				}
				
				qrcode.setBelongUser(user.getWorkno());
				List<Qrcode> datas = qrcodeService.findQrcodeByBelongUser(qrcode, page); 
				
				flag = "1";
				model.addAttribute("user", user);
				model.addAttribute("flag", flag);
				returnObject.setPage(page);
				returnObject.setData(datas);
				
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return "/sinova/qrcodeSales/listQrcode";
				
			}else{
				logger.info("===没有获取到手机号===");
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return "/sinova/qrcodeSales/listQrcode";
			}
			
		}else{//关
			logger.info("===获取手机号开关为关闭状态===");
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return "/sinova/qrcodeSales/listQrcode";
		}
	}
	
	/**
	 * 验证手机号是否有工号
	 * @param request
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/checkPhone")
	@ResponseBody
	public ReturnDatas checkPhone(HttpServletRequest request, Model model){
		String mobile = request.getParameter("phoneNo");
		logger.info("checkPhone");

		if(StringUtils.isNotBlank(mobile)){
			try {
				User user = new User();
				user.setPhone(mobile);
				user = qrcodeService.findWorkNoByPhone(mobile);
				if(StringUtils.isNotBlank(user.getWorkno())){
					request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
					this.sendVCode(request, mobile);//发送验证码
					return new ReturnDatas(Constant.ONE,"");
				}else{
					return new ReturnDatas(Constant.ZERO, "");
				}
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
	 * 验证手机验证码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkVCode")
	@ResponseBody
	public ReturnDatas checkVCode(HttpServletRequest request, Model model){
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
	//手输手机号查询列表
	@RequestMapping("/writePhone")
	public String writePhone(HttpServletRequest request, Model model) throws Exception{
		request.getSession().removeAttribute(Const.RESUBMIT_DATE_TAG);//清除重复提交标识
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		Page page = newPage(request);
		page.setPageSize(8);
		
		String phoneNo = request.getParameter("phoneNo");
		String flag = "";
		String workNo = "";
		User user = new User();
		user.setPhone(phoneNo);
		user = qrcodeService.findWorkNoByPhone(phoneNo);
		try {
			if(user != null){
				workNo = user.getWorkno();
			}
		} catch (Exception e) {
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return "/sinova/qrcodeSales/qrcodeError";
		}
		
		logger.info("---phoneNo----"+phoneNo+"---workNo---"+workNo);
		Qrcode qrcode = new Qrcode();
		String qrcodeName = request.getParameter("qrcodeName");
		if(StringUtils.isNotBlank(qrcodeName)){
			qrcode.setQrcodeName(qrcodeName);
			qrcode.setBelongUser(workNo);
			List<Qrcode> datas = qrcodeService.findQrcodeByQrcodeName(qrcode, page);
			flag = "1";
			model.addAttribute("user", user);
			model.addAttribute("flag", flag);
			model.addAttribute("qrcodeName", qrcodeName);
			returnObject.setPage(page);
			returnObject.setData(datas);
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			
			return "/sinova/qrcodeSales/listQrcode";
		}
		if(StringUtils.isNotBlank(workNo)){
			qrcode.setBelongUser(workNo);
			List<Qrcode> datas = qrcodeService.findQrcodeByBelongUser(qrcode, page); 
			
			flag = "1";
			model.addAttribute("user", user);
			model.addAttribute("flag", flag);
			returnObject.setPage(page);
			returnObject.setData(datas);
		}else{
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return "/sinova/qrcodeSales/qrcodeError";
		}
		
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		
		return "/sinova/qrcodeSales/listQrcode";
	}
	
	
	/**二维码详情页
	 * @param request
	 * @param model
	 * @param qrcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDetails")
//	@ResponseBody
	public String findDetails(HttpServletRequest request, Model model, Qrcode qrcode) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		String workno = request.getParameter("workno");
		String userName = new String(request.getParameter("userName").getBytes("ISO-8859-1"),"utf-8");
		//String userName = request.getParameter("userName");
		logger.info("---findDetails::::userName="+userName);
		String jsonString = "";
		
		String id = qrcode.getId();
		qrcode = qrcodeService.findQrcodeById(id);
		User user = new User();
		String str1 = "";
		//专属业务二维码
		if("1".equals(qrcode.getType())){
			SpecialQrcodeDTO specialQrcode = new SpecialQrcodeDTO();
			user.setWorkno(workno);
			User userDto = userService.queryForObject(user);
			specialQrcode.setBelongUser(userDto.getId());
			specialQrcode.setType("1");
			List<Business> datas = specialQrcodeService.findBus(specialQrcode, null);
			if(datas!=null && datas.size()>0){//已勾选的专属二维码
				logger.info("---findDetails-----专属已勾选-------userId="+userDto.getId()+"----- 业务数量======="+datas.size());
				for(int i=0; i<datas.size(); i++){
					String busName = datas.get(i).getBusName();
					str1 = str1 + busName + "，";
				}
			}else{//未勾选过的专属二维码
				List<Business> busdata = specialQrcodeService.findBusByUserId(userDto.getId());
				logger.info("---findDetails-----专属未勾选-------userId="+userDto.getId()+"----- 业务数量======="+busdata.size());
				if(busdata!=null && busdata.size()>0){
					for(int i=0; i<busdata.size(); i++){
						String busName = busdata.get(i).getBusName();
						str1 = str1 + busName + "，";
					}
				}	
			}
		}else{
			//普通的二维码
			jsonString = qrcode.getParamStr();
			UserBusinessDTO userBusinessDTO = JSONObject.parseObject(jsonString, UserBusinessDTO.class);
			List<BusinessGoodsDTO> list = userBusinessDTO.getBusGoodsList();
		
			for(int i=0;i<list.size();i++){
				BusinessGoodsDTO businessGoodsDTO = list.get(i);
				//获取业务编码
				String busCode = businessGoodsDTO.getBusinessCode();
				Finder finder = Finder.getSelectFinder(Business.class).append(" where state = '1' and up_down = '1' and bus_code = :busCode   ");
				finder.setParam("busCode", busCode);
				Business dto = businessService.queryForObject(finder, Business.class);
				if(dto != null){
					String busStr = dto.getBusName();
					str1 = str1 + busStr + "，";
					
				}
			}
		}
		//业务名称字符串
		String str2 = str1.substring(0, str1.length()-1)+"。";
		logger.info("---findDetails---业务名称字符串--"+str2);
		user.setName(userName);
		user.setWorkno(workno);
		
		model.addAttribute("user", user);
		model.addAttribute("bus", str2);
	    model.addAttribute("qrcode", qrcode);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/qrcodeSales/qrcodeDetails";
	}
	
	
	
}
