package org.springrain.sinova.dto;

import org.springrain.sinova.entity.Rotate;

public class RotateDTO extends Rotate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	
	/**
	 * 开始时间 查询用
	 */
	private java.util.Date beginTime;
	
	/**
	 * 结束时间 查询用
	 */
	private java.util.Date endTime;
	private String jinpin;
	
	private String stateS;
	
	public String getJinpin() {
		String tempA = this.getPrize();
		if("timeout".equals(tempA)){
			return "超时";
		}
		if("0".equals(tempA)){
			return "谢谢参与";
		}
		if("1".equals(tempA)){
			return "iPhone 6";
		}
		if("2".equals(tempA)){
			return "50元购机券";
		}
		if("3".equals(tempA)){
			return "300M流量";
		}
		if("4".equals(tempA)){
			return "100M流量";
		}
		if("5".equals(tempA)){
			return "咪咕音乐定向流量包5元500M";
		}
		if("6".equals(tempA)){
			return "和动漫定向流量包5元500M";
		}
		if("7".equals(tempA)){
			return "和视频定向流量包10元1G";
		}
		if("8".equals(tempA)){
			return "30M流量";
		}
		if("9".equals(tempA)){
			return "10M流量";
		}
		
		return jinpin;
	}

	public void setJinpin(String jinpin) {
		this.jinpin = jinpin;
	}

	public String getStateS() {
		String temB = this.getState();
		if("0".equals(temB)){
			return "未中奖";
		}
		
		if("1".equals(temB)){
			return "中奖";
		}
		
		if("3".equals(temB)){
			return "超时";
		}
		
		return stateS;
	}

	public void setStateS(String stateS) {
		this.stateS = stateS;
	}



	public java.util.Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
}
