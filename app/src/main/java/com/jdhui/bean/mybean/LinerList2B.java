package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

/**
 * 服务--展示豪华邮轮列表
 */
public class LinerList2B implements IMouldType {
    private MouldList<LinerList3B> list;
    private String msg;
    private int count;

    public MouldList<LinerList3B> getList() {
        return list;
    }

    public void setList(MouldList<LinerList3B> list) {
        this.list = list;
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

	
