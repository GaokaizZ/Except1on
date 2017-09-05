package org.springrain.sinova.interfaces.sitech.s4000cfmb;

/**
 * 商城二维码业务受理入参
 * @author 王玉军
 *
 */

public class New_S4000Cfm_BReruest {
	/**
	 * 操作工号
	 */
	
	private String loginNo;  
	/**
	 * 工号密码
	 */
	
	private String loginPwd;  
	/**
	 * 服务号码
	 */
	
	private String   serviceNo;  
	/**
	 * 主体服务标识
	 */
	
	private String   masterServId;   
	/**
	 * 操作类型 A-订购 U-修改 D-退订 N-保持   资费的操作类型传N，属性的操作类型传U 
	 */

	private String  operateType;
	
	/**
	 * 产品资费标识
	 */
	
	private String   prodPrcId; 
	/**
	 * 生效时间
	 */
	
	private String   effDate;
	/**
	 * 失效时间
	 */
	
	private String   expDate;
	/**
	 * 推荐工号 非空    
	 */
	
	private String  developNo; 
	/**
	 * 省代码 非空    10011 山西省
	 */
	
	private String  provinceGroup ; 
	
	/**
	 * 操作模块代码
	 */
	
	private String  opCode;

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

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getProdPrcId() {
		return prodPrcId;
	}

	public void setProdPrcId(String prodPrcId) {
		this.prodPrcId = prodPrcId;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getDevelopNo() {
		return developNo;
	}

	public void setDevelopNo(String developNo) {
		this.developNo = developNo;
	}

	public String getProvinceGroup() {
		return provinceGroup;
	}

	public void setProvinceGroup(String provinceGroup) {
		this.provinceGroup = provinceGroup;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	} 
	
	

	
	
	
}
