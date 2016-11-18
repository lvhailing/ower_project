package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultProductIndexContentBean implements IMouldType {
	private String check;
	private String code;
	private ResultProductIndexBean data;
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

	public ResultProductIndexBean getData() {
		return data;
	}

	public void setData(ResultProductIndexBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
