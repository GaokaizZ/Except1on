package org.springrain.sinova.interfaces.sitech.s4035IntCfm;

import java.util.Map;

public class MeanAllResponse {
	private Map<String,LimitInfo> limitInfo;
	private String meanAllFlag;

	public Map<String, LimitInfo> getLimitInfo() {
		return limitInfo;
	}

	public void setLimitInfo(Map<String, LimitInfo> limitInfo) {
		this.limitInfo = limitInfo;
	}

	public String getMeanAllFlag() {
		return meanAllFlag;
	}

	public void setMeanAllFlag(String meanAllFlag) {
		this.meanAllFlag = meanAllFlag;
	}
}
