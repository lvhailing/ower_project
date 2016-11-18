package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultFindPWDbyPhoneContent implements IMouldType {

	private String flag;
	private String msg;

	public ResultFindPWDbyPhoneContent(String flag, String msg) {
		this.flag = flag;
		this.msg = msg;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}