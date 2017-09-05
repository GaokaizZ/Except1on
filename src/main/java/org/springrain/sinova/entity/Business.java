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
 * @version  2015-06-19 12:37:34
 * @see org.springrain.sinova.entity.Business
 */
@Table(name = "T_BUSINESS")
public class Business extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "业务表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BUS_CODE = "业务编号";
	public static final String ALIAS_BUS_NAME = "业务名称";
	public static final String ALIAS_DEFAULT_REWARD = "默认酬金";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_STATE = "是否有效，0否/1是";
	public static final String ALIAS_UP_DOWN = "上下架，0下架/1上架";
	public static final String ALIAS_BUS_TYPE = "业务类型";
	public static final String ALIAS_BUS_URL = "业务URL";
	public static final String ALIAS_GROUP_ID = "groupId";
	public static final String ALIAS_EDIT_TIME = "editTime";
	public static final String ALIAS_START_TIME = "startTime";
	public static final String ALIAS_END_TIME = "endTime";
	public static final String ALIAS_GROUP_CUST_ID = "groupCustId";
    */
	
	//date formats
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 业务编号
	 */
	private java.lang.String busCode;
	/**
	 * 业务名称
	 */
	private java.lang.String busName;
	/**
	 * 默认酬金
	 */
	private java.math.BigDecimal defaultReward;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 排序
	 */
	private java.lang.Long sort;
	/**
	 * 是否有效，0否/1是
	 */
	private java.lang.String state;
	/**
	 * 上下架，0下架/1上架
	 */
	private java.lang.String upDown;
	/**
	 * 业务类型
	 */
	private java.lang.String busType;
	/**
	 * 业务URL
	 */
	private java.lang.String busUrl;
	/**
	 * groupId
	 */
	private java.lang.String groupId;
	/**
	 * editTime
	 */
	private java.lang.String editTime;
	/**
	 * startTime
	 */
	private java.lang.String startTime;
	/**
	 * endTime
	 */
	private java.lang.String endTime;
	/**
	 * groupCustId
	 */
	private java.lang.String groupCustId;
	//columns END 数据库字段结束
	
	//concstructor

	public Business(){
	}

	public Business(
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
    @WhereSQL(sql = "id=:Business_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setBusCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busCode = value;
	}
	
    @WhereSQL(sql = "busCode=:Business_busCode", column = "BUS_CODE")
	public java.lang.String getBusCode() {
		return this.busCode;
	}
	
	public void setBusName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busName = value;
	}
	
    @WhereSQL(sql = "busName=:Business_busName", column = "BUS_NAME")
	public java.lang.String getBusName() {
		return this.busName;
	}
	
	public void setDefaultReward(java.math.BigDecimal value) {
		this.defaultReward = value;
	}
	
    @WhereSQL(sql = "defaultReward=:Business_defaultReward", column = "DEFAULT_REWARD")
	public java.math.BigDecimal getDefaultReward() {
		return this.defaultReward;
	}
	
	public void setRemark(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.remark = value;
	}
	
    @WhereSQL(sql = "remark=:Business_remark", column = "REMARK")
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setSort(java.lang.Long value) {
		this.sort = value;
	}
	
    @WhereSQL(sql = "sort=:Business_sort", column = "SORT")
	public java.lang.Long getSort() {
		return this.sort;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Business_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setUpDown(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.upDown = value;
	}
	
    @WhereSQL(sql = "upDown=:Business_upDown", column = "UP_DOWN")
	public java.lang.String getUpDown() {
		return this.upDown;
	}
	
	public void setBusType(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busType = value;
	}
	
    @WhereSQL(sql = "busType=:Business_busType", column = "BUS_TYPE")
	public java.lang.String getBusType() {
		return this.busType;
	}
	
	public void setBusUrl(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busUrl = value;
	}
	
    @WhereSQL(sql = "busUrl=:Business_busUrl", column = "BUS_URL")
	public java.lang.String getBusUrl() {
		return this.busUrl;
	}
	
	public void setGroupId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.groupId = value;
	}
	
    @WhereSQL(sql = "groupId=:Business_groupId", column = "GROUP_ID")
	public java.lang.String getGroupId() {
		return this.groupId;
	}
	
	public void setEditTime(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.editTime = value;
	}
	
    @WhereSQL(sql = "editTime=:Business_editTime", column = "EDIT_TIME")
	public java.lang.String getEditTime() {
		return this.editTime;
	}
	
	public void setStartTime(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.startTime = value;
	}
	
    @WhereSQL(sql = "startTime=:Business_startTime", column = "START_TIME")
	public java.lang.String getStartTime() {
		return this.startTime;
	}
	
	public void setEndTime(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.endTime = value;
	}
	
    @WhereSQL(sql = "endTime=:Business_endTime", column = "END_TIME")
	public java.lang.String getEndTime() {
		return this.endTime;
	}
	
	public void setGroupCustId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.groupCustId = value;
	}
	
    @WhereSQL(sql = "groupCustId=:Business_groupCustId", column = "GROUP_CUST_ID")
	public java.lang.String getGroupCustId() {
		return this.groupCustId;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("业务编号[").append(getBusCode()).append("],")
			.append("业务名称[").append(getBusName()).append("],")
			.append("默认酬金[").append(getDefaultReward()).append("],")
			.append("备注[").append(getRemark()).append("],")
			.append("排序[").append(getSort()).append("],")
			.append("是否有效，0否/1是[").append(getState()).append("],")
			.append("上下架，0下架/1上架[").append(getUpDown()).append("],")
			.append("业务类型[").append(getBusType()).append("],")
			.append("业务URL[").append(getBusUrl()).append("],")
			.append("groupId[").append(getGroupId()).append("],")
			.append("editTime[").append(getEditTime()).append("],")
			.append("startTime[").append(getStartTime()).append("],")
			.append("endTime[").append(getEndTime()).append("],")
			.append("groupCustId[").append(getGroupCustId()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Business == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Business other = (Business)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
