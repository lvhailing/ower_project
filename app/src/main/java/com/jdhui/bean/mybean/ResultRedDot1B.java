package com.jdhui.bean.mybean;


import com.jdhui.bean.ResultProductIndexBean;
import com.jdhui.mould.types.IMouldType;

public class ResultRedDot1B implements IMouldType {
    private String check;
    private String code;
    private ResultRedDot2B data;
    private String msg;

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

    public ResultRedDot2B getData() {
        return data;
    }

    public void setData(ResultRedDot2B data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
