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
 * @version  2015-06-25 10:55:22
 * @see org.springrain.sinova.entity.AdGoods
 */
@Table(name = "T_AD_GOODS")
public class AdGoods extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "AdGoods";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_OWNER = "owner";
	public static final String ALIAS_GOODS_ID = "goodsId";
	public static final String ALIAS_STATE = "state";
    */
	
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * owner
	 */
	private java.lang.String owner;
	/**
	 * goodsId
	 */
	private java.lang.String goodsId;
	/**
	 * state
	 */
	private java.lang.String state;
	//columns END 数据库字段结束
	
	//concstructor

	public AdGoods(){
	}

	public AdGoods(
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
    @WhereSQL(sql = "id=:AdGoods_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setOwner(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.owner = value;
	}
	
    @WhereSQL(sql = "owner=:AdGoods_owner", column = "OWNER")
	public java.lang.String getOwner() {
		return this.owner;
	}
	
	public void setGoodsId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.goodsId = value;
	}
	
    @WhereSQL(sql = "goodsId=:AdGoods_goodsId", column = "GOODS_ID")
	public java.lang.String getGoodsId() {
		return this.goodsId;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:AdGoods_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("owner[").append(getOwner()).append("],")
			.append("goodsId[").append(getGoodsId()).append("],")
			.append("state[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof AdGoods == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		AdGoods other = (AdGoods)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
