package com.huaweisoft.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huaweisoft.retrofitdemo.bean.ArticleList;
import com.huaweisoft.retrofitdemo.bean.BaseBean;
import com.huaweisoft.retrofitdemo.bean.LoginBean;
import com.huaweisoft.retrofitdemo.util.CookieUtil;
import com.huaweisoft.retrofitdemo.util.ParseErrorUtil;
import com.huaweisoft.retrofitdemo.util.VerifyEtUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;

import static com.huaweisoft.retrofitdemo.config.UrlConfig.BASE_URL;

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
        btnLogin.setOnClickListener(this);
        btnGetArtList.setOnClickListener(this);
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
            netWorkController.getArticleAndData();
        }
    }

}
