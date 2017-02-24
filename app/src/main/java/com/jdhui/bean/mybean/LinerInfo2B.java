package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

import java.util.ArrayList;

/**
 * 服务--游轮详情之游轮信息  接收后台返回的数据
 */
public class LinerInfo2B implements IMouldType {
    private ArrayList<ArrayList<LinerInfo3B>> voyageInfo;

    public ArrayList<ArrayList<LinerInfo3B>> getVoyageInfo() {
        return voyageInfo;
    }

    public void setVoyageInfo(ArrayList<ArrayList<LinerInfo3B>> voyageInfo) {
        this.voyageInfo = voyageInfo;
    }
}

	
