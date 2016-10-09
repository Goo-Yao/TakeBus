package com.rdc.takebus.presenter.tbinterface;

import com.rdc.takebus.entity.AlarmInfo;

import java.util.List;

/**
 * Created by ZZH on 2016/5/24.
 */
public interface SetAlarmInterface {
    void loadMediaData(List<AlarmInfo>lists);
    void notifyData();
}
