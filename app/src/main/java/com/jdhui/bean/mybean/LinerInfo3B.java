package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--游轮详情之游轮信息  接收后台返回数据
 */
public class LinerInfo3B implements IMouldType {
    private String startPoint; //离港地点
    private String endPiont; //抵港地点
    private String startTime; //离港时间
    private String endTime; //抵港时间
    private String lowerTicketPrice; //最低票价
    private String routeDuration; //历时
    private String shipId; //游轮Id
    private String shipTime; //游行时间
    private String innerRoom; //内仓房
    private String seaviewRoom; //海景房
    private String balconyRoom; //阳台房
    private String suiteRoom; //套房
    private String shipCount; //航期数

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPiont() {
        return endPiont;
    }

    public void setEndPiont(String endPiont) {
        this.endPiont = endPiont;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public String getSuiteRoom() {
        return suiteRoom;
    }

    public void setSuiteRoom(String suiteRoom) {
        this.suiteRoom = suiteRoom;
    }

    public String getInnerRoom() {
        return innerRoom;
    }

    public void setInnerRoom(String innerRoom) {
        this.innerRoom = innerRoom;
    }

    public String getBalconyRoom() {
        return balconyRoom;
    }

    public void setBalconyRoom(String balconyRoom) {
        this.balconyRoom = balconyRoom;
    }

    public String getSeaviewRoom() {
        return seaviewRoom;
    }

    public void setSeaviewRoom(String seaviewRoom) {
        this.seaviewRoom = seaviewRoom;
    }

    public String getShipCount() {
        return shipCount;
    }

    public void setShipCount(String shipCount) {
        this.shipCount = shipCount;
    }

}

