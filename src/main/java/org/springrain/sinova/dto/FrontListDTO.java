package org.springrain.sinova.dto;

public class FrontListDTO {

	/**
	 * 业务id
	 */
	private String busId;
	/**
	 * 业务编码
	 */
	private String busCode;
	/**
	 * 业务名称
	 */
	private String busName;
	/**
	 * 商品id
	 */
	private String goodsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 业务是否有效(0:无效,1:有效)
	 */
	private String busState;
	/**
	 * 业务上下架(0:下架,1:上架)
	 */
	private String busUpDown;
	/**
	 * 商品是否有效(0:无效,1:有效)
	 */
	private String goodsState;
	/**
	 * 商品上下架(0:下架,1:上架)
	 */
	private String goodsUpDown;
	/**
	 * 商品资费代码
	 */
	private String goodsFeeCode;
	/**
	 * 业务类型
	 */
	private String busType;
	/**
	 * 商品二级分类
	 */
	private String secondCategory;
	

	public String getSecondCategory() {
		return secondCategory;
	}

	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getGoodsFeeCode() {
		return goodsFeeCode;
	}

	public void setGoodsFeeCode(String goodsFeeCode) {
		this.goodsFeeCode = goodsFeeCode;
	}

	public String getBusState() {
		return busState;
	}

	public void setBusState(String busState) {
		this.busState = busState;
	}

	public String getBusUpDown() {
		return busUpDown;
	}

	public void setBusUpDown(String busUpDown) {
		this.busUpDown = busUpDown;
	}

	public String getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	public String getGoodsUpDown() {
		return goodsUpDown;
	}

	public void setGoodsUpDown(String goodsUpDown) {
		this.goodsUpDown = goodsUpDown;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
