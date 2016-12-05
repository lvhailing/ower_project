package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ResultFixedProductDetailBean implements IMouldType {

    private String productId;    //产品id
    private String productName;//产品名称
    private String amount;    //产品规模
    private String tenderCondition;    //投资门槛
    private String timeLimit;    //产品期限
    private String unitNet;//产品净值
    private String annualRateType;//预计收益类型(direct:直接显示;region:区间显示)
    private String annualRateDirect;    //预期收益-直接显示
    private MouldList<FixedProductDetailRateReginBean> annualRateRegion;    //区间显示

    private String repayType; //付息间隔
    private String establishmentDate;    //成立日期
    private String administrativeFee;//管理费
    private String trusteeFee;//托管费
    private String administrator;        //管理人
    private String trustee;//托管人
    private String investCoverage;//投资范围
    private String controlMeasures;//风控措施
    private String productAdvantage;    //投资亮点

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getUnitNet() {
        return unitNet;
    }

    public void setUnitNet(String unitNet) {
        this.unitNet = unitNet;
    }

    public String getAnnualRateType() {
        return annualRateType;
    }

    public void setAnnualRateType(String annualRateType) {
        this.annualRateType = annualRateType;
    }

    public String getAnnualRateDirect() {
        return annualRateDirect;
    }

    public void setAnnualRateDirect(String annualRateDirect) {
        this.annualRateDirect = annualRateDirect;
    }

    public MouldList<FixedProductDetailRateReginBean> getAnnualRateRegion() {
        return annualRateRegion;
    }

    public void setAnnualRateRegion(MouldList<FixedProductDetailRateReginBean> annualRateRegion) {
        this.annualRateRegion = annualRateRegion;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getAdministrativeFee() {
        return administrativeFee;
    }

    public void setAdministrativeFee(String administrativeFee) {
        this.administrativeFee = administrativeFee;
    }

    public String getTrusteeFee() {
        return trusteeFee;
    }

    public void setTrusteeFee(String trusteeFee) {
        this.trusteeFee = trusteeFee;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getTrustee() {
        return trustee;
    }

    public void setTrustee(String trustee) {
        this.trustee = trustee;
    }

    public String getInvestCoverage() {
        return investCoverage;
    }

    public void setInvestCoverage(String investCoverage) {
        this.investCoverage = investCoverage;
    }

    public String getControlMeasures() {
        return controlMeasures;
    }

    public void setControlMeasures(String controlMeasures) {
        this.controlMeasures = controlMeasures;
    }

    public String getProductAdvantage() {
        return productAdvantage;
    }

    public void setProductAdvantage(String productAdvantage) {
        this.productAdvantage = productAdvantage;
    }
}