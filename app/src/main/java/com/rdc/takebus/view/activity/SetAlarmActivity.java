package com.rdc.takebus.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.entity.AlarmInfo;
import com.rdc.takebus.model.adapter.AlarmAdapter;
import com.rdc.takebus.presenter.ActivityPresenter.SetAlarmPresenter;
import com.rdc.takebus.presenter.tbinterface.OnRecyclerItemClickListener;
import com.rdc.takebus.presenter.tbinterface.SetAlarmInterface;

import java.util.List;

/**
 * 设置闹钟界面
 * Created by ZZH on 2016/5/24.
 */
public class SetAlarmActivity extends BaseSwipeBackActivity<SetAlarmInterface, SetAlarmPresenter> implements OnRecyclerItemClickListener, SetAlarmInterface, View.OnClickListener {
    private RecyclerView mRecyclerView;
    private List<AlarmInfo> lists;
    private AlarmAdapter adapter;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private ImageView imgBack;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_alarm);
        super.onCreate(savedInstanceState);
        mPresenter.getMediaData();
        mPresenter.setBeforeData(position);
    }

    @Override
    protected SetAlarmPresenter createPresenter() {
        return new SetAlarmPresenter(this, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        imgBack = (ImageView) findViewById(R.id.img_back);

        mSharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        position = mSharedPreferences.getInt("position", 1);
    }

    @Override
    protected void initVariables() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SetAlarmActivity.this));
        adapter = new AlarmAdapter(SetAlarmActivity.this);
        imgBack.setOnClickListener(this);
    }


    @Override
    public void onItemClick(int position) {
        //写入数据
        editor.putInt("position", position);
        editor.putString("alarmName", lists.get(position).getAlarmName());
        editor.putString("uri", lists.get(position).getUid());
        editor.commit();
        mPresenter.playMedia(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onDestory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void loadMediaData(List<AlarmInfo> lists) {
        this.lists = lists;
        adapter.setLists(lists);
        adapter.setOnRecyclerItemClickListener(this);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void notifyData() {
        adapter.notifyDataSetChanged();
    }
}
