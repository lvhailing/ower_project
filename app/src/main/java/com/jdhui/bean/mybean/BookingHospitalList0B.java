package com.jdhui.bean.mybean;

/**
 * 服务--展示预约医院列表  准备入参
 */
public class BookingHospitalList0B {
    private  String hospitalName;//搜索时输入的医院名称
    private  String province; //省份
    private  String city; //地级市
    private  String page; //页码

    public BookingHospitalList0B(String province, String city, String hospitalName, String page) {
        this.province = province;
        this.city = city;
        this.hospitalName = hospitalName;
        this.page = page;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
