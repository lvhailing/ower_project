package com.jdhui.bean.mybean;

/**
 * 服务--提交海外医疗预约  准备入参
 */
public class SubOverseasMedical0B {
    private String client; // 预约人
    private String clientPhone; // 预约电话
    private String overseasType; // 海外医疗预约类型
    private String financial; // 专属理财师

    public SubOverseasMedical0B(String client, String clientPhone, String overseasType, String financial) {
        this.client = client;
        this.clientPhone = clientPhone;
        this.overseasType = overseasType;
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

    public String getOverseasType() {
        return overseasType;
    }

    public void setOverseasType(String overseasType) {
        this.overseasType = overseasType;
    }

    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }
}
