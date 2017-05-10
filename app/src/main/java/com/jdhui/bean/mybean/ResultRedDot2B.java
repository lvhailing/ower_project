package com.jdhui.bean.mybean;


import com.jdhui.bean.ResultProductIndexBulletinBean;
import com.jdhui.bean.ResultProductIndexHotItemBean;
import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ResultRedDot2B implements IMouldType {

	private String num;  //未读数量（0:代表无未读公告）

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}