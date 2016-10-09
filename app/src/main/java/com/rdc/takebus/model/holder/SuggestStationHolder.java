package com.rdc.takebus.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rdc.takebus.R;


/**
 * Created by 53261 on 2016-5-22.
 */
public class SuggestStationHolder extends RecyclerView.ViewHolder {

    public TextView tvSuggestStation;

    public SuggestStationHolder(View itemView) {
        super(itemView);
        tvSuggestStation = (TextView) itemView.findViewById(R.id.tv_suggestStation);
    }

}
