package com.jdhui.bean.mybean;

/**
 * 服务--展示高尔夫球场地列表  准备入参
 */
public class GolfList0B {
    private  String page; //页码

    public GolfList0B(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
