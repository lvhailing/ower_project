package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class InvestorJudgeBean implements IMouldType {
	private String userId;
	private String qualifiedInvestor;

	public InvestorJudgeBean(String userId, String qualifiedInvestor) {
		this.userId = userId;
		this.qualifiedInvestor = qualifiedInvestor;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQualifiedInvestor() {
		return qualifiedInvestor;
	}

	public void setQualifiedInvestor(String qualifiedInvestor) {
		this.qualifiedInvestor = qualifiedInvestor;
	}
}
