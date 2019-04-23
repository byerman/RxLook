package com.huaweisoft.retrofitdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.huaweisoft.retrofitdemo.R;

public class MergeOperActivity extends BaseOperActivity {

    private Button btnMerge;
    private Button btnMergeArray;
    private TextView tvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_merge);
        initView();
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
        btnMerge = findViewById(R.id.btn_merge);;
        btnMergeArray = findViewById(R.id.btn_mergeArray);
        tvLog = findViewById(R.id.tv_log);
    }

}
