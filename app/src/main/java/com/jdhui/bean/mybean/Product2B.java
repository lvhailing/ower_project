package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class Product2B implements IMouldType {

	private MouldList<Product3B> list;

	public MouldList<Product3B> getList() {
		return list;
	}

	public void setList(MouldList<Product3B> list) {
		this.list = list;
	}
}
	
