package org.springrain.sinova.interfaces.sitech.sgrouptree.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SGroupTreeRequest {

	@XStreamAlias(value = "REQUEST_INFO")
	private RequestInfo requestInfo = new RequestInfo();

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}
}
