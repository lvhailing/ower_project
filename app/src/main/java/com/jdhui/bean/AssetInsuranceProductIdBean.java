package com.jdhui.bean;

/**
 * 保险投资详情  准备入参
 * Created by hasee on 2016/8/17.
 */
public class AssetInsuranceProductIdBean {

    private String userId; //用户ID
    private String tenderId; //订单编号

    public AssetInsuranceProductIdBean(String userId, String tenderId) {
        this.userId = userId;
        this.tenderId = tenderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }
}
