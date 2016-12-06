package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

/**
 * 保险产品公共参数
 */
public class ResultAssetInsuranceProductDetailContentBean implements IMouldType {
	private String check;
	private String code;
	private ResultAssetInsuranceProductDetailBean data;
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

	public ResultAssetInsuranceProductDetailBean getData() {
		return data;
	}

	public void setData(ResultAssetInsuranceProductDetailBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
