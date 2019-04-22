package com.huaweisoft.retrofitdemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huaweisoft.retrofitdemo.NetWorkController;
import com.huaweisoft.retrofitdemo.R;
import com.huaweisoft.retrofitdemo.util.VerifyEtUtil;

/**
 * @author baiaj
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    private void initView() {
        Button btnJumpRetrofit = findViewById(R.id.btn_retrofit);
        btnJumpRetrofit.setOnClickListener(this);
        Button btnJumpRxJava = findViewById(R.id.btn_rxjava);
        btnJumpRxJava.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_rxjava) {
            jumpToActivity(MainActivity.this, RxJavaActivity.class);
        } else if (v.getId() == R.id.btn_retrofit) {
            jumpToActivity(MainActivity.this,RetrofitActivity.class);
        }
    }

}
