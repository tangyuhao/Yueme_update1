package com.avoscloud.Yueme.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.avos.avoscloud.AVUser;
import com.avoscloud.Yueme.R;
import com.avoscloud.Yueme.adapter.AddFriendAdapter;
import com.avoscloud.Yueme.base.App;
import com.avoscloud.Yueme.service.UserService;
import com.avoscloud.Yueme.ui.view.xlist.XListView;
import com.avoscloud.Yueme.util.ChatUtils;
import com.avoscloud.Yueme.util.NetAsyncTask;
import com.avoscloud.Yueme.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class AddFriendActivity extends BaseActivity implements OnClickListener, XListView.IXListViewListener, OnItemClickListener {
  EditText searchNameEdit;
  Button searchBtn;
  List<AVUser> users = new ArrayList<AVUser>();//change it first , then adapter
  XListView listView;
  AddFriendAdapter adapter;
  String searchName = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.contact_add_friend_activity);
    initView();
    search(searchName);
  }

  private void initView() {
    initActionBar(App.ctx.getString(R.string.findFriends));
    searchNameEdit = (EditText) findViewById(R.id.searchNameEdit);
    searchBtn = (Button) findViewById(R.id.searchBtn);
    searchBtn.setOnClickListener(this);
    initXListView();
  }

  private void initXListView() {
    listView = (XListView) findViewById(R.id.searchList);
    listView.setPullLoadEnable(false);
    listView.setPullRefreshEnable(false);
    listView.setXListViewListener(this);

    adapter = new AddFriendAdapter(this, users);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.searchBtn:
        searchName = searchNameEdit.getText().toString();
        if (searchName != null) {
          adapter.clear();
          search(searchName);
        }
        break;
    }
  }

  private void search(final String searchName) {
    new NetAsyncTask(ctx, false) {
      List<AVUser> users;

      @Override
      protected void doInBack() throws Exception {
        users = UserService.searchUser(searchName, adapter.getCount());
      }

      @Override
      protected void onPost(Exception e) {
        stopLoadMore();
        if (e != null) {
          e.printStackTrace();
          Utils.toast(ctx, R.string.pleaseCheckNetwork);
        } else {
          ChatUtils.handleListResult(listView, adapter, users);
        }
      }
    }.execute();

  }


  @Override
  public void onRefresh() {
    // TODO Auto-generated method stub
  }

  @Override
  public void onLoadMore() {
    // TODO Auto-generated method stub
    search(searchName);
  }

  private void stopLoadMore() {
    if (listView.getPullLoading()) {
      listView.stopLoadMore();
    }
  }
}
