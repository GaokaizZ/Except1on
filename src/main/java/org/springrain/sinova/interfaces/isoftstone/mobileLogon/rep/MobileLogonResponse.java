package org.springrain.sinova.interfaces.isoftstone.mobileLogon.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 移动端登录认证接口Respinse
 * @author frank
 *
 */
@XStreamAlias(value = "response")
public class MobileLogonResponse {

	/*<!—
	认证返回码信息如下：
	01—	认证通过，短信网关正常，可以下发验证码
	02—	认证通过，短信网关关闭，无法下发验证码,不需要下发短信无影响
	03—	主账号密码错误
	04—	手机imei认证错误
	05—	主账号暂挂
	06—	主账号密码过期
	07—	主账号不存在
	08—	用户的入参信息不全
	09—	对应的厂商和渠道不允许访问
	10—	从账号信息不存在
	11—	从账号暂挂
	12—	其他
	-->*/
	@XStreamAlias(value = "retCode")
	private String retCode;	
	@XStreamAlias(value = "retMsg")
	private String retMsg;//返回信息描述
	
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
