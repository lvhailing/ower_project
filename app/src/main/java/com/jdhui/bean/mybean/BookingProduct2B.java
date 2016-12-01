package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

/**
 * 非保险预约  后台接口返回的数据
 */
public class BookingProduct2B implements IMouldType {
	private String message; //预约成功/预约失败

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
	
