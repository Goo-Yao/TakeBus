package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.entity.BillInfo;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbinterface.PaymentInterface;
import com.rdc.takebus.presenter.tbinterface.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 支付界面处理中介
 */
public class PaymentPresenter extends BaseActivityPresenter<PaymentInterface> {
    private static final String TAG = "PaymentPresenter";
    private PaymentInterface mPaymentInterfaceImpl;

    public PaymentPresenter(PaymentInterface paymentInterface) {
        this.mPaymentInterfaceImpl = paymentInterface;
    }

    /**
     * 联网支付
     *
     * @param payPsw 支付密码
     * @param price  支付价格
     */
    public void doPayment(String payPsw, String price, String startStation, String terminalStation, String routeName) {
        Log.e(TAG, "payPsw = " + payPsw + "   price = " + price);
        RequestBody requestBody = new FormBody.Builder()
                .add(AppConstants.PAYPASSWORD, payPsw)
                .add(AppConstants.COST, price)
                .add(AppConstants.STARTPOINT, startStation)
                .add(AppConstants.ENDPOINT, terminalStation)
                .add(AppConstants.ROUTE, routeName)
                .build();
        Request request = new Request.Builder()
                .url(AppConstants.URL_PAYMENT)
                .post(requestBody).build();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).postData(request, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String state = jsonObject.getString(AppConstants.RESULT);
                        if (state.equals(AppConstants.SUCCESS)) {
                            String jsonData = jsonObject.getString(AppConstants.ORDER);

                            BillInfo info = new Gson().fromJson(jsonData, BillInfo.class);
                            mPaymentInterfaceImpl.startDetailActivity(info);

                            // 清空AppConstants里面对应的值
                            AppConstants.startStation = "";
                            AppConstants.terminalStation = "";
                        } else {
                            mPaymentInterfaceImpl.onFail(state);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    mPaymentInterfaceImpl.onFail("数据错误");
                }
            }

            @Override
            public void onResultFail(String result) {
                mPaymentInterfaceImpl.onFail(result);
            }
        }, (Activity) mPaymentInterfaceImpl);
    }


    /**
     * 获取从上个activity传来的价格数据
     */
    public String getPriceData(Intent intent) {
        // 获取价格信息
        String price = intent.getStringExtra(AppConstants.COST);
        if (TextUtils.isEmpty(price)) {
            price = "1";
        }
        return price;
    }


    public String getStartStationData(Intent intent) {
        String startStation = intent.getStringExtra(AppConstants.STARTPOINT);
        return startStation;
    }

    public String getTerminalStationData(Intent intent) {
        String terminal = intent.getStringExtra(AppConstants.ENDPOINT);
        return terminal;
    }

    public String getRouteNameData(Intent intent) {
        String route = intent.getStringExtra(AppConstants.ROUTE);
        return route;
    }
}
