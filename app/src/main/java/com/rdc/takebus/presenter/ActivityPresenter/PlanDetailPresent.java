package com.rdc.takebus.presenter.ActivityPresenter;

import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.presenter.tbinterface.PlanDetailViewInterface;

/**
 * 路线规划详情Presenter
 * Created by 53261 on 2016-5-25.
 */
public class PlanDetailPresent extends BaseActivityPresenter<PlanDetailViewInterface> {
    private PlanDetailViewInterface viewInterface;

    public PlanDetailPresent(PlanDetailViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }


}
