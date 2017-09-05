package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep;

import java.util.List;

import org.springrain.sinova.interfaces.sitech.ResponseHead;

public class WsGetSaleMeansContentReponse extends ResponseHead {
	private String actionId; // 活动ID
	private String actionName; // 活动名称
	private String actionDesc;// 活动描述
	private String actionDate;// 活动起止日期
	private String sendStartTime; // 短信发送开始时间
	private String sendEndTime;// 短信发送截至时间
	private String mktDiction;// 营销用语
	private String provideType;// 配送方式
	private String custgroupid;// -客户群编码
	private List<MeanContent> meansList;// 营销方式

	public String getCustgroupid() {
		return custgroupid;
	}

	public void setCustgroupid(String custgroupid) {
		this.custgroupid = custgroupid;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public String getActionDate() {
		return actionDate;
	}

	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}

	public String getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(String sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	public String getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(String sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public String getMktDiction() {
		return mktDiction;
	}

	public void setMktDiction(String mktDiction) {
		this.mktDiction = mktDiction;
	}

	public String getProvideType() {
		return provideType;
	}

	public void setProvideType(String provideType) {
		this.provideType = provideType;
	}

	public List<MeanContent> getMeansList() {
		return meansList;
	}

	public void setMeansList(List<MeanContent> meansList) {
		this.meansList = meansList;
	}

	public WsGetSaleMeansContentReponse() {
		super();
	}

}
