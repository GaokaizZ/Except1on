package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class UserRate {
	/**
	 *     付费比率 写死1；非空 ；
	 */
	@XStreamAlias(value = "FEE_RATE")
	private String   feeRate="";
	
	/**
	 *     费用代码；写死1；非空  ；
	 */
	@XStreamAlias(value = "FEE_CODE")
	private String   feeCode="";
	/**
	 *     费用明细   即二级账目项；非空
	 */
	@XStreamAlias(value = "DETAL_CODE")
	private String   detalCode="";
	
	/**
	 *     生效时间；
	 */
	@XStreamAlias(value = "EFF_DATE")
	private String   effDate="";
	/**
	 *     失效时间；
	 */
	@XStreamAlias(value = "EXP_DATE")
	private String   expDate="";
	
	public String getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getDetalCode() {
		return detalCode;
	}
	public void setDetalCode(String detalCode) {
		this.detalCode = detalCode;
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
	
	
}
