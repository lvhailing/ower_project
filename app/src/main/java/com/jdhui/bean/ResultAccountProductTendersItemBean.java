package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

/**
 * 资产--固收投资列表
 * Created by hasee on 2016/8/17.
 *  3B：  投资列表 (account/product/tenders)
 */
public class ResultAccountProductTendersItemBean implements IMouldType{
    private String productName; //产品名称
    private String productId; //产品编号
    private String tenderId; //订单编号
    private String transAmount; //购买金额
    private String tenderAmountRate; //购买金额占比

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

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getTenderAmountRate() {
        return tenderAmountRate;
    }

    public void setTenderAmountRate(String tenderAmountRate) {
        this.tenderAmountRate = tenderAmountRate;
    }
}
