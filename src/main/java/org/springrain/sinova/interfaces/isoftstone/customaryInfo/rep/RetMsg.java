package org.springrain.sinova.interfaces.isoftstone.customaryInfo.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class RetMsg {

	@XStreamAlias(value = "uid")
	private String uid;
	@XStreamAlias(value = "name")
	private String name;
	@XStreamAlias(value = "phoneNum")
	private String phoneNum;
	@XStreamAlias(value = "orgID")
	private String orgID;
	@XStreamAlias(value = "bossAcc")
	private String bossAcc;
	@XStreamAlias(value = "regionName")
	private String regionName;
	@XStreamAlias(value = "districtName")
	private String districtName;
	@XStreamAlias(value = "towmName")
	private String towmName;
	@XStreamAlias(value = "status")
	private String status;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getBossAcc() {
		return bossAcc;
	}

	public void setBossAcc(String bossAcc) {
		this.bossAcc = bossAcc;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getTowmName() {
		return towmName;
	}

	public void setTowmName(String towmName) {
		this.towmName = towmName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
