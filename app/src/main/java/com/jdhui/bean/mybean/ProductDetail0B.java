package com.jdhui.bean.mybean;

/**
 * 更多--产品预约详情  准备入参
 */
public class ProductDetail0B {
    private String id;  //用户Id
    private String category; //产品类型

    public ProductDetail0B(String id, String category) {
        this.id = id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
