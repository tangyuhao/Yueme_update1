// Generated code from Butter Knife. Do not modify!
package com.syc.yueme.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ConversationFragment$$ViewInjector {
  public static void inject(Finder finder, final com.syc.yueme.ui.fragment.ConversationFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558564, "field 'listView'");
    target.listView = (com.syc.yueme.ui.view.BaseListView<com.syc.yueme.entity.Conversation>) view;
  }

  public static void reset(com.syc.yueme.ui.fragment.ConversationFragment target) {
    target.listView = null;
  }
}
