package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--展示预约基因检测详情
 */
public class GeneticTestingDetail2B implements IMouldType {
    private GeneticTestingDetail3B geneticTesting;

    public GeneticTestingDetail3B getGeneticTesting() {
        return geneticTesting;
    }

    public void setGeneticTesting(GeneticTestingDetail3B geneticTesting) {
        this.geneticTesting = geneticTesting;
    }
}

	
