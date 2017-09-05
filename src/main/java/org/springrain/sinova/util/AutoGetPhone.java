package org.springrain.sinova.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.system.util.Constant;

public class AutoGetPhone {
	
	
	public static void autoGetPhone(HttpServletRequest request,ISitechInterface sitechInterFace){
		
		Logger logger = LoggerFactory.getLogger(AutoGetPhone.class);
		//获取IP
		String ip = request.getHeader("x-forwarded-for");
		logger.info("AutoGetPhone=autoGetPhone==x-forwarded-for="+ip);
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.info("AutoGetPhone=autoGetPhone==Proxy-Client-IP="+ip);
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            logger.info("AutoGetPhone=autoGetPhone==WL-Proxy-Client-IP="+ip);
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            logger.info("AutoGetPhone=autoGetPhone==getRemoteAddr="+ip);
        }
        String phoneNo ="";
        //117.136.37.140 117.136.37
        if(StringUtils.isNotBlank(ip) && !"unknown".equals(ip)){
        	if(ip.indexOf("223.104.14.")>-1 || ip.indexOf("117.136.4.")>-1 || ip.indexOf("117.136.37.")>-1){
        		//获取手机号
        		String REQUEST_HEADER_PHONE_NO = "x-up-calling-line-id";
        		logger.info("AutoGetPhone=autoGetPhone==x-up-calling-line-id="+REQUEST_HEADER_PHONE_NO);
        		String header = request.getHeader(REQUEST_HEADER_PHONE_NO);
        		logger.info("AutoGetPhone=autoGetPhone==header="+header);
        		//header="8615666779618";//测试
        		if(StringUtils.isNotBlank(header)){
        			phoneNo = header.trim();
        			logger.info("AutoGetPhone=autoGetPhone==phoneNo="+phoneNo);
        			if(StringUtils.isNotBlank(phoneNo)){
        				phoneNo = phoneNo.startsWith("86") ? phoneNo.substring(2, phoneNo.length()) : phoneNo;
        				String str="";
        				try {
        					str = sitechInterFace.sQUserBase(phoneNo);
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
            				logger.info("AutoGetPhone=autoGetPhone==phoneNo="+phoneNo);
        				} catch (Exception e) {
        					logger.error("AutoGetPhone===autoGetPhone==sQUserBase==ERROR"+e.getMessage());
        				}
        			}
        		}
        	}else{
        		//HttpSession session=request.getSession();
				//session.removeAttribute("USER_MOBILE");
        	}
        }
	}
	
	public  AutoGetPhone(){}
	

}
