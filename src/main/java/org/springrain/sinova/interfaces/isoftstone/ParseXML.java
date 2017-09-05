package org.springrain.sinova.interfaces.isoftstone;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.sinova.interfaces.isoftstone.checkTokenCode.rep.CheckTokenCodeResponse;
import org.springrain.sinova.interfaces.isoftstone.checkTokenCode.req.CheckTokenCodeRequest;
import org.springrain.sinova.interfaces.isoftstone.customaryInfo.rep.CustomaryInfoResponse;
import org.springrain.sinova.interfaces.isoftstone.customaryInfo.req.CustomaryInfoRequest;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.rep.MobileLogonResponse;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.req.Extend;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.req.MobileLogonRequest;
import org.springrain.sinova.interfaces.isoftstone.sendTokenCode.rep.SendTokenCodeResponse;
import org.springrain.sinova.interfaces.isoftstone.sendTokenCode.req.SendTokenCodeRequest;
import org.springrain.sinova.util.Util;
import org.springrain.system.util.XStreamUtil;

@SuppressWarnings("unused")
public class ParseXML {

	private static final Logger logger = LoggerFactory.getLogger(ParseXML.class);
	private static final String XMLHEAD_U = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
	private static final String XMLHEAD_G = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>";
	private static final String XMLSTART = "<request>";
	private static final String XMLEND = "</request>";
	private static final String ENTER = "\n";

	/**
	 * 通过4A查询bossno
	 * 
	 * @param customaryInfoRequest
	 * @return
	 */
	public static String requestXMLCustomaryInfo(CustomaryInfoRequest customaryInfoRequest) {
		logger.info("reruestXMLCustomaryInfo(4A查询工号入参)   start");
		StringBuffer sb = new StringBuffer();
		// sb.append(XMLHEAD_U).append(ENTER);
		sb.append("<request>").append(ENTER);
		sb.append("<congAccount>").append(customaryInfoRequest.getCongAccount() == null ? "" : customaryInfoRequest.getCongAccount()).append("</congAccount>").append(ENTER);
		sb.append("<firmID>").append(customaryInfoRequest.getFirmID() == null ? "" : customaryInfoRequest.getFirmID()).append("</firmID>").append(ENTER);
		sb.append("<channelID>").append(customaryInfoRequest.getChannelID() == null ? "" : customaryInfoRequest.getChannelID()).append("</channelID>").append(ENTER);
		sb.append("<sourceIP>").append(customaryInfoRequest.getSourceIP() == null ? "" : customaryInfoRequest.getSourceIP()).append("</sourceIP>").append(ENTER);
		sb.append("<destinationIP>").append(customaryInfoRequest.getDestinationIP() == null ? "" : customaryInfoRequest.getDestinationIP()).append("</destinationIP>").append(ENTER);
		sb.append("<destinationPort>").append(customaryInfoRequest.getDestinationPort() == null ? "" : customaryInfoRequest.getDestinationPort()).append("</destinationPort>").append(ENTER);

		sb.append("<extend>").append(ENTER);
		sb.append("<destinationPort>").append(customaryInfoRequest.getExtend().getBusinessInfo() == null ? "" : customaryInfoRequest.getExtend().getBusinessInfo()).append("</destinationPort>").append(ENTER);
		sb.append("</extend>").append(ENTER);

		sb.append("</request>").append(ENTER);
		logger.info("reruestXMLCustomaryInfo   end");
		return sb.toString();
	}

	public static CustomaryInfoResponse responseXMLCustomaryInfo(String xml) throws DocumentException {
		logger.info("responseXMLCustomaryInfo(4A查询工号出参) start");
		CustomaryInfoResponse response = new CustomaryInfoResponse();
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		response.setRetCode(root.elementTextTrim("retCode"));
		Element retMsg = root.element("retMsg");
		response.getRetMsg().setUid(retMsg.elementTextTrim("uid"));
		response.getRetMsg().setName(retMsg.elementTextTrim("name"));
		response.getRetMsg().setPhoneNum(retMsg.elementTextTrim("phoneNum"));
		response.getRetMsg().setOrgID(retMsg.elementTextTrim("orgID"));
		response.getRetMsg().setBossAcc(retMsg.elementTextTrim("bossAcc"));
		response.getRetMsg().setRegionName(retMsg.elementTextTrim("regionName"));
		response.getRetMsg().setDistrictName(retMsg.elementTextTrim("districtName"));
		response.getRetMsg().setTowmName(retMsg.elementTextTrim("towmName"));
		response.getRetMsg().setStatus(retMsg.elementTextTrim("status"));
		return response;
	}

	/**
	 * 4A用户校验
	 * 
	 * @param mobileLogonRequest
	 * @return
	 */
	public static String requestXMLMobileLogon(MobileLogonRequest mobileLogonRequest) {
		logger.info("reruestXMLMobileLogon(用户密码校验入参)   start");
		//String reqXml = XStreamUtil.toXml(mobileLogonRequest);
		StringBuffer sb = new StringBuffer();
		sb.append(XMLSTART).append(ENTER);
		sb.append("<uid>").append(mobileLogonRequest.getUid() == null ? "" : mobileLogonRequest.getUid()).append("</uid>").append(ENTER);
		sb.append("<password>").append(mobileLogonRequest.getPassword() == null ? "" : mobileLogonRequest.getPassword()).append("</password>").append(ENTER);
		sb.append("<imei>").append(mobileLogonRequest.getImei() == null ? "" : mobileLogonRequest.getImei()).append("</imei>").append(ENTER);
		sb.append("<channelID>").append(mobileLogonRequest.getChannelID() == null ? "" : mobileLogonRequest.getChannelID()).append("</channelID>").append(ENTER);
		sb.append("<firmID>").append(mobileLogonRequest.getFirmID() == null ? "" : mobileLogonRequest.getFirmID()).append("</firmID>").append(ENTER);
		sb.append("<serviceID>").append(mobileLogonRequest.getServiceID() == null ? "" : mobileLogonRequest.getServiceID()).append("</serviceID>").append(ENTER);
		sb.append("<shortmsg>").append(mobileLogonRequest.getShortmsg() == null ? "" : mobileLogonRequest.getShortmsg()).append("</shortmsg>").append(ENTER);
		sb.append("<sourceIP>").append(mobileLogonRequest.getSourceIP() == null ? "" : mobileLogonRequest.getSourceIP()).append("</sourceIP>").append(ENTER);
		sb.append("<destinationIP>").append(mobileLogonRequest.getDestinationIP() == null ? "" : mobileLogonRequest.getDestinationIP()).append("</destinationIP>").append(ENTER);
		sb.append("<destinationPort>").append(mobileLogonRequest.getDestinationPort() == null ? "" : mobileLogonRequest.getDestinationPort()).append("</destinationPort>").append(ENTER);
		sb.append("<extend>");
		if(mobileLogonRequest.getExtend() != null){
			sb.append("<businessInfo>").append(mobileLogonRequest.getExtend().getBusinessInfo() == null ? "" : mobileLogonRequest.getExtend().getBusinessInfo()).append("</businessInfo>").append(ENTER);
		}
		sb.append("</extend>");
		String reqXml = sb.toString();
		logger.info("reruestXMLMobileLogon requestxml   end");
		return reqXml;
	}

	public static MobileLogonResponse responseXMLMobileLogon(String xml) throws DocumentException {
		logger.info("responseXMLMobileLogon(用户密码校验出参) start");
		MobileLogonResponse rep = XStreamUtil.toBean(xml, MobileLogonResponse.class);
		logger.info("responseXMLMobileLogon end");
		return rep;
	}
	
	/**4A短信下发校验
	 * @param sendTokenCodeRequest
	 * @return
	 */
	public static String requestXMLSendTokenCode(SendTokenCodeRequest sendTokenCodeRequest) {
		logger.info("requestXMLSendTokenCode(短信下发入参) start");
		String reqXml = XStreamUtil.toXml(sendTokenCodeRequest);
		logger.info("requestXMLSendTokenCode end");
		return reqXml;
	}
	
	public static SendTokenCodeResponse responseXMLSendTokenCode(String xml) throws DocumentException {
		logger.info("responseXMLSendTokenCode(短信下发出参) start");
		SendTokenCodeResponse rep = XStreamUtil.toBean(xml, SendTokenCodeResponse.class);
		logger.info("responseXMLSendTokenCode end");
		return rep;
	}
	
	/**
	 * 4A登录短信校验
	 * @param checkTokenCodeRequest
	 * @return
	 */
	public static String requestXMLCheckTokenCode(CheckTokenCodeRequest checkTokenCodeRequest) {
		logger.info("requestXMLCheckTokenCode(登录短信校验入参) start");
		String reqXml = XStreamUtil.toXml(checkTokenCodeRequest);
		logger.info("requestXMLCheckTokenCode end");
		return reqXml;
	}
	
	public static CheckTokenCodeResponse responseXMLCheckTokenCode(String xml) throws DocumentException {
		logger.info("responseXMLSendTokenCode(登录短信校验出参) start");
		CheckTokenCodeResponse rep = XStreamUtil.toBean(xml, CheckTokenCodeResponse.class);
		logger.info("responseXMLSendTokenCode end");
		return rep;
	}

	public static void main(String[] args) throws DocumentException {
	
		/*MobileLogonRequest mobileLogonRequest = new MobileLogonRequest();
		mobileLogonRequest.setUid("1");// 4A主账号必填
		mobileLogonRequest.setPassword("2");// 主账号密码必填
		mobileLogonRequest.setImei("");// 手机的IMEI码可选
		mobileLogonRequest.setChannelID("01");// 渠道代码，固定值必填01 web、02 wap
		mobileLogonRequest.setFirmID("sinovatech");// 厂商编码必填
		mobileLogonRequest.setServiceID("8528894278356196767");// 从账号服务编码必填
		mobileLogonRequest.setShortmsg("01");// 是否下发短信，必填 00--表示不下发,01— 短信验证码下发---校验时为03
		mobileLogonRequest.setSourceIP("1.1.1.1");// 源IP-
		mobileLogonRequest.setDestinationIP("2.2.2.2");// 目地IP
		mobileLogonRequest.setDestinationPort("90");
		
		Extend e = new Extend();
		e.setBusinessInfo("test");
		mobileLogonRequest.setExtend(e);
		System.out.println(ParseXML.requestXMLMobileLogon(mobileLogonRequest));*/
		
//		String s= "<response><retCode>07</retCode><retMsg>123 此账号不存在</retMsg></response>";
//		CheckTokenCodeResponse c = ParseXML.responseXMLCheckTokenCode(s);
//		System.out.println(c);
		
		
		
		/*
		 * CheckTokenCodeRequest r = new CheckTokenCodeRequest(); r.setBusinessID("1"); r.setChannelID("2"); r.setDestinationIP("3"); r.setExtend("4"); r.setFirmID("5"); r.setSourceIP("6");
		 * r.setTokenCode("7"); r.setUid("8"); System.out.println(requestXMLCheckTokenCode(r));
		 */
		
		/*
		 * SendTokenCodeRequest r = new SendTokenCodeRequest(); r.setChannelID("1"); r.setDestinationIP("2"); r.setExtend("3"); r.setFirmID("4"); r.setShortflag("5"); r.setSourceIP("6");
		 * r.setUid("7"); System.out.println(ParseXML.requestXMLSendTokenCode(r));
		 */
		
		/*
		 * CustomaryInfoRequest r = new CustomaryInfoRequest(); r.setChannelID("1"); r.setCongAccount("2"); r.setDestinationIP("3"); r.setDestinationPort("4"); Extend e = new Extend();
		 * e.setBusinessInfo("44"); r.setExtend(e); r.setFirmID("6"); r.setSourceIP("7"); //System.out.println(ParseXML.reruestXMLCustomaryInfo(r)); System.out.println(XStreamUtil.toXml(r));
		 */

		/*
		 * MobileLogonRequest mobileLogonRequest = new MobileLogonRequest(); mobileLogonRequest.setChannelId("1"); mobileLogonRequest.setDestinationIp("2"); mobileLogonRequest.setExtend("3");
		 * mobileLogonRequest.setFirmId("5"); mobileLogonRequest.setImei("6"); mobileLogonRequest.setPassword("7"); mobileLogonRequest.setServiceId("8"); mobileLogonRequest.setShortmsg("9");
		 * mobileLogonRequest.setSourceIp("10"); mobileLogonRequest.setUid("11"); System.out.println(ParseXML.requestXMLMobileLogon(mobileLogonRequest));
		 */
	}

}
