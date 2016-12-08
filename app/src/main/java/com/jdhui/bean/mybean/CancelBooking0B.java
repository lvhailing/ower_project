package com.jdhui.bean.mybean;

/**
 * 服务--提交基因检测预约  准备入参
 */
public class CancelBooking0B {
    private String id; //服务Id
    private String serviceItems; //服务类型
    private String name; //医院名称
    private String departments; //科室
    private String bookingTime; //预约时间
    private String golfName; //高尔夫球场名称

    public CancelBooking0B(String id, String serviceItems, String name, String departments, String bookingTime, String golfName) {
        this.id = id;
        this.serviceItems = serviceItems;
        this.name = name;
        this.departments = departments;
        this.bookingTime = bookingTime;
        this.golfName = golfName;
    }

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

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getGolfName() {
        return golfName;
    }

    public void setGolfName(String golfName) {
        this.golfName = golfName;
    }
}
