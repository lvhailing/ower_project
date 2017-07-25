package com.jdhui.bean.mybean;



import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

import java.util.ArrayList;

// 海外项目详情
public class OverseaProjectDetail2B implements IMouldType {
    private String pid; // 项目编号
    private String projectImg; // 项目展示图片地址
    private String name; // 项目名字
    private String price; // 项目价格
    private String area; // 项目面积
    private String location; // 项目位置
    private String category; // 类型
    private String total; // 项目体量
    private String decorateStandard; // 装修标准
    private String projectDesc; // 项目描述
    private String houseType; // 项目居室
    private ArrayList<String> houseTypeImg; // 项目居室图片
    private String supportFacility; // 配套设施
    private String geographyLocation; // 地理位置


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProjectImg() {
        return projectImg;
    }

    public void setProjectImg(String projectImg) {
        this.projectImg = projectImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDecorateStandard() {
        return decorateStandard;
    }

    public void setDecorateStandard(String decorateStandard) {
        this.decorateStandard = decorateStandard;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public ArrayList<String> getHouseTypeImg() {
        return houseTypeImg;
    }

    public void setHouseTypeImg(ArrayList<String> houseTypeImg) {
        this.houseTypeImg = houseTypeImg;
    }

    public String getSupportFacility() {
        return supportFacility;
    }

    public void setSupportFacility(String supportFacility) {
        this.supportFacility = supportFacility;
    }

    public String getGeographyLocation() {
        return geographyLocation;
    }

    public void setGeographyLocation(String geographyLocation) {
        this.geographyLocation = geographyLocation;
    }

}