package com.rdc.takebus.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.takebus.R;

/**
 * Created by 梦涵 on 2016/5/24.
 */
public class PayFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_pay, container, false);
        }
        return view;
    }
}
