package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.entity.BusLocation;
import com.rdc.takebus.entity.BusResultData;
import com.rdc.takebus.entity.BusStationTag;
import com.rdc.takebus.entity.RecyclerStation;
import com.rdc.takebus.entity.Route;
import com.rdc.takebus.entity.Station;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.EncoderUtil;
import com.rdc.takebus.model.utils.JsonParseUtil;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbinterface.ResultListener;
import com.rdc.takebus.presenter.tbinterface.RouteStationInterface;
import com.rdc.takebus.view.CustomView.CustomToast;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by ZZH on 2016/5/18.
 */
public class RouteStationPresenter extends BaseActivityPresenter<RouteStationInterface> {
    //路线唯一标识
    private int cityId;
    private RouteStationInterface mRouteStationInterface;
    private Context context;
    private SpotsDialog dialog;
    private String routeName;
    private String cityName;
    private List<Station> listStation = new ArrayList<Station>();
    private List<List<BusLocation>> listBus = new ArrayList<>();
    private List<List<BusStationTag>> listBusStationTags = new ArrayList<>();

    public RouteStationPresenter(Context context, RouteStationInterface mRouteStationInterface) {
        this.context = context;
        this.mRouteStationInterface = mRouteStationInterface;
    }

    public void showProgressDialog() {
        dialog = new SpotsDialog(context);
        dialog.show();
    }

    public void dissmissProgressDialog() {
        dialog.dismiss();
    }

    //设置路线检索的参数
    public void setRouteData(int cityId, String cityName, String routeName) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.routeName = routeName;
    }

    public void getRouteStation() {
        showProgressDialog();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_OHTER).getData(AppConstants.getRouteUrl(cityId, routeName), new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                Log.e("result", result + ">>>");
                if (result.contains("ok")) {
                    List<Route> listRoute = JsonParseUtil.parseRouteJson(result);
                    listStation = listRoute.get(0).getList();
                    mRouteStationInterface.loadBusStation(listRoute);
                    getBusLocationData("0");
                } else {
                    mRouteStationInterface.onFail("获取数据失败");
                    dissmissProgressDialog();
                }
            }

            @Override
            public void onResultFail(String result) {
                dissmissProgressDialog();
                mRouteStationInterface.onFail("获取数据失败");
            }
        }, (Activity) context);
    }

    public void getBusLocation(List<RecyclerStation> listStation, String station) {
        if (listBus.size() != 0 && listBusStationTags.size() != 0) {
            int tag = getBusId(station);
            Log.e("stationNameTag", tag + ">>");
            if (tag < listBus.size()) {
                List<BusLocation> listBusLocation = listBus.get(tag);
                List<BusStationTag> listBusStationTag = listBusStationTags.get(tag);
                Log.e("stationName", listBusLocation.size() + ">>" + listBusStationTag.size());
                for (BusLocation busLocation : listBusLocation) {
                    String stationName = EncoderUtil.decodeUnicode(listBusStationTag.get(busLocation.getStation() - 1).getStateName());
                    Log.e("stationName", stationName + ">>");
                    for (int i = 0; i < listStation.size(); i++) {
                        RecyclerStation mRecyclerStation = listStation.get(i);
                        if (mRecyclerStation.getBusStation().getStation().equals(stationName)) {
                            mRecyclerStation.setShowUpBus(true);
                            if (i >= 1)
                                listStation.get(i - 1).setShowDownBus(true);
                        }
                    }
                }
            }
        }
    }

    private int getBusId(String stationName) {
        Log.e("statioName", stationName + ">>");
        //去掉一些符号
        stationName = stationName.replace("(", "");
        stationName = stationName.replace(")", "");
        Log.e("statioName", stationName + ">>");

        for (int i = 0; i < listBusStationTags.size(); i++) {
            List<BusStationTag> listBusStationTag = listBusStationTags.get(i);
            String station = listBusStationTag.get(1).getStateName().replace("站", "");
            Log.e("statioNamestation", station + ">>");
            if (station.equals(stationName))
                return i;
        }
        return 0;
    }

    private void getBusLocationData(final String direction) {
        FormBody body = new FormBody.Builder().add("city", cityName).add("bus", routeName.replace('路', ' ')).add("direction", direction).build();
        Request request = new Request.Builder().url(AppConstants.GET_BUS_LOCATION_URL).addHeader("apikey", AppConstants.BUS_API_KEY).post(body).build();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_OHTER).postData(request, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                Log.e("onResult", result + ">>");
                if (result.contains("success")) {
                    BusResultData busResultData = JsonParseUtil.parseBusLocationJson(result);
                    if (busResultData.getBuses() != null)
                        listBus.add(busResultData.getBuses());
                    listBusStationTags.add(busResultData.getStations());
                    Log.e("list size", listBus.size() + ">>");
                    Log.e("list size tag", listBusStationTags.size() + ">>");
                    if (direction.equals("0"))
                        getBusLocationData("1");
                    else {
                        dissmissProgressDialog();
                        mRouteStationInterface.loadBusLocation();
                    }
                } else {
                    dissmissProgressDialog();
                    if (direction.equals("1"))
                        mRouteStationInterface.loadBusLocation();
                    else
                        CustomToast.showToast(context, "获取不到公交实时信息", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onResultFail(String result) {
                dissmissProgressDialog();
            }
        }, (Activity) context);
    }
}
