package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep;

public class A00 {
	private String payMoney;
	private String feeCode;
	private String detailCode;
	private String feeType;
	private String accountType;
	private String isPure;
	private String feeName;
	private String isModify;
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getDetailCode() {
		return detailCode;
	}
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getIsPure() {
		return isPure;
	}
	public void setIsPure(String isPure) {
		this.isPure = isPure;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getIsModify() {
		return isModify;
	}
	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}
	public A00(String payMoney, String feeCode, String detailCode,
			String feeType, String accountType, String isPure, String feeName,
			String isModify) {
		super();
		this.payMoney = payMoney;
		this.feeCode = feeCode;
		this.detailCode = detailCode;
		this.feeType = feeType;
		this.accountType = accountType;
		this.isPure = isPure;
		this.feeName = feeName;
		this.isModify = isModify;
	}
	public A00() {
		super();
	}
	
	
}
