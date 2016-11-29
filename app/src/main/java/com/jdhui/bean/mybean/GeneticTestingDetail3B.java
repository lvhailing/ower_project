package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--展示预约基因检测详情  接收后台返回数据
 */
public class GeneticTestingDetail3B implements IMouldType {

    private String id; //基因检测 id
    private String name; //基因检测名称
    private String items; //检测项目
    private String unvipPrice; //非会员价
    private String vipPrice; //会员价
    private String balancePrice; //结算价

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getUnvipPrice() {
        return unvipPrice;
    }

    public void setUnvipPrice(String unvipPrice) {
        this.unvipPrice = unvipPrice;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(String balancePrice) {
        this.balancePrice = balancePrice;
    }
}

