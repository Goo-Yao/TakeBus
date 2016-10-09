package com.rdc.takebus.model.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.rdc.takebus.view.CustomView.CustomToast;
import com.rdc.takebus.view.activity.MainActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Created by 梦涵 on 2016/5/22.
 */
public class LoginUtil {
    private Activity activity;
    private String name;
    private Bitmap photo;
    public  LoginUtil(Activity activity){
        this.activity = activity;
    }

    public BaseUiListener getListener(){
        return new BaseUiListener();
    }

    public String getName(){
        return name;
    }

    public Bitmap getPhoto(){
        return photo;
    }

    //实现登录的回调接口
     private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            Log.e("LoginObject",""+o);
            if (o == null) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
                Log.e("LoginUtil", "返回为空登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) o;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
                Log.e("LoginUtil", "返回为空登录失败");
                return;
            }
            doComplete((JSONObject) o);
        }

        @Override
        public void onError(UiError uiError) {
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
            Log.e("LoginPresenter,","uiError--"+"code--"+uiError.errorCode+",msg--"+uiError.errorMessage
                    +",detail--"+uiError.errorDetail);
        }

        @Override
        public void onCancel() {
        }


        private void doComplete(JSONObject jsonObject) {
            //登录成功时调用
            try {
                if (jsonObject.getInt("ret") == 0){
                    getNameAndPhoto();
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                }else{
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    //获取用户的昵称和头像
    public void getNameAndPhoto(){
        IUiListener listener = new IUiListener() {
            @Override
            public void onComplete(final Object o) {
                Log.e("Object","--"+ o);
                Message msg = new Message();
                if (o == null){
                    msg.what = 3;
                    handler.sendMessage(msg);
                    return;
                }
                try {
                    if (((JSONObject)o).getInt("ret") != 0){
                        msg.what = 3;
                    }else{
                        msg.obj = o;
                        msg.what = 0;
                    }
                    handler.sendMessage(msg);

                    //下载头像
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                photo = PhotoUtil.getBitmap(((JSONObject) o).getString("figureurl_qq_2"));
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                            Message msg = new Message();
                            msg.obj = photo;
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }.start();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);
            }

            @Override
            public void onCancel() {

            }
        };
        QQToken qqToken = AppConstants.mTencent.getQQToken();
        UserInfo info = new UserInfo(activity, qqToken);
        info.getUserInfo(listener);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                JSONObject object = (JSONObject) msg.obj;
                if (object.has("nickname")){
                    try {
                        name = object.getString("nickname");
                        Log.e("nickname","--"+name);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            }else if (msg.what == 1){
                photo = (Bitmap)msg.obj;
                Log.e("photo","--"+photo);
            }else if(msg.what == 2){
                CustomToast.showToast(activity, "登录失败", Toast.LENGTH_SHORT);
            }else if(msg.what == 3){
                CustomToast.showToast(activity, "获取用户信息失败", Toast.LENGTH_LONG);
            }
        }
    };

}
