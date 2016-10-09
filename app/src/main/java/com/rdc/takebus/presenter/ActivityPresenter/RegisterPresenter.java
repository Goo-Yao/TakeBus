package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbinterface.RegisterInterface;
import com.rdc.takebus.presenter.tbinterface.ResultListener;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 注册中介
 */
public class RegisterPresenter extends BaseActivityPresenter<RegisterInterface> {

    private RegisterInterface mRegisterActivity;

    public RegisterPresenter(RegisterInterface registerInterface) {
        this.mRegisterActivity = registerInterface;
    }

    public void doRegister() {
        String username = mRegisterActivity.getUsername();
        String password = mRegisterActivity.getPassword();
        String confirmPsw = mRegisterActivity.getConfirmPsw();
        String identifyCode = mRegisterActivity.getIdentifyCode();

        if (!password.equals(confirmPsw)) {
            mRegisterActivity.showToast("密码不匹配");
            return;
        }

        registerNetwork(username, password, identifyCode);
    }


    /**
     * 联网进行注册
     */
    private void registerNetwork(String username, String password, String identifyCode) {
        RequestBody requestBody = new FormBody.Builder()
                .add(AppConstants.USERNAME, username)
                .add(AppConstants.PASSWORD, password)
                .add(AppConstants.RAND, identifyCode)
                .build();
        Request request = new Request.Builder().url(AppConstants.URL_REGISTER)
                .post(requestBody).build();


        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).postData(request, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                if (result.equals(AppConstants.SUCCESS)) {
                    mRegisterActivity.onSuccess(AppConstants.URL_REGISTER, result);
                } else {
                    mRegisterActivity.onFail(result);
                }
            }

            @Override
            public void onResultFail(String result) {
                mRegisterActivity.onFail(result);
            }
        }, (Activity) mRegisterActivity);
    }

    /**
     * 获取验证码
     */
    public void getIdentifyCodeNetwork() {
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).doGetImage(AppConstants.URL_VALIDATE, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                mRegisterActivity.onSuccess(AppConstants.URL_VALIDATE, result);
            }

            @Override
            public void onResultFail(String result) {
                mRegisterActivity.onFail(result);
            }
        }, (Activity) mRegisterActivity);
    }

    /**
     * 读取图片
     */
    public Bitmap loadBitmapFromFile(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }
}
