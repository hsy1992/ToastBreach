package com.endless.toastbreach;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 弹窗时间的长短
 * @author haosiyuan
 * @date 2019/3/13 3:20 PM
 */
@IntDef({
        ToastDuration.LENGTH_SHORT,
        ToastDuration.LENGTH_LONG
})
@Retention(RetentionPolicy.SOURCE)
public @interface ToastDuration {

    int LENGTH_SHORT = 4000;

    int LENGTH_LONG = 7000;
}
