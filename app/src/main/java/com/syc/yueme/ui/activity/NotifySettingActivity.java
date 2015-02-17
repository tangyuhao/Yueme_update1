package com.syc.yueme.ui.activity;

import android.os.Bundle;
import com.syc.yueme.R;

/**
 * Created by lzw on 14-9-24.
 */
public class NotifySettingActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.my_space_setting_notify_layout);
    initActionBar(R.string.notifySetting);
  }
}
