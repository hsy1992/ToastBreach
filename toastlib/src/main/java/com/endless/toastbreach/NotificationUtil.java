package com.endless.toastbreach;

import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

/**
 * 通知工具
 * @author haosiyuan
 * @date 2019/3/13 4:25 PM
 */
public class NotificationUtil {

    /**
     * 消息是否开启
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        return areNotificationsEnabled;
    }
}
