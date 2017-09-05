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
 * @version  2015-01-09 09:17:37
 * @see org.springrain.sinova.entity.ExecuteTime
 */
@Table(name = "T_EXECUTE_TIME")
public class ExecuteTime extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "接口执行时间统计";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_CLASS_NAME = "类名";
	public static final String ALIAS_METHOD_NAME = "方法名";
	public static final String ALIAS_INPUT_PARAM = "入参串";
	public static final String ALIAS_OUTPUT_PARAM = "出参串";
	public static final String ALIAS_TIME_CONSUME = "耗时";
	public static final String ALIAS_CALL_TIME = "调用时间";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_MOBILE = "手机号";
	public static final String ALIAS_ACCOUNT = "用户账号";
    */
	
	//date formats
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 类名
	 */
	private java.lang.String className;
	/**
	 * 方法名
	 */
	private java.lang.String methodName;
	/**
	 * 入参串
	 */
	private java.lang.String inputParam;
	/**
	 * 出参串
	 */
	private java.lang.String outputParam;
	/**
	 * 耗时
	 */
	private java.lang.String timeConsume;
	/**
	 * 调用时间
	 */
	private java.lang.String callTime;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * 用户账号
	 */
	private java.lang.String account;
	//columns END 数据库字段结束
	
	//concstructor

	public ExecuteTime(){
	}

	public ExecuteTime(
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
    @WhereSQL(sql = "id=:ExecuteTime_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setClassName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.className = value;
	}
	
    @WhereSQL(sql = "className=:ExecuteTime_className", column = "CLASS_NAME")
	public java.lang.String getClassName() {
		return this.className;
	}
	
	public void setMethodName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.methodName = value;
	}
	
    @WhereSQL(sql = "methodName=:ExecuteTime_methodName", column = "METHOD_NAME")
	public java.lang.String getMethodName() {
		return this.methodName;
	}
	
	public void setInputParam(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.inputParam = value;
	}
	
    @WhereSQL(sql = "inputParam=:ExecuteTime_inputParam", column = "INPUT_PARAM")
	public java.lang.String getInputParam() {
		return this.inputParam;
	}
	
	public void setOutputParam(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.outputParam = value;
	}
	
    @WhereSQL(sql = "outputParam=:ExecuteTime_outputParam", column = "OUTPUT_PARAM")
	public java.lang.String getOutputParam() {
		return this.outputParam;
	}
	
	public void setTimeConsume(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.timeConsume = value;
	}
	
    @WhereSQL(sql = "timeConsume=:ExecuteTime_timeConsume", column = "TIME_CONSUME")
	public java.lang.String getTimeConsume() {
		return this.timeConsume;
	}
	
	public void setCallTime(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.callTime = value;
	}
	
    @WhereSQL(sql = "callTime=:ExecuteTime_callTime", column = "CALL_TIME")
	public java.lang.String getCallTime() {
		return this.callTime;
	}
	
	public void setRemark(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.remark = value;
	}
	
    @WhereSQL(sql = "remark=:ExecuteTime_remark", column = "REMARK")
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setMobile(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.mobile = value;
	}
	
    @WhereSQL(sql = "mobile=:ExecuteTime_mobile", column = "MOBILE")
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setAccount(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.account = value;
	}
	
    @WhereSQL(sql = "account=:ExecuteTime_account", column = "ACCOUNT")
	public java.lang.String getAccount() {
		return this.account;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("类名[").append(getClassName()).append("],")
			.append("方法名[").append(getMethodName()).append("],")
			.append("入参串[").append(getInputParam()).append("],")
			.append("出参串[").append(getOutputParam()).append("],")
			.append("耗时[").append(getTimeConsume()).append("],")
			.append("调用时间[").append(getCallTime()).append("],")
			.append("备注[").append(getRemark()).append("],")
			.append("手机号[").append(getMobile()).append("],")
			.append("用户账号[").append(getAccount()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof ExecuteTime == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ExecuteTime other = (ExecuteTime)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
