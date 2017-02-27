package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

/**
 * 邮轮提交预约  后台接口返回的数据
 */
public class SubBookingShip1B implements IMouldType {
    private String check;
    private String code;
    private String msg;
    private SubBookingShip2B data;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SubBookingShip2B getData() {
        return data;
    }

    public void setData(SubBookingShip2B data) {
        this.data = data;
    }
}

