package com.endless.toastbreach;


import android.os.Handler;
import android.os.Message;

import java.util.PriorityQueue;

import androidx.annotation.NonNull;

/**
 * 处理系统弹窗
 * @author haosiyuan
 * @date 2019/3/14 10:29 AM
 */
public class SystemHandler extends Handler {

    final static int REMOVE = 2;

    private PriorityQueue<SystemToast> queue;

    private static class SingletonHolder {
        private static final SystemHandler systemTn = new SystemHandler();
    }

    public static SystemHandler getInstance() {
        return SingletonHolder.systemTn;
    }

    private SystemHandler() {
        new ToastException("can not instance");
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg == null) {return;}
        switch (msg.what) {
            case REMOVE:
                remove((SystemToast) msg.obj);
                break;
            default:
                break;
        }
    }

    /**
     * 取消Toast
     */
    public void cancelAll() {
        removeMessages(REMOVE);
        if (!getQueue().isEmpty()) {
            ((SystemToast)getQueue().peek()).cancelInternal();
        }
        getQueue().clear();
    }

    /**
     * 新增Toast加入队列
     * @param toast
     */
    public void add(SystemToast toast) {
        if (toast == null) {
            return;
        }
        SystemToast mToast = toast.clone();
        if (mToast == null) {
            return;
        }

        notifyNewToast(mToast);
    }

    /**
     * 刷新Toast显示
     */
    private void notifyNewToast(@NonNull SystemToast mToast) {

        boolean isShowing = isShowing();

        SystemToast preToast = (SystemToast) getQueue().peek();
        //加入队列后 已经按优先级排好顺序
        getQueue().add(mToast);

        if (isShowing) {
            if (getQueue().size() == 2) {
                //获取优先级最高的
                SystemToast toast = (SystemToast) getQueue().peek();
                if (toast == mToast) {
                    //终止正在展示的
                    sendRemoveToast(preToast);
                }
            } else {
                return;
            }
        } else{
            showNextToast();
        }

    }

    private void sendRemoveToast(SystemToast preToast) {
        removeMessages(REMOVE);
        Message message = obtainMessage(REMOVE);
        message.obj = preToast;
        sendMessage(message);
    }

    private void remove(SystemToast toast) {
        getQueue().remove(toast);
        toast.cancelInternal();
        // 展示下一个Toast
        showNextToast();
    }

    private void sendRemoveMsgDelay(SystemToast toast) {
        removeMessages(REMOVE);
        Message message = obtainMessage(REMOVE);
        message.obj = toast;
        sendMessageDelayed(message, toast.getConfig().getDuration());
    }

    /**
     * 显示下一个弹窗
     */
    private void showNextToast() {
        if (getQueue().isEmpty()) return;
        SystemToast toast = (SystemToast) getQueue().peek();
        if (null == toast) {
            getQueue().poll();
            showNextToast();
        } else {
            displayToast(toast);
        }
    }

    /**
     * 显示弹窗
     * @param toast
     */
    private void displayToast(@NonNull SystemToast toast) {
        toast.showBreach();
        //展示到时间后移除
        sendRemoveMsgDelay(toast);
    }


    /**
     * 是否有弹窗正在显示
     * @return
     */
    private boolean isShowing() {
        return getQueue().size() > 0;
    }

    /**
     * 获得队列
     * @return
     */
    private PriorityQueue getQueue() {
        if (queue == null) {
            queue = new PriorityQueue<>();
        }
        return queue;
    }


}
