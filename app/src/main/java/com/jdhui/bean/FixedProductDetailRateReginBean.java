package com.jdhui.bean;

import com.jdhui.mould.types.IMouldType;

/**
 * Created by hasee on 2016/8/16.
 */
public class FixedProductDetailRateReginBean implements IMouldType {

    private String amount; // 认购金额
    private String rate;  // 预期收益

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
