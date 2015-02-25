package com.syc.yueme.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.entity.SortUser;
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.view.ViewHolder;

import java.util.List;

@SuppressLint("DefaultLocale")
public class UserFriendAdapter extends BaseAdapter implements SectionIndexer {
  private Context ct;
  private List<SortUser> data;

  public UserFriendAdapter(Context ct, List<SortUser> datas) {
    this.ct = ct;
    this.data = datas;
  }

  public void updateDatas(List<SortUser> list) {
    this.data = list;
    notifyDataSetChanged();
  }

  public void remove(SortUser user) {
    this.data.remove(user);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Object getItem(int position) {
   SortUser a = data.get(position);
      return a;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(ct).inflate(
          R.layout.item_user_friend, null);
    }
    TextView alpha = ViewHolder.findViewById(convertView, R.id.alpha);
    TextView nameView = ViewHolder.findViewById(convertView, R.id.tv_friend_name);
    ImageView avatarView = ViewHolder.findViewById(convertView, R.id.img_friend_avatar);

    SortUser friend = data.get(position);
    final String name = friend.getInnerUser().getUsername();
    final String avatarUrl = User.getAvatarUrl(friend.getInnerUser());

    UserService.displayAvatar(avatarUrl, avatarView);
    nameView.setText(name);

    int section = getSectionForPosition(position);
    if (position == getPositionForSection(section)) {
      alpha.setVisibility(View.VISIBLE);
      alpha.setText("   " + friend.getSortLetters());
    } else {
      alpha.setVisibility(View.GONE);
    }

    return convertView;
  }

  public int getSectionForPosition(int position) {
    return data.get(position).getSortLetters().charAt(0);
  }


  @SuppressLint("DefaultLocale")
  public int getPositionForSection(int section) {
    for (int i = 0; i < getCount(); i++) {
      String sortStr = data.get(i).getSortLetters();
      char firstChar = sortStr.toUpperCase().charAt(0);
      if (firstChar == section) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public Object[] getSections() {
    return null;
  }

}
