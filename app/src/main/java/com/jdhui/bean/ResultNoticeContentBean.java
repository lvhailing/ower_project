package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultNoticeContentBean implements IMouldType {
	
	private String title;	//产品名称
	private String description;
	private String sendTime;
	private String bulletinId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}
}
	
