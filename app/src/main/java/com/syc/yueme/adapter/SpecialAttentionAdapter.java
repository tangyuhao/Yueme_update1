package com.syc.yueme.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.service.CloudService;
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.view.ViewHolder;
import com.syc.yueme.util.NetAsyncTask;
import com.syc.yueme.util.Utils;

import java.util.List;

import static com.syc.yueme.ui.activity.ChatActivity.getUserChatIntent;

public class SpecialAttentionAdapter extends BaseListAdapter<AVUser> {
  public SpecialAttentionAdapter(Context context, List<AVUser> list) {
    super(context, list);
    // TODO Auto-generated constructor stub
  }

  @Override
  public View getView(int position, View conView, ViewGroup parent) {
    // TODO Auto-generated method stub
    if (conView == null) {
      conView = inflater.inflate(R.layout.contact_add_friend_item, null);
    }
    final AVUser contact = datas.get(position);
    TextView nameView = ViewHolder.findViewById(conView, R.id.name);
    ImageView avatarView = ViewHolder.findViewById(conView, R.id.avatar);
    Button addBtn = ViewHolder.findViewById(conView, R.id.add);
    String avatarUrl = User.getAvatarUrl(contact);
    UserService.displayAvatar(avatarUrl, avatarView);
    nameView.setText(contact.getUsername());
    addBtn.setText(R.string.talk);
      addBtn.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
              Intent intent = getUserChatIntent(ctx,contact.getObjectId());
              ctx.startActivity(intent);
          }
      });
      return conView;
  }

}

