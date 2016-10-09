package com.rdc.takebus.view.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.rdc.takebus.model.utils.CityIdUtil;

/**
 * Created by ZZH on 2016/5/21.
 */
public class BusApplication extends Application{
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        CityIdUtil.initListCity();
    }
}
