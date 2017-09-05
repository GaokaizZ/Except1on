package org.springrain.system.entity;

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
 * @version  2013-07-06 16:03:00
 * @see org.springrain.system.entity.UserRole
 */
@Table(name="t_user_role")
public class UserRole  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户角色关系";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_ROLE_ID = "角色ID";
	public static final String ALIAS_CREATE_USER = "创建人账号";
	public static final String ALIAS_CREATE_DATE = "创建时间";
    */
	
	//date formats
	//public static final String FORMAT_CREATE_DATE = DateUtils.DATETIME_FORMAT;
	
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
	 * 角色ID
	 */
	private java.lang.String roleId;
	/**
	 * 创建人账号
	 */
	private java.lang.String createUser;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	//columns END 数据库字段结束
	
	//concstructor

	public UserRole(){
	}

	public UserRole(
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
    @WhereSQL(sql = "id=:UserRole_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setUserId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.userId = value;
	}
	
    @WhereSQL(sql = "userId=:UserRole_userId", column = "USER_ID")
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setRoleId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.roleId = value;
	}
	
    @WhereSQL(sql = "roleId=:UserRole_roleId", column = "ROLE_ID")
	public java.lang.String getRoleId() {
		return this.roleId;
	}
	
	public void setCreateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.createUser = value;
	}
	
    @WhereSQL(sql = "createUser=:UserRole_createUser", column = "CREATE_USER")
	public java.lang.String getCreateUser() {
		return this.createUser;
	}
	/*
	public String getcreateDateString() {
		return DateUtils.convertDate2String(FORMAT_CREATE_DATE, getcreateDate());
	}
	public void setcreateDateString(String value) throws ParseException{
		setcreateDate(DateUtils.convertString2Date(FORMAT_CREATE_DATE,value));
	}
	*/
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
    @WhereSQL(sql = "createDate=:UserRole_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("用户ID[").append(getUserId()).append("],")
			.append("角色ID[").append(getRoleId()).append("],")
			.append("创建人账号[").append(getCreateUser()).append("],")
			.append("创建时间[").append(getCreateDate()).append("],")
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
		if (obj instanceof UserRole == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserRole other = (UserRole)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
