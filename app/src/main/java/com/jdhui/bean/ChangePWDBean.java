package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ChangePWDBean implements IMouldType {
	private String userId;
	private String oldPassword;
	private String password;

	public ChangePWDBean(String userId, String oldPassword, String password) {
		setOldPassword(oldPassword);
		setPassword(password);
		setUserId(userId);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
