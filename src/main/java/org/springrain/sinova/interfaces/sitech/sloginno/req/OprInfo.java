package org.springrain.sinova.interfaces.sitech.sloginno.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OprInfo {

	@XStreamAlias(value = "REGION_ID")
	private String regionId;

	@XStreamAlias(value = "CHANNEL_TYPE")
	private String channelType;

	@XStreamAlias(value = "LOGIN_NO")
	private String loginNo;

	@XStreamAlias(value = "LOGIN_PWD")
	private String loginPwd;

	@XStreamAlias(value = "IP_ADDRESS")
	private String ipAddress;

	@XStreamAlias(value = "GROUP_ID")
	private String groupId;

	@XStreamAlias(value = "CONTACT_ID")
	private String contactId;

	@XStreamAlias(value = "OP_CODE")
	private String opCode;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

}
