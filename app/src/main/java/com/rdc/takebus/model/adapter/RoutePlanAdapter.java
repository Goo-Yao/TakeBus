package com.rdc.takebus.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.takebus.R;
import com.rdc.takebus.entity.RoutePlan;
import com.rdc.takebus.model.holder.RoutePlanItemHolder;
import com.rdc.takebus.model.holder.SuggestStationHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 乘车方案
 * Created by 53261 on 2016-5-24.
 */
public class RoutePlanAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<RoutePlan> data = new ArrayList<RoutePlan>();
    private Context mContext;

    public RoutePlanAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<RoutePlan> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_route_can_select_item, parent, false);
        return new RoutePlanItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RoutePlanItemHolder routePlanItemHolder = (RoutePlanItemHolder) holder;
        RoutePlan info = data.get(position);
        routePlanItemHolder.tvTitle.setText(info.getBusTitle());
        routePlanItemHolder.tvDetail.setText(Html.fromHtml(info.getDetail()));
        routePlanItemHolder.itemView.setTag(info);
        routePlanItemHolder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (RoutePlan) v.getTag());
        }

    }

    public interface OnPlanItemClickListener {
        void onItemClick(View view, RoutePlan plan);
    }

    private OnPlanItemClickListener mOnItemClickListener = null;

    public void setOnPlanItemClickListener(OnPlanItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
