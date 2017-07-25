package com.jdhui.bean.mybean;

/**
 * 服务--提交海外项目预约  准备入参
 */
public class SubOverseaProject0B {
    private String client; // 预约人
    private String clientPhone; // 预约电话
    private String houseId; // 房产项目Id
    private String financial; // 专属理财师

    public SubOverseaProject0B(String client, String clientPhone, String houseId, String financial) {
        this.client = client;
        this.clientPhone = clientPhone;
        this.houseId = houseId;
        this.financial = financial;
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }
}
