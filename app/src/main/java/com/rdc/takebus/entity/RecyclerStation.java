package com.rdc.takebus.entity;

/**
 * Created by ZZH on 2016/5/16.
 *
 * @describe 公车站实体类
 */
public class RecyclerStation {
    //站名
    private String name;
    //是否设置了闹钟
    private boolean isAlarming = false;
    private boolean isFirstStation = false;
    private boolean isEndStation = false;
    private boolean isNearest = false;
    private Station busStation;
    private boolean showUpBus = false;
    private boolean showDownBus = false;

    public boolean isShowUpBus() {
        return showUpBus;
    }

    public void setShowUpBus(boolean showUpBus) {
        this.showUpBus = showUpBus;
    }

    public boolean isShowDownBus() {
        return showDownBus;
    }

    public void setShowDownBus(boolean showDownBus) {
        this.showDownBus = showDownBus;
    }

    public RecyclerStation(Station busStation) {
        this.busStation = busStation;
    }

    public RecyclerStation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlarming() {
        return isAlarming;
    }

    public void setAlarming(boolean alarming) {
        isAlarming = alarming;
    }

    public boolean isFirstStation() {
        return isFirstStation;
    }

    public void setFirstStation(boolean firstStation) {
        isFirstStation = firstStation;
    }

    public boolean isEndStation() {
        return isEndStation;
    }

    public void setEndStation(boolean endStation) {
        isEndStation = endStation;
    }

    public Station getBusStation() {
        return busStation;
    }

    public void setBusStation(Station busStation) {
        this.busStation = busStation;
    }

    public boolean isNearest() {
        return isNearest;
    }

    public void setNearest(boolean nearest) {
        isNearest = nearest;
    }

}
