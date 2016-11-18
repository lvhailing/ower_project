package com.jdhui.bean;

public class ProductListBean {

	private String category;			//产品类型：取值有（optimum:固收，floating:浮动收益）
	private int page;

	public ProductListBean(String category, int page) {
		this.category = category;
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getType() {
		return category;
	}

	public void setType(String type) {
		this.category = type;
	}
}
