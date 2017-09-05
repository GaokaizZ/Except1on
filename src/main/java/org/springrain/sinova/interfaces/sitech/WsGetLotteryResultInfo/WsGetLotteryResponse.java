package org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo;

import java.util.List;

import org.springrain.sinova.interfaces.sitech.ResponseHead;

public class WsGetLotteryResponse extends ResponseHead{
	private String countLot;
	private List<LotInfo> lotList;
	private String inXml;
	private String outXml;
	public String getCountLot() {
		return countLot;
	}
	public void setCountLot(String countLot) {
		this.countLot = countLot;
	}
	public List<LotInfo> getLotList() {
		return lotList;
	}
	public void setLotList(List<LotInfo> lotList) {
		this.lotList = lotList;
	}
	public String getInXml() {
		return inXml;
	}
	public void setInXml(String inXml) {
		this.inXml = inXml;
	}
	public String getOutXml() {
		return outXml;
	}
	public void setOutXml(String outXml) {
		this.outXml = outXml;
	}
	
}
