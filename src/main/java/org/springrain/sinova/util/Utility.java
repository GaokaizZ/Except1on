/**
 * Copyright 2014. www.sinovatech.com Inc. All rights reserved.
 * wdInspection
 * 2014年10月13日 下午2:43:01
 */
package org.springrain.sinova.util;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.system.util.Cryptos;
import org.springrain.system.util.EncodeUtils;
import org.springrain.system.util.PropertyFileCache;
import org.springrain.system.util.SHA1;

/**
 * @description TODO <br/>
 * @date 2014年10月13日 下午2:43:01 <br/>
 * @author wangbo
 */
public class Utility {
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	private static final PropertyFileCache pfCache = PropertyFileCache.getInstance();

	private Utility() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @description 将Map对象按照Key排序后把键值拼接成字符串 <br/>
	 * @date 2014年10月13日 下午2:36:08 <br/>
	 * @param argsMap
	 * @return
	 */
	public static String mapSort2String(Map<String, Object> argsMap) {
		StringBuilder sb = new StringBuilder();
		Object[] keys = argsMap.keySet().toArray();
		Arrays.sort(keys);
		for (Object key1 : keys) {
			sb.append(key1).append(argsMap.get(key1));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @description 加密属性值 <br/>
	 * @date 2015年1月12日 上午10:18:08 <br/>
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		String appkey = pfCache.get(Const.SECURITY_FILE, Const.PF_APPKEY);
		return encode(appkey, str);
	}

	/**
	 * 
	 * @description 加密属性值 <br/>
	 * @date 2014年10月16日 下午8:58:45 <br/>
	 * @param appkey
	 * @param str
	 * @return
	 */
	public static String encode(String appkey, String str) {
		logger.debug("appkey = {}, encode str = {}", appkey, str);
		if (StringUtils.isBlank(str)) {
			return str;
		}
		// 秘钥
		String secretKey = pfCache.get(Const.SECURITY_FILE, Const.PF_SECRETKEY + appkey);
		String algorithmParam = pfCache.get(Const.SECURITY_FILE, Const.PF_ALGORITHMPARAM + appkey);
		// AES加密
		String encode = EncodeUtils.hexEncode(Cryptos.aesEncrypt(str.getBytes(), EncodeUtils.hexDecode(secretKey), EncodeUtils.hexDecode(algorithmParam)));
		return encode;
	}

	/**
	 * 
	 * @description 解密属性值 <br/>
	 * @date 2015年1月12日 上午10:30:08 <br/>
	 * @param appkey
	 * @return
	 */
	public static String decode(String str) {
		String appkey = pfCache.get(Const.SECURITY_FILE, Const.PF_APPKEY);
		return decode(appkey, str);
	}

	/**
	 * 
	 * @description 解密属性值 <br/>
	 * @date 2014年10月16日 下午8:58:53 <br/>
	 * @param appkey
	 * @param str
	 * @return
	 */
	public static String decode(String appkey, String str) {
		logger.debug("appkey = {}, decode str = {}", appkey, str);
		if (StringUtils.isBlank(str)) {
			return str;
		}
		// 秘钥
		String secretKey = pfCache.get(Const.SECURITY_FILE, Const.PF_SECRETKEY + appkey);
		String algorithmParam = pfCache.get(Const.SECURITY_FILE, Const.PF_ALGORITHMPARAM + appkey);
		// AES解密
		String decode = Cryptos.aesDecrypt(EncodeUtils.hexDecode(str), EncodeUtils.hexDecode(secretKey), EncodeUtils.hexDecode(algorithmParam));
		return decode;
	}

	/**
	 * 
	 * @description 计算加盐安全认证值<br/>
	 * @date 2014年10月18日 下午6:45:39 <br/>
	 * @param strSrc
	 * @return
	 */
	public static String calcCertifiedSalt(String strSrc) {
		logger.debug("src str = {}", strSrc);
		if (StringUtils.isBlank(strSrc)) {
			return strSrc;
		}
		String certifiedSalt = pfCache.get(Const.SECURITY_FILE, "certified.salt");
		String safeValue = SHA1.Encrypt(strSrc.concat(certifiedSalt));
		logger.debug("salt = {}, dest str = {}", certifiedSalt, safeValue);
		return safeValue;
	}

}
