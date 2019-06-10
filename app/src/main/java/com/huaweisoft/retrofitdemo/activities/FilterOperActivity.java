package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;
import com.huaweisoft.retrofitdemo.view.PoPUpView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;


/**
 * @author baiaj
 * 过滤操作符演示界面
 */
public class FilterOperActivity extends BaseOperActivity implements PoPUpView.IPopUpClickListener {

    private PoPUpView mPopUpView;
    private List<String> firstDatas = new ArrayList<>();
    private List<List<String>> secondDatas = new ArrayList<>();
    private TextView tvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_filter);
        tvLog = findViewById(R.id.tv_log);
        initData();
        initPopUpView();
    }

    private void initData() {
        firstDatas.add("指定条件过滤");
        firstDatas.add("指定事件数量过滤");
        firstDatas.add("指定时间过滤");
        firstDatas.add("指定事件位置过滤");
        List<String> conditionDatas = new ArrayList<>();
        conditionDatas.add("filter");
        conditionDatas.add("ofType");
        conditionDatas.add("skip");
        conditionDatas.add("skipLast");
        conditionDatas.add("distinct");
        conditionDatas.add("distinctUntilChange");
        List<String> thingCountDatas = new ArrayList<>();
        thingCountDatas.add("take");
        thingCountDatas.add("takeLast");
        List<String> timeDatas = new ArrayList<>();
        timeDatas.add("throttleFirst");
        timeDatas.add("throttleLast");
        timeDatas.add("sample");
        timeDatas.add("throttleWithTimeOut");
        timeDatas.add("debounce");
        List<String> thingPositionDatas = new ArrayList<>();
        thingPositionDatas.add("firstElement");
        thingPositionDatas.add("lastElement");
        thingPositionDatas.add("elementAt");
        thingPositionDatas.add("elementAtOrError");
        secondDatas.add(conditionDatas);
        secondDatas.add(thingCountDatas);
        secondDatas.add(timeDatas);
        secondDatas.add(thingPositionDatas);
    }

    private void initPopUpView() {
        mPopUpView = findViewById(R.id.popview);
        mPopUpView.setFirstData(firstDatas);
        mPopUpView.setSecondData(secondDatas);
        mPopUpView.setPopUpClickListener(this);
    }

    /**
     * filter操作符
     */
    private void filter() {
        tvLog.setText("");
        setLogText("Filter操作符",false);
        setLogText("作用:",false);
        setLogText("过滤特定条件的事件",false);
        setLogText("根据test()返回的结果决定是否过滤",false);
        setLogText("返回true,继续发送,false则过滤",false);
        setLogText("**********************************", false);
        setLogText("示例：发送1,2,3,4,5五个事件并过滤大于3的事件",true);
        Observable.just(1,2,3,4,5)
                .filter(new Predicate<Integer>() {
                    // 根据test()的返回值 对被观察者发送的事件进行过滤 & 筛选
                    // a. 返回true，则继续发送
                    // b. 返回false，则不发送（即过滤）
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        setLogText("过滤前的事件:" + integer,true);
                        return integer <= 3;
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                setLogText("订阅成功",true);
            }

            @Override
            public void onNext(Integer integer) {
                setLogText("过滤后的事件:" + integer,true);
            }

            @Override
            public void onError(Throwable e) {
                setLogText("收到异常:" + e.toString(),true);
            }

            @Override
            public void onComplete() {
                setLogText("发送完成",true);
            }
        });
    }

    /**
     * ofType操作符
     */
    private void ofType() {
        tvLog.setText("");
        setLogText("ofType操作符",false);
        setLogText("作用:",false);
        setLogText("过滤特定数据类型的事件",false);
        setLogText("**********************************", false);
        setLogText("示例，发送1,2,3，baiaj,hi五个事件并过滤出整型数据",true);
        Observable.just(1,2,3,"baiaj","hi")
                .ofType(Integer.class)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("订阅成功",true);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        setLogText("过滤后的事件:" + integer,true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常:" + e.toString(),true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完成",true);
                    }
                });
    }

    /**
     * skip操作符
     */
    private void skip() {
        tvLog.setText("");
        setLogText("skip操作符",false);
        setLogText("作用:",false);
        setLogText("跳过正序的某个事件",false);
        setLogText("skip(long n),跳过正序的前n个事件",false);
        setLogText("skip(long n,TimeUnits timeUnit),跳过前n个时间单位的事件",false);
        setLogText("**********************************", false);
//        setLogText("示例,发送1,2,3,4,5五个事件,过滤前3个事件",true);
        setLogText("示例，从0开始,间隔1s发送1个事件，共5个事件并过滤第一秒的事件",true);
//        Observable.just(1,2,3,4,5)
//                .skip(3)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        setLogText("订阅成功",true);
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        setLogText("过滤后的事件:" + integer,true);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        setLogText("收到异常:" + e.toString(),true);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        setLogText("发送完成",true);
//                    }
//                });
        Observable.intervalRange(0,5,0,1, TimeUnit.SECONDS)
                .skip(1,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("订阅成功",true);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setLogText("过滤后的事件:" + aLong,true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常:" + e.toString(),true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完成",true);
                    }
                });
    }

    /**
     * skipLast操作符
     */
    private void skipLast() {
        tvLog.setText("");
        setLogText("skipLast操作符",false);
        setLogText("作用:",false);
        setLogText("跳过反序的某个事件",false);
        setLogText("skipLast(long n),跳过反序的前n个事件",false);
        setLogText("skipLast(long n,TimeUnits timeUnit),跳过后n个时间单位的事件",false);
        setLogText("**********************************", false);
//        setLogText("示例,发送1,2,3,4,5五个事件,过滤后3个事件",true);
        setLogText("示例，从0开始,间隔1s发送1个事件，共5个事件并过滤最后一秒的事件",true);
//        Observable.just(1,2,3,4,5)
//                .skipLast(3)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        setLogText("订阅成功",true);
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        setLogText("过滤后的事件:" + integer,true);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        setLogText("收到异常:" + e.toString(),true);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        setLogText("发送完成",true);
//                    }
//                });
        Observable.intervalRange(0,5,0,1,TimeUnit.SECONDS)
                .skipLast(1,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        setLogText("订阅成功",true);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setLogText("过滤后的事件:" + aLong,true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLogText("收到异常:" + e.toString(),true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完成",true);
                    }
                });
    }

    /**
     * distinct操作符
     */
    private void distinct() {
        tvLog.setText("");
        setLogText("distinct操作符",false);
        setLogText("作用:",false);
        setLogText("过滤事件序列中重复的事件",false);
        setLogText("**********************************", false);
        setLogText("示例:发送1,2,3,1,2五个事件并过滤其中重复的事件",true);
        Observable.just(1,2,3,1,2)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        setLogText("过滤后的事件:" + integer,true);
                    }
                });
    }

    /**
     * distinctUntilChange操作符
     */
    private void distinctUntilChange() {
        tvLog.setText("");
        setLogText("distinctUntilChange操作符",false);
        setLogText("作用：",false);
        setLogText("过滤连续重复的事件",false);
        setLogText("**********************************", false);
        setLogText("示例：发送1,2,3,3,4,4,3七个事件并过滤掉连续重复的事件",true);
        Observable.just(1,2,3,3,4,4,3)
                .distinctUntilChanged()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        setLogText("过滤后的事件:" + integer,true);
                    }
                });
    }

    private void take() {

    }

    private void takeLast() {

    }

    private void throttleFirst() {

    }

    private void throttleLast() {

    }

    private void sample() {

    }

    private void throttleWithTimeOut() {

    }

    private void debounce() {

    }

    private void firstElement() {

    }

    private void lastElement() {

    }

    private void elementAt() {

    }

    private void elementAtOrError() {

    }

    @Override
    protected boolean isShowCodeIcon() {
        return true;
    }

    @Override
    protected String getTitleStr() {
        return getString(R.string.filter_oper);
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

    @Override
    public void onClick(int currentIndex, int position) {
        switch (currentIndex) {
            case 0:
                switch (position) {
                    case 0:
                        filter();
                        code = " /**\n" +
                                "     * filter操作符\n" +
                                "     */\n" +
                                "    private void filter() {\n" +
                                "        tvLog.setText(\"\");\n" +
                                "        setLogText(\"Filter操作符\",false);\n" +
                                "        setLogText(\"作用:\",false);\n" +
                                "        setLogText(\"过滤特定条件的事件\",false);\n" +
                                "        setLogText(\"根据test()返回的结果决定是否过滤\",false);\n" +
                                "        setLogText(\"返回true,继续发送,false则过滤\",false);\n" +
                                "        setLogText(\"**********************************\", false);\n" +
                                "        setLogText(\"示例：发送1,2,3,4,5五个事件并过滤大于3的事件\",true);\n" +
                                "        Observable.just(1,2,3,4,5)\n" +
                                "                .filter(new Predicate<Integer>() {\n" +
                                "                    // 根据test()的返回值 对被观察者发送的事件进行过滤 & 筛选\n" +
                                "                    // a. 返回true，则继续发送\n" +
                                "                    // b. 返回false，则不发送（即过滤）\n" +
                                "                    @Override\n" +
                                "                    public boolean test(Integer integer) throws Exception {\n" +
                                "                        setLogText(\"过滤前的事件:\" + integer,true);\n" +
                                "                        return integer <= 3;\n" +
                                "                    }\n" +
                                "                }).subscribe(new Observer<Integer>() {\n" +
                                "            @Override\n" +
                                "            public void onSubscribe(Disposable d) {\n" +
                                "                setLogText(\"订阅成功\",true);\n" +
                                "            }\n" +
                                "\n" +
                                "            @Override\n" +
                                "            public void onNext(Integer integer) {\n" +
                                "                setLogText(\"过滤后的事件:\" + integer,true);\n" +
                                "            }\n" +
                                "\n" +
                                "            @Override\n" +
                                "            public void onError(Throwable e) {\n" +
                                "                setLogText(\"收到异常:\" + e.toString(),true);\n" +
                                "            }\n" +
                                "\n" +
                                "            @Override\n" +
                                "            public void onComplete() {\n" +
                                "                setLogText(\"发送完成\",true);\n" +
                                "            }\n" +
                                "        });\n" +
                                "    }";
                        break;
                    case 1:
                        ofType();
                        code = "/**\n" +
                                "     * ofType操作符\n" +
                                "     */\n" +
                                "    private void ofType() {\n" +
                                "        tvLog.setText(\"\");\n" +
                                "        setLogText(\"ofType操作符\",false);\n" +
                                "        setLogText(\"作用:\",false);\n" +
                                "        setLogText(\"过滤特定数据类型的事件\",false);\n" +
                                "        setLogText(\"**********************************\", false);\n" +
                                "        setLogText(\"示例，发送1,2,3，baiaj,hi五个事件并过滤出整型数据\",true);\n" +
                                "        Observable.just(1,2,3,\"baiaj\",\"hi\")\n" +
                                "                .ofType(Integer.class)\n" +
                                "                .subscribe(new Observer<Integer>() {\n" +
                                "                    @Override\n" +
                                "                    public void onSubscribe(Disposable d) {\n" +
                                "                        setLogText(\"订阅成功\",true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onNext(Integer integer) {\n" +
                                "                        setLogText(\"过滤后的事件:\" + integer,true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onError(Throwable e) {\n" +
                                "                        setLogText(\"收到异常:\" + e.toString(),true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onComplete() {\n" +
                                "                        setLogText(\"发送完成\",true);\n" +
                                "                    }\n" +
                                "                });\n" +
                                "    }";
                        break;
                    case 2:
                        skip();
                        code = "/**\n" +
                                "     * skip操作符\n" +
                                "     */\n" +
                                "    private void skip() {\n" +
                                "        tvLog.setText(\"\");\n" +
                                "        setLogText(\"skip操作符\",false);\n" +
                                "        setLogText(\"作用:\",false);\n" +
                                "        setLogText(\"跳过正序的某个事件\",false);\n" +
                                "        setLogText(\"skip(long n),跳过正序的前n个事件\",false);\n" +
                                "        setLogText(\"skip(long n,TimeUnits timeUnit),跳过前n个时间单位的事件\",false);\n" +
                                "        setLogText(\"**********************************\", false);\n" +
                                "//        setLogText(\"示例,发送1,2,3,4,5五个事件,过滤前3个事件\",true);\n" +
                                "        setLogText(\"示例，从0开始,间隔1s发送1个事件，共5个事件并过滤第一秒的事件\",true);\n" +
                                "//        Observable.just(1,2,3,4,5)\n" +
                                "//                .skip(3)\n" +
                                "//                .subscribe(new Observer<Integer>() {\n" +
                                "//                    @Override\n" +
                                "//                    public void onSubscribe(Disposable d) {\n" +
                                "//                        setLogText(\"订阅成功\",true);\n" +
                                "//                    }\n" +
                                "//\n" +
                                "//                    @Override\n" +
                                "//                    public void onNext(Integer integer) {\n" +
                                "//                        setLogText(\"过滤后的事件:\" + integer,true);\n" +
                                "//                    }\n" +
                                "//\n" +
                                "//                    @Override\n" +
                                "//                    public void onError(Throwable e) {\n" +
                                "//                        setLogText(\"收到异常:\" + e.toString(),true);\n" +
                                "//                    }\n" +
                                "//\n" +
                                "//                    @Override\n" +
                                "//                    public void onComplete() {\n" +
                                "//                        setLogText(\"发送完成\",true);\n" +
                                "//                    }\n" +
                                "//                });\n" +
                                "        Observable.intervalRange(0,5,0,1, TimeUnit.SECONDS)\n" +
                                "                .skip(1,TimeUnit.SECONDS)\n" +
                                "                .observeOn(AndroidSchedulers.mainThread())\n" +
                                "                .subscribe(new Observer<Long>() {\n" +
                                "                    @Override\n" +
                                "                    public void onSubscribe(Disposable d) {\n" +
                                "                        setLogText(\"订阅成功\",true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onNext(Long aLong) {\n" +
                                "                        setLogText(\"过滤后的事件:\" + aLong,true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onError(Throwable e) {\n" +
                                "                        setLogText(\"收到异常:\" + e.toString(),true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onComplete() {\n" +
                                "                        setLogText(\"发送完成\",true);\n" +
                                "                    }\n" +
                                "                });\n" +
                                "    }";
                        break;
                    case 3:
                        skipLast();
                        code = " /**\n" +
                                "     * skipLast操作符\n" +
                                "     */\n" +
                                "    private void skipLast() {\n" +
                                "        tvLog.setText(\"\");\n" +
                                "        setLogText(\"skipLast操作符\",false);\n" +
                                "        setLogText(\"作用:\",false);\n" +
                                "        setLogText(\"跳过反序的某个事件\",false);\n" +
                                "        setLogText(\"skipLast(long n),跳过反序的前n个事件\",false);\n" +
                                "        setLogText(\"skipLast(long n,TimeUnits timeUnit),跳过后n个时间单位的事件\",false);\n" +
                                "        setLogText(\"**********************************\", false);\n" +
                                "//        setLogText(\"示例,发送1,2,3,4,5五个事件,过滤后3个事件\",true);\n" +
                                "        setLogText(\"示例，从0开始,间隔1s发送1个事件，共5个事件并过滤最后一秒的事件\",true);\n" +
                                "//        Observable.just(1,2,3,4,5)\n" +
                                "//                .skipLast(3)\n" +
                                "//                .subscribe(new Observer<Integer>() {\n" +
                                "//                    @Override\n" +
                                "//                    public void onSubscribe(Disposable d) {\n" +
                                "//                        setLogText(\"订阅成功\",true);\n" +
                                "//                    }\n" +
                                "//\n" +
                                "//                    @Override\n" +
                                "//                    public void onNext(Integer integer) {\n" +
                                "//                        setLogText(\"过滤后的事件:\" + integer,true);\n" +
                                "//                    }\n" +
                                "//\n" +
                                "//                    @Override\n" +
                                "//                    public void onError(Throwable e) {\n" +
                                "//                        setLogText(\"收到异常:\" + e.toString(),true);\n" +
                                "//                    }\n" +
                                "//\n" +
                                "//                    @Override\n" +
                                "//                    public void onComplete() {\n" +
                                "//                        setLogText(\"发送完成\",true);\n" +
                                "//                    }\n" +
                                "//                });\n" +
                                "        Observable.intervalRange(0,5,0,1,TimeUnit.SECONDS)\n" +
                                "                .skipLast(1,TimeUnit.SECONDS)\n" +
                                "                .observeOn(AndroidSchedulers.mainThread())\n" +
                                "                .subscribe(new Observer<Long>() {\n" +
                                "                    @Override\n" +
                                "                    public void onSubscribe(Disposable d) {\n" +
                                "                        setLogText(\"订阅成功\",true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onNext(Long aLong) {\n" +
                                "                        setLogText(\"过滤后的事件:\" + aLong,true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onError(Throwable e) {\n" +
                                "                        setLogText(\"收到异常:\" + e.toString(),true);\n" +
                                "                    }\n" +
                                "\n" +
                                "                    @Override\n" +
                                "                    public void onComplete() {\n" +
                                "                        setLogText(\"发送完成\",true);\n" +
                                "                    }\n" +
                                "                });\n" +
                                "    }";
                        break;
                    case 4:
                        distinct();
                        code = "/**\n" +
                                "     * distinct操作符\n" +
                                "     */\n" +
                                "    private void distinct() {\n" +
                                "        tvLog.setText(\"\");\n" +
                                "        setLogText(\"distinct操作符\",false);\n" +
                                "        setLogText(\"作用:\",false);\n" +
                                "        setLogText(\"过滤事件序列中重复的事件\",false);\n" +
                                "        setLogText(\"**********************************\", false);\n" +
                                "        setLogText(\"示例:发送1,2,3,1,2五个事件并过滤其中重复的事件\",true);\n" +
                                "        Observable.just(1,2,3,1,2)\n" +
                                "                .distinct()\n" +
                                "                .subscribe(new Consumer<Integer>() {\n" +
                                "                    @Override\n" +
                                "                    public void accept(Integer integer) throws Exception {\n" +
                                "                        setLogText(\"过滤后的事件:\" + integer,true);\n" +
                                "                    }\n" +
                                "                });\n" +
                                "    }";
                        break;
                    case 5:
                        distinctUntilChange();
                        code = "/**\n" +
                                "     * distinctUntilChange操作符\n" +
                                "     */\n" +
                                "    private void distinctUntilChange() {\n" +
                                "        tvLog.setText(\"\");\n" +
                                "        setLogText(\"distinctUntilChange操作符\",false);\n" +
                                "        setLogText(\"作用：\",false);\n" +
                                "        setLogText(\"过滤连续重复的事件\",false);\n" +
                                "        setLogText(\"**********************************\", false);\n" +
                                "        setLogText(\"示例：发送1,2,3,3,4,4,3七个事件并过滤掉连续重复的事件\",true);\n" +
                                "        Observable.just(1,2,3,3,4,4,3)\n" +
                                "                .distinctUntilChanged()\n" +
                                "                .subscribe(new Consumer<Integer>() {\n" +
                                "                    @Override\n" +
                                "                    public void accept(Integer integer) throws Exception {\n" +
                                "                        setLogText(\"过滤后的事件:\" + integer,true);\n" +
                                "                    }\n" +
                                "                });\n" +
                                "    }";
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
