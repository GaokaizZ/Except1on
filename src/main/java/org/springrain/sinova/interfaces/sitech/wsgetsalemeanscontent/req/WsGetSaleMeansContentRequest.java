package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.req;

/**
 * 活动查询档次对象
 * 
 * @author frank
 * 
 */
public class WsGetSaleMeansContentRequest {
	private String falg;// 登录前：Y,登录后：N 营销推荐标识（一体化合约机传Z） 非空
	private String idNo;// 用户标识 （可为空）
	private String phoneNo;// 服务号码 （未登录传空）
	private String regionCode;// 业务地区编码
	private String actId; // 活动ID
	private String meansId;// 营销方式ID 档次代码（可为空）
	private String custGroupId;// 客户群编号
	private String brandId;// 用户品牌（未登录传空）
	private String resourceModel;// 终端型号
	private String channelType; // 渠道编码（未登录写死 '5'）
	private String provinceCode; // 地市编码 写死（10011）
	private String mobileMarket;
	private String loginNo;
	private String checkFlag;
	private String actionFlag;
	private String selectActionList;

	// 合约 一体化添加
	private String districtCode; // 区县编码 必传
	private String backCode; // 短信回复代码
	private String actPrice; // 营销包价格 必须传
	private String buyoutPrice; // 社会渠道买断价格 必须传
	private String onlyShowMenasFlag; // 是否只展示档次（此处传值Y）
	private String gropId; // 营业厅编码 必须传
	private String actQueryFlag; // 商城传1

	// ///--终端活动分地市添加------------///////////////////////////
	private String resourceSerialNo; // 资源流水号
	private String resourceRegionCod; // 区域编码

	public String getResourceSerialNo() {
		return resourceSerialNo;
	}

	public void setResourceSerialNo(String resourceSerialNo) {
		this.resourceSerialNo = resourceSerialNo;
	}

	public String getResourceRegionCod() {
		return resourceRegionCod;
	}

	public void setResourceRegionCod(String resourceRegionCod) {
		this.resourceRegionCod = resourceRegionCod;
	}

	public String getActQueryFlag() {
		return actQueryFlag;
	}

	public void setActQueryFlag(String actQueryFlag) {
		this.actQueryFlag = actQueryFlag;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String getSelectActionList() {
		return selectActionList;
	}

	public void setSelectActionList(String selectActionList) {
		this.selectActionList = selectActionList;
	}

	public String getMobileMarket() {
		return mobileMarket;
	}

	public void setMobileMarket(String mobileMarket) {
		this.mobileMarket = mobileMarket;
	}

	public WsGetSaleMeansContentRequest() {
		super();
	}

	public WsGetSaleMeansContentRequest(String falg, String idNo, String phoneNo, String regionCode, String actId, String meansId, String custGroupId, String brandId, String resourceModel,
			String channelType, String provinceCode, String mobileMarket, String loginNo, String checkFlag, String actionFlag, String selectActionList) {
		super();
		this.falg = falg;
		this.idNo = idNo;
		this.phoneNo = phoneNo;
		this.regionCode = regionCode;
		this.actId = actId;
		this.meansId = meansId;
		this.custGroupId = custGroupId;
		this.brandId = brandId;
		this.resourceModel = resourceModel;
		this.channelType = channelType;
		this.provinceCode = provinceCode;
		this.mobileMarket = mobileMarket;
		this.loginNo = loginNo;
		this.checkFlag = checkFlag;
		this.actionFlag = actionFlag;
		this.selectActionList = selectActionList;
	}

	public String getFalg() {
		return falg;
	}

	public void setFalg(String falg) {
		this.falg = falg;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
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

	public String getCustGroupId() {
		return custGroupId;
	}

	public void setCustGroupId(String custGroupId) {
		this.custGroupId = custGroupId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getResourceModel() {
		return resourceModel;
	}

	public void setResourceModel(String resourceModel) {
		this.resourceModel = resourceModel;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}

	public String getActPrice() {
		return actPrice;
	}

	public void setActPrice(String actPrice) {
		this.actPrice = actPrice;
	}

	public String getBuyoutPrice() {
		return buyoutPrice;
	}

	public void setBuyoutPrice(String buyoutPrice) {
		this.buyoutPrice = buyoutPrice;
	}

	public String getOnlyShowMenasFlag() {
		return onlyShowMenasFlag;
	}

	public void setOnlyShowMenasFlag(String onlyShowMenasFlag) {
		this.onlyShowMenasFlag = onlyShowMenasFlag;
	}

	public String getGropId() {
		return gropId;
	}

	public void setGropId(String gropId) {
		this.gropId = gropId;
	}

}
