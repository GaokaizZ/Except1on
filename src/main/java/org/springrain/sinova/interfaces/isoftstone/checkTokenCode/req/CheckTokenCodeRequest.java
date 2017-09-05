package org.springrain.sinova.interfaces.isoftstone.checkTokenCode.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "request")
public class CheckTokenCodeRequest {

	@XStreamAlias(value = "uid")
	private String uid; // 4A主账号 必填
	@XStreamAlias(value = "tokenCode")
	private String tokenCode; // 短信验证码必填
	@XStreamAlias(value = "channelID")
	private String channelID; // 渠道代码，固定值必填01 web、02 wap
	@XStreamAlias(value = "firmID")
	private String firmID; // 厂商编码必填

	/*
	 * <!-- 业务代码必填 01--标示单纯短信验证码校验 02—修改密码 03--主账号认证校验验证码 -->
	 */
	@XStreamAlias(value = "businessID")
	private String businessID; // 厂商编码必填
	@XStreamAlias(value = "sourceIP")
	private String sourceIP; // 源IP
	@XStreamAlias(value = "destinationIP")
	private String destinationIP; // 目地IP-
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

	public String getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
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

	public String getBusinessID() {
		return businessID;
	}

	public void setBusinessID(String businessID) {
		this.businessID = businessID;
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
