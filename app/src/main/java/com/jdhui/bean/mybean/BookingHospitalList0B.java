package com.jdhui.bean.mybean;

/**
 * 服务--展示预约医院列表  准备入参
 */
public class BookingHospitalList0B {
    private final String province; //省份
    private final String city; //地级市
    private final String page; //页码
    private final String hospitalName;//搜索时输入的医院名称

    public BookingHospitalList0B(String province, String city, String hospitalName, String page) {
        this.province = province;
        this.city = city;
        this.hospitalName = hospitalName;
        this.page = page;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getPage() {
        return page;
    }

    public String getHospitalName() {
        return hospitalName;
    }
}
