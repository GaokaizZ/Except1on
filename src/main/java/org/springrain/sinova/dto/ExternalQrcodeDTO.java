package org.springrain.sinova.dto;

import org.apache.commons.lang3.StringUtils;
import org.springrain.sinova.entity.ExternalQrcode;

public class ExternalQrcodeDTO extends ExternalQrcode{
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String officeName;
	
	private String regionName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.userName = value;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.officeName = value;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String value) {
		if (StringUtils.isNotBlank(value)) {
	    	value = value.trim();
		}
		this.regionName = value;
	}
	
	
	

}
