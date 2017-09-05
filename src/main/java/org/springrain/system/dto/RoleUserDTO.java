/**
 * Copyright 2015. www.sinovatech.com Inc. All rights reserved.
 * qrcodehall
 * 2015年1月19日 下午8:01:35
 */
package org.springrain.system.dto;

/**
 * @description TODO <br/>
 * @date 2015年1月19日 下午8:01:35 <br/>
 * @author wangbo
 */
public class RoleUserDTO {

	private String id;

	private String account;

	private String userName;

	private String roleId;

	private String roleCode;

	private String roleName;

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return this.account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return this.roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return this.roleCode;
	}

	/**
	 * @param roleCode
	 *            the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return this.roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
