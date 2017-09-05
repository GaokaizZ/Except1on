package org.springrain.sinova.interfaces.sitech.sloginno.rep;

import org.springrain.sinova.interfaces.sitech.ResponseHead;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SLoginNoQryResponse extends ResponseHead {
	@XStreamAlias(value = "OUT_DATA")
	private OutData OutData = new OutData();

	public OutData getOutData() {
		return OutData;
	}

	public void setOutData(OutData outData) {
		OutData = outData;
	}

}
