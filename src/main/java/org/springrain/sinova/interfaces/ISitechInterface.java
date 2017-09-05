package org.springrain.sinova.interfaces;

import org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo.WsGetLotteryRequest;
import org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo.WsGetLotteryResponse;
//import org.springrain.sinova.interfaces.sitech.s4000cfmb.New_S4000Cfm_BReruest;
import org.springrain.sinova.interfaces.sitech.s4000cfmb.S4000Cfm_BReruest;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.S4035IntCfmRequest;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.S4035IntCfmResponse;
import org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.S4035IntChkResponse;
import org.springrain.sinova.interfaces.sitech.s4035IntChk.req.S4035IntChkRequest;
import org.springrain.sinova.interfaces.sitech.sGetAction.MarketingActionResponse;
import org.springrain.sinova.interfaces.sitech.sGetAction.WsGetActionRequest;
import org.springrain.sinova.interfaces.sitech.sGetSaleAction.rep.WsGetSaleActionResponse;
import org.springrain.sinova.interfaces.sitech.sGetSaleAction.req.WsGetSaleActionRequest;
import org.springrain.sinova.interfaces.sitech.sgrouptree.rep.SGroupTreeResponse;
import org.springrain.sinova.interfaces.sitech.sloginno.rep.SLoginNoQryResponse;
import org.springrain.sinova.interfaces.sitech.squserbase.SQUserBaseResponse;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.WsGetSaleMeansContentReponse;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.req.WsGetSaleMeansContentRequest;

public interface ISitechInterface {
	
	/**
	 * 业务办理
	 * @param request
	 * @return
	 */
	String s4000cfm(S4000Cfm_BReruest request);
	
	
	/**
	 * 业务办理新的接口
	 * @param request
	 * @return
	 */
	//String new_s4000cfm(New_S4000Cfm_BReruest request);
	
	
	/**
	 * 下发短信
	 * @param phoneNo
	 * @param content
	 * @return
	 */
	String smsSendInfo(String phoneNo, String content);
	
	/**
	 * 获取用户信息
	 * @param phoneNo
	 * @return
	 */
	String sQUserBase(String phoneNo);
	
	/**
	 * 是否山西用户
	 * @param phoneNo
	 * @return
	 */
	String isSXUser(String phoneNo);
	
	/**
	 * BOSS工号信息查询
	 * @param bossNo
	 * @return
	 */
	SLoginNoQryResponse searchOffice(String bossNo);
	
	/**
	 * 组织机构查询服务
	 * @param loginNo
	 * @param groupId
	 * @return
	 */
	SGroupTreeResponse searchOfficeDetail(String loginNo, String groupId);

	/**
	 * 抽奖接口
	 * @param S4035IntCfmRequest request
	 * @return
	 */
	S4035IntCfmResponse s4035intCfm(S4035IntCfmRequest request);
	
	/**
	 * 中奖信息查询接口
	 * @param WsGetLotteryRequest request
	 * @return
	 */
	WsGetLotteryResponse getLottery(WsGetLotteryRequest request);
	
	/**
	 * s4035用户参加活动权限校验接口
	 * 王玉军
	 * @param request
	 * @param meansName
	 * @return
	 */
	S4035IntChkResponse  s4035intChk(S4035IntChkRequest request,String meansName);
	
	
	/**
	 * 王玉军
	 * 查询用户信息 
	 * @param phoneNo
	 * @return
	 */
	SQUserBaseResponse queryUserBaseResponse(String phoneNo);
	
	/** 
	 * 查询营销活动
	 * @param request
	 * @return
	 */
	MarketingActionResponse queryWsGetAction(WsGetActionRequest request);
	
	/**
	 * 查询档次信息
	 * @param request
	 * @return
	 */
	WsGetSaleMeansContentReponse queryWsGetSaleMeansContent(WsGetSaleMeansContentRequest request);
	
	/**
	 * 登陆后查询营销活动
	 * @param request
	 * @return
	 */
	WsGetSaleActionResponse queryWsGetSaleAction(WsGetSaleActionRequest request);
}
