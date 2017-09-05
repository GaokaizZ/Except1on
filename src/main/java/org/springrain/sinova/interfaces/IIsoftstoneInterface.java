package org.springrain.sinova.interfaces;

import javax.xml.rpc.ServiceException;

import org.springrain.sinova.interfaces.isoftstone.checkTokenCode.rep.CheckTokenCodeResponse;
import org.springrain.sinova.interfaces.isoftstone.customaryInfo.rep.CustomaryInfoResponse;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.rep.MobileLogonResponse;
import org.springrain.sinova.interfaces.isoftstone.sendTokenCode.rep.SendTokenCodeResponse;
import org.springrain.system.entity.User;

public interface IIsoftstoneInterface {

	/**
	 * 4A查询bossno
	 * 
	 * @param fourA
	 * @return
	 */
	CustomaryInfoResponse search4A2BossNo(String fourA);

	/**
	 * 4A校验
	 * 
	 * @param u
	 * @return
	 */
	MobileLogonResponse validate4A(User u,String shortmsg) throws Exception;

	/**
	 * 4A短信下发
	 * 
	 * @param u
	 * @return
	 */
	SendTokenCodeResponse sendTokenCode(User u);

	/**
	 * 4A短信校验
	 * 
	 * @param u
	 * @return
	 */
	CheckTokenCodeResponse checkTokenCode(User u);

}
