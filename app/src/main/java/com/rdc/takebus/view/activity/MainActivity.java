package com.rdc.takebus.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ScrollingView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.google.gson.Gson;
import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseActivity;
import com.rdc.takebus.entity.Information;
import com.rdc.takebus.entity.Line;
import com.rdc.takebus.entity.RecyclerStation;
import com.rdc.takebus.entity.Station;
import com.rdc.takebus.model.adapter.RouteAdapter;
import com.rdc.takebus.model.service.LocationService;
import com.rdc.takebus.model.utils.AnimationUtil;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.BitmapUtil;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.ActivityPresenter.MainPresenter;
import com.rdc.takebus.presenter.tbinterface.MapInterface;
import com.rdc.takebus.presenter.tbinterface.ResultListener;
import com.rdc.takebus.view.CustomView.CircleImageView;
import com.rdc.takebus.view.CustomView.CustomToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity<MapInterface, MainPresenter> implements
        MapInterface {
    private ImageView ivUserMsg;
    private TextView selectRoute;
    private BaiduMap mBaiduMap;
    private FloatingActionButton fabMyLocation;
    private Context context;
    // drawerlayout相关
    private DrawerLayout drawer;
    private TextView selfdate, fingerprint, ring, about, history, bill, config, exit;
    private ScrollView llDrawerView;
    private FloatingActionButton fabSlideUp;
    private LayoutInflater inflater;
    private View view;

    private RecyclerView recyclerView;
    private RouteAdapter mRouteAdapter;
    private int currentPoint = 0;
    private Intent intentRouteStation, locationService;
    private CircleImageView imageHead;
    private TextView tvName;
    private int startPointInt;
    //FAB
    private View mTagClosestBusStation;
    private View mTagSearchRoute;
    private FloatingActionButton mFABClosestBusStation;
    private FloatingActionButton mFABSearchRoute;
    private FloatingActionButton mFABMain;
    private boolean isShowMenu = false;
    private Bitmap bitmap;
    private SpotsDialog dialog;
    private Gson gson;
    public List<RecyclerStation> listStations = new ArrayList<>();
    public static final int ROUTE_STATION_TAG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        dialog = new SpotsDialog(this);
        gson = new Gson();
        initDrawerData();
        mPresenter.setContext(context, this);
        mPresenter.initMap(mBaiduMap);
        startLocationService();
        mPresenter.initLocation();
        mPresenter.addBusMarkers(AppConstants.Buses);
        //设置闹钟
        mPresenter.route(recyclerView, mRouteAdapter, listStations, this);
    }

    private void initDrawerData() {
        getPersonData();
        bitmap = BitmapUtil.readBitmap();
        if (bitmap == null) {
            imageHead.setImageBitmap(BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.ic_default_user_icon_48dp));
        } else {
            imageHead.setImageBitmap(bitmap);
        }
    }

    private void startLocationService() {
        locationService = new Intent(MainActivity.this, LocationService.class);
        startService(locationService);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        fineAllViewById();
        setAllOnClickListener();
        fabMyLocation.setSelected(true);
        mBaiduMap = AppConstants.mMapView.getMap();
        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                fabMyLocation.setSelected(false);
                if (isShowMenu) {
                    startAnimationToHide();
                }
            }
        });

        //为RecyclerView设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mRouteAdapter = new RouteAdapter(this, RouteAdapter.ROUTE_TAG);
        mRouteAdapter.setListStations(listStations);
        recyclerView.setAdapter(mRouteAdapter);
        //设置两秒返回
        isDoubleBackDestory = true;
    }

    private void setAllOnClickListener() {
        fabMyLocation.setOnClickListener(this);
        ivUserMsg.setOnClickListener(this);
        selfdate.setOnClickListener(this);
        fingerprint.setOnClickListener(this);
        ring.setOnClickListener(this);
        about.setOnClickListener(this);
        config.setOnClickListener(this);
        exit.setOnClickListener(this);
        history.setOnClickListener(this);
        bill.setOnClickListener(this);
        fabSlideUp.setOnClickListener(this);
        mFABMain.setOnClickListener(this);
        mFABSearchRoute.setOnClickListener(this);
        mFABClosestBusStation.setOnClickListener(this);
        mTagClosestBusStation.setOnClickListener(this);
        mTagSearchRoute.setOnClickListener(this);
    }

    private void fineAllViewById() {

        //FAB
        mFABMain = (FloatingActionButton) findViewById(R.id.fab_route_main);
        mFABClosestBusStation = (FloatingActionButton) findViewById(R.id.fab_closest_station);
        mFABSearchRoute = (FloatingActionButton) findViewById(R.id.fab_search_route);
        mTagClosestBusStation = findViewById(R.id.cv_closest_station_tag);
        mTagSearchRoute = findViewById(R.id.cv_search_route_tag);

        imageHead = (CircleImageView) findViewById(R.id.civ_user_icon);
        tvName = (TextView) findViewById(R.id.tv_user_name);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        llDrawerView = (ScrollView) findViewById(R.id.ll_main_activity_drawer);
        selfdate = (TextView) findViewById(R.id.selfdate);
        fingerprint = (TextView) findViewById(R.id.fingerprint);
        ring = (TextView) findViewById(R.id.ring);
        about = (TextView) findViewById(R.id.about);
        config = (TextView) findViewById(R.id.config);
        exit = (TextView) findViewById(R.id.exit);
        history = (TextView) findViewById(R.id.history);
        bill = (TextView) findViewById(R.id.bill);
        ivUserMsg = (ImageView) findViewById(R.id.iv_ctrl_drawer);
        fabMyLocation = (FloatingActionButton) findViewById(R.id.fab_MyLocation);
        fabSlideUp = (FloatingActionButton) findViewById(R.id.fab_slide_up);
        selectRoute = (TextView) findViewById(R.id.selectRoute);
        selectRoute.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_station);
        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.activity_select_route, null);
        AppConstants.mMapView = (MapView) findViewById(R.id.bmapView);
    }

    @Override
    protected void initVariables() {
        context = this;
        EventBus.getDefault().register(MainActivity.this);
    }

    //定位回調判斷距離顯示鬧鐘
    @Subscribe
    public void onEventMainThread(BDLocation location) {
        Log.e("onEventLocation ", "location");
        mPresenter.showLocationDistance(location, listStations);
    }

    //定位回調判斷距離顯示鬧鐘
    @Subscribe
    public void onEventMainThread(String s) {
        // CustomToast.showToast(MainActivity.this, s + ">>", Toast.LENGTH_SHORT);
        //  mPresenter.getRouteStation();
    }

    //订单完成显示数据
    @Subscribe
    public void onEventMainThread(Line line) {
        //开启公交搭乘
        listStations.clear();
        for (int i = 0; i < line.getLists().size(); i++) {
            Station station = line.getLists().get(i);
            RecyclerStation mRecyclerStation = new RecyclerStation(station);
            if (mRecyclerStation.getBusStation().getStation().equals(line.getStartStation())) {
                mRecyclerStation.setFirstStation(true);
                startPointInt = i;
            } else if (mRecyclerStation.getBusStation().getStation().equals(line.getEndStation()))
                mRecyclerStation.setEndStation(true);
            listStations.add(mRecyclerStation);
        }
        mRouteAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(startPointInt);
        if (fabSlideUp.getVisibility() == View.GONE)
            fabSlideUp.setVisibility(View.VISIBLE);
        selectRoute.setText(line.getLineName());
        if (selectRoute.getVisibility() == View.GONE)
            selectRoute.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_route_main:
                if (isShowMenu) {
                    startAnimationToHide();
                } else {
                    startAnimationToShow();
                }
                break;
            case R.id.iv_ctrl_drawer:
                drawer.openDrawer((View) llDrawerView);
                break;
            case R.id.fab_MyLocation:// 返回我的位置
                mPresenter.returnToMylocation(fabMyLocation);
                break;
            case R.id.selfdate:
                mPresenter.startIntent(drawer, llDrawerView, 0);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.fingerprint:
                mPresenter.startIntent(drawer, llDrawerView, 1);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.ring:
                mPresenter.startIntent(drawer, llDrawerView, 2);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.about:
                mPresenter.startIntent(drawer, llDrawerView, 3);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.config:
                mPresenter.startIntent(drawer, llDrawerView, 4);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.history:
                mPresenter.startIntent(drawer, llDrawerView, 5);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.bill:
                mPresenter.startIntent(drawer, llDrawerView, 6);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.exit: {
                exit();
            }
            break;
            case R.id.ll_select_start_station:
                // 获取起点
                Intent intent1 = new Intent(this, SearchStationActivity.class);
                intent1.putExtra("station", "起点站");
                startActivityForResult(intent1, 1);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.ll_select_terminal_station:
                // 获取终点
                Intent intent2 = new Intent(this, SearchStationActivity.class);
                intent2.putExtra("station", "终点站");
                startActivityForResult(intent2, 2);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.fab_search_route:
                startActivity(new Intent(this, SelectPlanActivity.class));
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.fab_slide_up:
                mPresenter.animate(recyclerView, fabSlideUp, mRouteAdapter, MainActivity.this);
                break;
            case R.id.cv_search_route_tag:
            case R.id.selectRoute: {
                intentRouteStation = new Intent(MainActivity.this, RouteStationActivity.class);
                intentRouteStation.putExtra("city", "广州");
                intentRouteStation.putExtra("busLineName", selectRoute.getText().toString());
                intentRouteStation.putExtra("tag", ROUTE_STATION_TAG);
                startActivity(intentRouteStation);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
            }
            break;
            case R.id.fab_closest_station:
            case R.id.cv_closest_station_tag:
                startActivity(new Intent(MainActivity.this, NearestStationActivity.class));
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;

            default:
                break;
        }
        if (v.getId() != R.id.fab_route_main && isShowMenu) {
            startAnimationToHide();
        }
    }

    private void exit() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage("是否确定注销账号");
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
                if (AppConstants.LoginState == AppConstants.QQ) {
                    AppConstants.mTencent.logout(MainActivity.this);
                }
                Intent intent = new Intent(MainActivity.this, LoginViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                finish();

            }
        });
        dialog.show();
    }

    @Override
    public void locationInit(MapStatusUpdate msu) {
        mBaiduMap.setMapStatus(msu);
    }


    @Override
    public void addMarkers(MapStatusUpdate msu) {
        mBaiduMap.setMapStatus(msu);
    }

    @Override
    public void returnToStation(MapStatusUpdate msu) {
        mBaiduMap.animateMapStatus(msu);
    }

    //实时位置
    @Override
    public void showBusLocation(int point) {
        if (currentPoint == 0)
            currentPoint = point;
        if (currentPoint != point) {
            recyclerView.scrollToPosition(point);
            currentPoint = point;
        }
        mRouteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFinish() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowMenu) {
            // 如果菜单已经显示了，那按返回键就是关闭菜单
            startAnimationToHide();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭定位图层
        if (bitmap != null)
            bitmap.recycle();
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(false);
            AppConstants.mMapView.onDestroy();
        }
        //  AppConstants.listStations.clear();
        if (intentRouteStation != null)
            stopService(intentRouteStation);
        if (locationService != null)
            stopService(locationService);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listStations != null && mRouteAdapter != null)
            mRouteAdapter.notifyDataSetChanged();
        if (AppConstants.isInfoChanging) {
            initDrawerData();
            AppConstants.isInfoChanging = false;
        }

        AppConstants.mMapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppConstants.mMapView.onPause();
    }

    private int mRotateDegree = 0;  // 记录大按钮的旋转角度
    private int delta = 0;

    /**
     * 动画显示所有菜单栏
     */
    private void startAnimationToShow() {
        if (delta == 0) {
            // 设置delta偏移量为屏幕一半
            delta = getResources().getDisplayMetrics().widthPixels / 2;
        } else {
            mFABSearchRoute.setTranslationX(0);
            mFABClosestBusStation.setTranslationX(0);
            mTagSearchRoute.setTranslationX(0);
            mTagClosestBusStation.setTranslationX(0);
        }

        // 缩放动画显示
        Animation scaleAnim = AnimationUtil.getScaleToShowAnimDinning(this);
        Animation scaleAnim_2 = AnimationUtil.getScaleToShowAnimDinning(this);
        mFABSearchRoute.startAnimation(scaleAnim);
        mFABClosestBusStation.startAnimation(scaleAnim);
        mTagSearchRoute.startAnimation(scaleAnim_2);
        mTagClosestBusStation.startAnimation(scaleAnim_2);

        // 大按钮的旋转动画
        mFABMain.startAnimation(AnimationUtil.getFABRotateAnimDinning(mFABMain, mRotateDegree));

        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFABMain.setEnabled(false);
                mFABSearchRoute.setVisibility(View.VISIBLE);
                mFABClosestBusStation.setVisibility(View.VISIBLE);
                mTagSearchRoute.setVisibility(View.VISIBLE);
                mTagClosestBusStation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFABMain.setEnabled(true);
                isShowMenu = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 动画隐藏所有菜单
     */
    public void startAnimationToHide() {
        // 缩放动画隐藏
        Animation scaleAnim = AnimationUtil.getScaleToHideAnimDinning(this);
        Animation scaleAnim_2 = AnimationUtil.getScaleToHideAnimDinning(this);
        mFABSearchRoute.startAnimation(scaleAnim);
        mFABClosestBusStation.startAnimation(scaleAnim);
        mTagSearchRoute.startAnimation(scaleAnim_2);
        mTagClosestBusStation.startAnimation(scaleAnim_2);

        mFABMain.startAnimation(AnimationUtil.getFABRotateAnimDining_2(mFABMain, mRotateDegree));


        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 将大的按钮设置不可点击
                mFABMain.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFABMain.setEnabled(true);
                hideMenuItem();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 隐藏菜单所有内容
     */
    private void hideMenuItem() {
        mFABClosestBusStation.setTranslationX(delta);
        mFABSearchRoute.setTranslationX(delta);
        mTagClosestBusStation.setTranslationX(delta);
        mFABSearchRoute.setTranslationX(delta);
        isShowMenu = false;
    }

    //获取头像信息
    private void getHeadImage(String url) {
        Log.e("resultUrl", url + ">>");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                CustomToast.showToast(MainActivity.this, "获取头像失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                BitmapUtil.saveBitmap(bitmap);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageHead.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    //获取个人信息
    private void getPersonData() {
        dialog.show();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).getData(AppConstants.URL_PERSON_INFO, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                Log.e("result", result + ">>");
                if (result != null && !result.contains("html")) {
                    Information mInformation = gson.fromJson(result, Information.class);
                    if (mInformation.getNickName() != null)
                        tvName.setText(mInformation.getNickName());
                    else
                        tvName.setText("无");
                    if (bitmap == null)
                        if (mInformation.getIconUrl() != null)
                            getHeadImage(mInformation.getIconUrl());
                        else
                            dialog.dismiss();
                    else
                        dialog.dismiss();
                } else {
                    CustomToast.showToast(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT);
                    dialog.dismiss();
                }
            }

            @Override
            public void onResultFail(String result) {
                CustomToast.showToast(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        }, this);

    }

}
