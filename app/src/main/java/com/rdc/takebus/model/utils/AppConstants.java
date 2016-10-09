package com.rdc.takebus.model.utils;

import android.app.Activity;

import com.baidu.mapapi.map.MapView;
import com.rdc.takebus.entity.Bus;
import com.rdc.takebus.entity.RecyclerStation;
import com.rdc.takebus.view.activity.SplashActivity;
import com.tencent.tauth.Tencent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * App常量类
 */
public class AppConstants {
    public static String GET_ROUTE_URL = "http://api.jisuapi.com/transit/line";
    public static String GET_STATION_URL = "http://api.jisuapi.com/transit/station";
    public static String GET_BUS_LOCATION_URL = "http://apis.baidu.com/xiaota/bus_lines/buses_lines";

    public static SplashActivity splashActivity;
    public static List<Activity> activityList = new LinkedList<Activity>();

    public static final long serialVersionUID = -7101106684705227950L;

    public static List<Bus> Buses = new ArrayList<Bus>();
    public static MapView mMapView = null;

    public static double longiTude;//经度
    public static double latitude;//纬度
    public static float direction;//方向
    public static float accuracy;//精度
    public static String city = null;//当前所在城市


    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    public static final String SHOW_CUSTOM_ITEM = "showCustomItem";
    public static final String RESET_END_NODE = "resetEndNode";
    public static final String VOID_MODE = "voidMode";

    public static final int MSG_SHOW = 1;
    public static final int MSG_HIDE = 2;
    public static final int MSG_RESET_NODE = 3;
    public static final int MSG_DIALOG = 4;

    public static final String APP_FOLDER_NAME = "RdcTakeBus";//App在SD卡中的目录名

    public static final int OPEN_OVERPENDINGTRANSITION = 10;
    public static final int OUT_OVERPENDINGTRANSITION = 20;

    public static int PLAN_DETAIL_BUS_TAG = 100;
    public static int PLAN_DETAIL_STEP_TAG = 200;

    public static final String mAppId = "1105343187";  //调用QQ登录相关

    public static final int Local = 0;//使用账号登录
    public static final int QQ = 1;//QQ登录
    public static final int WeChat = 2;//微信登录
    public static final int ZhiFuBao = 3;//支付宝登录
    public static int LoginState;//标记登录的方式
    public static Tencent mTencent;

    public static final int SELECT_STARTSTATION_TAG = 10;
    public static final int SELECT_TERMINALSTATION_TAG = 20;


    //实时公交API KEY
    public static String BUS_API_KEY = "41ef8ce2c765075a425f33f4df25eb0f";
    //路线检索API KEY
    public static String APIKEY = "abaac7038e219285";


    public static final String FROM_PAYMENT_DATA = "from payment data";

    /**
     * 已付款账单信息
     */
    public static String PAID_BILL_DATA = "paid bill data";
    /**
     * 请求类型，其他网络数据
     */
    public static final int REQUEST_TYPE_OHTER = 0;
    /**
     * 请求类型，研发后台网络数据
     */
    public static final int REQUEST_TYPE_RDC = 1;


    /*  登陆模块  */
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";
    public static String userName = "无";
    public static final String RAND = "rand";  // 验证码

    /*  支付模块  */
    public static final String PAYPASSWORD = "payPassword";
    public static final String COST = "cost";
    public static final String STARTPOINT = "startPoint";
    public static final String ENDPOINT = "endPoint";
    public static final String ROUTE = "route";


    /*  订单模块  */
    public static final String CATEGORY = "category";
    public static final String UNFINISHED = "unfinished";
    public static final String FINISHED = "finished";
    public static final String SUCCESS = "success";
    public static final String ID = "orderId";

    public static final String RESULT = "result";
    public static final String ORDER = "order";

    /*  URL模块  */
    private static final String IP = "115.29.147.119";
    // 登陆
    public static final String URL_LOGIN = "http://" + IP + "/Bus/login";
    // 注册
    public static final String URL_REGISTER = "http://" + IP + "/Bus/register";
    // 验证码获取
    public static final String URL_VALIDATE = "http://" + IP + "/Bus/validate";
    // 生成订单
    public static final String URL_PAYMENT = "http://" + IP + "/Bus/generateQrCode";
    // 查询订单
    public static final String URL_BILL_INFO = "http://" + IP + "/Bus/getOrders";
    // 查看订单状态
    public static final String URL_STATE_CONFIRM = "http://" + IP + "/Bus/confirmFinshed";

    // 存储起点站和终点站
    public static String startStation = "";
    public static String terminalStation = "";


    public static String FRAGMENT_TYPE = "fragment type";
    public static int TYPE_HISTROY = 10;
    public static int TYPE_NOW = 11;
    //获取个人信息
    public static final String URL_PERSON_INFO = " http://" + IP + "/Bus/getInformation";
    //更新个人信息
    public static final String URL_UPDATE_PERSON_INFO = " http://" + IP + "/Bus/updateInformation";
    //更新头像
    public static final String URL_UPDATE_HEAD_IMAGE = " http://" + IP + "/Bus/updateIcon";
    //是否开启闹钟
    public static int cityId;
    public static boolean isAlarming = false;
    public static boolean isInfoChanging = false;

    public static String getRouteUrl(int cityId, String routeName) {
        try {
            return GET_ROUTE_URL + "?cityid=" + cityId + "&transitno=" + URLEncoder.encode(routeName, "UTF-8") + "&appkey=" + APIKEY;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStationUrl(int cityId, String stationName) {
        try {
            return GET_STATION_URL + "?cityid=" + cityId + "&station=" + URLEncoder.encode(stationName, "UTF-8") + "&appkey=" + APIKEY;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
