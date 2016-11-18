package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultMoreInfoContentBean implements IMouldType {
	private String	pictureServerURL;	//图片地址
	private String	userName;	//用户姓名
	private String  mobile;	//手机号
	private String	userInfoId;		//用户编号

	public String getPictureServerURL() {
		return pictureServerURL;
	}

	public void setPictureServerURL(String pictureServerURL) {
		this.pictureServerURL = pictureServerURL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}
}
