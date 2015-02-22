package com.syc.yueme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.service.ChatService;

/**
 * Created by lzw on 14-9-24.
 */
public class NotifySettingActivity extends BaseActivity implements View.OnClickListener{
//    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_space_setting_notify_layout);
        initActionBar(R.string.notifySetting);
    }
    public void onClick(View v) {
        ChatService.closeSession();
        AVUser.logOut();
        startActivity(new Intent(ctx, LoginActivity.class));
    }
}
