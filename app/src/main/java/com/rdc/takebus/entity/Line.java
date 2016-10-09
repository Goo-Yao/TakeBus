package com.rdc.takebus.entity;

import java.util.List;

/**
 * Created by ZZH on 2016/5/27.
 */
public class Line {
    private String lineName;
    private String startStation;
    private String endStation;
    private List<Station> lists;


    public Line(String lineName, String startStation, String endStation, List<Station> lists) {
        this.lineName = lineName;
        this.startStation = startStation;
        this.endStation = endStation;
        this.lists = lists;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public List<Station> getLists() {
        return lists;
    }

    public void setLists(List<Station> lists) {
        this.lists = lists;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}
