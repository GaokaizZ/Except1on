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
 * @version  2015-01-09 09:15:03
 * @see org.springrain.sinova.entity.UserBusiness
 */
@Table(name = "T_USER_BUSINESS")
public class UserBusiness extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户与业务关系";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_BUS_ID = "业务ID";
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
	 * 业务ID
	 */
	private java.lang.String busId;
	//columns END 数据库字段结束
	
	//concstructor

	public UserBusiness(){
	}

	public UserBusiness(
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
    @WhereSQL(sql = "id=:UserBusiness_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setUserId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.userId = value;
	}
	
    @WhereSQL(sql = "userId=:UserBusiness_userId", column = "USER_ID")
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setBusId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busId = value;
	}
	
    @WhereSQL(sql = "busId=:UserBusiness_busId", column = "BUS_ID")
	public java.lang.String getBusId() {
		return this.busId;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("用户ID[").append(getUserId()).append("],")
			.append("业务ID[").append(getBusId()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof UserBusiness == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserBusiness other = (UserBusiness)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
