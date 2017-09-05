package org.springrain.sinova.interfaces.sitech.sGetAction;
/**
 * 
 */
public class TermiInfo {
	private String termiType;//终端类型
	private String termiName;//终端名称
	private String termiPrice;//终端市场价
	private String termiRealPrice;//终端实收金额
	private String termiDescribe;//终端描述
	private String maxNum;//最大团购数量
	private String remnantNum;//剩余数量
	public String getTermiType() {
		return termiType;
	}
	public void setTermiType(String termiType) {
		this.termiType = termiType;
	}
	public String getTermiName() {
		return termiName;
	}
	public void setTermiName(String termiName) {
		this.termiName = termiName;
	}
	public String getTermiPrice() {
		return termiPrice;
	}
	public void setTermiPrice(String termiPrice) {
		this.termiPrice = termiPrice;
	}
	public String getTermiRealPrice() {
		return termiRealPrice;
	}
	public void setTermiRealPrice(String termiRealPrice) {
		this.termiRealPrice = termiRealPrice;
	}
	public String getTermiDescribe() {
		return termiDescribe;
	}
	public void setTermiDescribe(String termiDescribe) {
		this.termiDescribe = termiDescribe;
	}
	public String getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}
	public String getRemnantNum() {
		return remnantNum;
	}
	public void setRemnantNum(String remnantNum) {
		this.remnantNum = remnantNum;
	}

	
}
