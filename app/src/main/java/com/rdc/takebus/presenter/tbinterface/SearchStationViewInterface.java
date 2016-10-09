package com.rdc.takebus.presenter.tbinterface;

import java.util.List;
import java.util.Map;

/**
 * SearchStationView操作接口
 * Created by 梦涵 on 2016/5/10.
 */
public interface SearchStationViewInterface {

    //展示相关站点列表
    void showStationList(List<String> suggestData);

    void selectStation();
}
