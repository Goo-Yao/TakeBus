package com.rdc.takebus.entity;

/**
 * 站点实体类
 * Created by ZZH on 2016/5/21.
 */
public class Station {
    //顺序
    private int sequenceno;
    private String station;//站名
    private double lat;//纬度
    private double lng;//经度

    public int getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(int sequenceno) {
        this.sequenceno = sequenceno;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
