package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

// 海外项目详情
public class OverseaProjectDetail3B implements IMouldType {
    private String hid; // 房源编号
    private String hname; // 房源名称
    private String hType; // 房源居室
    private String hArea; // 房源面积
    private String hCoverImg; // 房源封面图片
    private String hPrice; // 房源价格
    private String pid; // 价格

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String gethType() {
        return hType;
    }

    public void sethType(String hType) {
        this.hType = hType;
    }

    public String gethArea() {
        return hArea;
    }

    public void sethArea(String hArea) {
        this.hArea = hArea;
    }

    public String gethCoverImg() {
        return hCoverImg;
    }

    public void sethCoverImg(String hCoverImg) {
        this.hCoverImg = hCoverImg;
    }

    public String gethPrice() {
        return hPrice;
    }

    public void sethPrice(String hPrice) {
        this.hPrice = hPrice;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}