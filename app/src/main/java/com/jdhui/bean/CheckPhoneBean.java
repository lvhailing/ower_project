package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

/**
 *  修改手机号（一）调接口时需要的入参
 */
public class CheckPhoneBean implements IMouldType {
	private String userId; // 用户id
	private String mobile; // 新手机号

	public CheckPhoneBean(String userId, String mobile ) {
		this.userId = userId;
		this.mobile = mobile;
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
