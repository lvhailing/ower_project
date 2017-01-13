package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

/**
 * 资产--固收投资列表
 *  1B：  投资列表 (account/product/tenders)
 */
public class ResultAccountProductTendersContentBean implements IMouldType {
	private String check;
	private String code;
	private ResultAccountProductTendersBean data;
	private String msg;

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ResultAccountProductTendersBean getData() {
		return data;
	}

	public void setData(ResultAccountProductTendersBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
