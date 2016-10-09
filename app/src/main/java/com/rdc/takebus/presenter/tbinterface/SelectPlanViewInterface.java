package com.rdc.takebus.presenter.tbinterface;

import com.rdc.takebus.entity.RoutePlan;

import java.util.List;

/**
 * 出行方案展示界面操作接口
 * Created by 53261 on 2016-5-20.
 */
public interface SelectPlanViewInterface {

    void showNoRout();

    void showAmbiguousRout();

    void showRoute(List<RoutePlan> plansData);

    void showProgressBar();

    void dismissProgressBar();

}
