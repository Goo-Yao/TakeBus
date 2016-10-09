package com.rdc.takebus.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.rdc.takebus.R;

/**
 * Created by ZZH on 2016/5/21.
 */
public class StationDetailHolder extends RecyclerView.ViewHolder {
    private int point;
    private TextView tvRoute;
    private TextView tvRouteStation;
    private RelativeLayout rlRouteDetail;

    public StationDetailHolder(View itemView) {
        super(itemView);
        tvRoute = (TextView) itemView.findViewById(R.id.tv_route);
        tvRouteStation = (TextView) itemView.findViewById(R.id.tv_route_station);
        rlRouteDetail = (RelativeLayout) itemView.findViewById(R.id.rl_route_detail);
    }

    public RelativeLayout getRlRouteDetail() {
        return rlRouteDetail;
    }

    public void setRlRouteDetail(RelativeLayout rlRouteDetail) {
        this.rlRouteDetail = rlRouteDetail;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public TextView getTvRoute() {
        return tvRoute;
    }

    public void setTvRoute(TextView tvRoute) {
        this.tvRoute = tvRoute;
    }

    public TextView getTvRouteStation() {
        return tvRouteStation;
    }

    public void setTvRouteStation(TextView tvRouteStation) {
        this.tvRouteStation = tvRouteStation;
    }
}
