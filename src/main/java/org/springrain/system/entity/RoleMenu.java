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
 * @version  2013-07-06 16:02:59
 * @see org.springrain.system.entity.RoleMenu
 */
@Table(name="t_role_menu")
public class RoleMenu  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "角色菜单关系";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_ROLE_ID = "角色编号";
	public static final String ALIAS_MENU_ID = "菜单编号";
	public static final String ALIAS_CREATE_USER = "创建人账号";
	public static final String ALIAS_CREATE_DATE = "创建时间";
    */
	
	//date formats
	//public static final String FORMAT_CREATE_DATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 角色编号
	 */
	private java.lang.String roleId;
	/**
	 * 菜单编号
	 */
	private java.lang.String menuId;
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

	public RoleMenu(){
	}

	public RoleMenu(
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
	@WhereSQL(sql = "id=:RoleMenu_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}

	public void setRoleId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.roleId = value;
	}

	@WhereSQL(sql = "roleId=:RoleMenu_roleId", column = "ROLE_ID")
	public java.lang.String getRoleId() {
		return this.roleId;
	}

	public void setMenuId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.menuId = value;
	}

	@WhereSQL(sql = "menuId=:RoleMenu_menuId", column = "MENU_ID")
	public java.lang.String getMenuId() {
		return this.menuId;
	}
	
	public void setCreateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.createUser = value;
	}
	
    @WhereSQL(sql = "createUser=:RoleMenu_createUser", column = "CREATE_USER")
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
	
    @WhereSQL(sql = "createDate=:RoleMenu_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("编号[").append(getId()).append("],")
			.append("角色编号[").append(getRoleId()).append("],")
			.append("菜单编号[").append(getMenuId()).append("],")
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
		if (obj instanceof RoleMenu == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RoleMenu other = (RoleMenu)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
