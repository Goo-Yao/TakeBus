package com.rdc.takebus.entity;

import java.util.List;

/**
 * Created by ZZH on 2016/5/21.
 */
public class CityModel {
    private String status;
    private String msg;
    private List<City> result;

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

    public List<City> getResult() {
        return result;
    }

    public void setResult(List<City> result) {
        this.result = result;
    }
}
