package com.rdc.takebus.model.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.rdc.takebus.presenter.tbinterface.ResultListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;


public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil";
    private static volatile OkHttpUtil mOkHttpUtil = null;
    // 构造OkHttpClient，注意Cookie的保存
    private Call call;
    private static CookieJar mCookieJar;
    private static int mRequestType = AppConstants.REQUEST_TYPE_RDC;


    public OkHttpUtil() {
        initOkHttpCookieJar();
    }

    private void initOkHttpCookieJar() {
        mCookieJar = new CookieJar() {

            private Map<Integer, List<Cookie>> mCookieMap = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                mCookieMap.put(mRequestType, cookies);
                Log.e(TAG, "saveFromResponse : " + cookies + "  【请求类型：" + mRequestType + "】");
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = mCookieMap.get(mRequestType);
                Log.e(TAG, "loadForRequest : " + cookies + "  【请求类型：" + mRequestType + "】");
                return cookies == null ? new ArrayList<Cookie>() : cookies;
            }
        };
    }


    //双重校验锁
    public static OkHttpUtil getInstance(int requestType) {
        mRequestType = requestType;
        if (mOkHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (mOkHttpUtil == null) {
                    mOkHttpUtil = new OkHttpUtil();
                }
            }
        }
        return mOkHttpUtil;
    }


    //普通get数据请求
    public void getData(String url, ResultListener mResultListener, Activity activity) {
        ResultListener resultListener = mResultListener;
        Request request = new Request.Builder().url(url).build();
        //执行任务
        execute(request, resultListener, new OkHandler(activity, resultListener));
    }

    //普通发送参数的数据请求
    public void postData(Request request, ResultListener mResultListener, Activity activity) {
        ResultListener resultListener = mResultListener;
        //执行任务
        execute(request, resultListener, new OkHandler(activity, resultListener));
    }

    /**
     * 获得含有Cookie的OkHttpClient
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .cookieJar(mCookieJar).build();
    }

    /**
     * 执行联网任务
     *
     * @param request
     * @param resultListener
     * @param okHandler
     */

    private void execute(Request request, final ResultListener resultListener, final OkHandler okHandler) {
        OkHttpClient mOkHttpClient = getOkHttpClient();
        call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, "onResponse Call > " + result);
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                msg.setData(bundle);
                msg.what = 0x123;
                okHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                String result = "服务器异常";
                Log.e(TAG, "onFailure Call > " + call.toString());
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                msg.setData(bundle);
                msg.what = 0x124;
                okHandler.sendMessage(msg);
            }
        });
    }


    // 返回当前操作网络的call，进行取消访问网络请求
    public Call getCurrentCall() {
        return call;
    }


    static class OkHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        private ResultListener resultListener;

        public OkHandler(Activity activity, ResultListener resultListener) {
            this.resultListener = resultListener;
            mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                Bundle bundle = msg.getData();
                String result = bundle.getString("result");

                switch (msg.what) {
                    //执行成功返回
                    case 0x123: {
                        resultListener.onResultSuccess(result);
                    }
                    break;
                    //执行失败返回
                    case 0x124: {
                        resultListener.onResultFail(result);
                    }
                    break;
                }
            }
        }

    }


    /**
     * 联网下载图片
     */
    public void doGetImage(String url, ResultListener resultListener, Activity activity) {
        Request request = new Request.Builder().url(url).build();
        executeImage(request, resultListener, activity);
    }

    private void executeImage(Request request, ResultListener resultListener, final Activity activity) {
        final OkHandler okHandler = new OkHandler(activity, resultListener);
        OkHttpClient okHttpClient = getOkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                String dirPath = new FileUtil().getCacheFileObject(activity);
                FileOutputStream fos = new FileOutputStream(new FileUtil().getCacheFileObject(activity));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", dirPath);
                msg.setData(bundle);
                msg.what = 0x123;
                okHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                String result = "服务器异常";
                Log.e(TAG, "onFailure Call > " + call.toString());
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                msg.setData(bundle);
                msg.what = 0x124;
                okHandler.sendMessage(msg);
            }
        });
    }


}



