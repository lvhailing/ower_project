package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--游轮详情
 */
public class LinerDetail2B implements IMouldType {
    private LinerDetail3B luxuryShip;

    public LinerDetail3B getLuxuryShip() {
        return luxuryShip;
    }

    public void setLuxuryShip(LinerDetail3B luxuryShip) {
        this.luxuryShip = luxuryShip;
    }
}

	
