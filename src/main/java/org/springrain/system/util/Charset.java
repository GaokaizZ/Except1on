package org.springrain.system.util;

/**
 *
 * @description 字符集编码枚举类 <br/>
 * @date 2014-6-27 下午2:11:25 <br/>
 * @author wangbo
 */
public enum Charset {
	GBK("GBK"), UTF_8("UTF-8"), ISO_8859_1("ISO-8859-1");

	private String code;

	private Charset(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return this.code;
	}

}
