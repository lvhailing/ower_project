package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ResultInsuranceProductBean implements IMouldType {

	private String count;	//产品总数
	private String type;	//产品类型
	private MouldList<ResultInsuranceProductItemBean> list;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MouldList<ResultInsuranceProductItemBean> getList() {
		return list;
	}

	public void setList(MouldList<ResultInsuranceProductItemBean> list) {
		this.list = list;
	}
}