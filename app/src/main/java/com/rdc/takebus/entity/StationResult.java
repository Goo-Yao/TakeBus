package com.rdc.takebus.entity;

import java.util.List;

/**
 * Created by ZZH on 2016/5/21.
 */
public class StationResult {
    private String status;
    private String msg;
    private List<StationDetail> result;

    public List<StationDetail> getResult() {
        return result;
    }

    public void setResult(List<StationDetail> result) {
        this.result = result;
    }

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

}
