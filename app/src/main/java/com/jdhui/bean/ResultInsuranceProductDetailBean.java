package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultInsuranceProductDetailBean implements IMouldType {

	private String productId;			//产品id
	private String productName;			//产品名称
	private String type;				//保险类型
	private String recommendations;		//推荐说明
	private String companyName;			//保险公司
	private String guaranteeType;				//投保方式
	private String insuranceCoverage;		//投保范围
	private String timeLimit;			//保险期间

	private String payType;			//缴费方式
	private String riskTips;	//风险提醒
	private String advertisePictue;	//保险详情展示图片

	public String getAdvertisePictue() {
		return advertisePictue;
	}

	public void setAdvertisePictue(String advertisePictue) {
		this.advertisePictue = advertisePictue;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getInsuranceCoverage() {
		return insuranceCoverage;
	}

	public void setInsuranceCoverage(String insuranceCoverage) {
		this.insuranceCoverage = insuranceCoverage;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRiskTips() {
		return riskTips;
	}

	public void setRiskTips(String riskTips) {
		this.riskTips = riskTips;
	}
}