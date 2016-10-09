package com.rdc.takebus.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.takebus.R;

/**
 * 主界面站点的holder
 * Created by ZZH on 2016/5/16.
 */
public class RouteHolder extends RecyclerView.ViewHolder {
    private int point;
    private TextView textView;
    private ImageView imageView;
    private ImageView imgAlarm;
    private RelativeLayout rlStation;
    private ImageView imgBusLeft;
    private ImageView imgBusRight;


    public RouteHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.tv_station);
        imageView = (ImageView) itemView.findViewById(R.id.img_station);
        imgAlarm = (ImageView) itemView.findViewById(R.id.img_alarm);
        rlStation = (RelativeLayout) itemView.findViewById(R.id.rl_station);
        imgBusLeft = (ImageView) itemView.findViewById(R.id.img_bus_left);
        imgBusRight = (ImageView) itemView.findViewById(R.id.img_bus_right);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImgBusLeft() {
        return imgBusLeft;
    }

    public void setImgBusLeft(ImageView imgBusLeft) {
        this.imgBusLeft = imgBusLeft;
    }

    public ImageView getImgBusRight() {
        return imgBusRight;
    }

    public void setImgBusRight(ImageView imgBusRight) {
        this.imgBusRight = imgBusRight;
    }

    public RelativeLayout getRlStation() {
        return rlStation;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setRlStation(RelativeLayout rlStation) {
        this.rlStation = rlStation;
    }

    public ImageView getImgAlarm() {
        return imgAlarm;
    }

    public void setImgAlarm(ImageView imgAlarm) {
        this.imgAlarm = imgAlarm;
    }


}
