package com.rdc.takebus.presenter.ActivityPresenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.rdc.takebus.base.BaseActivityPresenter;
import com.rdc.takebus.entity.BillInfo;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbinterface.BillDetailInterface;
import com.rdc.takebus.presenter.tbinterface.ResultListener;
import com.rdc.takebus.view.activity.BillDetailActivity;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 账单详情中介
 */
public class BillDetailPresenter extends BaseActivityPresenter<BillDetailInterface> {
    private static final String TAG = "BillDetailPresenter";
    private BillDetailInterface mBillDetailInterfaceImpl;

    public BillDetailPresenter(BillDetailInterface billDetailInterface) {
        this.mBillDetailInterfaceImpl = billDetailInterface;
    }

    /**
     * 获取上个Activity传来的订单数据
     */
    public BillInfo getBillInfoData(Intent intent) {
        return (BillInfo) intent.getSerializableExtra(intent.getStringExtra(BillDetailActivity.TO_TYPE));
    }

    /**
     * 获取二维码图片
     *
     * @param qrCoderUrl 图片url
     */
    public void getQrCodeImageNetwork(final String qrCoderUrl) {
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_OHTER)
                .doGetImage(qrCoderUrl, new ResultListener() {
                    @Override
                    public void onResultSuccess(String result) {
                        mBillDetailInterfaceImpl.onSuccess(null, result);
                    }

                    @Override
                    public void onResultFail(String result) {
                        mBillDetailInterfaceImpl.onFail(result);
                    }
                }, (Activity) mBillDetailInterfaceImpl);
    }


    public Bitmap getImageFromFile(String imagePath) {
        return BitmapFactory.decodeFile(imagePath);
    }


    /**
     * 开始重复访问后台，检查订单状态
     */
    public void repeatCheckBillState(final int id) {
        checkBillStateNetwork(id);
    }

    /**
     * 联网检查id对应账单状态
     *
     * @param id 账单对应id
     */
    private void checkBillStateNetwork(final int id) {
        Log.e(TAG, "id = " + id);
        RequestBody requestBody = new FormBody.Builder().add(AppConstants.ID, "" + id).build();
        Request request = new Request.Builder().post(requestBody).url(AppConstants.URL_STATE_CONFIRM).build();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).postData(request, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                if (result.equals("true")) {
                    mBillDetailInterfaceImpl.showToast("扫描成功");
                    // 执行返回操作
                    ((Activity) mBillDetailInterfaceImpl).onBackPressed();
                } else if (mBillDetailInterfaceImpl.isActivited()) {
                    checkBillStateNetwork(id);
                }
            }

            @Override
            public void onResultFail(String result) {
                checkBillStateNetwork(id);
            }
        }, (Activity) mBillDetailInterfaceImpl);
    }

}
