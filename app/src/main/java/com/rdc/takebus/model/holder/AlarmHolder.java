package com.rdc.takebus.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.takebus.R;

/**
 * Created by ZZH on 2016/5/24.
 */
public class AlarmHolder extends RecyclerView.ViewHolder {
    private TextView tvName;
    private ImageView imgCheck;
    private RelativeLayout rlAlarm;
    private int point;

    public AlarmHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        imgCheck = (ImageView) itemView.findViewById(R.id.img_check);
        rlAlarm = (RelativeLayout) itemView.findViewById(R.id.rl_alarm);
    }

    public RelativeLayout getRlAlarm() {
        return rlAlarm;
    }

    public void setRlAlarm(RelativeLayout rlAlarm) {
        this.rlAlarm = rlAlarm;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public ImageView getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(ImageView imgCheck) {
        this.imgCheck = imgCheck;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
