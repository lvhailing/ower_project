package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

public class ResultUserLoginContentBean implements IMouldType {

	private String flag;
	private String message;
	private String userId;
	private String nickName;
	private String mobile;
	private String questionnaireRecordFlag;
	private String qualifiedInvestorFlag;
	private String totalAmountFlag;
	private String token;

	// public UserLoginResultBean(String flag, String message){
	// setFlag(flag);
	// setMessage(message);
	// }
	// {flag=true, message=, nickName=aaaasw, userId=14120415074007298439}
	public ResultUserLoginContentBean(String flag, String message,
			String userId, String nickName, String mobile,String questionnaireRecordFlag,String qualifiedInvestorFlag,String totalAmountFlag) {
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
}