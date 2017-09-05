package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SpInfo {
	
	@XStreamAlias(value = "OPERATE_TYPE")
	private String   operateType="" ;
	/**
	 * 企业代码 不能为空
	 */
	@XStreamAlias(value = "SP_ID")
	private String  spId="";
	/**
	 * 业务代码 不能为空
	 */
	@XStreamAlias(value = "BUSI_CODE")
	private String   busiCode="";
	/**
	 * 业务类型
	 */
	@XStreamAlias(value = "BUSI_TYPE")
	private String  busiType="";
	/**
	 * 赠送号码
	 */
	@XStreamAlias(value = "OTHER_SVCNUM")
	private String   otherSvcNum="";
	/**
	 * SP变化标识
	 */
	@XStreamAlias(value = "SPCHANGE_FLAG")
	private String  spchangeFlag="";
	/**
	 * 操作来源  非空    01-WEB 02-网上营业厅 03-WAP 04-短信 05-彩信 06-KJAVA 07-1860/营业厅 08-BOSS 09-SP
	 */
	@XStreamAlias(value = "OP_SOURCE")
	private String   opSource="";
	/**
	 * 手机搜索标识
	 */
	@XStreamAlias(value = "SEARCH_ID")
	private String   searchId="";
	
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public String getBusiCode() {
		return busiCode;
	}
	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getOtherSvcNum() {
		return otherSvcNum;
	}
	public void setOtherSvcNum(String otherSvcNum) {
		this.otherSvcNum = otherSvcNum;
	}
	public String getSpchangeFlag() {
		return spchangeFlag;
	}
	public void setSpchangeFlag(String spchangeFlag) {
		this.spchangeFlag = spchangeFlag;
	}
	public String getOpSource() {
		return opSource;
	}
	public void setOpSource(String opSource) {
		this.opSource = opSource;
	}
	public String getSearchId() {
		return searchId;
	}
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	
	
	
}
