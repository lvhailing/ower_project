package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

/**
 * 产品--固收、浮收列表(1B)
 * 用于接收后台返回的json数据；
*/
public class ResultFixedProductListContentBean implements IMouldType {
	private String check;
	private String code;
	private ResultFixedProductListBean data;
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

	public ResultFixedProductListBean getData() {
		return data;
	}

	public void setData(ResultFixedProductListBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
