package org.springrain.sinova.util;

/**
 *
 * @description TODO <br/>
 * @date 2014-6-24 下午4:18:35 <br/>
 * @author wangbo
 */
public enum Const {
	INSTANCE;

	// 文件后缀
	public static final String PROPERTIES_SUFFIX = ".properties";
	public static final String XML_SUFFIX = ".xml";

	// LOG前缀
	public static final String LOG_PREFIX = "com.sinovatech.unifiedsoa";

	// 属性文件
	public static final String SECURITY_FILE = "security.properties";
	public static final String SYSTEM_FILE = "system.properties";

	// 加解密相关key
	public static final String PF_APPKEY = "platform.appKey";
	public static final String PF_SECRETKEY = "platform.secretKey.";
	public static final String PF_ALGORITHMPARAM = "platform.algorithmParam.";

	// 跳转服务接口
	public static final String UNIFY_APPKEY = "appkey";
	public static final String UNIFY_METHOD = "method";
	public static final String UNIFY_V = "v";
	public static final String UNIFY_LOGINID = "loginid";
	public static final String UNIFY_PARAMS = "params";
	public static final String UNIFY_JSESSIONID = "jsessionid";
	public static final String UNIFY_SIG = "sig";

	// 接口地址
	public static final String INTERFACE_FILE = "interface.properties";

	// 短信验证码
	public static final String SEND_VERIFICATION_CODE = "尊敬的山西移动用户：您好，感谢您通过二维码办理业务，您本次的短信验证码为：";
	
	// 短信验证码
	public static final String SEND_VERIFICATION_CODE_BACK = "尊敬的用户：您好，您正在登录二维码直通车后台管理系统，您本次的短信验证码为：";
	
	// 短信验证码
	public static final String SEND_VERIFICATION_CODE_ROTATE = "尊敬的用户：您好，您正在登录二维码直通车系统，您本次的短信验证码为：";
	
	// 验证码
	public static final String RAND_IMAGE_VALIDATE = "RAND_IMAGE_VALIDATE_";

	// 倒计时
	public static final String COUNT_DOWN = "COUNT_DOWN_";
	
	// 营业厅管理员岗位编码
	public static final String USER_OFFICE_MANAGER = "2001";
	
	// 营业员岗位编码
	public static final String USER_OFFICE_ASSISTANT = "2002";

	// 二维码param
	public static final String QRCODE_PARAM_STR = "QRCODE_PARAM_STR_";

	// 全省编码（所有地市）
	public static final String REGION_ALL = "999";
	
	
	//防止重复提交
	public static final String RESUBMIT_DATE_TAG = "RESUBMIT_DATE_TAG_";
	
}


