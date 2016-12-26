package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 更多--产品预约详情  后台接口返回的数据
 */
public class ProductDetail2B implements IMouldType {
	
	private String productId; //产品ID
	private String productName; //产品名称
	private String userInfoName; //预约人
	private String mobile; //手机
	private String idNo; //证件号码
	private String status; //预约状态
	private String bookingTime; //预约时间
	private String bookingAmount; //预约金额
	private String bookingRemark; //备注
	private String idType; //证件号码类型

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

	public String getUserInfoName() {
		return userInfoName;
	}

	public void setUserInfoName(String userInfoName) {
		this.userInfoName = userInfoName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getBookingAmount() {
		return bookingAmount;
	}

	public void setBookingAmount(String bookingAmount) {
		this.bookingAmount = bookingAmount;
	}

	public String getBookingRemark() {
		return bookingRemark;
	}

	public void setBookingRemark(String bookingRemark) {
		this.bookingRemark = bookingRemark;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
}
	
