package org.springrain.sinova.interfaces.isoftstone.checkTokenCode.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "response")
public class CheckTokenCodeResponse {

	@XStreamAlias(value = "retCode")
	private String retCode;
	@XStreamAlias(value = "retMsg")
	private String retMsg;// 返回信息描述

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

}
