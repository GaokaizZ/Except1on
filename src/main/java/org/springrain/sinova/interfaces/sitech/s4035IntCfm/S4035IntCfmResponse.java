package org.springrain.sinova.interfaces.sitech.s4035IntCfm;


import org.springrain.sinova.interfaces.sitech.ResponseHead;

public class S4035IntCfmResponse extends ResponseHead{
	private String orderId;//订单id
	private String createAccept;//业务流水
	private String passFlag;//处理结果,如果是Y表示业务办理通过
	private LuckyawardInfo luckInfo;//中奖信息
	private MeanAllResponse meanAll;
	private String inXml;
	private String outXml;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCreateAccept() {
		return createAccept;
	}
	public void setCreateAccept(String createAccept) {
		this.createAccept = createAccept;
	}
	public String getPassFlag() {
		return passFlag;
	}
	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}
	public LuckyawardInfo getLuckInfo() {
		return luckInfo;
	}
	public void setLuckInfo(LuckyawardInfo luckInfo) {
		this.luckInfo = luckInfo;
	}
	public String getInXml() {
		return inXml;
	}
	public void setInXml(String inXml) {
		this.inXml = inXml;
	}
	public String getOutXml() {
		return outXml;
	}
	public void setOutXml(String outXml) {
		this.outXml = outXml;
	}
	public MeanAllResponse getMeanAll() {
		return meanAll;
	}
	public void setMeanAll(MeanAllResponse meanAll) {
		this.meanAll = meanAll;
	}
}
