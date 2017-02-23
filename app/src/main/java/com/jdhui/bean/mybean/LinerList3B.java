package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--展示豪华邮轮列表  接收后台返回数据
 */
public class LinerList3B implements IMouldType {
    private String id; //游轮id
    private String routeDuration; //历时
    private String routeName; //游行名称
    private String routeDestination; //游行地点（用，分开）
    private String lowerTicketPrice; //最低票价
    private String shipName; //游轮名称
    private String listPhoto; //列表图片

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteDuration() {
        return routeDuration;
    }

    public void setRouteDuration(String routeDuration) {
        this.routeDuration = routeDuration;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteDestination() {
        return routeDestination;
    }

    public void setRouteDestination(String routeDestination) {
        this.routeDestination = routeDestination;
    }

    public String getLowerTicketPrice() {
        return lowerTicketPrice;
    }

    public void setLowerTicketPrice(String lowerTicketPrice) {
        this.lowerTicketPrice = lowerTicketPrice;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(String listPhoto) {
        this.listPhoto = listPhoto;
    }
}

