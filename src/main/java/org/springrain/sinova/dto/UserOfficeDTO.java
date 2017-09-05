/**
 * Copyright 2015. www.sinovatech.com Inc. All rights reserved.
 * qrcodehall
 * 2015年1月10日 下午5:29:10
 */
package org.springrain.sinova.dto;

import org.apache.commons.lang3.StringUtils;
import org.springrain.system.util.Punctuation;

/**
 * @description TODO <br/>
 * @date 2015年1月10日 下午5:29:10 <br/>
 * @author wangbo
 */
public class UserOfficeDTO {
	/**
	 * 地市编号
	 */
	private String regionCode;
	/**
	 * 地市名称
	 */
	private String regionName;

	/**
	 * 区县编号
	 */
	private String countyCode;
	/**
	 * 区县名称
	 */
	private String countyName;

	/**
	 * 营业厅编号
	 */
	private String officeCode;
	/**
	 * 营业厅名称
	 */
	private String officeName;
	/**
	 * 营业厅地址
	 */
	private String address;

	/**
	 * 岗位编码，多个岗位用逗号分隔
	 */
	private String post;;

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return this.regionCode;
	}

	/**
	 * @param regionCode
	 *            the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return this.regionName;
	}

	/**
	 * @param regionName
	 *            the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the countyCode
	 */
	public String getCountyCode() {
		return this.countyCode;
	}

	/**
	 * @param countyCode
	 *            the countyCode to set
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * @return the countyName
	 */
	public String getCountyName() {
		return this.countyName;
	}

	/**
	 * @param countyName
	 *            the countyName to set
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return this.officeCode;
	}

	/**
	 * @param officeCode
	 *            the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return this.officeName;
	}

	/**
	 * @param officeName
	 *            the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the post
	 */
	public String[] getPost() {
		if (StringUtils.isBlank(this.post)) {
			return new String[] {};
		}
		return StringUtils.split(this.post, Punctuation.DOU_HAO.toString());
	}

	/**
	 * @param post
	 *            the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

}
