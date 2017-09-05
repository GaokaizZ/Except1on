package org.springrain.sinova.interfaces.sitech.s4035IntChk.rep;

import java.util.List;



public class MeanOne {
	private List<LimitInfo> limitInfo;
	private String meanOneFlag;

	public List<LimitInfo> getLimitInfo() {
		return limitInfo;
	}

	public void setLimitInfo(List<LimitInfo> listOne) {
		this.limitInfo = listOne;
	}

	public String getMeanOneFlag() {
		return meanOneFlag;
	}

	public void setMeanOneFlag(String meanOneFlag) {
		this.meanOneFlag = meanOneFlag;
	}

	public MeanOne(List<LimitInfo> limitInfo, String meanOneFlag) {
		super();
		this.limitInfo = limitInfo;
		this.meanOneFlag = meanOneFlag;
	}

	public MeanOne() {
		super();
		// TODO Auto-generated constructor stub
	}

}