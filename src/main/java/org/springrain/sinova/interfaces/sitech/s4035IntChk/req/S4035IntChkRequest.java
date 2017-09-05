package org.springrain.sinova.interfaces.sitech.s4035IntChk.req;

/**
 * 登录活动校验
 * @author frank
 *
 */
public class S4035IntChkRequest {
	private String province;//非空 省份标识
	private String loginNO;//非空 操作工号
	private String opNote;//非空 操作信息
	private String opCode;//写死1147
	private String serviceNO;//非空 服务号码
	private String actID;//非空 活动名称码
	private String meansID;//非空 档次编码
	private String custGroupID;// 目标客户id
	private String channelType;//非空 渠道
	private String phoneNo;
	private String masterServId;//主服务类型,从用户信息里取
	private String a28TransferSerIalno;
	private String a17TransferSerIalno;
	private String taPhoneNo;
	private String taIdNo;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLoginNO() {
		return loginNO;
	}
	public void setLoginNO(String loginNO) {
		this.loginNO = loginNO;
	}
	public String getOpNote() {
		return opNote;
	}
	public void setOpNote(String opNote) {
		this.opNote = opNote;
	}
	public String getServiceNO() {
		return serviceNO;
	}
	public void setServiceNO(String serviceNO) {
		this.serviceNO = serviceNO;
	}
	public String getActID() {
		return actID;
	}
	public void setActID(String actID) {
		this.actID = actID;
	}
	public String getMeansID() {
		return meansID;
	}
	public void setMeansID(String meansID) {
		this.meansID = meansID;
	}
	public String getCustGroupID() {
		return custGroupID;
	}
	public void setCustGroupID(String custGroupID) {
		this.custGroupID = custGroupID;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMasterServId() {
		return masterServId;
	}
	public void setMasterServId(String masterServId) {
		this.masterServId = masterServId;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public String getA28TransferSerIalno() {
		return a28TransferSerIalno;
	}
	public void setA28TransferSerIalno(String a28TransferSerIalno) {
		this.a28TransferSerIalno = a28TransferSerIalno;
	}
	public String getA17TransferSerIalno() {
		return a17TransferSerIalno;
	}
	public void setA17TransferSerIalno(String a17TransferSerIalno) {
		this.a17TransferSerIalno = a17TransferSerIalno;
	}
	public String getTaPhoneNo() {
		return taPhoneNo;
	}
	public void setTaPhoneNo(String taPhoneNo) {
		this.taPhoneNo = taPhoneNo;
	}
	public String getTaIdNo() {
		return taIdNo;
	}
	public void setTaIdNo(String taIdNo) {
		this.taIdNo = taIdNo;
	}
	
}
