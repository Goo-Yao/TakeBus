package com.rdc.takebus.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public abstract class BaseActivityPresenter<T> {
    //View接口类型的弱引用
    protected Reference<T> mViewRef;

    //建立关联
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    //判断是否与View建立关联
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    //解除关联
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
