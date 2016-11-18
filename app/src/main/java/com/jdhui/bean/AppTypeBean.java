package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

/**
 * Created by hasee on 2016/8/20.
 */
public class AppTypeBean implements IMouldType{

    private String appType;

    public AppTypeBean(String appType) {
        this.appType = appType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}
