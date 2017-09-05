package org.springrain.sinova.interfaces.isoftstone;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import org.apache.commons.lang3.BooleanUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springrain.sinova.interfaces.IIsoftstoneInterface;
import org.springrain.sinova.interfaces.isoftstone.checkTokenCode.rep.CheckTokenCodeResponse;
import org.springrain.sinova.interfaces.isoftstone.checkTokenCode.req.CheckTokenCodeRequest;
import org.springrain.sinova.interfaces.isoftstone.customaryInfo.rep.CustomaryInfoResponse;
import org.springrain.sinova.interfaces.isoftstone.customaryInfo.req.CustomaryInfoRequest;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.rep.MobileLogonResponse;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.req.MobileLogonRequest;
import org.springrain.sinova.interfaces.isoftstone.sendTokenCode.rep.SendTokenCodeResponse;
import org.springrain.sinova.interfaces.isoftstone.sendTokenCode.req.SendTokenCodeRequest;
import org.springrain.sinova.util.Const;
import org.springrain.system.entity.User;
import org.springrain.system.util.Constant;
import org.springrain.system.util.PropertyFileCache;

@Service("softstoneInterface")
public class IsoftstoneInterfaceImpl implements IIsoftstoneInterface {

	private static final Logger logger = LoggerFactory.getLogger(IsoftstoneInterfaceImpl.class);
	private PropertyFileCache pfCache = PropertyFileCache.getInstance();

	private static final String SOFT_STONE = "softStone.url";
	private static final String MOBILE_LOGON = "mobileLogon.url";
	private static final String FIRM_ID = "sinovatech";
	private static final String SERVICE_ID = "8528894278356196767";
	// private static final String SOURCE_IP = "10.208.217.173";//生产
	// private static final String SOURCE_IP = "10.204.96.213";//测试
	private static String SOURCE_IP = "";
	private static final String DESTINATION_IP = "10.208.215.135";
	private static final String DESTINATION_PORT = "9090";

	/**
	 * 通信开关
	 * 
	 * @param hint
	 * @return
	 */
	private boolean getFuncSwitch() {
		String thisIp = "";
		try {
			thisIp = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			// SOURCE_IP = "10.204.96.213";//测试
			// SOURCE_IP = "10.208.217.173";//生产
		}
		SOURCE_IP = thisIp.substring(thisIp.indexOf("/") + 1);
		boolean b = BooleanUtils.toBoolean(pfCache.get(Const.INTERFACE_FILE, "function.switch"));
		logger.info("function.switch ", b);
		return b;
	}

	@Override
	public CustomaryInfoResponse search4A2BossNo(String fourA) {
		logger.info("调用search4A2BossNo   start");
		if (!this.getFuncSwitch()) {
			logger.info("------------communication channel has been closed------------");
			return null;
		}
		CustomaryInfoResponse response = new CustomaryInfoResponse();

		String requestUrl = pfCache.get(Const.INTERFACE_FILE, SOFT_STONE);
		logger.info("url:" + requestUrl);
		CustomaryInfoRequest request = new CustomaryInfoRequest();
		request.setCongAccount(fourA);
		request.setFirmID(FIRM_ID);
		request.setChannelID("01");
		request.setSourceIP(SOURCE_IP);
		request.setDestinationIP(DESTINATION_IP);
		request.setDestinationPort(DESTINATION_PORT);
		request.getExtend().setBusinessInfo("炎黄二维码系统4A查询BOSS工号");
		String reqxml = ParseXML.requestXMLCustomaryInfo(request);

		// 创建一个服务(service)调用(call)
		try {
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(requestUrl));
			call.setOperationName(new QName("getCustomaryInfo"));// 接口方法getreallocation
			// 这carelessness个要跟wsdl地址里面的节点名称必须一样，XMLType.XSD_STRING表示写了参数的类型，String.class映射类
			call.addParameter("in0", XMLType.XSD_STRING, String.class, javax.xml.rpc.ParameterMode.IN);
			call.setUseSOAPAction(true);
			call.setReturnClass(String.class);
			String repXml;
			logger.info("-----search4A2BossNo入参报文-----" + reqxml);
			repXml = (String) call.invoke(new Object[] { reqxml });
			logger.info("-----search4A2BossNo返回报文-----" + repXml);
			response = ParseXML.responseXMLCustomaryInfo(repXml);
		} catch (ServiceException e1) {
			logger.error("【org.apache.axis.client.Service异常】");
			e1.printStackTrace();
		} catch (MalformedURLException e2) {
			logger.error("【setTargetEndpointAddress异常】");
			e2.printStackTrace();
		} catch (RemoteException e2) {
			logger.error("【call.invoke异常】");
			e2.printStackTrace();
		} catch (DocumentException e3) {
			logger.error("【回参解析异常】");
			e3.printStackTrace();
		}
		return response;
	}

	@Override
	public MobileLogonResponse validate4A(User u, String shortmsg) throws Exception {
		logger.info("调用validate4A   start");
		if (!this.getFuncSwitch()) {
			logger.info("------------communication channel has been closed------------");
			return null;
		}
		MobileLogonResponse response = new MobileLogonResponse();

		String requestUrl = pfCache.get(Const.INTERFACE_FILE, MOBILE_LOGON);
		logger.info("url:" + requestUrl);
		MobileLogonRequest mobileLogonRequest = new MobileLogonRequest();
		mobileLogonRequest.setUid(u.getAccount());// 4A主账号必填
		mobileLogonRequest.setPassword(u.getPassword());// 主账号密码必填
		mobileLogonRequest.setImei("");// 手机的IMEI码可选
		mobileLogonRequest.setChannelID("01");// 渠道代码，固定值必填01 web、02 wap
		mobileLogonRequest.setFirmID(FIRM_ID);// 厂商编码必填
		mobileLogonRequest.setServiceID(SERVICE_ID);// 从账号服务编码必填
		mobileLogonRequest.setShortmsg(shortmsg);// 是否下发短信，必填 00--表示不下发,01— 短信验证码下发---校验时为03
		mobileLogonRequest.setSourceIP(SOURCE_IP);// 源IP-
		mobileLogonRequest.setDestinationIP(DESTINATION_IP);// 目地IP
		mobileLogonRequest.setDestinationPort(DESTINATION_PORT);
		mobileLogonRequest.setExtend(new org.springrain.sinova.interfaces.isoftstone.mobileLogon.req.Extend("炎黄二维码系统4A登陆"));
		String reqxml = ParseXML.requestXMLMobileLogon(mobileLogonRequest);

		// 创建一个服务(service)调用(call)
		try {
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(requestUrl));
			call.setOperationName(new QName("mobileLogon"));// 接口方法getreallocation
			// 这carelessness个要跟wsdl地址里面的节点名称必须一样，XMLType.XSD_STRING表示写了参数的类型，String.class映射类
			call.addParameter("in0", XMLType.XSD_STRING, String.class, javax.xml.rpc.ParameterMode.IN);
			call.setUseSOAPAction(true);
			call.setReturnClass(String.class);
			String repXml;
			logger.info("-----validate4A入参报文-----" + reqxml);
			repXml = (String) call.invoke(new Object[] { reqxml });
			logger.info("-----validate4A返回报文-----" + repXml);
			response = ParseXML.responseXMLMobileLogon(repXml);
		} catch (ServiceException e1) {
			logger.error("【org.apache.axis.client.Service异常】");
			e1.printStackTrace();
		} catch (MalformedURLException e2) {
			logger.error("【setTargetEndpointAddress异常】");
			e2.printStackTrace();
		} catch (RemoteException e2) {
			logger.error("【call.invoke异常】");
			e2.printStackTrace();
			response.setRetCode(Constant.ONE_NET_ERROR);//-99
		} catch (DocumentException e3) {
			logger.error("【回参解析异常】");
			e3.printStackTrace();
		}
		return response;
	}

	@Override
	public SendTokenCodeResponse sendTokenCode(User u) {
		logger.info("调用sendTokenCode   start");
		if (!this.getFuncSwitch()) {
			logger.info("------------communication channel has been closed------------");
			return null;
		}
		SendTokenCodeResponse response = new SendTokenCodeResponse();

		String requestUrl = pfCache.get(Const.INTERFACE_FILE, MOBILE_LOGON);
		logger.info("url:" + requestUrl);
		SendTokenCodeRequest sendTokenCodeRequest = new SendTokenCodeRequest();
		sendTokenCodeRequest.setUid(u.getAccount());
		sendTokenCodeRequest.setChannelID("01");
		sendTokenCodeRequest.setFirmID(FIRM_ID);
		sendTokenCodeRequest.setShortflag("01");
		sendTokenCodeRequest.setSourceIP(SOURCE_IP);
		sendTokenCodeRequest.setDestinationIP(DESTINATION_IP);
		sendTokenCodeRequest.setExtend("");
		String reqxml = ParseXML.requestXMLSendTokenCode(sendTokenCodeRequest);

		// 创建一个服务(service)调用(call)
		try {
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(requestUrl));
			call.setOperationName(new QName("sendTokenCode"));// 接口方法getreallocation
			// 这carelessness个要跟wsdl地址里面的节点名称必须一样，XMLType.XSD_STRING表示写了参数的类型，String.class映射类
			call.addParameter("in0", XMLType.XSD_STRING, String.class, javax.xml.rpc.ParameterMode.IN);
			call.setUseSOAPAction(true);
			call.setReturnClass(String.class);
			String repXml;
			logger.info("-----sendTokenCode入参报文-----" + reqxml);
			repXml = (String) call.invoke(new Object[] { reqxml });
			logger.info("-----sendTokenCode返回报文-----" + repXml);
			response = ParseXML.responseXMLSendTokenCode(repXml);
		} catch (ServiceException e1) {
			logger.error("【org.apache.axis.client.Service异常】");
			e1.printStackTrace();
		} catch (MalformedURLException e2) {
			logger.error("【setTargetEndpointAddress异常】");
			e2.printStackTrace();
		} catch (RemoteException e2) {
			logger.error("【call.invoke异常】");
			e2.printStackTrace();
		} catch (DocumentException e3) {
			logger.error("【回参解析异常】");
			e3.printStackTrace();
		}
		return response;
	}

	@Override
	public CheckTokenCodeResponse checkTokenCode(User u) {
		logger.info("调用checkTokenCode   start");
		if (!this.getFuncSwitch()) {
			logger.info("------------communication channel has been closed------------");
			return null;
		}
		CheckTokenCodeResponse response = new CheckTokenCodeResponse();

		String requestUrl = pfCache.get(Const.INTERFACE_FILE, MOBILE_LOGON);
		logger.info("url:" + requestUrl);
		CheckTokenCodeRequest checkTokenCodeRequest = new CheckTokenCodeRequest();
		checkTokenCodeRequest.setUid(u.getAccount());
		checkTokenCodeRequest.setTokenCode(u.getDescription());
		checkTokenCodeRequest.setChannelID("01");
		checkTokenCodeRequest.setFirmID(FIRM_ID);
		checkTokenCodeRequest.setBusinessID("01");
		checkTokenCodeRequest.setSourceIP(DESTINATION_IP);
		checkTokenCodeRequest.setDestinationIP(DESTINATION_IP);
		checkTokenCodeRequest.setDestinationPort(DESTINATION_PORT);
		checkTokenCodeRequest.setExtend(new org.springrain.sinova.interfaces.isoftstone.checkTokenCode.req.Extend("炎黄二维码系统4A登陆短信校验"));

		String reqxml = ParseXML.requestXMLCheckTokenCode(checkTokenCodeRequest);

		// 创建一个服务(service)调用(call)
		try {
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(requestUrl));
			call.setOperationName(new QName("checkTokenCode"));// 接口方法getreallocation
			// 这carelessness个要跟wsdl地址里面的节点名称必须一样，XMLType.XSD_STRING表示写了参数的类型，String.class映射类
			call.addParameter("in0", XMLType.XSD_STRING, String.class, javax.xml.rpc.ParameterMode.IN);
			call.setUseSOAPAction(true);
			call.setReturnClass(String.class);
			String repXml;
			logger.info("-----checkTokenCode入参报文-----" + reqxml);
			repXml = (String) call.invoke(new Object[] { reqxml });
			logger.info("-----checkTokenCode返回报文-----" + repXml);
			response = ParseXML.responseXMLCheckTokenCode(repXml);
		} catch (ServiceException e1) {
			logger.error("【org.apache.axis.client.Service异常】");
			e1.printStackTrace();
		} catch (MalformedURLException e2) {
			logger.error("【setTargetEndpointAddress异常】");
			e2.printStackTrace();
		} catch (RemoteException e2) {
			logger.error("【call.invoke异常】");
			e2.printStackTrace();
		} catch (DocumentException e3) {
			logger.error("【回参解析异常】");
			e3.printStackTrace();
		}
		return response;
	}

	public static void main(String[] args) {
		String s = "shoptest/10.204.96.213";
		System.out.println(s.indexOf("/"));
		System.out.println(s.substring(s.indexOf("/") + 1));
		// logger.info("ip:"+InetAddress.getLocalHost());
		// logger.info("ip:"+InetAddress.getLoopbackAddress());
		// System.out.println(s.);
	}
}
