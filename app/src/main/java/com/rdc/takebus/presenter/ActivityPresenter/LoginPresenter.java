package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.LoginUtil;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbinterface.LoginViewInterface;
import com.rdc.takebus.presenter.tbinterface.ResultListener;
import com.rdc.takebus.view.activity.LoginViewActivity;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 登录中介
 * 持有：
 * 登录界面操作接口
 * 登录逻辑操作接口
 * Created by 梦涵 on 2016/5/12.
 */
public class LoginPresenter extends BaseActivityPresenter<LoginViewInterface> {
    private static final String TAG = "LoginPresenter";
    //view接口
    private LoginViewInterface mLoginView;
    private boolean isOK = false; //标记是否成功登录
    private Activity activity;
    private LoginUtil loginUtil;
    public IUiListener loginListener;
    private SpotsDialog dialog;

    //构造时候获得view接口
    public LoginPresenter(Context context, LoginViewInterface viewInterface) {
        mLoginView = viewInterface;
        dialog = new SpotsDialog(context);
        this.activity = (LoginViewActivity) viewInterface;
    }

    public boolean login(int i) {

        switch (i) {
            case AppConstants.Local:
                AppConstants.LoginState = AppConstants.Local;
                String userName = mLoginView.getUserName();
                String userPsw = mLoginView.getUserPsw();
                //校验输入
                if (!isError(userName, userPsw)) {
                    checkLoginNetwork(userName, userPsw);
                } else {
                    mLoginView.showMsgByToast("输入不能为空");
                    isOK = false;
                }
                break;
            case AppConstants.QQ:
                AppConstants.LoginState = AppConstants.QQ;
                loginUtil = new LoginUtil(activity);
                loginListener = loginUtil.getListener();
                AppConstants.mTencent = Tencent.createInstance(AppConstants.mAppId, activity);
                //先校验登录状态
                if (!AppConstants.mTencent.isSessionValid()) {
                    AppConstants.mTencent.login(activity, "all", loginListener);
                }
                break;
        }
        return isOK;
    }


    /**
     * 联网核对账号密码
     *
     * @param userName 用户名
     * @param userPsw  密码
     */
    private void checkLoginNetwork(final String userName, String userPsw) {
        /********************测试代码**********************/
        if (userName.equals("rdc") && userPsw.equals("123456")) {
            mLoginView.showMsgByToast("管理员登陆");
            mLoginView.onSuccess(AppConstants.URL_LOGIN, null);
            return;
        }
        /*************************************************/
        dialog.show();
        RequestBody requestBody = new FormBody.Builder().add(AppConstants.USERNAME
                , userName).add(AppConstants.PASSWORD, userPsw).build();
        Request request = new Request.Builder().url(AppConstants.URL_LOGIN).post(requestBody).build();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).postData(request, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                dialog.dismiss();
                Log.e(TAG, "登陆返回结果：" + result);
                if (result.equals(AppConstants.SUCCESS)) {
                    AppConstants.userName = userName;
                    mLoginView.onSuccess(AppConstants.URL_LOGIN, result);
                } else {
                    mLoginView.onFail(result);
                }
            }

            @Override
            public void onResultFail(String result) {
                mLoginView.onFail(result);
                dialog.dismiss();
            }
        }, (Activity) mLoginView);

    }


    private boolean isError(String userName, String userPsw) {
        //判空
        if (userName != null && !userName.equals("") && userPsw != null && !userPsw.equals("")) {
            return false;
        } else {
            return true;
        }
    }

}
