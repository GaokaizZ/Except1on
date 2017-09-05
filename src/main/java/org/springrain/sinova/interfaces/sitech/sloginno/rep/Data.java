package org.springrain.sinova.interfaces.sitech.sloginno.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Data {

	@XStreamAlias(value = "GROUP_ID")
	private String groupId;
	@XStreamAlias(value = "GROUP_NAME")
	private String groupName;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
