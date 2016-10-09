package com.rdc.takebus.entity;


import java.io.Serializable;
import java.util.List;

/**
 * 乘车方案
 * Created by 53261 on 2016-5-24.
 */
public class RoutePlan implements Serializable {
    //条目展示
    private String busTitle;
    private String detail;

    //点击进去的详情
    private List<String> busNames;//用到的公交车
    private List<String> stepIntroductions;//步骤说明
    private int distance;//距离（已转为公里）
    private int duration;//用时（已转为分钟）

    public String getBusTitle() {
        return busTitle;
    }

    public void setBusTitle(String busTitle) {
        this.busTitle = busTitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getBusNames() {
        return busNames;
    }

    public void setBusNames(List<String> busNames) {
        this.busNames = busNames;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getStepIntroductions() {
        return stepIntroductions;
    }

    public void setStepIntroductions(List<String> stepIntroductions) {
        this.stepIntroductions = stepIntroductions;
    }

}
