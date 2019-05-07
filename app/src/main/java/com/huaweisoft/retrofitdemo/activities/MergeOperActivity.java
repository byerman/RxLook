package com.huaweisoft.retrofitdemo.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class MergeOperActivity extends BaseOperActivity {

    private Button btnConcat;
    private Button btnConcatArray;
    private Button btnMerge;
    private Button btnMergeArray;
    private Button btnConcatDelayError;
    private Button btnZip;
    private Button btnCombineLatest;
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
        btnConcatDelayError = findViewById(R.id.btn_concatDelayError);
        btnZip = findViewById(R.id.btn_zip);
        btnCombineLatest = findViewById(R.id.btn_combineLatest);
        tvLog = findViewById(R.id.tv_log);
    }

    private void initListener() {
        btnMerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merge();
                code = " /**\n" +
                        "     * merge操作符\n" +
                        "     */\n" +
                        "    private void merge() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"merge操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"组合多个(<=4个)被观察者一起发送数据，合并后按时间线并行执行\",false);\n" +
                        "        setLogText(\"区别于concat和concatArray操作符是合并后的执行顺序是并行的，即每个事件到达的顺序可能不同\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"合并两个被观察者数据，1个从0开始，每隔1秒发送1个数据,1个从2开始，每隔1秒发送1个数据，各自发送3个数据\",true);\n" +
                        "        Observable.merge(Observable.intervalRange(0,3,1,1, TimeUnit.SECONDS),\n" +
                        "                Observable.intervalRange(2,3,1,1,,TimeUnit.SECONDS))\n" +
                        "                .subscribe(new Observer<Long>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"开始采用subscribe连接\", true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Long aLong) {\n" +
                        "                        setLogText(\"接收到事件:\" + aLong,true);\n" +
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
        btnMergeArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mergeArray();
                code = "/**\n" +
                        "     * mergeArray操作符\n" +
                        "     */\n" +
                        "    private void mergeArray() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"mergeArray操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"组合多个(可以大于4个)(被观察者一起发送数据，合并后按时间线并行执行\",false);\n" +
                        "        setLogText(\"区别于concat和concatArray操作符是合并后的执行顺序是并行的，即每个事件到达的顺序可能不同\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"合并五个被观察者数据，分别从1,4,7,10,13开始,隔1秒发送1个事件，共发送3次事件\",false);\n" +
                        "        Observable.mergeArray(Observable.intervalRange(1,3,0,1,TimeUnit.SECONDS),\n" +
                        "                Observable.intervalRange(4,3,0,1,TimeUnit.SECONDS),\n" +
                        "                Observable.intervalRange(7,3,0,1,TimeUnit.SECONDS),\n" +
                        "                Observable.intervalRange(10,3,0,1,TimeUnit.SECONDS),\n" +
                        "                Observable.intervalRange(13,3,0,1,TimeUnit.SECONDS))\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Observer<Long>() {\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"开始采用subscribe连接\", true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Long aLong) {\n" +
                        "                        setLogText(\"接收到事件:\" + aLong,true);\n" +
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
                concatArray();
                code = " /**\n" +
                        "     * concatArray操作符\n" +
                        "     */\n" +
                        "    private void concatArray() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"concatArray操作符\",false);\n" +
                        "        setLogText(\"功能大体上和concat操作符一致,不同的地方在于可以组合的被观察者数量>4个\",false);\n" +
                        "        setLogText(\"concat操作符内部也是通过concatArray实现的\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"合并5个被观察者的数据,顺序为(1,2,3),(4,5,6),(7,8,9),(10,11,12),(13,14,15)\",true);\n" +
                        "        Observable.concatArray(Observable.just(1,2,3),\n" +
                        "                                Observable.just(4,5,6),\n" +
                        "                                Observable.just(7,8,9),\n" +
                        "                                Observable.just(10,11,12),\n" +
                        "                                Observable.just(13,14,15))\n" +
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
        btnConcatDelayError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                concatDelayErrorOrMergeDelayError();
                code = " /**\n" +
                        "     * concatArrayDelayError和MergeArrayDelayError操作符\n" +
                        "     */\n" +
                        "    @SuppressLint(\"CheckResult\")\n" +
                        "    private void concatDelayErrorOrMergeDelayError() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"concatArrayDelayError/MergeArrayDelayError操作符\",false);\n" +
                        "        setLogText(\"使用背景：\",false);\n" +
                        "        setLogText(\"使用concat/merge或者concatArray/mergeArray时,当一个被观察者发出error事件时,则后续被观察者的事件再也无法收到\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"使用该操作符，可以把onError事件推迟到其他被观察者事件发送结束后再触发\",false);\n" +
                        "        setLogText(\"区别:\",false);\n" +
                        "        setLogText(\"concatDelay/mergeDelay差别在于接受事件顺序不同，前者按顺序接收，后者无序接收\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"使用concatArrayDelayError演示,合并两个被观察者,一个发送(1,2,3后error),一个发送(4,5,6)\",false);\n" +
                        "        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {\n" +
                        "                emitter.onNext(1);\n" +
                        "                emitter.onNext(2);\n" +
                        "                emitter.onNext(3);\n" +
                        "                emitter.onError(new RuntimeException());\n" +
                        "            }\n" +
                        "        }),Observable.just(4,5,6)).subscribe(new Observer<Object>() {\n" +
                        "            @Override\n" +
                        "            public void onSubscribe(Disposable d) {\n" +
                        "                setLogText(\"开始采用subscribe连接\", true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onNext(Object o) {\n" +
                        "                setLogText(\"接收到事件:\" + o,true);\n" +
                        "            }\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void onError(Throwable e) {\n" +
                        "                setLogText(\"出现异常:\" + e.toString(),true);\n" +
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
        btnZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zip();
                code = "/**\n" +
                        "     * zip操作符\n" +
                        "     */\n" +
                        "    private void zip() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"zip操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"合并多个被观察者发送的事件，生成一个新的事件序列,并最终发送\",false);\n" +
                        "        setLogText(\"注意:\",false);\n" +
                        "        setLogText(\"1.事件组合方式=严格按照原先事件序列进行对位合并\",false);\n" +
                        "        setLogText(\"2.最终合并的事件数量=多个被观察者中最少的事件数量\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例:合并两个被观察者(1,2,3)和(4,5,6,7),最终收到的事件是(5,7,9)\",false);\n" +
                        "        Observable.zip(Observable.just(1, 2, 3)\n" +
                        "                        .subscribeOn(Schedulers.io()),\n" +
                        "                Observable.just(4, 5, 6, 7)\n" +
                        "                        .subscribeOn(Schedulers.newThread()), new BiFunction<Integer, Integer, Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public Integer apply(Integer integer, Integer integer2) throws Exception {\n" +
                        "                        setLogText(\"合并事件:\" + integer + \"+\" + integer2,true);\n" +
                        "                        return integer + integer2;\n" +
                        "                    }\n" +
                        "                }).subscribe(new Consumer<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Integer integer) throws Exception {\n" +
                        "                setLogText(\"最终收到事件:\" + integer, true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnCombineLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combineLatest();
                code = "/**\n" +
                        "     * combineLatest操作符\n" +
                        "     */\n" +
                        "    private void combineLatest() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"combineLatest操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"组合多个被观察者发送的事件，把先发送数据的那个被观察者最后发送的\" +\n" +
                        "                \"事件与其他被观察者发送的事件结合，最终发送组合后的数据\",false);\n" +
                        "        setLogText(\"简单来说，就是等到多个被观察者都发送一次事件后,每接收一次数据就取每个被观察者最新的事件组合一次\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例:有3个被观察者，第一个先发送了(1,2,3),第二个延时1秒发送了(0,1,2,3)，\" +\n" +
                        "                \"第三个延时2秒发送了(0,1,2,3)\",true);\n" +
                        "        Observable.combineLatest(\n" +
                        "                Observable.just(1, 2, 3),\n" +
                        "                Observable.intervalRange(0, 4, 1,1,TimeUnit.SECONDS),\n" +
                        "                Observable.intervalRange(0, 4, 2,1,TimeUnit.SECONDS),\n" +
                        "                new Function3<Integer, Long, Long, Long>() {\n" +
                        "                    @Override\n" +
                        "                    public Long apply(final Integer integer, final Long integer2, final Long integer3) throws Exception {\n" +
                        "                        runOnUiThread(new Runnable() {\n" +
                        "                            @Override\n" +
                        "                            public void run() {\n" +
                        "                                // 这里使用runnable会导致text显示顺序有问题\n" +
                        "                                setLogText(\"收到事件:\" + integer + \",\" + integer2 + \",\" + integer3,true);\n" +
                        "                            }\n" +
                        "                        });\n" +
                        "                        return integer + integer2 + integer3;\n" +
                        "                    }\n" +
                        "                }\n" +
                        "        ).observeOn(AndroidSchedulers.mainThread())\n" +
                        "         .subscribe(new Consumer<Long>() {\n" +
                        "             @Override\n" +
                        "             public void accept(Long aLong) throws Exception {\n" +
                        "                 setLogText(\"合并后的事件:\" + aLong,true);\n" +
                        "             }\n" +
                        "         });\n" +
                        "    }";
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
        setLogText("合并4个被观察者的数据,顺序为(1,2,3),(4,5,6),(7,8,9),(10,11,12)",false);
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
                        setLogText("接收到事件:" + integer,true);
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
        setLogText("concat操作符内部也是通过concatArray实现的",false);
        setLogText("**********************************", false);
        setLogText("合并5个被观察者的数据,顺序为(1,2,3),(4,5,6),(7,8,9),(10,11,12),(13,14,15)",false);
        Observable.concatArray(Observable.just(1,2,3),
                                Observable.just(4,5,6),
                                Observable.just(7,8,9),
                                Observable.just(10,11,12),
                                Observable.just(13,14,15))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("开始采用subscribe连接", true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("接收到事件:" + integer,true);
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
     * merge操作符
     */
    private void merge() {
        tvLog.setText("");
        setLogText("merge操作符",false);
        setLogText("作用:",false);
        setLogText("组合多个(<=4个)被观察者一起发送数据，合并后按时间线并行执行",false);
        setLogText("区别于concat和concatArray操作符是合并后的执行顺序是并行的，即每个事件到达的顺序可能不同",false);
        setLogText("**********************************", false);
        setLogText("合并两个被观察者数据，1个从0开始，每隔1秒发送1个数据,1个从2开始，每隔1秒发送1个数据，各自发送3个数据",false);
        Observable.merge(Observable.intervalRange(0,3,1,1, TimeUnit.SECONDS),
                Observable.intervalRange(2,3,1,1,TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("开始采用subscribe连接", true);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setLogText("接收到事件:" + aLong,true);
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
     * mergeArray操作符
     */
    private void mergeArray() {
        tvLog.setText("");
        setLogText("mergeArray操作符",false);
        setLogText("作用:",false);
        setLogText("组合多个(可以大于4个)(被观察者一起发送数据，合并后按时间线并行执行",false);
        setLogText("区别于concat和concatArray操作符是合并后的执行顺序是并行的，即每个事件到达的顺序可能不同",false);
        setLogText("**********************************", false);
        setLogText("合并五个被观察者数据，分别从1,4,7,10,13开始,隔1秒发送1个事件，共发送3次事件",false);
        Observable.mergeArray(Observable.intervalRange(1,3,0,1,TimeUnit.SECONDS),
                Observable.intervalRange(4,3,0,1,TimeUnit.SECONDS),
                Observable.intervalRange(7,3,0,1,TimeUnit.SECONDS),
                Observable.intervalRange(10,3,0,1,TimeUnit.SECONDS),
                Observable.intervalRange(13,3,0,1,TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("开始采用subscribe连接", true);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setLogText("接收到事件:" + aLong,true);
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
     * concatArrayDelayError和MergeArrayDelayError操作符
     */
    @SuppressLint("CheckResult")
    private void concatDelayErrorOrMergeDelayError() {
        tvLog.setText("");
        setLogText("concatArrayDelayError/MergeArrayDelayError操作符",false);
        setLogText("使用背景：",false);
        setLogText("使用concat/merge或者concatArray/mergeArray时,当一个被观察者发出error事件时,则后续被观察者的事件再也无法收到",false);
        setLogText("作用:",false);
        setLogText("使用该操作符，可以把onError事件推迟到其他被观察者事件发送结束后再触发",false);
        setLogText("区别:",false);
        setLogText("concatDelay/mergeDelay差别在于接受事件顺序不同，前者按顺序接收，后者无序接收",false);
        setLogText("**********************************", false);
        setLogText("使用concatArrayDelayError演示,合并两个被观察者,一个发送(1,2,3后error),一个发送(4,5,6)",false);
        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new RuntimeException());
            }
        }),Observable.just(4,5,6)).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                setLogText("开始采用subscribe连接", true);
            }

            @Override
            public void onNext(Object o) {
                setLogText("接收到事件:" + o,true);
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
     * zip操作符
     */
    private void zip() {
        tvLog.setText("");
        setLogText("zip操作符",false);
        setLogText("作用:",false);
        setLogText("合并多个被观察者发送的事件，生成一个新的事件序列,并最终发送",false);
        setLogText("注意:",false);
        setLogText("1.事件组合方式=严格按照原先事件序列进行对位合并",false);
        setLogText("2.最终合并的事件数量=多个被观察者中最少的事件数量",false);
        setLogText("**********************************", false);
        setLogText("示例:合并两个被观察者(1,2,3)和(4,5,6,7),最终收到的事件是(5,7,9)",false);
        Observable.zip(Observable.just(1, 2, 3)
                        .subscribeOn(Schedulers.io()),
                Observable.just(4, 5, 6, 7)
                        .subscribeOn(Schedulers.newThread()), new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        setLogText("合并事件:" + integer + "+" + integer2,true);
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                setLogText("最终收到事件:" + integer, true);
            }
        });
    }

    /**
     * combineLatest操作符
     */
    private void combineLatest() {
        tvLog.setText("");
        setLogText("combineLatest操作符",false);
        setLogText("作用:",false);
        setLogText("组合多个被观察者发送的事件，把先发送数据的那个被观察者最后发送的" +
                "事件与其他被观察者发送的事件结合，最终发送组合后的数据",false);
        setLogText("简单来说，就是等到多个被观察者都发送一次事件后,每接收一次数据就取每个被观察者最新的事件组合一次",false);
        setLogText("**********************************", false);
        setLogText("示例:有3个被观察者，第一个先发送了(1,2,3),第二个延时1秒发送了(0,1,2,3)，" +
                "第三个延时2秒发送了(0,1,2,3)",true);
        Observable.combineLatest(
                Observable.just(1, 2, 3),
                Observable.intervalRange(0, 4, 1,1,TimeUnit.SECONDS),
                Observable.intervalRange(0, 4, 2,1,TimeUnit.SECONDS),
                new Function3<Integer, Long, Long, Long>() {
                    @Override
                    public Long apply(final Integer integer, final Long integer2, final Long integer3) throws Exception {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 这里使用runnable会导致text显示顺序有问题
                                setLogText("收到事件:" + integer + "," + integer2 + "," + integer3,true);
                            }
                        });
                        return integer + integer2 + integer3;
                    }
                }
        ).observeOn(AndroidSchedulers.mainThread())
         .subscribe(new Consumer<Long>() {
             @Override
             public void accept(Long aLong) throws Exception {
                 setLogText("合并后的事件:" + aLong,true);
             }
         });
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
