package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class VerifyPassWordBean implements IMouldType {
	private String userId;
	private String password;

	public VerifyPassWordBean(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
