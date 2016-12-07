package com.jdhui.bean.mybean;

import com.jdhui.mould.types.IMouldType;

public class PlaneMarchListBean implements IMouldType {
    private String matchTime; //时间
    private String startCity; //出发城市
    private String endCity; //目的城市
    private String numbers; //人数

    public PlaneMarchListBean(String matchTime, String startCity, String endCity, String numbers) {
        this.matchTime = matchTime;
        this.startCity = startCity;
        this.endCity = endCity;
        this.numbers = numbers;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}