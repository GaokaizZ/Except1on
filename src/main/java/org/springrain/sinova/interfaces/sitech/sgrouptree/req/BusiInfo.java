package org.springrain.sinova.interfaces.sitech.sgrouptree.req;

import org.springrain.sinova.interfaces.sitech.sloginno.req.OprInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BusiInfo {

	@XStreamAlias(value = "GROUP_ID")
	private String groupId;
	@XStreamAlias(value = "ROOT_DISTANCE")
	private String rootDistance;
	@XStreamAlias(value = "IS_ACTIVE")
	private String isActive;
	@XStreamAlias(value = "CLASS_CODE")
	private String classCode;
	@XStreamAlias(value = "CLASS_KIND")
	private String classKind;
	@XStreamAlias(value = "IS_AGENT")
	private String isAgent;
	@XStreamAlias(value = "IS_TOWN")
	private String isTown;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRootDistance() {
		return rootDistance;
	}

	public void setRootDistance(String rootDistance) {
		this.rootDistance = rootDistance;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassKind() {
		return classKind;
	}

	public void setClassKind(String classKind) {
		this.classKind = classKind;
	}

	public String getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	public String getIsTown() {
		return isTown;
	}

	public void setIsTown(String isTown) {
		this.isTown = isTown;
	}

}
