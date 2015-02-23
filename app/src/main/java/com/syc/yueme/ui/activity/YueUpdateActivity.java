package com.syc.yueme.ui.activity;

import android.os.Bundle;

import com.syc.yueme.R;

/**
 * Created by wangweijia on 2015/2/23.
 */
public class YueUpdateActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yue_update_activity);
        initActionBar("发布状态");
    }
}
