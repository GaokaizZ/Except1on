package org.springrain.sinova.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 08:57:50
 * @see org.springrain.sinova.entity.Rotate
 */
@Table(name = "T_ROTATE")
public class Rotate extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
 
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 奖品
	 */
	private java.lang.String prize;
	/**
	 * 是否成功（0否/1是）
	 */
	private java.lang.String state;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	
	/**
	 * 所属地市
	 */
	private java.lang.String groupId;
	
	/**
	 * 批次
	 */
	private java.lang.String batCode;
	
	/**
	 * 入参
	 */
	private java.lang.String inXml;
	
	/**
	 * 出参
	 */
	private java.lang.String outXml;
	
	/**
	 * 活动编码
	 */
	private java.lang.String actId;
	
	/**
	 * 档次编码
	 */
	private java.lang.String meanId;
	
	/**
	 * 办理记录id
	 */
	private java.lang.String acceptId;
	
	/**
	 * 是否办理
	 */
	private java.lang.String lotteryed;

	public Rotate(){
	}

	public Rotate(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	
	public void setId(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.id = value;
	}
	
	@Id
    @WhereSQL(sql = "id=:Rotate_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Rotate_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
	
	
    @WhereSQL(sql = "state=:Rotate_prize", column = "PRIZE")
	public java.lang.String getPrize() {
		return prize;
	}

	public void setPrize(java.lang.String prize) {
		this.prize = prize;
	}
	
	@WhereSQL(sql = "state=:Rotate_time", column = "CREATE_TIME")
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	@WhereSQL(sql = "state=:Rotate_mobile", column = "MOBILE")
	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	@WhereSQL(sql = "state=:Rotate_groupId", column = "GROUP_ID")
	public java.lang.String getGroupId() {
		return groupId;
	}

	public void setGroupId(java.lang.String groupId) {
		this.groupId = groupId;
	}
	
	@WhereSQL(sql = "state=:Rotate_batCode", column = "BAT_CODE")
	public java.lang.String getBatCode() {
		return batCode;
	}

	public void setBatCode(java.lang.String batCode) {
		this.batCode = batCode;
	}

	@WhereSQL(sql = "state=:Rotate_inXml", column = "IN_XMl")
	public java.lang.String getInXml() {
		return inXml;
	}
	
	public void setInXml(java.lang.String inXml) {
		this.inXml = inXml;
	}
	
	@WhereSQL(sql = "state=:Rotate_outXml", column = "OUT_XML")
	public java.lang.String getOutXml() {
		return outXml;
	}

	public void setOutXml(java.lang.String outXml) {
		this.outXml = outXml;
	}
	
	@WhereSQL(sql = "state=:Rotate_actId", column = "ACT_ID")
	public java.lang.String getActId() {
		return actId;
	}

	public void setActId(java.lang.String actId) {
		this.actId = actId;
	}

	@WhereSQL(sql = "state=:Rotate_meanId", column = "MEAN_ID")
	public java.lang.String getMeanId() {
		return meanId;
	}

	public void setMeanId(java.lang.String meanId) {
		this.meanId = meanId;
	}
	
	@WhereSQL(sql = "acceptId=:Rotate_acceptId", column = "ACCEPT_ID")
	public java.lang.String getAcceptId() {
		return acceptId;
	}
	
	public void setAcceptId(java.lang.String acceptId) {
		this.acceptId = acceptId;
	}

	@WhereSQL(sql = "lotteryed=:Rotate_lotteryed", column = "LOTTERYED")
	public java.lang.String getLotteryed() {
		return lotteryed;
	}

	public void setLotteryed(java.lang.String lotteryed) {
		this.lotteryed = lotteryed;
	}

	@Override
	public String toString() {
		return "Rotate [id=" + id + ", prize=" + prize + ", state=" + state
				+ ", createTime=" + createTime + ", mobile=" + mobile
				+ ", groupId=" + groupId + ", batCode=" + batCode+", inXml=" + inXml + ", outXml="
				+ outXml + "]";
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Rotate == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Rotate other = (Rotate)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
}

	
