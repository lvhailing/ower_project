package com.jdhui.bean;

public class InsuranceListBean {

	private String type;	//保险类型：健康险、意外险、人寿险、财产险、旅游险 healthInsurance:健康险;accidentInsurance:意外险;lifeInsurance:人寿险;propertyInsurance:财产险;travelInsurance:旅游险
	private String page;

	public InsuranceListBean(String type, String page) {
		this.type = type;
		this.page = page;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
}
