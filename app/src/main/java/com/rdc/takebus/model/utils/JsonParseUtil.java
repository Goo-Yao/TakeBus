package com.rdc.takebus.model.utils;

import com.google.gson.Gson;
import com.rdc.takebus.entity.BusResult;
import com.rdc.takebus.entity.BusResultData;
import com.rdc.takebus.entity.Route;
import com.rdc.takebus.entity.RouteResult;
import com.rdc.takebus.entity.StationDetail;
import com.rdc.takebus.entity.StationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParseUtil {
    public static List<String> list;
    public static Gson gson = new Gson();

    public static List<String> parseJson(String response) {
        list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject object = jsonArray.optJSONObject(0);
            JSONArray array = object.getJSONArray("lines");
            int i = 0;
            while (!array.isNull(i)) {
                list.add(array.getString(i++));
            }
            return list;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static List<Route> parseRouteJson(String response) {
        RouteResult routeResult = gson.fromJson(response, RouteResult.class);
        if (routeResult.getMsg().equals("ok"))
            return routeResult.getResult();
        else
            return null;
    }

    public static List<StationDetail> parseStationJson(String response) {
        StationResult stationResult = gson.fromJson(response, StationResult.class);
        if (stationResult.getMsg().equals("ok"))
            return stationResult.getResult();
        else
            return null;
    }

    public static BusResultData parseBusLocationJson(String response) {
        BusResult busResult = gson.fromJson(response, BusResult.class);
        return busResult.getData();
    }
}
