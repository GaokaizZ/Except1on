package org.springrain.sinova.interfaces.isoftstone.checkTokenCode.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "extend")
public class Extend {
	
	@XStreamAlias(value = "businessInfo")
	private String businessInfo;
	

	public Extend(String businessInfo) {
		super();
		this.businessInfo = businessInfo;
	}

	public String getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}
	
	
}
