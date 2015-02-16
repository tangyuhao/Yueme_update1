// Generated code from Butter Knife. Do not modify!
package com.avoscloud.yueme.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DiscoverFragment$$ViewInjector {
  public static void inject(Finder finder, final com.avoscloud.yueme.ui.fragment.DiscoverFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492943, "field 'listView'");
    target.listView = (com.avoscloud.yueme.ui.view.BaseListView<com.avos.avoscloud.AVUser>) view;
  }

  public static void reset(com.avoscloud.yueme.ui.fragment.DiscoverFragment target) {
    target.listView = null;
  }
}
