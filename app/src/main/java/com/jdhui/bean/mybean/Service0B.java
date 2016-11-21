package com.jdhui.bean.mybean;

/**
 * 更多--服务预约  准备入参
 */
public class Service0B {
    private String page;  //页码
    private String serviceItems; //服务类型，绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking

    public Service0B(String page, String serviceItems) {
        this.page = page;
        this.serviceItems = serviceItems;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getServiceItems() {
        return serviceItems;
    }

    public void setServiceItems(String serviceItems) {
        this.serviceItems = serviceItems;
    }
}
