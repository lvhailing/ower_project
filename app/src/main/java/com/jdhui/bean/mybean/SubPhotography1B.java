package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

public class SubPhotography1B implements IMouldType {
	private String check;
	private String code;
	private String msg;
	private SubPhotography2B data;

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

	public SubPhotography2B getData() {
		return data;
	}

	public void setData(SubPhotography2B data) {
		this.data = data;
	}
}
	
