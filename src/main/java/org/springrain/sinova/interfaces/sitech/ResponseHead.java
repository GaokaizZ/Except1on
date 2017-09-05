package org.springrain.sinova.interfaces.sitech;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "ROOT")
public class ResponseHead {
	
	@XStreamAlias(value = "RETURN_CODE")
	private String returnCode;	//操作代码 0成功其它失败
	
	@XStreamAlias(value = "RETURN_MSG")
	private String returnMsg;	//成功或者失败
	
	@XStreamAlias(value = "USER_MSG")
	private String userMsg;
	
	@XStreamAlias(value = "DETAIL_MSG")
	private String detailMsg;	//操作失败原因
	
	@XStreamAlias(value = "PROMPT_MSG")
	private String promptMsg;
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}
	public String getDetailMsg() {
		return detailMsg;
	}
	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	public String getPromptMsg() {
		return promptMsg;
	}
	public void setPromptMsg(String promptMsg) {
		this.promptMsg = promptMsg;
	}
	
}
