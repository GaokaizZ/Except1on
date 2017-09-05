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
 * @version  2015-01-09 09:13:24
 * @see org.springrain.sinova.entity.Goods
 */
@Table(name = "T_GOODS")
public class Goods extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "商品表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_GOODS_NAME = "商品名称";
	public static final String ALIAS_FEE_CODE = "资费代码";
	public static final String ALIAS_GOODS_DESC = "商品简介（50）";
	public static final String ALIAS_FEE_STANDARD = "资费标准（20）";
	public static final String ALIAS_DETAIL_DESC = "详细描述（300）";
	public static final String ALIAS_EFFECT_WAY = "生效方式";
	public static final String ALIAS_TRANSACT_WAY = "办理方式";
	public static final String ALIAS_KEY_CHARSET = "智能搜索关键字";
	public static final String ALIAS_FIRST_CATEGORY = "一级分类";
	public static final String ALIAS_SECOND_CATEGORY = "二级分类";
	public static final String ALIAS_THIRD_CATEGORY = "三级分类";
	public static final String ALIAS_BUS_ID = "业务ID";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_STATE = "是否有效（0否/1是）";
    */
	
	//date formats
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 商品名称
	 */
	private java.lang.String goodsName;
	/**
	 * 资费代码
	 */
	private java.lang.String feeCode;
	/**
	 * 商品简介（50）
	 */
	private java.lang.String goodsDesc;
	/**
	 * 资费标准（20）
	 */
	private java.lang.String feeStandard;
	/**
	 * 详细描述（300）
	 */
	private java.lang.String detailDesc;
	/**
	 * 生效方式
	 */
	private java.lang.String effectWay;
	/**
	 * 办理方式
	 */
	private java.lang.String transactWay;
	/**
	 * 智能搜索关键字
	 */
	private java.lang.String keyCharset;
	/**
	 * 一级分类
	 */
	private java.lang.String firstCategory;
	/**
	 * 二级分类
	 */
	private java.lang.String secondCategory;
	/**
	 * 三级分类
	 */
	private java.lang.String thirdCategory;
	/**
	 * 业务ID
	 */
	private java.lang.String busId;
	/**
	 * 排序
	 */
	private java.lang.Long sort;
	/**
	 * 是否有效（0否/1是）
	 */
	private java.lang.String state;
	
	/**
	 * 上下架，0下架/1上架
	 */
	private String upDown;
	
	//columns END 数据库字段结束
	
	//concstructor

	public Goods(){
	}

	public Goods(
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
    @WhereSQL(sql = "id=:Goods_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setGoodsName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.goodsName = value;
	}
	
    @WhereSQL(sql = "goodsName=:Goods_goodsName", column = "GOODS_NAME")
	public java.lang.String getGoodsName() {
		return this.goodsName;
	}
	
	public void setFeeCode(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.feeCode = value;
	}
	
    @WhereSQL(sql = "feeCode=:Goods_feeCode", column = "FEE_CODE")
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}
	
	public void setGoodsDesc(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.goodsDesc = value;
	}
	
    @WhereSQL(sql = "goodsDesc=:Goods_goodsDesc", column = "GOODS_DESC")
	public java.lang.String getGoodsDesc() {
		return this.goodsDesc;
	}
	
	public void setFeeStandard(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.feeStandard = value;
	}
	
    @WhereSQL(sql = "feeStandard=:Goods_feeStandard", column = "FEE_STANDARD")
	public java.lang.String getFeeStandard() {
		return this.feeStandard;
	}
	
	public void setDetailDesc(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.detailDesc = value;
	}
	
    @WhereSQL(sql = "detailDesc=:Goods_detailDesc", column = "DETAIL_DESC")
	public java.lang.String getDetailDesc() {
		return this.detailDesc;
	}
	
	public void setEffectWay(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.effectWay = value;
	}
	
    @WhereSQL(sql = "effectWay=:Goods_effectWay", column = "EFFECT_WAY")
	public java.lang.String getEffectWay() {
		return this.effectWay;
	}
	
	public void setTransactWay(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.transactWay = value;
	}
	
    @WhereSQL(sql = "transactWay=:Goods_transactWay", column = "TRANSACT_WAY")
	public java.lang.String getTransactWay() {
		return this.transactWay;
	}
	
	public void setKeyCharset(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.keyCharset = value;
	}
	
    @WhereSQL(sql = "keyCharset=:Goods_keyCharset", column = "KEY_CHARSET")
	public java.lang.String getKeyCharset() {
		return this.keyCharset;
	}
	
	public void setFirstCategory(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.firstCategory = value;
	}
	
    @WhereSQL(sql = "firstCategory=:Goods_firstCategory", column = "FIRST_CATEGORY")
	public java.lang.String getFirstCategory() {
		return this.firstCategory;
	}
	
	public void setSecondCategory(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.secondCategory = value;
	}
	
    @WhereSQL(sql = "secondCategory=:Goods_secondCategory", column = "SECOND_CATEGORY")
	public java.lang.String getSecondCategory() {
		return this.secondCategory;
	}
	
	public void setThirdCategory(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.thirdCategory = value;
	}
	
    @WhereSQL(sql = "thirdCategory=:Goods_thirdCategory", column = "THIRD_CATEGORY")
	public java.lang.String getThirdCategory() {
		return this.thirdCategory;
	}
	
	public void setBusId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.busId = value;
	}
	
    @WhereSQL(sql = "busId=:Goods_busId", column = "BUS_ID")
	public java.lang.String getBusId() {
		return this.busId;
	}
	
	public void setSort(java.lang.Long value) {
		this.sort = value;
	}
	
    @WhereSQL(sql = "sort=:Goods_sort", column = "SORT")
	public java.lang.Long getSort() {
		return this.sort;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Goods_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
    
	/**
	 * @return the upDown
	 */
    @WhereSQL(sql = "upDown=:Goods_upDown", column = "UP_DOWN")
	public String getUpDown() {
		return this.upDown;
	}

	/**
	 * @param upDown the upDown to set
	 */
	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("商品名称[").append(getGoodsName()).append("],")
			.append("资费代码[").append(getFeeCode()).append("],")
			.append("商品简介（50）[").append(getGoodsDesc()).append("],")
			.append("资费标准（20）[").append(getFeeStandard()).append("],")
			.append("详细描述（300）[").append(getDetailDesc()).append("],")
			.append("生效方式[").append(getEffectWay()).append("],")
			.append("办理方式[").append(getTransactWay()).append("],")
			.append("智能搜索关键字[").append(getKeyCharset()).append("],")
			.append("一级分类[").append(getFirstCategory()).append("],")
			.append("二级分类[").append(getSecondCategory()).append("],")
			.append("三级分类[").append(getThirdCategory()).append("],")
			.append("业务ID[").append(getBusId()).append("],")
			.append("排序[").append(getSort()).append("],")
			.append("是否有效（0否/1是）[").append(getState()).append("],")
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
		Goods other = (Goods)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
