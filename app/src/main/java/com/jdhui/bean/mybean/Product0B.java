package com.jdhui.bean.mybean;

/**
 * 更多--产品预约  准备入参
 */
public class Product0B {
    private String userInfoId;  //用户ID
    private String category;  //产品类型
    private String status;  //预约状态 （submit:待确认;confirm:已确认;cancel:无效预约）
    private String page;  //页码

    public Product0B(String userInfoId, String category, String status, String page) {
        this.userInfoId = userInfoId;
        this.category = category;
        this.status = status;
        this.page = page;
    }

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
