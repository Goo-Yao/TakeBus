package com.rdc.takebus.presenter.ActivityPresenter;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.entity.RoutePlan;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.tbinterface.SelectPlanViewInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据起点、终点站搜索得出出行方案
 * Created by 53261 on 2016-5-20.
 */
public class SelectPlanPresenter extends BaseActivityPresenter<SelectPlanViewInterface> implements OnGetRoutePlanResultListener {

    private SelectPlanViewInterface selectRouteView;
    private RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    private List<RoutePlan> plansData = new ArrayList<RoutePlan>();

    public SelectPlanPresenter(SelectPlanViewInterface selectRouteView) {
        this.selectRouteView = selectRouteView;
    }

    /**
     * 联网获得查询路线
     */
    public void searchRouteFromNet(String strStartStation, String strTerminalStation) {
//        Log.e("TEST", "起点、终点为：" + strStartStation + "---" + strTerminalStation);
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        // 设置起终点信息，对于tranist search 来说，城市名无意义
        PlanNode stNode = PlanNode.withCityNameAndPlaceName(AppConstants.city, strStartStation);
        PlanNode enNode = PlanNode.withCityNameAndPlaceName(AppConstants.city, strTerminalStation);
        selectRouteView.showProgressBar();
        mSearch.transitSearch((new TransitRoutePlanOption())
                .from(stNode).city(AppConstants.city).to(enNode));
    }


    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        selectRouteView.dismissProgressBar();
        //公交
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //无相关出行
            selectRouteView.showNoRout();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //地点模糊
            selectRouteView.showNoRout();
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            List<TransitRouteLine> routesData = result.getRouteLines();
            if (plansData.size() > 0) {
                plansData.clear();
            }
            for (int i = 0; i < routesData.size(); i++) {

                TransitRouteLine transitRouteLine = routesData.get(i);

                List<TransitRouteLine.TransitStep> transitSteps = transitRouteLine.getAllStep();

                int distance = transitRouteLine.getDistance();
                int duration = transitRouteLine.getDuration();

                List<String> busNames = new ArrayList<String>();
                List<String> strpInstructions = new ArrayList<String>();

                int passStationNumAll = 0;

                for (int j = 0; j < transitSteps.size(); j++) {
                    TransitRouteLine.TransitStep transitStep = transitSteps.get(j);

                    VehicleInfo vehicleInfo = transitStep.getVehicleInfo();
                    if (vehicleInfo != null) {
                        int passStationNum = vehicleInfo.getPassStationNum();
                        passStationNumAll += passStationNum;
                        String title = vehicleInfo.getTitle();
                        busNames.add(title);
                    }
                    String strpInstruction = (j + 1) + "." + transitStep.getInstructions();
                    strpInstructions.add(strpInstruction);
                }

                String tempTitle = "";
                for (int k = 0; k < busNames.size(); k++) {
                    tempTitle = tempTitle + busNames.get(k);
                    if (k != busNames.size() - 1) {
                        tempTitle = tempTitle + "->";
                    }
                }

                RoutePlan plan = new RoutePlan();
                //条目展示
                plan.setBusTitle(tempTitle);
                String detailHTML = "<font color=\"#e37479\">" + "" + Math.round((distance / 1000f) * 100) / 100 + "</font>" + "公里" + " | " + "途径" + "<font color=\"#e37479\">" + "" + passStationNumAll + "</font>" + "站" + " | " + "预计用时" + "<font color=\"#e37479\">" + "" + duration / 60 + "</font>" + "分钟";
                plan.setDetail(detailHTML);

                //详细信息
                plan.setBusNames(busNames);
                plan.setDistance(Math.round((distance / 1000f) * 100) / 100);
                plan.setDuration(duration / 60);
                plan.setStepIntroductions(strpInstructions);

                plansData.add(plan);
            }
            //展示得出的结果
            selectRouteView.showRoute(plansData);
        }
    }


    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        //驾车
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
        //骑行
    }
}
