package com.jdhui.bean.mybean;

/**
 * 服务--提交预约高尔夫场地  准备入参
 */
public class SubmitBookingGolf0B {
    private String bookingTime; //预约时间
    private String clientPhone; //预约人电话号
    private String golfId; //高尔夫球场Id
    private String peersOne; //同行人1
    private String peersTwo; //同行人2

    public SubmitBookingGolf0B(String bookingTime, String clientPhone, String golfId, String peersOne, String peersTwo) {
        this.bookingTime = bookingTime;
        this.clientPhone = clientPhone;
        this.golfId = golfId;
        this.peersOne = peersOne;
        this.peersTwo = peersTwo;
    }


    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getGolfId() {
        return golfId;
    }

    public void setGolfId(String golfId) {
        this.golfId = golfId;
    }

    public String getPeersOne() {
        return peersOne;
    }

    public void setPeersOne(String peersOne) {
        this.peersOne = peersOne;
    }

    public String getPeersTwo() {
        return peersTwo;
    }

    public void setPeersTwo(String peersTwo) {
        this.peersTwo = peersTwo;
    }
}
