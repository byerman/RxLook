package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

/**
 * @author baiaj
 * 基础操作符界面
 */
public abstract class BaseOperActivity extends BaseActivity {

    protected String code = "";
    /**
     * 操作界面展示
     */
    private RelativeLayout rlytOper;
    /**
     * 源码界面展示
     */
    private RelativeLayout rlytCode;
    private ImageButton btnClose;
    private ImageButton btnBack;
    private ImageButton btnShowCode;
    private TextView tvCode;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getContentViewId());
        initView();
        initListener();
        showCodeIcon();
        setTitle();
    }

    /**
     * 控制源码按钮的显示
     */
    private void showCodeIcon() {
        if (isShowCodeIcon()) {
            btnShowCode.setVisibility(View.VISIBLE);
        } else {
            btnShowCode.setVisibility(View.GONE);
        }
    }

    private void setTitle() {
        tvTitle.setText(getTitleStr());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_oper_base;
    }

    /**
     * 重写setContentView,让继承的Activity可以按常规操作接入
     *
     * @param layoutResID 继承的Activity的布局Id
     */
    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.title_show);
        if (rlytOper != null) {
            rlytOper.addView(view, layoutParams);
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        rlytOper = findViewById(R.id.rlyt_view);
        rlytCode = findViewById(R.id.rlyt_code);
        btnClose = findViewById(R.id.btn_close);
        btnBack = findViewById(R.id.btn_back);
        btnShowCode = findViewById(R.id.ibtn_code);
        tvCode = findViewById(R.id.tv_code);
        tvTitle = findViewById(R.id.tv_title);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCloseBtnClick()) {
                    rlytCode.setVisibility(View.GONE);
                    rlytOper.setVisibility(View.VISIBLE);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackClick()) {
                    finish();
                }
            }
        });
        btnShowCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onShowCodeClick()) {
                    rlytCode.setVisibility(View.VISIBLE);
                    rlytOper.setVisibility(View.GONE);
                    tvCode.setText(code);
                }
            }
        });
    }

    /**
     * 点击了关闭按钮
     *
     * @return 是否执行默认操作
     */
    protected boolean onCloseBtnClick() {
        return true;
    }

    /**
     * 点击了返回按钮
     *
     * @return 是否执行默认操作
     */
    protected boolean onBackClick() {
        return true;
    }

    /**
     * 点击了显示源码按钮
     *
     * @return 是否执行默认操作
     */
    protected boolean onShowCodeClick() {
        return true;
    }

    /**
     * 是否展示源码按钮
     *
     * @return 结果
     */
    protected abstract boolean isShowCodeIcon();

    /**
     * 设置标题
     *
     * @return 标题字符串
     */
    protected abstract String getTitleStr();
}
