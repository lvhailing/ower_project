package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ResultFixedProductListBean implements IMouldType {

	private String category;
	private String count;
	private MouldList<ResultFixedProductListItemBean> list;


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public MouldList<ResultFixedProductListItemBean> getList() {
		return list;
	}

	public void setList(MouldList<ResultFixedProductListItemBean> list) {
		this.list = list;
	}
}