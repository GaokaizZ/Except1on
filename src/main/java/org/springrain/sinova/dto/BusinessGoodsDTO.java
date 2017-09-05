package org.springrain.sinova.dto;

import java.util.List;

public class BusinessGoodsDTO {
    /**
     * 业务编号
     */
    private String businessCode;
    /**
     * 商品编号
     */
    private List<String> goodsList;
    public String getBusinessCode() {
        return businessCode;
    }
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    public List<String> getGoodsList() {
        return goodsList;
    }
    public void setGoodsList(List<String> goodsList) {
        this.goodsList = goodsList;
    }
    
}
