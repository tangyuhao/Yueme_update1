package com.syc.yueme.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.InjectView;
import com.syc.yueme.R;
import com.syc.yueme.adapter.RecentMessageAdapter;
import com.syc.yueme.entity.Conversation;
import com.syc.yueme.entity.RoomType;
import com.syc.yueme.service.ChatService;
import com.syc.yueme.service.listener.MsgListener;
import com.syc.yueme.service.receiver.GroupMsgReceiver;
import com.syc.yueme.service.receiver.MsgReceiver;
import com.syc.yueme.ui.activity.ChatActivity;
import com.syc.yueme.ui.view.BaseListView;

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
