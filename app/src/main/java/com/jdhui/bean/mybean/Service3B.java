package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 更多--服务预约  后台接口返回的数据
 */
public class Service3B implements IMouldType {

    private String id; //服务 id
    private String serviceItems; //服务类型，绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking
    private String bookingTime; //预约时间

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

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }
}

