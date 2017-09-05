package org.springrain.sinova.interfaces.sitech.sgrouptree.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BusiInfo {

	@XStreamAlias(value = "GROUP_ID")
	private String groupId;
	@XStreamAlias(value = "PARENT_GROUP_ID")
	private String parentGroupId;
	@XStreamAlias(value = "DENORM_LEVEL")
	private String denormLevel;
	@XStreamAlias(value = "PARENT_LEVEL")
	private String parentLevel;
	@XStreamAlias(value = "CURRENT_LEVEL")
	private String currentLevel;
	@XStreamAlias(value = "GROUP_NAME")
	private String groupName;
	@XStreamAlias(value = "ROOT_DISTANCE")
	private String rootDistance;
	@XStreamAlias(value = "HAS_CHILD")
	private String hasChild;
	@XStreamAlias(value = "QUERY_INDEX")
	private String queryIndex;
	@XStreamAlias(value = "BUREAU_CODE")
	private String bureauCode;
	@XStreamAlias(value = "BOSS_ORG_CODE")
	private String bossOrgCode;
	@XStreamAlias(value = "FIRST_CLASS_CODE")
	private String firstClassCode;
	@XStreamAlias(value = "CLASS_CODE")
	private String classCode;
	@XStreamAlias(value = "IS_ACTIVE")
	private String isActive;
	@XStreamAlias(value = "CITY_GRADE_CODE")
	private String cityGradeCode;
	@XStreamAlias(value = "CREATE_TIME")
	private String createTime;
	@XStreamAlias(value = "GRADE_CODE")
	private String gradeCode;
	@XStreamAlias(value = "DESCRIBE")
	private String describe;
	@XStreamAlias(value = "CREDIT")
	private String credit;
	@XStreamAlias(value = "BAIL")
	private String bail;
	@XStreamAlias(value = "BUSINESS_HOURS")
	private String busionessHours;
	@XStreamAlias(value = "OPEN_DATE")
	private String openDate;
	@XStreamAlias(value = "PHONE")
	private String phone;
	@XStreamAlias(value = "FAX")
	private String fax;
	@XStreamAlias(value = "LAYER_CODE")
	private String layerCode;
	@XStreamAlias(value = "MAP")
	private String map;
	@XStreamAlias(value = "ACTIVE_TIME")
	private String activeTime;
	@XStreamAlias(value = "INVALID_TIME")
	private String invalidTime;
	@XStreamAlias(value = "AUDIT_FLAG")
	private String auditFlag;
	@XStreamAlias(value = "AUDIT_STATUS")
	private String auditStatus;
	@XStreamAlias(value = "AUDIT_TIME")
	private String auditTime;
	@XStreamAlias(value = "ERP_CODE")
	private String erpCode;
	@XStreamAlias(value = "REGION_ID")
	private String regionId;
	@XStreamAlias(value = "LOGIN_PREFIX")
	private String loginPrefix;
	@XStreamAlias(value = "P_REGION_NAME")
	private String pRegionName;
	@XStreamAlias(value = "TOWN_ADDRESS")
	private String townAddress;
	@XStreamAlias(value = "SERVICE_CONTENT")
	private String serviceContent;
	@XStreamAlias(value = "CREATE_LOGIN")
	private String createLogin;
	@XStreamAlias(value = "TWO_DIMENSIONAL_CODE")
	private String towDimensionalCode;
	@XStreamAlias(value = "GIVEOUT_FLAG")
	private String giveoutFlag;
	@XStreamAlias(value = "AREA_CODE")
	private String areaCode;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getDenormLevel() {
		return denormLevel;
	}

	public void setDenormLevel(String denormLevel) {
		this.denormLevel = denormLevel;
	}

	public String getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}

	public String getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRootDistance() {
		return rootDistance;
	}

	public void setRootDistance(String rootDistance) {
		this.rootDistance = rootDistance;
	}

	public String getHasChild() {
		return hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	public String getQueryIndex() {
		return queryIndex;
	}

	public void setQueryIndex(String queryIndex) {
		this.queryIndex = queryIndex;
	}

	public String getBureauCode() {
		return bureauCode;
	}

	public void setBureauCode(String bureauCode) {
		this.bureauCode = bureauCode;
	}

	public String getBossOrgCode() {
		return bossOrgCode;
	}

	public void setBossOrgCode(String bossOrgCode) {
		this.bossOrgCode = bossOrgCode;
	}

	public String getFirstClassCode() {
		return firstClassCode;
	}

	public void setFirstClassCode(String firstClassCode) {
		this.firstClassCode = firstClassCode;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCityGradeCode() {
		return cityGradeCode;
	}

	public void setCityGradeCode(String cityGradeCode) {
		this.cityGradeCode = cityGradeCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getBail() {
		return bail;
	}

	public void setBail(String bail) {
		this.bail = bail;
	}

	public String getBusionessHours() {
		return busionessHours;
	}

	public void setBusionessHours(String busionessHours) {
		this.busionessHours = busionessHours;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLayerCode() {
		return layerCode;
	}

	public void setLayerCode(String layerCode) {
		this.layerCode = layerCode;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getLoginPrefix() {
		return loginPrefix;
	}

	public void setLoginPrefix(String loginPrefix) {
		this.loginPrefix = loginPrefix;
	}

	public String getpRegionName() {
		return pRegionName;
	}

	public void setpRegionName(String pRegionName) {
		this.pRegionName = pRegionName;
	}

	public String getTownAddress() {
		return townAddress;
	}

	public void setTownAddress(String townAddress) {
		this.townAddress = townAddress;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public String getCreateLogin() {
		return createLogin;
	}

	public void setCreateLogin(String createLogin) {
		this.createLogin = createLogin;
	}

	public String getTowDimensionalCode() {
		return towDimensionalCode;
	}

	public void setTowDimensionalCode(String towDimensionalCode) {
		this.towDimensionalCode = towDimensionalCode;
	}

	public String getGiveoutFlag() {
		return giveoutFlag;
	}

	public void setGiveoutFlag(String giveoutFlag) {
		this.giveoutFlag = giveoutFlag;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
