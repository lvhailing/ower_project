package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

public class MyPhotoBean implements IMouldType {

	private String userId;
	private String pictureServerURL;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPictureServerURL() {
		return pictureServerURL;
	}

	public void setPictureServerURL(String pictureServerURL) {
		this.pictureServerURL = pictureServerURL;
	}
	
}
