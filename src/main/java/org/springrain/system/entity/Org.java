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
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2013-07-06 16:02:58
 * @see org.springrain.system.entity.Org
 */
@Table(name="t_org")
public class Org  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "组织";
	public static final String ALIAS_ID = "主键ID";
	public static final String ALIAS_NAME = "组织名称";
	public static final String ALIAS_COMCODE = "组织代码";
	public static final String ALIAS_PID = "上级组织ID";
	public static final String ALIAS_SYSID = "子系统ID";
	public static final String ALIAS_TYPE = "0组织、1部门、2岗位";
	public static final String ALIAS_LEAF = "叶子节点(0:树枝节点;1:叶子节点)";
	public static final String ALIAS_SORTNO = "排序号";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_STATE = "是否有效(0否/1是)";
	public static final String ALIAS_ORG_GRADE = "组织等级";
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
	 * 主键ID
	 */
	private java.lang.String id;
	/**
	 * 组织名称
	 */
	private java.lang.String name;
	/**
	 * 组织代码
	 */
	private java.lang.String comcode;
	/**
	 * 上级组织ID
	 */
	private java.lang.String pid;
	/**
	 * 子系统ID
	 */
	private java.lang.String sysid;
	/**
	 * 0组织、1部门、2岗位
	 */
	private java.lang.Long type;
	/**
	 * 叶子节点(0:树枝节点;1:叶子节点)
	 */
	private java.lang.Long leaf;
	/**
	 * 排序号
	 */
	private java.lang.Long sortno;
	/**
	 * 描述
	 */
	private java.lang.String description;
	/**
	 * 是否有效(0否/1是)
	 */
	private java.lang.String state;
	/**
	 * 组织等级
	 */
	private java.lang.Long orgGrade;
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
	
	
	
	private List<Org> leafOrg;
	
	//concstructor

	public Org(){
	}

	public Org(
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
	@WhereSQL(sql = "id=:Org_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}

	public void setName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.name = value;
	}

	@WhereSQL(sql = "name=:Org_name", column = "NAME")
	public java.lang.String getName() {
		return this.name;
	}

	public void setComcode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.comcode = value;
	}

	@WhereSQL(sql = "comcode=:Org_comcode", column = "COMCODE")
	public java.lang.String getComcode() {
		return this.comcode;
	}

	public void setPid(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.pid = value;
	}

	@WhereSQL(sql = "pid=:Org_pid", column = "PID")
	public java.lang.String getPid() {
		return this.pid;
	}

	public void setSysid(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sysid = value;
	}

	@WhereSQL(sql = "sysid=:Org_sysid", column = "SYSID")
	public java.lang.String getSysid() {
		return this.sysid;
	}
	
	public void setType(java.lang.Long value) {
		this.type = value;
	}
	
    @WhereSQL(sql = "type=:Org_type", column = "TYPE")
	public java.lang.Long getType() {
		return this.type;
	}
	
	public void setLeaf(java.lang.Long value) {
		this.leaf = value;
	}
	
    @WhereSQL(sql = "leaf=:Org_leaf", column = "LEAF")
	public java.lang.Long getLeaf() {
		return this.leaf;
	}
	
	public void setSortno(java.lang.Long value) {
		this.sortno = value;
	}
	
    @WhereSQL(sql = "sortno=:Org_sortno", column = "SORTNO")
	public java.lang.Long getSortno() {
		return this.sortno;
	}

	public void setDescription(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.description = value;
	}

	@WhereSQL(sql = "description=:Org_description", column = "DESCRIPTION")
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Org_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setOrgGrade(java.lang.Long value) {
		this.orgGrade = value;
	}
	
    @WhereSQL(sql = "orgGrade=:Org_orgGrade", column = "ORG_GRADE")
	public java.lang.Long getOrgGrade() {
		return this.orgGrade;
	}
	
	public void setCreateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.createUser = value;
	}
	
    @WhereSQL(sql = "createUser=:Org_createUser", column = "CREATE_USER")
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
	
    @WhereSQL(sql = "createDate=:Org_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setUpdateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.updateUser = value;
	}
	
    @WhereSQL(sql = "updateUser=:Org_updateUser", column = "UPDATE_USER")
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
	
    @WhereSQL(sql = "updateDate=:Org_updateDate", column = "UPDATE_DATE")
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键ID[").append(getId()).append("],")
			.append("组织名称[").append(getName()).append("],")
			.append("组织代码[").append(getComcode()).append("],")
			.append("上级组织ID[").append(getPid()).append("],")
			.append("子系统ID[").append(getSysid()).append("],")
			.append("0组织、1部门、2岗位[").append(getType()).append("],")
			.append("叶子节点(0:树枝节点;1:叶子节点)[").append(getLeaf()).append("],")
			.append("排序号[").append(getSortno()).append("],")
			.append("描述[").append(getDescription()).append("],")
			.append("是否有效(0否/1是)[").append(getState()).append("],")
			.append("组织等级[").append(getOrgGrade()).append("],")
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
		if (obj instanceof Org == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Org other = (Org)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Transient
	public List<Org> getLeafOrg() {
		return leafOrg;
	}

	public void setLeafOrg(List<Org> leafOrg) {
		this.leafOrg = leafOrg;
	}
}

	
