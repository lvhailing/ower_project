package com.jdhui.bean.mybean;

/**
 * 服务-- 提交私人摄影盛宴的预约  准备入参
 */
public class SubPhotography0B {
    private String client; // 预约人
    private String clientPhone; // 预约电话
    private String financial; // 专属理财师

    public SubPhotography0B(String client, String clientPhone, String financial) {
        this.client = client;
        this.clientPhone = clientPhone;
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

    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }
}
