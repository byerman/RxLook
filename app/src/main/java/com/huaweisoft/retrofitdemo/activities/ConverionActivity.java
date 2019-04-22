package com.huaweisoft.retrofitdemo.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


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

            }
        });
        btnConcatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnBuffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * map操作符
     */
    @SuppressLint("CheckResult")
    private void map() {
        tvLog.setText("");
        setLogText("map操作符",false);
        setLogText("作用:",false);
        setLogText("对被观察者发送的每1个事件都通过指定的函数处理，从而变换成另外一种事件",false);
        setLogText("应用场景:数据类型转换",false);
        setLogText("**********************************", false);
        Observable.just(1, 2, 3, 4)
                .map(new Function<Integer, String>() {
                    // 在此处进行事件变换
                    @Override
                    public String apply(Integer integer) throws Exception {
                        setLogText("收到初始事件:" + integer,true);
                        String newEvent = "新的事件:" + integer;
                        setLogText("进行事件变换," + newEvent,true);
                        return newEvent;
                    }
                    // 此处Consumer效果和Observer类似,只关注于事件接收和错误处理
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                setLogText("收到事件:" + s,true);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                setLogText("发送数据过程出现异常:" + throwable.toString(),true);
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
     * @param content       内容
     * @param isNeedTime    是否需要时间显示
     */
    private void setLogText(String content, boolean isNeedTime) {
        setLogText(tvLog,content,isNeedTime);
    }
}
