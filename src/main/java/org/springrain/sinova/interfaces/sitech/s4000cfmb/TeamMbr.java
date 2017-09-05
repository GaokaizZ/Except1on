package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "TEAMMBR")
public class TeamMbr {
	/**
	 *   组信息节点操作类型
	 */
	@XStreamAlias(value = "OPERATE_TYPE")
	private String  operateType="";
	/**
	 *   组类型          亲情号码：B11    呼叫免打扰B30                                                                       | 呼叫免打扰B30
	 */
	@XStreamAlias(value = "TEAM_TYPE")
	private String  teamType="";
	
	/**
	 * 组成员角色   呼叫免打扰：3001 黑名单，3002 红名单   亲情号码：3101
	 */
	@XStreamAlias(value = "MEMBER_ROLE_ID")
	private String  memberRoleId="";
	
	@XStreamAlias(value = "MASTER_SERV_ID")
	private String  masterServId="";	
	/**
	 *  原亲情号码    可空，当修改亲情号码时非空 
	 */
	@XStreamAlias(value = "FORMER_OBJECT_ID")
	private String formerObjectId="";
	
	/**
	 *  亲情号码    非空 
	 */
	@XStreamAlias(value = "OBJECT_ID")
	private String objectId="";
	
	/**
	 *  成员短号 
	 */
	@XStreamAlias(value = "SHORT_NUM")
	private String shortNum="";


	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getTeamType() {
		return teamType;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

	public String getMemberRoleId() {
		return memberRoleId;
	}

	public void setMemberRoleId(String memberRoleId) {
		this.memberRoleId = memberRoleId;
	}

	public String getFormerObjectId() {
		return formerObjectId;
	}

	public void setFormerObjectId(String formerObjectId) {
		this.formerObjectId = formerObjectId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getShortNum() {
		return shortNum;
	}

	public void setShortNum(String shortNum) {
		this.shortNum = shortNum;
	}

	public String getOperateType() {
		return operateType;
	}

	public String getMasterServId() {
		return masterServId;
	}

	public void setMasterServId(String masterServId) {
		this.masterServId = masterServId;
	}
	
	

}
