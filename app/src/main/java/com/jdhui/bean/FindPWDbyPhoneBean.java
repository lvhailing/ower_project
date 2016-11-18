package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class FindPWDbyPhoneBean implements IMouldType {
	private String mobile;
	private String newPassword;
	private String userName;
	private String validateCode;

	public FindPWDbyPhoneBean(String mobile, String newPassword, String userName, String validateCode) {
		this.mobile = mobile;
		this.newPassword = newPassword;
		this.userName = userName;
		this.validateCode = validateCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
}
