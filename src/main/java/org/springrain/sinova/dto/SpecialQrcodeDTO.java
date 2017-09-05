package org.springrain.sinova.dto;

import org.springrain.sinova.entity.SpecialQrcode;

public class SpecialQrcodeDTO extends SpecialQrcode{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 业务名称
	 */
	private java.lang.String busName;
	
	/**
	 * 业务编码
	 */
	private java.lang.String busCode;
	
	/**
	 * 商品名称
	 */
	private java.lang.String goodsName;
	
	/**
	 * 资费代码
	 */
	private java.lang.String feeCode;

	public java.lang.String getBusName() {
		return busName;
	}

	public void setBusName(java.lang.String busName) {
		this.busName = busName;
	}

	public java.lang.String getBusCode() {
		return busCode;
	}

	public void setBusCode(java.lang.String busCode) {
		this.busCode = busCode;
	}

	public java.lang.String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}

	public java.lang.String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(java.lang.String feeCode) {
		this.feeCode = feeCode;
	}
	
	

}
