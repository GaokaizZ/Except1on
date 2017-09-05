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
 * @version  2015-06-23 16:24:02
 * @see org.springrain.sinova.entity.VisitRecord
 */
@Table(name = "T_VISIT_RECORD")
public class VisitRecord extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "VisitRecord";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_MOBILE = "mobile";
	public static final String ALIAS_QRCODE_ID = "qrcodeId";
	public static final String ALIAS_QRCODE_TYPE = "qrcodeType";
	public static final String ALIAS_VISIT_TIME = "visitTime";
	public static final String ALIAS_SP1 = "sp1";
	public static final String ALIAS_SP2 = "sp2";
	public static final String ALIAS_SP3 = "sp3";
    */
	
	//date formats
	//public static final String FORMAT_VISIT_TIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * mobile
	 */
	private java.lang.String mobile;
	/**
	 * qrcodeId
	 */
	private java.lang.String qrcodeId;
	/**
	 * qrcodeType
	 */
	private java.lang.String qrcodeType;
	/**
	 * visitTime
	 */
	private java.util.Date visitTime;
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

	public VisitRecord(){
	}

	public VisitRecord(
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
    @WhereSQL(sql = "id=:VisitRecord_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setMobile(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.mobile = value;
	}
	
    @WhereSQL(sql = "mobile=:VisitRecord_mobile", column = "MOBILE")
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setQrcodeId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.qrcodeId = value;
	}
	
    @WhereSQL(sql = "qrcodeId=:VisitRecord_qrcodeId", column = "QRCODE_ID")
	public java.lang.String getQrcodeId() {
		return this.qrcodeId;
	}
	
	public void setQrcodeType(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.qrcodeType = value;
	}
	
    @WhereSQL(sql = "qrcodeType=:VisitRecord_qrcodeType", column = "QRCODE_TYPE")
	public java.lang.String getQrcodeType() {
		return this.qrcodeType;
	}
	/*
	public String getvisitTimeString() {
		return DateUtils.convertDate2String(FORMAT_VISIT_TIME, getvisitTime());
	}
	public void setvisitTimeString(String value) throws ParseException{
		setvisitTime(DateUtils.convertString2Date(FORMAT_VISIT_TIME,value));
	}
	*/
	
	public void setVisitTime(java.util.Date value) {
		this.visitTime = value;
	}
	
    @WhereSQL(sql = "visitTime=:VisitRecord_visitTime", column = "VISIT_TIME")
	public java.util.Date getVisitTime() {
		return this.visitTime;
	}
	
	public void setSp1(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sp1 = value;
	}
	
    @WhereSQL(sql = "sp1=:VisitRecord_sp1", column = "SP1")
	public java.lang.String getSp1() {
		return this.sp1;
	}
	
	public void setSp2(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sp2 = value;
	}
	
    @WhereSQL(sql = "sp2=:VisitRecord_sp2", column = "SP2")
	public java.lang.String getSp2() {
		return this.sp2;
	}
	
	public void setSp3(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.sp3 = value;
	}
	
    @WhereSQL(sql = "sp3=:VisitRecord_sp3", column = "SP3")
	public java.lang.String getSp3() {
		return this.sp3;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("mobile[").append(getMobile()).append("],")
			.append("qrcodeId[").append(getQrcodeId()).append("],")
			.append("qrcodeType[").append(getQrcodeType()).append("],")
			.append("visitTime[").append(getVisitTime()).append("],")
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
		if (obj instanceof VisitRecord == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		VisitRecord other = (VisitRecord)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
