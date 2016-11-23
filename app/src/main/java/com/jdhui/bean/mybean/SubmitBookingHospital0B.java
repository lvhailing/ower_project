package com.jdhui.bean.mybean;

/**
 * 服务--提交预约医院  准备入参
 */
public class SubmitBookingHospital0B {
    private String userId;  //用户Id
    private String hospitalId; //医院Id
    private String departments; //科室
    private String doctor; //医生
    private String bookingTime; //预约时间
    private String bakTimeOne; //备选时间1
    private String bakTimeTwo; //备选时间2
    private String illnessCondition; //主诉病情
    private String bookingClient; //预约人
    private String securityNum; //社保号码
    private String clientPhone; //预约人电话号
    private String clientIdNo; //预约人身份证号

    public SubmitBookingHospital0B(String userId, String hospitalId, String departments,String doctor,String bookingTime,String bakTimeOne,
                                   String bakTimeTwo,String illnessCondition,String bookingClient,String securityNum,String clientPhone,String clientIdNo) {
        this.userId = userId;
        this.hospitalId = hospitalId;
        this.departments = departments;
        this.doctor = doctor;
        this.bookingTime = bookingTime;
        this.bakTimeOne = bakTimeOne;
        this.bakTimeTwo = bakTimeTwo;
        this.illnessCondition = illnessCondition;
        this.bookingClient = bookingClient;
        this.securityNum = securityNum;
        this.clientPhone = clientPhone;
        this.clientIdNo = clientIdNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
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

    public String getIllnessCondition() {
        return illnessCondition;
    }

    public void setIllnessCondition(String illnessCondition) {
        this.illnessCondition = illnessCondition;
    }

    public String getBookingClient() {
        return bookingClient;
    }

    public void setBookingClient(String bookingClient) {
        this.bookingClient = bookingClient;
    }

    public String getSecurityNum() {
        return securityNum;
    }

    public void setSecurityNum(String securityNum) {
        this.securityNum = securityNum;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientIdNo() {
        return clientIdNo;
    }

    public void setClientIdNo(String clientIdNo) {
        this.clientIdNo = clientIdNo;
    }
}
