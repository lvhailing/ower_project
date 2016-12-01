package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;
/**
 *  2B：  投资列表 (account/product/tenders)
 */
public class ResultAccountProductTendersBean implements IMouldType {

	private String tenderTotalAmount; // 该类产品购买金额
	private String type;	// 产品类别{非保险类别(固收:optimum;  浮动收益:floating; }{保险:insurance) }
	private MouldList<ResultAccountProductTendersItemBean> list;

	public String getTenderTotalAmount() {
		return tenderTotalAmount;
	}

	public void setTenderTotalAmount(String tenderTotalAmount) {
		this.tenderTotalAmount = tenderTotalAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MouldList<ResultAccountProductTendersItemBean> getList() {
		return list;
	}

	public void setList(MouldList<ResultAccountProductTendersItemBean> list) {
		this.list = list;
	}
}