package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

public class GetGolfInfo1B implements IMouldType {
	private String check;
	private String code;
	private String msg;
	private GetGolfInfo2B data;

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

	public GetGolfInfo2B getData() {
		return data;
	}

	public void setData(GetGolfInfo2B data) {
		this.data = data;
	}
}
	
