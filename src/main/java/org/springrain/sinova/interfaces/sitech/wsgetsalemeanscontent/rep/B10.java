package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep;

import java.util.List;


public class B10 {
	private String modifyFlag; 
	private String servNo;
	private List<MonthReturn> monthreturnList;
	
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	public String getServNo() {
		return servNo;
	}
	public void setServNo(String servNo) {
		this.servNo = servNo;
	}
	public List<MonthReturn> getMonthreturnList() {
		return monthreturnList;
	}
	public void setMonthreturnList(List<MonthReturn> monthreturnList) {
		this.monthreturnList = monthreturnList;
	}
	public B10() {
		super();
	}
	
}
