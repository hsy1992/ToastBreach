package com.endless.toastbreach;


/**
 * 弹窗接口
 * @author haosiyuan
 * @date 2019/3/13 2:37 PM
 */
public interface IToast {

    void show();

    void showLong();

    void cancel();

    void show(ToastConfig config);

    IToast text(String message);
}
