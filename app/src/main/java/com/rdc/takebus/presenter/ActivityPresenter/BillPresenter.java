package com.rdc.takebus.presenter.ActivityPresenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rdc.takebus.base.BaseFragmentPresenter;
import com.rdc.takebus.entity.BillInfo;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbinterface.MyBillInterface;
import com.rdc.takebus.presenter.tbinterface.ResultListener;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ”我的账单“处理中介
 */
public class BillPresenter extends BaseFragmentPresenter<MyBillInterface> {

    private static final String TAG = "MyBillInterface";
    private MyBillInterface myBillInterface;

    public BillPresenter(MyBillInterface myBillInterface) {
        this.myBillInterface = myBillInterface;
    }

    /**
     * 联网获取未完成的所有车票
     */
    public void getAllBillInfoNetwork(String type) {
        RequestBody requestBody = new FormBody.Builder()
                .add(AppConstants.CATEGORY, type)

                .build();
        final Request request = new Request.Builder()
                .url(AppConstants.URL_BILL_INFO)
                .post(requestBody)
                .build();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC)
                .postData(request, new ResultListener() {
                    @Override
                    public void onResultSuccess(String result) {
                        Log.e(TAG, "result =" + result + "=");
                        if (!result.equals("[]")) {
                            myBillInterface.onSuccess(AppConstants.URL_BILL_INFO, result);
                        } else {
                            myBillInterface.onFail("暂无车票数据");
                        }
                    }

                    @Override
                    public void onResultFail(String result) {
                        myBillInterface.onFail(result);
                    }
                }, myBillInterface.getFragmentActivity());
    }


    /**
     * 解析json字符串
     *
     * @param json 字符串
     * @return BillInfo数据容器
     */
    public List<BillInfo> getBillInfosFormJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<BillInfo>>() {
        }.getType());

    }

}
