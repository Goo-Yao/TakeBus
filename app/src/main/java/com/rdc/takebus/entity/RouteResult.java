package com.rdc.takebus.entity;

import java.util.List;

/**
 * 路线返回实体类
 * Created by ZZH on 2016/5/21.
 */
public class RouteResult {
    private String status;
    private String msg;
    private List<Route> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Route> getResult() {
        return result;
    }

    public void setResult(List<Route> result) {
        this.result = result;
    }
}
