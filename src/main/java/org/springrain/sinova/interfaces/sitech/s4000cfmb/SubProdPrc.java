package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "SUB_PRODPRC")
public class SubProdPrc {
	/**
	 *     可选子产品信息节点 操作类型；  A-订购 U-修改 D-退订 
	 */
	@XStreamAlias(value = "OPERATE_TYPE")
	private String   operateType = "";
	
	/**
	 *    SUB_PRODPRC 资费标识 
	 */
	@XStreamAlias(value = "PROD_PRCID")
	private String    prodPrcId = "";
	
	/**
	 *    SUB_PRODPRC 产品标识 
	 */
	@XStreamAlias(value = "PROD_ID")
	private String   prodId = "";

	public SubProdPrc(){
		super();
	}
	public SubProdPrc(String operateType,String prodPrcId,String prodId){
		super();  
		this.operateType =  operateType;
		this.prodPrcId = prodPrcId;
		this.prodId = prodId;
	}
	
	
	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getProdPrcId() {
		return prodPrcId;
	}

	public void setProdPrcId(String prodPrcId) {
		this.prodPrcId = prodPrcId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	
	
}
