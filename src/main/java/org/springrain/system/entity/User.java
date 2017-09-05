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
 * @version  2013-07-06 16:03:00
 * @see org.springrain.system.entity.User
 */
@Table(name="t_user")
public class User  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户";
	public static final String ALIAS_ID = "主键ID";
	public static final String ALIAS_NAME = "姓名";
	public static final String ALIAS_ACCOUNT = "账号";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_WORKNO = "工号";
	public static final String ALIAS_CARDNO = "身份证";
	public static final String ALIAS_AGE = "年龄";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_PHONE = "电话号码";
	public static final String ALIAS_MOBILE = "手机号码";
	public static final String ALIAS_EMAIL = "邮箱";
	public static final String ALIAS_ADDRESS = "地址";
	public static final String ALIAS_GRADE_ID = "级别";
	public static final String ALIAS_EDU_NAME = "学历";
	public static final String ALIAS_FIRE_NAME = "紧急联系人";
	public static final String ALIAS_FIRE_PHONE = "紧急联系电话";
	public static final String ALIAS_DESCRIPTION = "备注";
	public static final String ALIAS_WEIXIN_ID = "微信Id";
	public static final String ALIAS_STATE = "是否有效,1是/0否/离职";
	public static final String ALIAS_POST_CODE = "邮政编码";
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
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 账号
	 */
	private java.lang.String account;
	/**
	 * 密码
	 */
	private java.lang.String password;
	/**
	 * 工牌号 
	 */
	private java.lang.String workno;
	/**
	 * 身份证
	 */
	private java.lang.String cardno;
	/**
	 * 年龄
	 */
	private java.lang.Long age;
	/**
	 * 性别
	 */
	private java.lang.String sex;
	/**
	 * 电话号码
	 */
	private java.lang.String phone;
	/**
	 * 手机号码
	 */
	private java.lang.String mobile;
	/**
	 * 邮箱
	 */
	private java.lang.String email;
	/**
	 * 地址
	 */
	private java.lang.String address;
	/**
	 * 级别
	 */
	private java.lang.String gradeId;
	/**
	 * 学历
	 */
	private java.lang.String eduName;

	/**
	 * 紧急联系人
	 */
	private java.lang.String fireName;
	/**
	 * 紧急联系电话
	 */
	private java.lang.String firePhone;
	/**
	 * 备注
	 */
	private java.lang.String description;
	/**
	 * 微信Id
	 */
	private java.lang.String weixinId;
	/**
	 * 是否有效,1是/0否/离职
	 */
	private java.lang.String state;
	/**
	 * 邮政编码
	 */
	private java.lang.String postCode;
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
	


	//用户所有的组织
	private List<Org> userOrgs;
	//用户的所有角色
	private List<Role> userRoles;
	
	
	

	

	//concstructor
	public User(){
	}
	
	public User(String account,String password){
		this.account = account;
		this.password = password;
	}

	public User(
		java.lang.String id
	){
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
	@WhereSQL(sql = "id=:User_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}

	public void setName(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.name = value;
	}

	@WhereSQL(sql = "name=:User_name", column = "NAME")
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setAccount(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.account = value;
	}

	@WhereSQL(sql = "account=:User_account", column = "ACCOUNT")
	public java.lang.String getAccount() {
		return this.account;
	}

	public void setPassword(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.password = value;
	}

	@WhereSQL(sql = "password=:User_password", column = "PASSWORD")
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setWorkno(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.workno = value;
	}
	
    @WhereSQL(sql = "workno=:User_workno", column = "WORKNO")
	public java.lang.String getWorkno() {
		return this.workno;
	}
	
	public void setCardno(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.cardno = value;
	}

	@WhereSQL(sql = "cardno=:User_cardno", column = "CARDNO")
	public java.lang.String getCardno() {
		return this.cardno;
	}
	
	public void setAge(java.lang.Long value) {
		this.age = value;
	}
	
    @WhereSQL(sql = "age=:User_age", column = "AGE")
	public java.lang.Long getAge() {
		return this.age;
	}

	public void setSex(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sex = value;
	}

	@WhereSQL(sql = "sex=:User_sex", column = "SEX")
	public java.lang.String getSex() {
		return this.sex;
	}

	public void setPhone(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.phone = value;
	}

	@WhereSQL(sql = "phone=:User_phone", column = "PHONE")
	public java.lang.String getPhone() {
		return this.phone;
	}

	public void setMobile(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.mobile = value;
	}

	@WhereSQL(sql = "mobile=:User_mobile", column = "MOBILE")
	public java.lang.String getMobile() {
		return this.mobile;
	}

	public void setEmail(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.email = value;
	}
	
    @WhereSQL(sql = "email=:User_email", column = "EMAIL")
	public java.lang.String getEmail() {
		return this.email;
	}

	public void setAddress(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.address = value;
	}

	@WhereSQL(sql = "address=:User_address", column = "ADDRESS")
	public java.lang.String getAddress() {
		return this.address;
	}

	public void setGradeId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.gradeId = value;
	}

	@WhereSQL(sql = "gradeId=:User_gradeId", column = "GRADE_ID")
	public java.lang.String getGradeId() {
		return this.gradeId;
	}
	
	public void setEduName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.eduName = value;
	}
	
    @WhereSQL(sql = "eduName=:User_eduName", column = "EDU_NAME")
	public java.lang.String getEduName() {
		return this.eduName;
	}

	public void setFireName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.fireName = value;
	}

	@WhereSQL(sql = "fireName=:User_fireName", column = "FIRE_NAME")
	public java.lang.String getFireName() {
		return this.fireName;
	}

	public void setFirePhone(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.firePhone = value;
	}

	@WhereSQL(sql = "firePhone=:User_firePhone", column = "FIRE_PHONE")
	public java.lang.String getFirePhone() {
		return this.firePhone;
	}

	public void setDescription(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.description = value;
	}

	@WhereSQL(sql = "description=:User_description", column = "DESCRIPTION")
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setWeixinId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.weixinId = value;
	}
	
    @WhereSQL(sql = "weixinId=:User_weixinId", column = "WEIXIN_ID")
	public java.lang.String getWeixinId() {
		return this.weixinId;
	}
	
	public void setState(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.state = value;
	}

	@WhereSQL(sql = "state=:User_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setPostCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.postCode = value;
	}
	
    @WhereSQL(sql = "postCode=:User_postCode", column = "POST_CODE")
	public java.lang.String getPostCode() {
		return this.postCode;
	}
	
	public void setCreateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.createUser = value;
	}
	
    @WhereSQL(sql = "createUser=:User_createUser", column = "CREATE_USER")
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
	
    @WhereSQL(sql = "createDate=:User_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setUpdateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.updateUser = value;
	}
	
    @WhereSQL(sql = "updateUser=:User_updateUser", column = "UPDATE_USER")
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
	
    @WhereSQL(sql = "updateDate=:User_updateDate", column = "UPDATE_DATE")
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键ID[").append(getId()).append("],")
			.append("姓名[").append(getName()).append("],")
			.append("账号[").append(getAccount()).append("],")
			.append("密码[").append(getPassword()).append("],")
			.append("工号[").append(getWorkno()).append("],")
			.append("身份证[").append(getCardno()).append("],")
			.append("年龄[").append(getAge()).append("],")
			.append("性别[").append(getSex()).append("],")
			.append("电话号码[").append(getPhone()).append("],")
			.append("手机号码[").append(getMobile()).append("],")
			.append("邮箱[").append(getEmail()).append("],")
			.append("地址[").append(getAddress()).append("],")
			.append("级别[").append(getGradeId()).append("],")
			.append("学历[").append(getEduName()).append("],")
			.append("紧急联系人[").append(getFireName()).append("],")
			.append("紧急联系电话[").append(getFirePhone()).append("],")
			.append("备注[").append(getDescription()).append("],")
			.append("微信Id[").append(getWeixinId()).append("],")
			.append("是否有效,1是/0否/离职[").append(getState()).append("],")
			.append("邮政编码[").append(getPostCode()).append("],")
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
		if (obj instanceof User == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Transient
	public List<Org> getUserOrgs() {
		return userOrgs;
	}

	public void setUserOrgs(List<Org> userOrgs) {
		this.userOrgs = userOrgs;
	}

	@Transient
	public List<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
	}

}

	
