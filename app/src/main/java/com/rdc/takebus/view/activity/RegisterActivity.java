package com.rdc.takebus.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.RegisterPresenter;
import com.rdc.takebus.presenter.tbinterface.RegisterInterface;
import com.rdc.takebus.view.CustomView.CustomToast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseSwipeBackActivity<RegisterInterface, RegisterPresenter>
        implements RegisterInterface, TextWatcher, View.OnClickListener {
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtConfirmPsw;
    private EditText mEtIdentifyCode;

    private ImageView mIvRegisterCode;
    private ImageView mIvNext;
    private ImageView mIvBack;
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mEtUsername = (EditText) findViewById(R.id.et_register_username);
        mEtPassword = (EditText) findViewById(R.id.et_register_password);
        mEtConfirmPsw = (EditText) findViewById(R.id.et_register_confirm_psw);
        mEtIdentifyCode = (EditText) findViewById(R.id.et_register_identify_code);

        mIvNext = (ImageView) findViewById(R.id.iv_register_next);
        mIvBack = (ImageView) findViewById(R.id.ivBackToLogin);
        mIvRegisterCode = (ImageView) findViewById(R.id.iv_register_identify_code);
        // 监听输入状态
        mEtUsername.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mEtConfirmPsw.addTextChangedListener(this);
        mEtIdentifyCode.addTextChangedListener(this);

        // 点击事件
        mIvNext.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mIvRegisterCode.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter.getIdentifyCodeNetwork();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_register_next:
                mPresenter.doRegister();
                break;
            case R.id.ivBackToLogin:
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                break;
            case R.id.iv_register_identify_code:
                mPresenter.getIdentifyCodeNetwork();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        String confirmPsw = mEtConfirmPsw.getText().toString();
        String identifyCode = mEtIdentifyCode.getText().toString();
        if (username.length() >= 6 && password.length() >= 6
                && confirmPsw.length() >= 6 && identifyCode.length() == 4) {
            mIvNext.setImageResource(R.drawable.btn_next);
        } else {
            mIvNext.setImageResource(R.drawable.btn_next_gray);
        }
    }

    @Override
    public String getUsername() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public String getConfirmPsw() {
        return mEtConfirmPsw.getText().toString();
    }

    @Override
    public String getIdentifyCode() {
        return mEtIdentifyCode.getText().toString();
    }

    @Override
    public void showToast(String message) {
        CustomToast.showToast(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onSuccess(String id, String result) {
        switch (id) {
            case AppConstants.URL_REGISTER:
                showToast("注册成功");
                map.put("userName", mEtUsername.getText().toString());
                map.put("passWord", mEtPassword.getText().toString());
                EventBus.getDefault().post(map);
                finish();
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case AppConstants.URL_VALIDATE:
//                showToast("验证码获取成功");
                Bitmap bitmap = mPresenter.loadBitmapFromFile(result);
                mIvRegisterCode.setImageBitmap(bitmap);
                break;
        }

    }

    @Override
    public void onFail(String result) {
        showToast(result);
    }
}