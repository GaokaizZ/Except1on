package org.springrain.sinova.interfaces.sitech.wsgetsalemeanscontent.rep;

import java.util.List;


public class A10 {
	private String modifyFlag; 
	private List<MonthReturn> monthreturnList;
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	public List<MonthReturn> getMonthreturnList() {
		return monthreturnList;
	}
	public void setMonthreturnList(List<MonthReturn> monthreturnList) {
		this.monthreturnList = monthreturnList;
	}
	public A10() {
		super();
	} 
	
}