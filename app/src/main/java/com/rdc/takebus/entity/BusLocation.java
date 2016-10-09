package com.rdc.takebus.entity;

/**
 * Created by ZZH on 2016/5/22.
 */
public class BusLocation {
    private int busId;            //排序id
    private int station;        //开往站台
    private int state;            //0为未到站,1为到站
    private int distance;    //离站台的距离
    private int reporTime;    //数据更新时间,xxx秒前

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getReporTime() {
        return reporTime;
    }

    public void setReporTime(int reporTime) {
        this.reporTime = reporTime;
    }
}

