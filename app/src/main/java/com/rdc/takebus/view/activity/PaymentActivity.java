package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseActivity;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.entity.BillInfo;
import com.rdc.takebus.model.adapter.KeyboardAdapter;
import com.rdc.takebus.model.utils.TimeUtil;
import com.rdc.takebus.presenter.ActivityPresenter.PaymentPresenter;
import com.rdc.takebus.presenter.tbinterface.OnKeyboardPressListener;
import com.rdc.takebus.presenter.tbinterface.PaymentInterface;
import com.rdc.takebus.view.CustomView.CustomToast;

import dmax.dialog.SpotsDialog;

/**
 * 支付界面
 */
public class PaymentActivity extends BaseSwipeBackActivity<PaymentInterface, PaymentPresenter>
        implements PaymentInterface, OnKeyboardPressListener, View.OnClickListener {

    private static final String TAG = "PaymentActivity";

    public static final String FROM_PAYMENT_DATA = "from payment data";
    private ViewGroup mVpPassword;
    private GridView mGvKeyboard;
    private ImageView mIvClose;

    // 对话框
    private SpotsDialog mSpotsDialog;

    private KeyboardAdapter mKeyboardAdapter;

    // 支付价格
    private String mPrice;
    // 支付密码
    private String mPayPsw = "";
    private String mStartStation;
    private String mTerminalStation;
    private String mRouteName;

    @Override
    protected PaymentPresenter createPresenter() {
        return new PaymentPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_payment);
        super.onCreate(savedInstanceState);
        mPrice = mPresenter.getPriceData(getIntent());
        mStartStation = mPresenter.getStartStationData(getIntent());
        mTerminalStation = mPresenter.getTerminalStationData(getIntent());
        mRouteName = mPresenter.getRouteNameData(getIntent());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mVpPassword = (ViewGroup) findViewById(R.id.ll_payment_password);
        mGvKeyboard = (GridView) findViewById(R.id.gv_payment_keyboard);
        mIvClose = (ImageView) findViewById(R.id.iv_keyboard_back);
        mSpotsDialog = new SpotsDialog(this);

        mKeyboardAdapter = new KeyboardAdapter(this);
        mKeyboardAdapter.setOnKeyboardPressListener(this);
        mGvKeyboard.setAdapter(mKeyboardAdapter);
        mIvClose.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mKeyboardAdapter = new KeyboardAdapter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_keyboard_back:
                finish();
                break;
        }
    }


    @Override
    public void onKeyboardPress(String num) {
        if (num.equals(KeyboardAdapter.DELETE)) {
            // 删除键
            if (!TextUtils.isEmpty(mPayPsw)) {
                mVpPassword.getChildAt((mPayPsw.length() - 1) * 2).setVisibility(View.INVISIBLE);
                mPayPsw = mPayPsw.substring(0, mPayPsw.length() - 1);
            }
            return;
        }
        mPayPsw += num;

        int length = mPayPsw.length();
        for (int i = 0; i < mVpPassword.getChildCount(); i += 2) {
            if (i < 2 * length) {
                mVpPassword.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mVpPassword.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }

        if (length == 6) {
            mSpotsDialog.show();
            mPresenter.doPayment(mPayPsw, mPrice, mStartStation, mTerminalStation, mRouteName);
            mPayPsw = "";
            for (int i = 0; i < mVpPassword.getChildCount(); i += 2) {
                mVpPassword.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onSuccess(String id, String result) {


    }

    @Override
    public void onFail(String result) {
        mSpotsDialog.dismiss();
        CustomToast.showToast(this, result, Toast.LENGTH_SHORT);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            finish();
        return true;
    }

    @Override
    public void startDetailActivity(BillInfo info) {
        CustomToast.showToast(this, "支付成功", Toast.LENGTH_SHORT);
        mSpotsDialog.dismiss();

        Intent intent = new Intent(this, BillDetailActivity.class);
        intent.putExtra(FROM_PAYMENT_DATA, info);
        intent.putExtra(BillDetailActivity.TO_TYPE, FROM_PAYMENT_DATA);
        startActivity(intent);
        finish();
    }
}
