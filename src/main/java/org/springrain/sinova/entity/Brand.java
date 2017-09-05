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
 * @version  2015-01-09 08:57:50
 * @see org.springrain.sinova.entity.Brand
 */
@Table(name = "T_BRAND")
public class Brand extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "品牌表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BRAND_CODE = "品牌编号";
	public static final String ALIAS_BRAND_NAME = "品牌名称";
	public static final String ALIAS_STATE = "是否有效（0否/1是）";
    */
	
	//date formats
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 品牌编号
	 */
	private java.lang.String brandCode;
	/**
	 * 品牌名称
	 */
	private java.lang.String brandName;
	/**
	 * 是否有效（0否/1是）
	 */
	private java.lang.String state;
	//columns END 数据库字段结束
	
	//concstructor

	public Brand(){
	}

	public Brand(
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
    @WhereSQL(sql = "id=:Brand_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setBrandCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.brandCode = value;
	}
	
    @WhereSQL(sql = "brandCode=:Brand_brandCode", column = "BRAND_CODE")
	public java.lang.String getBrandCode() {
		return this.brandCode;
	}
	
	public void setBrandName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.brandName = value;
	}
	
    @WhereSQL(sql = "brandName=:Brand_brandName", column = "BRAND_NAME")
	public java.lang.String getBrandName() {
		return this.brandName;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Brand_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("品牌编号[").append(getBrandCode()).append("],")
			.append("品牌名称[").append(getBrandName()).append("],")
			.append("是否有效（0否/1是）[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Brand == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Brand other = (Brand)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
