package com.rdc.takebus.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rdc.takebus.R;

/**
 * 路线详情条目
 * Created by 53261 on 2016-5-24.
 */
public class PlanBusItemHolder extends RecyclerView.ViewHolder {
    public TextView tvDetail;

    public PlanBusItemHolder(View itemView) {
        super(itemView);
        tvDetail = (TextView) itemView.findViewById(R.id.tv_plan_detail_title);
    }
}
