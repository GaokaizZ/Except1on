package org.springrain.sinova.interfaces.sitech.sgrouptree.rep;

import org.springrain.sinova.interfaces.sitech.ResponseHead;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SGroupTreeResponse extends ResponseHead {
	@XStreamAlias(value = "OUT_DATA")
	private OutData OutData = new OutData();

	public OutData getOutData() {
		return OutData;
	}

	public void setOutData(OutData outData) {
		OutData = outData;
	}

}
