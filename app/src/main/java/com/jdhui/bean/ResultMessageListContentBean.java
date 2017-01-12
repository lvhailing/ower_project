package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

/**
 * 资产--消息列表（1-1）用于接收后台返回的json数据
 */
public class ResultMessageListContentBean implements IMouldType {
	private String check;
	private String code;
	private MouldList<ResultMessageListBean> data;
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

	public MouldList<ResultMessageListBean> getData() {
		return data;
	}

	public void setData(MouldList<ResultMessageListBean> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
