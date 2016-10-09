package com.rdc.takebus.presenter.tbinterface;


import com.rdc.takebus.entity.BillInfo;

/**
 * 支付界面接口
 */
public interface PaymentInterface extends NetworkInterface {
    /**
     * 启动详细账单界面
     *
     * @param info 账单详细信息
     */
    void startDetailActivity(BillInfo info);
}
