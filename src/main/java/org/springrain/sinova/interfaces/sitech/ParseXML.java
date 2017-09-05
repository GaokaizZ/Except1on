package org.springrain.sinova.interfaces.sitech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo.LotInfo;
import org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo.WsGetLotteryRequest;
import org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo.WsGetLotteryResponse;
import org.springrain.sinova.interfaces.sitech.s4000cfmb.S4000Cfm_BReruest;
import org.springrain.sinova.interfaces.sitech.s4000cfmb.S4000Cfm_BResponse;
import org.springrain.sinova.interfaces.sitech.s4000cfmb.SubProdPrc;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.LimitInfo;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.LuckyawardInfo;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.MeanAllResponse;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.S4035IntCfmRequest;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.S4035IntCfmResponse;
import org.springrain.sinova.interfaces.sitech.s4035IntCfm.TaInfo;
import org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.MeanOne;
import org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.S4035IntChkResponse;
import org.springrain.sinova.interfaces.sitech.s4035IntChk.req.S4035IntChkRequest;
import org.springrain.sinova.interfaces.sitech.sGetAction.MarketingActionResponse;
import org.springrain.sinova.interfaces.sitech.sGetAction.MarkingActionInfo;
import org.springrain.sinova.interfaces.sitech.sGetAction.TermiInfo;
import org.springrain.sinova.interfaces.sitech.sGetAction.WsGetActionRequest;
import org.springrain.sinova.interfaces.sitech.sGetSaleAction.rep.SaleActionLogin;
import org.springrain.sinova.interfaces.sitech.sGetSaleAction.rep.WsGetSaleActionResponse;
import org.springrain.sinova.interfaces.sitech.sGetSaleAction.req.WsGetSaleActionRequest;
import org.springrain.sinova.interfaces.sitech.sgrouptree.rep.SGroupTreeResponse;
import org.springrain.sinova.interfaces.sitech.sgrouptree.req.SGroupTreeRequest;
import org.springrain.sinova.interfaces.sitech.sloginno.rep.SLoginNoQryResponse;
import org.springrain.sinova.interfaces.sitech.sloginno.req.SLoginNoQryRequest;
import org.springrain.sinova.interfaces.sitech.smssendinfo.SmsSendInfoResponse;
import org.springrain.sinova.interfaces.sitech.squserbase.SQUserBaseRequest;
import org.springrain.sinova.interfaces.sitech.squserbase.SQUserBaseResponse;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.A00;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.A02;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.A06;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.A07;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.A10;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.A11;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.B02;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.B10;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.MeanContent;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.MonthReturn;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep.WsGetSaleMeansContentReponse;
import org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.req.WsGetSaleMeansContentRequest;
import org.springrain.sinova.util.Util;
import org.springrain.system.util.Constant;




 

public class ParseXML {
	private static final Logger logger = LoggerFactory
			.getLogger(ParseXML.class);
	private static final String XMLHEAD_U = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
	private static final String XMLHEAD_G = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>";
	private static final String XMLSTART = "<ROOT>";
	private static final String XMLEND = "</ROOT>";
	private static final String ENTER = "\n";

	
	
	/**
	 * 用户订购、修改或退订产品 入参拼接
	 * 
	 * @param request
	 * @return
	 */
	public static String reruestXMLS4000Cfm_B(S4000Cfm_BReruest request) {
		logger.info("reruestXMLS4000Cfm_B  requestxml   start");
		String xml = "";
		StringBuffer sb = new StringBuffer();
		sb.append(XMLHEAD_U).append(ENTER);
		sb.append(XMLSTART).append(ENTER);

		sb.append("<LOGIN_NO type=\"string\">")
				.append(request.getLoginNO() == null ? "" : request
						.getLoginNO()).append("</LOGIN_NO>").append(ENTER);
		sb.append("<LOGIN_PWD type=\"string\">")
				.append(request.getLoginPwd() == null ? "" : request
						.getLoginPwd()).append("</LOGIN_PWD>").append(ENTER);
		sb.append("<SERVICE_NO type=\"string\">")
				.append(request.getServiceNo() == null ? "" : request
						.getServiceNo()).append("</SERVICE_NO>").append(ENTER);
		sb.append("<MASTER_SERV_ID type=\"string\">")
				.append(request.getMasterServId() == null ? "" : request
						.getMasterServId()).append("</MASTER_SERV_ID>")
				.append(ENTER);
		sb.append("<RE_CONFIRM type=\"string\">")
				.append(request.getReConfirm() == null ? "" : request
						.getReConfirm()).append("</RE_CONFIRM>").append(ENTER);
		sb.append("<OP_CODE type=\"string\">")
				.append(request.getOpCode() == null ? "" : request.getOpCode())
				.append("</OP_CODE>").append(ENTER);
		sb.append("<COMMON_INFO>").append(ENTER);
		sb.append("<PROVINCE_GROUP type=\"string\">")
				.append(request.getProvinceGroup() == null ? "" : request
						.getProvinceGroup()).append("</PROVINCE_GROUP>")
				.append(ENTER);
		sb.append("</COMMON_INFO>").append(ENTER);
		if (request.getBusiInfo() != null) {
			sb.append("<BUSI_INFO>").append(ENTER);
			sb.append("<OPERATE_TYPE type=\"string\">")
					.append(request.getBusiInfo().getOperateType() == null ? ""
							: request.getBusiInfo().getOperateType())
					.append("</OPERATE_TYPE>").append(ENTER);
			sb.append("<PACKGE_PRCID type=\"string\">")
					.append(request.getBusiInfo().getPackgePricId() == null ? ""
							: request.getBusiInfo().getPackgePricId())
					.append("</PACKGE_PRCID>").append(ENTER);
			sb.append("<FORMER_PRCID type=\"string\">")
					.append(request.getBusiInfo().getFormerPrcId() == null ? ""
							: request.getBusiInfo().getFormerPrcId())
					.append("</FORMER_PRCID>").append(ENTER);
			sb.append("<PROD_ID type=\"string\">")
					.append(request.getBusiInfo().getProdId() == null ? ""
							: request.getBusiInfo().getProdId())
					.append("</PROD_ID>").append(ENTER);
			sb.append("<PROD_PRCID type=\"string\">")
					.append(request.getBusiInfo().getProdPrcId() == null ? ""
							: request.getBusiInfo().getProdPrcId())
					.append("</PROD_PRCID>").append(ENTER);
			sb.append("<EFF_DATE type=\"string\">")
					.append(request.getBusiInfo().getEffDate() == null ? ""
							: request.getBusiInfo().getEffDate())
					.append("</EFF_DATE>").append(ENTER);
			sb.append("<EXP_DATE type=\"string\">")
					.append(request.getBusiInfo().getExpDate() == null ? ""
							: request.getBusiInfo().getExpDate())
					.append("</EXP_DATE>").append(ENTER);
			sb.append("<DEALTYPE type=\"string\">")
					.append(request.getBusiInfo().getDealType() == null ? ""
							: request.getBusiInfo().getDealType())
					.append("</DEALTYPE>").append(ENTER);
			// /////////////////////////////////////////////////////////////////////////////

			// /////////预留////////////

			// /////////////////////////////////////////////////////////////////////////////
			if (request.getBusiInfo().getSubProdPrcList() != null) {
				sb.append("<SUB_PRODPRC_LIST>").append(ENTER);
				for (int i = 0; i < request.getBusiInfo().getSubProdPrcList()
						.size(); i++) {
					SubProdPrc subProdPrc = request.getBusiInfo()
							.getSubProdPrcList().get(i);
					sb.append("<SUB_PRODPRC>").append(ENTER);
					sb.append("<OPERATE_TYPE type=\"string\">")
							.append(subProdPrc.getOperateType() == null ? ""
									: subProdPrc.getOperateType())
							.append("</OPERATE_TYPE>").append(ENTER);
					sb.append("<PROD_PRCID type=\"string\">")
							.append(subProdPrc.getProdPrcId() == null ? ""
									: subProdPrc.getProdPrcId())
							.append("</PROD_PRCID>").append(ENTER);
					sb.append("<PROD_ID type=\"string\">")
							.append(subProdPrc.getProdId() == null ? ""
									: subProdPrc.getProdId())
							.append("</PROD_ID>").append(ENTER);
					sb.append("</SUB_PRODPRC>").append(ENTER);
				}
				sb.append("</SUB_PRODPRC_LIST>").append(ENTER);
			}
			sb.append("</BUSI_INFO>").append(ENTER);
		}
		sb.append(XMLEND).append(ENTER);
		xml = sb.toString();
		logger.info("reruestXMLS4000Cfm_B :" + ENTER + xml);
		logger.info("reruestXMLS4000Cfm_B  requestxml   end");
		return xml;

	}

	/**
	 * 用户订购、修改或退订产品 出参封装
	 * 
	 * @param request
	 * @return
	 * @author
	 */
	public static S4000Cfm_BResponse responseXMLS4000Cfm_B(String xml)
			throws DocumentException {
		logger.info("responseXMLS4000Cfm_B start xml:" + xml);

		Document document = DocumentHelper.parseText(xml);

		S4000Cfm_BResponse response = new S4000Cfm_BResponse();
		Element root = document.getRootElement();
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		String RETURN_MSG = root.elementTextTrim("RETURN_MSG");
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");
		if (RETURN_CODE != null && RETURN_CODE.equals("0")) {
			Element OUT_DATA = root.element("OUT_DATA");
			if (OUT_DATA != null) {
				String EFFEXP_MODE = OUT_DATA.elementTextTrim("EFFEXP_MODE");
				String SP_FLAG = OUT_DATA.elementTextTrim("SP_FLAG");
				String LOGIN_ACCEPT = OUT_DATA.elementTextTrim("LOGIN_ACCEPT");
				String EFF_DATE = OUT_DATA.elementTextTrim("EFF_DATE");
				String EXP_DATE = OUT_DATA.elementTextTrim("EXP_DATE");
				String BRAND_ID = OUT_DATA.elementTextTrim("BRAND_ID");
				response.setEffExpMode(EFFEXP_MODE);
				response.setSpFlag(SP_FLAG);
				response.setLoginAccept(LOGIN_ACCEPT);
				response.setEffDate(EFF_DATE);
				response.setExpDate(EXP_DATE);
				response.setBrandId(BRAND_ID);

			}
		}
		response.setDetailMsg(DETAIL_MSG);
		response.setReturnCode(RETURN_CODE);
		response.setReturnMsg(RETURN_MSG);
		logger.info("responseXMLS4000Cfm_B end xml");
		return response;
	}

	/**
	 * 组合短信发送入参
	 * 
	 * @param phoneNo
	 * @param content
	 * @return
	 */
	public static String reruestXMLSendSms(String phoneNo, String content,
			String templateid) {
		logger.info("reruestXMLSendSms start ");
		StringBuffer sms = new StringBuffer();
		sms.append("{\"msg\":\"").append(content).append("\"}");
		String xml = null;
		StringBuffer sb = new StringBuffer();
		sb.append(XMLHEAD_G).append(ENTER);
		sb.append(XMLSTART).append(ENTER);
		sb.append("<SYSID type=\"string\">5</SYSID>").append(ENTER);
		sb.append("<COMEFROM type=\"string\">999</COMEFROM>").append(ENTER);
		sb.append("<SEQ type=\"string\">").append(Util.getUniqueSuffix())
				.append("</SEQ>").append(ENTER);
		sb.append("<TEMPLATEID type=\"string\">").append(templateid)
				.append("</TEMPLATEID>").append(ENTER);
		sb.append("<PARAMS type=\"string\">").append(sms.toString())
				.append("</PARAMS>").append(ENTER);
		sb.append("<SERVICENO type=\"string\"></SERVICENO>").append(ENTER);
		sb.append("<PHONENO type=\"string\">").append(phoneNo)
				.append("</PHONENO>").append(ENTER);
		sb.append("<LOGINNO type=\"string\">sxshop</LOGINNO>").append(ENTER);
		sb.append("<SERVNO type=\"string\"></SERVNO>").append(ENTER);
		sb.append("<SERVNAME type=\"string\"></SERVNAME>").append(ENTER);
		sb.append("<SUBPHONESEQ type=\"string\"></SUBPHONESEQ>").append(ENTER);
		sb.append("<SENDTIME type=\"string\">").append(Util.getSendTime())
				.append("</SENDTIME>").append(ENTER);
		sb.append("<HOLD1 type=\"string\"></HOLD1>").append(ENTER);
		sb.append("<HOLD2 type=\"string\">1000</HOLD2>").append(ENTER);
		sb.append("<HOLD3 type=\"string\"></HOLD3>").append(ENTER);
		sb.append("<HOLD4 type=\"string\"></HOLD4>").append(ENTER);
		sb.append(XMLEND).append(ENTER);
		xml = sb.toString();
		logger.info("reruestXMLSendSms  " + ENTER );
		logger.info("reruestXMLSendSms xml  end");
		return xml;
	}

	/**
	 * 短信出参
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public static SmsSendInfoResponse responseXMLSendSms(String xml)
			throws DocumentException {
		logger.info("responseXMLSendSms start");
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		SmsSendInfoResponse response = new SmsSendInfoResponse();
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		String RETURN_MSG = root.elementTextTrim("RETURN_MSG");
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");
		response.setDetailMsg(DETAIL_MSG);
		response.setReturnCode(RETURN_CODE);
		response.setReturnMsg(RETURN_MSG);
		logger.info("responseXMLSendSms end");
		return response;
	}

	/**
	 * 获取用户信息入参
	 * 
	 * @param phoneNo
	 * 
	 */

	public static String reruestXMLSQUserBase(String phoneNo) {
		logger.info("reruestXMLSQUserBase start");
		SQUserBaseRequest squ = new SQUserBaseRequest();
		squ.setPhoneNo(phoneNo);
		squ.setMasterServId("1001");
		String xml = "";
		StringBuffer sb = new StringBuffer();
		sb.append(XMLHEAD_G).append(ENTER);
		sb.append(XMLSTART).append(ENTER);
		sb.append("<PHONE_NO type='string'>").append(phoneNo)
				.append("</PHONE_NO>").append(ENTER);
		sb.append("<MASTER_SERV_ID type='string'>").append("1001")
				.append("</MASTER_SERV_ID>").append(ENTER);
		sb.append(XMLEND).append(ENTER);
		xml = sb.toString();
		logger.info("reruestXMLSQUserBase xml:" + ENTER + xml);
		logger.info("reruestXMLSQUserBase end");
		return sb.toString();
	}

	/**
	 * 获取用户信息出参
	 * 
	 * @throws DocumentException
	 */
	public static SQUserBaseResponse responseXMLSQUserBase(String xml)
			throws DocumentException {
		logger.info("responseXMLSQUserBase start");
		SQUserBaseResponse response = new SQUserBaseResponse();

		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		String RETURN_MSG = root.elementTextTrim("RETURN_MSG");
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");
		String USER_MSG = root.elementTextTrim("USER_MSG");
		String PROMPT_MSG = root.elementTextTrim("PROMPT_MSG");

		response.setDetailMsg(DETAIL_MSG);
		response.setReturnCode(RETURN_CODE);
		response.setReturnMsg(RETURN_MSG);
		response.setUserMsg(USER_MSG);
		response.setPromptMsg(PROMPT_MSG);

		Element outData = root.element("OUT_DATA");
		String masterServId = outData.elementTextTrim("MASTER_SERV_ID");
		String masterServName = outData.elementTextTrim("MASTER_SERV_NAME");
		String phoneNo = outData.elementTextTrim("PHONE_NO");
		String idNo = outData.elementTextTrim("ID_NO");
		String custId = outData.elementTextTrim("CUST_ID");
		String custName = outData.elementTextTrim("CUST_NAME");
		String runCode = outData.elementTextTrim("RUN_CODE");
		String runCodeName = outData.elementTextTrim("RUN_CODE_NAME");
		String cardType = outData.elementTextTrim("CARD_TYPE");
		String cardTypeName = outData.elementTextTrim("masterServName");
		String groupId = outData.elementTextTrim("GROUP_ID");
		String groupName = outData.elementTextTrim("GROUP_NAME");
		String regionId = outData.elementTextTrim("REGION_ID");
		String regionName = outData.elementTextTrim("REGION_NAME");
		String brindId = outData.elementTextTrim("BRAND_ID");
		String brindName = outData.elementTextTrim("BRAND_NAME");
		String simNO = outData.elementTextTrim("SIM_NO");
		String imsiNo = outData.elementTextTrim("IMSI_NO");
		String idIccid = outData.elementTextTrim("ID_ICCID");
		String idType = outData.elementTextTrim("ID_TYPE");
		String idTypeName = outData.elementTextTrim("ID_TYPE_NAME");
		String contactPhone = outData.elementTextTrim("CONTACT_PHONE");
		String contactName = outData.elementTextTrim("CONTACT_NAME");
		String contactAddress = outData.elementTextTrim("CONTACT_ADDRESS");
		String openTime = outData.elementTextTrim("OPEN_TIME");
		String contractNo = outData.elementTextTrim("CONTRACT_NO");
		String idsStr = outData.elementTextTrim("IDS_STR");

		response.setMasterServId(masterServId);
		response.setMasterServName(masterServName);
		response.setPhoneNo(phoneNo);
		response.setIdNo(idNo);
		response.setCustId(custId);
		response.setCustName(custName);
		response.setRunCode(runCode);
		response.setRunCodeName(runCodeName);
		response.setCardType(cardType);
		response.setCardTypeName(cardTypeName);
		response.setGroupId(groupId);
		response.setGroupName(groupName);
		response.setRegionId(regionId);
		response.setRegionName(regionName);
		response.setBrindId(brindId);
		response.setBrindName(brindName);
		response.setSimNO(simNO);
		response.setImsiNo(imsiNo);
		response.setIdIccid(idIccid);
		response.setIdType(idType);
		response.setIdTypeName(idTypeName);
		response.setContactPhone(contactPhone);
		response.setContactName(contactName);
		response.setContactAddress(contactAddress);
		response.setOpenTime(openTime);
		response.setContractNo(contractNo);
		response.setIdsStr(idsStr);
		logger.info("responseXMLSQUserBase end");
		return response;
	}
	
	/**
	 * BOSS工号信息查询 
	 * @param request
	 * @return
	 */
	public static String reruestXMLSLoginNoQry(SLoginNoQryRequest request) {
		logger.info("reruestXMLSLoginNoQry  requestxml   start");
		StringBuffer sb = new StringBuffer();
		sb.append(XMLHEAD_U).append(ENTER);
		sb.append(XMLSTART).append(ENTER);
		sb.append("<REQUEST_INFO type=\"node\">").append(ENTER);
		org.springrain.sinova.interfaces.sitech.sloginno.req.RequestInfo requestInfo = request.getRequestInfo(); 
		org.springrain.sinova.interfaces.sitech.sloginno.req.OprInfo oprInfo = requestInfo.getOprInfo();
		if(oprInfo != null){
			sb.append("<OPR_INFO type=\"node\">").append(ENTER);
			sb.append("<REGION_ID type=\"int\">").append(oprInfo.getRegionId() == null ? "" : oprInfo.getRegionId()).append("</REGION_ID>").append(ENTER);
			sb.append("<CHANNEL_TYPE type=\"string\">").append(oprInfo.getChannelType() == null ? "" : oprInfo.getChannelType()).append("</CHANNEL_TYPE>").append(ENTER);
			sb.append("<LOGIN_NO type=\"string\">").append(oprInfo.getLoginNo() == null ? "" : oprInfo.getLoginNo()).append("</LOGIN_NO>").append(ENTER);
			sb.append("<LOGIN_PWD type=\"string\">").append(oprInfo.getLoginPwd() == null ? "" : oprInfo.getLoginPwd()).append("</LOGIN_PWD>").append(ENTER);
			sb.append("<IP_ADDRESS type=\"string\">").append(oprInfo.getIpAddress() == null ? "" : oprInfo.getIpAddress()).append("</IP_ADDRESS>").append(ENTER);
			sb.append("<GROUP_ID type=\"string\">").append(oprInfo.getGroupId() == null ? "" : oprInfo.getGroupId()).append("</GROUP_ID>").append(ENTER);
			sb.append("<CONTACT_ID type=\"long\">").append(oprInfo.getContactId() == null ? "" : oprInfo.getContactId()).append("</CONTACT_ID>").append(ENTER);
			sb.append("<OP_CODE type=\"string\">").append(oprInfo.getOpCode() == null ? "" : oprInfo.getOpCode()).append("</OP_CODE>").append(ENTER);
			sb.append("</OPR_INFO>").append(ENTER);
		}
		sb.append("<BUSI_INFO_LIST>").append(ENTER);
		List<org.springrain.sinova.interfaces.sitech.sloginno.req.BusiInfo> busiInfos = request.getRequestInfo().getBusiInfoList();
		for (org.springrain.sinova.interfaces.sitech.sloginno.req.BusiInfo busiInfo : busiInfos) {
			sb.append("<BUSI_INFO type=\"node\">").append(ENTER);
			sb.append("<VALID_FLAG type=\"string\">").append(busiInfo.getValidFlag() == null ? "" : busiInfo.getValidFlag()).append("</VALID_FLAG>").append(ENTER);
			sb.append("<GROUP_ID type=\"string\">").append(busiInfo.getGroupId() == null ? "" : busiInfo.getGroupId()).append("</GROUP_ID>").append(ENTER);
			sb.append("<LOGIN_NO type=\"string\">").append(busiInfo.getLoginNo() == null ? "" : busiInfo.getLoginNo()).append("</LOGIN_NO>").append(ENTER);
			sb.append("<LOGIN_NAME type=\"string\">").append(busiInfo.getLoginName() == null ? "" : busiInfo.getLoginName()).append("</LOGIN_NAME>").append(ENTER);
			sb.append("<PAGE_NUM type=\"int\">").append(busiInfo.getPageNum() == null ? "" : busiInfo.getPageNum()).append("</PAGE_NUM>").append(ENTER);
			sb.append("<PAGE_AMOUNT type=\"int\">").append(busiInfo.getPageAmount() == null ? "" : busiInfo.getPageAmount()).append("</PAGE_AMOUNT>").append(ENTER);
			sb.append("</BUSI_INFO>").append(ENTER);
		}
		sb.append("</BUSI_INFO_LIST>").append(ENTER);
		sb.append("</REQUEST_INFO>").append(ENTER);
		sb.append(XMLEND).append(ENTER);
//		logger.info("reruestXMLSLoginNoQry :" + ENTER + sb.toString());
		logger.info("reruestXMLSLoginNoQry  requestxml   end");
		return sb.toString();
	}
	
	public static SLoginNoQryResponse responseXMLSLoginNoQry(String xml) throws DocumentException {
		logger.info("responseXMLSQUserBase start");
		SLoginNoQryResponse response = new SLoginNoQryResponse();
		Document document = DocumentHelper.parseText(xml);
		
		Element root = document.getRootElement();
		response.setReturnCode(root.elementTextTrim("RETURN_CODE"));
		response.setReturnMsg( root.elementTextTrim("RETURN_MSG"));
		response.setDetailMsg(root.elementTextTrim("DETAIL_MSG"));
		response.setUserMsg(root.elementTextTrim("USER_MSG"));
		response.setPromptMsg(root.elementTextTrim("PROMPT_MSG"));
		
		Element outData = root.element("OUT_DATA");
		response.getOutData().setCountNum(outData.elementTextTrim("COUNT_NUM"));
		response.getOutData().setCountPage(outData.elementTextTrim("COUNT_PAGE"));
		response.getOutData().setPageNum(outData.elementTextTrim("PAGE_NUM"));
		
		if(StringUtils.equals(outData.elementTextTrim("COUNT_NUM"), "0")){
			response.setReturnCode(Constant.ONE_INFO_ERROR);
			response.setReturnMsg( "未查到相关信息【SLoginNoQry】");
		}else{
			Element data = outData.element("DATA");
			response.getOutData().getData().setGroupId(data.elementTextTrim("GROUP_ID"));
			response.getOutData().getData().setGroupName(data.elementTextTrim("GROUP_NAME"));
		}
		
		
		logger.info("responseXMLSQUserBase end");
		return response;
	}
	
	/**组织机构查询服务
	 * @param request
	 * @return
	 */
	public static String reruestXMLSGroupTree(SGroupTreeRequest request) {
		logger.info("reruestXMLSGroupTree  requestxml   start");
		StringBuffer sb = new StringBuffer();
		sb.append(XMLHEAD_U).append(ENTER);
		sb.append(XMLSTART).append(ENTER);
		sb.append("<REQUEST_INFO type=\"node\">").append(ENTER);
		org.springrain.sinova.interfaces.sitech.sgrouptree.req.RequestInfo requestInfo = request.getRequestInfo(); 
		org.springrain.sinova.interfaces.sitech.sloginno.req.OprInfo oprInfo = requestInfo.getOprInfo();
		if(oprInfo != null){
			sb.append("<OPR_INFO type=\"node\">").append(ENTER);
			sb.append("<REGION_ID type=\"int\">").append(oprInfo.getRegionId() == null ? "" : oprInfo.getRegionId()).append("</REGION_ID>").append(ENTER);
			sb.append("<CHANNEL_TYPE type=\"string\">").append(oprInfo.getChannelType() == null ? "" : oprInfo.getChannelType()).append("</CHANNEL_TYPE>").append(ENTER);
			sb.append("<LOGIN_NO type=\"string\">").append(oprInfo.getLoginNo() == null ? "" : oprInfo.getLoginNo()).append("</LOGIN_NO>").append(ENTER);
			sb.append("<LOGIN_PWD type=\"string\">").append(oprInfo.getLoginPwd() == null ? "" : oprInfo.getLoginPwd()).append("</LOGIN_PWD>").append(ENTER);
			sb.append("<IP_ADDRESS type=\"string\">").append(oprInfo.getIpAddress() == null ? "" : oprInfo.getIpAddress()).append("</IP_ADDRESS>").append(ENTER);
			sb.append("<GROUP_ID type=\"string\">").append(oprInfo.getGroupId() == null ? "" : oprInfo.getGroupId()).append("</GROUP_ID>").append(ENTER);
			sb.append("<CONTACT_ID type=\"long\">").append(oprInfo.getContactId() == null ? "" : oprInfo.getContactId()).append("</CONTACT_ID>").append(ENTER);
			sb.append("<OP_CODE type=\"string\">").append(oprInfo.getOpCode() == null ? "" : oprInfo.getOpCode()).append("</OP_CODE>").append(ENTER);
			sb.append("</OPR_INFO>").append(ENTER);
		}
		sb.append("<BUSI_INFO_LIST>").append(ENTER);
		List<org.springrain.sinova.interfaces.sitech.sgrouptree.req.BusiInfo> busiInfos = request.getRequestInfo().getBusiInfoList();
		for (org.springrain.sinova.interfaces.sitech.sgrouptree.req.BusiInfo busiInfo : busiInfos) {
			sb.append("<BUSI_INFO type=\"node\">").append(ENTER);
			sb.append("<GROUP_ID type=\"string\">").append(busiInfo.getGroupId() == null ? "" : busiInfo.getGroupId()).append("</GROUP_ID>").append(ENTER);
			sb.append("<ROOT_DISTANCE type=\"string\">").append(busiInfo.getRootDistance() == null ? "" : busiInfo.getRootDistance()).append("</ROOT_DISTANCE>").append(ENTER);
			sb.append("<IS_ACTIVE type=\"string\">").append(busiInfo.getIsActive() == null ? "" : busiInfo.getIsActive()).append("</IS_ACTIVE>").append(ENTER);
			sb.append("<CLASS_CODE type=\"string\">").append(busiInfo.getClassCode() == null ? "" : busiInfo.getClassCode()).append("</CLASS_CODE>").append(ENTER);
			sb.append("<CLASS_KIND type=\"string\">").append(busiInfo.getClassKind() == null ? "" : busiInfo.getClassKind()).append("</CLASS_KIND>").append(ENTER);
			sb.append("<IS_AGENT type=\"string\">").append(busiInfo.getIsAgent() == null ? "" : busiInfo.getIsAgent()).append("</IS_AGENT>").append(ENTER);
			sb.append("<IS_TOWN type=\"string\">").append(busiInfo.getIsTown() == null ? "" : busiInfo.getIsTown()).append("</IS_TOWN>").append(ENTER);
			sb.append("</BUSI_INFO>").append(ENTER);
		}
		sb.append("</BUSI_INFO_LIST>").append(ENTER);
		sb.append("</REQUEST_INFO>").append(ENTER);
		sb.append(XMLEND).append(ENTER);
//		logger.info("reruestXMLSGroupTree :" + ENTER + sb.toString());
		logger.info("reruestXMLSGroupTree  requestxml   end");
		return sb.toString();
	}
	
	public static SGroupTreeResponse responseXMLSGroupTree(String xml) throws DocumentException {
		logger.info("responseXMLSGroupTree start");
		SGroupTreeResponse response = new SGroupTreeResponse();
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		response.setReturnCode(root.elementTextTrim("RETURN_CODE"));
		response.setReturnMsg( root.elementTextTrim("RETURN_MSG"));
		response.setDetailMsg(root.elementTextTrim("DETAIL_MSG"));
		response.setUserMsg(root.elementTextTrim("USER_MSG"));
		response.setPromptMsg(root.elementTextTrim("PROMPT_MSG"));
		Element outData = root.element("OUT_DATA");
		Element _busiInfo = outData.element("BUSI_INFO");
		response.getOutData().getBusiInfo().setGroupId(_busiInfo.elementTextTrim("GROUP_ID"));
		response.getOutData().getBusiInfo().setParentGroupId(_busiInfo.elementTextTrim("PARENT_GROUP_ID"));
		response.getOutData().getBusiInfo().setpRegionName(_busiInfo.elementTextTrim("P_REGION_NAME"));
		response.getOutData().getBusiInfo().setTownAddress(_busiInfo.elementTextTrim("TOWN_ADDRESS"));
		logger.info("responseXMLSGroupTree end");
		return response;
	}

	
	
	/**
	 * 解析[s4035IntCfm]前项限制校验及订购服务 请求为XML
	 * @param request
	 * @return
	 */
	public static String parseS4035IntCfmRequest2XML(S4035IntCfmRequest request){
		String xml=null;
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>").append("\n");
		sb.append("<ROOT>").append("\n");
		 sb.append("<SERVICE_NO type=\"string\">"+request.getServiceNo()+"</SERVICE_NO>").append("\n");
		 sb.append("<MASTER_SERV_ID type=\"string\">"+request.getMasterServId()+"</MASTER_SERV_ID>").append("\n");
		 sb.append("<REQUEST_INFO>").append("\n");
		 sb.append("<OPR_INFO>").append("\n");
		 sb.append("<PROVINCE_GROUP type=\"string\">"+request.getProvinceGroup()+"</PROVINCE_GROUP>").append("\n");
		 sb.append("<OPEN_TIME  type=\"string\">"+request.getOpenTime()+"</OPEN_TIME>").append("\n");
		 sb.append("<LOGIN_NO type=\"string\">"+request.getLoginNo()+"</LOGIN_NO>").append("\n");
		 sb.append("<SERVICE_NO type=\"string\">"+request.getServiceNo()+"</SERVICE_NO>").append("\n");
		 sb.append("<ACT_ID type=\"string\">"+request.getActId()+"</ACT_ID>").append("\n");
		 if(StringUtils.isNotBlank(request.getOutOrderId())){
			 sb.append("<OUT_ORDER_ID type=\"string\">"+request.getOutOrderId()+"</OUT_ORDER_ID>").append("\n");
		 }
		 if(request.getContactId()==null){
			 sb.append("<CONTACT_ID type=\"string\"></CONTACT_ID>").append("\n");
		 }else{
			 sb.append("<CONTACT_ID type=\"string\">"+request.getContactId()+"</CONTACT_ID>").append("\n");
		 }
		 
		 sb.append("<MEANS_ID type=\"string\">"+request.getMeansId()+"</MEANS_ID>").append("\n");
		 if(request.getCustAdvince()==null){
			 sb.append("<CUST_ADVINCE type=\"string\"></CUST_ADVINCE>").append("\n");
		 }else{
			 sb.append("<CUST_ADVINCE type=\"string\">"+request.getCustAdvince()+"</CUST_ADVINCE>").append("\n");
		 }
		 if(request.getRecommendNo()==null){
			 sb.append("<RECOMMEND_NO type=\"string\"></RECOMMEND_NO>").append("\n");
		 }else{
			 sb.append("<RECOMMEND_NO type=\"string\">"+request.getRecommendNo()+"</RECOMMEND_NO>").append("\n");
		 }
		 sb.append("<CHANNEL_TYPE type=\"string\">"+request.getChannelType()+"</CHANNEL_TYPE>").append("\n");
		 if(request.getToGroupId()==null){
			 sb.append("<TO_GROUP_ID type=\"string\"></TO_GROUP_ID>").append("\n");
		 }else{
			 sb.append("<TO_GROUP_ID type=\"string\">"+request.getToGroupId()+"</TO_GROUP_ID>").append("\n");
		 }
		 if(request.getSerialNo()==null){
			 sb.append("<SERIAL_NO type=\"string\"></SERIAL_NO>").append("\n");
		 }else{
			 sb.append("<SERIAL_NO type=\"string\">"+request.getSerialNo()+"</SERIAL_NO>").append("\n");
		 }
		
		 if(request.getJoinTermiFlag()==null){
			 sb.append("<JOIN_TERMI_FLAG type=\"string\"></JOIN_TERMI_FLAG>").append("\n");
		 }else{
			 sb.append("<JOIN_TERMI_FLAG type=\"string\">"+request.getJoinTermiFlag()+"</JOIN_TERMI_FLAG>").append("\n");
		 }
		 //PRE_ACT_TYPE
		 if(request.getPreActType()==null){
			 sb.append("<PRE_ACT_TYPE type=\"string\"></PRE_ACT_TYPE>").append("\n");
		 }else{
			 sb.append("<PRE_ACT_TYPE type=\"string\">"+request.getPreActType()+"</PRE_ACT_TYPE>").append("\n");
		 }
		
		 sb.append("<TAINFO_LIST>").append("\n");
		 if(request.getTaInfoList()!=null&&request.getTaInfoList().size()>0){
			 String phoneNo=null;
			 String idNo=null;
			 for(int i=0;i<request.getTaInfoList().size();i++){
				 TaInfo taInfo=request.getTaInfoList().get(i);
				 sb.append("<TA_INFO>").append("\n");
				 phoneNo=taInfo.getPhoneNo()!=null?taInfo.getPhoneNo():"";
				 idNo=taInfo.getIdNo()!=null?taInfo.getIdNo():"";	 
				 sb.append("<PHONE_NO type=\"string\">"+phoneNo+"</PHONE_NO>").append("\n");
				 sb.append("<ID_NO type=\"string\">"+idNo+"</ID_NO>").append("\n");	 
				 sb.append("</TA_INFO>").append("\n");	 
			 }	 
		 }else{
			 sb.append("<TA_INFO>").append("\n");
			 sb.append("<PHONE_NO type=\"string\"></PHONE_NO>").append("\n");
			 sb.append("<ID_NO type=\"string\"></ID_NO>").append("\n");
			 sb.append("</TA_INFO>").append("\n");
		 }
		 sb.append("</TAINFO_LIST>").append("\n");
		 sb.append("</OPR_INFO>").append("\n");
		 sb.append("<PARAMS>").append("\n");
		 sb.append("<A06>").append("\n");
		 sb.append("<PRC_INFO_LIST>").append("\n");
		 sb.append("<PRC_INFO>").append("\n");
		 sb.append("<PROD_PRCID type=\"string\"></PROD_PRCID>").append("\n");
		 sb.append("</PRC_INFO>").append("\n");
		 sb.append("</PRC_INFO_LIST>").append("\n");
		 sb.append("</A06>").append("\n");
		 sb.append("<B06>").append("\n");
		 sb.append("<PRC_INFO_LIST>").append("\n");
		 sb.append("<PRC_INFO>").append("\n");
		 sb.append("<PROD_PRCID type=\"string\"></PROD_PRCID>").append("\n");
		 sb.append("</PRC_INFO>").append("\n");
		 sb.append("</PRC_INFO_LIST>").append("\n");
		 sb.append("<SERV_NO type=\"string\"></SERV_NO>").append("\n");
		 sb.append("</B06>").append("\n");
		 sb.append("<B02>").append("\n");
		 sb.append("<SERV_NO type=\"string\"></SERV_NO>").append("\n");
		 sb.append("</B02>").append("\n");
		 sb.append("<B10>").append("\n");
		 sb.append("<SERV_NO type=\"string\"></SERV_NO>").append("\n");
		 sb.append("</B10>").append("\n");
		 sb.append("<A11>").append("\n");
		 sb.append("<RESOURCE_LIST>").append("\n");
		 sb.append("<RESOURCE_INFO>").append("\n");
		 sb.append("<RECEIVE_PHONE type=\"string\"></RECEIVE_PHONE>").append("\n");
		 sb.append("<IMEI_NO type=\"string\"></IMEI_NO>").append("\n");
		 sb.append("<COLOR_ID type=\"string\"></COLOR_ID>").append("\n");
		 sb.append("<RESOURCE_CODE type=\"string\"></RESOURCE_CODE>").append("\n");
		 sb.append("<BRAND_CODE type=\"string\"></BRAND_CODE>").append("\n");
		 sb.append("</RESOURCE_INFO>").append("\n");
		 sb.append("</RESOURCE_LIST>").append("\n");
		 sb.append("</A11>").append("\n");
		 sb.append("<A14>").append("\n");
		 sb.append("<PHONE_NO type=\"string\"></PHONE_NO>").append("\n");
		 sb.append("</A14>").append("\n");
		 sb.append("<A17>").append("\n");
		 sb.append("<CONDCLASS_0178 type=\"string\"></CONDCLASS_0178>").append("\n");
		 sb.append("</A17>").append("\n");
		 sb.append("<A24>").append("\n");
		 sb.append("<SCORE_MONEY type=\"string\"></SCORE_MONEY>").append("\n");
		 sb.append("</A24>").append("\n");
		 sb.append("<A26>").append("\n");
		 sb.append("<IMEI_NO type=\"string\"></IMEI_NO>").append("\n");
		 sb.append("<PHONE_NO type=\"string\"></PHONE_NO>").append("\n");
		 sb.append("</A26>").append("\n");
		 sb.append("<A04>").append("\n");
		 sb.append("<INDEX type=\"string\"></INDEX>").append("\n");
		 sb.append("</A04>").append("\n");
		 sb.append("</PARAMS>").append("\n");
		 sb.append("</REQUEST_INFO>").append("\n");
		 sb.append("</ROOT>").append("\n");
		 xml = sb.toString();
		 return xml;
		
	}

	public static S4035IntCfmResponse parseXML2S4035IntCfmResponse(String xml) throws DocumentException{
		Document document =DocumentHelper.parseText(xml);
		S4035IntCfmResponse response =new S4035IntCfmResponse();
		Element root = document.getRootElement();
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");
		String PROMPT_MSG = root.elementTextTrim("PROMPT_MSG");
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		if(RETURN_CODE!=null&& RETURN_CODE.equals("0")){
			Element OUT_DATA = root.element("OUT_DATA");
			String ORDER_ID = OUT_DATA.elementTextTrim("ORDER_ID");
			String CREATE_ACCEPT = OUT_DATA.elementTextTrim("CREATE_ACCEPT");
			String PASS_FLAG = OUT_DATA.elementTextTrim("PASS_FLAG");
			response.setOrderId(ORDER_ID);
			response.setCreateAccept(CREATE_ACCEPT);
			response.setPassFlag(PASS_FLAG);
			if(StringUtils.isNotBlank(PASS_FLAG)){
				if("Y".equals(PASS_FLAG)){
					Element LUCK_INFO = OUT_DATA.element("LUCKYAWARD_INFO");
					if(LUCK_INFO!= null){
						String AWARD_FLAG = LUCK_INFO.elementTextTrim("AWARD_FLAG");
						String AWARD_RANK = LUCK_INFO.elementTextTrim("AWARD_RANK");
						String PRIZE_NO = LUCK_INFO.elementTextTrim("PRIZE_NO");
						String ACCEPT_TYPE = LUCK_INFO.elementTextTrim("ACCEPT_TYPE");
						String CHECK_CODE = LUCK_INFO.elementTextTrim("CHECK_CODE");
						String POSITION = LUCK_INFO.elementTextTrim("POSITION");
						LuckyawardInfo luckInfo=new LuckyawardInfo();
						luckInfo.setAwardFlag(AWARD_FLAG);
						luckInfo.setAwardRank(AWARD_RANK);
						luckInfo.setPrizeNo(PRIZE_NO);
						luckInfo.setAcceptType(ACCEPT_TYPE);
						luckInfo.setCheckCode(CHECK_CODE);
						luckInfo.setPosition(POSITION);
						response.setLuckInfo(luckInfo);
					}
				}else if("N".equals(PASS_FLAG)){
					Element MEAN_ALL = OUT_DATA.element("MEAN_ALL");
					if(MEAN_ALL != null){
						String meanAllPassFlag = MEAN_ALL.elementTextTrim("PASS_FLAG");
						Map<String,LimitInfo> map=new HashMap<String, LimitInfo>();
						Iterator it_all = MEAN_ALL.elementIterator("LIMIT_INFO");
						while(it_all.hasNext()){
							Element recordEle  = (Element)it_all.next(); 
							LimitInfo limitInfo=new LimitInfo();
							String indexSeq = recordEle.elementTextTrim("INDEX");
							String LIMIT_LEVEL = recordEle.elementText("LIMIT_LEVEL");
							String LIMIT_TYPE = recordEle.elementText("LIMIT_TYPE");
							String LIMIT_CODE = recordEle.elementText("LIMIT_CODE");
							String LIMIT_TAR = recordEle.elementText("LIMIT_TAR");
							String LIMIT_VALUE = recordEle.elementText("LIMIT_VALUE");
							String LEVEL_NAME = recordEle.elementText("LEVEL_NAME");
							String TYPE_NAME = recordEle.elementText("TYPE_NAME");
							String CODE_NAME = recordEle.elementText("CODE_NAME");
							String TAR_NAME = recordEle.elementText("TAR_NAME");
							String TEAM_NO = recordEle.elementText("TEAM_NO");
							String LIMIT_NAME = recordEle.elementText("LIMIT_NAME");
							String LIMIT_NO = recordEle.elementText("LIMIT_NO");
							String NOTE = recordEle.elementText("NOTE");
							String ERRMSG = recordEle.elementText("ERRMSG");
							String PASS_FLAG_IN = recordEle.elementText("PASS_FLAG");
							limitInfo.setIndexSeq(indexSeq);
							limitInfo.setTeamNo(TEAM_NO);
							limitInfo.setCodeName(CODE_NAME);
							limitInfo.setLimitName(LIMIT_NAME);
							limitInfo.setNote(NOTE);
							limitInfo.setLimitTar(LIMIT_TAR);
							limitInfo.setTarName(TAR_NAME);
							limitInfo.setTypeName(TYPE_NAME);
							limitInfo.setLimitLevel(LIMIT_LEVEL);
							limitInfo.setLimitValue(LIMIT_VALUE);
							limitInfo.setLimitCode(LIMIT_CODE);
							limitInfo.setLimitNo(LIMIT_NO);
							limitInfo.setLimitType(LIMIT_TYPE);
							limitInfo.setLevelName(LEVEL_NAME);
							limitInfo.setErrMsg(ERRMSG);
							limitInfo.setPassFlag(PASS_FLAG_IN);
							map.put(indexSeq, limitInfo);
						}
						MeanAllResponse meanAll=new MeanAllResponse();
						meanAll.setMeanAllFlag(meanAllPassFlag);
						meanAll.setLimitInfo(map);
						response.setMeanAll(meanAll);
					}
				}
			}
			
		}
		response.setDetailMsg(DETAIL_MSG);
		response.setPromptMsg(PROMPT_MSG);
		response.setReturnCode(RETURN_CODE);
		return response;
	}
	
	
	/**
	 * 王玉军 
	 * 查询档次请求报文
	 * @param request
	 * @return
	 */
	public static String parseWsGetSaleMeansContentRequest2XML(WsGetSaleMeansContentRequest request){
		String xml = null;
	    StringBuffer sb = new StringBuffer();
	    sb.append(XMLHEAD_U).append(ENTER);
		sb.append(XMLSTART).append(ENTER);
	    sb.append("<COMMON_INFO>").append(ENTER);
	    sb.append("<PROVINCE_GROUP>").append(request.getProvinceCode() == null ? "" : request.getProvinceCode()).append("</PROVINCE_GROUP>").append(ENTER);
	    sb.append("</COMMON_INFO>").append(ENTER);
	    sb.append("<REQUEST_INFO>").append(ENTER);
	    sb.append("<ID_NO>").append(request.getIdNo() == null ? "" : request.getIdNo()).append("</ID_NO>").append(ENTER);
	    sb.append("<PHONE_NO>").append(request.getPhoneNo() == null ? "" : request.getPhoneNo()).append("</PHONE_NO>").append(ENTER);
	    sb.append("<REGION_CODE>").append(request.getRegionCode() == null ? "" : request.getRegionCode()).append("</REGION_CODE>").append(ENTER);
	    sb.append("<ACT_ID>").append(request.getActId() == null ? "" : request.getActId()).append("</ACT_ID>").append(ENTER);
	    sb.append("<MEANS_ID>").append(request.getMeansId() == null ? "" : request.getMeansId()).append("</MEANS_ID>").append(ENTER);
	    sb.append("<CUST_GROUP_ID>").append(request.getCustGroupId() == null ? "" : request.getCustGroupId()).append("</CUST_GROUP_ID>").append(ENTER);
	    sb.append("<BRAND_ID>").append(request.getBrandId() == null ? "" : request.getBrandId()).append("</BRAND_ID>").append(ENTER);
	    sb.append("<RESOURCE_MODEL>").append(request.getResourceModel() == null ? "" : request.getResourceModel()).append("</RESOURCE_MODEL>").append(ENTER);
	    sb.append("<CHANNEL_TYPE>").append(request.getChannelType() == null ? "" : request.getChannelType()).append("</CHANNEL_TYPE>").append(ENTER);
	    sb.append("<FLAG>").append(request.getFalg() == null ? "" : request.getFalg()).append("</FLAG>").append(ENTER);
	    sb.append("</REQUEST_INFO>").append(ENTER);
	    sb.append("</ROOT>").append(ENTER);
	    xml = sb.toString();	  
	    return xml;
	}
	
	
	/**
	 * 
	 * 王玉军 
	 * @param xml  解析活动档次返回报文
	 * @return
	 * @throws DocumentException
	 */

	public static WsGetSaleMeansContentReponse parseXML2WsGetSaleMeansContentReponse(String xml) throws DocumentException {

		WsGetSaleMeansContentReponse response = new WsGetSaleMeansContentReponse();
		Document document = DocumentHelper.parseText(xml);
		logger.info("MeanContentReponse star -xml:");

		Element root = document.getRootElement();
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		String RETURN_MSG = root.elementTextTrim("RETURN_MSG");
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");

		if (RETURN_CODE != null && RETURN_CODE.equals("0")) {

			Element OUT_DATA = root.element("OUT_DATA");
			Element ACTION = OUT_DATA.element("ACTION");
			String actionId = ACTION.elementTextTrim("ACTION_ID");
			String actionName = ACTION.elementTextTrim("ACTION_NAME");
			String actionDesc = ACTION.elementTextTrim("ACTION_DESC");
			String actionDate = ACTION.elementTextTrim("ACTION_DATE");
			String mktDiction = ACTION.elementTextTrim("MKT_DICTION");
			String sendStartTime = ACTION.elementTextTrim("SEND_START_TIME");
			String sendEndTime = ACTION.elementTextTrim("SEND_END_TIME");
			String provideType = ACTION.elementTextTrim("PROVIDE_TYPE");
			List<MeanContent> mcList = new ArrayList<MeanContent>();
			Element MEANS = OUT_DATA.element("MEANS");
			List MEAN = MEANS.elements("MEAN");
			for (int i = 0; i < MEAN.size(); i++) {
				MeanContent mcResponse = new MeanContent();
				A00 a00Reponse = null;
				boolean A01Flag = false;
				A02 a02Reponse = null;
				boolean A04Flag = false;
				boolean A05Flag = false;
				A06 a06Reponse = null;// 解析资费标识使用
				A07 a07Reponse = null;
				boolean A09Flag = false;
				A10 a10Reponse = null;
				A11 a11Reponse = null;
				boolean A12Flag = false;
				boolean A14Flag = false;
				boolean A15Flag = false;
				boolean A16Flag = false;
				boolean A17Flag = false;
				boolean A18Flag = false;
				boolean A19Flag = false;
				boolean A20Flag = false;
				boolean A24Flag = false;
				boolean A25Flag = false;
				boolean A26Flag = false;
				boolean A28Flag = false;
				boolean A29Flag = false;
				boolean A30Flag = false;
				boolean A35Flag = false;
				boolean A38Flag = false;
				boolean A39Flag = false;
				boolean A40Flag = false;
				boolean A41Flag = false;
				boolean A42Flag = false;
				boolean A43Flag = false;
				B02 b02Reponse = null;
				boolean B06Flag = false;
				B10 b10Reponse = null;
				boolean B25Flag = false;
				Element mean = (Element) MEAN.get(i);
				String meansId = mean.elementTextTrim("MEANS_ID");
				String meansName = mean.elementTextTrim("MEANS_NAME");
				String smsMsg = mean.elementTextTrim("SMS_MSG");
				String sendFlag = mean.elementTextTrim("SEND_FLAG");
				String msg = mean.elementTextTrim("MSG");

				Element a00 = (Element) mean.element("A00");
				if (null != a00) {
					a00Reponse = parseXML2A00Reponse(a00);
				}
				
				Element a01 = (Element) mean.element("A01");
				if (null != a01) {
					A01Flag = true;
				}

				Element a02 = (Element) mean.element("A02");
				if (null != a02) {
					a02Reponse = parseXML2A02Reponse(a02);
				}
				
				
				Element a04 = (Element) mean.element("A04");
				if (null != a04) {
					A04Flag = true;
				}
				
				Element a05 = (Element) mean.element("A05");
				if (null != a05) {
					A05Flag = true;
				}

				Element a06 = (Element) mean.element("A06");// 解析资费标识使用
				if (null != a06) {
					a06Reponse = parseXML2A06Reponse(a06);
				}

				Element a07 = (Element) mean.element("A07");
				if (null != a07) {
					a07Reponse = parseXML2A07Reponse(a07);
				}
				
				
				Element a09 = (Element) mean.element("A09");
				if (null != a09) {
					A09Flag = true;
				}

				Element a10 = (Element) mean.element("A10");
				if (null != a10) {
					a10Reponse = parseXML2A10Reponse(a10);
				}

				Element a11 = (Element) mean.element("A11");
				if (null != a11) {
					a11Reponse = parseXML2A11Reponse(a11);
				}
				
				Element a12 = (Element) mean.element("A12");
				if (null != a12) {
					A12Flag = true;
				}
				
				
				
				Element a14 = (Element) mean.element("A14");
				if (null != a14) {
					A14Flag = true;
				}
				
				Element a15 = (Element) mean.element("A15");
				if (null != a15) {
					A15Flag = true;
				}
				
				Element a16 = (Element) mean.element("A16");
				if (null != a16) {
					A16Flag = true;
				}
				
				Element a17 = (Element) mean.element("A17");
				if (null != a17) {
					A17Flag = true;
				}
				
				Element a18 = (Element) mean.element("A18");
				if (null != a18) {
					A18Flag = true;
				}
				
				Element a19 = (Element) mean.element("A19");
				if (null != a19) {
					A19Flag = true;
				}
				
				Element a20 = (Element) mean.element("A20");
				if (null != a20) {
					A20Flag = true;
				}
				
			
				
				Element a24 = (Element) mean.element("A24");
				if (null != a24) {
					A24Flag = true;
				}
				
				Element a25 = (Element) mean.element("A25");
				if (null != a25) {
					A25Flag = true;
				}
				
				Element a26 = (Element) mean.element("A26");
				if (null != a26) {
					A26Flag = true;
				}
				
				
				Element a28 = (Element) mean.element("A28");
				if (null != a28) {
					A28Flag = true;
				}
				
				Element a29 = (Element) mean.element("A29");
				if (null != a29) {
					A29Flag = true;
				}
				
				Element a30 = (Element) mean.element("A30");
				if (null != a30) {
					A30Flag = true;
				}
				
				Element a35 = (Element) mean.element("A35");
				if (null != a35) {
					A35Flag = true;
				}
				
				Element a38 = (Element) mean.element("A38");
				if (null != a38) {
					A38Flag = true;
				}
				
				Element a39 = (Element) mean.element("A39");
				if (null != a39) {
					A39Flag = true;
				}
				
				Element a40 = (Element) mean.element("A40");
				if (null != a40) {
					A40Flag = true;
				}
				
				Element a41 = (Element) mean.element("A41");
				if (null != a41) {
					A41Flag = true;
				}
				
				Element a42 = (Element) mean.element("A42");
				if (null != a42) {
					A42Flag = true;
				}
				
				Element a43 = (Element) mean.element("A43");
				if (null != a43) {
					A43Flag = true;
				}

				Element b02 = (Element) mean.element("B02");
				if (null != b02) {
					b02Reponse = parseXML2B02Reponse(b02);
				}
				
				Element b06 = (Element) mean.element("B06");
				if (null != b06) {
					B06Flag = true;
				}

				Element b10 = (Element) mean.element("B10");
				if (null != b10) {
					b10Reponse = parseXML2B10Reponse(b10);
				}
				
				Element b25 = (Element) mean.element("B25");
				if (null != b25) {
					B25Flag = true;
				}

				mcResponse.setMeansId(meansId);
				mcResponse.setMeansName(meansName);
				mcResponse.setMsg(msg);
				mcResponse.setSendFlag(sendFlag);
				mcResponse.setSmsMsg(smsMsg);
				mcResponse.setA00(a00Reponse);
				mcResponse.setA01(A01Flag);
				mcResponse.setA02(a02Reponse);
				mcResponse.setA04(A04Flag);
				mcResponse.setA05(A05Flag);
				mcResponse.setA06(a06Reponse);
				mcResponse.setA07(a07Reponse);
				mcResponse.setA09(A09Flag);
				mcResponse.setA10(a10Reponse);
				mcResponse.setA11(a11Reponse);
				mcResponse.setA12(A12Flag);
				mcResponse.setA14(A14Flag);
				mcResponse.setA15(A15Flag);
				mcResponse.setA16(A16Flag);
				mcResponse.setA17(A17Flag);
				mcResponse.setA18(A18Flag);
				mcResponse.setA19(A19Flag);
				mcResponse.setA20(A20Flag);
				mcResponse.setA24(A24Flag);
				mcResponse.setA25(A25Flag);
				mcResponse.setA26(A26Flag);
				mcResponse.setA28(A28Flag);
				mcResponse.setA29(A29Flag);
				mcResponse.setA30(A30Flag);
				mcResponse.setA35(A35Flag);
				mcResponse.setA38(A38Flag);
				mcResponse.setA39(A39Flag);
				mcResponse.setA40(A40Flag);
				mcResponse.setA41(A41Flag);
				mcResponse.setA42(A42Flag);
				mcResponse.setA43(A43Flag);
				mcResponse.setB02(b02Reponse);
				mcResponse.setB06(B06Flag);
				mcResponse.setB10(b10Reponse);
				mcResponse.setB25(B25Flag);
				mcList.add(mcResponse);
			}
			response.setActionId(actionId);
			response.setActionName(actionName);
			response.setActionDesc(actionDesc);
			response.setActionDate(actionDate);
			response.setMktDiction(mktDiction);
			response.setSendEndTime(sendEndTime);
			response.setSendStartTime(sendStartTime);
			response.setProvideType(provideType);
			response.setMeansList(mcList);
		}
		response.setDetailMsg(DETAIL_MSG);
		response.setReturnCode(RETURN_CODE);
		response.setReturnMsg(RETURN_MSG);
		return response;
	}
		
	//查询活动档次的小项解析
	public static A00 parseXML2A00Reponse(Element e) throws DocumentException{
		A00 response=new A00();
	//	Element mean=(Element)e.element("A00");
		String payMoney=e.elementTextTrim("PAY_MONEY");
		String feeCode=e.elementTextTrim("FEE_CODE");
		String detailCode=e.elementTextTrim("DETAIL_CODE");
		String feeType=e.elementTextTrim("FEE_TYPE");
		String accountType=e.elementTextTrim("ACCOUNT_TYPE");
		String isPure=e.elementTextTrim("IS_PURE");
		String feeName=e.elementTextTrim("FEE_NAME");
		response.setAccountType(accountType);
		response.setDetailCode(detailCode);
		response.setFeeCode(feeCode);
		response.setFeeName(feeName);
		response.setFeeType(feeType);
		response.setIsPure(isPure);
		response.setPayMoney(payMoney);
		return response;
	}
	
	
	public static A02 parseXML2A02Reponse(Element e) throws DocumentException{
		A02 response=new A02();

		String payMoney=e.elementTextTrim("PAY_MONEY");
		String feeCode=e.elementTextTrim("FEE_CODE");
		String detailCode=e.elementTextTrim("DETAIL_CODE");
		String feeType=e.elementTextTrim("FEE_TYPE");
		String effType=e.elementTextTrim("EFF_TYPE");
		String payType=e.elementTextTrim("PAY_TYPE");
		String feeName=e.elementTextTrim("FEE_NAME");
		String offSetMonth=e.elementTextTrim("OFFSET_MONTH");
		String effDate=e.elementTextTrim("EFF_DATE");
		String feeRate=e.elementTextTrim("FEE_RATE");
		
		response.setEffDate(effDate);
		response.setDetailCode(detailCode);
		response.setFeeCode(feeCode);
		response.setFeeName(feeName);
		response.setFeeType(feeType);
		response.setFeeRate(feeRate);
		response.setPayMoney(payMoney);
		response.setOffSetMonth(offSetMonth);
		response.setEffType(effType);
		response.setPayType(payType);
		return response;
	}
	
	
	
	
	
	public static A06 parseXML2A06Reponse(Element e) throws DocumentException{
		A06 response=new A06();
		Element PRC_INFO_LIST=e.element("PRC_INFO_LIST");
		List<Element> infoList=PRC_INFO_LIST.elements("PRC_INFO");
		String PROD_PRCID="";
		if(!infoList.isEmpty()){
			Element PRC_INFO=infoList.get(0);
			PROD_PRCID=PRC_INFO.elementText("PROD_PRCID");
		}	
		response.setPrcId(PROD_PRCID);
		return response;
	}
	
	
	/**
	 * 合约一体化 A06解析
	 * @param e
	 * @return
	 * @throws DocumentException
	 */
	public static A06 parseXML2A06ReponseNew(Element e) throws DocumentException{
		A06 response=new A06();
		Element PRC_INFO_LIST=e.element("PRC_INFO_LIST");
		List<Element> infoList=PRC_INFO_LIST.elements("PRC_INFO");
		String PROD_PRCID="";
		List PROD_PRCID_LIST = new ArrayList();
		if(!infoList.isEmpty()){
			for(int i=0;i<infoList.size();i++){
				Element PRC_INFO=infoList.get(i);
				PROD_PRCID=PRC_INFO.elementText("PROD_PRCID");
				PROD_PRCID_LIST.add(PROD_PRCID);
			}
		}	
		response.setPrcIdList(PROD_PRCID_LIST);
		return response;
	}
	
	
	public static A07 parseXML2A07Reponse(Element e) throws DocumentException{
		A07 response=new A07();
		String payMoney=e.elementTextTrim("PAY_MONEY");
		String feeCode=e.elementTextTrim("FEE_CODE");
		String feeType=e.elementTextTrim("FEE_TYPE");
		String feeName=e.elementTextTrim("FEE_NAME");

		response.setFeeCode(feeCode);
		response.setFeeName(feeName);
		response.setFeeType(feeType);
		response.setPayMoney(payMoney);
		return response;
	}
	
	
	
	public static A10 parseXML2A10Reponse(Element e) throws DocumentException{
		A10 response=new A10();
	//	Element mean=(Element)e.element("A00");
		String modifyFlag=e.elementTextTrim("MODIFY_FLAG");
		List<MonthReturn> mrList=new ArrayList<MonthReturn>();
		List monthReturn =e.elements("MONTH_RETURN");
		for (int i = 0; i < monthReturn.size(); i++) {
			MonthReturn mr= new MonthReturn();
			Element month=(Element)monthReturn.get(i);
			String index=month.elementTextTrim("INDEX");         
			String feeCode=month.elementTextTrim("FEE_CODE");       
			String giveFeeCode=month.elementTextTrim("GIVE_FEE_CODE");   
			String totalMoney=month.elementTextTrim("TOTAL_MONEY");    
			String stagesMonth=month.elementTextTrim("STAGES_MONTH");   
			String stagesMoney=month.elementTextTrim("STAGES_MONEY");   
			String stagesSpace=month.elementTextTrim("STAGES_SPACE");   
			String stagesCycle=month.elementTextTrim("STAGES_CYCLE");   
			String favRate=month.elementTextTrim("FAV_RATE");       
			String detailCode=month.elementTextTrim("DETAIL_CODE");    
			String payMoney=month.elementTextTrim("PAY_MONEY");      
			String giveDetailCode=month.elementTextTrim("GIVE_DETAIL_CODE");
			String giveMoney=month.elementTextTrim("GIVE_MONEY");     
			String effType=month.elementTextTrim("EFF_TYPE");       
			String feeType=month.elementTextTrim("FEE_TYPE");       
			String giveFeeType=month.elementTextTrim("GIVE_FEE_NAME");   
			String giveFeeName=month.elementTextTrim("GIVE_FEE_TYPE");   
			String addFeeFlag=month.elementTextTrim("ADD_FEE_FLAG");    
			String feeName=month.elementTextTrim("FEE_NAME");       
			String isComplex=month.elementTextTrim("IS_COMPLEX");     
			String effDate=month.elementTextTrim("EFF_DATE");       
			String limitC1=month.elementTextTrim("LIMIT_C1");       
			String limitC2=month.elementTextTrim("LIMIT_C2");       
			String limitC3=month.elementTextTrim("LIMIT_C3");       
			String limitC4=month.elementTextTrim("LIMIT_C4");       
			String limitC5=month.elementTextTrim("LIMIT_C5");       
			String limitC6=month.elementTextTrim("LIMIT_C6");       
			String limitC7=month.elementTextTrim("LIMIT_C7");       
			String limitC8=month.elementTextTrim("LIMIT_C8");      
			mr.setAddFeeFlag(addFeeFlag);
			mr.setDetailCode(detailCode);
			mr.setEffDate(effDate);
			mr.setEffType(effType);
			mr.setFavRate(favRate);
			mr.setFeeCode(feeCode);
			mr.setFeeName(feeName);
			mr.setFeeType(feeType);
			mr.setGiveDetailCode(giveDetailCode);
			mr.setGiveFeeCode(giveFeeCode);
			mr.setGiveFeeName(giveFeeName);
			mr.setGiveFeeType(giveFeeType);
			mr.setGiveMoney(giveMoney);
			mr.setIndex(index);
			mr.setIsComplex(isComplex);
			mr.setPayMoney(payMoney);
			mr.setStagesCycle(stagesCycle);
			mr.setStagesMoney(stagesMoney);
			mr.setStagesMonth(stagesMonth);
			mr.setStagesSpace(stagesSpace);
			mr.setTotalMoney(totalMoney);
			mr.setLimitC1(limitC1);
			mr.setLimitC2(limitC2);
			mr.setLimitC3(limitC3);
			mr.setLimitC4(limitC4);
			mr.setLimitC5(limitC5);
			mr.setLimitC6(limitC6);
			mr.setLimitC7(limitC7);
			mr.setLimitC8(limitC8);
			mrList.add(mr);
		}
		response.setModifyFlag(modifyFlag);
		response.setMonthreturnList(mrList);
		return response;
	}
	public static A11 parseXML2A11Reponse(Element e) throws DocumentException{
		A11 response=new A11();
		Element attrCtrl=(Element)e.element("ATTR_CTRL");
		
		String isModify=attrCtrl.elementTextTrim("IS_MODIFY");
		String isScore=e.elementTextTrim("IS_SCORE");
		String isOpenage=e.elementTextTrim("IS_OPENAGE");
		String isActlib=e.elementTextTrim("IS_ACTLIB");
		Element info=(Element)e.element("RESOURCE_INFO");
		String index=info.elementTextTrim("INDEX");         
		String resourceCode=info.elementTextTrim("RESOURCE_CODE");       
		String resourceModel=info.elementTextTrim("RESOURCE_MODEL");   
		String resourceName=info.elementTextTrim("RESOURCE_NAME");    
		String resourceCostPrice=info.elementTextTrim("RESOURCE_COST_PRICE");   
		String resourceFee=info.elementTextTrim("RESOURCE_FEE");   
		String allowanceFee=info.elementTextTrim("ALLOWANCE_FEE");   
		String mobileAllowance=info.elementTextTrim("MOBILE_ALLOWANCE");   
		String resourceFavRate=info.elementTextTrim("RESOURCE_FAV_RATE");       
		String resourceRealPrice=info.elementTextTrim("RESOURCE_REAL_PRICE");    
		String feeCode=info.elementTextTrim("FEE_CODE");      
		String feeType=info.elementTextTrim("FEE_TYPE");
		String contractTime=info.elementTextTrim("CONTRACT_TIME");     
		String sepUim=info.elementTextTrim("SEP_UIM");       
		String feeName=info.elementTextTrim("FEE_NAME");       
		String brandId=info.elementTextTrim("BRAND_ID");   
		String termNum=info.elementTextTrim("TERM_NUM");   
		String groupPurch=info.elementTextTrim("GROUP_PURCH");    
		String resourceDesc=info.elementTextTrim("RESOURCE_DESC");       
		String sepMonth=info.elementTextTrim("SEP_MONTH");     
		String terminType=info.elementTextTrim("TERMIN_TYPE");       
		String markFee=info.elementTextTrim("MARK_FEE");       
		String mFee=info.elementTextTrim("M_FEE");       
		String mark=info.elementTextTrim("MARK");       
		String resourceNo=info.elementTextTrim("RESOURCE_NO");       
		String openageFee=info.elementTextTrim("OPENAGE_FEE");       
		String openage=info.elementTextTrim("OPENAGE");       
		String openageRate=info.elementTextTrim("OPENAGE_RATE");       
		String scoreMoney=info.elementTextTrim("SCORE_MONEY");    
		String scoreRate=info.elementTextTrim("SCORE_RATE");   
		String openageMoney=info.elementTextTrim("OPENAGE_MONEY");
		
		response.setAllowanceFee(allowanceFee);
		response.setBrandId(brandId);
		response.setContractTime(contractTime);
		response.setFeeCode(feeCode);
		response.setFeeName(feeName);
		response.setFeeType(feeType);
		response.setGroupPurch(groupPurch);
		response.setIndex(index);
		response.setIsActlib(isActlib);
		response.setIsModify(isModify);
		response.setIsOpenage(isOpenage);
		response.setIsScore(isScore);
		response.setMark(mark);
		response.setMarkFee(markFee);
		response.setmFee(mFee);
		response.setMobileAllowance(mobileAllowance);
		response.setOpenage(openage);
		response.setOpenageFee(openageFee);
		response.setOpenageMoney(openageMoney);
		response.setOpenageRate(openageRate);
		response.setResourceCode(resourceCode);
		response.setResourceCostPrice(resourceCostPrice);
		response.setResourceDesc(resourceDesc);
		response.setResourceFavRate(resourceFavRate);
		response.setResourceFee(resourceFee);
		response.setResourceModel(resourceModel);
		response.setResourceName(resourceName);
		response.setResourceNo(resourceNo);
		response.setResourceRealPrice(resourceRealPrice);
		response.setScoreMoney(scoreMoney);
		response.setScoreRate(scoreRate);
		response.setSepMonth(sepMonth);
		response.setSepUim(sepUim);
		response.setTerminType(terminType);
		response.setTermNum(termNum);
		

		return response;
	}
	
	public static B02 parseXML2B02Reponse(Element e) throws DocumentException{
		B02 response=new B02();

		String payMoney=e.elementTextTrim("PAY_MONEY");
		String feeCode=e.elementTextTrim("FEE_CODE");
		String detailCode=e.elementTextTrim("DETAIL_CODE");
		String feeType=e.elementTextTrim("FEE_TYPE");
		String effType=e.elementTextTrim("EFF_TYPE");
		String payType=e.elementTextTrim("PAY_TYPE");
		String feeName=e.elementTextTrim("FEE_NAME");
		String offSetMonth=e.elementTextTrim("OFFSET_MONTH");
		String effDate=e.elementTextTrim("EFF_DATE");
		String feeRate=e.elementTextTrim("FEE_RATE");
		String servNo=e.elementTextTrim("SERV_NO");
		
		response.setServNo(servNo);
		response.setEffDate(effDate);
		response.setDetailCode(detailCode);
		response.setFeeCode(feeCode);
		response.setFeeName(feeName);
		response.setFeeType(feeType);
		response.setFeeRate(feeRate);
		response.setPayMoney(payMoney);
		response.setOffSetMonth(offSetMonth);
		response.setEffType(effType);
		response.setPayType(payType);
		return response;
	}
	
	public static B10 parseXML2B10Reponse(Element e) throws DocumentException{
		B10 response=new B10();
	//	Element mean=(Element)e.element("A00");
		String modifyFlag=e.elementTextTrim("MODIFY_FLAG");
		String servNo=e.elementTextTrim("SERV_NO");
		List<MonthReturn> mrList=new ArrayList<MonthReturn>();
		
		List monthReturn =e.elements("MONTH_RETURN");
		for (int i = 0; i < monthReturn.size(); i++) {
			MonthReturn mr= new MonthReturn();
			Element month=(Element)monthReturn.get(i);
			String index=month.elementTextTrim("INDEX");         
			String feeCode=month.elementTextTrim("FEE_CODE");       
			String giveFeeCode=month.elementTextTrim("GIVE_FEE_CODE");   
			String totalMoney=month.elementTextTrim("TOTAL_MONEY");    
			String stagesMonth=month.elementTextTrim("STAGES_MONTH");   
			String stagesMoney=month.elementTextTrim("STAGES_MONEY");   
			String stagesSpace=month.elementTextTrim("STAGES_SPACE");   
			String stagesCycle=month.elementTextTrim("STAGES_CYCLE");   
			String favRate=month.elementTextTrim("FAV_RATE");       
			String detailCode=month.elementTextTrim("DETAIL_CODE");    
			String payMoney=month.elementTextTrim("PAY_MONEY");      
			String giveDetailCode=month.elementTextTrim("GIVE_DETAIL_CODE");
			String giveMoney=month.elementTextTrim("GIVE_MONEY");     
			String effType=month.elementTextTrim("EFF_TYPE");       
			String feeType=month.elementTextTrim("FEE_TYPE");       
			String giveFeeType=month.elementTextTrim("GIVE_FEE_NAME");   
			String giveFeeName=month.elementTextTrim("GIVE_FEE_TYPE");   
			String addFeeFlag=month.elementTextTrim("ADD_FEE_FLAG");    
			String feeName=month.elementTextTrim("FEE_NAME");       
			String isComplex=month.elementTextTrim("IS_COMPLEX");     
			String effDate=month.elementTextTrim("EFF_DATE");       
			String limitC1=month.elementTextTrim("LIMIT_C1");       
			String limitC2=month.elementTextTrim("LIMIT_C2");       
			String limitC3=month.elementTextTrim("LIMIT_C3");       
			String limitC4=month.elementTextTrim("LIMIT_C4");       
			String limitC5=month.elementTextTrim("LIMIT_C5");       
			String limitC6=month.elementTextTrim("LIMIT_C6");       
			String limitC7=month.elementTextTrim("LIMIT_C7");       
			String limitC8=month.elementTextTrim("LIMIT_C8");      
			mr.setAddFeeFlag(addFeeFlag);
			mr.setDetailCode(detailCode);
			mr.setEffDate(effDate);
			mr.setEffType(effType);
			mr.setFavRate(favRate);
			mr.setFeeCode(feeCode);
			mr.setFeeName(feeName);
			mr.setFeeType(feeType);
			mr.setGiveDetailCode(giveDetailCode);
			mr.setGiveFeeCode(giveFeeCode);
			mr.setGiveFeeName(giveFeeName);
			mr.setGiveFeeType(giveFeeType);
			mr.setGiveMoney(giveMoney);
			mr.setIndex(index);
			mr.setIsComplex(isComplex);
			mr.setPayMoney(payMoney);
			mr.setStagesCycle(stagesCycle);
			mr.setStagesMoney(stagesMoney);
			mr.setStagesMonth(stagesMonth);
			mr.setStagesSpace(stagesSpace);
			mr.setTotalMoney(totalMoney);
			mr.setLimitC1(limitC1);
			mr.setLimitC2(limitC2);
			mr.setLimitC3(limitC3);
			mr.setLimitC4(limitC4);
			mr.setLimitC5(limitC5);
			mr.setLimitC6(limitC6);
			mr.setLimitC7(limitC7);
			mr.setLimitC8(limitC8);
			String offSetMonth=month.elementTextTrim("OFFSET_MONTH");   
			mr.setOffSetMonth(offSetMonth);
			mrList.add(mr);
		}
		response.setModifyFlag(modifyFlag);
		response.setServNo(servNo);
		response.setMonthreturnList(mrList);
		return response;
	}

	
	
	
	/**
	 * 解析[s4035IntChk]前项限制校验 请求为XML
	 * @param request
	 * @return
	 */
	public static String parseS4035IntChkRequest2XML(S4035IntChkRequest request){
		String xml = null;
		StringBuffer sb = new StringBuffer();
	    sb.append("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>").append("\n");
	    sb.append("<ROOT>").append("\n");
	    sb.append("<SERVICE_NO type=\"string\">"+request.getServiceNO()+"</SERVICE_NO>").append("\n");
	    sb.append("<MASTER_SERV_ID type=\"string\">"+request.getMasterServId()+"</MASTER_SERV_ID>").append("\n");
	    sb.append("<REQUEST_INFO>").append("\n");
	    sb.append("<OPR_INFO>").append("\n");
	    sb.append("<PROVINCE_GROUP type=\"string\">"+request.getProvince()+"</PROVINCE_GROUP>").append("\n");
	    sb.append("<LOGIN_NO type=\"string\">"+request.getLoginNO()+"</LOGIN_NO>").append("\n");//<!--操作员工号-->
	    sb.append("<OP_CODE type=\"string\">"+request.getOpCode()+"</OP_CODE>").append("\n");//<!--操作-->
	    sb.append("<OP_NOTE type=\"string\">"+request.getOpNote()+"</OP_NOTE>").append("\n");//<!--操作备注-->
	    sb.append("<PHONE_NO type=\"string\">"+request.getPhoneNo()+"</PHONE_NO>").append("\n");// 手机号码
	    sb.append("<CHANNEL_TYPE type=\"string\">"+request.getChannelType()+"</CHANNEL_TYPE>").append("\n");//
	    sb.append("<ACT_ID type=\"string\">"+request.getActID()+"</ACT_ID>").append("\n");//	<!--活动ID-->
	    sb.append("<MEANS_ID type=\"string\">"+request.getMeansID()+"</MEANS_ID>").append("\n");//<!--方式ID-->
	//    sb.append("<CUST_GROUP_ID type=\"string\">"+request.getCustGroupID()+"</CUST_GROUP_ID>").append("\n");//<!--目标客户群ID MEANS_ID和CUST_GROUP_ID必传一个-->
	    sb.append("</OPR_INFO>").append("\n");
	    sb.append("<PARAMS>").append("\n");
	    sb.append("<A28>").append("\n");
	    if(null==request.getA28TransferSerIalno()){
	    	sb.append("<TRANSFER_SERIALNO type=\"string\"></TRANSFER_SERIALNO>").append("\n");
	    }else{
	    	sb.append("<TRANSFER_SERIALNO type=\"string\">"+request.getA28TransferSerIalno()+"</TRANSFER_SERIALNO>").append("\n");
	    }
	    
	    sb.append("</A28>").append("\n");
	    sb.append("<A17>").append("\n");
	    
	    if(null==request.getA28TransferSerIalno()){
	    	 sb.append("<TRANSFER_SERIALNO type=\"string\"></TRANSFER_SERIALNO>").append("\n");
	    }else{
	    	 sb.append("<TRANSFER_SERIALNO type=\"string\">"+request.getA17TransferSerIalno()+"</TRANSFER_SERIALNO>").append("\n");
	    }
	   
	    sb.append("</A17>").append("\n");
	    sb.append("</PARAMS>").append("\n");
	    sb.append("<TAINFO_LIST>").append("\n");
	    sb.append("<TA_INFO>").append("\n");
	    if(null==request.getA28TransferSerIalno()){
	    	sb.append("<PHONE_NO type=\"string\"></PHONE_NO>").append("\n");
	    }else{
	    	sb.append("<PHONE_NO type=\"string\">"+request.getTaPhoneNo()+"</PHONE_NO>").append("\n");
	    }
	    if(null==request.getA28TransferSerIalno()){
	    	sb.append("<ID_NO type=\"string\"></ID_NO>").append("\n");
	    }else{
	    	sb.append("<ID_NO type=\"string\">"+request.getTaIdNo()+"</ID_NO>").append("\n");
	    }
	    
	    sb.append("</TA_INFO>").append("\n");
	    sb.append("</TAINFO_LIST>").append("\n");
	    sb.append("</REQUEST_INFO>").append("\n");
	    sb.append("</ROOT>").append("\n");
	    xml = sb.toString();
	    logger.info("parseChkRequest2XML :"+xml);
		return xml;
		
	}


	/**
	 * 解析[s4035IntChk]返回报文为XML
	 * @param request
	 * @return
	 */
	
	public static S4035IntChkResponse parseXML2S4035IntChkResponse(String xml) throws DocumentException{
		Document document =DocumentHelper.parseText(xml);
		logger.info("s4035IntChk star -xml:"+xml);
		S4035IntChkResponse response = new S4035IntChkResponse();
		Element root = document.getRootElement();
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		String RETURN_MSG = root.elementTextTrim("RETURN_MSG");
		String USER_MSG = root.elementTextTrim("USER_MSG");
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");
		String PROMPT_MSG = root.elementTextTrim("PROMPT_MSG");
		if(RETURN_CODE!=null&&(RETURN_CODE.equals("0")||RETURN_CODE.equals("000000"))){
			Element OUT_DATA = root.element("OUT_DATA");
			String PASS_FLAG = OUT_DATA.elementTextTrim("PASS_FLAG");
			if(!StringUtils.isBlank(PASS_FLAG)&& "N".equals(PASS_FLAG.trim())){
				Element MEAN_ALL = OUT_DATA.element("MEAN_ALL");
				if(null!=MEAN_ALL){
					String meanAllPassFlag = MEAN_ALL.elementTextTrim("PASS_FLAG");
					List<org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.LimitInfo> listAll = new ArrayList<org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.LimitInfo>();
					Iterator it_all = MEAN_ALL.elementIterator("LIMIT_INFO");
					while(it_all.hasNext()){
						Element recordEle  = (Element)it_all.next();
						org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.LimitInfo limitInfo = new org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.LimitInfo();
						String indexSeq = recordEle.elementTextTrim("INDEX");
						String TEAM_NO = recordEle.elementText("TEAM_NO");
						String CODE_NAME = recordEle.elementText("CODE_NAME");
						String LIMIT_NAME = recordEle.elementText("LIMIT_NAME");
						String NOTE = recordEle.elementText("NOTE");
						String LIMIT_TAR = recordEle.elementText("LIMIT_TAR");
						String TAR_NAME = recordEle.elementText("TAR_NAME");
						String TYPE_NAME = recordEle.elementText("TYPE_NAME");
						String LIMIT_LEVEL = recordEle.elementText("LIMIT_LEVEL");
						String LIMIT_VALUE = recordEle.elementText("LIMIT_VALUE");
						String LIMIT_CODE = recordEle.elementText("LIMIT_CODE");
						String LIMIT_NO = recordEle.elementText("LIMIT_NO");
						String LIMIT_TYPE = recordEle.elementText("LIMIT_TYPE");
						String LEVEL_NAME = recordEle.elementText("LEVEL_NAME");
						String PASS_FLAG_IN = recordEle.elementText("PASS_FLAG");
						limitInfo.setIndexSeq(indexSeq);
						limitInfo.setTeamNo(TEAM_NO);
						limitInfo.setCodeName(CODE_NAME);
						limitInfo.setLimitName(LIMIT_NAME);
						limitInfo.setNote(NOTE);
						limitInfo.setLimitTar(LIMIT_TAR);
						limitInfo.setTarName(TAR_NAME);
						limitInfo.setTypeName(TYPE_NAME);
						limitInfo.setLimitLevel(LIMIT_LEVEL);
						limitInfo.setLimitValue(LIMIT_VALUE);
						limitInfo.setLimitCode(LIMIT_CODE);
						limitInfo.setLimitNo(LIMIT_NO);
						limitInfo.setLimitType(LIMIT_TYPE);
						limitInfo.setLevelName(LEVEL_NAME);
						limitInfo.setPassFlag(PASS_FLAG_IN);
						listAll.add(limitInfo);
					}
					org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.MeanAll meanAll=new org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.MeanAll();
					meanAll.setMeanAllFlag(meanAllPassFlag);
					meanAll.setLimitInfo(listAll);
					response.setMeanALl(meanAll);
				}
				
				///////////////////////////////////////////////
				Element MEAN_ONE = OUT_DATA.element("MEAN_ONE");
				if(null!=MEAN_ONE){
					String meanOnePassFlag = MEAN_ONE.elementTextTrim("PASS_FLAG");
//					List<LimitInfo> listOne = new ArrayList<LimitInfo>();
					List<org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.LimitInfo> listOne = new ArrayList<org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.LimitInfo>();
					Iterator it_one = MEAN_ONE.elementIterator("LIMIT_INFO");
					while(it_one.hasNext()){
						Element recordEle  = (Element)it_one.next();
						org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.LimitInfo limitInfo = new org.springrain.sinova.interfaces.sitech.s4035IntChk.rep.LimitInfo();
						String indexSeq = recordEle.elementTextTrim("INDEX");
						String TEAM_NO = recordEle.elementText("TEAM_NO");
						String CODE_NAME = recordEle.elementText("CODE_NAME");
						String LIMIT_NAME = recordEle.elementText("LIMIT_NAME");
						String NOTE = recordEle.elementText("NOTE");
						String LIMIT_TAR = recordEle.elementText("LIMIT_TAR");
						String TAR_NAME = recordEle.elementText("TAR_NAME");
						String TYPE_NAME = recordEle.elementText("TYPE_NAME");
						String LIMIT_LEVEL = recordEle.elementText("LIMIT_LEVEL");
						String LIMIT_VALUE = recordEle.elementText("LIMIT_VALUE");
						String LIMIT_CODE = recordEle.elementText("LIMIT_CODE");
						String LIMIT_NO = recordEle.elementText("LIMIT_NO");
						String LIMIT_TYPE = recordEle.elementText("LIMIT_TYPE");
						String LEVEL_NAME = recordEle.elementText("LEVEL_NAME");
						String PASS_FLAG_IN = recordEle.elementText("PASS_FLAG");
						limitInfo.setIndexSeq(indexSeq);
						limitInfo.setTeamNo(TEAM_NO);
						limitInfo.setCodeName(CODE_NAME);
						limitInfo.setLimitName(LIMIT_NAME);
						limitInfo.setNote(NOTE);
						limitInfo.setLimitTar(LIMIT_TAR);
						limitInfo.setTarName(TAR_NAME);
						limitInfo.setTypeName(TYPE_NAME);
						limitInfo.setLimitLevel(LIMIT_LEVEL);
						limitInfo.setLimitValue(LIMIT_VALUE);
						limitInfo.setLimitCode(LIMIT_CODE);
						limitInfo.setLimitNo(LIMIT_NO);
						limitInfo.setLimitType(LIMIT_TYPE);
						limitInfo.setLevelName(LEVEL_NAME);
						limitInfo.setPassFlag(PASS_FLAG_IN);
						listOne.add(limitInfo);
					}
					MeanOne meanOne=new MeanOne();
					meanOne.setMeanOneFlag(meanOnePassFlag);
					meanOne.setLimitInfo(listOne);
					response.setMeanOne(meanOne);
				}
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				
			}
			response.setPassFlag(PASS_FLAG);
		}
		
		response.setDetailMsg(DETAIL_MSG);		
		response.setPromptMsg(PROMPT_MSG);
		response.setReturnCode(RETURN_CODE);
		response.setReturnMsg(RETURN_MSG);
		response.setUserMsg(USER_MSG);
		logger.info("s4035IntChk star -xml: END");
		return response;
	}
	
	
	
	public static String parseWsGetLotteryRequest2XML(WsGetLotteryRequest request){
		String xml=null;
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>").append("\n");
		sb.append("<ROOT>").append("\n");
		sb.append("<REQUEST_INFO>").append("\n");
		sb.append("<PHONE_NO  type=\"string\">"+request.getPhoneNo()+"</PHONE_NO>").append("\n");
	    sb.append("<ACT_ID type=\"string\">"+request.getActId()+"</ACT_ID>").append("\n");
	    sb.append("</REQUEST_INFO>").append("\n");
	    sb.append("</ROOT>").append("\n");
	    xml = sb.toString();
	    return xml;
	}
	
	
	public static WsGetLotteryResponse parseXML2WsGetLotteryResponse(String xml) throws DocumentException{
		Document document =DocumentHelper.parseText(xml);
		WsGetLotteryResponse response =new WsGetLotteryResponse();
		Element root = document.getRootElement();
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");
		String RETURN_MSG = root.elementTextTrim("RETURN_MSG");
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		if(RETURN_CODE!=null&& RETURN_CODE.equals("0")){
			Element OUT_DATA = root.element("OUT_DATA");
			String COUNT_LOT = OUT_DATA.elementTextTrim("COUNT_LOT");
			List<LotInfo> list=new ArrayList<LotInfo>();
			Element LOT_LIST = OUT_DATA.element("LOT_LIST");
			if(null!=LOT_LIST){
				Iterator it_all = LOT_LIST.elementIterator("LOT_INFO");
				while(it_all.hasNext()){
					Element recordEle  = (Element)it_all.next();
					LotInfo lotInfo=new LotInfo();
					String PRIZE_LEVEL = recordEle.elementTextTrim("PRIZE_LEVEL");
					String PRIZE_NAME = recordEle.elementText("PRIZE_NAME");
					String PRIZE_DATE = recordEle.elementText("PRIZE_DATE");
					lotInfo.setPrizeLevel(PRIZE_LEVEL);
					lotInfo.setPrizeName(PRIZE_NAME);
					lotInfo.setPrizeDate(PRIZE_DATE);
					list.add(lotInfo);
				}
			}
			response.setReturnCode(RETURN_CODE);
			response.setReturnMsg(RETURN_MSG);
			response.setDetailMsg(DETAIL_MSG);
			response.setCountLot(COUNT_LOT);
			response.setLotList(list);
		}
		return response;
	}
	
	
	/**
	 * 登陆前查询营销活动
	 * @param request
	 * @return
	 */
	
	public static String parseWsGetActionRequestXML(WsGetActionRequest request){
		String xml=null;

		StringBuffer sb = new StringBuffer();
	    sb.append("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>").append("\n");
	    sb.append("<ROOT>").append("\n");
	    sb.append("<REQUEST_INFO>").append("\n");
	    
	    sb.append("<FLAG  type=\"string\">"+request.getFlag()+"</FLAG>").append("\n");//length 5	标志位	非空
	    if(null==request.getActID()){
	    	sb.append("<ACT_ID type=\"string\"></ACT_ID>").append("\n");//length 18	活动编码	可空
	    }else{
	    	sb.append("<ACT_ID type=\"string\">"+request.getActID()+"</ACT_ID>").append("\n");//length 18	活动编码	可空
	    }
	    
	    sb.append("<CHN_TYPE type=\"string\">"+request.getChnType()+"</CHN_TYPE>").append("\n");//length 渠道类型	非空
	    
	    if(null==request.getStartDate()){
	    	sb.append("<START_DATE type=\"string\"></START_DATE>").append("\n");//length 11	活动开始时间	可空
	    }else{
	    	sb.append("<START_DATE type=\"string\">"+request.getStartDate()+"</START_DATE>").append("\n");//length 11	活动开始时间	可空
	    }
	    
	    if(null==request.getEndDate()){
	    	sb.append("<END_DATE type=\"string\"></END_DATE>").append("\n");//length 11	活动结束时间	可空
	    }else{
	    	sb.append("<END_DATE type=\"string\">"+request.getEndDate()+"</END_DATE>").append("\n");//length 11	活动结束时间	可空
	    }
	    
	    if(null==request.getRegionCode()){
	    	 sb.append("<REGION_CODE type=\"string\"></REGION_CODE>").append("\n");//length 5	地市编码	可空
	    }else{
	    	 sb.append("<REGION_CODE type=\"string\">"+request.getRegionCode()+"</REGION_CODE>").append("\n");//length 5	地市编码	可空
	    }
	    
	    if(null==request.getActClass()){
	    	 sb.append("<ACT_CLASS type=\"string\"></ACT_CLASS>").append("\n");//length 5	活动类型	可空
	    }else{
	    	 sb.append("<ACT_CLASS type=\"string\">"+request.getActClass()+"</ACT_CLASS>").append("\n");//length 5	活动类型	可空
	    }
	    
	    if(null==request.getCustGruopType()){
	    	 sb.append("<CUST_GROUP_TYPE type=\"string\"></CUST_GROUP_TYPE>").append("\n");//length 5	目标客户群类型	可空
	    }else{
	    	 sb.append("<CUST_GROUP_TYPE type=\"string\">"+request.getCustGruopType()+"</CUST_GROUP_TYPE>").append("\n");//length 5	目标客户群类型	可空
	    }
	    
	    sb.append("</REQUEST_INFO>").append("\n");
	    sb.append("</ROOT>").append("\n");
	    xml = sb.toString();
		return xml;
	}
	
	/**
	 *  regino 100 返回全省活动
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * add  查询营销活动解析返回报文
	 */
public static MarketingActionResponse parseXML2MarketingActionResponse(String xml) throws DocumentException{
		
		Document document =DocumentHelper.parseText(xml);
		MarketingActionResponse response=new MarketingActionResponse();
		Element root = document.getRootElement();
		
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		String RETURN_MSG = root.elementTextTrim("RETURN_MSG");
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");
		
		if(RETURN_CODE!=null&&RETURN_CODE.equals("0")){
			Element	OUT_DATA=root.element("OUT_DATA");
			Element ACT_LIST=OUT_DATA.element("ACT_LIST");
			List<MarkingActionInfo> actList = new ArrayList();
			Iterator it_one=ACT_LIST.elementIterator("ACT_INFO");
			while(it_one.hasNext()){
				MarkingActionInfo actionInfo=new MarkingActionInfo();
				Element recordEle  = (Element)it_one.next();
				String actID=recordEle.elementTextTrim("ACT_ID");
				String actName=recordEle.elementTextTrim("ACT_NAME");	
				String mktDiction=recordEle.elementTextTrim("MKT_DICTION");		
				String custGroupID=recordEle.elementTextTrim("CUST_GROUP_ID");	
				String custGroupName=recordEle.elementTextTrim("CUST_GROUP_NAME");	
				String startDate=recordEle.elementTextTrim("START_DATE");			
				String endDate=recordEle.elementTextTrim("END_DATE");			
				String areaCode=recordEle.elementTextTrim("AREA_CODE");			
				String areaName=recordEle.elementTextTrim("AREA_NAME");			
				String actClass=recordEle.elementTextTrim("ACT_CLASS");			
				String actClassName=recordEle.elementTextTrim("ACT_CLASS_NAME");	
				Element CONTENT=recordEle.element("CONTENT");
				Element TERMIS=CONTENT.element("TERMIS");
				
				List<TermiInfo> termiList = new ArrayList();
				Iterator it_one2=TERMIS.elementIterator("TERMI");
				while(it_one2.hasNext()){
					TermiInfo termiInfo=new TermiInfo();
					Element recordEle2  = (Element)it_one2.next();
					String termiType=recordEle2.elementTextTrim("TERMI_TYPE");	
					String termiName=recordEle2.elementTextTrim("TERMI_NAME");			
					String termiPrice=recordEle2.elementTextTrim("TERMI_PRICE");			
					String termiRealPrice=recordEle2.elementTextTrim("TERMI_REAL_PRICE");	
					String termiDescribe=recordEle2.elementTextTrim("TERMI_DESCRIBE");	
					String maxNum=recordEle2.elementTextTrim("MAX_NUM");					
					String remnantNum=recordEle2.elementTextTrim("REMNANT_NUM");			
					
					termiInfo.setMaxNum(maxNum);
					termiInfo.setRemnantNum(remnantNum);
					termiInfo.setTermiDescribe(termiDescribe);
					termiInfo.setTermiName(termiName);
					termiInfo.setTermiPrice(termiPrice);
					termiInfo.setTermiRealPrice(termiRealPrice);
					termiInfo.setTermiType(termiType);
					termiList.add(termiInfo);
				}
				
				actionInfo.setActClass(actClassName);
				actionInfo.setActClassName(actClassName);
				actionInfo.setActID(actID);
				actionInfo.setActName(actName);
				actionInfo.setAreaCode(areaCode);
				actionInfo.setAreaName(areaName);
				actionInfo.setCustGroupID(custGroupID);
				actionInfo.setCustGroupName(custGroupName);
				actionInfo.setEndDate(endDate);
				actionInfo.setStartDate(startDate);
				actionInfo.setMktDiction(mktDiction);
				actionInfo.setTermiList(termiList);
				actList.add(actionInfo);
			}
			response.setActList(actList);
		}
		
		
		response.setReturnCode(RETURN_CODE);
		response.setReturnMsg(RETURN_MSG);
		response.setDetailMsg(DETAIL_MSG);
		return response;
	}
	
	public static void main(String[] args) {

		/*S4000Cfm_BReruest s4000 = new S4000Cfm_BReruest();
		BusiInfo info = new BusiInfo();
		SubProdPrc sub = new SubProdPrc();
		sub.setProdId("abc");
		List subList = new ArrayList<SubProdPrc>();
		subList.add(sub);
		info.setSubProdPrcList(subList);
		s4000.setBusiInfo(info);
		System.out.println(XStreamUtil.toXml(s4000));*/
		
		/*S4035IntCfmRequest request =new S4035IntCfmRequest();
		request.setServiceNo("15835128782");
		request.setMasterServId("1001");
		request.setProvinceGroup("10011");
		request.setOpenTime("20150325");
		request.setLoginNo("ll1868");
		request.setActId("201503231534386754");
		request.setMeansId("");
		request.setChannelType("1");
		String inxml=ParseXML.parseS4035IntCfmRequest2XML(request);
		System.out.println(inxml);*/
		WsGetLotteryRequest request=new WsGetLotteryRequest();
		request.setActId("201503303543322162");
		request.setPhoneNo("18235112720");
		String inxml=ParseXML.parseWsGetLotteryRequest2XML(request);
		System.out.println(inxml);
	}
	
	/**
	 * 登陆后查询活动请求报文拼接
	 * @param request
	 * @return
	 */
	public static String parseWsGetSaleActionRequest2XML(WsGetSaleActionRequest request){
		String xml="";
		StringBuffer sb=new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>").append("\n");
	    sb.append("<ROOT>").append("\n");
	    sb.append("<REQUEST_INFO>").append("\n");
	    
	    sb.append("<PHONE_NO  type=\"string\">"+request.getPhoneNo()+"</PHONE_NO>").append("\n");//手机号	非空
	    if(null==request.getIdNo()){
	    	sb.append("<ID_NO  type=\"string\"></ID_NO>").append("\n");//用户标识	可空
	    }else{
	    	sb.append("<ID_NO  type=\"string\">"+request.getIdNo()+"</ID_NO>").append("\n");//用户标识	可空
	    }
	    
	    sb.append("<BRAND_ID  type=\"string\">"+request.getBrandID()+"</BRAND_ID>").append("\n");//用户品牌	非空
	    sb.append("<REGION_CODE  type=\"string\">"+request.getRegionCode()+"</REGION_CODE>").append("\n");//业务区编码	非空
	    
	    if(null==request.getDistrictCode()){
	    	 sb.append("<DISTRICT_CODE  type=\"string\"></DISTRICT_CODE>").append("\n");//区县编码	可空
	    }else{
	    	 sb.append("<DISTRICT_CODE  type=\"string\">"+request.getDistrictCode()+"</DISTRICT_CODE>").append("\n");//区县编码	可空
	    }
	    
	    sb.append("<LOGIN_NO  type=\"string\">"+request.getLoginNo()+"</LOGIN_NO>").append("\n");//操作员工号	非空
	    sb.append("<CHANNEL_TYPE  type=\"string\">"+request.getChannelType()+"</CHANNEL_TYPE>").append("\n");//渠道编码	非空
	    sb.append("<GROUP_ID  type=\"string\">"+request.getGroupID()+"</GROUP_ID>").append("\n");//营业厅编码	非空

	    if(null==request.getOpCode()){
	    	 sb.append("<OP_CODE  type=\"string\"></OP_CODE>").append("\n");//业务功能代码	可空
	    }else{
	    	 sb.append("<OP_CODE  type=\"string\">"+request.getOpCode()+"</OP_CODE>").append("\n");//业务功能代码	可空
	    }
	   
	    sb.append("<FLAG  type=\"string\">"+request.getFlag()+"</FLAG>").append("\n");//营销推荐标识	非空
	    
	    if(null==request.getRedFlag()){
	    	sb.append("<RED_FLAG  type=\"string\"></RED_FLAG>").append("\n");//红名单标识	可空
	    }else{
	    	sb.append("<RED_FLAG  type=\"string\">"+request.getRedFlag()+"</RED_FLAG>").append("\n");//红名单标识	可空
	    }
	    
	    if(null==request.getBlackFlag()){
	    	sb.append("<BLACK_FLAG  type=\"string\"></BLACK_FLAG>").append("\n");//黑名单标识	可空
	    }else{
	    	sb.append("<BLACK_FLAG  type=\"string\">"+request.getBlackFlag()+"</BLACK_FLAG>").append("\n");//黑名单标识	可空
	    }
	    
	    if(null==request.getCustFlag()){
	    	sb.append("<CUST_FLAG  type=\"string\"></CUST_FLAG>").append("\n");//客户归属标识	可空
	    }else{
	    	sb.append("<CUST_FLAG  type=\"string\">"+request.getCustFlag()+"</CUST_FLAG>").append("\n");//客户归属标识	可空
	    }
	    
	    if(null==request.getMainServType()){
	    	sb.append("<MAIN_SERV_TYPE  type=\"string\"></MAIN_SERV_TYPE>").append("\n");//主体服务类型	可空
	    }else{
	    	sb.append("<MAIN_SERV_TYPE  type=\"string\">"+request.getMainServType()+"</MAIN_SERV_TYPE>").append("\n");//主体服务类型	可空
	    }
	    
	    if(null==request.getMainProdID()){
	    	sb.append("<MAIN_PROD_ID  type=\"string\"></MAIN_PROD_ID>").append("\n");//主套餐编码	可空
	    }else{
	    	sb.append("<MAIN_PROD_ID  type=\"string\">"+request.getMainProdID()+"</MAIN_PROD_ID>").append("\n");//主套餐编码	可空
	    }
	    
	    if(null==request.getActClass()){
	    	sb.append("<ACT_CLASS  type=\"string\"></ACT_CLASS>").append("\n");//活动类型编码	可空
	    }else{
	    	sb.append("<ACT_CLASS  type=\"string\">"+request.getActClass()+"</ACT_CLASS>").append("\n");//活动类型编码	可空
	    }
	    sb.append("</REQUEST_INFO>").append("\n");
	    sb.append("</ROOT>").append("\n");
	    xml=sb.toString();
	    logger.info("parseWsGetSaleActionRequest2XML :"+xml);
	    System.out.println(xml);
		return xml;
	}
	
	/**
	 * 登陆后查询活动返回报文拼接
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static WsGetSaleActionResponse parseXML2GetSaleActionResponse(String xml) throws DocumentException{
		Document document =DocumentHelper.parseText(xml);
		
		WsGetSaleActionResponse response=new WsGetSaleActionResponse();
		Element root = document.getRootElement();
		
		String RETURN_CODE = root.elementTextTrim("RETURN_CODE");
		String RETURN_MSG = root.elementTextTrim("RETURN_MSG");
		String DETAIL_MSG = root.elementTextTrim("DETAIL_MSG");
		
		if(RETURN_CODE!=null&&RETURN_CODE.equals("0")){
			Element	OUT_DATA=root.element("OUT_DATA");
			Element ACTIONS=OUT_DATA.element("ACTIONS");
			List<SaleActionLogin> actList = new ArrayList<SaleActionLogin>();
			Iterator it_one=ACTIONS.elementIterator("ACTION");
			while(it_one.hasNext()){
				SaleActionLogin actionInfo=new SaleActionLogin();
				Element recordEle  = (Element)it_one.next();
				String actID=recordEle.elementTextTrim("ACT_ID");
				String actName=recordEle.elementTextTrim("ACT_NAME");	
				String startDate=recordEle.elementTextTrim("START_DATE");			
				String endDate=recordEle.elementTextTrim("END_DATE");
				String taskId=recordEle.elementTextTrim("TASK_ID");
				String mktDiction=recordEle.elementTextTrim("MKT_DICTION");		
				String custGroupId=recordEle.elementTextTrim("CUST_GROUP_ID");
				String priorityCode=recordEle.elementTextTrim("PRIORITY_CODE");
				String isCheck=recordEle.elementTextTrim("IS_CHECK");
				String douwinFlag=recordEle.elementTextTrim("DOUWIN_FLAG");
				String currentOrder=recordEle.elementTextTrim("CURRENT_ORDER");
				String maxOrder=recordEle.elementTextTrim("MAX_ORDER");
				String preOrder=recordEle.elementTextTrim("PRE_ORDER");
				String orderNumber=recordEle.elementTextTrim("order_number");
				String actClass=recordEle.elementTextTrim("ACT_CLASS");			
				String actClassName=recordEle.elementTextTrim("ACT_CLASS_NAME");				
				List<TermiInfo> termiList = new ArrayList<TermiInfo>();
				Element CONTENT=recordEle.element("CONTENT");
				if(null!=CONTENT){
					Element TERMIS=CONTENT.element("TERMIS");
					Iterator it_one2=TERMIS.elementIterator("TERMI");
					while(it_one2.hasNext()){
						TermiInfo termiInfo=new TermiInfo();
						Element recordEle2  = (Element)it_one2.next();
						String termiType=recordEle2.elementTextTrim("TERMI_TYPE");	
						String termiName=recordEle2.elementTextTrim("TERMI_NAME");			
						String termiPrice=recordEle2.elementTextTrim("TERMI_PRICE");			
						String termiRealPrice=recordEle2.elementTextTrim("TERMI_REAL_PRICE");	
						String termiDescribe=recordEle2.elementTextTrim("TERMI_DESCRIBE");	
						String maxNum=recordEle2.elementTextTrim("MAX_NUM");					
						String remnantNum=recordEle2.elementTextTrim("REMNANT_NUM");			
						
						termiInfo.setMaxNum(maxNum);
						termiInfo.setRemnantNum(remnantNum);
						termiInfo.setTermiDescribe(termiDescribe);
						termiInfo.setTermiName(termiName);
						termiInfo.setTermiPrice(termiPrice);
						termiInfo.setTermiRealPrice(termiRealPrice);
						termiInfo.setTermiType(termiType);
						termiList.add(termiInfo);
					}
				}else{
					
				}
				actionInfo.setActClass(actClass);
				actionInfo.setActClassName(actClassName);
				actionInfo.setActId(actID);
				actionInfo.setActName(actName);
				actionInfo.setCurrentOrder(currentOrder);
				actionInfo.setCustGroupId(custGroupId);
				actionInfo.setPriorityCode(priorityCode);
				actionInfo.setDouwinFlag(douwinFlag);
				actionInfo.setEndDate(endDate);
				actionInfo.setStartDate(startDate);
				actionInfo.setMktDiction(mktDiction);
				actionInfo.setTermiList(termiList);
				actionInfo.setIsCheck(isCheck);
				actionInfo.setMaxOrder(maxOrder);
				actionInfo.setOrderNumber(orderNumber);
				actionInfo.setPreOrder(preOrder);
				actionInfo.setTaskId(taskId);
				actList.add(actionInfo);
			}
			response.setSalList(actList);
		}
		
		
		response.setReturnCode(RETURN_CODE);
		response.setReturnMsg(RETURN_MSG);
		response.setDetailMsg(DETAIL_MSG);
		return response;
	}
}
