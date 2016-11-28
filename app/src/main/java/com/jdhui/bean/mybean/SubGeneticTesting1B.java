package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

public class SubGeneticTesting1B implements IMouldType {
	private String check;
	private String code;
	private String msg;
	private SubGeneticTesting2B data;

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

	public SubGeneticTesting2B getData() {
		return data;
	}

	public void setData(SubGeneticTesting2B data) {
		this.data = data;
	}
}
	
