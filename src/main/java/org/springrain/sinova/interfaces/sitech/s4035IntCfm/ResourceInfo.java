package org.springrain.sinova.interfaces.sitech.s4035IntCfm;

public class ResourceInfo {
	private String receivePhone;//绑定的手机的号码,可空（终端激活才支持这个元素不能为空）
	private String imeiNo;//绑定的IMEI号，可空（终端激活才支持这个元素不能为空）
	private String colorId;//终端颜色，网上商城(必须)销售需要颜色
	private String resourseCode;//资源编码,/PRE_ACT_TYPE = 01 (终端合约分离)/PRE_ACT_TYPE = 02PAD客户端，必须传值，否则可空
	private String brandCode;///PRE_ACT_TYPE = 01 (终端合约分离)/PRE_ACT_TYPE = 02PAD客户端，必须传值，否则可空
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getImeiNo() {
		return imeiNo;
	}
	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public String getResourseCode() {
		return resourseCode;
	}
	public void setResourseCode(String resourseCode) {
		this.resourseCode = resourseCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	
	
	
}
