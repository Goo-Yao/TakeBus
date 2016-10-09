package com.rdc.takebus.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.A;
import com.baidu.platform.comapi.map.C;
import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseActivity;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.entity.BillInfo;
import com.rdc.takebus.entity.BillPaidInfo;
import com.rdc.takebus.entity.Line;
import com.rdc.takebus.entity.Route;
import com.rdc.takebus.entity.Station;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.JsonParseUtil;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.ActivityPresenter.BillDetailPresenter;
import com.rdc.takebus.presenter.tbinterface.BillDetailInterface;
import com.rdc.takebus.presenter.tbinterface.ResultListener;
import com.rdc.takebus.view.CustomView.CustomToast;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * 账单详细Activity
 */
public class BillDetailActivity extends BaseSwipeBackActivity<BillDetailInterface, BillDetailPresenter>
        implements BillDetailInterface, View.OnClickListener {
    private ImageView mIvQRCode;
    private TextView mTvTitle;
    private ImageView mIvBack;
    private TextView mTvStartStation;
    private TextView mTvTerminalStation;
    private TextView mTvPayTime;
    private TextView mTvCost;
    private TextView mTvRoute;

    private SpotsDialog mSpotsDialog;

    private BillInfo mBillInfo;

    // 传给该activity的activity类型
    public static final String TO_TYPE = "to type";
    private String mLastActivityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_detail);
        mLastActivityType = getIntent().getStringExtra(TO_TYPE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BillDetailPresenter createPresenter() {
        return new BillDetailPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mIvQRCode = (ImageView) findViewById(R.id.iv_bill_detail_qrcode);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvStartStation = (TextView) findViewById(R.id.tv_bill_detail_start);
        mTvTerminalStation = (TextView) findViewById(R.id.tv_bill_detail_terminal);
        mTvPayTime = (TextView) findViewById(R.id.tv_bill_detail_time);
        mTvCost = (TextView) findViewById(R.id.tv_bill_detail_cost);
        mTvRoute = (TextView) findViewById(R.id.tv_bill_detail_route);
        mSpotsDialog = new SpotsDialog(this);

        mIvBack.setOnClickListener(this);

        setInfoData();
    }

    /**
     * 设置文本数据
     */
    private void setInfoData() {
        mTvTitle.setText("订单详情");
        mBillInfo = mPresenter.getBillInfoData(getIntent());
        if (mBillInfo != null) {
            mTvStartStation.setText(mBillInfo.getStartPoint());
            mTvTerminalStation.setText(mBillInfo.getEndPoint());
            mTvPayTime.setText("支付时间：" + mBillInfo.getCreateTime());
            mTvCost.setText("票价：￥" + mBillInfo.getCost());
            mTvRoute.setText("线路：" + mBillInfo.getRoute());
        } else {
            CustomToast.showToast(this, "无订单数据", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void initVariables() {
        mSpotsDialog.show();
        Log.e("6666666666", "----*****************************" + mBillInfo);
        mPresenter.getQrCodeImageNetwork(mBillInfo.getQrCoderUrl());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onSuccess(String id, String result) {
        mSpotsDialog.dismiss();
        Bitmap bitmap = mPresenter.getImageFromFile(result);
        mIvQRCode.setImageBitmap(bitmap);
        mIvQRCode.getLayoutParams().height = mIvQRCode.getMeasuredWidth();
    }

    @Override
    public void onFail(String result) {
        mSpotsDialog.dismiss();
        CustomToast.showToast(this, result, Toast.LENGTH_SHORT);
    }

    public void getRouteStation(final BillPaidInfo billPaidInfo) {
        mSpotsDialog.show();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_OHTER).getData(AppConstants.getRouteUrl(75, billPaidInfo.getRouteName()), new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                Log.e("result", result + ">>>");
                mSpotsDialog.dismiss();
                if (result.contains("ok")) {
                    List<Route> listRoute = JsonParseUtil.parseRouteJson(result);
                    List<Station> listStation = listRoute.get(0).getList();
                    int tag = 0;
                    for (int i = 0; i < listStation.size(); i++) {
                        Station station = listStation.get(i);
                        if (station.getStation().equals(billPaidInfo.getStartStation())) {
                            tag = 1;
                        }
                        if (station.getStation().equals(billPaidInfo.getTerminalStation())) {
                            if (tag == 1) {
                                tag = 2;
                                break;
                            } else
                                break;
                        }
                    }
                    if (tag == 0)
                        Collections.reverse(listStation);
                    EventBus.getDefault().post(new Line(billPaidInfo.getRouteName(),billPaidInfo.getStartStation() , billPaidInfo.getTerminalStation() , listStation));
                    Intent intent = new Intent(BillDetailActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                } else {
                    CustomToast.showToast(BillDetailActivity.this, "获取公交信息失败", Toast.LENGTH_SHORT);
                    mSpotsDialog.dismiss();
                }
            }

            @Override
            public void onResultFail(String result) {
                mSpotsDialog.dismiss();
                CustomToast.showToast(BillDetailActivity.this, "获取公交信息失败", Toast.LENGTH_SHORT);
            }
        }, BillDetailActivity.this);
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(mLastActivityType) && mLastActivityType.equals(PaymentActivity.FROM_PAYMENT_DATA)) {
            // 封装已付款的账单的信息
            BillPaidInfo billPaidInfo = new BillPaidInfo(mBillInfo.getRoute(), mBillInfo.getStartPoint(), mBillInfo.getEndPoint());
            getRouteStation(billPaidInfo);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        onBackPressed();
        return true;
    }

    @Override
    public void showToast(String msg) {
        CustomToast.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    private boolean activited = true;

    @Override
    public boolean isActivited() {
        return activited;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBillInfo != null) {
            activited = true;
            mPresenter.repeatCheckBillState(mBillInfo.getId());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activited = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activited = false;
    }
}
