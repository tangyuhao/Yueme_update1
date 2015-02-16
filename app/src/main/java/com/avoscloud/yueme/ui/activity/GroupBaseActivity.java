package com.avoscloud.yueme.ui.activity;

import com.avoscloud.yueme.avobject.ChatGroup;
import com.avoscloud.yueme.service.CacheService;

/**
 * Created by lzw on 14/12/20.
 */
public abstract class GroupBaseActivity extends BaseActivity {
  public static ChatGroup getChatGroup() {
    return CacheService.getCurrentChatGroup();
  }

  abstract void onGroupUpdate();
}
