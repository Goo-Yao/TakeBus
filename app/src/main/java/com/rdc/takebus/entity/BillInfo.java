package com.rdc.takebus.entity;

import java.io.Serializable;

/**
 * 车票信息
 */
public class BillInfo implements Serializable {
    private int id;
    private boolean finished;
    private String startPoint;
    private String endPoint;
    private String route;
    private String createTime;
    private int cost;
    private String qrCoderUrl;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQrCoderUrl() {
        return qrCoderUrl;
    }

    public void setQrCoderUrl(String qrCoderUrl) {
        this.qrCoderUrl = qrCoderUrl;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    @Override
    public String toString() {
        return "BillInfo{" +
                "cost=" + cost +
                ", id=" + id +
                ", finished=" + finished +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", route='" + route + '\'' +
                ", createTime=" + createTime +
                ", qrCoderUrl='" + qrCoderUrl + '\'' +
                '}';
    }
}
