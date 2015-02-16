// Generated code from Butter Knife. Do not modify!
package com.avoscloud.yueme.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ConversationFragment$$ViewInjector {
  public static void inject(Finder finder, final com.avoscloud.yueme.ui.fragment.ConversationFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492974, "field 'listView'");
    target.listView = (com.avoscloud.yueme.ui.view.BaseListView<com.avoscloud.yueme.entity.Conversation>) view;
  }

  public static void reset(com.avoscloud.yueme.ui.fragment.ConversationFragment target) {
    target.listView = null;
  }
}
