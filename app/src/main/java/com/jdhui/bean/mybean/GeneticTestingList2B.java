package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

/**
 * 服务--展示预约基因检测列表
 */
public class GeneticTestingList2B implements IMouldType {
    private MouldList<GeneticTestingList3B> list;
    private String flag;
    private String msg;
    private int count;

    public MouldList<GeneticTestingList3B> getList() {
        return list;
    }

    public void setList(MouldList<GeneticTestingList3B> list) {
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

	
