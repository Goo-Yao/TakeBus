package com.rdc.takebus.entity;

import java.util.List;

/**
 * Created by 53261 on 2016-5-21.
 */
public class Route {
    //线路名字
    private String transitno;
    private String startstation;//起点
    private String endstation;//终点
    private String starttime;//发车时间
    private String endtime;//末班车时间
    private String price;//价格
    private String maxprice;//最大价格
    private List<Station> list;//途径站

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

    public String getEndstation() {
        return endstation;
    }

    public void setEndstation(String endstation) {
        this.endstation = endstation;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public List<Station> getList() {
        return list;
    }

    public void setList(List<Station> list) {
        this.list = list;
    }
}
