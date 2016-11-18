package com.jdhui.bean;

public class MoreInfoBean {
	private String userInfoId;

	public MoreInfoBean(String userId) {
		this.userInfoId = userId;
	}

	public String getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}
}
