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
 * @version  2015-05-05 10:37:53
 * @see org.springrain.sinova.entity.AdImage
 */
@Table(name = "T_AD_IMAGE")
public class AdImage extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "AdImage";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_TYPE = "type";
	public static final String ALIAS_OWNER = "owner";
	public static final String ALIAS_IMAGE = "image";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_RE_MARK = "reMark";
	public static final String ALIAS_SP1 = "sp1";
	public static final String ALIAS_SP2 = "sp2";
	public static final String ALIAS_SP3 = "sp3";
    */
	
	//date formats
	//public static final String FORMAT_CREATE_TIME = DateUtils.DATETIME_FORMAT;
	
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
	 * type
	 */
	private java.lang.String type;
	/**
	 * owner
	 */
	private java.lang.String owner;
	/**
	 * image
	 */
	private java.lang.String image;
	/**
	 * createTime
	 */
	private java.util.Date createTime;
	/**
	 * reMark
	 */
	private java.lang.String reMark;
	/**
	 * sp1
	 */
	private java.lang.String sp1;
	/**
	 * sp2
	 */
	private java.lang.String sp2;
	/**
	 * sp3
	 */
	private java.lang.String sp3;
	//columns END 数据库字段结束
	
	//concstructor

	public AdImage(){
	}

	public AdImage(
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
    @WhereSQL(sql = "id=:AdImage_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.name = value;
	}
	
    @WhereSQL(sql = "name=:AdImage_name", column = "NAME")
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setType(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.type = value;
	}
	
    @WhereSQL(sql = "type=:AdImage_type", column = "TYPE")
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setOwner(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.owner = value;
	}
	
    @WhereSQL(sql = "owner=:AdImage_owner", column = "OWNER")
	public java.lang.String getOwner() {
		return this.owner;
	}
	
	public void setImage(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.image = value;
	}
	
    @WhereSQL(sql = "image=:AdImage_image", column = "IMAGE")
	public java.lang.String getImage() {
		return this.image;
	}
	/*
	public String getcreateTimeString() {
		return DateUtils.convertDate2String(FORMAT_CREATE_TIME, getcreateTime());
	}
	public void setcreateTimeString(String value) throws ParseException{
		setcreateTime(DateUtils.convertString2Date(FORMAT_CREATE_TIME,value));
	}
	*/
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
    @WhereSQL(sql = "createTime=:AdImage_createTime", column = "CREATE_TIME")
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setReMark(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.reMark = value;
	}
	
    @WhereSQL(sql = "reMark=:AdImage_reMark", column = "RE_MARK")
	public java.lang.String getReMark() {
		return this.reMark;
	}
	
	public void setSp1(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sp1 = value;
	}
	
    @WhereSQL(sql = "sp1=:AdImage_sp1", column = "SP1")
	public java.lang.String getSp1() {
		return this.sp1;
	}
	
	public void setSp2(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sp2 = value;
	}
	
    @WhereSQL(sql = "sp2=:AdImage_sp2", column = "SP2")
	public java.lang.String getSp2() {
		return this.sp2;
	}
	
	public void setSp3(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sp3 = value;
	}
	
    @WhereSQL(sql = "sp3=:AdImage_sp3", column = "SP3")
	public java.lang.String getSp3() {
		return this.sp3;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("name[").append(getName()).append("],")
			.append("type[").append(getType()).append("],")
			.append("owner[").append(getOwner()).append("],")
			.append("image[").append(getImage()).append("],")
			.append("createTime[").append(getCreateTime()).append("],")
			.append("reMark[").append(getReMark()).append("],")
			.append("sp1[").append(getSp1()).append("],")
			.append("sp2[").append(getSp2()).append("],")
			.append("sp3[").append(getSp3()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof AdImage == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		AdImage other = (AdImage)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
