package org.springrain.sinova.interfaces.sitech.sloginno.rep;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OutData {

	@XStreamAlias(value = "COUNT_NUM")
	private String countNum;
	@XStreamAlias(value = "COUNT_PAGE")
	private String countPage;
	@XStreamAlias(value = "PAGE_NUM")
	private String pageNum;
	@XStreamAlias(value = "DATA")
	private Data data = new Data();

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

	public String getCountPage() {
		return countPage;
	}

	public void setCountPage(String countPage) {
		this.countPage = countPage;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
