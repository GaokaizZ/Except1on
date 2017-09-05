package org.springrain.sinova.interfaces.sitech.sgrouptree.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OutData {

	@XStreamAlias(value = "BUSI_INFO")
	private BusiInfo busiInfo = new BusiInfo();

	public BusiInfo getBusiInfo() {
		return busiInfo;
	}

	public void setBusiInfo(BusiInfo busiInfo) {
		this.busiInfo = busiInfo;
	}

}
