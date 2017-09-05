package org.springrain.sinova.dto;

import org.springrain.sinova.entity.AcceptFail;

public class AcceptFailDTO extends  AcceptFail{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 受理开始时间 查询用
	 */
	private java.util.Date beginTime;
	
	/**
	 * 受理结束时间 查询用
	 */
	private java.util.Date endTime;
	
	/**
	 * 地市名称
	 */
	private java.lang.String regionName;
	
	/**
	 * 营业厅名称
	 */
	private java.lang.String officeName;
	
	/**
	 * 资费名称
	 */
	private java.lang.String feeName;

	public java.lang.String getFeeName() {
		return feeName;
	}

	public void setFeeName(java.lang.String feeName) {
		this.feeName = feeName;
	}

	public java.util.Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public java.lang.String getRegionName() {
		return regionName;
	}

	public void setRegionName(java.lang.String regionName) {
		this.regionName = regionName;
	}

	public java.lang.String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(java.lang.String officeName) {
		this.officeName = officeName;
	}
	
	

}
