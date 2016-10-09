package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.DrawerPresenter;
import com.rdc.takebus.presenter.tbinterface.DrawerInterface;

/**
 * Created by 梦涵 on 2016/5/24.
 */
public class DrawerActivity extends BaseSwipeBackActivity<DrawerInterface, DrawerPresenter>
        implements DrawerInterface, View.OnClickListener {
    private ImageView imgBack;
    private TextView tvTitle;
    private FrameLayout frameLayout;
    private int position;//在drawerlayout中点击的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_drawer);
        position = (int) getIntent().getExtras().get("position");
        super.onCreate(savedInstanceState);
        mPresenter.setTransaction(getFragmentManager().beginTransaction());
        mPresenter.setIntent(position);
    }

    @Override
    protected DrawerPresenter createPresenter() {
        return new DrawerPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        imgBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        mPresenter.setIdAndTextView(R.id.framelayout, tvTitle);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
    }
}
