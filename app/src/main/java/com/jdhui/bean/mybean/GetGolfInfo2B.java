package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--获取高尔夫提交预约时的客户姓名和身份证  后台接口返回的数据
 */
public class GetGolfInfo2B implements IMouldType {

    private String userName; //客户姓名
    private String userIdNo; //客户身份证
    private String idType;//idCard:身份证，passport：护照，agencyCode：机构代码
    private String userMobile; //客户电话


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIdNo() {
        return userIdNo;
    }

    public void setUserIdNo(String userIdNo) {
        this.userIdNo = userIdNo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}

