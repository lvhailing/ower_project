package com.jdhui.bean;

public class UserProductTendersBean {


	private String type;
	private String page;
	private String userId;
	public UserProductTendersBean( String type, String page,String userId) {
		this.userId = userId;
		this.type = type;
		this.page = page;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
