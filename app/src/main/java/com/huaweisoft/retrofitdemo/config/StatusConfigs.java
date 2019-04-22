package com.huaweisoft.retrofitdemo.config;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;

/**
 * Created by baiaj on 2018/1/26.
 */

public class StatusConfigs {

    public static int statusDrawable;
    public static int toolbarBackgroundColor;
    public static int toolbarBackgroundDrawable;
    public static int backDrawable;
    public static boolean isStatusBarLight;

    public static void setStatusbarDrawable(@DrawableRes int statusDraw) {
        statusDrawable = statusDraw;
    }

    public static boolean isStatusBar() {
        return statusDrawable > 0;
    }

    public static void setToolbarDrawable(int toolbarBackgroundDrawable) {
        StatusConfigs.toolbarBackgroundDrawable = toolbarBackgroundDrawable;
    }

    public static void setBackDrawable(int backDrawable) {
        StatusConfigs.backDrawable = backDrawable;
    }

    public static void setIsStatusBarLight(boolean isStatusBarLight) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            StatusConfigs.statusDrawable = Color.parseColor("#33ffffff");
        }
        StatusConfigs.isStatusBarLight = isStatusBarLight;
    }
}
