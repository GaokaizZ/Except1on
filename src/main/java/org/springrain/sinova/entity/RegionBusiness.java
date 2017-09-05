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
 * @version  2015-01-09 09:14:14
 * @see org.springrain.sinova.entity.RegionBusiness
 */
@Table(name = "T_REGION_BUSINESS")
public class RegionBusiness extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "地市与业务关系";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_REGION_ID = "地市ID";
	public static final String ALIAS_BUS_ID = "业务ID";
	public static final String ALIAS_REWARD = "酬金";
    */
	
	//date formats
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 地市ID
	 */
	private java.lang.String regionId;
	/**
	 * 业务ID
	 */
	private java.lang.String busId;
	/**
	 * 酬金
	 */
	private java.math.BigDecimal reward;
	//columns END 数据库字段结束
	
	//concstructor

	public RegionBusiness(){
	}

	public RegionBusiness(
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
    @WhereSQL(sql = "id=:RegionBusiness_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setRegionId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.regionId = value;
	}
	
    @WhereSQL(sql = "regionId=:RegionBusiness_regionId", column = "REGION_ID")
	public java.lang.String getRegionId() {
		return this.regionId;
	}
	
	public void setBusId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busId = value;
	}
	
    @WhereSQL(sql = "busId=:RegionBusiness_busId", column = "BUS_ID")
	public java.lang.String getBusId() {
		return this.busId;
	}
	
	public void setReward(java.math.BigDecimal value) {
		this.reward = value;
	}
	
    @WhereSQL(sql = "reward=:RegionBusiness_reward", column = "REWARD")
	public java.math.BigDecimal getReward() {
		return this.reward;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("地市ID[").append(getRegionId()).append("],")
			.append("业务ID[").append(getBusId()).append("],")
			.append("酬金[").append(getReward()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof RegionBusiness == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RegionBusiness other = (RegionBusiness)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
