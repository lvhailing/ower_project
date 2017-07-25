package com.jdhui.bean.mybean;

/**
 * 服务--海项目详情  准备入参
 */
public class OverseaProjectDetail0B {
    private  String pid; // 项目编号

    public OverseaProjectDetail0B(String pid) {
        this.pid = pid;
    }

    public String getPage() {
        return pid;
    }

    public void setPage(String pid) {
        this.pid = pid;
    }
}
