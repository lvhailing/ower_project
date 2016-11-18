package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ResultNewsListContentBean implements IMouldType {

	private MouldList<ResultNewsContentBean> list;

	public MouldList<ResultNewsContentBean> getList() {
		return list;
	}

	public void setList(MouldList<ResultNewsContentBean> list) {
		this.list = list;
	}
}
	
