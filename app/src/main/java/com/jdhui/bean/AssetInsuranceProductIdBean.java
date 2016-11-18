package com.jdhui.bean;

/**
 * Created by hasee on 2016/8/17.
 */
public class AssetInsuranceProductIdBean {

    private String userId;
    private String tenderId;

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
