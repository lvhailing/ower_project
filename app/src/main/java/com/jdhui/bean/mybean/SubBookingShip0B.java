package com.jdhui.bean.mybean;

/**
 * 邮轮提交预约  准备入参
 */
public class SubBookingShip0B {
    private String clientPhone; //预约电话
    private String shipId; //游轮Id
    private String clientName; //预约人

    public SubBookingShip0B(String clientPhone, String shipId, String clientName) {
        this.clientPhone = clientPhone;
        this.shipId = shipId;
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
