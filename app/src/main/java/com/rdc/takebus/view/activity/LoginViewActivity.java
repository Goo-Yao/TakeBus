package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.LoginPresenter;
import com.rdc.takebus.presenter.tbinterface.LoginViewInterface;
import com.rdc.takebus.view.CustomView.CustomToast;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public class LoginViewActivity extends BaseSwipeBackActivity<LoginViewInterface, LoginPresenter> implements LoginViewInterface, View.OnClickListener {
    private ImageView ivBtnBack;//返回按钮
    private ImageView ivLogin;//登录按钮
    private ImageView ivRegister;//注册按钮
    private ImageView imgQq;//qq登录
    private ImageView imgWeChat;//微信登录
    private ImageView imgBao;//支付宝登录
    private EditText etAccount;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findAllViewById();
        setAllOnClickListener();
        Toast.makeText(LoginViewActivity.this, "服务器已失效，请直接点击登录按钮进入", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().register(this);
    }

    private void setAllOnClickListener() {
        ivLogin.setOnClickListener(this);
        ivRegister.setOnClickListener(this);
        imgQq.setOnClickListener(this);
        imgWeChat.setOnClickListener(this);
        imgBao.setOnClickListener(this);
        ivBtnBack.setOnClickListener(this);
    }

    private void findAllViewById() {
        ivLogin = (ImageView) findViewById(R.id.ivLogin);
        ivRegister = (ImageView) findViewById(R.id.ivRegister);
        imgQq = (ImageView) findViewById(R.id.img_qq);
        imgWeChat = (ImageView) findViewById(R.id.img_wechat);
        imgBao = (ImageView) findViewById(R.id.img_bao);
        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        ivBtnBack = (ImageView) findViewById(R.id.iv_back);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLogin:
//                mPresenter.login(AppConstants.Local);
//                //登录成功
//                if (mPresenter.login(AppConstants.Local)) {
                startActivity(new Intent(LoginViewActivity.this, MainActivity.class));
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                AppConstants.splashActivity.finish();//登录成功，把第一个页面也销毁掉
                finish();
//                } else {
//
//                }
                break;
            case R.id.ivRegister:
                //注册
                startActivity(new Intent(LoginViewActivity.this, RegisterActivity.class));
                setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
                break;
            case R.id.img_bao:
                //支付宝登录
                showMsgByToast("待接入支付宝");
                break;
            case R.id.img_wechat:
                //微信登录
                showMsgByToast("待接入微信");
                break;

            case R.id.img_qq:
                mPresenter.login(AppConstants.QQ);
                break;
            case R.id.iv_back:
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public String getUserName() {
        return etAccount.getText().toString();
    }

    @Override
    public String getUserPsw() {
        return etPassword.getText().toString();
    }

    @Override
    public void showMsgByToast(String strMsg) {
        CustomToast.showToast(this, strMsg, Toast.LENGTH_SHORT);
    }

    @Subscribe
    public void onEventMainThread(Map<String, String> map) {

        etAccount.setText(map.get("userName"));
        etPassword.setText(map.get("passWord"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccess(String id, String result) {
        CustomToast.showToast(LoginViewActivity.this, "登陆成功", Toast.LENGTH_SHORT);
        startActivity(new Intent(LoginViewActivity.this, MainActivity.class));
        setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);
        AppConstants.splashActivity.finish();//登录成功，把第一个页面也销毁掉
        finish();
    }

    @Override
    public void onFail(String result) {
        showMsgByToast(result);
    }

    //解决某些低端机登录成功之后无法回传数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mPresenter.loginListener);
    }
}
