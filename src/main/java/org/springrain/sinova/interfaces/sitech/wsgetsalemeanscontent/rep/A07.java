package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep;

public class A07 {
	private String payMoney;
	private String feeCode;
	private String feeType;
	private String feeName;
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
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public A07(String payMoney, String feeCode, String feeType,
			String feeName) {
		super();
		this.payMoney = payMoney;
		this.feeCode = feeCode;
		this.feeType = feeType;
		this.feeName = feeName;
	}
	public A07() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}