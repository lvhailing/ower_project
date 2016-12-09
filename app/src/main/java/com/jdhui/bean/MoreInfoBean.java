package com.jdhui.bean;

/**
 * 更多--我的信息  准备入参
 */
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
