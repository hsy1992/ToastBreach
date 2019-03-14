package com.endless.toastbreach;

import android.content.Context;

/**
 * {@link android.widget.Toast} 管理 支持androidx
 * @author haosiyuan
 * @date 2019/3/13 3:18 PM
 */
public class ToastManager {

    private IToast toast;

    public static IToast make(Context context) {
        IToast toast = new SystemToast(context);
        return toast;
    }

    /**
     * 终止并清除所有弹窗
     */
    public static void cancel() {
        SystemToast.cancelAll();
    }
}
