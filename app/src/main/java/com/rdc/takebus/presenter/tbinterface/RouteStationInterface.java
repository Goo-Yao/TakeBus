package com.rdc.takebus.presenter.tbinterface;

import com.rdc.takebus.entity.Route;
import com.rdc.takebus.entity.Station;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface RouteStationInterface {
    void onFail(String result);
    void loadBusStation(List<Route> listRoute);
    void loadBusLocation();
}
