package org.springrain.sinova.interfaces.sitech.s4035IntChk.rep;

import java.util.List;


/**
 * 
 * 2015-4-8
 * @author 王玉军
 *
 */
public class MeanAll {
	private List<LimitInfo> limitInfo;
	private String meanAllFlag;

	public MeanAll() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MeanAll(List<LimitInfo> limitInfo, String meanAllFlag) {
		super();
		this.limitInfo = limitInfo;
		this.meanAllFlag = meanAllFlag;
	}

	public List<LimitInfo> getLimitInfo() {
		return limitInfo;
	}

	public void setLimitInfo(List<LimitInfo> limitInfo) {
		this.limitInfo = limitInfo;
	}

	public String getMeanAllFlag() {
		return meanAllFlag;
	}

	public void setMeanAllFlag(String meanAllFlag) {
		this.meanAllFlag = meanAllFlag;
	}
}
