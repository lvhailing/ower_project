package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

public class Service1B implements IMouldType {
	private String check;
	private String code;
	private String msg;
	private Service2B data;

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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Service2B getData() {
		return data;
	}

	public void setData(Service2B data) {
		this.data = data;
	}
}
	
