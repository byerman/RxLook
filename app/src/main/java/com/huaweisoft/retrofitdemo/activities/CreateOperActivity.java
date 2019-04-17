package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateOperActivity extends AppCompatActivity {

    private RelativeLayout rlytView;
    private RelativeLayout rlytCode;
    private Button btnCreate;
    private Button btnCode;
    private TextView tvLog;
    private TextView tvCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_create);
        initView();
        initListener();
    }

    private void initView() {
        rlytView = findViewById(R.id.rlyt_view);
        rlytCode = findViewById(R.id.rlyt_code);
        btnCreate = findViewById(R.id.btn_create);
        btnCode = findViewById(R.id.btn_code);
        tvLog = findViewById(R.id.tv_log);
        tvCode = findViewById(R.id.tv_code);
    }

    private void initListener() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCode();
            }
        });
    }

    private void create() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()) // 在子线程发送事件
          .observeOn(AndroidSchedulers.mainThread())   // 在主线程处理事件
          .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                setLogText("开始订阅事件流");
            }

            @Override
            public void onNext(Integer integer) {
                setLogText("收到事件:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到错误:" + e.toString());
            }

            @Override
            public void onComplete() {
                setLogText("完成事件流传输");
            }
        });
    }

    private void setLogText(String tvStr) {
        tvLog.setText(tvLog.getText() + "\n" + tvStr);
    }

    private void showCode() {
        rlytView.setVisibility(View.GONE);
        rlytCode.setVisibility(View.VISIBLE);
        tvCode.setText("Observable.create(new ObservableOnSubscribe<Integer>() {\n" +
                "\n" +
                "            @Override\n" +
                "            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {\n" +
                "                emitter.onNext(1);\n" +
                "                emitter.onNext(2);\n" +
                "                emitter.onNext(3);\n" +
                "                emitter.onComplete();\n" +
                "            }\n" +
                "        }).subscribeOn(Schedulers.io()) // 在子线程发送事件\n" +
                "          .observeOn(AndroidSchedulers.mainThread())   // 在主线程处理事件\n" +
                "          .subscribe(new Observer<Integer>() {\n" +
                "            @Override\n" +
                "            public void onSubscribe(Disposable d) {\n" +
                "                setLogText(\"开始订阅事件流\");\n" +
                "            }\n" +
                "\n" +
                "            @Override\n" +
                "            public void onNext(Integer integer) {\n" +
                "                setLogText(\"收到事件:\" + integer);\n" +
                "            }\n" +
                "\n" +
                "            @Override\n" +
                "            public void onError(Throwable e) {\n" +
                "                setLogText(\"收到错误:\" + e.toString());\n" +
                "            }\n" +
                "\n" +
                "            @Override\n" +
                "            public void onComplete() {\n" +
                "                setLogText(\"完成事件流传输\");\n" +
                "            }\n" +
                "        });");
    }

}
