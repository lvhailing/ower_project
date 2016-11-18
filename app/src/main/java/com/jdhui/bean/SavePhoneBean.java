package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class SavePhoneBean implements IMouldType {
	private String userId;
	private String mobile;
	private String validateCode;

	public SavePhoneBean(String userId, String mobile, String validateCode) {
		this.validateCode = validateCode;
		this.userId = userId;
		this.mobile = mobile;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
