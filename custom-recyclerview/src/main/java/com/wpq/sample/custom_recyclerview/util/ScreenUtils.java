package com.wpq.sample.custom_recyclerview.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 *  屏幕相关工具类
 */
public final class ScreenUtils {

    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dp2px(Context context, float dp) {
        float density = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}