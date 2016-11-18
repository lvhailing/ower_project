package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultNoticeListBean implements IMouldType {

	private String check;
	private String code;
	private ResultNoticeListContentBean data;
	private String msg;

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ResultNoticeListContentBean getData() {
		return data;
	}

	public void setData(ResultNoticeListContentBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
	
