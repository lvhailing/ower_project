package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

public class GolfList1B implements IMouldType {
	private String check;
	private String code;
	private String msg;
	private GolfList2B data;

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

	public GolfList2B getData() {
		return data;
	}

	public void setData(GolfList2B data) {
		this.data = data;
	}
}
	