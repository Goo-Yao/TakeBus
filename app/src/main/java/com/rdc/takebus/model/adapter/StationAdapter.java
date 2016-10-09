package com.rdc.takebus.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.takebus.R;

import java.util.List;


/**
 * Created by 梦涵 on 2016/5/15.
 */
public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> implements View.OnClickListener {
    private StationListener stationListener = null;
    private List<String> stations;
    private List<String> distance;
    private List<String> detail;

    public void setData(List<String> stations, List<String> distance, List<String> detail) {
        this.stations = stations;
        this.distance = distance;
        this.detail = detail;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_station_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvStation.setText(stations.get(position));
        holder.tvDistance.setText("  " + distance.get(position) + "m");
        holder.tvDetail.setText("详情：" +detail.get(position));
        holder.rlStation.setTag(position);
        holder.rlStation.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return distance.size();
    }

    @Override
    public void onClick(View v) {
        if (stationListener != null) {
            stationListener.onItemClick(v, (int) v.getTag());
        }
    }

    public interface StationListener {
        void onItemClick(View view, int tag);
    }

    public void setOnItemClickListener(StationListener listener) {
        stationListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStation;
        public TextView tvDistance;
        public TextView tvDetail;
        public RelativeLayout rlStation;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStation = (TextView) itemView.findViewById(R.id.tv_station);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            tvDetail = (TextView) itemView.findViewById(R.id.expandable_text);
            rlStation = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
        }
    }
}
