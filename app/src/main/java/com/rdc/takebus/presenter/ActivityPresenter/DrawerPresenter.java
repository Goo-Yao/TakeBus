package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.tbinterface.DrawerInterface;
import com.rdc.takebus.view.fragment.AboutFragment;
import com.rdc.takebus.view.fragment.BillFragment;
import com.rdc.takebus.view.fragment.NoticeFragment;
import com.rdc.takebus.view.fragment.PayFragment;
import com.rdc.takebus.view.fragment.PersonalCenterFragment;
import com.rdc.takebus.view.fragment.SetFragment;

/**
 * Created by 梦涵 on 2016/5/24.
 */
public class DrawerPresenter extends BaseActivityPresenter<DrawerInterface>
        implements DrawerInterface {
    private DrawerInterface drawerInterface;
    private int frameLayoutId;
    private FragmentTransaction transaction;
    private AboutFragment aboutFragment;
    private BillFragment historyFrgment;
    private BillFragment billFragment;
    private NoticeFragment noticeFragment;
    private PayFragment payFragment;
    private SetFragment setFragment;
    private PersonalCenterFragment personalCenterFragment;
    private TextView tvTitle;

    public DrawerPresenter(DrawerInterface drawerInterface) {
        this.drawerInterface = drawerInterface;
    }

    public void setIdAndTextView(int id, TextView tvTitle) {
        frameLayoutId = id;
        this.tvTitle = tvTitle;
    }

    public void setTransaction(FragmentTransaction transaction) {
        this.transaction = transaction;
    }

    public void setIntent(int position) {
        switch (position) {
            case 0:
                if (personalCenterFragment == null) {
                    personalCenterFragment = new PersonalCenterFragment();
                }
                tvTitle.setText("个人中心");
                transaction.add(frameLayoutId, personalCenterFragment);
                transaction.commit();
                break;

            case 1:
                if (payFragment == null) {
                    payFragment = new PayFragment();
                }
                tvTitle.setText("付款方式");
                transaction.add(frameLayoutId, payFragment);
                transaction.commit();
                break;

            case 2:
                if (noticeFragment == null) {
                    noticeFragment = new NoticeFragment();
                }
                tvTitle.setText("通知");
                transaction.add(frameLayoutId, noticeFragment);
                transaction.commit();
                break;

            case 3:
                if (aboutFragment == null) {
                    aboutFragment = new AboutFragment();
                }
                tvTitle.setText("关于");
                transaction.add(frameLayoutId, aboutFragment);
                transaction.commit();
                break;
            case 4:
                if (setFragment == null) {
                    setFragment = new SetFragment();
                }
                tvTitle.setText("设置");
                transaction.add(frameLayoutId, setFragment);
                transaction.commit();
                break;
            case 5:
                if (historyFrgment == null) {
                    historyFrgment = new BillFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.FRAGMENT_TYPE, AppConstants.TYPE_HISTROY);
                    historyFrgment.setArguments(bundle);
                }
                tvTitle.setText("历史账单");
                transaction.add(frameLayoutId, historyFrgment);
                transaction.commit();
                break;
            case 6:
                if (billFragment == null) {
                    billFragment = new BillFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.FRAGMENT_TYPE, AppConstants.TYPE_NOW);
                    billFragment.setArguments(bundle);
                }
                tvTitle.setText("我的车票");
                transaction.add(frameLayoutId, billFragment);
                transaction.commit();
                break;
            default:
        }
    }
}
