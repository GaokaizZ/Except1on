package org.springrain.sinova.interfaces.sitech.sGetSaleAction.rep;

import java.util.List;

import org.springrain.sinova.interfaces.sitech.ResponseHead;

/**
 * 登陆后营销活动
 * @author lida
 *
 */
public class WsGetSaleActionResponse extends ResponseHead {
	private List<SaleActionLogin> salList;

	public List<SaleActionLogin> getSalList() {
		return salList;
	}

	public void setSalList(List<SaleActionLogin> salList) {
		this.salList = salList;
	}
}
