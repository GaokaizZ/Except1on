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
 * @version  2015-01-09 09:13:39
 * @see org.springrain.sinova.entity.Qrcode
 */
@Table(name = "T_QRCODE")
public class Qrcode extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "二维码表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_QRCODE_NAME = "二维码名称";
	public static final String ALIAS_DETAIL_DESC = "描述";
	public static final String ALIAS_BELONG_USER = "归属人";
	public static final String ALIAS_CREATE_USER = "创建人";
	public static final String ALIAS_CREATE_DATE = "创建时间";
	public static final String ALIAS_ICON_NAME = "图片名称";
	public static final String ALIAS_ICON_URL = "图片地址";
	public static final String ALIAS_STATE = "是否有效";
    */
	
	//date formats
	//public static final String FORMAT_CREATE_DATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * 主键
	 */
	private java.lang.String id;
	/**
	 * 二维码名称
	 */
	private java.lang.String qrcodeName;
	/**
	 * 描述
	 */
	private java.lang.String detailDesc;
	/**
	 * 归属人
	 */
	private java.lang.String belongUser;
	/**
	 * 创建人
	 */
	private java.lang.String createUser;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 图片名称
	 */
	private java.lang.String iconName;
	/**
	 * 图片地址
	 */
	private java.lang.String iconUrl;
	/**
	 * 是否有效
	 */
	private java.lang.String state;
	
	/**
	 * 流水号
	 */
	private String flowNo;
	/**
	 * 签名串
	 */
	private String sig;
	
	/**
	 * 参数串
	 */
	private String paramStr;
	
	private String type;
	
	//columns END 数据库字段结束
	
	//concstructor

	public Qrcode(){
	}

	public Qrcode(
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
    @WhereSQL(sql = "id=:Qrcode_id", column = "ID")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setQrcodeName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.qrcodeName = value;
	}
	
    @WhereSQL(sql = "qrcodeName=:Qrcode_qrcodeName", column = "QRCODE_NAME")
	public java.lang.String getQrcodeName() {
		return this.qrcodeName;
	}
	
	public void setDetailDesc(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.detailDesc = value;
	}
	
    @WhereSQL(sql = "detailDesc=:Qrcode_detailDesc", column = "DETAIL_DESC")
	public java.lang.String getDetailDesc() {
		return this.detailDesc;
	}
	
	public void setBelongUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.belongUser = value;
	}
	
    @WhereSQL(sql = "belongUser=:Qrcode_belongUser", column = "BELONG_USER")
	public java.lang.String getBelongUser() {
		return this.belongUser;
	}
	
	public void setCreateUser(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.createUser = value;
	}
	
    @WhereSQL(sql = "createUser=:Qrcode_createUser", column = "CREATE_USER")
	public java.lang.String getCreateUser() {
		return this.createUser;
	}
	/*
	public String getcreateDateString() {
		return DateUtils.convertDate2String(FORMAT_CREATE_DATE, getcreateDate());
	}
	public void setcreateDateString(String value) throws ParseException{
		setcreateDate(DateUtils.convertString2Date(FORMAT_CREATE_DATE,value));
	}
	*/
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
    @WhereSQL(sql = "createDate=:Qrcode_createDate", column = "CREATE_DATE")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setIconName(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.iconName = value;
	}
	
    @WhereSQL(sql = "iconName=:Qrcode_iconName", column = "ICON_NAME")
	public java.lang.String getIconName() {
		return this.iconName;
	}
	
	public void setIconUrl(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.iconUrl = value;
	}
	
    @WhereSQL(sql = "iconUrl=:Qrcode_iconUrl", column = "ICON_URL")
	public java.lang.String getIconUrl() {
		return this.iconUrl;
	}
	
	public void setState(java.lang.String value) {
	    if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.state = value;
	}
	
    @WhereSQL(sql = "state=:Qrcode_state", column = "STATE")
	public java.lang.String getState() {
		return this.state;
	}
    
    
	
	/**
	 * @return the flowNo
	 */
    @WhereSQL(sql = "flowNo=:Qrcode_flowNo", column = "FLOW_NO")
	public String getFlowNo() {
		return this.flowNo;
	}

	/**
	 * @param flowNo the flowNo to set
	 */
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	/**
	 * @return the sig
	 */
	@WhereSQL(sql = "sig=:Qrcode_sig", column = "SIG")
	public String getSig() {
		return this.sig;
	}

	/**
	 * @param sig the sig to set
	 */
	public void setSig(String sig) {
		this.sig = sig;
	}

	
	/**
	 * @return the paramStr
	 */
	@WhereSQL(sql = "paramStr=:Qrcode_paramStr", column = "PARAM_STR")
	public String getParamStr() {
		return this.paramStr;
	}

	/**
	 * @param paramStr the paramStr to set
	 */
	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}
	
	/**
	 * @return the type
	 */
	@WhereSQL(sql = "type=:Qrcode_type", column = "TYPE")
	public String getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return new StringBuffer()
			.append("主键[").append(getId()).append("],")
			.append("二维码名称[").append(getQrcodeName()).append("],")
			.append("描述[").append(getDetailDesc()).append("],")
			.append("归属人[").append(getBelongUser()).append("],")
			.append("创建人[").append(getCreateUser()).append("],")
			.append("创建时间[").append(getCreateDate()).append("],")
			.append("图片名称[").append(getIconName()).append("],")
			.append("图片地址[").append(getIconUrl()).append("],")
			.append("是否有效[").append(getState()).append("],")
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

	
