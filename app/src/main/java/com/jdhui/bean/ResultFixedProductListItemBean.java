package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

/**
 * Created by hasee on 2016/8/16.
 */
public class ResultFixedProductListItemBean implements IMouldType {

    private String productId;               //产品ID
    private String productName;             //产品名称
    private String sellingStatus;           //是否热销(normal:正常;hotsell:热销)
    private String tenderCondition;         //投资门槛
    private String timeLimit;               //产品期限
    private String annualRateDirect;        //预计收益

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellingStatus() {
        return sellingStatus;
    }

    public void setSellingStatus(String sellingStatus) {
        this.sellingStatus = sellingStatus;
    }

    public String getTenderCondition() {
        return tenderCondition;
    }

    public void setTenderCondition(String tenderCondition) {
        this.tenderCondition = tenderCondition;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getAnnualRateDirect() {
        return annualRateDirect;
    }

    public void setAnnualRateDirect(String annualRateDirect) {
        this.annualRateDirect = annualRateDirect;
    }
}
