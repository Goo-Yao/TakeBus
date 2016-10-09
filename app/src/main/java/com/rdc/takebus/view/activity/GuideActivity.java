package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.rdc.takebus.base.BaseActivity;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.GuidePresenter;
import com.rdc.takebus.presenter.tbinterface.GuideInterface;

/**
 * Created by 梦涵 on 2016/5/17.
 * 导航
 */
public class GuideActivity extends BaseActivity<GuideInterface, GuidePresenter>
implements GuideInterface{
    private BNRoutePlanNode mBNRoutePlanNode = null;
    private double longitude;//目的地经纬度
    private double latitude;
    private String stationName;//目的地名字
    private View view = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppConstants.activityList.add(this);
        //使用传统接口
        view = BNRouteGuideManager.getInstance().onCreate(this, mOnNavigationListener);
        if (view != null) {
            setContentView(view);
        }
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mBNRoutePlanNode = (BNRoutePlanNode) bundle.getSerializable(AppConstants.ROUTE_PLAN_NODE);
                longitude = (Double)bundle.get("longitude");
                latitude  = (Double)bundle.get("latitude");
                stationName = (String) bundle.get("name");
                mPresenter.createHandler(longitude, latitude, stationName);
            }
        }
    }
    @Override
    protected GuidePresenter createPresenter() {
        return new GuidePresenter(this, this, mBNRoutePlanNode);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {}

    @Override
    protected void initVariables() {}

    @Override
    protected void onResume() {
        super.onResume();
        BNRouteGuideManager.getInstance().onResume();
        if (mPresenter.hd != null) {
            mPresenter.hd.sendEmptyMessageAtTime(AppConstants.MSG_SHOW, 200);
        }
    }

    protected void onPause() {
        super.onPause();
        BNRouteGuideManager.getInstance().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BNRouteGuideManager.getInstance().onDestroy();
        AppConstants.activityList.remove(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BNRouteGuideManager.getInstance().onStop();
    }

    @Override
    public void onBackPressed() {
        BNRouteGuideManager.getInstance().onBackPressed(false);
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BNRouteGuideManager.getInstance().onConfigurationChanged(newConfig);
    }

    private BNRouteGuideManager.OnNavigationListener mOnNavigationListener = new BNRouteGuideManager.OnNavigationListener() {
        @Override
        public void onNaviGuideEnd() {
            finish();
        }
        @Override
        public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {
            if (actionType == 0) {
                //到达目的地
            }
        }
    };

    @Override
    public void onClick(View v) {

    }
}
