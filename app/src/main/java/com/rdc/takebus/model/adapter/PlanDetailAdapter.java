package com.rdc.takebus.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.takebus.R;
import com.rdc.takebus.model.holder.PlanBusItemHolder;
import com.rdc.takebus.model.holder.PlanStepItemHolder;
import com.rdc.takebus.model.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 规划详情Adapter
 * Created by 53261 on 2016-5-25.
 */
public class PlanDetailAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<String> data = new ArrayList<String>();
    private Context mContext;
    private int tag;

    public PlanDetailAdapter(Context context, int tag) {
        this.mContext = context;
        this.tag = tag;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (tag == AppConstants.PLAN_DETAIL_BUS_TAG) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_plan_bus_item, parent, false);
            return new PlanBusItemHolder(view);

        } else if (tag == AppConstants.PLAN_DETAIL_STEP_TAG) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_plan_step_item, parent, false);
            return new PlanStepItemHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (tag == AppConstants.PLAN_DETAIL_BUS_TAG) {
            PlanBusItemHolder routePlanItemHolder = (PlanBusItemHolder) holder;
            String str = data.get(position);
            routePlanItemHolder.tvDetail.setText(str);
            routePlanItemHolder.itemView.setTag(str);
            routePlanItemHolder.itemView.setOnClickListener(this);
        } else if (tag == AppConstants.PLAN_DETAIL_STEP_TAG) {
            PlanStepItemHolder routePlanItemHolder = (PlanStepItemHolder) holder;
            String str = data.get(position);
            routePlanItemHolder.tvDetail.setText(str);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    public interface OnPlanDetailItemClickListener {
        void onItemClick(View view, String str);
    }

    private OnPlanDetailItemClickListener mOnItemClickListener = null;

    public void setOnPlanDetailItemClickListener(OnPlanDetailItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
