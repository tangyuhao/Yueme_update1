package com.avoscloud.Yueme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.avos.avoscloud.Group;
import com.avos.avoscloud.Session;
import com.avoscloud.Yueme.adapter.GroupAdapter;
import com.avoscloud.Yueme.avobject.ChatGroup;
import com.avoscloud.Yueme.service.CacheService;
import com.avoscloud.Yueme.service.ChatService;
import com.avoscloud.Yueme.service.listener.GroupEventListener;
import com.avoscloud.Yueme.ui.view.xlist.XListView;
import com.avoscloud.Yueme.R;
import com.avoscloud.Yueme.base.App;
import com.avoscloud.Yueme.service.receiver.GroupMsgReceiver;
import com.avoscloud.Yueme.service.GroupService;
import com.avoscloud.Yueme.util.NetAsyncTask;
import com.avoscloud.Yueme.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lzw on 14-10-7.
 */
public class GroupListActivity extends BaseActivity implements GroupEventListener, AdapterView.OnItemClickListener, XListView.IXListViewListener {
  public static final int GROUP_NAME_REQUEST = 0;
  XListView groupListView;
  List<ChatGroup> chatGroups = new ArrayList<ChatGroup>();
  GroupAdapter groupAdapter;
  String newGroupName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.group_list_activity);
    findView();
    initList();
    onRefresh();
    initActionBar(App.ctx.getString(R.string.group));
    GroupMsgReceiver.addListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.group_list_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.create) {
      UpdateContentActivity.goActivityForResult(this, App.ctx.getString(R.string.groupName), GROUP_NAME_REQUEST);
    }
    return super.onMenuItemSelected(featureId, item);
  }

  private void initList() {
    groupAdapter = new GroupAdapter(ctx, chatGroups);
    groupListView.setPullRefreshEnable(true);
    groupListView.setPullLoadEnable(false);
    groupListView.setXListViewListener(this);
    groupListView.setAdapter(groupAdapter);
    groupListView.setOnItemClickListener(this);
  }

  private void findView() {
    groupListView = (XListView) findViewById(R.id.groupList);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      if (requestCode == GROUP_NAME_REQUEST) {
        newGroupName = UpdateContentActivity.getResultValue(data);
        Session session = ChatService.getSession();
        Group group = session.getGroup();
        group.join();
      }
    }
    super.onActivityResult(requestCode, resultCode, data);
  }


  @Override
  public void onJoined(final Group group) {
    //new Group
    if (newGroupName != null) {
      new NetAsyncTask(ctx) {
        ChatGroup chatGroup;

        @Override
        protected void doInBack() throws Exception {
          chatGroup = GroupService.setNewChatGroupData(group.getGroupId(), newGroupName);
        }

        @Override
        protected void onPost(Exception e) {
          newGroupName = null;
          if (e != null) {
            Utils.toast(e.getMessage());
            Utils.printException(e);
          } else {
            chatGroups.add(0, chatGroup);
            CacheService.registerChatGroupsCache(Arrays.asList(chatGroup));
            groupAdapter.notifyDataSetChanged();
          }
        }
      }.execute();
    } else {
      ChatGroup _chatGroup = findChatGroup(group.getGroupId());
      if (_chatGroup == null) {
        throw new RuntimeException("chat group is null");
      }
      ChatActivity.goGroupChat(this, _chatGroup.getObjectId());
    }
  }

  @Override
  public void onMemberUpdate(Group group) {
    groupAdapter.notifyDataSetChanged();
  }

  @Override
  public void onQuit(Group group) {
    onRefresh();
  }

  private ChatGroup findChatGroup(String groupId) {
    for (ChatGroup chatGroup : chatGroups) {
      if (chatGroup.getObjectId().equals(groupId)) {
        return chatGroup;
      }
    }
    return null;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    GroupMsgReceiver.removeListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    ChatGroup chatGroup = (ChatGroup) parent.getAdapter().getItem(position);
    Group group = ChatService.getGroupById(chatGroup.getObjectId());
    group.join();
  }

  @Override
  public void onRefresh() {
    new NetAsyncTask(ctx, false) {
      List<ChatGroup> subChatGroups;

      @Override
      protected void doInBack() throws Exception {
        subChatGroups = GroupService.findGroups();
      }

      @Override
      protected void onPost(Exception e) {
        groupListView.stopRefresh();
        if (e != null) {

        } else {
          chatGroups.clear();
          chatGroups.addAll(subChatGroups);
          CacheService.registerChatGroupsCache(chatGroups);
          groupAdapter.notifyDataSetChanged();
        }
      }
    }.execute();
  }

  @Override
  public void onLoadMore() {

  }
}
