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
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class FunctionOperActivity extends BaseOperActivity {

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
                onErrorReturn();
                code = "/**\n" +
                        "     * onErrorReturn操作符\n" +
                        "     */\n" +
                        "    private void onErrorReturn() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"onErrorReturn操作符\",false);\n" +
                        "        setLogText(\"作用：\",false);\n" +
                        "        setLogText(\"遇到错误时，发送一个特殊事件&正常终止\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"实例，发送1和一个error事件\",true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {\n" +
                        "                emitter.onNext(1);\n" +
                        "                emitter.onError(new Throwable(\"错误事件1\"));\n" +
                        "            }\n" +
                        "        }).onErrorReturn(new Function<Throwable, Integer>() {\n" +
                        "            @Override\n" +
                        "            public Integer apply(Throwable throwable) throws Exception {\n" +
                        "                setLogText(\"onErrorReturn:\" + throwable.toString(),true);\n" +
                        "                setLogText(\"发送新事件2\",true);\n" +
                        "                return 2;\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "                setLogText(\"订阅成功\",true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Integer integer) {\n" +
                        "                setLogText(\"收到事件:\" + integer,true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "                setLogText(\"收到异常回调:\" + e.toString(),true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"事件发送完毕\",true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnOnErrorResumeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onErrorResumeNext();
                code = "/**\n" +
                        "     * onErrorResumeNext操作符\n" +
                        "     */\n" +
                        "    private void onErrorResumeNext() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"onErrorResumeNext操作符\",false);\n" +
                        "        setLogText(\"作用：\",false);\n" +
                        "        setLogText(\"遇到错误时，发送一个新的Observable\",false);\n" +
                        "        setLogText(\"注意：这里拦截的错误类型是throwable\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"实例，发送1和一个throwable错误事件,拦截成功后，发送新的Observable(2,3)\",true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送1事件\",true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送throwable错误\",true);\n" +
                        "                emitter.onError(new Throwable(\"throwable错误\"));\n" +
                        "            }\n" +
                        "        }).onErrorResumeNext(new Function<Throwable, ObservableSource<?>>() {\n" +
                        "            @Override\n" +
                        "            public ObservableSource<?> apply(Throwable throwable) throws Exception {\n" +
                        "                setLogText(\"onErrorResumeNext:\" + throwable.toString(),true);\n" +
                        "                setLogText(\"发送新的Observable(2,3)\",true);\n" +
                        "                return Observable.just(2,3);\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Object>() {\n" +
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
                        "                setLogText(\"收到异常:\" + e.toString(),true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"事件发送完毕\",true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry();
                code = " /**\n" +
                        "     * retry操作符\n" +
                        "     */\n" +
                        "    private void retry() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"retry操作符\", false);\n" +
                        "        setLogText(\"作用:\", false);\n" +
                        "        setLogText(\"当出现错误时(即接收到onError()),让被观察者重新发送事件\", false);\n" +
                        "        setLogText(\"有5种重载方法:\", false);\n" +
                        "        setLogText(\"1.retry() //一直错误一直发\", false);\n" +
                        "        setLogText(\"2.retry(long time) //错误时重新发送数据(有次数(time)限制)\", false);\n" +
                        "        setLogText(\"3.retry(Perdicate predicate) //错误时根据判断逻辑(predicate)判断是否需要重新发送数据\", false);\n" +
                        "        setLogText(\"4.retry(new BiPredicate<Integer,Throwable>) //错误时根据判断逻辑(重试次数&错误信息)判断是否需要重新发送数据\", false);\n" +
                        "        setLogText(\"5.retry(long time,Predicate perdicate) //错误时根据判断逻辑(predicate)和重试次数(time)判断是否需要重新发送数\", false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"要测试其他重载，可修改代码测试,默认测试第二种\", false);\n" +
                        "        setLogText(\"实例，发送1,和onError()事件，2事件\", true);\n" +
                        "        retry2();\n" +
                        "    }\n" +
                        "\n" +
                        "    private void retry1() {\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送事件1\", true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送错误事件\", true);\n" +
                        "                emitter.onError(new Exception(\"发生错误了\"));\n" +
                        "                emitter.onNext(2);\n" +
                        "            }\n" +
                        "        }).retry()\n" +
                        "                .subscribe(new Observer<Object>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"订阅成功\", true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Object o) {\n" +
                        "                        setLogText(\"收到事件:\" + o, true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"收到异常:\" + e.toString(), true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完毕\", true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }\n" +
                        "\n" +
                        "    private void retry2() {\n" +
                        "        setLogText(\"重试3次\", true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送事件1\", true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送错误事件\", true);\n" +
                        "                emitter.onError(new Exception(\"发生错误了\"));\n" +
                        "                emitter.onNext(2);\n" +
                        "            }\n" +
                        "        }).retry(3)\n" +
                        "                .subscribe(new Observer<Object>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"订阅成功\", true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Object o) {\n" +
                        "                        setLogText(\"收到事件:\" + o, true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"收到异常:\" + e.toString(), true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完毕\", true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }\n" +
                        "\n" +
                        "    private void retry3() {\n" +
                        "        setLogText(\"拦截到异常不重试\",true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送事件1\", true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送错误事件\", true);\n" +
                        "                emitter.onError(new Exception(\"发生错误了\"));\n" +
                        "                emitter.onNext(2);\n" +
                        "            }\n" +
                        "        }).retry(new Predicate<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public boolean test(Throwable throwable) throws Exception {\n" +
                        "                setLogText(\"拦截到异常:\" + throwable.toString(), true);\n" +
                        "                setLogText(\"不重新发送数据，返回false\",true);\n" +
                        "                // 返回false不重试，返回true继续重试\n" +
                        "                return false;\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "                setLogText(\"订阅成功\", true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Object o) {\n" +
                        "                setLogText(\"收到事件:\" + o, true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "                setLogText(\"收到异常:\" + e.toString(), true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"发送完毕\", true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    private void retry4() {\n" +
                        "        setLogText(\"拦截到异常事件后，重试次数为2，则不重试\",true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送事件1\", true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送错误事件\", true);\n" +
                        "                emitter.onError(new Exception(\"发生错误了\"));\n" +
                        "                emitter.onNext(2);\n" +
                        "            }\n" +
                        "        }).retry(new BiPredicate<Integer, Throwable>() {\n" +
                        "            @Override\n" +
                        "            public boolean test(Integer integer, Throwable throwable) throws Exception {\n" +
                        "                setLogText(\"重试次数=\"+integer,true);\n" +
                        "                setLogText(\"拦截异常:\" + throwable.toString(),true);\n" +
                        "                // 返回false不重试，返回true继续重试\n" +
                        "                if (integer == 2) {\n" +
                        "                    return false;\n" +
                        "                } else {\n" +
                        "                    return true;\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "                setLogText(\"订阅成功\", true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Object o) {\n" +
                        "                setLogText(\"收到事件:\" + o, true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "                setLogText(\"收到异常:\" + e.toString(), true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"发送完毕\", true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }\n" +
                        "\n" +
                        "    private void retry5() {\n" +
                        "        setLogText(\"拦截到异常事件后，继续重试，重试次数为2，则不重试\",true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送事件1\", true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送错误事件\", true);\n" +
                        "                emitter.onError(new Exception(\"发生错误了\"));\n" +
                        "                emitter.onNext(2);\n" +
                        "            }\n" +
                        "        }).retry(2, new Predicate<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public boolean test(Throwable throwable) throws Exception {\n" +
                        "                setLogText(\"拦截到异常:\" + throwable.toString(),true);\n" +
                        "                // 返回false不重试，返回true继续重试\n" +
                        "                return true;\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "                setLogText(\"订阅成功\", true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Object o) {\n" +
                        "                setLogText(\"收到事件:\" + o, true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "                setLogText(\"收到异常:\" + e.toString(), true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"发送完毕\", true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnOnExceptionResumeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onExceptionResumeNext();
                code = "/**\n" +
                        "     * onExceptionResumeNext操作符\n" +
                        "     */\n" +
                        "    private void onExceptionResumeNext() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"onExceptionResumeNext操作符\",false);\n" +
                        "        setLogText(\"作用：\",false);\n" +
                        "        setLogText(\"遇到错误时，发送一个新的Observable\",false);\n" +
                        "        setLogText(\"注意：这里拦截的错误类型是Exception，如果拦截的错误类型是Thrwable，则会把事件发送到onError()\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"实例，发送1和一个Exception错误事件,拦截成功后，发送新的Observable(2,3)\",true);\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送1事件\",true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送Exception错误\",true);\n" +
                        "                emitter.onError(new Exception(\"Exception错误\"));\n" +
                        "            }\n" +
                        "        }).onExceptionResumeNext(new Observable<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            protected void subscribeActual(Observer observer) {\n" +
                        "                observer.onNext(2);\n" +
                        "                observer.onNext(3);\n" +
                        "                observer.onComplete();\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Object>() {\n" +
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
                        "                setLogText(\"收到异常:\" + e.toString(),true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"事件发送完毕\",true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnRetryUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryUntil();
                code = " /**\n" +
                        "     * retryUntil操作符\n" +
                        "     */\n" +
                        "    private void retryUntil() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"retryUntil操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"出现错误时，判断是否需要重新发送数据\",false);\n" +
                        "        setLogText(\"作用类似于retry(Predicate predicate)\",false);\n" +
                        "        setLogText(\"区别在于返回true停止继续发送数据,false则继续重试\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"实例：发送1，onError()事件\",true);\n" +
                        "        setLogText(\"重试2次\",true);\n" +
                        "        final int[] i = {0};\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送事件1\",true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送异常\",true);\n" +
                        "                emitter.onError(new Exception(\"发送错误了\"));\n" +
                        "            }\n" +
                        "        }).retryUntil(new BooleanSupplier() {\n" +
                        "            @Override\n" +
                        "            public boolean getAsBoolean() throws Exception {\n" +
                        "                // 返回true则停止继续重试,返回false则继续重试\n" +
                        "                i[0]++;\n" +
                        "                if (i[0] == 3) {\n" +
                        "                    setLogText(\"停止重试\",true);\n" +
                        "                    return true;\n" +
                        "                } else {\n" +
                        "                    setLogText(\"重试:\" + i[0],true);\n" +
                        "                    return false;\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Object>() {\n" +
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
                        "                setLogText(\"收到异常:\" + e.toString(),true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"发送完毕\",true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnRetryWhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryWhen();
                code = "/**\n" +
                        "     * retryWhen操作符\n" +
                        "     */\n" +
                        "    private void retryWhen() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"retryWhen操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"遇到错误时,将错误传递给新的被观察者，并决定是否需要重新发送事件\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"实例:发送1,error事件\",true);\n" +
                        "        final int[] i = {0};\n" +
                        "        Observable.create(new ObservableOnSubscribe<Object>() {\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {\n" +
                        "                setLogText(\"发送1事件\",true);\n" +
                        "                emitter.onNext(1);\n" +
                        "                setLogText(\"发送错误\",true);\n" +
                        "                emitter.onError(new Throwable(\"发生了错误\"));\n" +
                        "            }\n" +
                        "        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {\n" +
                        "            @Override\n" +
                        "            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {\n" +
                        "                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {\n" +
                        "                    @Override\n" +
                        "                    public ObservableSource<?> apply(Throwable throwable) throws Exception {\n" +
                        "                        setLogText(\"拦截到错误:\" + throwable.toString(),true);\n" +
                        "                        // 此时，若返回error事件，则不重新发送事件,error事件可以被onError()捕获\n" +
                        "                        // 若返回complete事件，也不重试，但不会被onComplete()捕获\n" +
                        "                        // 若返回next事件，则继续发送\n" +
                        "                        i[0]++;\n" +
                        "                        setLogText(\"重试次数:\" + i[0],true);\n" +
                        "                        if (i[0] == 3) {\n" +
                        "                            setLogText(\"不重试\",true);\n" +
                        "                            return Observable.error(new Throwable(\"发生了新的错误事件\"));\n" +
                        "//                            return Observable.empty();\n" +
                        "                        } else {\n" +
                        "                            setLogText(\"继续重试\",true);\n" +
                        "                            return Observable.just(2);\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                });\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Object>() {\n" +
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
                        "                setLogText(\"收到异常事件:\" + e.toString(),true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"发送完毕\",true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeat();
                code = " /**\n" +
                        "     * repeat操作符\n" +
                        "     */\n" +
                        "    private void repeat() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"repeat操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"无条件重复发送被观察者事件\",false);\n" +
                        "        setLogText(\"repeat(long time) //重载方法,重复发送次数\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"实例，重复发送1，2事件3次\",true);\n" +
                        "        Observable.just(1,2)\n" +
                        "                .repeat(3)\n" +
                        "                .subscribe(new Observer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"订阅成功\",true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Integer integer) {\n" +
                        "                        setLogText(\"收到事件:\" + integer,true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"收到异常:\" + e.toString(),true);\n" +
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
        setLogText("subscribe操作符", false);
        setLogText("作用:", false);
        setLogText("订阅,即连接被观察者和观察者", false);
        setLogText("只有被观察者订阅了观察者,被观察者发送的事件才能被观察者接收", false);
        setLogText("**********************************", false);
        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                // 发送事件
                setLogText("发送事件1", true);
                emitter.onNext("事件1");
                // 发送完成
                setLogText("发送完成事件", true);
                emitter.onComplete();
                // 发送异常
//                emitter.onError(new RuntimeException("new exception"));
            }
        });
        // 开始订阅
        setLogText("开始订阅", true);
        observable.subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                // 订阅了就会回调到这里
                setLogText("收到订阅回调", true);
            }

            @Override
            public void onNext(Object o) {
                // 收到的事件会回调到这里
                setLogText("收到事件:" + o, true);
            }

            @Override
            public void onError(Throwable e) {
                // 发送事件过程出现异常会回调到这里
                setLogText("收到异常:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("收到发送事件完成回调", true);
            }
        });
    }

    /**
     * subScribeOn、observeOn操作符
     */
    private void subscribeOn() {
        tvLog.setText("");
        setLogText("subScribeOn/observeOn操作符", false);
        setLogText("作用:", false);
        setLogText("指定被观察者(observable)/观察者(observer)的工作线程类型", false);
        setLogText("subscribeOn()多次调用时,只有第一次指定有效，其余的指定线程都无效", false);
        setLogText("observeOn()多次调用时,调用一次，就会进行一次线程的切换", false);
        setLogText("Rxjava内置了多个用于调度的线程类型,如下:", false);
        setLogText("Schedulers.immediate(),不指定线程", false);
        setLogText("AndroidSchedulers.mainThread(),主线程", false);
        setLogText("Schedulers.newThread(),常规新线程", false);
        setLogText("Schedulers.io(),io操作线程", false);
        setLogText("Schedulers.computation(),CPU计算操作线程", false);
        setLogText("**********************************", false);
        setLogText("示例", true);
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                String currentThread = Thread.currentThread().getName();
                setLogText("发送事件1,2:" + currentThread, true);
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
                        setLogText("收到订阅回调:" + currentThread, true);
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
                        setLogText("发送事件完成：" + currentThread, true);
                    }
                });
    }

    /**
     * delay操作符
     */
    private void delay() {
        tvLog.setText("");
        setLogText("delay操作符", false);
        setLogText("作用:", false);
        setLogText("使被观察者延迟一段时间再发送事件", false);
        setLogText("delay()具有多个重载方法", false);
        setLogText("// 参数1 = 时间 参数2 = 时间单位", false);
        setLogText("delay(long delay, TimeUint unit)", false);
        setLogText("// 参数1 = 时间 参数2 = 时间单位 参数3 = 线程调度器", false);
        setLogText("delay(long delay, TimeUint unit, Scheduler scheduler)", false);
        setLogText("// 参数1 = 时间 参数2 = 时间单位 参数3 = 错误延迟", false);
        setLogText("// 错误延迟指的是若存在Error事件，则如常执行，执行完成再抛异常", false);
        setLogText("delay(long delay, TimeUint unit, boolean delayError)", false);
        setLogText("// 参数1 = 时间 参数2 = 时间单位 参数3 = 线程调度器 参数4 = 错误延迟", false);
        setLogText("delay(long delay,TimeUnit unit,Scheduler scheduler, boolean delayError)", false);
        setLogText("**********************************", false);
        setLogText("示例,延迟3秒发送(1,2,3)事件", true);
        Observable.just(1, 2, 3)

                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("开始订阅", true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("收到事件:" + integer, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常事件:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送事件完毕", true);
                    }
                });
    }

    /**
     * do操作符
     */
    private void doOper() {
        tvLog.setText("");
        setLogText("do操作符", false);
        setLogText("作用:", false);
        setLogText("在某个事件的生命周期中调用", false);
        setLogText("类型:", false);
        setLogText("do()操作符有很多个类型,如doOnEach()/doOnNext()/doOnError()/doOnSubscribe()", false);
        setLogText("具体解释看源码部分和实例演示", false);
        setLogText("**********************************", false);
        setLogText("实例，发送1,2,3和一个error事件", true);
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
                        setLogText("doOnEach:" + value, true);
                    }
                })
                // 执行onNext()前会收到回调
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        setLogText("doOnNext:" + o, true);
                    }
                })
                // 执行onNext()后会收到回调
                .doAfterNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        setLogText("doAfterNext:" + o, true);
                    }
                })
                // Observable正常发送完事件后会收到回调
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        setLogText("doComplete", true);
                    }
                })
                // Observable发送错误事件时回调
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setLogText("doOnError:" + throwable.getMessage(), true);
                    }
                })
                // Observer订阅时回调
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        setLogText("doOnSubscribe", true);
                    }
                })
                // Observable发送事件完毕后调用，无论正常发送完毕/发生异常
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        setLogText("doAfterTerminate", true);
                    }
                })
                // 最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        setLogText("doFinally", true);
                    }
                })
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("订阅成功", true);
                    }

                    @Override
                    public void onNext(Object o) {
                        setLogText("收到事件:" + o, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常事件:" + e.getMessage(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送事件完毕", true);
                    }
                });
    }

    /**
     * onErrorReturn操作符
     */
    private void onErrorReturn() {
        tvLog.setText("");
        setLogText("onErrorReturn操作符", false);
        setLogText("作用：", false);
        setLogText("遇到错误时，发送一个特殊事件&正常终止", false);
        setLogText("**********************************", false);
        setLogText("实例，发送1和一个error事件", true);
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onError(new Throwable("错误事件1"));
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                setLogText("onErrorReturn:" + throwable.toString(), true);
                setLogText("发送新事件2", true);
                return 2;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功", true);
            }

            @Override
            public void onNext(Integer integer) {
                setLogText("收到事件:" + integer, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常回调:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("事件发送完毕", true);
            }
        });
    }

    /**
     * onErrorResumeNext操作符
     */
    private void onErrorResumeNext() {
        tvLog.setText("");
        setLogText("onErrorResumeNext操作符", false);
        setLogText("作用：", false);
        setLogText("遇到错误时，发送一个新的Observable", false);
        setLogText("注意：这里拦截的错误类型是throwable,如果拦截的错误类型是exception,则事件会发送到onError()", false);
        setLogText("**********************************", false);
        setLogText("实例，发送1和一个throwable错误事件,拦截成功后，发送新的Observable(2,3)", true);
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送1事件", true);
                emitter.onNext(1);
                setLogText("发送throwable错误", true);
                emitter.onError(new Throwable("throwable错误"));
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                setLogText("onErrorResumeNext:" + throwable.toString(), true);
                setLogText("发送新的Observable(2,3)", true);
                return Observable.just(2, 3);
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功", true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("收到事件:" + o, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("事件发送完毕", true);
            }
        });
    }

    /**
     * retry操作符
     */
    private void retry() {
        tvLog.setText("");
        setLogText("retry操作符", false);
        setLogText("作用:", false);
        setLogText("当出现错误时(即接收到onError()),让被观察者重新发送事件", false);
        setLogText("有5种重载方法:", false);
        setLogText("1.retry() //一直错误一直发", false);
        setLogText("2.retry(long time) //错误时重新发送数据(有次数(time)限制)", false);
        setLogText("3.retry(Perdicate predicate) //错误时根据判断逻辑(predicate)判断是否需要重新发送数据", false);
        setLogText("4.retry(new BiPredicate<Integer,Throwable>) //错误时根据判断逻辑(重试次数&错误信息)判断是否需要重新发送数据", false);
        setLogText("5.retry(long time,Predicate perdicate) //错误时根据判断逻辑(predicate)和重试次数(time)判断是否需要重新发送数", false);
        setLogText("**********************************", false);
        setLogText("要测试其他重载，可修改代码测试,默认测试第二种", false);
        setLogText("实例，发送1,和onError()事件，2事件", true);
        retry2();
    }

    private void retry1() {
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送事件1", true);
                emitter.onNext(1);
                setLogText("发送错误事件", true);
                emitter.onError(new Exception("发生错误了"));
                emitter.onNext(2);
            }
        }).retry()
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("订阅成功", true);
                    }

                    @Override
                    public void onNext(Object o) {
                        setLogText("收到事件:" + o, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕", true);
                    }
                });
    }

    private void retry2() {
        setLogText("重试3次", true);
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送事件1", true);
                emitter.onNext(1);
                setLogText("发送错误事件", true);
                emitter.onError(new Exception("发生错误了"));
                emitter.onNext(2);
            }
        }).retry(3)
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("订阅成功", true);
                    }

                    @Override
                    public void onNext(Object o) {
                        setLogText("收到事件:" + o, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕", true);
                    }
                });
    }

    private void retry3() {
        setLogText("拦截到异常不重试", true);
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送事件1", true);
                emitter.onNext(1);
                setLogText("发送错误事件", true);
                emitter.onError(new Exception("发生错误了"));
                emitter.onNext(2);
            }
        }).retry(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                setLogText("拦截到异常:" + throwable.toString(), true);
                setLogText("不重新发送数据，返回false", true);
                // 返回false不重试，返回true继续重试
                return false;
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功", true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("收到事件:" + o, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("发送完毕", true);
            }
        });
    }

    private void retry4() {
        setLogText("拦截到异常事件后，重试次数为2，则不重试", true);
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送事件1", true);
                emitter.onNext(1);
                setLogText("发送错误事件", true);
                emitter.onError(new Exception("发生错误了"));
                emitter.onNext(2);
            }
        }).retry(new BiPredicate<Integer, Throwable>() {
            @Override
            public boolean test(Integer integer, Throwable throwable) throws Exception {
                setLogText("重试次数=" + integer, true);
                setLogText("拦截异常:" + throwable.toString(), true);
                // 返回false不重试，返回true继续重试
                if (integer == 2) {
                    return false;
                } else {
                    return true;
                }
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功", true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("收到事件:" + o, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("发送完毕", true);
            }
        });
    }

    private void retry5() {
        setLogText("拦截到异常事件后，继续重试，重试次数为2，则不重试", true);
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送事件1", true);
                emitter.onNext(1);
                setLogText("发送错误事件", true);
                emitter.onError(new Exception("发生错误了"));
                emitter.onNext(2);
            }
        }).retry(2, new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                setLogText("拦截到异常:" + throwable.toString(), true);
                // 返回false不重试，返回true继续重试
                return true;
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功", true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("收到事件:" + o, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("发送完毕", true);
            }
        });
    }

    /**
     * onExceptionResumeNext操作符
     */
    private void onExceptionResumeNext() {
        tvLog.setText("");
        setLogText("onExceptionResumeNext操作符", false);
        setLogText("作用：", false);
        setLogText("遇到错误时，发送一个新的Observable", false);
        setLogText("注意：这里拦截的错误类型是Exception，如果拦截的错误类型是Thrwable，则会把事件发送到onError()", false);
        setLogText("**********************************", false);
        setLogText("实例，发送1和一个Exception错误事件,拦截成功后，发送新的Observable(2,3)", true);
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送1事件", true);
                emitter.onNext(1);
                setLogText("发送Exception错误", true);
                emitter.onError(new Exception("Exception错误"));
            }
        }).onExceptionResumeNext(new Observable<Object>() {

            @Override
            protected void subscribeActual(Observer observer) {
                setLogText("发送新的事件2,3", true);
                observer.onNext(2);
                observer.onNext(3);
                observer.onComplete();
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功", true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("收到事件:" + o, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("事件发送完毕", true);
            }
        });
    }

    /**
     * retryUntil操作符
     */
    private void retryUntil() {
        tvLog.setText("");
        setLogText("retryUntil操作符", false);
        setLogText("作用:", false);
        setLogText("出现错误时，判断是否需要重新发送数据", false);
        setLogText("作用类似于retry(Predicate predicate)", false);
        setLogText("区别在于返回true停止继续发送数据,false则继续重试", false);
        setLogText("**********************************", false);
        setLogText("实例：发送1，onError()事件", true);
        setLogText("重试2次", true);
        final int[] i = {0};
        Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送事件1", true);
                emitter.onNext(1);
                setLogText("发送异常", true);
                emitter.onError(new Exception("发送错误了"));
            }
        }).retryUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Exception {
                // 返回true则停止继续重试,返回false则继续重试
                i[0]++;
                if (i[0] == 3) {
                    setLogText("停止重试", true);
                    return true;
                } else {
                    setLogText("重试:" + i[0], true);
                    return false;
                }
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功", true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("收到事件:" + o, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("发送完毕", true);
            }
        });
    }

    /**
     * retryWhen操作符
     */
    private void retryWhen() {
        tvLog.setText("");
        setLogText("retryWhen操作符", false);
        setLogText("作用:", false);
        setLogText("遇到错误时,将错误传递给新的被观察者，并决定是否需要重新发送事件", false);
        setLogText("**********************************", false);
        setLogText("实例:发送1,error事件", true);
        final int[] i = {0};
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                setLogText("发送1事件", true);
                emitter.onNext(1);
                setLogText("发送错误", true);
                emitter.onError(new Throwable("发生了错误"));
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        setLogText("拦截到错误:" + throwable.toString(), true);
                        // 此时，若返回error事件，则不重新发送事件,error事件可以被onError()捕获
                        // 若返回complete事件，也不重试，但不会被onComplete()捕获
                        // 若返回next事件，则继续发送
                        i[0]++;
                        setLogText("重试次数:" + i[0], true);
                        if (i[0] == 3) {
                            setLogText("不重试", true);
                            return Observable.error(new Throwable("发生了新的错误事件"));
//                            return Observable.empty();
                        } else {
                            setLogText("继续重试", true);
                            return Observable.just(2);
                        }
                    }
                });
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功", true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("收到事件:" + o, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常事件:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("发送完毕", true);
            }
        });
    }

    /**
     * repeat操作符
     */
    private void repeat() {
        tvLog.setText("");
        setLogText("repeat操作符", false);
        setLogText("作用:", false);
        setLogText("无条件重复发送被观察者事件", false);
        setLogText("repeat(long time) //重载方法,重复发送次数", false);
        setLogText("**********************************", false);
        setLogText("实例，重复发送1，2事件3次", true);
        Observable.just(1, 2)
                .repeat(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("订阅成功", true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("收到事件:" + integer, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕", true);
                    }
                });
    }

    /**
     * repeatWhen操作符
     */
    private void repeatWhen() {
        tvLog.setText("");
        setLogText("repeatWhen操作符", false);
        setLogText("作用:", false);
        setLogText("有条件地重复发生被观察者的事件", false);
        setLogText("原理:", false);
        setLogText("将原被观察者停止发送事件的标识(onError/onComplete)转换成一个Object类型的数据传递给新被观察者", false);
        setLogText("若新的被观察者返回1个complete/error事件，则不重新订阅&不发送原来的事件", false);
        setLogText("若新的被观察者返回其他事件,则重新订阅&发送", false);
        setLogText("**********************************", false);
        Observable.just(1, 2)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {

                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                return null;
                            }
                        });
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
