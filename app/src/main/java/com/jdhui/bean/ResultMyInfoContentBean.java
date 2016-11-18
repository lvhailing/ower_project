package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultMyInfoContentBean implements IMouldType {
	private String	pictureServerURL;	//图片地址
	private String	userName;	//用户姓名
	private String  address;	//地址
	private String	idNo;		//身份证号

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}

