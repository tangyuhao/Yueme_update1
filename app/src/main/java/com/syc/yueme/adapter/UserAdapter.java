package com.syc.yueme.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.base.C;
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 14-8-7.
 */
public class UserAdapter extends BaseAdapter {
  Activity cxt;
  List<AVUser> users = new ArrayList<AVUser>();

  public UserAdapter(Activity cxt) {
    this.cxt = cxt;
  }

  public void setUsers(List<AVUser> users) {
    this.users = users;
  }

  @Override
  public int getCount() {
    return users.size();
  }

  @Override
  public Object getItem(int position) {
    return users.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View conView, ViewGroup parent) {
    if (conView == null) {
      LayoutInflater inflater = LayoutInflater.from(cxt);
      conView = inflater.inflate(R.layout.chat_user_row, null, false);
    }
    TextView usernameView = ViewHolder.findViewById(conView, R.id.name);
    TextView onlineStatusView = ViewHolder.findViewById(conView, R.id.onlineStatus);
    ImageView avatarView = ViewHolder.findViewById(conView, R.id.userAvatar);
    AVUser user = users.get(position);
    usernameView.setText(user.getUsername());
    setTextBasedOnFlag(user.getBoolean(C.ONLINE), onlineStatusView,
        R.string.status_online, R.string.status_offline);
    UserService.displayAvatar(User.getAvatarUrl(user), avatarView);
    return conView;
  }

  public void setTextBasedOnFlag(boolean flag, TextView textView, int onStringId, int offStringId) {
    textView.setText(flag ? onStringId : offStringId);
  }
}
