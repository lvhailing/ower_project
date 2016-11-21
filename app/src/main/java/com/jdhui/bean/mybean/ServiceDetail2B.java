package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 更多--服务预约  后台接口返回的数据
 */
public class ServiceDetail2B implements IMouldType {
	
	private String id;
	private String serviceItems; //服务类型，绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking
	private String bookingClient; //绿通就医和基因检测的预约人
	private String userInfoName; //高尔夫球场地的预约人
	private String securityNum; //社保号码
	private String clientIdNo; //预约人身份证
	private String clientPhone; //绿通就医和基因检测的预约人电话
	private String bookingTime; //预约时间 3个公用
	private String bakTimeOne; //备份时间一
	private String bakTimeTwo; //备份时间二
	private String name; //绿通就医和基因检测的医院名称、或检测项目名称
	private String departments; //科室
	private String doctor; //医生
	private String illnessCondition; //主诉病情
	private String userAddress; //客户地址
	private String userAge; //客户年龄
	private String userSex; //客户性别

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceItems() {
		return serviceItems;
	}

	public void setServiceItems(String serviceItems) {
		this.serviceItems = serviceItems;
	}

	public String getBookingClient() {
		return bookingClient;
	}

	public void setBookingClient(String bookingClient) {
		this.bookingClient = bookingClient;
	}

	public String getUserInfoName() {
		return userInfoName;
	}

	public void setUserInfoName(String userInfoName) {
		this.userInfoName = userInfoName;
	}

	public String getSecurityNum() {
		return securityNum;
	}

	public void setSecurityNum(String securityNum) {
		this.securityNum = securityNum;
	}

	public String getClientIdNo() {
		return clientIdNo;
	}

	public void setClientIdNo(String clientIdNo) {
		this.clientIdNo = clientIdNo;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getBakTimeOne() {
		return bakTimeOne;
	}

	public void setBakTimeOne(String bakTimeOne) {
		this.bakTimeOne = bakTimeOne;
	}

	public String getBakTimeTwo() {
		return bakTimeTwo;
	}

	public void setBakTimeTwo(String bakTimeTwo) {
		this.bakTimeTwo = bakTimeTwo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getIllnessCondition() {
		return illnessCondition;
	}

	public void setIllnessCondition(String illnessCondition) {
		this.illnessCondition = illnessCondition;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
}
	
