package org.springrain.sinova.interfaces.sitech.sloginno.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BusiInfo {

	@XStreamAlias(value = "VALID_FLAG")
	private String validFlag;
	@XStreamAlias(value = "GROUP_ID")
	private String groupId;
	@XStreamAlias(value = "LOGIN_NO")
	private String loginNo;
	@XStreamAlias(value = "LOGIN_NAME")
	private String loginName;
	@XStreamAlias(value = "PAGE_NUM")
	private String pageNum;
	@XStreamAlias(value = "PAGE_AMOUNT")
	private String pageAmount;

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageAmount() {
		return pageAmount;
	}

	public void setPageAmount(String pageAmount) {
		this.pageAmount = pageAmount;
	}

}
