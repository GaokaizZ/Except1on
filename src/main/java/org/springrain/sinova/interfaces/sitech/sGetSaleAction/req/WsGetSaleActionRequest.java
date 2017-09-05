package org.springrain.sinova.interfaces.sitech.sGetSaleAction.req;

/**
 * 登陆后查活动
 * @author lida
 *
 */
public class WsGetSaleActionRequest {
	public String phoneNo;//手机号 非空
	public String idNo;//用户标识非空
	public String brandID;//用户品牌非空
	public String regionCode;//业务区编码非空
	public String districtCode;//区县编码非空
	public String loginNo;//操作员工号非空
	public String channelType;//渠道编码非空
	public String groupID;//营业厅编码非空
	public String opCode;//业务功能代码
	public String flag;//营销推荐标识非空
	public String redFlag;
	public String blackFlag;
	public String custFlag;//客户归属标识
	public String mainServType;//主体服务类型
	public String mainProdID;//主套餐编码
	public String actClass;//活动类型编码
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getBrandID() {
		return brandID;
	}
	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getLoginNo() {
		return loginNo;
	}
	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRedFlag() {
		return redFlag;
	}
	public void setRedFlag(String redFlag) {
		this.redFlag = redFlag;
	}
	public String getBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(String blackFlag) {
		this.blackFlag = blackFlag;
	}
	public String getCustFlag() {
		return custFlag;
	}
	public void setCustFlag(String custFlag) {
		this.custFlag = custFlag;
	}
	public String getMainServType() {
		return mainServType;
	}
	public void setMainServType(String mainServType) {
		this.mainServType = mainServType;
	}
	public String getMainProdID() {
		return mainProdID;
	}
	public void setMainProdID(String mainProdID) {
		this.mainProdID = mainProdID;
	}
	public String getActClass() {
		return actClass;
	}
	public void setActClass(String actClass) {
		this.actClass = actClass;
	}
}
