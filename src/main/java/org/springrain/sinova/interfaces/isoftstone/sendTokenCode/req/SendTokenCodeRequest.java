package org.springrain.sinova.interfaces.isoftstone.sendTokenCode.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 短信验证码下发接口
 * @author frank
 *
 */
@XStreamAlias(value = "request")
public class SendTokenCodeRequest {

	@XStreamAlias(value = "uid")
	private String uid; // 4A主账号 必填
	@XStreamAlias(value = "ChannelID")
	private String ChannelID; // 渠道代码，固定值必填01 web、02 wap
	@XStreamAlias(value = "firmID")
	private String firmID; // 厂商编码必填
	@XStreamAlias(value = "shortflag")
	private String shortflag; // 01:单纯短信验证码下发 02:主账号密码修改，短信验证码下发
	@XStreamAlias(value = "sourceIP")
	private String sourceIP; // 源IP
	@XStreamAlias(value = "destinationIP")
	private String destinationIP; // 目地IP
	@XStreamAlias(value = "extend")
	private String extend; // 扩展字段可选

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getChannelID() {
		return ChannelID;
	}

	public void setChannelID(String channelID) {
		ChannelID = channelID;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public String getShortflag() {
		return shortflag;
	}

	public void setShortflag(String shortflag) {
		this.shortflag = shortflag;
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

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

}
