package com.rdc.takebus.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.rdc.takebus.R;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.view.CustomView.CustomToast;


/**
 * Created by 梦涵 on 2016/5/10.
 * 建立Presenter弱引用
 */
public abstract class BaseActivity<V, T extends BaseActivityPresenter<V>>
        extends Activity implements View.OnClickListener {
    protected T mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        initViews(savedInstanceState);
        initVariables();
        //View与Presenter建立关联
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按两下返回
            if (isDoubleBackDestory) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    CustomToast.showToast(BaseActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT);
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    //结束进程
                    System.exit(0);
                }
            } else {
                Log.e("keyDown", "onKeyDown");
                finish();
                overridePendingTransition(R.anim.translate_not_move,
                        R.anim.translate_right_out);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}