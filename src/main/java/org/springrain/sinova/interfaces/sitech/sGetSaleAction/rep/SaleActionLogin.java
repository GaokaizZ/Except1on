package org.springrain.sinova.interfaces.sitech.sGetSaleAction.rep;

import java.util.List;

import org.springrain.sinova.interfaces.sitech.ResponseHead;
import org.springrain.sinova.interfaces.sitech.sGetAction.TermiInfo;

public class SaleActionLogin extends ResponseHead {

	private String actId;
	private String actName;
	private String startDate;
	private String endDate;
	private String taskId;
	private String mktDiction;
	private String custGroupId;
	private String priorityCode;
	private String isCheck;
	private String douwinFlag;
	private String signFlag;
	private String currentOrder;
	private String maxOrder;
	private String preOrder;
	private String actClass;
	private String actClassName;
	private String orderNumber;
	private List<Attachs> attList;
	private List<TermiInfo> termiList;
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getMktDiction() {
		return mktDiction;
	}
	public void setMktDiction(String mktDiction) {
		this.mktDiction = mktDiction;
	}
	public String getCustGroupId() {
		return custGroupId;
	}
	public void setCustGroupId(String custGroupId) {
		this.custGroupId = custGroupId;
	}
	public String getPriorityCode() {
		return priorityCode;
	}
	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getDouwinFlag() {
		return douwinFlag;
	}
	public void setDouwinFlag(String douwinFlag) {
		this.douwinFlag = douwinFlag;
	}
	public String getSignFlag() {
		return signFlag;
	}
	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}
	public String getCurrentOrder() {
		return currentOrder;
	}
	public void setCurrentOrder(String currentOrder) {
		this.currentOrder = currentOrder;
	}
	public String getMaxOrder() {
		return maxOrder;
	}
	public void setMaxOrder(String maxOrder) {
		this.maxOrder = maxOrder;
	}
	public String getPreOrder() {
		return preOrder;
	}
	public void setPreOrder(String preOrder) {
		this.preOrder = preOrder;
	}
	public String getActClass() {
		return actClass;
	}
	public void setActClass(String actClass) {
		this.actClass = actClass;
	}
	public String getActClassName() {
		return actClassName;
	}
	public void setActClassName(String actClassName) {
		this.actClassName = actClassName;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public List<Attachs> getAttList() {
		return attList;
	}
	public void setAttList(List<Attachs> attList) {
		this.attList = attList;
	}
	public List<TermiInfo> getTermiList() {
		return termiList;
	}
	public void setTermiList(List<TermiInfo> termiList) {
		this.termiList = termiList;
	}
}
