package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.entity.RoutePlan;
import com.rdc.takebus.model.adapter.PlanDetailAdapter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.PlanDetailPresent;
import com.rdc.takebus.presenter.tbinterface.PlanDetailViewInterface;

import java.util.List;

/**
 * Created by 53261 on 2016-5-25.
 */
public class PlanDetailActivity extends BaseSwipeBackActivity<PlanDetailViewInterface, PlanDetailPresent> implements PlanDetailViewInterface {
    private TextView tvStartStation, tvEndStation, tvDuration, tvDistance;
    private RecyclerView rvBus, rvIntro;
    private RecyclerView.LayoutManager layoutManagerBus, layoutManagerStep;
    private PlanDetailAdapter busAdapter, stepAdapter;
    private ImageView ivBack;
    private RoutePlan plan = null;
    private String strTerminalStation, strStartStation;//起点、终点
    private String strDuration, strDistance;//用时、距离
    private List<String> busNames;//用到的公交车
    private List<String> stepIntroductions;//步骤说明


    @Override
    protected PlanDetailPresent createPresenter() {
        return new PlanDetailPresent(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intentFromSelect = getIntent();
        if (intentFromSelect != null) {
            initDataFromIntent(intentFromSelect);
        }
        super.onCreate(savedInstanceState);
    }

    private void initDataFromIntent(Intent intentFromSelect) {
        Bundle bundleFromSelect = intentFromSelect.getExtras();

        plan = (RoutePlan) bundleFromSelect.getSerializable("plan");

        strStartStation = bundleFromSelect.getString("strStartStation");
        strStartStation = strStartStation.substring(0, strStartStation.indexOf("-"));

        strTerminalStation = bundleFromSelect.getString("strTerminalStation");
        strTerminalStation = strTerminalStation.substring(0, strTerminalStation.indexOf("-"));

        //plan中信息
        strDistance = "总路程: " + "<font color = \"#e37479\">" + plan.getDistance() + "</font>公里";
        strDuration = "预计用时: " + "<font color = \"#e37479\">" + plan.getDuration() + "</font>分钟";

        busNames = plan.getBusNames();
        stepIntroductions = plan.getStepIntroductions();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_route_detail);
        findAllViewById();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
            }
        });

        tvStartStation.setText(strStartStation);
        tvEndStation.setText(strTerminalStation);

        tvDistance.setText(Html.fromHtml(strDistance));
        tvDuration.setText(Html.fromHtml(strDuration));

        layoutManagerBus = new LinearLayoutManager(this);
        layoutManagerStep = new LinearLayoutManager(this);

        rvBus.setLayoutManager(layoutManagerBus);
        rvBus.setHasFixedSize(true);

        rvIntro.setLayoutManager(layoutManagerStep);
        rvIntro.setHasFixedSize(true);

        busAdapter = new PlanDetailAdapter(this, AppConstants.PLAN_DETAIL_BUS_TAG);
        busAdapter.setData(busNames);
        busAdapter.setOnPlanDetailItemClickListener(new PlanDetailAdapter.OnPlanDetailItemClickListener() {
            @Override
            public void onItemClick(View view, String str) {
                Intent intent = new Intent(PlanDetailActivity.this, RouteStationActivity.class);
                intent.putExtra("busLineName", str);
                intent.putExtra("city", AppConstants.city);
                startActivity(intent);
            }
        });

        stepAdapter = new PlanDetailAdapter(this, AppConstants.PLAN_DETAIL_STEP_TAG);
        stepAdapter.setData(stepIntroductions);

        rvBus.setAdapter(busAdapter);
        rvIntro.setAdapter(stepAdapter);


    }

    private void findAllViewById() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvStartStation = (TextView) findViewById(R.id.tv_start_station);
        tvEndStation = (TextView) findViewById(R.id.tv_end_station);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        rvBus = (RecyclerView) findViewById(R.id.rv_bus);
        rvIntro = (RecyclerView) findViewById(R.id.rv_intro);
    }

    @Override
    protected void initVariables() {

    }
}
