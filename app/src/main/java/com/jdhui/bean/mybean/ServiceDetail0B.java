package com.jdhui.bean.mybean;

/**
 * 更多--服务预约详情  准备入参
 */
public class ServiceDetail0B {
    private String id;  //服务Id
    private String serviceItems; //服务类型，绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking  公务机包机：airplaneBooking  豪华游轮:luxuryShipBooking

    public ServiceDetail0B(String serviceItems, String id) {
        this.serviceItems = serviceItems;
        this.id = id;
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
