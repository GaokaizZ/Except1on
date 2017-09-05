/**
 * Copyright 2014. www.sinovatech.com Inc. All rights reserved.
 * wdInspection
 * 2014年10月18日 上午9:47:39
 */
package org.springrain.sinova.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springrain.system.exceptions.BaseRuntimeException;
import org.springrain.system.util.Punctuation;

import com.alibaba.fastjson.JSONObject;

/**
 * @description TODO <br/>
 * @date 2015年1月12日 上午11:48:29 <br/>
 * @author wangbo
 */
public class VerifyParam {

	public VerifyParam() {
	}

	/**
	 * 
	 * @description 验证签名信息 <br/>
	 * @date 2015年1月12日 上午11:52:02 <br/>
	 * @param paramString
	 */
	public static void verifySing(String paramString) {
		if (StringUtils.isBlank(paramString)) {
			throw new IllegalArgumentException("参数串为空");
		}
		// 如果疑问号开始，剔除该问号
		if (StringUtils.startsWith(paramString, Punctuation.WEN_HAO.toString())) {
			paramString = StringUtils.substring(paramString, 1);
		}
		String[] paramArr = StringUtils.split(paramString, Punctuation.DIZHI_HAO.toString());
		if (ArrayUtils.isEmpty(paramArr)) {
			throw new IllegalArgumentException("参数串分解数组为空");
		}

		String sig = null;
		JSONObject paramsObj = new JSONObject();
		for (String param : paramArr) {
			String[] array = StringUtils.splitPreserveAllTokens(param, Punctuation.DENG_HAO.toString());
			if (ArrayUtils.isEmpty(array) || array.length != 2) {
				continue;
			}
			if (StringUtils.equals(array[0], Const.UNIFY_SIG)) {
				sig = array[1];
				continue;
			}
			paramsObj.put(array[0], array[1]);
		}

		String sortString = Utility.mapSort2String(paramsObj);
		String sigTmp = Utility.encode(sortString);

		if (!StringUtils.equals(sig, sigTmp)) {// 不相等，说明参数被篡改
			throw new BaseRuntimeException("参数数据存在异常，签名验证不通过。");
		}
	}

}
