package com.jdhui.bean.mybean;

import com.jdhui.bean.ResultCycleIndexBean;
import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ServicePicture1B implements IMouldType {
	private String check;
	private String code;
	private String msg;
	private MouldList<ServicePicture2B> data;

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

	public MouldList<ServicePicture2B> getData() {
		return data;
	}

	public void setData(MouldList<ServicePicture2B> data) {
		this.data = data;
	}
}
	
