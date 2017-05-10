package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultNoticeContentBean implements IMouldType {
	
	private String title;	//公告标题
	private String description;
	private String content; // 公告内容
	private String sendTime; // 发送时间
	private String bulletinId; // 公告编号
	private String readState; // 公告是否阅读（yes:已阅读；no:未阅读）

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReadState() {
		return readState;
	}

	public void setReadState(String readState) {
		this.readState = readState;
	}
}
	
