package com.jdhui.bean;

public class UserLoginBean {
	private String userName;
	private String password;
	private String validateCode;
	private String appid;
	
	public UserLoginBean(String userName, String password, String validateCode,
			String appid) {
		super();
		this.userName = userName;
		this.password = password;
		this.validateCode = validateCode;
		this.appid = appid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
}
