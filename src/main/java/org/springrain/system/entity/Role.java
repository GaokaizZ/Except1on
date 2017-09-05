package org.springrain.system.entity;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2013-07-06 16:02:59
 * @see org.springrain.system.entity.Role
 */
@Table(name = "t_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// alias
	/*
	public static final String TABLE_ALIAS = "角色";
	public static final String ALIAS_ID = "角色主键";
	public static final String ALIAS_NAME = "角色名称";
	public static final String ALIAS_CODE = "角色编号";
	public static final String ALIAS_PID = "上级角色主键";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_STATE = "是否有效(0否/1是)";
	public static final String ALIAS_GRADE = "角色等级";
	public static final String ALIAS_ROLE_TYPE = "角色类型";
	public static final String ALIAS_CREATE_USER = "创建人账号";
	public static final String ALIAS_CREATE_DATE = "创建时间";
	public static final String ALIAS_UPDATE_USER = "修改人账号";
	public static final String ALIAS_UPDATE_DATE = "修改时间";
    */
	
	//date formats
	//public static final String FORMAT_CREATE_DATE = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_UPDATE_DATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * 角色主键
	 */
	private java.lang.String id;
	/**
	 * 角色名称
	 */
	private java.lang.String name;
	/**
	 * 角色编号
	 */
	private java.lang.String code;
	/**
	 * 上级角色主键
	 */
	private java.lang.String pid;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 是否有效(0否/1是)
	 */
	private java.lang.String state;
	/**
	 * 角色等级
	 */
	private java.lang.Long grade;
	/**
	 * 角色类型
	 */
	private java.lang.String roleType;
	/**
	 * 创建人账号
	 */
	private java.lang.String createUser;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 修改人账号
	 */
	private java.lang.String updateUser;
	/**
	 * 修改时间
	 */
	private java.util.Date updateDate;
	//columns END 数据库字段结束
	
	private List<Menu> menus;
	
	//concstructor

	public Role() {
	}

	public Role(java.lang.String id) {
		this.id = id;
	}

	// get and set
	public void setId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.id = value;
	}

	@Id
	@WhereSQL(sql = "id=:Role_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}

	public void setName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.name = value;
	}

	@WhereSQL(sql = "name=:Role_name", column = "NAME")
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.code = value;
	}
	
    @WhereSQL(sql = "code=:Role_code", column = "CODE")
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setPid(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.pid = value;
	}

	@WhereSQL(sql = "pid=:Role_pid", column = "PID")
	public java.lang.String getPid() {
		return this.pid;
	}

	public void setRemark(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.remark = value;
	}

	@WhereSQL(sql = "remark=:Role_remark", column = "REMARK")
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Role_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setGrade(java.lang.Long value) {
		this.grade = value;
	}
	
    @WhereSQL(sql = "grade=:Role_grade", column = "GRADE")
	public java.lang.Long getGrade() {
		return this.grade;
	}
	
	public void setRoleType(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.roleType = value;
	}
	
    @WhereSQL(sql = "roleType=:Role_roleType", column = "ROLE_TYPE")
	public java.lang.String getRoleType() {
		return this.roleType;
	}
	
	public void setCreateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.createUser = value;
	}
	
    @WhereSQL(sql = "createUser=:Role_createUser", column = "CREATE_USER")
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
	
    @WhereSQL(sql = "createDate=:Role_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setUpdateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.updateUser = value;
	}
	
    @WhereSQL(sql = "updateUser=:Role_updateUser", column = "UPDATE_USER")
	public java.lang.String getUpdateUser() {
		return this.updateUser;
	}
	/*
	public String getupdateDateString() {
		return DateUtils.convertDate2String(FORMAT_UPDATE_DATE, getupdateDate());
	}
	public void setupdateDateString(String value) throws ParseException{
		setupdateDate(DateUtils.convertString2Date(FORMAT_UPDATE_DATE,value));
	}
	*/
	
	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}
	
    @WhereSQL(sql = "updateDate=:Role_updateDate", column = "UPDATE_DATE")
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("角色主键[").append(getId()).append("],")
			.append("角色名称[").append(getName()).append("],")
			.append("角色编号[").append(getCode()).append("],")
			.append("上级角色主键[").append(getPid()).append("],")
			.append("备注[").append(getRemark()).append("],")
			.append("是否有效(0否/1是)[").append(getState()).append("],")
			.append("角色等级[").append(getGrade()).append("],")
			.append("角色类型[").append(getRoleType()).append("],")
			.append("创建人账号[").append(getCreateUser()).append("],")
			.append("创建时间[").append(getCreateDate()).append("],")
			.append("修改人账号[").append(getUpdateUser()).append("],")
			.append("修改时间[").append(getUpdateDate()).append("],")
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
		if (obj instanceof Role == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Role other = (Role)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}

	@Transient
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

}
