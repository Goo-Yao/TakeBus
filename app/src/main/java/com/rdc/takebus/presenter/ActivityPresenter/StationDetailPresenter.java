package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.JsonParseUtil;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbinterface.ResultListener;
import com.rdc.takebus.presenter.tbinterface.StationDetailInterface;

import dmax.dialog.SpotsDialog;

/**
 * Created by ZZH on 2016/5/20.
 */
public class StationDetailPresenter extends BaseActivityPresenter<StationDetailInterface> {
    private Context context;
    private StationDetailInterface mStaionDetailInterface;
    private int cityId;
    private String stationName;
    private SpotsDialog dialog;

    public StationDetailPresenter(Context context, StationDetailInterface mStaionDetailInterface) {
        this.context = context;
        this.mStaionDetailInterface = mStaionDetailInterface;
    }

    public void setCityIdAndName(int cityId, String stationName) {
        this.cityId = cityId;
        this.stationName = stationName;
    }

    public void getStationDetailData() {
        showProgressDialog();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_OHTER).getData(AppConstants.getStationUrl(cityId, stationName), new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                Log.e("result", result + ">>>");
                if (result.contains("ok")) {
                    mStaionDetailInterface.loadStationData(JsonParseUtil.parseStationJson(result));
                } else
                    mStaionDetailInterface.onFail("获取数据失败");
                dissmissProgressDialog();
            }

            @Override
            public void onResultFail(String result) {
                dissmissProgressDialog();
                mStaionDetailInterface.onFail("获取数据失败");
            }
        }, (Activity) context);
    }

    public void showProgressDialog() {
        dialog = new SpotsDialog(context);
        dialog.show();
    }

    public void dissmissProgressDialog() {
        dialog.dismiss();
    }
}
