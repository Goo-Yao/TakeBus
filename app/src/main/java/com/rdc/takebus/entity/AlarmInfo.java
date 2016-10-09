package com.rdc.takebus.entity;

/**
 * Created by ZZH on 2016/5/24.
 */
public class AlarmInfo {
    private String alarmName;
    private String uid;
    private boolean isChecked = false;

    public AlarmInfo(String alarmName, String uid) {
        this.alarmName = alarmName;
        this.uid = uid;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
