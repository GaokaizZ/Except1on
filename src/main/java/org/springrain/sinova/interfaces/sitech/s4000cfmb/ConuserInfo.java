package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ConuserInfo {
	
	/**
	 *  账户标识     非空（群用户账户）
	 */
	@XStreamAlias(value = "CONTRACT_NO")
	private String contractNo="" ;
	
	/**
	 *   付费类型   写死0:全额；可空 
	 */
	@XStreamAlias(value = "PAY_TYPE")
	private String payType="";
	
	/**
	 *   付费数值   写死0:无意义；非空
	 */
	@XStreamAlias(value = "PAY_VALUE")
	private String payValue="";
	
	/**
	 *   生效时间
	 */
	@XStreamAlias(value = "EFF_DATE")
	private String   effDate="";
	/**
	 *   失效时间
	 */
	@XStreamAlias(value = "EXP_DATE")
	private String   expDate="";
	/**
	 *   周期类型 写死0:月；可空 
	 */
	@XStreamAlias(value = "CYCLE_TYPE")
	private String   cycleType="";
	/**
	 *    有效周期  写死1；可空
	 */
	@XStreamAlias(value = "DATE_CYCLE")
	private String   dateCycle="";
	
	/**
	 *    是否明细付费  Y:有明细,N:无明细；非空--> (只有当“代费类型“选择了“代付月租”时，这个节点为Y)
	 */
	@XStreamAlias(value = "RATE_FLAG")
	private String   rateFlag="";
	
	@XStreamAlias(value = "USER_RATE")
	private UserRate userRate = new UserRate();

}
