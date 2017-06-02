package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

/**
 * Created by hasee on 2016/8/19.
 */
public class ResultProductIndexHotItemBean implements IMouldType{
    private String id; //产品id
    private String name; //产品名称
    private String tenderCondition; //投资门槛      非保险
    private String timeLimit; //产品期限      非保险

    private String annualRate; //预计收益
    private String category; //类别（optimum:固定; floating:浮动收益; insurance:保险）
    private String companyName; //保险公司
    private String trusteeFee; //托管费
    private String protectionType; //保险类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(String annualRate) {
        this.annualRate = annualRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTrusteeFee() {
        return trusteeFee;
    }

    public void setTrusteeFee(String trusteeFee) {
        this.trusteeFee = trusteeFee;
    }

    public String getProtectionType() {
        return protectionType;
    }

    public void setProtectionType(String protectionType) {
        this.protectionType = protectionType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
