package org.springrain.sinova.dto;

import org.springrain.sinova.entity.Qrcode;

public class QrcodeDto extends Qrcode{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * name
	 */
	private java.lang.String name;

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}
	

}
