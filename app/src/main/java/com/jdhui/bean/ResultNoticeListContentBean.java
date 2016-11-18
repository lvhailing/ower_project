package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ResultNoticeListContentBean implements IMouldType {

	private MouldList<ResultNoticeContentBean> list;

	public MouldList<ResultNoticeContentBean> getList() {
		return list;
	}

	public void setList(MouldList<ResultNoticeContentBean> list) {
		this.list = list;
	}
}
	
