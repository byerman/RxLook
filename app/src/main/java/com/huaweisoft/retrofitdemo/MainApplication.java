package com.huaweisoft.retrofitdemo;

import android.app.Application;

import com.huaweisoft.retrofitdemo.config.StatusConfigs;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StatusConfigs.setToolbarDrawable(R.color.colorred);
        StatusConfigs.setStatusbarDrawable(R.color.colorred);
        StatusConfigs.setBackDrawable(R.drawable.ic_back);
        StatusConfigs.setIsStatusBarLight(true);
    }
}
