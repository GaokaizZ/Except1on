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
 * TODO 在此加入类描述	受理业务表
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:17:04
 * @see org.springrain.sinova.entity.AcceptRecord
 */
@Table(name = "T_ACCEPT_RECORD")
public class AcceptRecord extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "受理记录表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ACCOUNT = "用户账号";
	public static final String ALIAS_MOBILE = "客户手机号";
	public static final String ALIAS_REWARD = "酬金";
	public static final String ALIAS_IP = "IP地址";
	public static final String ALIAS_OPERATE_TYPE = "操作类型";
	public static final String ALIAS_CHANNEL_CODE = "渠道编号";
	public static final String ALIAS_ORDER_ID = "BOSS工单号";
	public static final String ALIAS_HINT = "提示信息";
	public static final String ALIAS_BUS_ID = "业务ID";
	public static final String ALIAS_FEE_CODE = "资费代码";
	public static final String ALIAS_DATETIME = "受理时间";
	public static final String ALIAS_REMARK = "备注信息";
    */
	
	//date formats
	//public static final String FORMAT_DATETIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 用户账号
	 */
	private java.lang.String account;
	/**
	 * 客户手机号
	 */
	private java.lang.String mobile;
	/**
	 * 酬金
	 */
	private java.math.BigDecimal reward;
	/**
	 * IP地址
	 */
	private java.lang.String ip;
	/**
	 * 操作类型
	 */
	private java.lang.String operateType;
	/**
	 * 渠道编号
	 */
	private java.lang.String channelCode;
	/**
	 * BOSS工单号
	 */
	private java.lang.String orderId;
	/**
	 * 提示信息
	 */
	private java.lang.String hint;
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
	 * 备注信息
	 */
	private java.lang.String remark;
	
	/**
	 * 抽奖id，如果为空，则为正常办理
	 */
	private java.lang.String rotateId;
	//columns END 数据库字段结束
	
	//concstructor

	public AcceptRecord(){
	}

	public AcceptRecord(
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
    @WhereSQL(sql = "id=:AcceptRecord_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setAccount(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.account = value;
	}
	
    @WhereSQL(sql = "account=:AcceptRecord_account", column = "ACCOUNT")
	public java.lang.String getAccount() {
		return this.account;
	}
	
	public void setMobile(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.mobile = value;
	}
	
    @WhereSQL(sql = "mobile=:AcceptRecord_mobile", column = "MOBILE")
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setReward(java.math.BigDecimal value) {
		this.reward = value;
	}
	
    @WhereSQL(sql = "reward=:AcceptRecord_reward", column = "REWARD")
	public java.math.BigDecimal getReward() {
		return this.reward;
	}
	
	public void setIp(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.ip = value;
	}
	
    @WhereSQL(sql = "ip=:AcceptRecord_ip", column = "IP")
	public java.lang.String getIp() {
		return this.ip;
	}
	
	public void setOperateType(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.operateType = value;
	}
	
    @WhereSQL(sql = "operateType=:AcceptRecord_operateType", column = "OPERATE_TYPE")
	public java.lang.String getOperateType() {
		return this.operateType;
	}
	
	public void setChannelCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.channelCode = value;
	}
	
    @WhereSQL(sql = "channelCode=:AcceptRecord_channelCode", column = "CHANNEL_CODE")
	public java.lang.String getChannelCode() {
		return this.channelCode;
	}
	
	public void setOrderId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.orderId = value;
	}
	
    @WhereSQL(sql = "orderId=:AcceptRecord_orderId", column = "ORDER_ID")
	public java.lang.String getOrderId() {
		return this.orderId;
	}
	
	public void setHint(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.hint = value;
	}
	
    @WhereSQL(sql = "hint=:AcceptRecord_hint", column = "HINT")
	public java.lang.String getHint() {
		return this.hint;
	}
	
	public void setBusId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busId = value;
	}
	
    @WhereSQL(sql = "busId=:AcceptRecord_busId", column = "BUS_ID")
	public java.lang.String getBusId() {
		return this.busId;
	}
	
	public void setFeeCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.feeCode = value;
	}
	
    @WhereSQL(sql = "feeCode=:AcceptRecord_feeCode", column = "FEE_CODE")
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}
	/*
	public String getdatetimeString() {
		return DateUtils.convertDate2String(FORMAT_DATETIME, getdatetime());
	}
	public void setdatetimeString(String value) throws ParseException{
		setdatetime(DateUtils.convertString2Date(FORMAT_DATETIME,value));
	}
	*/
	
	public void setDatetime(java.util.Date value) {
		this.datetime = value;
	}
	
    @WhereSQL(sql = "datetime=:AcceptRecord_datetime", column = "DATETIME")
	public java.util.Date getDatetime() {
		return this.datetime;
	}
	
	public void setRemark(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.remark = value;
	}
	
    @WhereSQL(sql = "remark=:AcceptRecord_remark", column = "REMARK")
	public java.lang.String getRemark() {
		return this.remark;
	}
    
    @WhereSQL(sql = "rotateId=:AcceptRecord_rotateId", column = "ROTATE_ID")
	public java.lang.String getRotateId() {
		return rotateId;
	}

	public void setRotateId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.rotateId = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("用户账号[").append(getAccount()).append("],")
			.append("客户手机号[").append(getMobile()).append("],")
			.append("酬金[").append(getReward()).append("],")
			.append("IP地址[").append(getIp()).append("],")
			.append("操作类型[").append(getOperateType()).append("],")
			.append("渠道编号[").append(getChannelCode()).append("],")
			.append("BOSS工单号[").append(getOrderId()).append("],")
			.append("提示信息[").append(getHint()).append("],")
			.append("业务ID[").append(getBusId()).append("],")
			.append("资费代码[").append(getFeeCode()).append("],")
			.append("受理时间[").append(getDatetime()).append("],")
			.append("备注信息[").append(getRemark()).append("],")
			.append("抽奖ID[").append(getRotateId()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof AcceptRecord == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		AcceptRecord other = (AcceptRecord)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
