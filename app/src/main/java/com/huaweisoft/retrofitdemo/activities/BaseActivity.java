package com.huaweisoft.retrofitdemo.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huaweisoft.retrofitdemo.config.StatusConfigs;
import com.huaweisoft.retrofitdemo.view.StatusBarUtil;

/**
 * 所有Activity的基类
 * @author baiaj
 * 可以设置布局id
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 状态栏
     */
    private View statuBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDectorViewListener();
        setContentView(getContentViewId());
    }

    /**
     * 获取布局id
     * @return 布局id
     */
    protected abstract int getContentViewId();

    /**
     * 子类通过复写该方法,控制是否改变状态栏(默认改变)
     */
    protected boolean isStatusBar() {
        return StatusConfigs.isStatusBar();
    }

    /**
     * 子类通过复写该方法,控制是否需要改变状态栏字体颜色
     */
    protected boolean isStatusBarLight() {
        return StatusConfigs.isStatusBarLight;
    }

    /**
     * 设置DectorView监听
     */
    private void setDectorViewListener() {
        // 延时加载数据
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (isStatusBarLight()){
                    StatusBarUtil.setStatusBarDarkMode(getWindow());
                }
                if (isStatusBar()){
                    initStatusBar();
                    // 避免弹出软键盘后statusbar颜色复原
                    getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener(){
                        @Override
                        public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            initStatusBar();
                        }
                    });
                }
                return false;
            }
        });
    }

    /**
     * 初始化状态栏
     */
    @SuppressLint("ResourceType")
    private void initStatusBar() {
        if (statuBar == null){
            // android系统级的资源得这么拿，否则拿不到
            int identifier = getResources().getIdentifier("statusBarBackground","id","android");
            statuBar = getWindow().findViewById(identifier);
        }
        if (statuBar != null){
            if (isStatusBarLight()){
                statuBar.setBackgroundDrawable(null);
            }
            statuBar.setBackgroundResource(StatusConfigs.statusDrawable);
        }
    }

    /**
     * 跳转到新的Activity
     * @param packageContext 旧Act的Context
     * @param targetContext  新Act的Context
     */
    protected void jumpToActivity(Context packageContext, Class<?> targetContext) {
        startActivity(new Intent(packageContext,targetContext));
    }
}
