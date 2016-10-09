package com.rdc.takebus.view.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.takebus.R;
import com.rdc.takebus.view.activity.SetAlarmActivity;

/**
 * Created by ZZH on 2016/5/25.
 */
public class SetFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tvAlarm;
    private TextView tvShock;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private String alarmName;
    private String shockName;
    private String array[] = new String[]{"开启", "关闭"};
    private RelativeLayout rlAlarm, rlShock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_set, container, false);
            initView(view);
            initData();
            setData();
        }
        return view;
    }


    private void setData() {
        tvAlarm.setText(alarmName);
        tvShock.setText(shockName);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragmentOnResult", "onR esu;lt");
        initData();
        setData();
    }

    private void initData() {
        mSharedPreferences = getActivity().getSharedPreferences("alarm", getActivity().MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        alarmName = mSharedPreferences.getString("alarmName", "默认");
        shockName = mSharedPreferences.getString("shock", array[0]);
    }

    private void initView(View view) {
        tvAlarm = (TextView) view.findViewById(R.id.tv_alarm);
        tvShock = (TextView) view.findViewById(R.id.tv_shock);
        rlAlarm = (RelativeLayout) view.findViewById(R.id.rl_alarm);
        rlShock = (RelativeLayout) view.findViewById(R.id.rl_shock);

        rlAlarm.setOnClickListener(this);
        rlShock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_alarm: {
                Intent intent = new Intent(getActivity(), SetAlarmActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.rl_shock: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvShock.setText(array[which]);
                        editor.putString("shock", array[which]);
                        editor.commit();
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
            break;
        }
    }

}