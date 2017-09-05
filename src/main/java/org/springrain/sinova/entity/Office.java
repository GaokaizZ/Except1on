package org.springrain.sinova.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:01
 * @see org.springrain.sinova.entity.Office
 */
@Table(name = "T_OFFICE")
public class Office extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "营业厅表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_REGION_CODE = "地市编号";
	public static final String ALIAS_COUNTY_CODE = "区县编号";
	public static final String ALIAS_OFFICE_CODE = "营业厅编号";
	public static final String ALIAS_OFFICE_NAME = "营业厅名称";
	public static final String ALIAS_ADDRESS = "营业厅地址";
	public static final String ALIAS_STATE = "是否有效（1有效、0无效）";
    */
	
	//date formats
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 地市编号
	 */
	private java.lang.String regionCode;
	/**
	 * 区县编号
	 */
	private java.lang.String countyCode;
	/**
	 * 营业厅编号
	 */
	private java.lang.String officeCode;
	/**
	 * 营业厅名称
	 */
	private java.lang.String officeName;
	/**
	 * 营业厅地址
	 */
	private java.lang.String address;
	/**
	 * 是否有效（1有效、0无效）
	 */
	private java.lang.String state;
	//columns END 数据库字段结束
	
	//concstructor

	public Office(){
	}

	public Office(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	
	public void setId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.id = value;
	}
	
	@Id
    @WhereSQL(sql = "id=:Office_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setRegionCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.regionCode = value;
	}
	
    @WhereSQL(sql = "regionCode=:Office_regionCode", column = "REGION_CODE")
	public java.lang.String getRegionCode() {
		return this.regionCode;
	}
	
	public void setCountyCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.countyCode = value;
	}
	
    @WhereSQL(sql = "countyCode=:Office_countyCode", column = "COUNTY_CODE")
	public java.lang.String getCountyCode() {
		return this.countyCode;
	}
	
	public void setOfficeCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.officeCode = value;
	}
	
    @WhereSQL(sql = "officeCode=:Office_officeCode", column = "OFFICE_CODE")
	public java.lang.String getOfficeCode() {
		return this.officeCode;
	}
	
	public void setOfficeName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.officeName = value;
	}
	
    @WhereSQL(sql = "officeName=:Office_officeName", column = "OFFICE_NAME")
	public java.lang.String getOfficeName() {
		return this.officeName;
	}
	
	public void setAddress(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.address = value;
	}
	
    @WhereSQL(sql = "address=:Office_address", column = "ADDRESS")
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Office_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("地市编号[").append(getRegionCode()).append("],")
			.append("区县编号[").append(getCountyCode()).append("],")
			.append("营业厅编号[").append(getOfficeCode()).append("],")
			.append("营业厅名称[").append(getOfficeName()).append("],")
			.append("营业厅地址[").append(getAddress()).append("],")
			.append("是否有效（1有效、0无效）[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Office == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Office other = (Office)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
