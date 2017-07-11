package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 海外项目列表  接收后台返回数据
 */
public class OverseaProjectList3B implements IMouldType {
    private String pid; // 项目编号
    private String path; //项目展示图片地址
    private String name; //项目名称
    private String area; //户型面积
    private String price; //起购价格

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

