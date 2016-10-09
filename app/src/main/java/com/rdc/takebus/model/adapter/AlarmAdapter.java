package com.rdc.takebus.model.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.takebus.R;
import com.rdc.takebus.entity.AlarmInfo;
import com.rdc.takebus.model.holder.AlarmHolder;
import com.rdc.takebus.presenter.tbinterface.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by ZZH on 2016/5/24.
 */
public class AlarmAdapter extends RecyclerView.Adapter {
    private List<AlarmInfo> lists;
    private Context context;
    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener mOnRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = mOnRecyclerItemClickListener;
    }

    public AlarmAdapter(Context context) {
        this.context = context;
    }

    public List<AlarmInfo> getLists() {
        return lists;
    }

    public void setLists(List<AlarmInfo> lists) {
        this.lists = lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_alarm, parent, false);
        return new AlarmHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindAlarmHolder((AlarmHolder) holder, position);
    }

    private void onBindAlarmHolder(AlarmHolder holder, final int position) {
        holder.getTvName().setText(lists.get(position).getAlarmName());
        Resources resource = context.getResources();
        if (lists.get(position).isChecked()) {
            holder.getTvName().setTextColor(resource.getColor(R.color.colorPrimary));
            holder.getImgCheck().setVisibility(View.VISIBLE);
        } else {
            holder.getImgCheck().setVisibility(View.INVISIBLE);
            holder.getTvName().setTextColor(resource.getColor(R.color.standardColorBlack));
        }
        holder.getRlAlarm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
