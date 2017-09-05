package org.springrain.system.util;

import org.apache.commons.lang3.StringUtils;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.rep.MobileLogonResponse;

/**
 *
 * @description TODO <br/>
 * @date 2014-6-24 下午4:18:35 <br/>
 * @author wangbo
 */
public enum Constant {  
	INSTANCE;

	public static final String ZERO = "0";
	public static final String ONE = "1";
	public static final String ONE_NEGATIVE = "-1";
	public static final String ONE_NET_ERROR = "403";//网络异常
	public static final String ONE_INFO_ERROR = "500";//网络异常
	
	public static final String ROLE_REGION_MANAGER = "ROLE_REGION_MANAGER";
	public static final String ROLE_OFFICE_MANAGE = "ROLE_OFFICE_MANAGE";
	public static final String ROLE_OFFICE = "ROLE_OFFICE";
	
	
	public static String turnLoginMsg(String code) {
		if(StringUtils.equals(code, "01")){
			return "认证通过，短信网关正常，可以下发验证码";
		}else if(StringUtils.equals(code, "02")){
			return "认证通过，短信网关关闭，无法下发验证码,不需要下发短信无影响";
		}else if(StringUtils.equals(code, "03")){
			return "主账号密码错误";
		}else if(StringUtils.equals(code, "04")){
			return "手机imei认证错误";
		}else if(StringUtils.equals(code, "05")){
			return "主账号暂挂";
		}else if(StringUtils.equals(code, "06")){
			return "主账号密码过期";
		}else if(StringUtils.equals(code, "07")){
			return "主账号不存在";
		}else if(StringUtils.equals(code, "08")){
			return "用户的入参信息不全";
		}else if(StringUtils.equals(code, "09")){
			return "对应的厂商和渠道不允许访问";
		}else if(StringUtils.equals(code, "10")){
			return "从账号信息不存在";
		}else if(StringUtils.equals(code, "11")){
			return "从账号暂挂";
		}else{
			return "其他";
		}
	}
	public static String turnCodeMsg(String code) {
		if(StringUtils.equals(code, "01")){
			return "单纯短信验证码校验成功";
		}else if(StringUtils.equals(code, "02")){
			return "主账号密码修改成功";
		}else if(StringUtils.equals(code, "03")){
			return "用户的入参信息不全";
		}else if(StringUtils.equals(code, "04")){
			return "对应的厂商和渠道不允许访问";
		}else if(StringUtils.equals(code, "05")){
			return "业务代码不存在";
		}else if(StringUtils.equals(code, "06")){
			return "验证码超过10分钟";
		}else if(StringUtils.equals(code, "07")){
			return "主账号不存在";
		}else if(StringUtils.equals(code, "08")){
			return "其他";
		}else if(StringUtils.equals(code, "09")){
			return "验证码错误";
		}else{
			return "其它";
		}
	}

}
