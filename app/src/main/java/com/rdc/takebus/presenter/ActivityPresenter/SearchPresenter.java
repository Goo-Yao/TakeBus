package com.rdc.takebus.presenter.ActivityPresenter;

import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.tbinterface.SearchStationViewInterface;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public class SearchPresenter extends BaseActivityPresenter<SearchStationViewInterface> implements OnGetGeoCoderResultListener {
    private SearchStationViewInterface searchStationViewInterface;

    public SearchPresenter(SearchStationViewInterface searchStationViewInterface) {
        this.searchStationViewInterface = searchStationViewInterface;
    }

    public String getCurrentCity() {
        if (AppConstants.city != null) {
            return AppConstants.city;
        } else {
            return null;
        }
    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {


    }
}

