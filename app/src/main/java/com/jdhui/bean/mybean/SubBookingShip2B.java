package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 邮轮提交预约  后台接口返回的数据
 */
public class SubBookingShip2B implements IMouldType {
	
	private String flag; //true或false   成功或失败
	private String msg; //失败信息说明

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
	
