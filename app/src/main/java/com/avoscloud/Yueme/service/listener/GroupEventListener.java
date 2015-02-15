package com.avoscloud.Yueme.service.listener;

import com.avos.avoscloud.Group;

/**
 * Created by lzw on 14-10-8.
 */
//GroupMemberEvent
public interface GroupEventListener {

  void onJoined(Group group);

  void onMemberUpdate(Group group);

  void onQuit(Group group);
}
