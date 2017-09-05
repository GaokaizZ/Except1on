package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class GroupMbr {
	/**
	 *  群成员信息节点 操作类型 
	 */
	@XStreamAlias(value = "OPERATE_TYPE")
	private String operateType="";
	
	/**
	 * 群标识
	 */
	@XStreamAlias(value = "GROUP_ID")
	private String groupId="";
	
	/**
	 * 学校代码  
	 */
	@XStreamAlias(value = "SCHOOL_CODE")
	private String schoolCode="";
	
	/**
	 * 群用户的服务号码 
	 */
	@XStreamAlias(value = "GROUP_SERVICE_NO")
	private String groupServiceNo="";
	/**
	 * 群成员角色   10001   普通 10002 主角   
	 */
	@XStreamAlias(value = "MEMBER_ROLE_ID")
	private String memberRoleId="";
	
	/**
	 * 群 成员短号  
	 */
	@XStreamAlias(value = "SHORT_NUM")
	private String shortNum="" ;
	
	/**
	 *  家庭主角号码  
	 */
	@XStreamAlias(value = "MASTER_MBR_PHONE")
	private String  masterMbrPhone="" ;
	
	@XStreamAlias(value = "CONUSER_INFOIF")
	private ConuserInfo conuserInfo = new ConuserInfo();

}
