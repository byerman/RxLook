package com.huaweisoft.retrofitdemo.view;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huaweisoft.retrofitdemo.config.StatusConfigs;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author baiaj
 * @date 2018/1/26
 */
public class StatusBarUtil {


    /**
     * 设置状态栏的黑色字体图标
     * 适配4.4以上版本MIUI、Flyme和6.0以上版本其他Android
     */
    public static int getStatusBarLightMode(Window window) {
        int result = 0;
        // 判断当前android版本是否大于4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 当前系统为MIUI并在V6以上
            if (MIUISetStatusBarLightMode(window, true)) {
                result = 1;
            }else if (FlymeSetStatusBarLightMode(window, true)){
                result = 2;
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }else {
                //5.0
            }
        }
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     */
    public static void setStatusBarLightMode(Window window) {
        int type = getStatusBarLightMode(window);
        if (type == 1) {
            MIUISetStatusBarLightMode(window, true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(window, true);
        } else if (type == 3) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {//5.0
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                StatusConfigs.statusDrawable = Color.parseColor("#333ffffff");
            }
        }
    }

    /**
     * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
     */
    public static void setStatusBarDarkMode(Window window) {
        int type = getStatusBarLightMode(window);
        if (type == 1) {
            MIUISetStatusBarLightMode(window, false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(window, false);
        } else if (type == 3) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体图标设置为深色
     * @return 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                // 获取MIUI关于windowmanager的管理类及方法，然后调用相关设置的方法
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    // 状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    // 清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }
}
