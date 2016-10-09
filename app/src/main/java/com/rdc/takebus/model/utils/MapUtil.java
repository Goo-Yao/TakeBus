package com.rdc.takebus.model.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.rdc.takebus.view.CustomView.CustomToast;

import java.util.List;

/**
 * Created by 梦涵 on 2016/5/11.
 */
public class MapUtil {

    //计算两点之间的纬度距离
    public static double calculateDistance(double long1, double lat1, double long2,
                                           double lat2) {
        double tagA, tagB, earthRadius;
        double distance;
        double sa2, sb2;
        earthRadius = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        tagA = lat1 - lat2;
        tagB = (long1 - long2) * Math.PI / 180.0;
        sa2 = Math.sin(tagA / 2.0);
        sb2 = Math.sin(tagB / 2.0);
        distance = 2 * earthRadius * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return distance;
    }

    /**
     * 查询站点
     *
     * @param strStation 站点关键字
     */
    public void findStation(String strStation) {

    }

}

