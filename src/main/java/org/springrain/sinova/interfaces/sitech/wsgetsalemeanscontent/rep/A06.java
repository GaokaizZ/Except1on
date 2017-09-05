package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep;

import java.util.List;

public class A06 {
	private String prcId;
	//合约一体化 资费id是个list 修改
	private List<String>prcIdList;
	
	public A06() {
		super();
	}

	public A06(String prcId) {
		super();
		this.prcId = prcId;
	}

	public String getPrcId() {
		return prcId;
	}

	public void setPrcId(String prcId) {
		this.prcId = prcId;
	}

	public List<String> getPrcIdList() {
		return prcIdList;
	}

	public void setPrcIdList(List<String> prcIdList) {
		this.prcIdList = prcIdList;
	}
	
}
