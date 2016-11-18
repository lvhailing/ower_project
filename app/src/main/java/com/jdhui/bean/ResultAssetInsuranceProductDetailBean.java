package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultAssetInsuranceProductDetailBean implements IMouldType {

	private String tenderId;			//产品订单编号
	private String type;			//保险类型（healthInsurance:健康险;accidentInsurance:意外险;lifeInsurance:人寿险;propertyInsurance:财产险;travelInsurance:旅游险）
	private String productName;				//产品名称
	private String insuranceDate;		//投保日期
	private String timeLimit;			//保险期限
	private String payLimit;				//缴费期间
	private String coverageAmount;		//保额
	private String premiumAmount;			//保费
	private String effectiveDate;			//生效日期
	private String renewalDate;	//续费日期
	private String policyholder;	//投保人
	private String policyholderIdNo;			//投保人身份证
	private String insured;			//被保人
	private String insuredIdNo;			//被保人身份证
	private String beneficiary;			//受益人
	private String remark;			//备注
	private String productId;			//产品id

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInsuranceDate() {
		return insuranceDate;
	}

	public void setInsuranceDate(String insuranceDate) {
		this.insuranceDate = insuranceDate;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getPayLimit() {
		return payLimit;
	}

	public void setPayLimit(String payLimit) {
		this.payLimit = payLimit;
	}

	public String getCoverageAmount() {
		return coverageAmount;
	}

	public void setCoverageAmount(String coverageAmount) {
		this.coverageAmount = coverageAmount;
	}

	public String getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(String renewalDate) {
		this.renewalDate = renewalDate;
	}

	public String getPolicyholder() {
		return policyholder;
	}

	public void setPolicyholder(String policyholder) {
		this.policyholder = policyholder;
	}

	public String getPolicyholderIdNo() {
		return policyholderIdNo;
	}

	public void setPolicyholderIdNo(String policyholderIdNo) {
		this.policyholderIdNo = policyholderIdNo;
	}

	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public String getInsuredIdNo() {
		return insuredIdNo;
	}

	public void setInsuredIdNo(String insuredIdNo) {
		this.insuredIdNo = insuredIdNo;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}