package org.springrain.sinova.interfaces.sitech.sloginno.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "ROOT")
public class SLoginNoQryRequest {

	@XStreamAlias(value = "REQUEST_INFO")
	private RequestInfo requestInfo = new RequestInfo(); 

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

}
