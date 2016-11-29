package com.jdhui.bean.mybean;

/**
 * 服务--提交基因检测预约  准备入参
 */
public class SubGeneticTesting0B {
    private String geneticTestingId; //基因检测Id
    private String userSex; //预约人性别
    private String userAge; //预约人年龄
    private String userAddress; //预约人地址
    private String bookingClient; //预约人
    private String clientPhone; //预约人电话号

    public SubGeneticTesting0B(String geneticTestingId, String userSex, String userAge, String userAddress, String bookingClient, String clientPhone) {
        this.geneticTestingId = geneticTestingId;
        this.userSex = userSex;
        this.userAge = userAge;
        this.userAddress = userAddress;
        this.bookingClient = bookingClient;
        this.clientPhone = clientPhone;
    }

    public String getGeneticTestingId() {
        return geneticTestingId;
    }

    public void setGeneticTestingId(String geneticTestingId) {
        this.geneticTestingId = geneticTestingId;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getBookingClient() {
        return bookingClient;
    }

    public void setBookingClient(String bookingClient) {
        this.bookingClient = bookingClient;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
}
