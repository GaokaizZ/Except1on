package org.springrain.sinova.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;

@Table(name = "T_ACCEPT_FAIL")
public class AcceptFail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 工号
	 */
	private java.lang.String workno;
	/**
	 * 客户手机号
	 */
	private java.lang.String mobile;
	/**
	 * 业务ID
	 */
	private java.lang.String busId;
	/**
	 * 资费代码
	 */
	private java.lang.String feeCode;

	/**
	 * 受理时间
	 */
	private java.util.Date datetime;
	
	/**
	 * 报文
	 * 
	 */
	private java.lang.String message;
	
	private java.lang.String spare1;
	private java.lang.String spare2;
	private java.lang.String spare3;
	private java.lang.String spare4;
	private java.lang.String spare5;
	
	//concstructor

	public AcceptFail(){
	}

	public AcceptFail(
		java.lang.String id
	){
		this.id = id;
	}
	@Id
    @WhereSQL(sql = "id=:AcceptFail_id", column = "ID")	
	public java.lang.String getId() {
		return this.id;
	}
	public void setId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.id = value;
	}
	 
	@WhereSQL(sql = "account=:AcceptFail_workno", column = "WORKNO")
	public java.lang.String getWorkno() {
		return this.workno;
	}
	public void setWorkno(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.workno = value;
	}
	
	
	@WhereSQL(sql = "mobile=:AcceptFail_mobile", column = "MOBILE")
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setMobile(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.mobile = value;
	}
	
	public void setBusId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busId = value;
	}
	
    @WhereSQL(sql = "busId=:AcceptFail_busId", column = "BUS_ID")
	public java.lang.String getBusId() {
		return this.busId;
	}
    
    public void setFeeCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.feeCode = value;
	}
	
    @WhereSQL(sql = "feeCode=:AcceptFail_feeCode", column = "FEE_CODE")
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}
    
    public void setDatetime(java.util.Date value) {
		this.datetime = value;
	}
	
    @WhereSQL(sql = "datetime=:AcceptFial_datetime", column = "DATETIME")
	public java.util.Date getDatetime() {
		return this.datetime;
	}
    
    @WhereSQL(sql = "remark=:AcceptFail_message", column = "MESSAGE")
	public java.lang.String getMessage() {
		return this.message;
	}
	public void setMessage(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.message = value;
	}
	@WhereSQL(sql = "spare1=:AcceptFail_spare1", column = "SPARE1")
	public java.lang.String getSpare1() {
		return this.spare1;
	}
	public void setSpare1(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare1 = value;
	}
	@WhereSQL(sql = "spare2=:AcceptFail_spare2", column = "SPARE2")
	public java.lang.String getSpare2() {
		return this.spare2;
	}
	public void setSpare2(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare2 = value;
	}
	@WhereSQL(sql = "spare3=:AcceptFail_spare3", column = "SPARE3")
	public java.lang.String getSpare3() {
		return this.spare3;
	}
	public void setSpare3(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare3 = value;
	}
	@WhereSQL(sql = "spare4=:AcceptFail_spare4", column = "SPARE4")
	public java.lang.String getSpare4() {
		return this.spare4;
	}
	public void setSpare4(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare4 = value;
	}
	@WhereSQL(sql = "spare5=:AcceptFail_spare5", column = "SPARE5")
	public java.lang.String getSpare5() {
		return this.spare5;
	}
	public void setSpare5(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare5 = value;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("工号[").append(getWorkno()).append("],")
			.append("客户手机号[").append(getMobile()).append("],")
			.append("业务ID[").append(getBusId()).append("],")
			.append("资费代码[").append(getFeeCode()).append("],")
			.append("受理时间[").append(getDatetime()).append("],")
			.append("报文错误信息[").append(getMessage()).append("],")
			.append("备用1[").append(getSpare1()).append("],")
			.append("备用2[").append(getSpare2()).append("],")
			.append("备用3[").append(getSpare3()).append("],")
			.append("备用4[").append(getSpare4()).append("],")
			.append("备用5[").append(getSpare5()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof AcceptFail == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		AcceptFail other = (AcceptFail)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
	

}
