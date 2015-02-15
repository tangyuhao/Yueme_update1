package com.avoscloud.Yueme.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.avos.avoscloud.AVUser;
import com.avoscloud.Yueme.R;
import com.avoscloud.Yueme.service.UserService;
import com.avoscloud.Yueme.util.Utils;

public class SplashActivity extends BaseEntryActivity {
  private static final int GO_MAIN_MSG = 1;
  private static final int GO_LOGIN_MSG = 2;
  public static final int SPLASH_DURATION = 2000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.entry_splash_layout);

    if (AVUser.getCurrentUser() != null) {
      UserService.updateUserInfo();
      handler.sendEmptyMessageDelayed(GO_MAIN_MSG, SPLASH_DURATION);
    } else {
      handler.sendEmptyMessageDelayed(GO_LOGIN_MSG, SPLASH_DURATION);
    }
  }

  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case GO_MAIN_MSG:
          MainActivity.goMainActivity(SplashActivity.this);
          break;
        case GO_LOGIN_MSG:
          Utils.goActivity(ctx, LoginActivity.class);
          break;
      }
    }
  };
}
