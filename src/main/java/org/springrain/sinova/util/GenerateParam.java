/**
 * Copyright 2014. www.sinovatech.com Inc. All rights reserved.
 * wdInspection
 * 2014年10月18日 上午9:47:39
 */
package org.springrain.sinova.util;

import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springrain.system.exceptions.BaseRuntimeException;
import org.springrain.system.util.Punctuation;

import com.alibaba.fastjson.JSONObject;

/**
 * @description TODO <br/>
 * @date 2015年1月12日 上午10:34:33 <br/>
 * @author wangbo
 */
public class GenerateParam {

	private String url;

	public GenerateParam() {
	}

	public GenerateParam(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @description 把参数按照生成规则，生成字符串 <br/>
	 * @date 2015年1月12日 上午11:07:58 <br/>
	 * @param params
	 * @return
	 */
	public String generateStringArg(JSONObject params) {
		return generateStringArg(params, null);
	}

	/**
	 * 
	 * @description 把参数按照生成规则，生成字符串 <br/>
	 * @date 2015年1月12日 上午11:07:51 <br/>
	 * @param params
	 * @param certifiedValue
	 *            安全认证码
	 * @return
	 */
	public String generateStringArg(JSONObject params, String certifiedValue) {
		if (params == null) {
			return StringUtils.EMPTY;
		}

		try {
			String sortString = Utility.mapSort2String(params);
			if (StringUtils.isNotBlank(certifiedValue)) {
				sortString += certifiedValue;
			}

			String sigValue = Utility.encode(sortString);
			params.put(Const.UNIFY_SIG, sigValue);

			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNoneBlank(this.url)) {
				sb.append(this.url).append(Punctuation.WEN_HAO.toString());
			} else {
				sb.append(Punctuation.WEN_HAO.toString());
			}

			for (Entry<String, Object> entry : params.entrySet()) {
				sb.append(entry.getKey()).append(Punctuation.DENG_HAO).append(entry.getValue()).append(Punctuation.DIZHI_HAO);
			}
			// 剔除最后一个取地址符号
			if (StringUtils.length(sb) > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.toString();

		} catch (Exception e) {
			throw new BaseRuntimeException("编码转换发生错误");
		}

		// String jsonString = JSON.toJSONString(params);
		// return jsonString;
	}

}
