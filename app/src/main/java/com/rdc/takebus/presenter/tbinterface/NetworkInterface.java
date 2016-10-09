package com.rdc.takebus.presenter.tbinterface;

/**
 * 网络处理接口
 */
public interface NetworkInterface {
    /**
     * @param id     用于标识网络返回的类型
     * @param result 处理结果
     */
    void onSuccess(String id, String result);

    void onFail(String result);
}
