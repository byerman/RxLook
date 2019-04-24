package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MergeOperActivity extends BaseOperActivity {

    private Button btnConcat;
    private Button btnConcatArray;
    private Button btnMerge;
    private Button btnMergeArray;
    private TextView tvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_merge);
        initView();
        initListener();
    }

    @Override
    protected boolean isShowCodeIcon() {
        return true;
    }

    @Override
    protected String getTitleStr() {
        return getString(R.string.merge_oper);
    }

    private void initView() {
        btnConcat = findViewById(R.id.btn_concat);
        btnConcatArray = findViewById(R.id.btn_concatArray);
        btnMerge = findViewById(R.id.btn_merge);
        btnMergeArray = findViewById(R.id.btn_mergeArray);
        tvLog = findViewById(R.id.tv_log);
    }

    private void initListener() {
        btnMerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnMergeArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnConcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concat();
                code = "/**\n" +
                        "     * concat操作符\n" +
                        "     */\n" +
                        "    private void concat() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"concat操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"组合多个被观察者一起发送数据，合并后按发送顺序串行执行\",false);\n" +
                        "        setLogText(\"注:可组合的被观察者数量<=4个\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"合并4个被观察者的数据,顺序为(1,2,3),(4,5,6),(7,8,9),(10,11,12)\",true);\n" +
                        "        Observable.concat(Observable.just(1,2,3),\n" +
                        "                          Observable.just(4,5,6),\n" +
                        "                          Observable.just(7,8,9),\n" +
                        "                          Observable.just(10,11,12))\n" +
                        "                .subscribe(new Observer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"开始采用subscribe连接\", true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Integer integer) {\n" +
                        "                        setLogText(\"接收到事件:\" + integer,false);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"出现异常:\" + e.toString(),true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完毕\",true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        btnConcatArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * concat操作符
     */
    private void concat() {
        tvLog.setText("");
        setLogText("concat操作符",false);
        setLogText("作用:",false);
        setLogText("组合多个被观察者一起发送数据，合并后按发送顺序串行执行",false);
        setLogText("注:可组合的被观察者数量<=4个",false);
        setLogText("**********************************", false);
        setLogText("合并4个被观察者的数据,顺序为(1,2,3),(4,5,6),(7,8,9),(10,11,12)",true);
        Observable.concat(Observable.just(1,2,3),
                          Observable.just(4,5,6),
                          Observable.just(7,8,9),
                          Observable.just(10,11,12))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("开始采用subscribe连接", true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("接收到事件:" + integer,false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("出现异常:" + e.toString(),true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕",true);
                    }
                });
    }

    /**
     * concatArray操作符
     */
    private void concatArray() {
        tvLog.setText("");
        setLogText("concatArray操作符",false);
        setLogText("功能大体上和concat操作符一致,不同的地方在于可以组合的被观察者数量>4个",false);
        setLogText("**********************************", false);
        setLogText("合并5个被观察者的数据,顺序为(1,2,3),(4,5,6),(7,8,9),(10,11,12),(13,14,15)",true);
    }

    /**
     * merge操作符
     */
    private void merge() {

    }

    /**
     * mergeArray操作符
     */
    private void mergeArray() {

    }

    /**
     * 设置日志显示
     * @param tvStr    新增内容
     * @param needTime 是否需要显示时间
     */
    private void setLogText(String tvStr, boolean needTime) {
        setLogText(tvLog,tvStr,needTime);
    }

}
