package com.endless.toastbreach;

import android.view.Gravity;
import android.view.View;

/**
 * 弹窗配置
 * @author haosiyuan
 * @date 2019/3/13 3:20 PM
 */
public class ToastConfig {

    /**
     * 弹窗时间
     */
    @ToastDuration
    private int duration = ToastDuration.LENGTH_SHORT;

    /**
     * 自定义弹窗view
     */
    private View view;

    /**
     * 默认为具底部64dip处弹窗
     */
    private int gravity = Gravity.BOTTOM;

    /**
     * 默认x坐标偏移
     */
    private int xOffset = 0;

    /**
     * 默认y坐标偏移量
     */
    private int yOffset = 64;

    /**
     * 弹窗动画
     */
    private int animation;

    /**
     * 弹窗优先级
     */
    private int mPriority;

    public static final class ToastConfigBuilder {
        private int duration = ToastDuration.LENGTH_SHORT;
        private View view;
        private int gravity = Gravity.BOTTOM;
        private int xOffset = 0;
        private int yOffset = 64;
        private int animation = android.R.style.Animation_Toast;
        private int mPriority = 1;

        public ToastConfigBuilder withDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public ToastConfigBuilder withView(View view) {
            this.view = view;
            return this;
        }

        public ToastConfigBuilder withGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public ToastConfigBuilder withXOffset(int xOffset) {
            this.xOffset = xOffset;
            return this;
        }

        public ToastConfigBuilder withYOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }

        public ToastConfigBuilder withAnimation(int animation) {
            this.animation = animation;
            return this;
        }

        public ToastConfigBuilder withMPriority(int mPriority) {
            this.mPriority = mPriority;
            return this;
        }

        public ToastConfig build() {
            ToastConfig toastConfig = new ToastConfig();
            toastConfig.duration = this.duration;
            toastConfig.yOffset = this.yOffset;
            toastConfig.view = this.view;
            toastConfig.animation = this.animation;
            toastConfig.mPriority = this.mPriority;
            toastConfig.xOffset = this.xOffset;
            toastConfig.gravity = this.gravity;
            return toastConfig;
        }
    }

    public int getDuration() {
        return duration;
    }

    public View getView() {
        return view;
    }

    public int getGravity() {
        return gravity;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public int getAnimation() {
        return animation;
    }

    public int getmPriority() {
        return mPriority;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public void setmPriority(int mPriority) {
        this.mPriority = mPriority;
    }
}
