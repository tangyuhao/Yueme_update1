package com.syc.yueme.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.service.CacheService;
import com.syc.yueme.ui.view.ViewHolder;
import com.syc.yueme.util.ChatUtils;

import java.util.List;

/**
 * Created by lzw on 14-10-11.
 */
public class GroupAddMembersAdapter extends BaseCheckListAdapter<String> {

  public GroupAddMembersAdapter(Context ctx, List<String> datas) {
    super(ctx, datas);
  }

  @Override
  public View getView(final int position, View conView, ViewGroup parent) {
    if (conView == null) {
      conView = View.inflate(ctx, R.layout.group_add_members_item, null);
    }
    String userId = datas.get(position);
    AVUser user = CacheService.lookupUser(userId);
    ChatUtils.setUserView(conView, user);
    CheckBox checkBox = ViewHolder.findViewById(conView, R.id.checkbox);
    setCheckBox(checkBox, position);
    checkBox.setOnCheckedChangeListener(new CheckListener(position));
    return conView;
  }
}
