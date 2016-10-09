package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.tbinterface.NearestStationInterface;
import com.rdc.takebus.view.CustomView.CustomToast;
import com.rdc.takebus.view.activity.GuideActivity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


/**
 * Created by 梦涵 on 2016/5/15.
 */
public class NearestStationPresenter extends BaseActivityPresenter<NearestStationInterface>
        implements OnGetPoiSearchResultListener {
    private NearestStationInterface stationInterface;
    private Context context;
    private PoiSearch poiSearch; // 用于poi检索
    private List<String> stations = new ArrayList<>();
    private List<String> distance = new ArrayList<>();
    private List<String> detail = new ArrayList<>();
    private List<LatLng> location = new ArrayList<>();//存储公交站的经纬度
    private LatLng pointme = new LatLng(AppConstants.latitude, AppConstants.longiTude);
    private AlertDialog dialog;//加载对话框
    private String mSDCardPath = null;//SD卡的路径
    private Activity mActivity;
    private int item;//点击的item


    public NearestStationPresenter(NearestStationInterface stationInterface, Context context) {
        this.stationInterface = stationInterface;
        this.context = context;
    }

    public void init() {
        dialog = new SpotsDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        //初始化搜索并对搜索结果进行监听
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        //搜索附近1000米的公交站
        poiSearch.searchNearby(new PoiNearbySearchOption()
                .keyword("公交站")
                .location(new LatLng(AppConstants.latitude, AppConstants.longiTude))
                .radius(1000)
                .pageCapacity(50)//设置为最大值，让检索到的公交站都能显示出来
                .sortType(PoiSortType.distance_from_near_to_far));
    }

    public void startNavi(int arg0, Activity activity) {
        dialog.show();
        item = arg0;
        mActivity = activity;
        if (initDirs()) {
            initNavi(mActivity);
        }
    }

    //停止检索
    public void destroyPoiSearch() {
        poiSearch.destroy();
    }

    //获取检索到的公交站信息
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null
                || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            CustomToast.showToast(context, "未找到结果", Toast.LENGTH_LONG);
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            DecimalFormat df = new DecimalFormat("#0.00");
            for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                PoiInfo poiInfo = poiResult.getAllPoi().get(i);
                if (stations.contains(poiInfo.name)) { //有相同的公交站，距离相差很小,只存第一个
                    continue;
                }
                stations.add(poiInfo.name);
                distance.add(df.format(DistanceUtil.getDistance(pointme, poiInfo.location)));//计算两点间的距离,保留小数点后两位
                location.add(poiInfo.location);
                detail.add(poiInfo.address);
                Log.e("公交站", "--" + poiInfo.name);
                Log.e("地址---", "" + poiInfo.address);
            }
            dialog.dismiss();
            stationInterface.loadData(stations, distance, detail);
            CustomToast.showToast(context, "加载完毕", Toast.LENGTH_SHORT);
            return;
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            CustomToast.showToast(context, "抱歉，未找到结果", Toast.LENGTH_SHORT);
        }
    }

    //导航相关

    /**
     * 初始化SD卡，在SD卡路径下新建文件夹：App目录名，文件中包含了很多东西，比如log、cache等等
     *
     * @return
     */
    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, AppConstants.APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    //    showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    //    showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {
        @Override
        public void playEnd() {
        }

        @Override
        public void playStart() {
        }
    };

    /**
     * 使用SDK前，先进行百度服务授权和引擎初始化
     */
    private void initNavi(Activity activity) {
        Log.e("授权与初始化引擎", "--");
        BaiduNaviManager.getInstance().init(activity, mSDCardPath, AppConstants.APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    Log.e("key校验成功", "--NearestStationPresenter");
                } else {
                    Log.e("key校验失败", "" + "key校验失败, " + msg);
                }
            }

            public void initSuccess() {
                Log.e("百度导航引擎初始化成功", "--NearestStationPresenter");
                initSetting();
                if (BaiduNaviManager.isNaviInited()) {
                    Log.e("开始规划路线", "--");
                    routeplanToNavi(item, mActivity);
                }
            }

            public void initStart() {
                Log.e("百度导航引擎初始化开始", "--NearestStationPresenter");
            }

            public void initFailed() {
                Log.e("百度导航引擎初始化失败", "--NearestStationPresenter");
            }
        }, null, ttsHandler, null);
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }


    /**
     * 算路设置起、终点，算路偏好，是否模拟导航等参数，然后在回调函数中设置跳转至诱导。
     * CoordinateType 坐标类型
     * BNRoutePlanNode 路径规划节点对象
     */
    private void routeplanToNavi(int i, Activity activity) {
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        sNode = new BNRoutePlanNode(AppConstants.longiTude, AppConstants.latitude, "我的位置", null, BNRoutePlanNode.CoordinateType.BD09LL);
        eNode = new BNRoutePlanNode(location.get(i).longitude, location.get(i).latitude, stations.get(i), null, BNRoutePlanNode.CoordinateType.BD09LL);
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            Log.e("eNode", "--" + eNode);
            BaiduNaviManager.getInstance().launchNavigator(activity, list, 1, true, new RoutePlanListener(sNode));
        }
    }

    /**
     * 导航回调监听器
     */
    public class RoutePlanListener implements BaiduNaviManager.RoutePlanListener {
        private BNRoutePlanNode mBNRoutePlanNode = null;

        public RoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /*
             * 设置途径点以及resetEndNode会回调该接口
			 */
            for (Activity ac : AppConstants.activityList) {

                if (ac.getClass().getName().endsWith("GuideActivity")) {
                    return;
                }
            }
            /**
             * 导航activity
             */
            if (mActivity.isFinishing()) { //点击导航之后又切换其他activity则不进行导航。
                return;
            }
            Intent intent = new Intent(mActivity, GuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            bundle.putDouble("longitude", location.get(item).longitude);
            bundle.putDouble("latitude", location.get(item).latitude);
            bundle.putString("name", stations.get(item));
            intent.putExtras(bundle);
            mActivity.startActivity(intent);
            dialog.dismiss();
            mActivity.finish();
        }

        @Override
        public void onRoutePlanFailed() {
            CustomToast.showToast(context, "算路失败", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 导航设置管理器
     */
    private void initSetting() {
        /**
         * 日夜模式 1：自动模式 2：白天模式 3：夜间模式
         */
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_AUTO);
        /**
         * 设置全程路况显示
         */
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        /**
         * 设置语音播报模式
         */
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        /**
         * 设置省电模式
         */
        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        /**
         * 设置实时路况条
         */
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {
        @Override
        public void stopTTS() {
            Log.e("test_TTS", "stopTTS");
        }

        @Override
        public void resumeTTS() {
            Log.e("test_TTS", "resumeTTS");
        }

        @Override
        public void releaseTTSPlayer() {
            Log.e("test_TTS", "releaseTTSPlayer");
        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
            Log.e("test_TTS", "playTTSText" + "_" + speech + "_" + bPreempt);

            return 1;
        }

        @Override
        public void phoneHangUp() {
            //电话挂断
            Log.e("test_TTS", "phoneHangUp");
        }

        @Override
        public void phoneCalling() {
            //来电
            Log.e("test_TTS", "phoneCalling");
        }

        @Override
        public void pauseTTS() {
            Log.e("test_TTS", "pauseTTS");
        }

        @Override
        public void initTTSPlayer() {
            Log.e("test_TTS", "initTTSPlayer");
        }

        @Override
        public int getTTSState() {
            Log.e("test_TTS", "getTTSState");
            return 1;
        }
    };

}
