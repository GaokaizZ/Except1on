package org.springrain.sinova.interfaces.sitech.s4000cfmb;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BusiInfo {
/**
 * 操作类型 A-订购 U-修改 D-退订 N-保持   资费的操作类型传N，属性的操作类型传U 
 */
	@XStreamAlias(value = "OPERATE_TYPE")
	private String  operateType="";
	/**
	 *打包产品资费标识
	 */
	@XStreamAlias(value = "PACKGE_PRCID")
	private String   packgePricId="";
	
	@XStreamAlias(value = "FORMER_PRCID")
	private String   formerPrcId="";
	/**
	 *产品标识
	 */
	@XStreamAlias(value = "PROD_ID")
	private String  prodId="";
	/**
	 * 产品资费标识
	 */
	@XStreamAlias(value = "PROD_PRCID")
	private String   prodPrcId=""; 
	/**
	 * 生效时间
	 */
	@XStreamAlias(value = "EFF_DATE")
	private String   effDate="";
	/**
	 * 失效时间
	 */
	@XStreamAlias(value = "EXP_DATE")
	private String   expDate="";
	
	/**
	 * 到期是否失效
	 */
	@XStreamAlias(value = "DEALTYPE")
	private String   dealType="";
	
	@XStreamAlias(value = "SP_INFO")
    private SpInfo   spInfo = new SpInfo();
    
	@XStreamAlias(value = "ATTR_LIST")
  //  private  Attr   attr = new Attr();
	private  Attr   attr;
	
	@XStreamAlias(value = "TEAMMBR_LIST")
  //  private  TeamMbr teamMbr = new TeamMbr();
	private  TeamMbr teamMbr;
	
	@XStreamAlias(value = "GROUPMBR")
  //  private  GroupMbr groupMbr = new  GroupMbr();
    private  GroupMbr groupMbr;
	
	@XStreamAlias(value = "SUB_PRODPRC_LIST")
    private List<SubProdPrc>subProdPrcList = new ArrayList<SubProdPrc>();
	
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getPackgePricId() {
		return packgePricId;
	}

	public void setPackgePricId(String packgePricId) {
		this.packgePricId = packgePricId;
	}

	public String getFormerPrcId() {
		return formerPrcId;
	}

	public void setFormerPrcId(String formerPrcId) {
		this.formerPrcId = formerPrcId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdPrcId() {
		return prodPrcId;
	}

	public void setProdPrcId(String prodPrcId) {
		this.prodPrcId = prodPrcId;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public SpInfo getSpInfo() {
		return spInfo;
	}

	public void setSpInfo(SpInfo spInfo) {
		this.spInfo = spInfo;
	}

	public Attr getAttr() {
		return attr;
	}

	public void setAttr(Attr attr) {
		this.attr = attr;
	}

	public TeamMbr getTeamMbr() {
		return teamMbr;
	}

	public void setTeamMbr(TeamMbr teamMbr) {
		this.teamMbr = teamMbr;
	}

	public GroupMbr getGroupMbr() {
		return groupMbr;
	}

	public void setGroupMbr(GroupMbr groupMbr) {
		this.groupMbr = groupMbr;
	}

	public String getOperateType() {
		
		return operateType;
	}

	public List<SubProdPrc> getSubProdPrcList() {
		return subProdPrcList;
	}

	public void setSubProdPrcList(List<SubProdPrc> subProdPrcList) {
		this.subProdPrcList = subProdPrcList;
	}

    
    
}
