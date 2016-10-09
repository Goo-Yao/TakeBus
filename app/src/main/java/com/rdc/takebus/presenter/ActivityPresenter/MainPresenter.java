package com.rdc.takebus.presenter.ActivityPresenter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.entity.Bus;
import com.rdc.takebus.entity.RecyclerStation;
import com.rdc.takebus.entity.Route;
import com.rdc.takebus.entity.Station;
import com.rdc.takebus.model.adapter.RouteAdapter;
import com.rdc.takebus.model.holder.RouteHolder;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.JsonParseUtil;
import com.rdc.takebus.model.utils.MapUtil;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbabstract.OnRecyclerItemClickClass;
import com.rdc.takebus.presenter.tbinterface.MapInterface;
import com.rdc.takebus.presenter.tbinterface.ResultListener;
import com.rdc.takebus.view.CustomView.CustomToast;
import com.rdc.takebus.view.activity.AlarmActivity;
import com.rdc.takebus.view.activity.DrawerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by 梦涵 on 2016/5/11.
 */
public class MainPresenter extends BaseActivityPresenter<MapInterface> {
    private MapInterface mapInterface;
    private BaiduMap map;
    private Context context;
    // 覆盖物相关
    private BitmapDescriptor mBusMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_bus);
    private Activity mActivity;
    private int flag = 0; // 点击隐藏station的判定，第一次隐藏，第二次显示
    private int viewHeight = 0;
    private LatLng latLng;
    private int startPoint = 0;
    private int endPoint = 0;
    //50米报闹钟
    private static double BUS_DISTANCE = 50;
    //锁屏、唤醒相关
    private KeyguardManager.KeyguardLock kl;
    public MainPresenter(MapInterface mapInterface) {
        this.mapInterface = mapInterface;
    }

    public void setContext(Context context, Activity activity) {
        this.context = context;
        mActivity = activity;
    }

    public void initMap(BaiduMap map) {
        this.map = map;
        //移除百度地图的logo
        AppConstants.mMapView.removeViewAt(1);
        //删除缩放按钮
        View scale = AppConstants.mMapView.getChildAt(1);
        scale.setVisibility(View.GONE);
        //删除比例尺
        View rule = AppConstants.mMapView.getChildAt(2);
        rule.setVisibility(View.GONE);
        //删除指南针
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setCompassEnabled(false);
    }


    public void initLocation() {
        // 一开始显示的放大倍数在500m左右
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mapInterface.locationInit(msu);
    }

    public void route(RecyclerView recyclerView, final RouteAdapter adapter, final List<RecyclerStation> list, final Context context) {
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickClass(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                RouteHolder holder = (RouteHolder) vh;
                CustomToast.showToast(context, holder.getTextView().getText() + ">>" + holder.getPoint() + ">>", Toast.LENGTH_SHORT);
                final int point = holder.getPoint();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(list.get(point).getBusStation().getStation());
                if (list.get(point).isAlarming())
                    builder.setMessage("是否取消闹钟");
                else
                    builder.setMessage("是否设置闹钟");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (list.get(point).isAlarming()) {
                            list.get(point).setAlarming(false);
                            AppConstants.isAlarming = false;
                        } else {
                            list.get(point).setAlarming(true);
                            AppConstants.isAlarming = true;
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {

            }
        });
    }

    public void addBusMarkers(List<Bus> buses) {
        map.clear();
        LatLng latlng = null;// 经纬度对象
        Marker marker = null;
        OverlayOptions options;
        for (Bus bus : buses) {
            // 经纬度
            latlng = new LatLng(bus.getLatitude(), bus.getLongtitude());
            // Marker图标,图层高度
            options = new MarkerOptions().position(latlng).icon(mBusMarker).zIndex(5);
            marker = (Marker) map.addOverlay(options);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bus", bus);
            marker.setExtraInfo(bundle);
        }
        // 把地图回到最后一个地址
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latlng);
        mapInterface.addMarkers(msu);
    }


    // 隐藏Station
    public void animate(View viewChild, View view, RouteAdapter mRouteAdapter, Context context) {
        ObjectAnimator animator, anim_alpha;
        Animation anim;
        if (viewHeight == 0) {
            viewHeight = viewChild.getHeight();
        }
        if (flag % 2 == 0) {
            anim = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
            anim.setFillAfter(true);
            animator = ObjectAnimator.ofFloat(viewChild, "scaleY", 1.0f, 0.0f);
            anim_alpha = ObjectAnimator.ofFloat(viewChild, "alpha", 1.0f, 0.0f);
        } else {
            anim = AnimationUtils.loadAnimation(context, R.anim.rotate_down);
            anim.setFillAfter(true);
            animator = ObjectAnimator.ofFloat(viewChild, "scaleY", 0.0f, 1.0f);
            anim_alpha = ObjectAnimator.ofFloat(viewChild, "alpha", 0.0f, 1.0f);
        }
        //开启动画
        view.startAnimation(anim);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300).playTogether(anim_alpha, animator);
        set.start();
        animatorUpdate(animator, viewChild, mRouteAdapter, viewHeight);
        flag++;
    }

    //动画伸缩
    public void animatorUpdate(ObjectAnimator animator, final View view, final RouteAdapter mRouteAdapter, final int viewHeight) {
        final LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) view.getLayoutParams();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float f = (Float) animation.getAnimatedValue();
                llp.height = Math.round(f * viewHeight);
                view.setLayoutParams(llp);
                //通知RecyclerView重新绘制
                mRouteAdapter.notifyDataSetChanged();

            }
        });
    }

    /**
     * 返回我的位置
     */
    public void returnToMylocation(FloatingActionButton floatingActionButton) {
        if (latLng == null) {
            latLng = new LatLng(AppConstants.latitude, AppConstants.longiTude);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mapInterface.returnToStation(msu);
        floatingActionButton.setSelected(true);
    }

    public void setStationPoint(int startPoint , int endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    //定位回調判斷距離顯示鬧鐘
    public void showLocationDistance(BDLocation location, List<RecyclerStation> listStations) {
        if (startPoint == 0 && endPoint == 0)
            setStationPoint(listStations);
        if (listStations.size() != 0) {
            if(startPoint < endPoint) {
                Station mStation = listStations.get(startPoint + 1).getBusStation();
                double distance = MapUtil.calculateDistance(location.getLongitude(), location.getLatitude(), mStation.getLng(), mStation.getLat());
                Log.e("distance", distance + ">>");
                setBusStationTag(startPoint, listStations);
                if (distance <= BUS_DISTANCE) {
                    startPoint++;
                    //跳转到下一个站
                    setBusStationTag(startPoint, listStations);
                    //如果下一个站有开启提醒功能则显示闹钟
                    if (listStations.get(startPoint).isAlarming()) {
                        //显示完则隐藏
                        listStations.get(startPoint).setAlarming(false);
                        //喚醒屏幕
                        wakeAndUnlock(true, listStations);
                    }
                }
                mapInterface.showBusLocation(startPoint);
            }
            else
                //完成本次的搭乘
                mapInterface.onFinish();
        }
    }
    private void setBusStationTag(int position, List<RecyclerStation> listStations) {
        //先清空
        for (RecyclerStation mRecyclerStation : listStations) {
            mRecyclerStation.setShowUpBus(false);
            mRecyclerStation.setShowDownBus(false);
        }
        //再设置
        if (position < endPoint) {
            listStations.get(position).setShowDownBus(true);
            listStations.get(position + 1).setShowUpBus(true);
        }
    }

    //获取起点站和终点站的位置
    private void setStationPoint(List<RecyclerStation> listStations) {
        for (int i = 0; i < listStations.size(); i++) {
            RecyclerStation mRecyclerStation = listStations.get(i);
            if (mRecyclerStation.isFirstStation())
                startPoint = i;
            if (mRecyclerStation.isEndStation())
                endPoint = i;
        }
    }

    public void startIntent(DrawerLayout drawerLayout, ScrollView scrollView, int position) {
        Intent intent = new Intent(mActivity, DrawerActivity.class);
        intent.putExtra("position", position);
        mActivity.startActivity(intent);
        drawerLayout.closeDrawer(scrollView);
    }

    private void wakeAndUnlock(boolean b , List<RecyclerStation> listStations) {
        if (b) {
            Intent dialogIntent = new Intent(context, AlarmActivity.class);
            dialogIntent.putExtra("stationName",
                    listStations.get(startPoint).getBusStation().getStation());
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dialogIntent);

            //得到键盘锁管理器对象
            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            kl = km.newKeyguardLock("unLock");
            //解锁
            kl.disableKeyguard();

        } else {
            //锁屏
            kl.reenableKeyguard();
        }

    }
}
