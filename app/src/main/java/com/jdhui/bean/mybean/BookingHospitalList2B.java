package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;
import com.jdhui.mould.types.MouldList;

/**
 * 服务--展示预约医院列表
 */
public class BookingHospitalList2B implements IMouldType {
    private MouldList<BookingHospitalList3B> list;
    private String flag;
    private String msg;
    private String count;

    public MouldList<BookingHospitalList3B> getList() {
        return list;
    }

    public void setList(MouldList<BookingHospitalList3B> list) {
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

	
