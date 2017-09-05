package org.springrain.sinova.interfaces.sitech.s4035IntCfm;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录活动办理
 * @author frank
 *
 */
public class S4035IntCfmRequest {
	private String serviceNo;//服务号码
	private String masterServId;//用户主体服务类型
	private String provinceGroup;//省份标志，山西10011,非空
	private String loginNo;//工号，非空
	private String phoneNo;//用户信息，非空
	private String actId;//活动id，非空
	private String meansId;//档次id，非空
	private String contactId;//接触id，可空，但是此几点必须有
	private String channelType;//渠道，3是网厅，5是短厅，0和1是实体营业厅，码表是mk_chntype_dict，非空
	private String custAdvince;//用户意见信息，可空
	private String recommendNo;//推荐号码，可空，现在他人的信息都在tainfo_list中，此节点现在不用
	private List<TaInfo> taInfoList =new ArrayList<TaInfo>();//输入他人信息,此节点可空
	private String toGroupId;//OCP激活中串码流转营业厅的GROUP_ID,OCP激活不能为空，其他的可空
	private String serialNo;//OCP激活中串码预占时对应的流水号（mk_resturn_info）,OCP激活不能为空，其他的可空
	private String joinTermiFlag;//区分终端激活,终端激活中需要传入此几点不能为空，其他的可以没有此节点或为空
	private String preActType;//PRE_ACT_TYPE = 00 (普通活动)/PRE_ACT_TYPE = 01 (终端合约分离)/PRE_ACT_TYPE = 02PAD客户端
	private List<PrcInfo> prcInfoList =new ArrayList<PrcInfo>();
	private List<ResourceInfo>  resourceInfo=new ArrayList<ResourceInfo>();//可空（终端激活才支持这个元素不能为空）
	private String servNo;
	private String imeiNo;
	private String openTime;
	private String outOrderId;
	
	
	
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getImeiNo() {
		return imeiNo;
	}
	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}
	private String transferSNo;//TRANSFER_SERIALNO
	
	private String condclass;//CONDCLASS_0178
	
	private String scoreMoney;
	
	private String index;
	
	
	
	
	
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getScoreMoney() {
		return scoreMoney;
	}
	public void setScoreMoney(String scoreMoney) {
		this.scoreMoney = scoreMoney;
	}
	public String getCondclass() {
		return condclass;
	}
	public void setCondclass(String condclass) {
		this.condclass = condclass;
	}
	public String getTransferSNo() {
		return transferSNo;
	}
	public void setTransferSNo(String transferSNo) {
		this.transferSNo = transferSNo;
	}
	public String getServNo() {
		return servNo;
	}
	public void setServNo(String servNo) {
		this.servNo = servNo;
	}
	public List<PrcInfo> getPrcInfoList() {
		return prcInfoList;
	}
	public void setPrcInfoList(List<PrcInfo> prcInfoList) {
		this.prcInfoList = prcInfoList;
	}
	public List<ResourceInfo> getResourceInfo() {
		return resourceInfo;
	}
	public void setResourceInfo(List<ResourceInfo> resourceInfo) {
		this.resourceInfo = resourceInfo;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getMasterServId() {
		return masterServId;
	}
	public void setMasterServId(String masterServId) {
		this.masterServId = masterServId;
	}
	public String getProvinceGroup() {
		return provinceGroup;
	}
	public void setProvinceGroup(String provinceGroup) {
		this.provinceGroup = provinceGroup;
	}
	public String getLoginNo() {
		return loginNo;
	}
	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getMeansId() {
		return meansId;
	}
	public void setMeansId(String meansId) {
		this.meansId = meansId;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getCustAdvince() {
		return custAdvince;
	}
	public void setCustAdvince(String custAdvince) {
		this.custAdvince = custAdvince;
	}
	public String getRecommendNo() {
		return recommendNo;
	}
	public void setRecommendNo(String recommendNo) {
		this.recommendNo = recommendNo;
	}
	public List<TaInfo> getTaInfoList() {
		return taInfoList;
	}
	public void setTaInfoList(List<TaInfo> taInfoList) {
		this.taInfoList = taInfoList;
	}
	public String getToGroupId() {
		return toGroupId;
	}
	public void setToGroupId(String toGroupId) {
		this.toGroupId = toGroupId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getJoinTermiFlag() {
		return joinTermiFlag;
	}
	public void setJoinTermiFlag(String joinTermiFlag) {
		this.joinTermiFlag = joinTermiFlag;
	}
	public String getPreActType() {
		return preActType;
	}
	public void setPreActType(String preActType) {
		this.preActType = preActType;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	
}
