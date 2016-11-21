package com.jdhui.bean.mybean;

/**
 * 更多--服务预约详情  准备入参
 */
public class ServiceDetail0B {
    private String id;  //服务Id
    private String serviceItems; //服务类型，绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking

    public ServiceDetail0B(String id, String serviceItems) {
        this.id = id;
        this.serviceItems = serviceItems;
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
}
