package com.huaweisoft.retrofitdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huaweisoft.retrofitdemo.R;

/**
 * @author baiaj
 * RxJava界面
 */
public class RxJavaActivity extends BaseOperActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_conversion).setOnClickListener(this);
        findViewById(R.id.btn_merge).setOnClickListener(this);
        findViewById(R.id.btn_function).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                jumpToActivity(RxJavaActivity.this, CreateOperActivity.class);
                break;
            case R.id.btn_conversion:
                break;
            case R.id.btn_merge:
                break;
            case R.id.btn_function:
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean isShowCodeIcon() {
        return false;
    }

    @Override
    protected String getTitleStr() {
        return "RxJava操作符";
    }

}
