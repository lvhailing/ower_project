package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class FeedBackBean implements IMouldType {
	private String userId;
	private String content;

	public FeedBackBean(String userId, String content) {
		this.userId = userId;
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
