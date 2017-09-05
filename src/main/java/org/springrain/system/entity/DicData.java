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
 * @version  2013-08-02 16:32:29
 * @see org.springrain.system.entity.DicData
 */
@Table(name="t_dic_data")
public class DicData  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "数据字典";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_CODE = "编码";
	public static final String ALIAS_PID = "父ID";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_REMARK = "描述";
	public static final String ALIAS_STATE = "是否有效（0否/1是）";
	public static final String ALIAS_TYPEKEY = "类型";
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
	 * id
	 */
	private java.lang.String id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 编码
	 */
	private java.lang.String code;
	/**
	 * 父ID
	 */
	private java.lang.String pid;
	/**
	 * 排序
	 */
	private java.lang.Long sort;
	/**
	 * 描述
	 */
	private java.lang.String remark;
	/**
	 * 是否有效（0否/1是）
	 */
	private java.lang.String state;
	/**
	 * 类型
	 */
	private java.lang.String typekey;
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
	
	//concstructor

	public DicData(){
	}

	public DicData(
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
	@WhereSQL(sql = "id=:DicData_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}

	public void setName(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.name = value;
	}

	@WhereSQL(sql = "name=:DicData_name", column = "NAME")
	public java.lang.String getName() {
		return this.name;
	}

	public void setCode(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.code = value;
	}
	
    @WhereSQL(sql = "code=:DicData_code", column = "CODE")
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setPid(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.pid = value;
	}
	
    @WhereSQL(sql = "pid=:DicData_pid", column = "PID")
	public java.lang.String getPid() {
		return this.pid;
	}
	
	public void setSort(java.lang.Long value) {
		this.sort = value;
	}
	
    @WhereSQL(sql = "sort=:DicData_sort", column = "SORT")
	public java.lang.Long getSort() {
		return this.sort;
	}

	public void setRemark(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.remark = value;
	}

	@WhereSQL(sql = "remark=:DicData_remark", column = "REMARK")
	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setState(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.state = value;
	}

	@WhereSQL(sql = "state=:DicData_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}

	public void setTypekey(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.typekey = value;
	}

	@WhereSQL(sql = "typekey=:DicData_typekey", column = "TYPEKEY")
	public java.lang.String getTypekey() {
		return this.typekey;
	}
	
	public void setCreateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.createUser = value;
	}
	
    @WhereSQL(sql = "createUser=:DicData_createUser", column = "CREATE_USER")
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
	
    @WhereSQL(sql = "createDate=:DicData_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setUpdateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.updateUser = value;
	}
	
    @WhereSQL(sql = "updateUser=:DicData_updateUser", column = "UPDATE_USER")
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
	
    @WhereSQL(sql = "updateDate=:DicData_updateDate", column = "UPDATE_DATE")
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("编码[").append(getCode()).append("],")
			.append("父ID[").append(getPid()).append("],")
			.append("排序[").append(getSort()).append("],")
			.append("描述[").append(getRemark()).append("],")
			.append("是否有效（0否/1是）[").append(getState()).append("],")
			.append("类型[").append(getTypekey()).append("],")
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
		if (obj instanceof DicData == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		DicData other = (DicData)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
