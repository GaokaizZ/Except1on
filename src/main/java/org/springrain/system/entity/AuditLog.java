package org.springrain.system.entity;

import java.util.Calendar;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.TableGroup;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
import org.springrain.frame.entity.IAuditLog;
import org.springrain.frame.util.GlobalStatic;
/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2013-04-02 10:17:31
 * @see org.springrain.system.entity.AuditLog
 */
@Table(name="t_auditlog")
@TableGroup(name="ext")
public class AuditLog  extends BaseEntity implements IAuditLog {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Auditlog";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_OPERATION_TYPE = "操作类型";
	public static final String ALIAS_OPERATOR_NAME = "操作人姓名";
	public static final String ALIAS_PRE_VALUE = "旧值";
	public static final String ALIAS_CUR_VALUE = "新值";
	public static final String ALIAS_OPERATION_TIME = "操作时间";
	public static final String ALIAS_OPERATION_CLASS = "操作类";
	public static final String ALIAS_OPERATION_CLASS_ID = "记录ID";
    */
	//date formats
	//public static final String FORMAT_OPERATION_TIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 操作类型
	 */
	private java.lang.String operationType;
	/**
	 * 操作人姓名
	 */
	private java.lang.String operatorName;
	/**
	 * 旧值
	 */
	private java.lang.String preValue;
	/**
	 * 新值
	 */
	private java.lang.String curValue;
	/**
	 * 操作时间
	 */
	private java.util.Date operationTime;
	/**
	 * 操作类
	 */
	private java.lang.String operationClass;
	/**
	 * 记录ID
	 */
	private java.lang.String operationClassId;
	//columns END
	
	
	private String ext;
	
	//concstructor

	public AuditLog(){
	}

	public AuditLog(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id
	@WhereSQL(sql = "id=:Auditlog_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}

	@Override
	public void setOperationType(java.lang.String value) {
		this.operationType = value;
	}

	@Override
	@WhereSQL(sql = "operationType=:Auditlog_operationType", column = "OPERATION_TYPE")
	public java.lang.String getOperationType() {
		return this.operationType;
	}

	@Override
	public void setOperatorName(java.lang.String value) {
		this.operatorName = value;
	}

	@Override
	@WhereSQL(sql = "operatorName=:Auditlog_operatorName", column = "OPERATOR_NAME")
	public java.lang.String getOperatorName() {
		return this.operatorName;
	}

	@Override
	public void setPreValue(java.lang.String value) {
		this.preValue = value;
	}

	@Override
	@WhereSQL(sql = "preValue=:Auditlog_preValue", column = "PRE_VALUE")
	public java.lang.String getPreValue() {
		return this.preValue;
	}

	@Override
	public void setCurValue(java.lang.String value) {
		this.curValue = value;
	}

	@Override
	@WhereSQL(sql = "curValue=:Auditlog_curValue", column = "CUR_VALUE")
	public java.lang.String getCurValue() {
		return this.curValue;
	}

	@Override
	public void setOperationTime(java.util.Date value) {
		this.operationTime = value;
	}

	@Override
	@WhereSQL(sql = "operationTime=:Auditlog_operationTime", column = "OPERATION_TIME")
	public java.util.Date getOperationTime() {
		return this.operationTime;
	}

	@Override
	public void setOperationClass(java.lang.String value) {
		this.operationClass = value;
	}

	@Override
	@WhereSQL(sql = "operationClass=:Auditlog_operationClass", column = "OPERATION_CLASS")
	public java.lang.String getOperationClass() {
		return this.operationClass;
	}

	@Override
	public void setOperationClassId(java.lang.String value) {
		this.operationClassId = value;
	}

	@Override
	@WhereSQL(sql = "operationClassId=:Auditlog_operationClassId", column = "OPERATION_CLASS_ID")
	public java.lang.String getOperationClassId() {
		return this.operationClassId;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("ID[").append(getId()).append("],")
			.append("操作类型[").append(getOperationType()).append("],")
			.append("操作人姓名[").append(getOperatorName()).append("],")
			.append("旧值[").append(getPreValue()).append("],")
			.append("新值[").append(getCurValue()).append("],")
			.append("操作时间[").append(getOperationTime()).append("],")
			.append("操作类[").append(getOperationClass()).append("],")
			.append("记录ID[").append(getOperationClassId()).append("],")
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
		if(obj instanceof AuditLog == false) return false;
		if(this == obj) return true;
		AuditLog other = (AuditLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	@Override
	@Transient
	public String getExt() {
		if(StringUtils.isBlank(ext)){
			int year= Calendar.getInstance().get(Calendar.YEAR);
			this.ext= GlobalStatic.tableExt + year;
		}
			return ext;
	}

	@Override
	public void setExt(String ext) {
		this.ext = ext;
	}
	
}

	
