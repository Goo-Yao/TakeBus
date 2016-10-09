package com.rdc.takebus.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseFragment;
import com.rdc.takebus.entity.BillInfo;
import com.rdc.takebus.model.adapter.BillInfoAdapter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.BillPresenter;
import com.rdc.takebus.presenter.tbinterface.MyBillInterface;
import com.rdc.takebus.presenter.tbinterface.OnRecyclerItemClickListener;
import com.rdc.takebus.view.CustomView.CustomToast;
import com.rdc.takebus.view.activity.BillDetailActivity;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * "我的帐单"Fragment
 */
public class BillFragment extends BaseFragment<MyBillInterface, BillPresenter>
        implements MyBillInterface, OnRecyclerItemClickListener {

    public static final String FORM_BILLFRAGMENT_DATA = "from billfragment data";
    private static final String TAG = "BillFragment";

    private View mContentView;
    private RecyclerView mRecyclerView;
    private List<BillInfo> mBillInfos;
    private BillInfoAdapter mBillInfoAdapter;
    private Activity mActivity;
    private SpotsDialog mSpotsDialog;

    private String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(AppConstants.FRAGMENT_TYPE) == AppConstants.TYPE_NOW
                ? "unfinished" : "finished";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_my_bill, container, false);
        }
        initViews(savedInstanceState);
        return mContentView;
    }

    @Override
    protected BillPresenter createPresenter() {
        return new BillPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) mContentView;
        mSpotsDialog = new SpotsDialog(getFragmentActivity());

        mBillInfoAdapter = new BillInfoAdapter(getActivity(), mBillInfos);
        mRecyclerView.setAdapter(mBillInfoAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (getArguments().getInt(AppConstants.FRAGMENT_TYPE) == AppConstants.TYPE_NOW) {
            mBillInfoAdapter.setOnRecyclerItemClickListener(this);
        }

        mSpotsDialog.show();
        mPresenter.getAllBillInfoNetwork(type);
    }

    @Override
    protected void initVariables() {
        mBillInfos = new ArrayList<>();
    }

    @Override
    public Activity getFragmentActivity() {
        if (mActivity == null) {
            mActivity = getActivity();
        }
        return mActivity;
    }

    @Override
    public void onSuccess(String id, String result) {
        mSpotsDialog.hide();
        if (id == AppConstants.URL_BILL_INFO) {
            List<BillInfo> billInfos = mPresenter.getBillInfosFormJson(result);
            mBillInfos.clear();
            mBillInfos.addAll(billInfos);
            mBillInfoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFail(String result) {
        mSpotsDialog.dismiss();
        showToast(result);
    }


    public void showToast(String msg) {
        CustomToast.showToast(getFragmentActivity(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getFragmentActivity(), BillDetailActivity.class);
        intent.putExtra(FORM_BILLFRAGMENT_DATA, mBillInfos.get(position));
        intent.putExtra(BillDetailActivity.TO_TYPE, FORM_BILLFRAGMENT_DATA);
        startActivity(intent);
    }
}
