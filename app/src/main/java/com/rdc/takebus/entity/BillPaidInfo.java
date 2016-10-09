package com.rdc.takebus.entity;

import java.io.Serializable;

/**
 * 已付款账单信息
 */
public class BillPaidInfo implements Serializable {

    /**
     * 起点站
     */
    private String startStation;
    /**
     * 终点站
     */
    private String terminalStation;
    /**
     * 路线名
     */
    private String routeName;

    public BillPaidInfo(String routeName, String startStation, String terminalStation) {
        this.routeName = routeName;
        this.startStation = startStation;
        this.terminalStation = terminalStation;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getTerminalStation() {
        return terminalStation;
    }

    public void setTerminalStation(String terminalStation) {
        this.terminalStation = terminalStation;
    }
}
