package com.rdc.takebus.model.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.takebus.R;

/**
 * Created by ZZH on 2016/5/19.
 */
public class RouteTagHolder {
    public TextView tvStation;
    public TextView tvStart;
    public TextView tvEnd;
    public ImageView imgDivider;
    private boolean isSelected;


    public RouteTagHolder(View rootView) {
        tvStation = (TextView) rootView.findViewById(R.id.tv_station);
        tvStart = (TextView) rootView.findViewById(R.id.tv_start);
        tvEnd = (TextView) rootView.findViewById(R.id.tv_end);
        imgDivider = (ImageView) rootView.findViewById(R.id.img_divider);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
