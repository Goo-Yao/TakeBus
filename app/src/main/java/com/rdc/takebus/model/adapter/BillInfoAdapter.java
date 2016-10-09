package com.rdc.takebus.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.takebus.R;
import com.rdc.takebus.entity.BillInfo;
import com.rdc.takebus.model.holder.BillInfoHolder;
import com.rdc.takebus.presenter.tbinterface.OnRecyclerItemClickListener;

import java.util.List;

/**
 * 车票信息适配器
 */
public class BillInfoAdapter extends RecyclerView.Adapter<BillInfoHolder> implements View.OnClickListener {
    private LayoutInflater mLayoutInflater;
    private List<BillInfo> mBillInfos;

    public BillInfoAdapter(Context context, List<BillInfo> billInfos) {
        mLayoutInflater = LayoutInflater.from(context);
        mBillInfos = billInfos;
        Log.e("---", "billinfos size = " + billInfos.size());
    }

    @Override
    public int getItemViewType(int position) {
        // view类型以位置来代替
        return position;
    }

    @Override
    public BillInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_recycler_item_bill, parent, false);
        view.setTag(viewType);
        view.setOnClickListener(this);
        return new BillInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(BillInfoHolder holder, int position) {
        BillInfo billInfo = mBillInfos.get(position);
        holder.mTvStart.setText(billInfo.getStartPoint());
        holder.mTvTerminal.setText(billInfo.getEndPoint());
        holder.mTvTime.setText(billInfo.getCreateTime());
        holder.mTvCost.setText(billInfo.getCost() + " RMB");
    }

    @Override
    public int getItemCount() {
        return mBillInfos.size();
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (onRecyclerItemClickListener != null) {
            onRecyclerItemClickListener.onItemClick(position);
        }
    }
}
