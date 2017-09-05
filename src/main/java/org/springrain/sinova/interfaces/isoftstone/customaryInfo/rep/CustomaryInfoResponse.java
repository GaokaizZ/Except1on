package org.springrain.sinova.interfaces.isoftstone.customaryInfo.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "response")
public class CustomaryInfoResponse {

	@XStreamAlias(value = "retCode")
	private String retCode;
	@XStreamAlias(value = "retMsg")
	private RetMsg retMsg = new RetMsg();

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public RetMsg getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(RetMsg retMsg) {
		this.retMsg = retMsg;
	}

}
