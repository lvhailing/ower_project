package com.jdhui.bean.mybean;

/**
 * 服务--展示预约基因检测列表  准备入参
 */
public class GeneticTestingList0B {
    private  String page; //页码

    public GeneticTestingList0B(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
