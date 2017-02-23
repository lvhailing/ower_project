package com.jdhui.bean.mybean;

/**
 * 服务--展示豪华邮轮游列表  准备入参
 */
public class LinerList0B {
    private  String page; //页码

    public LinerList0B(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
