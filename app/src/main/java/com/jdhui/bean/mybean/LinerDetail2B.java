package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--游轮详情
 */
public class LinerDetail2B implements IMouldType {
    private LinerDetail3B golf;

    public LinerDetail3B getGolf() {
        return golf;
    }

    public void setGolf(LinerDetail3B golf) {
        this.golf = golf;
    }
}

	
