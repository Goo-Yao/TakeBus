package com.rdc.takebus.presenter.tbinterface;

import android.view.View;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.rdc.takebus.entity.RecyclerStation;
import com.rdc.takebus.entity.Station;

import java.util.List;


/**
 * 主界面View操作接口
 * Created by 梦涵 on 2016/5/11.
 */
public interface MapInterface {
    void locationInit(MapStatusUpdate msu);

    void addMarkers(MapStatusUpdate msu);//添加覆盖物

    void returnToStation(MapStatusUpdate msu);

    //实时显示位置
    void showBusLocation(int startPoint);
    //完成本次的搭乘
    void onFinish();

}
