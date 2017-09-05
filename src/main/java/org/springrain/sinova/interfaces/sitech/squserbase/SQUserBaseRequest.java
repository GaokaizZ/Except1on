package org.springrain.sinova.interfaces.sitech.squserbase;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "ROOT")
public class SQUserBaseRequest {
	
	@XStreamAlias(value = "PHONE_NO")
	private String phoneNo;   //手机号
	
	@XStreamAlias(value = "MASTER_SERV_ID")
	private String masterServId; //渠道编码

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

}
