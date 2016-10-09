package com.rdc.takebus.presenter.tbinterface;

import com.baidu.navisdk.adapter.BNRoutePlanNode;

import java.util.List;

/**
 * Created by 梦涵 on 2016/5/15.
 */
public interface NearestStationInterface {
    public void loadData(List<String> stations, List<String> distance, List<String> detail);
}
