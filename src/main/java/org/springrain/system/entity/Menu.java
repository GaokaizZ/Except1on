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
 * @see org.springrain.system.entity.Menu
 */
@Table(name="t_menu")
public class Menu  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "菜单";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_PID = "父编码";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_PAGEURL = "URL地址";
	public static final String ALIAS_TYPE = "0.功能按钮,1.导航菜单";
	public static final String ALIAS_STATE = "是否有效（0否/1是）";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_ICON = "图标";
	public static final String ALIAS_REMARK = "备注";
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
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 父编码
	 */
	private java.lang.String pid;
	/**
	 * 描述
	 */
	private java.lang.String description;
	/**
	 * URL地址
	 */
	private java.lang.String pageurl;
	/**
	 * 0.功能按钮,1.导航菜单
	 */
	private java.lang.Long type;
	/**
	 * 是否有效（0否/1是）
	 */
	private java.lang.String state;
	/**
	 * 排序
	 */
	private java.lang.Long sort;
	/**
	 * 图标
	 */
	private java.lang.String icon;
	/**
	 * 备注
	 */
	private java.lang.String remark;
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
	private String pidName;
	@Transient
	public String getPidName() {
		return pidName;
	}

	public void setPidName(String pidName) {
		this.pidName = pidName;
	}

	//
	private List<Menu> leaf;
	
	//concstructor

	public Menu(){
	}

	public Menu(
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
	@WhereSQL(sql = "id=:Menu_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}

	public void setName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.name = value;
	}

	@WhereSQL(sql = "name=:Menu_name", column = "NAME")
	public java.lang.String getName() {
		return this.name;
	}

	public void setPid(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.pid = value;
	}

	@WhereSQL(sql = "pid=:Menu_pid", column = "PID")
	public java.lang.String getPid() {
		return this.pid;
	}

	public void setDescription(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.description = value;
	}

	@WhereSQL(sql = "description=:Menu_description", column = "DESCRIPTION")
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setPageurl(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.pageurl = value;
	}

	@WhereSQL(sql = "pageurl=:Menu_pageurl", column = "PAGEURL")
	public java.lang.String getPageurl() {
		return this.pageurl;
	}
	
	public void setType(java.lang.Long value) {
		this.type = value;
	}
	
    @WhereSQL(sql = "type=:Menu_type", column = "TYPE")
	public java.lang.Long getType() {
		return this.type;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Menu_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setSort(java.lang.Long value) {
		this.sort = value;
	}
	
    @WhereSQL(sql = "sort=:Menu_sort", column = "SORT")
	public java.lang.Long getSort() {
		return this.sort;
	}
	
	public void setIcon(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.icon = value;
	}
	
    @WhereSQL(sql = "icon=:Menu_icon", column = "ICON")
	public java.lang.String getIcon() {
		return this.icon;
	}
	
	public void setRemark(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.remark = value;
	}
	
    @WhereSQL(sql = "remark=:Menu_remark", column = "REMARK")
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setCreateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.createUser = value;
	}
	
    @WhereSQL(sql = "createUser=:Menu_createUser", column = "CREATE_USER")
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
	
    @WhereSQL(sql = "createDate=:Menu_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setUpdateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.updateUser = value;
	}
	
    @WhereSQL(sql = "updateUser=:Menu_updateUser", column = "UPDATE_USER")
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
	
    @WhereSQL(sql = "updateDate=:Menu_updateDate", column = "UPDATE_DATE")
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("父编码[").append(getPid()).append("],")
			.append("描述[").append(getDescription()).append("],")
			.append("URL地址[").append(getPageurl()).append("],")
			.append("0.功能按钮,1.导航菜单[").append(getType()).append("],")
			.append("是否有效（0否/1是）[").append(getState()).append("],")
			.append("排序[").append(getSort()).append("],")
			.append("图标[").append(getIcon()).append("],")
			.append("备注[").append(getRemark()).append("],")
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
		if (obj instanceof Menu == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Menu other = (Menu)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Transient
	public List<Menu> getLeaf() {
		return leaf;
	}

	public void setLeaf(List<Menu> leaf) {
		this.leaf = leaf;
	}
}

	
