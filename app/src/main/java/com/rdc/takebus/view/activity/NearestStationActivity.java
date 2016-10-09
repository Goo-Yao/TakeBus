package com.rdc.takebus.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.model.adapter.StationAdapter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.NearestStationPresenter;
import com.rdc.takebus.presenter.tbinterface.NearestStationInterface;

import java.util.List;

/**
 * Created by 梦涵 on 2016/5/15.
 */
public class NearestStationActivity extends BaseSwipeBackActivity<NearestStationInterface, NearestStationPresenter>
        implements NearestStationInterface, View.OnClickListener {
    private RecyclerView recyclerStation;
    private StationAdapter stationAdapter;
    private ImageView ivBack;
    private RecyclerView.LayoutManager mLayoutManager;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nearest_station);
        super.onCreate(savedInstanceState);
        AppConstants.activityList.add(this);
        mPresenter.init();
    }

    @Override
    protected NearestStationPresenter createPresenter() {
        return new NearestStationPresenter(NearestStationActivity.this, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        recyclerStation = (RecyclerView) findViewById(R.id.recycler_station);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(NearestStationActivity.this);
        dialog.setMessage("是否开启导航？");
        dialog.setCancelable(false);
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.startNavi(position, NearestStationActivity.this);
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        recyclerStation.setLayoutManager(mLayoutManager);
        recyclerStation.setHasFixedSize(true);
        stationAdapter = new StationAdapter();
        stationAdapter.setOnItemClickListener(new StationAdapter.StationListener() {
            @Override
            public void onItemClick(View view, int tag) {
                position = tag;
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                break;
            default:
                break;
        }

    }

    @Override
    public void loadData(List<String> stations, List<String> distance, List<String> detail) {
        stationAdapter.setData(stations, distance, detail);
        recyclerStation.setAdapter(stationAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroyPoiSearch();
    }

}
