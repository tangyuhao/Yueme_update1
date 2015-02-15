package com.avoscloud.Yueme.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.avoscloud.Yueme.avobject.ChatGroup;
import com.avoscloud.Yueme.ui.view.ViewHolder;
import com.avoscloud.Yueme.R;

import java.util.List;

/**
 * Created by lzw on 14-10-8.
 */
public class GroupAdapter extends BaseListAdapter<ChatGroup> {
  public GroupAdapter(Context ctx, List<ChatGroup> datas) {
    super(ctx, datas);
  }

  @Override
  public View getView(int position, View conView, ViewGroup parent) {
    if (conView == null) {
      //conView = View.inflate(ctx, R.layout.group_item,null);
      conView = inflater.inflate(R.layout.group_item, null);
    }
    TextView nameView = ViewHolder.findViewById(conView, R.id.name);
    ChatGroup chatGroup = datas.get(position);
    nameView.setText(chatGroup.getTitle());
    return conView;
  }
}
