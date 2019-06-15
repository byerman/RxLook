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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @author baiaj
 * 条件/布尔操作符演示界面
 */
public class ConditionOperActivity extends BaseOperActivity implements PoPUpView.IPopUpClickListener {

    private PoPUpView mPopUpView;
    private List<String> firstDatas = new ArrayList<>();
    private TextView tvLog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_condition);
        tvLog = findViewById(R.id.tv_log);
        initData();
        initPopUpView();
    }

    /**
     * 初始化操作符数据集
     */
    private void initData() {
        firstDatas.add("all");
        firstDatas.add("contains");
        firstDatas.add("isEmpty");
        firstDatas.add("amb");
        firstDatas.add("takeWhile");
        firstDatas.add("takeUntil");
        firstDatas.add("skipWhile");
        firstDatas.add("skipUntil");
        firstDatas.add("defaultIfEmpty");
        firstDatas.add("SequenceEqual");
    }

    private void initPopUpView() {
        mPopUpView = findViewById(R.id.popview);
        mPopUpView.setFirstData(firstDatas);
        mPopUpView.setPopUpClickListener(this);
    }

    @Override
    protected boolean isShowCodeIcon() {
        return true;
    }

    /**
     * all操作符
     */
    private void all() {
        tvLog.setText("");
        setLogText("all操作符", false);
        setLogText("作用:", false);
        setLogText("判断发送的每个事件是否满足设置的函数条件,若全部满足，则返回true，不满足，返回false", false);
        setLogText("**********************************", false);
        setLogText("示例,发送1,2,3,4,5五个事件，判断是否都小于10", true);
        Observable.range(1, 5)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 10;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                setLogText("是否满足条件:" + aBoolean, true);
            }
        });
    }

    /**
     * contains操作符
     */
    private void contains() {
        tvLog.setText("");
        setLogText("contains操作符", false);
        setLogText("作用:", false);
        setLogText("判断发送的每个事件是否是否包含指定的事件，若包含，则返回true，否则返回false", false);
        setLogText("**********************************", false);
        setLogText("示例,发送1,2,3,4,5五个事件，判断是否包含6", true);
        Observable.range(1, 5)
                .contains(6)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        setLogText("是否包含:" + aBoolean, true);
                    }
                });
    }

    /**
     * isEmpty操作符
     */
    private void isEmpty() {
        tvLog.setText("");
        setLogText("isEmpty操作符", false);
        setLogText("作用:", false);
        setLogText("判断发送的事件是否为空，若为空，则返回true，否则返回false", false);
        setLogText("**********************************", false);
        setLogText("示例,发送空事件，判断是否为空", true);
        Observable.empty()
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        setLogText("是否为空:" + aBoolean, true);
                    }
                });
    }

    /**
     * amb操作符
     */
    private void amb() {
        tvLog.setText("");
        setLogText("amb操作符",false);
        setLogText("作用:",false);
        setLogText("当需要发送多个Observable时,只发送先发送数据的Observable的数据," +
                "而其余Observable则被丢弃",false);
        setLogText("**********************************", false);
        setLogText("示例:发送两个Observable，一个发送1事件，一个延时1秒发送2事件",true);
        List<ObservableSource<Integer>> observableSourceList = new ArrayList<>();
        observableSourceList.add(Observable.just(2).delay(1, TimeUnit.SECONDS));
        observableSourceList.add(Observable.just(1));
        // 一共需要发送2个Observable的数据
        // 但由于使用了amba（）,所以仅发送先发送数据的Observable
        // 即第二个（因为第1个延时了）
        Observable.amb(observableSourceList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        setLogText("收到事件:" + integer,true);
                    }
                });
    }

    /**
     * takeWhile操作符
     */
    private void takeWhile() {
        tvLog.setText("");
        setLogText("takeWhile操作符",false);
        setLogText("作用:",false);
        setLogText("判断发送的每个事件是否满足设置的条件，若满足，则发送，不满足就不发送",false);
        setLogText("**********************************", false);
        setLogText("发送1,2,3,4,5五个事件，设置条件为小于3的数据可以通过",true);
        Observable.range(1,5)
                .takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        // 当发送的事件满足小于3的条件时,可继续发送，否则拦截
                        // 返回true则通过，false拦截
                        return integer < 3;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                setLogText("收到事件:" + integer,true);
            }
        });
    }

    /**
     * takeUntil操作符
     */
    private void takeUntil() {
        tvLog.setText("");
        setLogText("takeUntil操作符",false);
        setLogText("作用:",false);
        setLogText("执行到某个条件时，停止发送事件",false);
        setLogText("**********************************", false);
        setLogText("实例:从0开始，每隔1秒递增发送1个事件,直到事件大于3就停止",true);
        Observable.interval(1,TimeUnit.SECONDS)
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        // 返回true时，停止发送事件(最后一个事件还是会发送)
                        // 返回false,继续发送
                        return aLong > 3;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                setLogText("收到事件:" + aLong,true);
            }
        });
    }

    /**
     * skipWhile操作符
     */
    private void skipWhile() {
        tvLog.setText("");
        setLogText("skipWhile操作符",false);
        setLogText("作用:",false);
        setLogText("判断发送的每项数据是否满足设置的条件,直到不满足，才开始发送事件",false);
        setLogText("**********************************", false);
        setLogText("示例:发送1到10的事件,设置条件为小于5，当大于等于5才开始发送事件",true);
        Observable.range(1,10)
                .skipWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        // 返回true则拦截
                        // 返回false则开始发送事件(即大于等于5就开始发送事件)
                        return integer < 5;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                setLogText("接收事件:" + integer,true);
            }
        });
    }

    /**
     * skipUntil操作符
     */
    private void skipUntil() {
        tvLog.setText("");
        setLogText("skipUntil操作符",false);
        setLogText("作用:",false);
        setLogText("当skipUntil()传入的Observable开始发送事件，第一个Observable才开始发送事件",false);
        setLogText("**********************************", false);
        setLogText("示例，第一个Observable从1开始，间隔1秒发送1个事件，共发10个事件，skipUntil()传入的Observable延时3秒发送事件",true);
        Observable.intervalRange(1,10,0,1,TimeUnit.SECONDS)
                .skipUntil(Observable.timer(3,TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        setLogText("收到事件:" + integer,true);
                    }
                });
    }

    /**
     * defaultEmpty操作符
     */
    private void defaultIfEmpty() {
        tvLog.setText("");
        setLogText("defaultIfEmpty操作符",false);
        setLogText("作用:",false);
        setLogText("在不发送任何有效事件(Next事件),仅发送Complete事件的情况下，发送一个默认值",false);
        setLogText("**********************************", false);
        setLogText("示例,仅发送一个Complete事件，默认值为1",true);
        Observable.empty()
                .defaultIfEmpty(1)
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
                        setLogText("收到异常回调:" + e.toString(),true);
                    }

                    @Override
                    public void onComplete() {
                        setLogText("发送完成",true);
                    }
                });
    }

    /**
     * SequenceEqual操作符
     */
    private void SequenceEqual() {
        tvLog.setText("");
        setLogText("SequenceEqual操作符",false);
        setLogText("作用:",false);
        setLogText("判断两个Observable发送的事件是否相同，相同返回true,否则返回false",false);
        setLogText("**********************************", false);
        setLogText("示例:发送两个1,2,3事件，判断是否相同",true);
        Observable.sequenceEqual(Observable.just(1,2,3),Observable.just(1,2,3))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        setLogText("判断结果:" + aBoolean,true);
                    }
                });
    }

    @Override
    protected String getTitleStr() {
        return getString(R.string.condition_oper);
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
                all();
                code = " /**\n" +
                        "     * all操作符\n" +
                        "     */\n" +
                        "    private void all() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"all操作符\", false);\n" +
                        "        setLogText(\"作用:\", false);\n" +
                        "        setLogText(\"判断发送的每个事件是否满足设置的函数条件,若全部满足，则返回true，不满足，返回false\", false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例,发送1,2,3,4,5五个事件，判断是否都小于10\", true);\n" +
                        "        Observable.range(1, 5)\n" +
                        "                .all(new Predicate<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public boolean test(Integer integer) throws Exception {\n" +
                        "                        return integer < 10;\n" +
                        "                    }\n" +
                        "                }).subscribe(new Consumer<Boolean>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Boolean aBoolean) throws Exception {\n" +
                        "                setLogText(\"是否满足条件:\" + aBoolean,true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
                break;
            case 1:
                contains();
                code = "/**\n" +
                        "     * contains操作符\n" +
                        "     */\n" +
                        "    private void contains() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"contains操作符\", false);\n" +
                        "        setLogText(\"作用:\", false);\n" +
                        "        setLogText(\"判断发送的每个事件是否是否包含指定的事件，若包含，则返回true，否则返回false\", false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例,发送1,2,3,4,5五个事件，判断是否包含6\", true);\n" +
                        "        Observable.range(1, 5)\n" +
                        "                .contains(6)\n" +
                        "                .subscribe(new Consumer<Boolean>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Boolean aBoolean) throws Exception {\n" +
                        "                setLogText(\"是否包含:\" + aBoolean,true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
                break;
            case 2:
                isEmpty();
                code = "/**\n" +
                        "     * isEmpty操作符\n" +
                        "     */\n" +
                        "    private void isEmpty() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"isEmpty操作符\", false);\n" +
                        "        setLogText(\"作用:\", false);\n" +
                        "        setLogText(\"判断发送的事件是否为空，若为空，则返回true，否则返回false\", false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例,发送空事件，判断是否为空\", true);\n" +
                        "        Observable.empty()\n" +
                        "                .isEmpty()\n" +
                        "                .subscribe(new Consumer<Boolean>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Boolean aBoolean) throws Exception {\n" +
                        "                        setLogText(\"是否为空:\" + aBoolean, true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
                break;
            case 3:
                amb();
                code = "/**\n" +
                        "     * amb操作符\n" +
                        "     */\n" +
                        "    private void amb() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"amb操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"当需要发送多个Observable时,只发送先发送数据的Observable的数据,\" +\n" +
                        "                \"而其余Observable则被丢弃\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例:发送两个Observable，一个发送1事件，一个延时1秒发送2事件\",true);\n" +
                        "        List<ObservableSource<Integer>> observableSourceList = new ArrayList<>();\n" +
                        "        observableSourceList.add(Observable.just(2).delay(1, TimeUnit.SECONDS));\n" +
                        "        observableSourceList.add(Observable.just(1));\n" +
                        "        // 一共需要发送2个Observable的数据\n" +
                        "        // 但由于使用了amba（）,所以仅发送先发送数据的Observable\n" +
                        "        // 即第二个（因为第1个延时了）\n" +
                        "        Observable.amb(observableSourceList)\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Consumer<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Integer integer) throws Exception {\n" +
                        "                        setLogText(\"收到事件:\" + integer,true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
                break;
            case 4:
                takeWhile();
                code = "/**\n" +
                        "     * takeWhile操作符\n" +
                        "     */\n" +
                        "    private void takeWhile() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"takeWhile操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"判断发送的每个事件是否满足设置的条件，若满足，则发送，不满足就不发送\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"发送1,2,3,4,5五个事件，设置条件为小于3的数据可以通过\",true);\n" +
                        "        Observable.range(1,5)\n" +
                        "                .takeWhile(new Predicate<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public boolean test(Integer integer) throws Exception {\n" +
                        "                        // 当发送的事件满足小于3的条件时,可继续发送，否则拦截\n" +
                        "                        // 返回true则通过，false拦截\n" +
                        "                        return integer < 3;\n" +
                        "                    }\n" +
                        "                }).subscribe(new Consumer<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Integer integer) throws Exception {\n" +
                        "                setLogText(\"收到事件:\" + integer,true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
                break;
            case 5:
                takeUntil();
                code = "/**\n" +
                        "     * takeUntil操作符\n" +
                        "     */\n" +
                        "    private void takeUntil() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"takeUntil操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"执行到某个条件时，停止发送事件\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"实例:从0开始，每隔1秒递增发送1个事件,直到事件大于3就停止\",true);\n" +
                        "        Observable.interval(1,TimeUnit.SECONDS)\n" +
                        "                .takeUntil(new Predicate<Long>() {\n" +
                        "                    @Override\n" +
                        "                    public boolean test(Long aLong) throws Exception {\n" +
                        "                        // 返回true时，停止发送事件(最后一个事件还是会发送)\n" +
                        "                        // 返回false,继续发送\n" +
                        "                        return aLong > 3;\n" +
                        "                    }\n" +
                        "                })\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Consumer<Long>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Long aLong) throws Exception {\n" +
                        "                setLogText(\"收到事件:\" + aLong,true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
                break;
            case 6:
                skipWhile();
                code = "/**\n" +
                        "     * skipWhile操作符\n" +
                        "     */\n" +
                        "    private void skipWhile() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"skipWhile操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"判断发送的每项数据是否满足设置的条件,直到不满足，才开始发送事件\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例:发送1到10的事件,设置条件为小于5，当大于等于5才开始发送事件\",true);\n" +
                        "        Observable.range(1,10)\n" +
                        "                .skipWhile(new Predicate<Integer>() {\n" +
                        "                    @Override\n" +
                        "                    public boolean test(Integer integer) throws Exception {\n" +
                        "                        // 返回true则拦截\n" +
                        "                        // 返回false则开始发送事件(即大于等于5就开始发送事件)\n" +
                        "                        return integer < 5;\n" +
                        "                    }\n" +
                        "                }).subscribe(new Consumer<Integer>() {\n" +
                        "            @Override\n" +
                        "            public void accept(Integer integer) throws Exception {\n" +
                        "                setLogText(\"接收事件:\" + integer,true);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }";
                break;
            case 7:
                skipUntil();
                code = "/**\n" +
                        "     * skipUntil操作符\n" +
                        "     */\n" +
                        "    private void skipUntil() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"skipUntil操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"当skipUntil()传入的Observable开始发送事件，第一个Observable才开始发送事件\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例，第一个Observable从1开始，间隔1秒发送1个事件，共发10个事件，skipUntil()传入的Observable延时3秒发送事件\",true);\n" +
                        "        Observable.intervalRange(1,10,0,1,TimeUnit.SECONDS)\n" +
                        "                .skipUntil(Observable.timer(3,TimeUnit.SECONDS))\n" +
                        "                .observeOn(AndroidSchedulers.mainThread())\n" +
                        "                .subscribe(new Consumer<Long>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Long integer) throws Exception {\n" +
                        "                        setLogText(\"收到事件:\" + integer,true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
                break;
            case 8:
                defaultIfEmpty();
                code = "/**\n" +
                        "     * defaultEmpty操作符\n" +
                        "     */\n" +
                        "    private void defaultIfEmpty() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"defaultIfEmpty操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"在不发送任何有效事件(Next事件),仅发送Complete事件的情况下，发送一个默认值\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例,仅发送一个Complete事件，默认值为1\",true);\n" +
                        "        Observable.empty()\n" +
                        "                .defaultIfEmpty(1)\n" +
                        "                .subscribe(new Observer<Object>() {\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onSubscribe(Disposable d) {\n" +
                        "                        setLogText(\"订阅成功\",true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onNext(Object o) {\n" +
                        "                        setLogText(\"收到事件:\" + o,true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onError(Throwable e) {\n" +
                        "                        setLogText(\"收到异常回调:\" + e.toString(),true);\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onComplete() {\n" +
                        "                        setLogText(\"发送完成\",true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
                break;
            case 9:
                SequenceEqual();
                code = "/**\n" +
                        "     * SequenceEqual操作符\n" +
                        "     */\n" +
                        "    private void SequenceEqual() {\n" +
                        "        tvLog.setText(\"\");\n" +
                        "        setLogText(\"SequenceEqual操作符\",false);\n" +
                        "        setLogText(\"作用:\",false);\n" +
                        "        setLogText(\"判断两个Observable发送的事件是否相同，相同返回true,否则返回false\",false);\n" +
                        "        setLogText(\"**********************************\", false);\n" +
                        "        setLogText(\"示例:发送两个1,2,3事件，判断是否相同\",true);\n" +
                        "        Observable.sequenceEqual(Observable.just(1,2,3),Observable.just(1,2,3))\n" +
                        "                .subscribe(new Consumer<Boolean>() {\n" +
                        "                    @Override\n" +
                        "                    public void accept(Boolean aBoolean) throws Exception {\n" +
                        "                        setLogText(\"判断结果:\" + aBoolean,true);\n" +
                        "                    }\n" +
                        "                });\n" +
                        "    }";
                break;
            default:
                break;
        }
    }
}
