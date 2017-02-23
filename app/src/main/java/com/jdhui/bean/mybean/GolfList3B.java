package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--展示高尔夫球场列表  接收后台返回数据
 */
public class GolfList3B implements IMouldType {
    private String id; //高尔夫球场id
    private String golfName; //高尔夫球场名称
    private String listPhoto; //列表图片

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

    public String getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(String listPhoto) {
        this.listPhoto = listPhoto;
    }
}

