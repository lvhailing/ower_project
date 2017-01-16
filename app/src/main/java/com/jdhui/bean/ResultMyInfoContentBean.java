package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultMyInfoContentBean implements IMouldType {
	private String	pictureServerURL;	//图片地址
	private String	userName;	//用户姓名
	private String  address;	//地址
	private String	idNo; //身份证号或者护照或者机构代码
	private String	idType;	 //idCard:身份证    passport：护照    agencyCode：机构代码

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

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
}

