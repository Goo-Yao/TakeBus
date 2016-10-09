package com.rdc.takebus.base;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.swipeback.SwipeBackActivity;
import com.rdc.takebus.view.CustomView.CustomToast;

/**
 * Created by 53261 on 2016-5-20.
 */
public abstract class BaseSwipeBackActivity<V, T extends BaseActivityPresenter<V>> extends SwipeBackActivity {

    protected T mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter
        mPresenter = createPresenter();
        //View与Presenter建立关联
        mPresenter.attachView((V) this);
        initViews(savedInstanceState);
        initVariables();
    }

    protected abstract T createPresenter();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initVariables();

    public void loadData() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放所持有的Activity对象,避免内存泄露
        mPresenter.detachView();
    }

    public void setPendingTransition(int TYPE) {
        if (TYPE == AppConstants.OPEN_OVERPENDINGTRANSITION) {
            overridePendingTransition(R.anim.translate_right_in,
                    R.anim.translate_not_move);
        } else if (TYPE == AppConstants.OUT_OVERPENDINGTRANSITION) {
            overridePendingTransition(R.anim.translate_not_move,
                    R.anim.translate_right_out);
        }
    }

    // 再按一次退出程序
    protected long exitTime = 0;
    protected boolean isDoubleBackDestory = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isDoubleBackDestory && keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                CustomToast.showToast(BaseSwipeBackActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
