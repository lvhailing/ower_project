package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

public class ResultSentSMSContentBean implements IMouldType {
	private String result;
	private String flag;
	private String message;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}