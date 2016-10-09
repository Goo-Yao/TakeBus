package com.rdc.takebus.model.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.rdc.takebus.R;
import com.rdc.takebus.entity.Bus;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.MyOrientationUtil;
import com.rdc.takebus.view.CustomView.CustomToast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;

/**
 * 闹钟服务
 * Created by ZZH on 2016/5/23.
 */
public class LocationService extends Service implements BDLocationListener, OnGetGeoCoderResultListener {
    private LocationClient mLocationClient;
    private LocationClientOption option;
    private boolean isFirstLoc = true;
    private GeoCoder mSearch = null;
    private BaiduMap mBaiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private MyOrientationUtil orientationUtil;
    private BitmapDescriptor mBusMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_bus);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLocationClient();
    }

    private void initLocationClient() {
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(this);
        if (AppConstants.mMapView != null) {
            mBaiduMap = AppConstants.mMapView.getMap();
            initLocationClientOption();

            orientationUtil = new MyOrientationUtil(getApplicationContext());
            orientationUtil.setOnOrientationListener(new MyOrientationUtil.OnOrientationListener() {
                @Override
                public void onOrientationChange(float x) {
                    setDirection(x);
                    MyLocationData data = new MyLocationData.Builder()// 方向设置
                            .direction(AppConstants.direction)
                            .accuracy(AppConstants.accuracy)// 此处设置开发者获取到的方向信息，顺时针0-360
                            .latitude(AppConstants.latitude)// 纬度
                            .longitude(AppConstants.longiTude).build();
                    mBaiduMap.setMyLocationData(data);
                    MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, null);
                    mBaiduMap.setMyLocationConfigeration(config);
                }
            });
            orientationUtil.star();
        }
        mLocationClient.setLocOption(option);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    private void initLocationClientOption() {
        option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        option.setScanSpan(1000);// 每1秒请求一次
    }

    private void setDirection(float x) {
        AppConstants.direction = x;
    }

    private void setLongitude(double longiTude) {
        AppConstants.longiTude = longiTude;
    }

    private void setLatitude(double latitude) {
        AppConstants.latitude = latitude;
    }

    private void setCurrentCity() {
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        LatLng ptCenter = new LatLng(AppConstants.latitude, AppConstants.longiTude);
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(ptCenter));
    }

    //公车覆盖物
    public void addBusMarkers(double lat, double lng) {
        if (AppConstants.mMapView != null) {

            mBaiduMap.clear();
            // 经纬度
            LatLng latlng = new LatLng(lat, lng);
            // Marker图标,图层高度
            OverlayOptions options = new MarkerOptions().position(latlng).icon(mBusMarker).zIndex(5);
            Marker marker = (Marker) mBaiduMap.addOverlay(options);
        }
    /*    Bundle bundle = new Bundle();
        bundle.putSerializable("bus", bus);
        marker.setExtraInfo(bundle);
*/
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        setLongitude(location.getLongitude());
        setLatitude(location.getLatitude());
        setAccuracy(location.getRadius());
        setCurrentCity();
        if (AppConstants.mMapView != null) {

            mBaiduMap.setMyLocationEnabled(true);
            MyLocationData data = new MyLocationData.Builder()// 方向设置
                    .accuracy(AppConstants.accuracy)
                    .direction(AppConstants.direction)// 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(AppConstants.latitude)// 纬度
                    .longitude(AppConstants.longiTude).build();
            mBaiduMap.setMyLocationData(data);
            MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, null);
            mBaiduMap.setMyLocationConfigeration(config);

            // 第一次定位时候，把地图转移到中心点
            if (isFirstLoc) {
                isFirstLoc = false;
                // 经度、纬度
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(msu);
                CustomToast.showToast(getApplicationContext(), location.getAddrStr(), Toast.LENGTH_SHORT);
            }
        }
        //如果设置了闹钟，则发送广播计算位置
        if (AppConstants.isAlarming)
            EventBus.getDefault().post(location);
    }

    private void setAccuracy(float accuracy) {
        AppConstants.accuracy = accuracy;
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            AppConstants.city = null;
            return;
        }
        String address = result.getAddress();
        if (address != null && !address.equals("")) {
            AppConstants.city = address.substring(address.indexOf("省") + 1, address.indexOf("市"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        if (mLocationClient != null)
            mLocationClient.stop();
        if (orientationUtil != null)
            orientationUtil.stop();
    }
}
