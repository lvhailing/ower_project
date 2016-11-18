package com.jdhui.bean;

/**
 * Created by hasee on 2016/8/17.
 */
public class AssetProductIdBean {

    private String userId;
    private String tenderId;
    private String type;

    public AssetProductIdBean(String userId, String tenderId, String type) {
        this.userId = userId;
        this.tenderId = tenderId;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
