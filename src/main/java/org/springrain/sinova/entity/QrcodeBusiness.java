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
 * @version  2015-01-09 09:15:29
 * @see org.springrain.sinova.entity.QrcodeBusiness
 */
@Table(name = "T_QRCODE_BUSINESS")
public class QrcodeBusiness extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "二维码与业务关系";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_QRCODE_ID = "二维码ID";
	public static final String ALIAS_BUS_ID = "业务ID";
	public static final String ALIAS_FEE_CODE = "资费代码";
    */
	
	//date formats
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 二维码ID
	 */
	private java.lang.String qrcodeId;
	/**
	 * 业务ID
	 */
	private java.lang.String busId;
	/**
	 * 资费代码
	 */
	private java.lang.String feeCode;
	//columns END 数据库字段结束
	
	//concstructor

	public QrcodeBusiness(){
	}

	public QrcodeBusiness(
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
    @WhereSQL(sql = "id=:QrcodeBusiness_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setQrcodeId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.qrcodeId = value;
	}
	
    @WhereSQL(sql = "qrcodeId=:QrcodeBusiness_qrcodeId", column = "QRCODE_ID")
	public java.lang.String getQrcodeId() {
		return this.qrcodeId;
	}
	
	public void setBusId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busId = value;
	}
	
    @WhereSQL(sql = "busId=:QrcodeBusiness_busId", column = "BUS_ID")
	public java.lang.String getBusId() {
		return this.busId;
	}
	
	public void setFeeCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.feeCode = value;
	}
	
    @WhereSQL(sql = "feeCode=:QrcodeBusiness_feeCode", column = "FEE_CODE")
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("二维码ID[").append(getQrcodeId()).append("],")
			.append("业务ID[").append(getBusId()).append("],")
			.append("资费代码[").append(getFeeCode()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof QrcodeBusiness == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		QrcodeBusiness other = (QrcodeBusiness)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
