package org.springrain.sinova.interfaces.isoftstone.mobileLogon.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 移动端登录认证接口Request
 * 
 * @author frank
 * 
 */
@XStreamAlias(value = "request")
public class MobileLogonRequest {

	@XStreamAlias(value = "uid")
	private String uid; // 4A主账号 必填
	@XStreamAlias(value = "password")
	private String password; // 主账号密码 必填
	@XStreamAlias(value = "imei")
	private String imei; // 手机的IMEI码可
	@XStreamAlias(value = "channelID")
	private String channelID; // -渠道代码，固定值必填01 web、02 wap
	@XStreamAlias(value = "firmID")
	private String firmID; // 厂商编码必填
	@XStreamAlias(value = "serviceID")
	private String serviceID; // 从账号服务编码必填
	@XStreamAlias(value = "shortmsg")
	private String shortmsg; // 是否下发短信，必填 00--表示不下发 01— 短信验证码下发---校验时为03
	@XStreamAlias(value = "sourceIP")
	private String sourceIP; // 源IP
	@XStreamAlias(value = "destinationIP")
	private String destinationIP; // 目地IP
	@XStreamAlias(value = "destinationPort")
	private String destinationPort; // 目地IP
	@XStreamAlias(value = "extend")
	private Extend extend; // 扩展字段可选

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getShortmsg() {
		return shortmsg;
	}

	public void setShortmsg(String shortmsg) {
		this.shortmsg = shortmsg;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getDestinationIP() {
		return destinationIP;
	}

	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}

	public Extend getExtend() {
		return extend;
	}

	public void setExtend(Extend extend) {
		this.extend = extend;
	}

	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

}
