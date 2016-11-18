package com.jdhui.bean;

public class EcryptBean {
	private String check;
	private Object data;

	public EcryptBean(String check, Object data) {
		setCheck(check);
		setData(data);
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
