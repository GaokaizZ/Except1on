package org.springrain.sinova.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;

@Table(name = "T_GOODS_ACT")
public class GoodsAct extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 商品id
	 */
	private java.lang.String goodsId;
	/**
	 * 活动id
	 */
	private java.lang.String busId;
	/**
	 * 
	 */
	private java.lang.String groupId;
	
	private java.lang.String spare1;
	private java.lang.String spare2;
	private java.lang.String spare3;
	private java.lang.String spare4;
	private java.lang.String spare5;
	
	
	public GoodsAct(){}
	
	public GoodsAct(java.lang.String id){
		this.id = id;
	}
	
	
	@Id
    @WhereSQL(sql = "id=:GoodsAct_id", column = "ID")	
	public java.lang.String getId() {
		return this.id;
	}
	public void setId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.id = value;
	}
	@WhereSQL(sql = "goodsId=:GoodsAct_goodsId", column = "GOODS_ID")
	public java.lang.String getGoodsId() {
		return this.goodsId;
	}
	public void setGoodsId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.goodsId = value;
	}
	
	@WhereSQL(sql = "busId=:GoodsAct_busId", column = "BUS_ID")
	public java.lang.String getBusId() {
		return busId;
	}
	public void setBusId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busId = value;
	}
	@WhereSQL(sql = "groupId=:GoodsAct_groupId", column = "GROUP_ID")
	public java.lang.String getGroupId() {
		return groupId;
	}
	public void setGroupId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.groupId = value;
	}
	@WhereSQL(sql = "spare1=:GoodsAct_spare1", column = "SPARE1")
	public java.lang.String getSpare1() {
		return this.spare1;
	}
	public void setSpare1(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare1 = value;
	}
	@WhereSQL(sql = "spare2=:GoodsAct_spare2", column = "SPARE2")
	public java.lang.String getSpare2() {
		return this.spare2;
	}
	public void setSpare2(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare2 = value;
	}
	@WhereSQL(sql = "spare3=:GoodsAct_spare3", column = "SPARE3")
	public java.lang.String getSpare3() {
		return this.spare3;
	}
	public void setSpare3(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare3 = value;
	}
	@WhereSQL(sql = "spare4=:GoodsAct_spare4", column = "SPARE4")
	public java.lang.String getSpare4() {
		return this.spare4;
	}
	public void setSpare4(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare4 = value;
	}
	@WhereSQL(sql = "spare5=:GoodsAct_spare5", column = "SPARE5")
	public java.lang.String getSpare5() {
		return this.spare5;
	}
	public void setSpare5(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare5 = value;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("商品id[").append(getGoodsId()).append("],")
			.append("活动id[").append(getBusId()).append("],")
			.append("归属[").append(getGroupId()).append("],")
			.append("备用1[").append(getSpare1()).append("],")
			.append("备用2[").append(getSpare2()).append("],")
			.append("备用3[").append(getSpare3()).append("],")
			.append("备用4[").append(getSpare4()).append("],")
			.append("备用5[").append(getSpare5()).append("],")
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
		if (obj instanceof Goods == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GoodsAct other = (GoodsAct)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}

	
}
