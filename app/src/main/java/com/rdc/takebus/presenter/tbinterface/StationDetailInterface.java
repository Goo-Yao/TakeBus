package com.rdc.takebus.presenter.tbinterface;


import com.rdc.takebus.entity.StationDetail;

import java.util.List;

/**
 * Created by ZZH on 2016/5/20.
 */
public interface StationDetailInterface {
    void loadStationData(List<StationDetail> listStationDetail);
    void onFail(String result);
}
