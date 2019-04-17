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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etPwd;
    private NetWorkController netWorkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initNetWorkController();
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnGetArtList = findViewById(R.id.btn_getArtList);
        Button btnJumpRxJava = findViewById(R.id.btn_rxjava);
        btnLogin.setOnClickListener(this);
        btnGetArtList.setOnClickListener(this);
        btnJumpRxJava.setOnClickListener(this);
    }

    private void initNetWorkController() {
        netWorkController = NetWorkController.getmInstance(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            boolean result = VerifyEtUtil.verifyEtLogin(etName,etPwd,MainActivity.this);
            if (result) {
                netWorkController.login(etName.getText().toString(),etPwd.getText().toString());
            }
        } else if (v.getId() == R.id.btn_getArtList) {
//            netWorkController.getArticleList(ModelEnum.CallAdapter.rxjava);
//            netWorkController.getArticleAndData();
//            netWorkController.getArticleWhenError();
//            netWorkController.mergeArticleAndLocalRequest();
            netWorkController.zipArticleDoubleRequest();
        } else if (v.getId() == R.id.btn_rxjava) {
            startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
        }
    }

}
