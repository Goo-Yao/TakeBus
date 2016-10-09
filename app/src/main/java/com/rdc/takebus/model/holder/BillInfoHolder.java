package com.rdc.takebus.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rdc.takebus.R;

/**
 * 车票信息ViewHolder
 */
public class BillInfoHolder extends RecyclerView.ViewHolder {
    public TextView mTvStart;
    public TextView mTvTerminal;
    public TextView mTvTime;
    public TextView mTvCost;

    public BillInfoHolder(View itemView) {
        super(itemView);
        mTvStart = (TextView) itemView.findViewById(R.id.tv_item_bill_start);
        mTvTerminal = (TextView) itemView.findViewById(R.id.tv_item_bill_terminal);
        mTvTime = (TextView) itemView.findViewById(R.id.tv_item_bill_time);
        mTvCost = (TextView) itemView.findViewById(R.id.tv_item_bill_cost);
    }
}
