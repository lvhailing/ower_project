package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

/**
 * 服务--展示预约基因检测列表
 */
public class GeneticTestingDetail2B implements IMouldType {
    private MouldList<GeneticTestingDetail3B> list;
    private String flag;
    private String msg;
    private int count;

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

    public MouldList<GeneticTestingDetail3B> getList() {
        return list;
    }

    public void setList(MouldList<GeneticTestingDetail3B> list) {
        this.list = list;
    }
}

	
