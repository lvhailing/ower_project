package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

/**
 * 修改手机号（二）调接口时需要的入参
 */
public class SavePhoneBean implements IMouldType {
	private String userId; // 用户ID
	private String mobile; // 修改后的手机号
	private String validateCode; // 验证码

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
