package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

/**
 * 保险投资详情  接收分红列表返回数据
 */
public class BonusListBean implements IMouldType {
    private String repayDate;    //分红日期
    private String bonusAmount;//分红金额

    public BonusListBean(String repayDate, String bonusAmount) {
        this.repayDate = repayDate;
        this.bonusAmount = bonusAmount;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(String bonusAmount) {
        this.bonusAmount = bonusAmount;
    }
}