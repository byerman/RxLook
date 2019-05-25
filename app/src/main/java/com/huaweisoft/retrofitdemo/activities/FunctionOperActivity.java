package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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
                subscribeOn();
                code = "/**\n" +
                        "     * subScribeOn、observeOn操作符\n" +
                        "     */\n" +
                        "    private void subscribeOn() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"subScribeOn/observeOn操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"指定被观察者(observable)/观察者(observer)的工作线程类型\",false);\n" +
                        "        setLogText(\"subscribeOn()多次调用时,只有第一次指定有效，其余的指定线程都无效\",false);\n" +
                        "        setLogText(\"observeOn()多次调用时,调用一次，就会进行一次线程的切换\",false);\n" +
                        "        setLogText(\"Rxjava内置了多个用于调度的线程类型,如下:\",false);\n" +
                        "        setLogText(\"Schedulers.immediate(),不指定线程\",false);\n" +
                        "        setLogText(\"AndroidSchedulers.mainThread(),主线程\",false);\n" +
                        "        setLogText(\"Schedulers.newThread(),常规新线程\",false);\n" +
                        "        setLogText(\"Schedulers.io(),io操作线程\",false);\n" +
                        "        setLogText(\"Schedulers.computation(),CPU计算操作线程\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例\",true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Integer>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {\n" +
                        "                    String currentThread = Thread.currentThread().getName();\n" +
                        "                    setLogText(\"发送事件1,2:\" + currentThread,true);\n" +
                        "                    emitter.onNext(1);\n" +
                        "                    emitter.onNext(2);\n" +
                        "                    emitter.onComplete();\n" +
                        "            }\n" +
                        "        }).subscribeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribeOn(Schedulers.io())\n" +
                        "                .observeOn(Schedulers.newThread())\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Observer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        String currentThread = Thread.currentThread().getName();\n" +
                        "                        setLogText(\"收到订阅回调:\" + currentThread,true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Integer integer) {\n" +
                        "                        String currentThread = Thread.currentThread().getName();\n" +
                        "                        setLogText(\"收到事件:\" + integer + \",thread:\" + currentThread, true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"收到异常通知:\" + e.toString(), true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        String currentThread = Thread.currentThread().getName();\n" +
                        "                        setLogText(\"发送事件完成：\" + currentThread,true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        btnDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delay();
                code = "/**\n" +
                        "     * delay操作符\n" +
                        "     */\n" +
                        "    private void delay() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"delay操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"使被观察者延迟一段时间再发送事件\",false);\n" +
                        "        setLogText(\"delay()具有多个重载方法\",false);\n" +
                        "        setLogText(\"// 参数1 = 时间 参数2 = 时间单位\",true);\n" +
                        "        setLogText(\"delay(long delay, TimeUint unit)\",true);\n" +
                        "        setLogText(\"// 参数1 = 时间 参数2 = 时间单位 参数3 = 线程调度器\",true);\n" +
                        "        setLogText(\"delay(long delay, TimeUint unit, Scheduler scheduler)\",true);\n" +
                        "        setLogText(\"// 参数1 = 时间 参数2 = 时间单位 参数3 = 错误延迟\",true);\n" +
                        "        setLogText(\"// 错误延迟指的是若存在Error事件，则如常执行，执行完成再抛异常\",true);\n" +
                        "        setLogText(\"delay(long delay, TimeUint unit, boolean delayError)\",true);\n" +
                        "        setLogText(\"// 参数1 = 时间 参数2 = 时间单位 参数3 = 线程调度器 参数4 = 错误延迟\",true);\n" +
                        "        setLogText(\"delay(long delay,TimeUnit unit,Scheduler scheduler, boolean delayError)\",true);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例,延迟3秒发送(1,2,3)事件\",true);\n" +
                        "        Observable.just(1,2,3)\n" +
                        "\n" +
                        "                .delay(3, TimeUnit.SECONDS)\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Observer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"开始订阅\",true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Integer integer) {\n" +
                        "                        setLogText(\"收到事件:\" + integer, true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"收到异常事件:\" + e.toString(),true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送事件完毕\",true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doOper();
                code = "/**\n" +
                        "     * do操作符\n" +
                        "     */\n" +
                        "    private void doOper() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"do操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"在某个事件的生命周期中调用\",false);\n" +
                        "        setLogText(\"类型:\",false);\n" +
                        "        setLogText(\"do()操作符有很多个类型,如doOnEach()/doOnNext()/doOnError()/doOnSubscribe()\",false);\n" +
                        "        setLogText(\"具体解释看源码部分和实例演示\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"实例，发送1,2,3和一个error事件\",true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                emitter.onNext(1);\n" +
                        "                emitter.onNext(2);\n" +
                        "                emitter.onNext(3);\n" +
                        "                emitter.onError(new Throwable(\"有一个错误\"));\n" +
                        "            }\n" +
                        "        })\n" +
                        "          //  当Observerable每发送一个事件就会收到一次回调\n" +
                        "          .doOnEach(new Consumer<Notification<Object>>() {\n" +
                        "              @Override\n" +
                        "              public void accept(Notification<Object> objectNotification) throws Exception {\n" +
                        "                  setLogText(\"doOnEach:\" + objectNotification.getValue() == null ? \n" +
                        "                          objectNotification.getError().toString() : (String) objectNotification.getValue(),true);\n" +
                        "              }\n" +
                        "          })\n" +
                        "        // 执行onNext()前会收到回调\n" +
                        "        .doOnNext(new Consumer<Object>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Object o) throws Exception {\n" +
                        "                setLogText(\"doOnNext:\" + o,true);\n" +
                        "            }\n" +
                        "        })\n" +
                        "        // 执行onNext()后会收到回调\n" +
                        "        .doAfterNext(new Consumer<Object>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Object o) throws Exception {\n" +
                        "                setLogText(\"doAfterNext:\" + o,true);\n" +
                        "            }\n" +
                        "        })\n" +
                        "        // Observable正常发送完事件后会收到回调\n" +
                        "        .doOnComplete(new Action() {\n" +
                        "            @Override\n" +
                        "            public void run() throws Exception {\n" +
                        "                setLogText(\"doComplete\",true);\n" +
                        "            }\n" +
                        "        })\n" +
                        "        // Observable发送错误事件时回调\n" +
                        "        .doOnError(new Consumer<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Throwable throwable) throws Exception {\n" +
                        "                setLogText(\"doOnError:\" + throwable.getMessage(),true);\n" +
                        "            }\n" +
                        "        })\n" +
                        "        // Observer订阅时回调\n" +
                        "        .doOnSubscribe(new Consumer<Disposable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Disposable disposable) throws Exception {\n" +
                        "                setLogText(\"doOnSubscribe\",true);\n" +
                        "            }\n" +
                        "        })\n" +
                        "        // Observable发送事件完毕后调用，无论正常发送完毕/发生异常\n" +
                        "        .doAfterTerminate(new Action() {\n" +
                        "            @Override\n" +
                        "            public void run() throws Exception {\n" +
                        "                setLogText(\"doAfterTerminate\",true);\n" +
                        "            }\n" +
                        "        })\n" +
                        "        // 最后执行\n" +
                        "        .doFinally(new Action() {\n" +
                        "            @Override\n" +
                        "            public void run() throws Exception {\n" +
                        "                setLogText(\"doFinally\",true);\n" +
                        "            }\n" +
                        "        })\n" +
                        "        .subscribe(new Observer<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "                setLogText(\"订阅成功\",true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Object o) {\n" +
                        "                setLogText(\"收到事件:\" + o,true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "                setLogText(\"收到异常事件:\" + e.getMessage(),true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"发送事件完毕\",true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
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
        tvLog.setText("");
        setLogText("subScribeOn/observeOn操作符",false);
        setLogText("作用:",false);
        setLogText("指定被观察者(observable)/观察者(observer)的工作线程类型",false);
        setLogText("subscribeOn()多次调用时,只有第一次指定有效，其余的指定线程都无效",false);
        setLogText("observeOn()多次调用时,调用一次，就会进行一次线程的切换",false);
        setLogText("Rxjava内置了多个用于调度的线程类型,如下:",false);
        setLogText("Schedulers.immediate(),不指定线程",false);
        setLogText("AndroidSchedulers.mainThread(),主线程",false);
        setLogText("Schedulers.newThread(),常规新线程",false);
        setLogText("Schedulers.io(),io操作线程",false);
        setLogText("Schedulers.computation(),CPU计算操作线程",false);
        setLogText("**********************************", false);
        setLogText("示例",true);
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                    String currentThread = Thread.currentThread().getName();
                    setLogText("发送事件1,2:" + currentThread,true);
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onComplete();
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        String currentThread = Thread.currentThread().getName();
                        setLogText("收到订阅回调:" + currentThread,true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        String currentThread = Thread.currentThread().getName();
                        setLogText("收到事件:" + integer + ",thread:" + currentThread, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常通知:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        String currentThread = Thread.currentThread().getName();
                        setLogText("发送事件完成：" + currentThread,true);
                    }
                });
    }

    /**
     * delay操作符
     */
    private void delay() {
        tvLog.setText("");
        setLogText("delay操作符",false);
        setLogText("作用:",false);
        setLogText("使被观察者延迟一段时间再发送事件",false);
        setLogText("delay()具有多个重载方法",false);
        setLogText("// 参数1 = 时间 参数2 = 时间单位",false);
        setLogText("delay(long delay, TimeUint unit)",false);
        setLogText("// 参数1 = 时间 参数2 = 时间单位 参数3 = 线程调度器",false);
        setLogText("delay(long delay, TimeUint unit, Scheduler scheduler)",false);
        setLogText("// 参数1 = 时间 参数2 = 时间单位 参数3 = 错误延迟",false);
        setLogText("// 错误延迟指的是若存在Error事件，则如常执行，执行完成再抛异常",false);
        setLogText("delay(long delay, TimeUint unit, boolean delayError)",false);
        setLogText("// 参数1 = 时间 参数2 = 时间单位 参数3 = 线程调度器 参数4 = 错误延迟",false);
        setLogText("delay(long delay,TimeUnit unit,Scheduler scheduler, boolean delayError)",false);
        setLogText("**********************************", false);
        setLogText("示例,延迟3秒发送(1,2,3)事件",true);
        Observable.just(1,2,3)

                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("开始订阅",true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("收到事件:" + integer, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常事件:" + e.toString(),true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送事件完毕",true);
                    }
                });
    }

    /**
     * do操作符
     */
    private void doOper() {
        tvLog.setText("");
        setLogText("do操作符",false);
        setLogText("作用:",false);
        setLogText("在某个事件的生命周期中调用",false);
        setLogText("类型:",false);
        setLogText("do()操作符有很多个类型,如doOnEach()/doOnNext()/doOnError()/doOnSubscribe()",false);
        setLogText("具体解释看源码部分和实例演示",false);
        setLogText("**********************************", false);
        setLogText("实例，发送1,2,3和一个error事件",true);
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new Throwable("有一个错误"));
            }
        })
          //  当Observerable每发送一个事件就会收到一次回调
          .doOnEach(new Consumer<Notification<Object>>() {
              @Override
              public void accept(Notification<Object> objectNotification) throws Exception {
                  Object object = objectNotification.getValue();
                  String value = object == null ? objectNotification.getError().toString() : object.toString();
                  setLogText("doOnEach:" + value,true);
              }
          })
        // 执行onNext()前会收到回调
        .doOnNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                setLogText("doOnNext:" + o,true);
            }
        })
        // 执行onNext()后会收到回调
        .doAfterNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                setLogText("doAfterNext:" + o,true);
            }
        })
        // Observable正常发送完事件后会收到回调
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                setLogText("doComplete",true);
            }
        })
        // Observable发送错误事件时回调
        .doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                setLogText("doOnError:" + throwable.getMessage(),true);
            }
        })
        // Observer订阅时回调
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                setLogText("doOnSubscribe",true);
            }
        })
        // Observable发送事件完毕后调用，无论正常发送完毕/发生异常
        .doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                setLogText("doAfterTerminate",true);
            }
        })
        // 最后执行
        .doFinally(new Action() {
            @Override
            public void run() throws Exception {
                setLogText("doFinally",true);
            }
        })
        .subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功",true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("收到事件:" + o,true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常事件:" + e.getMessage(),true);
            }

            @Override
            public void onComplete() {
                setLogText("发送事件完毕",true);
            }
        });
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
