package org.springrain.sinova.entity;

import org.springrain.frame.util.DateUtils;
import java.text.ParseException;
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
 * @version  2015-04-20 11:59:16
 * @see org.springrain.sinova.entity.Holder
 */
@Table(name = "T_HOLDER")
public class Holder extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Holder";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_TOKEN = "token";
	public static final String ALIAS_CHANNEL = "channel";
	public static final String ALIAS_SOURCE_NO = "sourceNo";
	public static final String ALIAS_WORK_NO = "workNo";
	public static final String ALIAS_SOURCE_IP = "sourceIp";
	public static final String ALIAS_CREATE_DATE = "createDate";
	public static final String ALIAS_LIMIT = "limit";
	public static final String ALIAS_STATE = "state";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_SPARE = "spare";
	public static final String ALIAS_FINDER = "finder";
	public static final String ALIAS_FINDER_PHONE = "finderPhone";
    */
	
	//date formats
	//public static final String FORMAT_CREATE_DATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * name
	 */
	private java.lang.String name;
	/**
	 * token
	 */
	private java.lang.String token;
	/**
	 * channel
	 */
	private java.lang.String channel;
	/**
	 * sourceNo
	 */
	private java.lang.String sourceNo;
	/**
	 * workNo
	 */
	private java.lang.String workNo;
	/**
	 * sourceIp
	 */
	private java.lang.String sourceIp;
	/**
	 * createDate
	 */
	private java.util.Date createDate;
	/**
	 * limit
	 */
	private java.lang.String limit;
	/**
	 * state
	 */
	private java.lang.String state;
	/**
	 * remark
	 */
	private java.lang.String remark;
	/**
	 * spare
	 */
	private java.lang.String spare;
	/**
	 * finder
	 */
	private java.lang.String finder;
	/**
	 * finderPhone
	 */
	private java.lang.String finderPhone;
	//columns END 数据库字段结束
	
	//concstructor

	public Holder(){
	}

	public Holder(
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
    @WhereSQL(sql = "id=:Holder_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.name = value;
	}
	
    @WhereSQL(sql = "name=:Holder_name", column = "NAME")
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setToken(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.token = value;
	}
	
    @WhereSQL(sql = "token=:Holder_token", column = "TOKEN")
	public java.lang.String getToken() {
		return this.token;
	}
	
	public void setChannel(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.channel = value;
	}
	
    @WhereSQL(sql = "channel=:Holder_channel", column = "CHANNEL")
	public java.lang.String getChannel() {
		return this.channel;
	}
	
	public void setSourceNo(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sourceNo = value;
	}
	
    @WhereSQL(sql = "sourceNo=:Holder_sourceNo", column = "SOURCE_NO")
	public java.lang.String getSourceNo() {
		return this.sourceNo;
	}
	
	public void setWorkNo(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.workNo = value;
	}
	
    @WhereSQL(sql = "workNo=:Holder_workNo", column = "WORK_NO")
	public java.lang.String getWorkNo() {
		return this.workNo;
	}
	
	public void setSourceIp(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sourceIp = value;
	}
	
    @WhereSQL(sql = "sourceIp=:Holder_sourceIp", column = "SOURCE_IP")
	public java.lang.String getSourceIp() {
		return this.sourceIp;
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
	
    @WhereSQL(sql = "createDate=:Holder_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setLimit(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.limit = value;
	}
	
    @WhereSQL(sql = "limit=:Holder_limit", column = "LIMIT")
	public java.lang.String getLimit() {
		return this.limit;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Holder_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setRemark(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.remark = value;
	}
	
    @WhereSQL(sql = "remark=:Holder_remark", column = "REMARK")
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setSpare(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare = value;
	}
	
    @WhereSQL(sql = "spare=:Holder_spare", column = "SPARE")
	public java.lang.String getSpare() {
		return this.spare;
	}
	
	public void setFinder(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.finder = value;
	}
	
    @WhereSQL(sql = "finder=:Holder_finder", column = "FINDER")
	public java.lang.String getFinder() {
		return this.finder;
	}
	
	public void setFinderPhone(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.finderPhone = value;
	}
	
    @WhereSQL(sql = "finderPhone=:Holder_finderPhone", column = "FINDER_PHONE")
	public java.lang.String getFinderPhone() {
		return this.finderPhone;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("name[").append(getName()).append("],")
			.append("token[").append(getToken()).append("],")
			.append("channel[").append(getChannel()).append("],")
			.append("sourceNo[").append(getSourceNo()).append("],")
			.append("workNo[").append(getWorkNo()).append("],")
			.append("sourceIp[").append(getSourceIp()).append("],")
			.append("createDate[").append(getCreateDate()).append("],")
			.append("limit[").append(getLimit()).append("],")
			.append("state[").append(getState()).append("],")
			.append("remark[").append(getRemark()).append("],")
			.append("spare[").append(getSpare()).append("],")
			.append("finder[").append(getFinder()).append("],")
			.append("finderPhone[").append(getFinderPhone()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Holder == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Holder other = (Holder)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
