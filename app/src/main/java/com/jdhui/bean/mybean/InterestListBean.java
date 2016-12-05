package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

public class InterestListBean implements IMouldType {
	private String repayDate;			//还款日期
	private String interestAmount;			//利息金额
	private String amount;				//本息金额

	public InterestListBean(String repayDate, String interestAmount, String amount) {
		this.repayDate = repayDate;
		this.interestAmount = interestAmount;
		this.amount = amount;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public String getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(String interestAmount) {
		this.interestAmount = interestAmount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}