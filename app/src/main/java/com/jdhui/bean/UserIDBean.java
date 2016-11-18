package com.jdhui.bean;

public class UserIDBean {
	private String userId;
	private String token;

	public UserIDBean(String userId) {
		this.userId = userId;
	}

	public UserIDBean(String userId, String token) {
		this.userId = userId;
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
