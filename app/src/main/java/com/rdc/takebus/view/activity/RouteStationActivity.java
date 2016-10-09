package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.entity.RecyclerStation;
import com.rdc.takebus.entity.Route;
import com.rdc.takebus.entity.Station;
import com.rdc.takebus.model.adapter.RouteAdapter;
import com.rdc.takebus.model.holder.RouteTagHolder;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.CityIdUtil;
import com.rdc.takebus.model.utils.MapUtil;
import com.rdc.takebus.presenter.ActivityPresenter.RouteStationPresenter;
import com.rdc.takebus.presenter.tbinterface.OnRecyclerItemClickListener;
import com.rdc.takebus.presenter.tbinterface.RouteStationInterface;
import com.rdc.takebus.view.CustomView.CustomToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZZH on 2016/5/18.
 */
public class RouteStationActivity extends BaseSwipeBackActivity<RouteStationInterface, RouteStationPresenter> implements RouteStationInterface, OnRecyclerItemClickListener, View.OnClickListener {
    private RelativeLayout llRoute;
    private LinearLayout llStation;
    private RecyclerView mRecyclerView;
    private TextView tvTitle;
    private ImageView imgBack;
    private RouteAdapter adapter;
    private int numBusLine;
    private String busLineName;
    private String city;
    private int cityId;
    private List<RecyclerStation> listStations = new ArrayList<RecyclerStation>();
    private List<RouteTagHolder> listHolders = new ArrayList<RouteTagHolder>();
    //距离最近范围500米
    public static double DISTANCE = 500;
    public static int tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_route_station);
        busLineName = getIntent().getStringExtra("busLineName");
        city = getIntent().getStringExtra("city");
        tag = getIntent().getIntExtra("tag" , 0);
        cityId = CityIdUtil.getCityId(city);
        AppConstants.cityId = cityId;
        super.onCreate(savedInstanceState);
        //设置参数
        mPresenter.setRouteData(cityId, city, busLineName);
        //发起检索
        mPresenter.getRouteStation();
    }

    @Override
    protected RouteStationPresenter createPresenter() {
        return new RouteStationPresenter(RouteStationActivity.this, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        llRoute = (RelativeLayout) findViewById(R.id.ll_route);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        imgBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llStation = (LinearLayout) findViewById(R.id.ll_station);
    }

    @Override
    protected void initVariables() {
        //默认垂直
        mRecyclerView.setLayoutManager(new LinearLayoutManager(RouteStationActivity.this));
        adapter = new RouteAdapter(RouteStationActivity.this, RouteAdapter.ROUTE_STATION_TAG);
        imgBack.setOnClickListener(this);
        tvTitle.setText(busLineName);
        adapter.setOnRecyclerItemClickListener(this);
        EventBus.getDefault().register(RouteStationActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                break;
            case R.id.view_tag_one: {
                if (!listHolders.get(0).isSelected()) {
                    listHolders.get(0).imgDivider.setImageResource(R.color.colorPrimary);
                    if (numBusLine > 1) {
                        listHolders.get(1).imgDivider.setImageResource(R.color.dividerColor);
                        listHolders.get(1).setSelected(false);
                    }
                    Collections.reverse(listStations);
                    for (RecyclerStation station : listStations) {
                        if (station.isNearest())
                            Log.e("neear ", station.getBusStation().getStation() + ">>");
                        station.setShowDownBus(false);
                        station.setShowUpBus(false);
                    }
                    mPresenter.getBusLocation(listStations, listStations.get(1).getBusStation().getStation());

                    adapter.notifyDataSetChanged();
                    listHolders.get(0).setSelected(true);
                }
            }
            break;
            case R.id.view_tag_two: {
                if (!listHolders.get(1).isSelected()) {
                    listHolders.get(0).imgDivider.setImageResource(R.color.dividerColor);
                    listHolders.get(1).imgDivider.setImageResource(R.color.colorPrimary);
                    Collections.reverse(listStations);
                    for (RecyclerStation station : listStations) {
                        if (station.isNearest())
                            Log.e("neear ", station.getBusStation().getStation() + ">>");
                        station.setShowDownBus(false);
                        station.setShowUpBus(false);
                    }
                    mPresenter.getBusLocation(listStations, listStations.get(1).getBusStation().getStation());
                    adapter.notifyDataSetChanged();
                    listHolders.get(0).setSelected(false);
                    listHolders.get(1).setSelected(true);
                }
            }
            break;

        }
    }

    @Override
    public void onFail(String result) {
        CustomToast.showToast(RouteStationActivity.this, result, Toast.LENGTH_SHORT);
    }

    @Override
    public void loadBusStation(List<Route> listRoute) {
        if (listRoute != null) {
            numBusLine = listRoute.size();
            Log.e("numBusLine", numBusLine + ">>");
            Route route = listRoute.get(0);
            List<Station> listStation = route.getList();
            double min = 0;
            int tag = 0;
            for (int i = 0; i < listStation.size(); i++) {
                Station busStation = listStation.get(i);
                //计算两点经纬度
                double distance = MapUtil.calculateDistance(AppConstants.longiTude, AppConstants.latitude, busStation.getLng(), busStation.getLat());
                if (min == 0)
                    min = distance;
                else if (min > distance) {
                    min = distance;
                    tag = i;
                }
                listStations.add(new RecyclerStation(busStation));
            }
            //排序找出距离最近的那个站
            Log.e("distance", tag + ">> " + min + ">simall tag");
            if (min < DISTANCE) {
                listStations.get(tag).setNearest(true);
                Log.e("distance ", " true " + Math.round(min));
            }
            adapter.setListStations(listStations);
            adapter.setLineAndPrice(busLineName, route.getPrice());
            loadBusStationDate(route.getStarttime(), route.getEndtime());
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void loadBusLocation() {
        mPresenter.getBusLocation(listStations, listStations.get(1).getBusStation().getStation());
        adapter.notifyDataSetChanged();
    }


    //加载数据
    private void loadBusStationDate(String startTime, String endTime) {
        listHolders.clear();
        for (int i = 0; i < numBusLine; i++) {
            View rootView = LayoutInflater.from(this).inflate(R.layout.route_station_tag, llStation, false);
            final RouteTagHolder holder = new RouteTagHolder(rootView);
            holder.tvStart.setText(startTime);
            holder.tvEnd.setText(endTime);
            if (i >= 1) {
                holder.imgDivider.setImageResource(R.color.dividerColor);
                holder.tvStation.setText(listStations.get(0).getBusStation().getStation() + "方向");
                rootView.setId(R.id.view_tag_two);
                holder.setSelected(false);

            } else {
                holder.imgDivider.setImageResource(R.color.colorPrimary);
                holder.tvStation.setText(listStations.get(listStations.size() - 1).getBusStation().getStation() + "方向");
                holder.setSelected(true);
                rootView.setId(R.id.view_tag_one);
            }
            rootView.setOnClickListener(this);
            listHolders.add(holder);
            llStation.addView(rootView);
        }
    }

    @Override
    public void onItemClick(int position) {
        Station busStation = listStations.get(position).getBusStation();
        Intent intent = new Intent(RouteStationActivity.this, StationDetailActivity.class);
        intent.putExtra("cityId", cityId);
        intent.putExtra("stationName", busStation.getStation());
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(String price) {
        String routeName = busLineName;
        String startStation = AppConstants.startStation;
        String terminalStation = AppConstants.terminalStation;
        String tips = "";
        if (TextUtils.isEmpty(price) || TextUtils.isEmpty(routeName) ||
                TextUtils.isEmpty(startStation) || TextUtils.isEmpty(terminalStation)) {
            tips += "----信息缺失----\n";
        } else {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(AppConstants.COST, price);
            intent.putExtra(AppConstants.STARTPOINT, startStation);
            intent.putExtra(AppConstants.ENDPOINT, terminalStation);
            intent.putExtra(AppConstants.ROUTE, routeName);
            startActivity(intent);
        }
        tips = "startStation = " + startStation
                + "\nterminalStation = " + terminalStation + "\nrouteName = " + routeName
                + "\nprice = " + price;
      //  CustomToast.showToast(this, tips, Toast.LENGTH_LONG);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(RouteStationActivity.this);
    }
}
