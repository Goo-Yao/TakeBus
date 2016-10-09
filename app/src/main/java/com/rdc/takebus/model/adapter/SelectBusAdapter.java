package com.rdc.takebus.model.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rdc.takebus.R;

import java.util.List;
import java.util.Map;

public class SelectBusAdapter extends BaseAdapter{

	private List<Map<String, String>> data;
	private Context context;
	private TextView tv_station, tv_time, tv_route;
	private String time, station;

	public SelectBusAdapter(List<Map<String, String>> list, Context context) {
		this.data = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.select_bus_item, null);
			holder.tv_station = (TextView) convertView.findViewById(R.id.tv_station);
			holder.tv_route = (TextView) convertView.findViewById(R.id.tv_route);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		station = (String) data.get(position).get("station");
		SpannableStringBuilder style = new SpannableStringBuilder(station);
		style.setSpan(new ForegroundColorSpan(Color.RED), 2, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		holder.tv_station.setText(style);
		Log.e("车站","" + station);

		holder.tv_route.setText((String) data.get(position).get("route"));

		time = (String) data.get(position).get("time");
		SpannableStringBuilder style2 = new SpannableStringBuilder(time);
		style2.setSpan(new ForegroundColorSpan(Color.RED), 1, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		holder.tv_time.setText(style2);
		Log.e("时间", "" + time);
		Log.e("取出数据", "------");

		return convertView;
	}

	class ViewHolder {
		TextView tv_route;
		TextView tv_time;
		TextView tv_station;
	}
}
