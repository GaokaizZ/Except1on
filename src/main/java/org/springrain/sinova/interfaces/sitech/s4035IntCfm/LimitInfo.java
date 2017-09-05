package org.springrain.sinova.interfaces.sitech.s4035IntCfm;

public class LimitInfo {
	private String indexSeq;//非空 序列 一个活动可能多组
	private String teamNo;//
	private String codeName;//非空 限制条件名称
	private String limitName;//限制名称
	private String note;//备注
	private String limitTar;//是否限制他人
	private String typeName;//限制条件内容
	private String limitLevel;//非空 限制级别
	private String limitValue;//非空 限制条件值
	private String limitCode;//非空 限制编码
	private String limitNo;//限制编号
	private String limitType;//非空 限制大类
	private String levelName;//非空 大类名称
	private String passFlag;//非空 校验通过 Y通过N不通过
	private String tarName;
	private String errMsg;
	public String getIndexSeq() {
		return indexSeq;
	}
	public void setIndexSeq(String indexSeq) {
		this.indexSeq = indexSeq;
	}
	public String getTeamNo() {
		return teamNo;
	}
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getLimitName() {
		return limitName;
	}
	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLimitTar() {
		return limitTar;
	}
	public void setLimitTar(String limitTar) {
		this.limitTar = limitTar;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getLimitLevel() {
		return limitLevel;
	}
	public void setLimitLevel(String limitLevel) {
		this.limitLevel = limitLevel;
	}
	public String getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}
	public String getLimitCode() {
		return limitCode;
	}
	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode;
	}
	public String getLimitNo() {
		return limitNo;
	}
	public void setLimitNo(String limitNo) {
		this.limitNo = limitNo;
	}
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getPassFlag() {
		return passFlag;
	}
	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}
	public String getTarName() {
		return tarName;
	}
	public void setTarName(String tarName) {
		this.tarName = tarName;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
