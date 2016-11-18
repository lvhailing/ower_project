package com.jdhui.bean;

/**
 * Created by hasee on 2016/8/20.
 */
public class UserPageBean {
    private String userId;
    private String page;

    public UserPageBean(String userId, String page) {
        this.userId = userId;
        this.page = page;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
