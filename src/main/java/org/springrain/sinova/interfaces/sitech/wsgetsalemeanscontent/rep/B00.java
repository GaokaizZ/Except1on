package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep;

import org.springrain.sinova.interfaces.sitech.ResponseHead;

public class B00 extends ResponseHead{
	private String returnCode; //操作代码 0成功其它失败
	private String returnMsg;  //成功或者失败
	private String detailMsg;  //操作失败原因
	private String actionId;   //活动ID
	private String actionName; //活动名称
	private String actionDesc;//活动描述
	private String actionDate;//活动起止日期
	private String sendStartTime; //短信发送开始时间
	private String sendEndTime;//短信发送截至时间
	private String mktDiction;// 营销用语
	private String provideType;//配送方式
	private String meansId;//营销方式ID
	private String meansName;//营销方式名称
	private String smsMsg;//回复代码
	private String sendFlag;//
	
}