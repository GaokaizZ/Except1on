package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 套餐办理
 * @author frank
 *
 */
@XStreamAlias(value = "ROOT")
public class S4000Cfm_BReruest {
	/**
	 * 操作工号
	 */
	@XStreamAlias(value = "LOGIN_NO")
	private String loginNO = "";  
	/**
	 * 工号密码
	 */
	@XStreamAlias(value = "LOGIN_PWD")
	private String loginPwd = "";  
	/**
	 * 服务号码
	 */
	@XStreamAlias(value = "SERVICE_NO")
	private String   serviceNo = "";  
	/**
	 * 主体服务标识
	 */
	@XStreamAlias(value = "MASTER_SERV_ID")
	private String   masterServId = "";   
	/**
	 * 是否需要二次确认  0不需要，1需要  可空
	 */
	@XStreamAlias(value = "RE_CONFIRM")
	private String  reConfirm = "";  
	/**
	 * 操作模块代码
	 */
	@XStreamAlias(value = "OP_CODE")
	private String  opCode = "";  
	/**
	 * 省代码 非空    10011 山西省
	 */
	@XStreamAlias(value = "PROVINCE_GROUP")
	private String  provinceGroup = ""; 
	
	
	
	
	@XStreamAlias(value = "BUSI_INFO")
	private BusiInfo busiInfo = new BusiInfo();

	public String getLoginNO() {
		return loginNO;
	}

	public void setLoginNO(String loginNO) {
		this.loginNO = loginNO;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	public String getMasterServId() {
		return masterServId;
	}

	public void setMasterServId(String masterServId) {
		this.masterServId = masterServId;
	}

	public String getReConfirm() {
		return reConfirm;
	}

	public void setReConfirm(String reConfirm) {
		this.reConfirm = reConfirm;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getProvinceGroup() {
		return provinceGroup;
	}

	public void setProvinceGroup(String provinceGroup) {
		this.provinceGroup = provinceGroup;
	}


	public BusiInfo getBusiInfo() {
		return busiInfo;
	}

	public void setBusiInfo(BusiInfo busiInfo) {
		this.busiInfo = busiInfo;
	}
	
	
}
