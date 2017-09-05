package org.springrain.sinova.interfaces.sitech.sGetAction;
/**
 * 未登录查询活动
 */
public class WsGetActionRequest {
	private String flag;
	private String actID;
	private String chnType;
	private String startDate;//活动开始时间
	private String endDate;//活动结束时间
	private String regionCode;//地市编码
	private String actClass;//活动类型
	private String custGruopType;//目标客户群类型
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getActID() {
		return actID;
	}
	public void setActID(String actID) {
		this.actID = actID;
	}
	public String getChnType() {
		return chnType;
	}
	public void setChnType(String chnType) {
		this.chnType = chnType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getActClass() {
		return actClass;
	}
	public void setActClass(String actClass) {
		this.actClass = actClass;
	}
	public String getCustGruopType() {
		return custGruopType;
	}
	public void setCustGruopType(String custGruopType) {
		this.custGruopType = custGruopType;
	}
	
	
}
