package com.jdhui.bean;

/**
 * 资产产品  固定浮动保险统一使用
 * Created by hasee on 2016/8/10.
 */
public class AssetFixedListBean {

    private String productName;     //产品名称
    private String productId;       //产品编号
    private String tenderId;            //订单编号
    private String tenderAmount;        //购买金额
    private String tenderAmountRate;        //购买金额占比

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getTenderAmount() {
        return tenderAmount;
    }

    public void setTenderAmount(String tenderAmount) {
        this.tenderAmount = tenderAmount;
    }

    public String getTenderAmountRate() {
        return tenderAmountRate;
    }

    public void setTenderAmountRate(String tenderAmountRate) {
        this.tenderAmountRate = tenderAmountRate;
    }
}
