package com.rdc.takebus.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.rdc.takebus.R;

/**
 * 路线站点中的holder
 * Created by ZZH on 2016/5/19.
 */
public class StationHolder extends RecyclerView.ViewHolder {
    //标记为
    private int point;
    private TextView tvStation;
    private TextView tvNearest;
    private ImageView imgStation;
    private RelativeLayout rlStation;
    private ImageView imgUpBus;
    private RelativeLayout rlDownBus;

    public StationHolder(View itemView) {
        super(itemView);
        tvStation = (TextView) itemView.findViewById(R.id.tv_station);
        imgStation = (ImageView) itemView.findViewById(R.id.img_station);
        rlStation = (RelativeLayout) itemView.findViewById(R.id.rl_station);
        tvNearest = (TextView) itemView.findViewById(R.id.tv_nearest);
        imgUpBus = (ImageView) itemView.findViewById(R.id.img_bus_top);
        rlDownBus = (RelativeLayout) itemView.findViewById(R.id.rl_bus_bottom);
    }

    public ImageView getImgUpBus() {
        return imgUpBus;
    }

    public void setImgUpBus(ImageView imgUpBus) {
        this.imgUpBus = imgUpBus;
    }

    public RelativeLayout getRlDownBus() {
        return rlDownBus;
    }

    public void setRlDownBus(RelativeLayout rlDownBus) {
        this.rlDownBus = rlDownBus;
    }

    public TextView getTvNearest() {
        return tvNearest;
    }

    public void setTvNearest(TextView tvNearest) {
        this.tvNearest = tvNearest;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public TextView getTvStation() {
        return tvStation;
    }

    public void setTvStation(TextView tvStation) {
        this.tvStation = tvStation;
    }

    public RelativeLayout getRlStation() {
        return rlStation;
    }

    public void setRlStation(RelativeLayout rlStation) {
        this.rlStation = rlStation;
    }

    public ImageView getImgStation() {
        return imgStation;
    }

    public void setImgStation(ImageView imgStation) {
        this.imgStation = imgStation;
    }

}
