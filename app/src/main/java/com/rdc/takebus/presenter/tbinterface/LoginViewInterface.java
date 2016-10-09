package com.rdc.takebus.presenter.tbinterface;

/**
 * 登录界面接口 - 仅针对view的操作
 * Created by 梦涵 on 2016/5/12.
 */
public interface LoginViewInterface extends NetworkInterface {
    public String getUserName();

    public String getUserPsw();

    public void showMsgByToast(String strMsg);
}
