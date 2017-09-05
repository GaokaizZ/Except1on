package org.springrain.sinova.interfaces.sitech.sGetAction;

import java.util.List;

/**
 * 
 */
public class MarkingActionInfo {
	private String actID;	//活动编码
	private String actName;///活动名称
	private String mktDiction;//活动描述
	private String custGroupID;//目标客户群编码
	private String custGroupName;//目标客户群名称
	private String startDate;//活动开始时间
	private String endDate;//活动结束时间
	private String areaCode;//区县编码
	private String areaName;//区县名称
	private String actClass;//活动类型
	private String actClassName;//活动类型名称
	
	private List<TermiInfo> termiList;
	
	public List<TermiInfo> getTermiList() {
		return termiList;
	}
	public void setTermiList(List<TermiInfo> termiList) {
		this.termiList = termiList;
	}
	public String getActID() {
		return actID;
	}
	public void setActID(String actID) {
		this.actID = actID;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getMktDiction() {
		return mktDiction;
	}
	public void setMktDiction(String mktDiction) {
		this.mktDiction = mktDiction;
	}
	public String getCustGroupID() {
		return custGroupID;
	}
	public void setCustGroupID(String custGroupID) {
		this.custGroupID = custGroupID;
	}
	public String getCustGroupName() {
		return custGroupName;
	}
	public void setCustGroupName(String custGroupName) {
		this.custGroupName = custGroupName;
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	
	
}
