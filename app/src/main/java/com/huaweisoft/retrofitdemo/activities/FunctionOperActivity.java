package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class FunctionOperActivity extends BaseOperActivity{

    private Button btnSubscibe;
    private Button btnSubscribeOn;
    private Button btnDelay;
    private Button btnDo;
    private Button btnOnErrorReturn;
    private Button btnOnErrorResumeNext;
    private Button btnRetry;
    private Button btnOnExceptionResumeNext;
    private Button btnRetryUntil;
    private Button btnRetryWhen;
    private Button btnRepeat;
    private Button btnRepeatWhen;
    private TextView tvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_function);
        initView();
        initListener();
    }

    @Override
    protected boolean isShowCodeIcon() {
        return true;
    }

    @Override
    protected String getTitleStr() {
        return getString(R.string.function_oper);
    }

    private void initView() {
        btnSubscibe = findViewById(R.id.btn_subscribe);
        btnSubscribeOn = findViewById(R.id.btn_subscribeOn);
        btnDelay = findViewById(R.id.btn_delay);
        btnDo = findViewById(R.id.btn_do);
        btnOnErrorReturn = findViewById(R.id.btn_onErrorReturn);
        btnOnErrorResumeNext = findViewById(R.id.btn_onErrorResumeNext);
        btnRetry = findViewById(R.id.btn_retry);
        btnOnExceptionResumeNext = findViewById(R.id.btn_onExceptionResumeNext);
        btnRetryUntil = findViewById(R.id.btn_retryUntil);
        btnRetryWhen = findViewById(R.id.btn_retryWhen);
        btnRepeat = findViewById(R.id.btn_repeat);
        btnRepeatWhen = findViewById(R.id.btn_repeatWhen);
        tvLog = findViewById(R.id.tv_log);
    }

    private void initListener() {
        btnSubscibe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribe();
                code = "/**\n" +
                        "     * subscribe操作符\n" +
                        "     */\n" +
                        "    private void subscribe() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"subscribe操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"订阅,即连接被观察者和观察者\",false);\n" +
                        "        setLogText(\"只有被观察者订阅了观察者,被观察者发送的事件才能被观察者接收\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                // 发送事件\n" +
                        "                setLogText(\"发送事件1\",true);\n" +
                        "                emitter.onNext(\"事件1\");\n" +
                        "                // 发送完成\n" +
                        "                setLogText(\"发送完成事件\",true);\n" +
                        "                emitter.onComplete();\n" +
                        "                // 发送异常\n" +
                        "//                emitter.onError(new RuntimeException(\"new exception\"));\n" +
                        "            }\n" +
                        "        });\n" +
                        "        // 开始订阅\n" +
                        "        setLogText(\"开始订阅\",true);\n" +
                        "        observable.subscribe(new Observer<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "                // 订阅了就会回调到这里\n" +
                        "                setLogText(\"收到订阅回调\",true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Object o) {\n" +
                        "                // 收到的事件会回调到这里\n" +
                        "                setLogText(\"收到事件:\" + o,true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "                // 发送事件过程出现异常会回调到这里\n" +
                        "                setLogText(\"收到异常:\" + e.toString(),true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"收到发送事件完成回调\",true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnSubscribeOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnOnErrorReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnOnErrorResumeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnOnExceptionResumeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnRetryUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnRetryWhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnRepeatWhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * subscribe操作符
     */
    private void subscribe() {
        tvLog.setText("");
        setLogText("subscribe操作符",false);
        setLogText("作用:",false);
        setLogText("订阅,即连接被观察者和观察者",false);
        setLogText("只有被观察者订阅了观察者,被观察者发送的事件才能被观察者接收",false);
        setLogText("**********************************", false);
        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                // 发送事件
                setLogText("发送事件1",true);
                emitter.onNext("事件1");
                // 发送完成
                setLogText("发送完成事件",true);
                emitter.onComplete();
                // 发送异常
//                emitter.onError(new RuntimeException("new exception"));
            }
        });
        // 开始订阅
        setLogText("开始订阅",true);
        observable.subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                // 订阅了就会回调到这里
                setLogText("收到订阅回调",true);
            }

            @Override
            public void onNext(Object o) {
                // 收到的事件会回调到这里
                setLogText("收到事件:" + o,true);
            }

            @Override
            public void onError(Throwable e) {
                // 发送事件过程出现异常会回调到这里
                setLogText("收到异常:" + e.toString(),true);
            }

            @Override
            public void onComplete() {
                setLogText("收到发送事件完成回调",true);
            }
        });
    }

    /**
     * subScribeOn、observeOn操作符
     */
    private void subscribeOn() {

    }

    /**
     * delay操作符
     */
    private void delay() {

    }

    /**
     * do操作符
     */
    private void doOper() {

    }

    /**
     * onErrorReturn操作符
     */
    private void onErrorReturn() {

    }

    /**
     * onErrorResumeNext操作符
     */
    private void onErrorResumeNext() {

    }

    /**
     *  retry操作符
     */
    private void retry() {

    }

    /**
     * onExceptionResumeNext操作符
     */
    private void onExceptionResumeNext() {

    }

    /**
     * retryUntil操作符
     */
    private void retryUntil() {

    }

    /**
     * retryWhen操作符
     */
    private void retryWhen() {

    }

    /**
     * repeat操作符
     */
    private void repeat() {

    }

    /**
     * repeatWhen操作符
     */
    private void repeatWhen() {

    }

    /**
     * 设置日志内容
     *
     * @param content    内容
     * @param isNeedTime 是否需要时间显示
     */
    private void setLogText(String content, boolean isNeedTime) {
        setLogText(tvLog, content, isNeedTime);
    }
}
