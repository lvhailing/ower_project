package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

public class ServicePicture2B implements IMouldType {
    private String picture;
    private String type; //绿通就医：hospitalBooking 基因检测：geneticBooking  高尔夫球场：golfBooking  公务机包机：airplaneBooking  豪华邮轮游：shipBooking 海外置业：houseBooking 海外医疗：overseasBooking

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

