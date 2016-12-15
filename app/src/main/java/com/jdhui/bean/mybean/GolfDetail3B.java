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
    private String originalPrice; //原价
    private String preferenialPrice; //优惠价
    private String guestPrice; //嘉宾价
    private String vipPrice; //会员价
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

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPreferenialPrice() {
        return preferenialPrice;
    }

    public void setPreferenialPrice(String preferenialPrice) {
        this.preferenialPrice = preferenialPrice;
    }

    public String getGuestPrice() {
        return guestPrice;
    }

    public void setGuestPrice(String guestPrice) {
        this.guestPrice = guestPrice;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }
}

