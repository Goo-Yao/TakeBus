package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.entity.RoutePlan;
import com.rdc.takebus.model.adapter.RoutePlanAdapter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.SelectPlanPresenter;
import com.rdc.takebus.presenter.tbinterface.SelectPlanViewInterface;

import java.util.List;


/**
 * 当用户选择好起点、终点之后发起搜索
 * Created by 梦涵 on 2016/5/10.
 */
public class SelectPlanActivity extends BaseSwipeBackActivity<SelectPlanViewInterface, SelectPlanPresenter> implements SelectPlanViewInterface, View.OnClickListener {

    private LinearLayout llStartStation, llTerminalStation;
    private TextView tvStartStation, tvTerminalStation, tvRouteResult;
    private ImageView ivBack;
    private RecyclerView rvRouteResult;
    private RecyclerView.LayoutManager layoutManager = null;
    private RoutePlanAdapter adapter = null;
    private String strStartStation = null, strTerminalStation = null;//起点、终点地点名
    private boolean isStartStation = false, isTerminalStation = false;//判断是否选择
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStartStation && isTerminalStation) {
            //联网搜索公交路线
            mPresenter.searchRouteFromNet(strStartStation, strTerminalStation);
        }
    }

    @Override
    protected SelectPlanPresenter createPresenter() {
        return new SelectPlanPresenter(this);
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_route);
        findAllViewById();
        setAllOnClickListener();
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this);
        }
        if (adapter == null) {
            adapter = new RoutePlanAdapter(this);
        }
        adapter.setOnPlanItemClickListener(new RoutePlanAdapter.OnPlanItemClickListener() {
            @Override
            public void onItemClick(View view, RoutePlan plan) {
                Intent intentToDetail = new Intent(SelectPlanActivity.this, PlanDetailActivity.class);
                Bundle bundleToDetail = new Bundle();
                bundleToDetail.putSerializable("plan", plan);
                bundleToDetail.putString("strStartStation", strStartStation);
                bundleToDetail.putString("strTerminalStation", strTerminalStation);
                intentToDetail.putExtras(bundleToDetail);
                startActivity(intentToDetail);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
            }
        });
        rvRouteResult.setLayoutManager(layoutManager);
        rvRouteResult.setHasFixedSize(true);
        rvRouteResult.setAdapter(adapter);
    }

    private void setAllOnClickListener() {
        ivBack.setOnClickListener(this);
        llStartStation.setOnClickListener(this);
        llTerminalStation.setOnClickListener(this);
    }

    private void findAllViewById() {
        llStartStation = (LinearLayout) findViewById(R.id.ll_select_start_station);
        llTerminalStation = (LinearLayout) findViewById(R.id.ll_select_terminal_station);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvRouteResult = (TextView) findViewById(R.id.tv_route_result);
        rvRouteResult = (RecyclerView) findViewById(R.id.rv_route);
        tvStartStation = (TextView) findViewById(R.id.tv_startStation);
        tvTerminalStation = (TextView) findViewById(R.id.tv_terminalStation);
        pb = (ProgressBar) findViewById(R.id.pb);
    }


    @Override
    protected void initVariables() {

    }


    /**
     * 搜索不到路线的时候
     */
    @Override
    public void showNoRout() {
        rvRouteResult.setVisibility(View.GONE);
        tvRouteResult.setVisibility(View.VISIBLE);
        tvRouteResult.setText("很抱歉，未查询到相关线路");
    }

    /**
     * 地点不准确
     */
    @Override
    public void showAmbiguousRout() {
        rvRouteResult.setVisibility(View.GONE);
        tvRouteResult.setVisibility(View.VISIBLE);
        tvRouteResult.setText("地点还不够准确，换一个试试吧");
    }

    /**
     * 显示相关路线
     */
    @Override
    public void showRoute(List<RoutePlan> plansData) {
        rvRouteResult.setVisibility(View.VISIBLE);
        tvRouteResult.setVisibility(View.GONE);
        adapter.setData(plansData);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
        rvRouteResult.setVisibility(View.GONE);
        tvRouteResult.setVisibility(View.GONE);
    }

    @Override
    public void dismissProgressBar() {
        pb.setVisibility(View.GONE);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                break;
            case R.id.ll_select_start_station:
                //返回起点站
                Intent intentStart = new Intent(this, SearchStationActivity.class);
                Bundle bundleStart = new Bundle();
                bundleStart.putInt("stationTag", AppConstants.SELECT_STARTSTATION_TAG);
                intentStart.putExtras(bundleStart);
                startActivityForResult(intentStart, 0);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.ll_select_terminal_station:
                //返回终点站
                Intent intentTerminal = new Intent(this, SearchStationActivity.class);
                Bundle bundleTerminal = new Bundle();
                bundleTerminal.putInt("stationTag", AppConstants.SELECT_TERMINALSTATION_TAG);
                intentTerminal.putExtras(bundleTerminal);
                startActivityForResult(intentTerminal, 0);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            int stationTag = bundle.getInt("stationTag");
            String stationName = bundle.getString("stationName");
//            if (stationName != null) {
//                stationName = stationName.substring(0, stationName.indexOf("-"));
//            }
            switch (stationTag) {
                case AppConstants.SELECT_STARTSTATION_TAG:
                    isStartStation = true;
                    strStartStation = stationName;
                    tvStartStation.setText(stationName);

                    AppConstants.startStation = strStartStation.replace("-公交车站","");
                    break;
                case AppConstants.SELECT_TERMINALSTATION_TAG:
                    isTerminalStation = true;
                    strTerminalStation = stationName;
                    tvTerminalStation.setText(stationName);

                    AppConstants.terminalStation = strTerminalStation.replace("-公交车站","");
                    break;
            }
        }
    }
}
