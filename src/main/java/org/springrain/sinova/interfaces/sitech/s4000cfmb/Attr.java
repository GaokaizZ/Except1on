package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "ATTR_LIST")
public class Attr {
	
	/**
	 * 属性的操作类型
	 */
	@XStreamAlias(value = "OPERATE_TYPE")
	private String   operateType="";
	/**
	 * 属性类型
	 */
	@XStreamAlias(value = "ATTR_TYPE")
	private String   attrType="";
	
	/**
	 * 服务标识 可空，当ATTR_TYPE=SVC时非空时以此为准 
	 */
	@XStreamAlias(value = "SVC_ID")
	private String   svcId="";
	/**
	 * 属性标识
	 */
	@XStreamAlias(value = "ATTR_ID")
	private String  attrId="";
	
	/**
	 * 属性值
	 */
	@XStreamAlias(value = "ATTR_VALUE_LIST")
	private List<AttrValue> attrValueList = new ArrayList<AttrValue>();


	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getSvcId() {
		return svcId;
	}

	public void setSvcId(String svcId) {
		this.svcId = svcId;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}


	public List<AttrValue> getAttrValueList() {
		return attrValueList;
	}

	public void setAttrValueList(List<AttrValue> attrValueList) {
		this.attrValueList = attrValueList;
	}

	public String getOperateType() {
	
		return operateType;
	}
	
	
}
