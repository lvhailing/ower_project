package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultAccountIndexBean implements IMouldType {

    private String totalAmount;// 总资产
    private String optimumAmount;    // 固收类购买总额
    private String optimumAmountRate;// 固收类购买总额百分比(0到100，不加百分号)
    private String floatingAmount;// 浮动收益类购买总额
    private String floatingAmountRate;// 浮动收益类购买总额百分比(0到100，不加百分号)
    private String insuranceAmount;    // 保险购买总额
    private String insuranceAmountRate;// 保险购买总额百分比 (0到100，不加百分号)
    private String unreadMessageNum;  // 未读消息数量
    private String qualifiedInvestor;// acceptable:合格投资者;unacceptable:不合格投资者
    private String userType; // conservative:保守型;steady:稳健型;balance:平衡型;growth:成长型;aggressive:进取型;
    private String incomeAmount;// 用户已获得收益

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOptimumAmount() {
        return optimumAmount;
    }

    public void setOptimumAmount(String optimumAmount) {
        this.optimumAmount = optimumAmount;
    }

    public String getOptimumAmountRate() {
        return optimumAmountRate;
    }

    public void setOptimumAmountRate(String optimumAmountRate) {
        this.optimumAmountRate = optimumAmountRate;
    }

    public String getFloatingAmount() {
        return floatingAmount;
    }

    public void setFloatingAmount(String floatingAmount) {
        this.floatingAmount = floatingAmount;
    }

    public String getFloatingAmountRate() {
        return floatingAmountRate;
    }

    public void setFloatingAmountRate(String floatingAmountRate) {
        this.floatingAmountRate = floatingAmountRate;
    }

    public String getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(String insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getInsuranceAmountRate() {
        return insuranceAmountRate;
    }

    public void setInsuranceAmountRate(String insuranceAmountRate) {
        this.insuranceAmountRate = insuranceAmountRate;
    }

    public String getUnreadMessageNum() {
        return unreadMessageNum;
    }

    public void setUnreadMessageNum(String unreadMessageNum) {
        this.unreadMessageNum = unreadMessageNum;
    }

    public String getQualifiedInvestor() {
        return qualifiedInvestor;
    }

    public void setQualifiedInvestor(String qualifiedInvestor) {
        this.qualifiedInvestor = qualifiedInvestor;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }
}