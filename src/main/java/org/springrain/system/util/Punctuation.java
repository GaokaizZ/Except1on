package org.springrain.system.util;

/**
 *
 * @description 标点符号枚举类 <br/>
 * @date 2014-6-27 下午2:11:25 <br/>
 * @author wangbo
 */
public enum Punctuation {
	// 默认符号
	DEFAULT("!#!"),

	// 感叹号
	GANTAN_HAO("!"),
	// 艾特号
	AITE_HAO("@"),
	// 井号
	JING_HAO("#"),
	// 美元号
	MEIYUAN_HAO("$"),
	// 百分号
	BAIFEN_HAO("%"),

	// 取地址号
	DIZHI_HAO("&"),
	// 星号
	XING_HAO("*"),

	// 下划线
	XIAHUA_XIAN("_"),
	// 逗号
	DOU_HAO(","),
	// 冒号
	MAO_HAO(":"),
	// 等号
	DENG_HAO("="),
	// 问号
	WEN_HAO("?"),
	// 连接号
	LIANJIE_HAO("-");

	private String code;

	private Punctuation(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return this.code;
	}

}
