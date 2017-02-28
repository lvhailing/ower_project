package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

/**
 * 更多--服务预约详情  后台接口返回的数据
 */
public class ServiceDetail3B implements IMouldType {
    private String serviceItems; //服务类型，绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking  公务机包机：airplaneBooking
    private String bookingClient; //绿通就医和基因检测的预约人
    private String securityNum; //社保号码
    private String clientIdNo; //绿通就医预约人身份证
    private String clientPhone; //联系电话  (四种类型服务共用)
    private String bookingTime; // 绿通就医、高尔夫球场的预约时间；基因检测的提交时间
    private String bakTimeOne; //备份时间一
    private String bakTimeTwo; //备份时间二
    private String name; //绿通就医的医院名称或基因检测的检测项目名称或高尔夫球场地预约人
    private String departments; //科室
    private String doctor; //医生
    private String illnessCondition; //主诉病情
    private String userAddress; //客户地址
    private String userAge; //客户年龄
    private String userSex; //客户性别
    private String bookingStatus; //预约状态 (四种类型服务共用)
    private String bookingRemark; //驳回原因  (四种类型服务共用)
    private String golfName; //场馆名称
    private String peersOne; //同行人1
    private String PeersTwo; //同行人2
    private String golfRight; //权限
    private String idNo; //高尔夫球场的身份证
    private String createTime; //绿通就医、高尔夫球场的提交时间
    private MouldList<PlaneMarchListBean> airplaneMarch;   //行程列表

    private String clientName; //邮轮 预约人
    private String routeName; //游行名称

    private String idType;      // idType  String     idCard: 身份证号  passport：护照  agencyCode：机构代码

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
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

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getGolfName() {
        return golfName;
    }

    public void setGolfName(String golfName) {
        this.golfName = golfName;
    }

    public String getPeersOne() {
        return peersOne;
    }

    public void setPeersOne(String peersOne) {
        this.peersOne = peersOne;
    }

    public String getPeersTwo() {
        return PeersTwo;
    }

    public void setPeersTwo(String peersTwo) {
        PeersTwo = peersTwo;
    }

    public String getGolfRight() {
        return golfRight;
    }

    public void setGolfRight(String golfRight) {
        this.golfRight = golfRight;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MouldList<PlaneMarchListBean> getAirplaneMarch() {
        return airplaneMarch;
    }

    public void setAirplaneMarch(MouldList<PlaneMarchListBean> airplaneMarch) {
        this.airplaneMarch = airplaneMarch;
    }

    public String getBookingRemark() {
        return bookingRemark;
    }

    public void setBookingRemark(String bookingRemark) {
        this.bookingRemark = bookingRemark;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}

