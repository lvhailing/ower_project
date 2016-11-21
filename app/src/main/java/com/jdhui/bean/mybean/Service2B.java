package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class Service2B implements IMouldType {

	private MouldList<Service3B> list;

	public MouldList<Service3B> getList() {
		return list;
	}

	public void setList(MouldList<Service3B> list) {
		this.list = list;
	}
}
	
