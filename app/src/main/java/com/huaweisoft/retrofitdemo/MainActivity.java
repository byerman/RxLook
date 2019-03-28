package com.huaweisoft.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huaweisoft.retrofitdemo.bean.ArticleList;
import com.huaweisoft.retrofitdemo.bean.LoginBean;
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

    private static final String TAG = MainActivity.class.getSimpleName();
    private Retrofit retrofit;
    private EditText etName;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRetrofit();
        initView();
//        getData();
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void getData() {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ArticleList> call = apiService.getArticleList();
        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(Call<ArticleList> call, Response<ArticleList> response) {
                ArticleList articleList = response.body();
                if (articleList != null) {
                    List<ArticleList.DataBean> dataBeanList = articleList.getData();
                    for (ArticleList.DataBean bean : dataBeanList) {
                        Log.d(TAG,"articleData:" + bean.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticleList> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            boolean result = VerifyEtUtil.verifyEtLogin(etName,etPwd,MainActivity.this);
            if (result) {
                login();
            }
        }
    }

    private void login() {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<LoginBean> call = apiService.login(etName.getText().toString(),etPwd.getText().toString());
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean bean = response.body();
                okhttp3.Headers headers = response.headers();
                Log.d(TAG,"loginResult:" + bean.toString());
                Log.d(TAG,"headers:" + headers.toString());
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }
}
