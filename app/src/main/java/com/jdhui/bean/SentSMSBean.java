package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class SentSMSBean implements IMouldType {
	private String userMobile; // 用户手机号
	private String busiType; // mobileEdit:修改手机

	public SentSMSBean(String userMobile, String busiType) {
		super();
		this.userMobile = userMobile;
		this.busiType = busiType;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
}
