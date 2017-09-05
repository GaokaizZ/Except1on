package org.springrain.sinova.interfaces.sitech.sloginno.req;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class RequestInfo {

	@XStreamAlias(value = "OPR_INFO")
	private OprInfo oprInfo = new OprInfo();

	@XStreamAlias(value = "BUSI_INFO_LIST")
	private List<BusiInfo> busiInfoList = new ArrayList<BusiInfo>();

	public OprInfo getOprInfo() {
		return oprInfo;
	}

	public void setOprInfo(OprInfo oprInfo) {
		this.oprInfo = oprInfo;
	}

	public List<BusiInfo> getBusiInfoList() {
		return busiInfoList;
	}

	public void setBusiInfoList(List<BusiInfo> busiInfoList) {
		this.busiInfoList = busiInfoList;
	}

}
