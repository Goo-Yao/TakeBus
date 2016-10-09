package com.rdc.takebus.entity;

import java.util.List;

/**
 * Created by ZZH on 2016/5/22.
 */
public class BusResult {
    private int code;
    private String msg;
    private BusResultData data;

    public BusResultData getData() {
        return data;
    }

    public void setData(BusResultData data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
