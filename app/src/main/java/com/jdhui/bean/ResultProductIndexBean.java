package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ResultProductIndexBean implements IMouldType {

	private ResultProductIndexBulletinBean bulletin;  //最新公告
	private MouldList<ResultProductIndexHotItemBean> list;  //热销产品

	public ResultProductIndexBulletinBean getBulletin() {
		return bulletin;
	}

	public void setBulletin(ResultProductIndexBulletinBean bulletin) {
		this.bulletin = bulletin;
	}

	public MouldList<ResultProductIndexHotItemBean> getList() {
		return list;
	}

	public void setList(MouldList<ResultProductIndexHotItemBean> list) {
		this.list = list;
	}
}