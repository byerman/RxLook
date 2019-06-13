package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;
import com.huaweisoft.retrofitdemo.view.PoPUpView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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

    }

    /**
     * takeWhile操作符
     */
    private void takeWhile() {

    }

    /**
     * takeUntil操作符
     */
    private void takeUntil() {

    }

    /**
     * skipWhile操作符
     */
    private void skipWhile() {

    }

    /**
     * skipUntil操作符
     */
    private void skipUntil() {

    }

    /**
     * defaultEmpty操作符
     */
    private void defaultEmpty() {

    }

    /**
     * SequenceEqual操作符
     */
    private void SequenceEqual() {

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
                break;
            case 4:
                takeWhile();
                break;
            case 5:
                takeUntil();
                break;
            case 6:
                skipWhile();
                break;
            case 7:
                skipUntil();
                break;
            case 8:
                defaultEmpty();
                break;
            case 9:
                SequenceEqual();
                break;
            default:
                break;
        }
    }
}
