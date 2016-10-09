package com.rdc.takebus.presenter.tbinterface;

/**
 * 注册界面所有接口
 */
public interface RegisterInterface extends NetworkInterface {
    /**
     * @return 用户名
     */
    String getUsername();

    /**
     * @return 密码
     */
    String getPassword();

    /**
     * @return 确认密码
     */
    String getConfirmPsw();

    /**
     * @return 验证码
     */
    String getIdentifyCode();

    /**
     * 弹土司显示信息
     */
    void showToast(String message);
}
