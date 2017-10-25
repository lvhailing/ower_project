package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

public class ResultUserLoginContentBean implements IMouldType {

	private String flag; // true/false,true为登录成功，false为登录失败
	private String message; // 登录成功或失败信息
	private String userId; // 用户ID
	private String nickName; // 用户昵称
	private String mobile; // 手机号
	private String questionnaireRecordFlag; // 判断是否答题false:未答题 true:已答题
	private String qualifiedInvestorFlag; // 是否做过合格投资者判定 false:未做合格投资者判定 true:做过合格投资者判定
	private String totalAmountFlag; // 账户资产是否大于300万false:判定为非合格投资者 true:判定为合格投资者
	private String token;
	private String realName; // 真实姓名

	// public UserLoginResultBean(String flag, String message){
	// setFlag(flag);
	// setMessage(message);
	// }
	// {flag=true, message=, nickName=aaaasw, userId=14120415074007298439}
	public ResultUserLoginContentBean(String flag, String message, String userId, String nickName, String mobile,
									  String questionnaireRecordFlag,String qualifiedInvestorFlag,String totalAmountFlag) {
		setFlag(flag);
		setMessage(message);
		setUserId(userId);
		setNickName(nickName);
		setMobile(mobile);
		setQuestionnaireRecordFlag(questionnaireRecordFlag);
		setQualifiedInvestorFlag(qualifiedInvestorFlag);
		setTotalAmountFlag(totalAmountFlag);
		setToken(token);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getQuestionnaireRecordFlag() {
		return questionnaireRecordFlag;
	}

	public void setQuestionnaireRecordFlag(String questionnaireRecordFlag) {
		this.questionnaireRecordFlag = questionnaireRecordFlag;
	}

	public String getQualifiedInvestorFlag() {
		return qualifiedInvestorFlag;
	}

	public void setQualifiedInvestorFlag(String qualifiedInvestorFlag) {
		this.qualifiedInvestorFlag = qualifiedInvestorFlag;
	}

	public String getTotalAmountFlag() {
		return totalAmountFlag;
	}

	public void setTotalAmountFlag(String totalAmountFlag) {
		this.totalAmountFlag = totalAmountFlag;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}