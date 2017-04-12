package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

import java.util.ArrayList;

/**
 * 服务--游轮详情之游轮信息里面动态获取房子的数量和价格  接收后台返回数据
 */
public class LinerInfo4B implements IMouldType {
    private String cabinPrice; //房子价格；
    private String cabinType; //房子类型；

    public String getCabinPrice() {
        return cabinPrice;
    }

    public void setCabinPrice(String cabinPrice) {
        this.cabinPrice = cabinPrice;
    }

    public String getCabinType() {
        return cabinType;
    }

    public void setCabinType(String cabinType) {
        this.cabinType = cabinType;
    }
}

