package org.springrain.sinova.dto;

import java.util.List;

/**
 * @二维码生成用 <br/>
 * @date 2015年1月13日 <br/>
 * @author yyb
 */
public class UserBusinessDTO {
    /**
     * 营业员工号
     */
    private String userCode;
    
    /**
     * 业务
     */
    private List<BusinessGoodsDTO> busGoodsList ;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<BusinessGoodsDTO> getBusGoodsList() {
        return busGoodsList;
    }

    public void setBusGoodsList(List<BusinessGoodsDTO> busGoodsList) {
        this.busGoodsList = busGoodsList;
    }
    
}
