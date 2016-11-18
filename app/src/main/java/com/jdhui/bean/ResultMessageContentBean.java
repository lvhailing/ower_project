package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultMessageContentBean implements IMouldType {
	
	private String TITLE;	//产品名称
	private String TIME;
	private String CONTENT;
	private boolean IsRead;

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String TITLE) {
		this.TITLE = TITLE;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String TIME) {
		this.TIME = TIME;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String CONTENT) {
		this.CONTENT = CONTENT;
	}

	public boolean isRead() {
		return IsRead;
	}

	public void setIsRead(boolean isRead) {
		IsRead = isRead;
	}
}
	
