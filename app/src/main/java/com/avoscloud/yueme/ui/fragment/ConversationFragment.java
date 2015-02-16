package com.avoscloud.yueme.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.InjectView;
import com.avoscloud.yueme.R;
import com.avoscloud.yueme.adapter.RecentMessageAdapter;
import com.avoscloud.yueme.entity.Conversation;
import com.avoscloud.yueme.entity.RoomType;
import com.avoscloud.yueme.service.ChatService;
import com.avoscloud.yueme.service.listener.MsgListener;
import com.avoscloud.yueme.service.receiver.GroupMsgReceiver;
import com.avoscloud.yueme.service.receiver.MsgReceiver;
import com.avoscloud.yueme.ui.activity.ChatActivity;
import com.avoscloud.yueme.ui.view.BaseListView;

import java.util.List;

/**
 * Created by lzw on 14-9-17.
 */
public class ConversationFragment extends BaseFragment implements MsgListener {

  @InjectView(R.id.convList)
  BaseListView<Conversation> listView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.message_fragment, container, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    onRefresh();
  }

  private void onRefresh() {
    listView.onRefresh();
  }

  private void initView() {
    headerLayout.showTitle(R.string.messages);
    listView = (BaseListView<Conversation>) getView().findViewById(R.id.convList);
    listView.init(new BaseListView.DataInterface<Conversation>() {
      @Override
      public List<Conversation> getDatas(int skip, int limit, List<Conversation> currentDatas) throws Exception {
        return ChatService.getConversationsAndCache();
      }

      @Override
      public void onItemSelected(Conversation item) {
        if (item.getMsg().getRoomType() == RoomType.Single) {
          ChatActivity.goUserChat(getActivity(), item.getToUser().getObjectId());
        } else {
          ChatActivity.goGroupChat(getActivity(), item.getToChatGroup().getObjectId());
        }
      }

    }, new RecentMessageAdapter(getActivity()));


  }

  private boolean hidden;

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    this.hidden = hidden;
    if (!hidden) {
      onRefresh();
    }
  }


  @Override
  public void onResume() {
    super.onResume();
    if (!hidden) {
      onRefresh();
    }
    GroupMsgReceiver.addMsgListener(this);
    MsgReceiver.addMsgListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();
    MsgReceiver.removeMsgListener(this);
    GroupMsgReceiver.removeMsgListener(this);
  }

  @Override
  public boolean onMessageUpdate(String otherId) {
    onRefresh();
    return false;
  }
}
