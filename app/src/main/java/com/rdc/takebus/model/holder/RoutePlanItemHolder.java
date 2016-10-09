package com.rdc.takebus.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rdc.takebus.R;

/**
 * 方案展示条目实体类
 * Created by 53261 on 2016-5-24.
 */
public class RoutePlanItemHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public TextView tvDetail;

    public RoutePlanItemHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_route_title);
        tvDetail = (TextView) itemView.findViewById(R.id.tv_route_detail);
    }
}
