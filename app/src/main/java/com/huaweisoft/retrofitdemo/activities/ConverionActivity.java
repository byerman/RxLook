package com.huaweisoft.retrofitdemo.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class ConverionActivity extends BaseOperActivity {

    private Button btnMap;
    private Button btnFlatMap;
    private Button btnConcatMap;
    private Button btnBuffer;
    private TextView tvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_conversion);
        initView();
        initListener();
    }

    private void initView() {
        btnMap = findViewById(R.id.btn_map);
        btnFlatMap = findViewById(R.id.btn_flatmap);
        btnConcatMap = findViewById(R.id.btn_concatmap);
        btnBuffer = findViewById(R.id.btn_buffer);
        tvLog = findViewById(R.id.tv_log);
    }

    private void initListener() {
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map();
                code = " /**\n" +
                        "     * map操作符\n" +
                        "     */\n" +
                        "    @SuppressLint(\"CheckResult\")\n" +
                        "    private void map() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"map操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"对被观察者发送的每1个事件都通过指定的函数处理，从而变换成另外一种事件\",false);\n" +
                        "        setLogText(\"应用场景:数据类型转换\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        Observable.just(1, 2, 3, 4)\n" +
                        "                .map(new Function<Integer, String>() {\n" +
                        "                    // 在此处进行事件变换\n" +
                        "                    @Override\n" +
                        "                    public String apply(Integer integer) throws Exception {\n" +
                        "                        setLogText(\"收到初始事件:\" + integer,true);\n" +
                        "                        String newEvent = \"新的事件:\" + integer;\n" +
                        "                        setLogText(\"进行事件变换,\" + newEvent,true);\n" +
                        "                        return newEvent;\n" +
                        "                    }\n" +
                        "                    // 此处Consumer效果和Observer类似,只关注于事件接收和错误处理\n" +
                        "                }).subscribe(new Consumer<String>() {  \n" +
                        "            @Override\n" +
                        "            public void accept(String s) throws Exception {\n" +
                        "                setLogText(\"收到事件:\" + s,true);\n" +
                        "            }\n" +
                        "        }, new Consumer<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Throwable throwable) throws Exception {\n" +
                        "                setLogText(\"发送数据过程出现异常:\" + throwable.toString(),true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnFlatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flatMap();
                code = "/**\n" +
                        "     * flapMap操作符\n" +
                        "     */\n" +
                        "    @SuppressLint(\"CheckResult\")\n" +
                        "    private void flatMap() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"flatMap操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"将被观察者发送的事件序列进行拆分&变换,再合并成一个新的事件序列，最后再进行发送\",false);\n" +
                        "        setLogText(\"原理:\",false);\n" +
                        "        setLogText(\"1.为事件序列中的每个事件都创建一个Obserable对象\",false);\n" +
                        "        setLogText(\"2.将对每个原始事件转换后的新事件都放入对应的Observable对象\",false);\n" +
                        "        setLogText(\"3.将新建的每个Observable都合并到一个新建的、总的Observabel对象\",false);\n" +
                        "        setLogText(\"4.新建的、总的Observable对象将新合并的事件序列发送给观察者\",false);\n" +
                        "        setLogText(\"应用场景:\",false);\n" +
                        "        setLogText(\"无序的将被观察者发送的事件序列进行变换\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"开始发送事件(1,2,3,4)\",true);\n" +
                        "        Observable.just(1,2,3,4)\n" +
                        "                .flatMap(new Function<Integer, ObservableSource<?>>() {\n" +
                        "                    @Override\n" +
                        "                    public ObservableSource<?> apply(Integer integer) throws Exception {\n" +
                        "                        setLogText(\"收到初始事件:\" + integer,true);\n" +
                        "                        List<String> newEvents = new ArrayList<>();\n" +
                        "                        for (int i = 0; i < 3; i++) {\n" +
                        "                            newEvents.add(\"事件:\" + integer + \"拆分后的新事件\" + i);\n" +
                        "                        }\n" +
                        "                        Observable observable = Observable.fromIterable(newEvents);\n" +
                        "                        // 这里添加了延时，测试无序性\n" +
                        "                        if (integer == 1) {\n" +
                        "                            observable = observable.delay(100,TimeUnit.MILLISECONDS);\n" +
                        "                        }\n" +
                        "                        return observable;\n" +
                        "                    }\n" +
                        "\n" +
                        "                })\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Consumer<Object>() {\n" +
                        "\n" +
                        "            @Override\n" +
                        "            public void accept(Object o) throws Exception {\n" +
                        "                setLogText(\"新事件:\" + o, true);\n" +
                        "            }\n" +
                        "        }, new Consumer<Throwable>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Throwable throwable) throws Exception {\n" +
                        "                setLogText(\"发送数据过程出现异常:\" + throwable.toString(),true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
            }
        });
        btnConcatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                concatMap();
                code = "/**\n" +
                        "     * concatMap操作符\n" +
                        "     */\n" +
                        "    @SuppressLint(\"CheckResult\")\n" +
                        "    private void concatMap() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"concatMap操作符\",false);\n" +
                        "        setLogText(\"作用与flatMap类似,都是将被观察者的事件序列拆分&变换\",false);\n" +
                        "        setLogText(\"不过flatMap的结果是无序的,concatMap的结果是有序的\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"开始发送事件(1,2,3,4)\",true);\n" +
                        "        Observable.just(1,2,3,4)\n" +
                        "                .flatMap(new Function<Integer, ObservableSource<?>>() {\n" +
                        "                    @Override\n" +
                        "                    public ObservableSource<?> apply(Integer integer) throws Exception {\n" +
                        "                        setLogText(\"收到初始事件:\" + integer,true);\n" +
                        "                        List<String> newEvents = new ArrayList<>();\n" +
                        "                        for (int i = 0; i < 3; i++) {\n" +
                        "                            newEvents.add(\"事件\" + integer + \"拆分的事件\" + i);\n" +
                        "                        }\n" +
                        "                        Observable observable = Observable.fromIterable(newEvents);\n" +
                        "                        // 添加延时测试无序性\n" +
                        "                        if (integer == 1) {\n" +
                        "                            observable = observable.delay(100,TimeUnit.MILLISECONDS);\n" +
                        "                        }\n" +
                        "                        return observable;\n" +
                        "                    }\n" +
                        "                }).observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Consumer<Object>() {\n" +
                        "                    // 结果应该是按照原顺序收到\n" +
                        "                    @Override\n" +
                        "                    public void accept(Object o) throws Exception {\n" +
                        "                        setLogText(\"新事件:\" + o, true);\n" +
                        "                    }\n" +
                        "                }, new Consumer<Throwable>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Throwable throwable) throws Exception {\n" +
                        "                        setLogText(\"发送数据过程出现异常:\" + throwable.toString(),true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
        btnBuffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buffer();
                code = "/**\n" +
                        "     * buffer操作符\n" +
                        "     */\n" +
                        "    @SuppressLint(\"CheckResult\")\n" +
                        "    private void buffer() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"buffer操作符\", false);\n" +
                        "        setLogText(\"作用:\", false);\n" +
                        "        setLogText(\"1.定期从被观察者需要发送的事件中获取一定数量的事件&放到缓存区,最终发送\", false);\n" +
                        "        setLogText(\"2.缓存被观察者发送的事件\", false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"要发送的事件(1,2,3,4)\", true);\n" +
                        "        Observable.just(1, 2, 3, 4)\n" +
                        "                // 第一个参数:缓存区大小，每次从被观察者获取的事件数量\n" +
                        "                // 步长:每次获取新事件的数量\n" +
                        "                .buffer(3, 1)\n" +
                        "                .subscribe(new Consumer<List<Integer>>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(List<Integer> integers) throws Exception {\n" +
                        "                        setLogText(\"缓冲区的事件数量:\" + integers.size(), true);\n" +
                        "                        for (Integer i : integers) {\n" +
                        "                            setLogText(\"事件:\" + i, true);\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                }, new Consumer<Throwable>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Throwable throwable) throws Exception {\n" +
                        "                        setLogText(\"发送数据过程出现异常:\" + throwable.toString(), true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
            }
        });
    }

    /**
     * map操作符
     */
    @SuppressLint("CheckResult")
    private void map() {
        tvLog.setText("");
        setLogText("map操作符", false);
        setLogText("作用:", false);
        setLogText("对被观察者发送的每1个事件都通过指定的函数处理，从而变换成另外一种事件", false);
        setLogText("应用场景:数据类型转换", false);
        setLogText("**********************************", false);
        Observable.just(1, 2, 3, 4)
                .map(new Function<Integer, String>() {
                    // 在此处进行事件变换
                    @Override
                    public String apply(Integer integer) throws Exception {
                        setLogText("收到初始事件:" + integer, true);
                        String newEvent = "新的事件:" + integer;
                        setLogText("进行事件变换," + newEvent, true);
                        return newEvent;
                    }
                    // 此处Consumer效果和Observer类似,只关注于事件接收和错误处理
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                setLogText("收到事件:" + s, true);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                setLogText("发送数据过程出现异常:" + throwable.toString(), true);
            }
        });
    }

    /**
     * flapMap操作符
     */
    @SuppressLint("CheckResult")
    private void flatMap() {
        tvLog.setText("");
        setLogText("flatMap操作符", false);
        setLogText("作用:", false);
        setLogText("将被观察者发送的事件序列进行拆分&变换,再合并成一个新的事件序列，最后再进行发送", false);
        setLogText("原理:", false);
        setLogText("1.为事件序列中的每个事件都创建一个Obserable对象", false);
        setLogText("2.将对每个原始事件转换后的新事件都放入对应的Observable对象", false);
        setLogText("3.将新建的每个Observable都合并到一个新建的、总的Observabel对象", false);
        setLogText("4.新建的、总的Observable对象将新合并的事件序列发送给观察者", false);
        setLogText("应用场景:", false);
        setLogText("无序的将被观察者发送的事件序列进行变换", false);
        setLogText("**********************************", false);
        setLogText("开始发送事件(1,2,3,4)", true);
        Observable.just(1, 2, 3, 4)
                .flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        setLogText("收到初始事件:" + integer, true);
                        List<String> newEvents = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            newEvents.add("事件:" + integer + "拆分后的新事件" + i);
                        }
                        Observable observable = Observable.fromIterable(newEvents);
                        // 这里添加了延时，测试无序性
                        if (integer == 1) {
                            observable = observable.delay(100, TimeUnit.MILLISECONDS);
                        }
                        return observable;
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        setLogText("新事件:" + o, true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setLogText("发送数据过程出现异常:" + throwable.toString(), true);
                    }
                });
    }

    /**
     * concatMap操作符
     */
    @SuppressLint("CheckResult")
    private void concatMap() {
        tvLog.setText("");
        setLogText("concatMap操作符", false);
        setLogText("作用与flatMap类似,都是将被观察者的事件序列拆分&变换", false);
        setLogText("不过flatMap的结果是无序的,concatMap的结果是有序的", false);
        setLogText("**********************************", false);
        setLogText("开始发送事件(1,2,3,4)", true);
        Observable.just(1, 2, 3, 4)
                .concatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        setLogText("收到初始事件:" + integer, true);
                        List<String> newEvents = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            newEvents.add("事件" + integer + "拆分的事件" + i);
                        }
                        Observable observable = Observable.fromIterable(newEvents);
                        // 添加延时测试无序性
                        if (integer == 1) {
                            observable = observable.delay(100, TimeUnit.MILLISECONDS);
                        }
                        return observable;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    // 结果应该是按照原顺序收到
                    @Override
                    public void accept(Object o) throws Exception {
                        setLogText("新事件:" + o, true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setLogText("发送数据过程出现异常:" + throwable.toString(), true);
                    }
                });
    }

    /**
     * buffer操作符
     */
    @SuppressLint("CheckResult")
    private void buffer() {
        tvLog.setText("");
        setLogText("buffer操作符", false);
        setLogText("作用:", false);
        setLogText("1.定期从被观察者需要发送的事件中获取一定数量的事件&放到缓存区,最终发送", false);
        setLogText("2.缓存被观察者发送的事件", false);
        setLogText("**********************************", false);
        setLogText("要发送的事件(1,2,3,4)", true);
        Observable.just(1, 2, 3, 4)
                // 第一个参数:缓存区大小，每次从被观察者获取的事件数量
                // 步长:每次获取新事件的数量
                .buffer(3, 1)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        setLogText("缓冲区的事件数量:" + integers.size(), true);
                        for (Integer i : integers) {
                            setLogText("事件:" + i, true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setLogText("发送数据过程出现异常:" + throwable.toString(), true);
                    }
                });
    }

    @Override
    protected boolean isShowCodeIcon() {
        return true;
    }

    @Override
    protected String getTitleStr() {
        return getString(R.string.conversion_oper);
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
