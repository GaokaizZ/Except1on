package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep;

public class B02 {
	private String payMoney;
	private String feeCode;
	private String detailCode;
	private String effType;
	private String payType;
	private String feeType;
	private String feeName;
	private String offSetMonth;
	private String effDate;
	private String feeRate;
	private String servNo;
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
	public String getEffType() {
		return effType;
	}
	public void setEffType(String effType) {
		this.effType = effType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
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
	public String getOffSetMonth() {
		return offSetMonth;
	}
	public void setOffSetMonth(String offSetMonth) {
		this.offSetMonth = offSetMonth;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}
	public String getServNo() {
		return servNo;
	}
	public void setServNo(String servNo) {
		this.servNo = servNo;
	}
	public B02(String payMoney, String feeCode, String detailCode,
			String effType, String payType, String feeType, String feeName,
			String offSetMonth, String effDate, String feeRate, String servNo) {
		super();
		this.payMoney = payMoney;
		this.feeCode = feeCode;
		this.detailCode = detailCode;
		this.effType = effType;
		this.payType = payType;
		this.feeType = feeType;
		this.feeName = feeName;
		this.offSetMonth = offSetMonth;
		this.effDate = effDate;
		this.feeRate = feeRate;
		this.servNo = servNo;
	}
	public B02() {
		super();
	}
	
}