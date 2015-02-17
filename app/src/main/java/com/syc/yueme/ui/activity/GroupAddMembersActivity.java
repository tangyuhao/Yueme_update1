package com.syc.yueme.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.syc.yueme.R;
import com.syc.yueme.adapter.GroupAddMembersAdapter;
import com.syc.yueme.service.CacheService;
import com.syc.yueme.service.GroupService;
import com.syc.yueme.util.SimpleNetTask;
import com.syc.yueme.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 14-10-11.
 */
public class GroupAddMembersActivity extends GroupBaseActivity {
  public static final int OK = 0;
  GroupAddMembersAdapter adapter;
  ListView userList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.group_add_members_layout);
    findView();
    initList();
    initActionBar();
    onGroupUpdate();
  }

  private void setListData() {
    List<String> ids = new ArrayList<String>();
    ids.addAll(CacheService.getFriendIds());
    ids.removeAll(getChatGroup().getMembers());
    adapter.setDatas(ids);
    adapter.notifyDataSetChanged();
  }

  @Override
  void onGroupUpdate() {
    setListData();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuItem add = menu.add(0, OK, 0, R.string.sure);
    Utils.alwaysShowMenuItem(add);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    int id = item.getItemId();
    if (id == OK) {
      addMembers();
    }
    return super.onMenuItemSelected(featureId, item);
  }

  private void addMembers() {
    final List<String> checkedUsers = adapter.getCheckedDatas();
    new SimpleNetTask(ctx) {
      @Override
      protected void doInBack() throws Exception {
        GroupService.inviteMembers(getChatGroup(), checkedUsers);
      }

      @Override
      protected void onSucceed() {
        Utils.toast(R.string.inviteSucceed);
        finish();
      }
    }.execute();
  }

  private void initList() {
    adapter = new GroupAddMembersAdapter(ctx, new ArrayList<String>());
    userList.setAdapter(adapter);
  }

  private void findView() {
    userList = (ListView) findViewById(R.id.userList);
  }

}
