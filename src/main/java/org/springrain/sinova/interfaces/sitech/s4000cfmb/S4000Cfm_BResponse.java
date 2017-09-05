package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import org.springrain.sinova.interfaces.sitech.ResponseHead;



public class S4000Cfm_BResponse extends ResponseHead{
	/**
	 * 操作流水
	 */
	private String  loginAccept;
	/**
	 * 生效时间
	 */
	private String effDate;
	/**
	 * 失效时间
	 */
	private String expDate;
	/**
	 * 	 * 用户品牌 001     全球通    003 神州行  002   动感地带  001    全球通优惠 005    宽带 0 无品牌   
	 *                                                                   
	 */
	private String brandId;
	/**
	 * SP业务标志   Y SP业务   N 非SP业务
	 */
	private String   spFlag;
	/**
	 *  生失效标志   LJSX 订购操作表示立即生效，退订操作表示立即失效 
	 */
	private String   effExpMode;
	public String getLoginAccept() {
		return loginAccept;
	}
	public void setLoginAccept(String loginAccept) {
		this.loginAccept = loginAccept;
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
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getSpFlag() {
		return spFlag;
	}
	public void setSpFlag(String spFlag) {
		this.spFlag = spFlag;
	}
	public String getEffExpMode() {
		return effExpMode;
	}
	public void setEffExpMode(String effExpMode) {
		this.effExpMode = effExpMode;
	}
	
	
}
