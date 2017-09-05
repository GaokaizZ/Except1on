package org.springrain.sinova.interfaces.sitech.sGetAction;

import java.util.List;

import org.springrain.sinova.interfaces.sitech.ResponseHead;

/**

 *
 */
public class MarketingActionResponse extends ResponseHead{
	private List<MarkingActionInfo> actList;

	public List<MarkingActionInfo> getActList() {
		return actList;
	}

	public void setActList(List<MarkingActionInfo> actList) {
		this.actList = actList;
	} 
	
}
