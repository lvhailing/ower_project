package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--高尔夫球场详情  接收后台返回数据
 */
public class GolfDetail3B implements IMouldType {

    private String id; //高尔夫球场id
    private String golfName; //高尔夫球场名称
    private String golfPhoto; //场地图片
    private String golfRights; //高尔夫权限  not：优惠价  A1：嘉宾价  A2：会员价  VIP：会员价
    private Number originalPrice; //原价
    private Number preferenialPrice; //优惠价
    private Number guestPrice; //嘉宾价
    private Number vipPrice; //会员价
    private String golfAddress; //地址
    private String golfBrief; //详情

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGolfName() {
        return golfName;
    }

    public void setGolfName(String golfName) {
        this.golfName = golfName;
    }

    public String getGolfPhoto() {
        return golfPhoto;
    }

    public void setGolfPhoto(String golfPhoto) {
        this.golfPhoto = golfPhoto;
    }

    public String getGolfRights() {
        return golfRights;
    }

    public void setGolfRights(String golfRights) {
        this.golfRights = golfRights;
    }

    public Number getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Number originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Number getPreferenialPrice() {
        return preferenialPrice;
    }

    public void setPreferenialPrice(Number preferenialPrice) {
        this.preferenialPrice = preferenialPrice;
    }

    public Number getGuestPrice() {
        return guestPrice;
    }

    public void setGuestPrice(Number guestPrice) {
        this.guestPrice = guestPrice;
    }

    public Number getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Number vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getGolfAddress() {
        return golfAddress;
    }

    public void setGolfAddress(String golfAddress) {
        this.golfAddress = golfAddress;
    }

    public String getGolfBrief() {
        return golfBrief;
    }

    public void setGolfBrief(String golfBrief) {
        this.golfBrief = golfBrief;
    }
}

