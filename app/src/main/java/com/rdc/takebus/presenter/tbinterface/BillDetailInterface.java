package com.rdc.takebus.presenter.tbinterface;

/**
 * 账单详情接口
 */
public interface BillDetailInterface extends NetworkInterface {
    void showToast(String msg);

    boolean isActivited();
}
