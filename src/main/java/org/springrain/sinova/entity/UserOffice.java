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
 * @version  2015-01-09 09:16:10
 * @see org.springrain.sinova.entity.UserOffice
 */
@Table(name = "T_USER_OFFICE")
public class UserOffice extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户与营业厅关系";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_REGION_CODE = "地市编号";
	public static final String ALIAS_COUNTY_CODE = "区县编号";
	public static final String ALIAS_OFFICE_CODE = "营业厅编号";
    */
	
	//date formats
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 用户ID
	 */
	private java.lang.String userId;
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
	 * 岗位编码，多个岗位用逗号分隔
	 */
	private String post;
	//columns END 数据库字段结束
	
	//concstructor

	public UserOffice(){
	}

	public UserOffice(
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
    @WhereSQL(sql = "id=:UserOffice_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setUserId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.userId = value;
	}
	
    @WhereSQL(sql = "userId=:UserOffice_userId", column = "USER_ID")
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setRegionCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.regionCode = value;
	}
	
    @WhereSQL(sql = "regionCode=:UserOffice_regionCode", column = "REGION_CODE")
	public java.lang.String getRegionCode() {
		return this.regionCode;
	}
	
	public void setCountyCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.countyCode = value;
	}
	
    @WhereSQL(sql = "countyCode=:UserOffice_countyCode", column = "COUNTY_CODE")
	public java.lang.String getCountyCode() {
		return this.countyCode;
	}
	
	public void setOfficeCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.officeCode = value;
	}
	
    @WhereSQL(sql = "officeCode=:UserOffice_officeCode", column = "OFFICE_CODE")
	public java.lang.String getOfficeCode() {
		return this.officeCode;
	}
    
	/**
	 * @return the post
	 */
    @WhereSQL(sql = "post=:UserOffice_post", column = "POST")
	public String getPost() {
		return this.post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("用户ID[").append(getUserId()).append("],")
			.append("地市编号[").append(getRegionCode()).append("],")
			.append("区县编号[").append(getCountyCode()).append("],")
			.append("营业厅编号[").append(getOfficeCode()).append("],")
			.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserOffice == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserOffice other = (UserOffice)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
