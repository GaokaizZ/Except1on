package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias(value = "ATTR_VALUE_LIST")
public class AttrValue {
	
	/**
	 * 属性值
	 */
	@XStreamAlias(value = "ATTR_VALUE")
	private String attrValue = "";

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}


	
}
