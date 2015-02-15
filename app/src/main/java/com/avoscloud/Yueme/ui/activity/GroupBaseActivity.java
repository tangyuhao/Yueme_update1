package com.avoscloud.Yueme.ui.activity;

import com.avoscloud.Yueme.avobject.ChatGroup;
import com.avoscloud.Yueme.service.CacheService;

/**
 * Created by lzw on 14/12/20.
 */
public abstract class GroupBaseActivity extends BaseActivity {
  public static ChatGroup getChatGroup() {
    return CacheService.getCurrentChatGroup();
  }

  abstract void onGroupUpdate();
}
