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
 * @version  2015-01-09 09:12:19
 * @see org.springrain.sinova.entity.Region
 */
@Table(name = "T_REGION")
public class Region extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "地市表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_REGION_CODE = "地市编号";
	public static final String ALIAS_REGION_NAME = "地市名称";
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
	 * 地市名称
	 */
	private java.lang.String regionName;
	/**
	 * 是否有效（1有效、0无效）
	 */
	private java.lang.String state;
	//columns END 数据库字段结束
	
	//concstructor

	public Region(){
	}

	public Region(
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
    @WhereSQL(sql = "id=:Region_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setRegionCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.regionCode = value;
	}
	
    @WhereSQL(sql = "regionCode=:Region_regionCode", column = "REGION_CODE")
	public java.lang.String getRegionCode() {
		return this.regionCode;
	}
	
	public void setRegionName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.regionName = value;
	}
	
    @WhereSQL(sql = "regionName=:Region_regionName", column = "REGION_NAME")
	public java.lang.String getRegionName() {
		return this.regionName;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Region_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("地市编号[").append(getRegionCode()).append("],")
			.append("地市名称[").append(getRegionName()).append("],")
			.append("是否有效（1有效、0无效）[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Region == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Region other = (Region)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
