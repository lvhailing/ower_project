package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

public class MyAccountBean implements IMouldType {
	private String userId;

	public MyAccountBean() {
	}

	public MyAccountBean(String userId) {
		setUserId(userId);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
