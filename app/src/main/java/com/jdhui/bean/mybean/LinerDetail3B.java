package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--游轮详情  接收后台返回数据
 */
public class LinerDetail3B implements IMouldType {
    private String id; //游轮id
    private String routeName; //游行名称
    private String infoPhoto; //游轮图片
    private String lowerTicketPrice; //最低票价
    private String routeDuration; //历时
    private String routeDestination; //游行地点（用，分开）
    private String gatewayPort; //途径港口
    private String shipName; //游轮名称
    private String starLevel; //游轮星级
    private String passgerCapacity; //载客量
    private String buildYear; //建造年份
    private String tonnage; //吨位

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getInfoPhoto() {
        return infoPhoto;
    }

    public void setInfoPhoto(String infoPhoto) {
        this.infoPhoto = infoPhoto;
    }

    public String getLowerTicketPrice() {
        return lowerTicketPrice;
    }

    public void setLowerTicketPrice(String lowerTicketPrice) {
        this.lowerTicketPrice = lowerTicketPrice;
    }

    public String getRouteDuration() {
        return routeDuration;
    }

    public void setRouteDuration(String routeDuration) {
        this.routeDuration = routeDuration;
    }

    public String getRouteDestination() {
        return routeDestination;
    }

    public void setRouteDestination(String routeDestination) {
        this.routeDestination = routeDestination;
    }

    public String getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(String gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public String getPassgerCapacity() {
        return passgerCapacity;
    }

    public void setPassgerCapacity(String passgerCapacity) {
        this.passgerCapacity = passgerCapacity;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getTonnage() {
        return tonnage;
    }

    public void setTonnage(String tonnage) {
        this.tonnage = tonnage;
    }
}

