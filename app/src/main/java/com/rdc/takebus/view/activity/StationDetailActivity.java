package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.entity.StationDetail;
import com.rdc.takebus.model.adapter.RouteAdapter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.StationDetailPresenter;
import com.rdc.takebus.presenter.tbinterface.OnRecyclerItemClickListener;
import com.rdc.takebus.presenter.tbinterface.StationDetailInterface;
import com.rdc.takebus.view.CustomView.CustomToast;

import java.util.ArrayList;
import java.util.List;

/**
 * 站点详细
 * Created by ZZH on 2016/5/20.
 */
public class StationDetailActivity extends BaseSwipeBackActivity<StationDetailInterface, StationDetailPresenter> implements OnRecyclerItemClickListener, View.OnClickListener, StationDetailInterface {
    private TextView tvTitle;
    private RecyclerView mRecyclerView;
    private RouteAdapter adapter;
    private int cityId;
    private String stationName;
    private ImageView imgBack;
    private List<StationDetail> listStationDetail = new ArrayList<StationDetail>();

    @Override
    protected StationDetailPresenter createPresenter() {
        return new StationDetailPresenter(StationDetailActivity.this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        cityId = intent.getIntExtra("cityId", 0);
        stationName = intent.getStringExtra("stationName");
        setContentView(R.layout.activity_station_detail);
        super.onCreate(savedInstanceState);
        mPresenter.setCityIdAndName(cityId, stationName);
        mPresenter.getStationDetailData();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        imgBack = (ImageView) findViewById(R.id.img_back);
    }

    @Override
    protected void initVariables() {
        tvTitle.setText(stationName);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StationDetailActivity.this));
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                break;
        }
    }

    @Override
    public void loadStationData(List<StationDetail> listStationDetails) {
        Log.e("station detail", listStationDetails.size() + ">>");
        for (int i = 0; i < listStationDetails.size(); i += 2) {
            listStationDetail.add(listStationDetails.get(i));
        }
        adapter = new RouteAdapter(StationDetailActivity.this, RouteAdapter.STATION_DETAIL_TAG);
        adapter.setListDetailStations(listStationDetail);
        adapter.setOnRecyclerItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onFail(String result) {
        CustomToast.showToast(StationDetailActivity.this, result, Toast.LENGTH_SHORT);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(StationDetailActivity.this, RouteStationActivity.class);
        intent.putExtra("busLineName", listStationDetail.get(position).getTransitno());
        intent.putExtra("city", AppConstants.city);
        startActivity(intent);
    }
}
