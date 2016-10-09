package com.rdc.takebus.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.takebus.R;

/**
 * 设置界面
 * Created by ZZH on 2016/5/24.
 */
public class SetActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvAlarm;
    private TextView tvShock;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private String alarmName;
    private String shockName;
    private String array[] = new String[]{"开启", "关闭"};
    private RelativeLayout rlAlarm, rlShock;
    private ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set);
        initView();
        initData();
        setData();
    }

    private void setData() {
        tvAlarm.setText(alarmName);
        tvShock.setText(shockName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        setData();
    }

    private void initData() {
        mSharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        alarmName = mSharedPreferences.getString("alarmName", "默认");
        shockName = mSharedPreferences.getString("shock", array[0]);
    }

    private void initView() {
        tvAlarm = (TextView) findViewById(R.id.tv_alarm);
        tvShock = (TextView) findViewById(R.id.tv_shock);
        rlAlarm = (RelativeLayout) findViewById(R.id.rl_alarm);
        rlShock = (RelativeLayout) findViewById(R.id.rl_shock);
        ivBack = (ImageView) findViewById(R.id.iv_back);

        ivBack.setOnClickListener(this);
        rlAlarm.setOnClickListener(this);
        rlShock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.rl_alarm: {
                Intent intent = new Intent(SetActivity.this, SetAlarmActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.rl_shock: {
                AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
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
