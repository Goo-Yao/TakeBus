package com.rdc.takebus.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.rdc.takebus.R;
import com.rdc.takebus.entity.RecyclerStation;
import com.rdc.takebus.entity.StationDetail;
import com.rdc.takebus.model.holder.BottomHolder;
import com.rdc.takebus.model.holder.RouteHolder;
import com.rdc.takebus.model.holder.StationDetailHolder;
import com.rdc.takebus.model.holder.StationHolder;
import com.rdc.takebus.presenter.tbinterface.OnRecyclerItemClickListener;
import com.rdc.takebus.view.activity.RouteStationActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ZZH on 2016/5/16.
 */
public class RouteAdapter extends RecyclerView.Adapter {
    private List<RecyclerStation> listStations;
    private List<StationDetail> listDetailStations;
    private int position;
    private Context context;
    //向上标志
    private static int UP_TAG = 0;
    //向下标志
    private static int DOWN_TAG = 1;
    private int width;
    //分5份
    private static int STATION_NUM = 5;

    public static int ROUTE_TAG = 0;
    public static int ROUTE_STATION_TAG = 1;
    public static int STATION_DETAIL_TAG = 2;

    public static int NORMAL_TAG = 0;
    public static int END_TAG = 1;
    private String busLineName;
    private String price;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void setLineAndPrice(String busLineName, String price) {
        this.busLineName = busLineName;
        this.price = price;
    }

    public List<StationDetail> getListDetailStations() {
        return listDetailStations;
    }

    public void setListDetailStations(List<StationDetail> listDetailStations) {
        this.listDetailStations = listDetailStations;
    }

    //设置数据
    public List<RecyclerStation> getListStations() {
        return listStations;
    }

    public void setListStations(List<RecyclerStation> listStations) {
        this.listStations = listStations;
    }

    public RouteAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
        if (position == ROUTE_TAG) {
            DisplayMetrics metric = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
            width = metric.widthPixels / STATION_NUM;//屏幕宽度（像素）
            Log.e("width", width + ">>");
        }
    }

    @Override
    public int getItemViewType(int point) {
        if (position == ROUTE_TAG) {
            if (point % 2 == 0)
                return UP_TAG;
            else
                return DOWN_TAG;
        } else if (position == ROUTE_STATION_TAG) {
            if (point <= listStations.size() - 1)
                return NORMAL_TAG;
            else
                return END_TAG;
        } else return point;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.e("position", position + ">>>>a  ");
        if (position == ROUTE_TAG) {
            if (viewType == UP_TAG)
                view = LayoutInflater.from(context).inflate(R.layout.recycler_item_station_up, parent, false);
            else
                view = LayoutInflater.from(context).inflate(R.layout.recycler_item_station_down, parent, false);
            return new RouteHolder(view);
        } else if (position == ROUTE_STATION_TAG) {
            if (viewType == NORMAL_TAG) {
                view = LayoutInflater.from(context).inflate(R.layout.recycler_item_station, parent, false);
                return new StationHolder(view);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.recycler_item_bottom, parent, false);
                return new BottomHolder(view);
            }
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_item_station_detail, parent, false);
            return new StationDetailHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int point) {
        if (position == ROUTE_TAG)
            onBindViewRouteStationHolder((RouteHolder) holder, point);
        else if (position == ROUTE_STATION_TAG) {
            if (getItemViewType(point) == NORMAL_TAG)
                onBindViewStationHolder((StationHolder) holder, point);
            else
                onBindViewBottomHolder((BottomHolder) holder, point);
        } else if (position == STATION_DETAIL_TAG) {
            onBindViewStationDetailHolder((StationDetailHolder) holder, point);
        }
    }

    private void onBindViewStationDetailHolder(StationDetailHolder holder, final int point) {
        holder.setPoint(point);
        holder.getTvRoute().setText(listDetailStations.get(point).getTransitno());
        holder.getTvRouteStation().setText(listDetailStations.get(point).getStartstation() + " ⇌ " + listDetailStations.get(point).getEndstation());
        holder.getRlRouteDetail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null)
                    onRecyclerItemClickListener.onItemClick(point);
            }
        });
    }

    private void onBindViewBottomHolder(final BottomHolder holder, int point) {
        final Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.img_scale);
        holder.getTvRoute().setText(busLineName);
        holder.getTvPrice().setText("¥" + price);
        holder.getRlAlipay().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getPayTag() != BottomHolder.ALIPAY_TAG) {
                    holder.getImgAli().setImageResource(R.drawable.circle_sure);
                    holder.getImgAli().startAnimation(animation);
                    holder.getImgWechat().setImageResource(R.drawable.circle_null);
                    holder.setPayTag(BottomHolder.ALIPAY_TAG);
                }
            }
        });
        holder.getRlWechat().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getPayTag() != BottomHolder.WECHAT_TAG) {
                    holder.getImgAli().setImageResource(R.drawable.circle_null);
                    holder.getImgWechat().setImageResource(R.drawable.circle_sure);
                    holder.getImgWechat().startAnimation(animation);
                    holder.setPayTag(BottomHolder.WECHAT_TAG);
                }
            }
        });
        if (RouteStationActivity.tag == 1) {
            holder.getCardView().setClickable(false);
            holder.getCardView().setCardBackgroundColor(R.color.dividerColor);
            holder.getTvCardView().setText("已买车票");
        } else
            holder.getCardView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(price);
                }
            });
    }

    private void onBindViewStationHolder(StationHolder holder, final int position) {
        holder.setPoint(position);
        holder.getTvStation().setText(listStations.get(position).getBusStation().getStation());
        //显示最近站点
        if (listStations.get(position).isShowUpBus())
            holder.getImgUpBus().setVisibility(View.VISIBLE);
        else
            holder.getImgUpBus().setVisibility(View.INVISIBLE);
        if (listStations.get(position).isShowDownBus())
            holder.getRlDownBus().setVisibility(View.VISIBLE);
        else
            holder.getRlDownBus().setVisibility(View.INVISIBLE);

        if (listStations.get(position).isNearest()) {
            holder.getTvNearest().setVisibility(View.VISIBLE);
        } else
            holder.getTvNearest().setVisibility(View.GONE);
        holder.getRlStation().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null)
                    onRecyclerItemClickListener.onItemClick(position);
            }
        });

    }

    private void onBindViewRouteStationHolder(RouteHolder holder, int position) {
        //按屏幕设置宽度,父控件是RecyclerView
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.getRlStation().getLayoutParams();
        params.width = width;
        holder.getRlStation().setLayoutParams(params);
        //设置颜色
        Resources resource = context.getResources();
        int colorId;
        int resId;
        //起点站
        if (listStations.get(position).isFirstStation()) {
            colorId = resource.getColor(R.color.startStationGreenColor);
            resId = R.drawable.circle_start_3_2px;
            //终点站
        } else if (listStations.get(position).isEndStation()) {
            colorId = resource.getColor(R.color.terminalStationRedColor);
            resId = R.drawable.circle_end_3_2px;
        } else {
            //普通站
            colorId = resource.getColor(R.color.textSecondaryColor);
            resId = R.drawable.circle_default_3_2px;
        }
        holder.getTextView().setText(listStations.get(position).getBusStation().getStation());
        holder.getTextView().setTextColor(colorId);
        holder.getImageView().setImageResource(resId);

        //显示实时位置
        if (listStations.get(position).isShowUpBus())
            holder.getImgBusLeft().setVisibility(View.VISIBLE);
        else
            holder.getImgBusLeft().setVisibility(View.INVISIBLE);

        if (listStations.get(position).isShowDownBus())
            holder.getImgBusRight().setVisibility(View.VISIBLE);
        else
            holder.getImgBusRight().setVisibility(View.INVISIBLE);

        //记录位置信息
        holder.setPoint(position);
        //如果设置了闹钟
        if (listStations.get(position).isAlarming()) {
            holder.getImgAlarm().setVisibility(View.VISIBLE);
            holder.getImageView().setVisibility(View.INVISIBLE);
        } else {
            holder.getImgAlarm().setVisibility(View.GONE);
            holder.getImageView().setVisibility(View.VISIBLE);
        }
    }

    //增加一个底部操作栏目
    @Override
    public int getItemCount() {
        if (position == STATION_DETAIL_TAG)
            return listDetailStations.size();
        else if (position == ROUTE_STATION_TAG)
            return listStations.size() + 1;
        else
            return listStations.size();
    }
}
