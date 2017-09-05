package org.springrain.sinova.dto;


/**
 * @ztree 展示数据用 <br/>
 * @date 2015年1月13日 <br/>
 * @author yyb
 */
public class TreeBusinessGoodsDTO {
    /**
     * 业务编号
     */
    private String businessCode;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 产品编号
     */
    private String goodsCode;
    /**
     * 产品名称
     */
    private String goodsName;
    
    /**
     * 短名称
     */
    private String sortName;
    
    
    
    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    
}
