package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 服务--展示预约基因检测列表  接收后台返回数据
 */
public class GeneticTestingList3B implements IMouldType {

    private String id; //基因检测 id
    private String name; //基因检测名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

