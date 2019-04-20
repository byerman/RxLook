package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huaweisoft.retrofitdemo.R;
import com.huaweisoft.retrofitdemo.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateOperActivity extends AppCompatActivity {

    private static final int STOP_COUNT = 5;
    private RelativeLayout rlytView;
    private RelativeLayout rlytCode;
    private Button btnCreate;
    private Button btnJust;
    private Button btnFromArray;
    private Button btnFromIterable;
    private Button btnDefer;
    private Button btnTimer;
    private Button btnInterval;
    private ImageButton ibtnClose;
    private ImageButton ibtnBack;
    private ImageButton ibtnCode;
    private TextView tvLog;
    private TextView tvCode;
    private String code;
    private List<Integer> deferData = new ArrayList<>();

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
        btnJust = findViewById(R.id.btn_just);
        btnFromArray = findViewById(R.id.btn_fromArray);
        btnFromIterable = findViewById(R.id.btn_fromIterable);
        btnDefer = findViewById(R.id.btn_defer);
        btnTimer = findViewById(R.id.btn_timer);
        btnInterval = findViewById(R.id.btn_interval);
        ibtnBack = findViewById(R.id.btn_back);
        ibtnClose = findViewById(R.id.btn_close);
        ibtnCode = findViewById(R.id.ibtn_code);
        tvLog = findViewById(R.id.tv_log);
        tvCode = findViewById(R.id.tv_code);
    }

    private void initListener() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
                code = "Observable.create(new ObservableOnSubscribe<Integer>() {\n" +
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
                        "        });";
            }
        });
        btnJust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                just();
                code = "private void just() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        Observable.just(1,2,3,4)\n" +
                        "                .subscribeOn(Schedulers.io())\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Observer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"Just操作符用于快速创建&发送事件\");\n" +
                        "                        setLogText(\"作用\");\n" +
                        "                        setLogText(\"1.快速创建1个被观察者对象\");\n" +
                        "                        setLogText(\"2.发送事件的特点：直接发送传入的事件\");\n" +
                        "                        setLogText(\"最多只能发送10个参数\");\n" +
                        "                        setLogText(\"**********************************\");\n" +
                        "                        setLogText(\"开始使用subscribe连接\");\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Integer integer) {\n" +
                        "                        setLogText(\"收到事件:\" + integer);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"发送过程抛出异常,对Error事件响应:\" + e.toString());\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完毕,对Complete事件响应\");\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        btnFromArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromArray();
                code = " private void fromArray() {\n" +
                        "        // 要发送的数组，会把数组内容遍历然后逐个发送\n" +
                        "        Integer[] items = new Integer[]{1, 2, 3, 4};\n" +
                        "        Observable.fromArray(items)\n" +
                        "                .subscribeOn(Schedulers.io()) // 在子线程发送事件\n" +
                        "                .observeOn(AndroidSchedulers.mainThread()) // 在主线程接收事件\n" +
                        "                .subscribe(new Observer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"fromArray操作符\", false);\n" +
                        "                        setLogText(\"作用\",false);\n" +
                        "                        setLogText(\"1.快速创建1个被观察者对象\",false);\n" +
                        "                        setLogText(\"2.把数组的内容遍历然后发送\",false);\n" +
                        "                        setLogText(\"3.可以发送10个以上的数据\",false);\n" +
                        "                        setLogText(\"**********************************\", false);\n" +
                        "                        setLogText(\"开始使用subscribe连接\", true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Integer integer) {\n" +
                        "                        setLogText(\"收到事件:\" + integer, true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"发送过程抛出异常,对Error事件响应:\" + e.toString(), true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完毕,对Complete事件响应\", true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        btnFromIterable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromIterable();
                code = "/**\n" +
                        "     * fromIterable操作符\n" +
                        "     */\n" +
                        "    private void fromIterable() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        // 创建一个集合List数据\n" +
                        "        List<Integer> list = new ArrayList<>();\n" +
                        "        list.add(1);\n" +
                        "        list.add(2);\n" +
                        "        list.add(3);\n" +
                        "        list.add(4);\n" +
                        "        // 通过fromInterable把集合中的数据发送出去\n" +
                        "        Observable.fromIterable(list)\n" +
                        "                .subscribeOn(Schedulers.io())\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Observer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"fromInterable操作符\", false);\n" +
                        "                        setLogText(\"作用\",false);\n" +
                        "                        setLogText(\"1.快速创建1个被观察者对象\",false);\n" +
                        "                        setLogText(\"2.把集合的内容遍历然后发送\",false);\n" +
                        "                        setLogText(\"3.可以发送10个以上的数据\",false);\n" +
                        "                        setLogText(\"**********************************\", false);\n" +
                        "                        setLogText(\"开始使用subscribe连接\", true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Integer integer) {\n" +
                        "                        setLogText(\"收到事件:\" + integer, true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"发送过程抛出异常,对Error事件响应:\" + e.toString(), true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完毕,对Complete事件响应\", true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        btnDefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defer();
                code = "/**\n" +
                        "     * defer操作符\n" +
                        "     */\n" +
                        "    private void defer() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        // 第一次赋值\n" +
                        "        deferData.add(1);\n" +
                        "        deferData.add(2);\n" +
                        "        deferData.add(3);\n" +
                        "        deferData.add(4);\n" +
                        "        Observable.defer(new Callable<ObservableSource<? extends Integer>>() {\n" +
                        "            @Override\n" +
                        "            public ObservableSource<? extends Integer> call() throws Exception {\n" +
                        "                // 订阅被观察者时收到该回调\n" +
                        "                // 对deferData数据进行变换\n" +
                        "                for (Integer i : deferData) {\n" +
                        "                    i++;\n" +
                        "                }\n" +
                        "                return Observable.fromIterable(deferData);\n" +
                        "            }\n" +
                        "        }).subscribe(new Observer<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "                setLogText(\"defer操作符\",false);\n" +
                        "                setLogText(\"作用\",false);\n" +
                        "                setLogText(\"直到有观察者订阅时,才动态创建被观察者对象并发送事件\",false);\n" +
                        "                setLogText(\"应用场景\",false);\n" +
                        "                setLogText(\"需要动态创建被观察者并且获取最新的被观察者对象数据\",false);\n" +
                        "                setLogText(\"**********************************\", false);\n" +
                        "                setLogText(\"开始使用subscribe连接\", true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Integer integer) {\n" +
                        "                setLogText(\"收到事件:\" + integer, true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "                setLogText(\"发送过程抛出异常,对Error事件响应:\" + e.toString(), true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onComplete() {\n" +
                        "                setLogText(\"发送完毕,对Complete事件响应\", true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer();
                code = "/**\n" +
                        "     * timer操作符\n" +
                        "     */\n" +
                        "    private void timer() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"timer操作符\",false);\n" +
                        "        setLogText(\"作用\",false);\n" +
                        "        setLogText(\"1.快速创建一个被观察者对象\",false);\n" +
                        "        setLogText(\"2.延迟指定的时间后，发送一个数值0\",false);\n" +
                        "        setLogText(\"本质:延迟指定的时候后，调用一次onNext(0)\",false);\n" +
                        "        setLogText(\"应用场景\",false);\n" +
                        "        setLogText(\"延迟指定时间，进行相关操作\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        Observable.timer(2, TimeUnit.SECONDS)\n" +
                        "                .subscribeOn(Schedulers.io())   // 在子线程发送事件\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())  // 在主线程接受事件\n" +
                        "                .subscribe(new Observer<Long>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"开始使用subscribe连接\", true);\n" +
                        "                        setLogText(\"等待两秒\",true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Long aLong) {\n" +
                        "                        setLogText(\"收到事件:\" + aLong, true);\n" +
                        "                        Toast.makeText(CreateOperActivity.this,\"开始执行\",Toast.LENGTH_SHORT).show();\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"发送过程抛出异常,对Error事件响应:\" + e.toString(), true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完毕,对Complete事件响应\", true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        btnInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interval();
                code = "/**\n" +
                        "     * interval操作符\n" +
                        "     */\n" +
                        "    private void interval() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"interval操作符\",false);\n" +
                        "        setLogText(\"作用\",false);\n" +
                        "        setLogText(\"1.快速创建一个被观察者\",false);\n" +
                        "        setLogText(\"2.发送事件的特点：每隔指定的时间，就发送一次事件\",false);\n" +
                        "        setLogText(\"3.发送事件的序列从0开始递增，每次加1，无限循环\",false);\n" +
                        "        setLogText(\"interval默认在computation调度器上执行,也可以自定义线程调度器：interval(long,timeUnit,Scheduler)\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        // 第一个参数，起始延时时间\n" +
                        "        // 第二个参数，下个事件发送延时\n" +
                        "        // 第三个参数，时间单位\n" +
                        "        final int[] count = {0};\n" +
                        "        final Disposable[] disposable = new Disposable[1];\n" +
                        "        Observable.interval(3,1,TimeUnit.SECONDS)\n" +
                        "                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理事件\n" +
                        "                .subscribe(new Observer<Long>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        disposable[0] = d;\n" +
                        "                        setLogText(\"开始使用subscribe连接,循环5次\", true);\n" +
                        "                        setLogText(\"初始延时3秒\",true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Long aLong) {\n" +
                        "                        setLogText(\"接收到事件:\" + aLong,true);\n" +
                        "                        count[0]++;\n" +
                        "                        if (count[0] == STOP_COUNT) {\n" +
                        "                            setLogText(\"结束循环\",true);\n" +
                        "                            this.onComplete();\n" +
                        "                            disposable[0].dispose();\n" +
                        "                        } else {\n" +
                        "                            setLogText(\"等待1秒\",true);\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"发送过程抛出异常,对Error事件响应:\" + e.toString(), true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完毕,对Complete事件响应\", true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ibtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlytCode.setVisibility(View.GONE);
                rlytView.setVisibility(View.VISIBLE);
            }
        });
        ibtnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCode();
            }
        });
        ibtnCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(CreateOperActivity.this, "查看源码", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    /**
     * create操作
     */
    private void create() {
        tvLog.setText("");
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
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
                        setLogText("Create操作符是RxJava中最基本的操作符", false);
                        setLogText("**********************************", false);
                        setLogText("开始订阅事件流", true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("收到事件:" + integer, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到错误:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("完成事件流传输", true);
                    }
                });
    }

    /**
     * just操作
     */
    private void just() {
        tvLog.setText("");
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("Just操作符用于快速创建&发送事件", false);
                        setLogText("作用", false);
                        setLogText("1.快速创建1个被观察者对象", false);
                        setLogText("2.发送事件的特点：直接发送传入的事件", false);
                        setLogText("最多只能发送10个参数", false);
                        setLogText("**********************************", false);
                        setLogText("开始使用subscribe连接", true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("收到事件:" + integer, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("发送过程抛出异常,对Error事件响应:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕,对Complete事件响应", true);
                    }
                });
    }

    /**
     * fromArray操作
     */
    private void fromArray() {
        tvLog.setText("");
        // 要发送的数组，会把数组内容遍历然后逐个发送
        Integer[] items = new Integer[]{1, 2, 3, 4};
        Observable.fromArray(items)
                .subscribeOn(Schedulers.io()) // 在子线程发送事件
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程接收事件
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("fromArray操作符", false);
                        setLogText("作用",false);
                        setLogText("1.快速创建1个被观察者对象",false);
                        setLogText("2.把数组的内容遍历然后发送",false);
                        setLogText("3.可以发送10个以上的数据",false);
                        setLogText("**********************************", false);
                        setLogText("开始使用subscribe连接", true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("收到事件:" + integer, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("发送过程抛出异常,对Error事件响应:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕,对Complete事件响应", true);
                    }
                });
    }

    /**
     * fromIterable操作符
     */
    private void fromIterable() {
        tvLog.setText("");
        // 创建一个集合List数据
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        // 通过fromInterable把集合中的数据发送出去
        Observable.fromIterable(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("fromInterable操作符", false);
                        setLogText("作用",false);
                        setLogText("1.快速创建1个被观察者对象",false);
                        setLogText("2.把集合的内容遍历然后发送",false);
                        setLogText("3.可以发送10个以上的数据",false);
                        setLogText("**********************************", false);
                        setLogText("开始使用subscribe连接", true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("收到事件:" + integer, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("发送过程抛出异常,对Error事件响应:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕,对Complete事件响应", true);
                    }
                });
    }

    /**
     * defer操作符
     */
    private void defer() {
        tvLog.setText("");
        // 第一次赋值
        deferData.add(1);
        deferData.add(2);
        deferData.add(3);
        deferData.add(4);
        Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                // 订阅被观察者时收到该回调
                // 对deferData数据进行变换
                for (int i = 0; i < deferData.size(); i++) {
                    deferData.set(i,deferData.get(i)+1);
                }
                return Observable.fromIterable(deferData);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                setLogText("defer操作符",false);
                setLogText("作用",false);
                setLogText("直到有观察者订阅时,才动态创建被观察者对象并发送事件",false);
                setLogText("应用场景",false);
                setLogText("需要动态创建被观察者并且获取最新的被观察者对象数据",false);
                setLogText("**********************************", false);
                setLogText("开始使用subscribe连接", true);
            }

            @Override
            public void onNext(Integer integer) {
                setLogText("收到事件:" + integer, true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("发送过程抛出异常,对Error事件响应:" + e.toString(), true);
            }

            @Override
            public void onComplete() {
                setLogText("发送完毕,对Complete事件响应", true);
            }
        });
    }

    /**
     * timer操作符
     */
    private void timer() {
        tvLog.setText("");
        setLogText("timer操作符",false);
        setLogText("作用",false);
        setLogText("1.快速创建一个被观察者对象",false);
        setLogText("2.延迟指定的时间后，发送一个数值0",false);
        setLogText("本质:延迟指定的时候后，调用一次onNext(0)",false);
        setLogText("应用场景",false);
        setLogText("延迟指定时间，进行相关操作",false);
        setLogText("**********************************", false);
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())   // 在子线程发送事件
                .observeOn(AndroidSchedulers.mainThread())  // 在主线程接受事件
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("开始使用subscribe连接", true);
                        setLogText("等待两秒",true);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setLogText("收到事件:" + aLong, true);
                        Toast.makeText(CreateOperActivity.this,"开始执行",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("发送过程抛出异常,对Error事件响应:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕,对Complete事件响应", true);
                    }
                });
    }

    /**
     * interval操作符
     */
    private void interval() {
        tvLog.setText("");
        setLogText("interval操作符",false);
        setLogText("作用",false);
        setLogText("1.快速创建一个被观察者",false);
        setLogText("2.发送事件的特点：每隔指定的时间，就发送一次事件",false);
        setLogText("3.发送事件的序列从0开始递增，每次加1，无限循环",false);
        setLogText("interval默认在computation调度器上执行,也可以自定义线程调度器：interval(long,timeUnit,Scheduler)",false);
        setLogText("**********************************", false);
        // 第一个参数，起始延时时间
        // 第二个参数，下个事件发送延时
        // 第三个参数，时间单位
        final int[] count = {0};
        final Disposable[] disposable = new Disposable[1];
        Observable.interval(3,1,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理事件
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable[0] = d;
                        setLogText("开始使用subscribe连接,循环5次", true);
                        setLogText("初始延时3秒",true);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setLogText("接收到事件:" + aLong,true);
                        count[0]++;
                        if (count[0] == STOP_COUNT) {
                            setLogText("结束循环",true);
                            this.onComplete();
                            disposable[0].dispose();
                        } else {
                            setLogText("等待1秒",true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("发送过程抛出异常,对Error事件响应:" + e.toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完毕,对Complete事件响应", true);
                    }
                });
    }

    /**
     * 设置日志显示
     *
     * @param tvStr    新增内容
     * @param needTime 是否需要显示时间
     */
    private void setLogText(String tvStr, boolean needTime) {
        String suffix = tvLog.getText() + "\n";
        if (needTime) {
            tvLog.setText(suffix + TimeUtil.getCurrentTimeStr() + " " + tvStr);
        } else {
            tvLog.setText(suffix + tvStr);
        }
    }

    private void showCode() {
        rlytView.setVisibility(View.GONE);
        rlytCode.setVisibility(View.VISIBLE);
        tvCode.setText(code);
    }

}
