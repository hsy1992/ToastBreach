package com.endless.toastbreach;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 系统弹窗
 * @author haosiyuan
 * @date 2019/3/13 4:15 PM
 */
public class SystemToast implements IToast, Comparable<SystemToast>, Cloneable {

    private static final String TAG = "SystemToast";
    private static Object iNotificationManagerObj;
    private Toast mToast;
    private Context mContext;
    private ToastConfig config;
    private String message;

    private static final String TOAST_GET_SERVICE = "getService";

    private static final String SYSTEM_TOAST_METHOD = "enqueueToast";
    private static final String SYSTEM_TOAST_METHOD_EX = "enqueueToastEx";

    public SystemToast(Context mContext) {
        this.mContext = mContext;
        this.config = new ToastConfig.ToastConfigBuilder().build();
    }

    /**
     * Toast 显示
     */
    public void showBreach() {
        if (mContext == null) {return;}

        mToast = Toast.makeText(mContext, message, config.getDuration() == ToastDuration.LENGTH_LONG
                ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);

        cloneToastConfig(mToast);
        if (!NotificationUtil.isNotificationEnabled(mContext)) {
            hookSystemHandler(mToast);
            hookToast(mToast);
        }
        mToast.show();
    }

    @Override
    public void show() {
        SystemHandler.getInstance().add(this);
    }

    @Override
    public void showLong() {
        config.setDuration(ToastDuration.LENGTH_LONG);
        SystemHandler.getInstance().add(this);
    }

    @Override
    public void cancel() {
        SystemHandler.getInstance().cancelAll();
    }

    @Override
    public void show(ToastConfig config) {
        this.config = config;
        SystemHandler.getInstance().add(this);
    }

    @Override
    public IToast text(String message) {
        this.message = message;
        return this;
    }

    /**
     * 将配置赋给Toast
     * @param mToast
     */
    private void cloneToastConfig(Toast mToast) {
        if (mToast == null) {
            return;
        }

        if (config.getView() != null) {
            mToast.setView(config.getView());
        }

        mToast.setGravity(config.getGravity(), config.getxOffset(), config.getyOffset());
        //设置动画 需要hook TN 与弹窗时间
        setupToastAnim(mToast, config.getAnimation());
    }

    /**
     * hook 设置动画
     * @param mToast
     * @param animation
     */
    private void setupToastAnim(Toast mToast, int animation) {
        Object mTN = getField(mToast, "mTN");
        if (mTN != null) {
            Object mParams = getField(mTN, "mParams");
            if (mParams instanceof WindowManager.LayoutParams) {
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                params.windowAnimations = animation;
            }
        }
    }

    /**
     * hook 替换TN中
     * @param mToast
     */
    private void hookSystemHandler(Toast mToast) {
    }

    /**
     * hook替换系统方法
     * @param toast
     */
    private void hookToast(Toast toast) {
        if (iNotificationManagerObj == null) {
            try {
                Method getServiceMethod = Toast.class.getDeclaredMethod(TOAST_GET_SERVICE);
                getServiceMethod.setAccessible(true);
                iNotificationManagerObj = getServiceMethod.invoke(toast);
                Class iNotificationManagerCls = Class.forName("android.app.INotificationManager");

                Object iNotificationManagerProxy = Proxy.newProxyInstance(toast.getClass().getClassLoader(),
                        new Class[]{iNotificationManagerCls}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                //强制使用系统Toast  华为p20 pro上为enqueueToastEx
                                if (SYSTEM_TOAST_METHOD.equals(method.getName()) ||
                                        SYSTEM_TOAST_METHOD_EX.equals(method.getName())) {
                                    args[0] = "android";
                                }
                                return method.invoke(iNotificationManagerObj, args);
                            }
                        });

                Field sServiceFiled = Toast.class.getDeclaredField("sService");
                sServiceFiled.setAccessible(true);
                sServiceFiled.set(toast, iNotificationManagerProxy);

            } catch (Exception e) {
                Log.d(TAG,"hook fail " + e.getMessage());
            }
        }
    }

    public int getPriority() {
        return config != null ?  config.getmPriority() : 0;
    }

    /**
     * 克隆
     * @return
     */
    @Override
    protected SystemToast clone() {
        SystemToast mToast = null;
        try {
            mToast = (SystemToast) super.clone();
            mToast.mContext = this.mContext;
            mToast.config = this.config;
        } catch (Exception e) {
            return null;
        }

        return mToast;
    }

    /**
     * 反射获取字段
     * @param object
     * @param fieldName
     * @return
     */
    private Object getField(Object object, String fieldName) {

        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (field != null) {
                field.setAccessible(true);
                return field.get(object);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取配置
     * @return
     */
    public ToastConfig getConfig() {
        return config;
    }

    /**
     * 取消弹窗
     */
    public void cancelInternal() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public static void cancelAll() {
        SystemHandler.getInstance().cancelAll();
    }

    @Override
    public int compareTo(SystemToast o) {
        return o.getPriority();
    }
}
