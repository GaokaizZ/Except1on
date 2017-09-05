package org.springrain.sinova.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;

@Table(name = "T_SPECIAL_QRCODE")
public class SpecialQrcode extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 二维码名称
	 */
	private java.lang.String qrcodeName;
	
	/**
	 * 归属人
	 */
	private java.lang.String belongUser;
	
	/**
	 * 流水号
	 */
	private java.lang.String flowNo;
	
	/**
	 * 业务id
	 */
	private java.lang.String busId;
	
	/**
	 * 商品资费代码
	 */
	private java.lang.String feeCode;
	
	/**
	 * 是否绑定：1绑定，0未绑定
	 */
	private java.lang.String type;
	
	/**
	 * 备用字段1
	 */
	private java.lang.String spare1;
	
	private java.lang.String spare2;
	
	private java.lang.String spare3;
	
	private java.lang.String spare4;
	
	private java.lang.String spare5;
	
	public SpecialQrcode(){}
	
	public SpecialQrcode(java.lang.String id){
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
    @WhereSQL(sql = "id=:SpecialQrcode_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setQrcodeName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.qrcodeName = value;
	}
	
    @WhereSQL(sql = "qrcodeName=:SpecialQrcode_qrcodeName", column = "QRCODE_NAME")
	public java.lang.String getQrcodeName() {
		return this.qrcodeName;
	}

    @WhereSQL(sql = "belongUser=:SpecialQrcode_belongUser", column = "BELONG_USER")
	public java.lang.String getBelongUser() {
		return this.belongUser;
	}

	public void setBelongUser(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.belongUser = value;
	}

	@WhereSQL(sql = "flowNo=:SpecialQrcode_flowNo", column = "FLOW_NO")
	public java.lang.String getFlowNo() {
		return this.flowNo;
	}

	public void setFlowNo(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.flowNo = value;
	}

	@WhereSQL(sql = "busId=:SpecialQrcode_busId", column = "BUS_ID")
	public java.lang.String getBusId() {
		return this.busId;
	}

	public void setBusId(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.busId = value;
	}

	@WhereSQL(sql = "feeCode=:SpecialQrcode_feeCode", column = "FEE_CODE")
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}

	public void setFeeCode(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.feeCode = value;
	}

	@WhereSQL(sql = "type=:SpecialQrcode_type", column = "TYPE")
	public java.lang.String getType() {
		return this.type;
	}

	public void setType(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.type = value;
	}

	@WhereSQL(sql = "spare1=:SpecialQrcode_spare1", column = "SPARE1")
	public java.lang.String getSpare1() {
		return this.spare1;
	}

	public void setSpare1(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.spare1 = value;
	}

	@WhereSQL(sql = "spare2=:SpecialQrcode_spare2", column = "SPARE2")
	public java.lang.String getSpare2() {
		return this.spare2;
	}

	public void setSpare2(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.spare2 = value;
	}

	@WhereSQL(sql = "spare3=:SpecialQrcode_spare3", column = "SPARE3")
	public java.lang.String getSpare3() {
		return this.spare3;
	}

	public void setSpare3(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.spare3 = value;
	}

	@WhereSQL(sql = "spare4=:SpecialQrcode_spare4", column = "SPARE4")
	public java.lang.String getSpare4() {
		return this.spare4;
	}

	public void setSpare4(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.spare4 = value;
	}
	
	@WhereSQL(sql = "spare5=:SpecialQrcode_spare5", column = "SPARE5")
	public java.lang.String getSpare5() {
		return this.spare5;
	}

	public void setSpare5(java.lang.String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.trim();
		}
		this.spare5 = value;
	}

	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("二维码名称[").append(getQrcodeName()).append("],")
			.append("归属人[").append(getBelongUser()).append("],")
			.append("流水号[").append(getFlowNo()).append("],")
			.append("业务id[").append(getBusId()).append("],")
			.append("资费代码[").append(getFeeCode()).append("],")
			.append("是否绑定[").append(getType()).append("],")
			.append("备用字段1[").append(getSpare1()).append("],")
			.append("备用字段1[").append(getSpare2()).append("],")
			.append("备用字段1[").append(getSpare3()).append("],")
			.append("备用字段1[").append(getSpare4()).append("],")
			.append("备用字段1[").append(getSpare5()).append("],")
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
		if (obj instanceof SpecialQrcode == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		SpecialQrcode other = (SpecialQrcode)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
   
}










