package com.jdhui.bean;


import com.jdhui.bean.mybean.InterestListBean;
import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ResultAssetFixedProductDetailBean implements IMouldType {

	private String tenderId;			//产品订单编号
	private String type;			//产品类别
	private String productName;				//产品名称
	private String productId;		//产品编号
	private String tenderAmount;			//购买金额
	private String annualRate;				//预计收益
	private String timeLimit;		//产品期限
	private String unitNet;			//产品净值(空字符串时不显示)
	private String purchaseDate;			//购买日期
	private String establishmentDate;	//成立日期
	private String repayType;	//付息间隔
	private String remark;			//备注
	private String isAnnualReport;			//是否有年度报告		yes:有;no:无
	private MouldList<InterestListBean> interestList;	//还款计划列表

	public MouldList<InterestListBean> getInterestList() {
		return interestList;
	}

	public void setInterestList(MouldList<InterestListBean> interestList) {
		this.interestList = interestList;
	}

	public String getIsAnnualReport() {
		return isAnnualReport;
	}

	public void setIsAnnualReport(String isAnnualReport) {
		this.isAnnualReport = isAnnualReport;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(String tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public String getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
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

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(String establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}