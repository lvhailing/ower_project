package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 更多--产品预约  后台接口返回的数据
 */
public class Product3B implements IMouldType {
	
	private String id;
	private String category; //产品类型
	private String productName; //产品名称
	private String status; //预约状态
	private String bookingTime; //预约时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
}
	
