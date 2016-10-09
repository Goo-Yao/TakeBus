package com.rdc.takebus.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.view.CustomView.CustomToast;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by 梦涵 on 2016/5/24.
 */
public class AboutFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tvGrade, tvFunction, tvHelp, tvUpgrade;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_about, container, false);
            findAllViewById();
            setAllOnClickListener();
        }
        return view;
    }

    private void setAllOnClickListener() {
        tvGrade.setOnClickListener(this);
        tvFunction.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        tvUpgrade.setOnClickListener(this);
    }

    private void findAllViewById() {
        tvGrade = (TextView) view.findViewById(R.id.tv_grade);
        tvFunction = (TextView) view.findViewById(R.id.tv_function);
        tvHelp = (TextView) view.findViewById(R.id.tv_help);
        tvUpgrade = (TextView) view.findViewById(R.id.tv_upgrade);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_grade:
                CustomToast.showToast(getActivity(), "感谢您的支持，评分功能待开启", Toast.LENGTH_SHORT);
                break;
            case R.id.tv_function:
                final MaterialDialog dialog = new MaterialDialog(getActivity());
                dialog.setMessage("“我爱搭公交”是一款致力于实时交通的一款APP，它相比于市面上一些导航软件类似百度地图这些，更偏向于便捷交通出行方面，专注于公交消费，范围\n" +
                        " 小，针对性强，另附有上下车提醒功能").setPositiveButton("好的", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.tv_help:
                CustomToast.showToast(getActivity(), "感谢您的支持，帮助与反馈功能待开启", Toast.LENGTH_SHORT);
                break;
            case R.id.tv_upgrade:
                CustomToast.showToast(getActivity(), "当前已是最新版", Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }

    }
}
