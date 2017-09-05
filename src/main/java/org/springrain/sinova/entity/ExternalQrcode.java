package org.springrain.sinova.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;

@Table(name = "T_EXTERNALQRCODE")
public class ExternalQrcode extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private java.lang.String id;
	private java.lang.String exName;
	private java.util.Date createTime;
	private java.lang.String exUrl;
	private java.lang.String state;
	private java.lang.String imageUrl;
	private java.lang.String imageName;
	private java.lang.String belongUser;
	private java.lang.String spare1;
	private java.lang.String spare2;
	private java.lang.String spare3;
	private java.lang.String spare4;
	private java.lang.String spare5;
	
	
	public ExternalQrcode(){
	}

	public ExternalQrcode(
		java.lang.String id
	){
		this.id = id;
	}
	
	
	//get and set
	
	@Id
    @WhereSQL(sql = "id=:ExternalQrcode_id", column = "ID")
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.id = value;
	}
	@WhereSQL(sql = "exName=:ExternalQrcode_exName", column = "EX_NAME")
	public java.lang.String getExName() {
		return exName;
	}
	public void setExName(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.exName = value;
	}
	
	@WhereSQL(sql = "createTime=:ExternalQrcode_createTime", column = "CREATE_TIME")
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	@WhereSQL(sql = "exUrl=:ExternalQrcode_exUrl", column = "EX_URL")
	public java.lang.String getExUrl() {
		return exUrl;
	}
	public void setExUrl(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.exUrl = value;
	}
	
	@WhereSQL(sql = "state=:ExternalQrcode_state", column = "STATE")
	public java.lang.String getState() {
		return state;
	}
	public void setState(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
	@WhereSQL(sql = "imageUrl=:ExternalQrcode_imageUrl", column = "IAMGE_URL")
	public java.lang.String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.imageUrl = value;
	}
	
	@WhereSQL(sql = "imageName=:ExternalQrcode_imageName", column = "IAMGE_NAME")
	public java.lang.String getImageName() {
		return imageName;
	}
	public void setImageName(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.imageName = value;
	}
	
	@WhereSQL(sql = "belongUser=:ExternalQrcode_belongUser", column = "BELONG_USER")
	public java.lang.String getBelongUser() {
		return belongUser;
	}
	public void setBelongUser(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.belongUser = value;
	}
	
	@WhereSQL(sql = "spare1=:ExternalQrcode_spare1", column = "SPARE1")
	public java.lang.String getSpare1() {
		return spare1;
	}
	public void setSpare1(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare1 = value;
	}
	@WhereSQL(sql = "spare2=:ExternalQrcode_spare2", column = "SPARE2")
	public java.lang.String getSpare2() {
		return spare2;
	}
	public void setSpare2(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare2 = value;
	}
	
	@WhereSQL(sql = "spare3=:ExternalQrcode_spare3", column = "SPARE3")
	public java.lang.String getSpare3() {
		return spare3;
	}
	public void setSpare3(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare3 = value;
	}
	
	@WhereSQL(sql = "spare4=:ExternalQrcode_spare4", column = "SPARE4")
	public java.lang.String getSpare4() {
		return spare4;
	}
	public void setSpare4(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare4 = value;
	}
	
	@WhereSQL(sql = "spare5=:ExternalQrcode_spare5", column = "SPARE5")
	public java.lang.String getSpare5() {
		return spare5;
	}
	public void setSpare5(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.spare5 = value;
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("二维码名称[").append(getExName()).append("],")
			.append("创建时间[").append(getCreateTime()).append("],")
			.append("外部链接[").append(getExUrl()).append("],")
			.append("是否有效[").append(getState()).append("],")
			.append("图片路径[").append(getImageUrl()).append("],")
			.append("图片名称[").append(getImageName()).append("],")
			.append("归属人[").append(getBelongUser()).append("],")
			.append("外链名称[").append(getSpare1()).append("],")
			.append("流水号[").append(getSpare2()).append("],")
			.append("点击量[").append(getSpare3()).append("],")
			.append("备用4[").append(getSpare4()).append("],")
			.append("备用5[").append(getSpare5()).append("],")
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Qrcode == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Qrcode other = (Qrcode)obj;
		return new EqualsBuilder()
			.append(getId(), other.getId())
			.isEquals();
	}
	
}
