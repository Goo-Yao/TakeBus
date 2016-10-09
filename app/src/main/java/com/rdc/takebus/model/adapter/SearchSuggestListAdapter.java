package com.rdc.takebus.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.takebus.R;
import com.rdc.takebus.model.holder.SuggestStationHolder;
import com.rdc.takebus.view.activity.SearchStationActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchSuggestListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<String> suggestData = new ArrayList<String>();
    private Context mContext;

    public SearchSuggestListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> suggestData) {
        this.suggestData = suggestData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_suggest_station, parent, false);
        return new SuggestStationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SuggestStationHolder suggestStationHolder = (SuggestStationHolder) holder;
        String strSuggset = suggestData.get(position);
        suggestStationHolder.tvSuggestStation.setText(strSuggset);
        suggestStationHolder.itemView.setTag(strSuggset);
        suggestStationHolder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return suggestData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }

    }

    public interface OnSuggestItemClickListener {
        void onItemClick(View view, String strSuggest);
    }

    private OnSuggestItemClickListener mOnItemClickListener = null;

    public void setOnSuggestItemClickListener(OnSuggestItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}