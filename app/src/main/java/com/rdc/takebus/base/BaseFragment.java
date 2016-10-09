package com.rdc.takebus.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类
 */
public abstract class BaseFragment<V, T extends BaseFragmentPresenter<V>> extends Fragment {
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        initVariables();
    }

    protected abstract T createPresenter();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initVariables();


    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }
}
