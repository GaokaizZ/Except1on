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
 * @version  2015-06-29 17:52:58
 * @see org.springrain.sinova.entity.AdMsg
 */
@Table(name = "T_AD_MSG")
public class AdMsg extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "AdMsg";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_KEY = "key";
	public static final String ALIAS_VALUE = "value";
	public static final String ALIAS_IS_PUB = "isPub";
	public static final String ALIAS_OWNER = "owner";
	public static final String ALIAS_MY_DESC = "myDesc";
    */
	
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * key
	 */
	private java.lang.String key;
	/**
	 * value
	 */
	private java.lang.String value;
	/**
	 * isPub
	 */
	private java.lang.String isPub;
	/**
	 * owner
	 */
	private java.lang.String owner;
	/**
	 * myDesc
	 */
	private java.lang.String myDesc;
	//columns END 数据库字段结束
	
	//concstructor

	public AdMsg(){
	}

	public AdMsg(
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
    @WhereSQL(sql = "id=:AdMsg_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setKey(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.key = value;
	}
	
    @WhereSQL(sql = "key=:AdMsg_key", column = "KEY")
	public java.lang.String getKey() {
		return this.key;
	}
	
	public void setValue(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.value = value;
	}
	
    @WhereSQL(sql = "value=:AdMsg_value", column = "VALUE")
	public java.lang.String getValue() {
		return this.value;
	}
	
	public void setIsPub(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.isPub = value;
	}
	
    @WhereSQL(sql = "isPub=:AdMsg_isPub", column = "IS_PUB")
	public java.lang.String getIsPub() {
		return this.isPub;
	}
	
	public void setOwner(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.owner = value;
	}
	
    @WhereSQL(sql = "owner=:AdMsg_owner", column = "OWNER")
	public java.lang.String getOwner() {
		return this.owner;
	}
	
	public void setMyDesc(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.myDesc = value;
	}
	
    @WhereSQL(sql = "myDesc=:AdMsg_myDesc", column = "MY_DESC")
	public java.lang.String getMyDesc() {
		return this.myDesc;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("key[").append(getKey()).append("],")
			.append("value[").append(getValue()).append("],")
			.append("isPub[").append(getIsPub()).append("],")
			.append("owner[").append(getOwner()).append("],")
			.append("myDesc[").append(getMyDesc()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof AdMsg == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		AdMsg other = (AdMsg)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
