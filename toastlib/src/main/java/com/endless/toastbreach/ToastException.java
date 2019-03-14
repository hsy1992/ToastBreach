package com.endless.toastbreach;

/**
 * Toast统一异常
 * @author haosiyuan
 * @date 2019/3/14 10:52 AM
 */
public class ToastException extends RuntimeException {

    public ToastException(String message) {
        super(message);
    }
}
