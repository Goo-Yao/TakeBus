package com.rdc.takebus.entity;

/**
 * Created by ZZH on 2016/5/21.
 */
public class StationDetail {
    private String transitno;
    private String startstation;
    private String endstation;

    public String getEndstation() {
        return endstation;
    }

    public void setEndstation(String endstation) {
        this.endstation = endstation;
    }

    public String getTransitno() {
        return transitno;
    }

    public void setTransitno(String transitno) {
        this.transitno = transitno;
    }

    public String getStartstation() {
        return startstation;
    }

    public void setStartstation(String startstation) {
        this.startstation = startstation;
    }
}
