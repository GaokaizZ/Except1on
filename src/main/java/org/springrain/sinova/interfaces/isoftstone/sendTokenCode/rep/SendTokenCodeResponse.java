package org.springrain.sinova.interfaces.isoftstone.sendTokenCode.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "response")
public class SendTokenCodeResponse {

	/*<!--
	认证返回码信息如下：
	01—	验证码下发成功
	02—	用户的入参信息不全
	03—	对应的厂商和渠道不允许访问
	04—	短信下发模板值不存在
	05—	短信网关关闭，无法下发验证码
	06—	主账号不存在
	07—	其他
	-->*/

	
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
