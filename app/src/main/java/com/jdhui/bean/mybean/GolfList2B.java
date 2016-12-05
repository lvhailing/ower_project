package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

/**
 * 服务--展示预约高尔夫球场列表
 */
public class GolfList2B implements IMouldType {
    private MouldList<GolfList3B> list;
    private String flag;
    private String msg;
    private int count;

    public MouldList<GolfList3B> getList() {
        return list;
    }

    public void setList(MouldList<GolfList3B> list) {
        this.list = list;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

	
