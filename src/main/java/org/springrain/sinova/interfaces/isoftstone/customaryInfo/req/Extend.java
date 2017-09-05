package org.springrain.sinova.interfaces.isoftstone.customaryInfo.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Extend {

	@XStreamAlias(value = "businessInfo")
	private String businessInfo;

	public String getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}

}
