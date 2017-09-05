package org.springrain.sinova.interfaces.sitech.s4035IntChk.rep;

import org.springrain.sinova.interfaces.sitech.ResponseHead;



/**
 * 
 * 类的说明需要如下信息：营销活动校验返回结果 目的: 采用的不变量： 并行策略： 作者:zhangbin 创建时间:2012-9-11 上午09:35:56
 * 修改历史： 已知问题： 是否建议使用
 */
public class S4035IntChkResponse extends ResponseHead {
	private String passFlag; // 最外层passflag
	private MeanAll meanALl;// MEAN_ALL
	private MeanOne meanOne;// MEAN_ONE

	public S4035IntChkResponse(String passFlag, MeanAll meanALl,
			MeanOne meanOne) {
		super();
		this.passFlag = passFlag;
		this.meanALl = meanALl;
		this.meanOne = meanOne;
	}

	public S4035IntChkResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public MeanAll getMeanALl() {
		return meanALl;
	}

	public void setMeanALl(MeanAll meanALl) {
		this.meanALl = meanALl;
	}

	public MeanOne getMeanOne() {
		return meanOne;
	}

	public void setMeanOne(MeanOne meanOne) {
		this.meanOne = meanOne;
	}

}