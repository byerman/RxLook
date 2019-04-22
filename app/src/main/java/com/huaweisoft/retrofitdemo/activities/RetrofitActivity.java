package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huaweisoft.retrofitdemo.NetWorkController;
import com.huaweisoft.retrofitdemo.R;
import com.huaweisoft.retrofitdemo.util.VerifyEtUtil;

/**
 * @author baiaj
 * Retrofit演示界面
 */
public class RetrofitActivity extends BaseActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etPwd;
    private NetWorkController netWorkController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initNetWorkController();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_retrofit;
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnGetArtList = findViewById(R.id.btn_getArtList);
        btnLogin.setOnClickListener(this);
        btnGetArtList.setOnClickListener(this);
    }

    private void initNetWorkController() {
        netWorkController = NetWorkController.getmInstance(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            boolean result = VerifyEtUtil.verifyEtLogin(etName,etPwd,RetrofitActivity.this);
            if (result) {
                netWorkController.login(etName.getText().toString(),etPwd.getText().toString());
            }
        } else if (v.getId() == R.id.btn_getArtList) {
//            netWorkController.getArticleList(ModelEnum.CallAdapter.rxjava);
//            netWorkController.getArticleAndData();
//            netWorkController.getArticleWhenError();
//            netWorkController.mergeArticleAndLocalRequest();
            netWorkController.zipArticleDoubleRequest();
        }
    }
}
