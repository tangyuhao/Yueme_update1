package com.syc.yueme.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.service.PreferenceMap;
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.view.ViewHolder;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

public class CommentAdapter extends BaseListAdapter<AVObject> {
    PrettyTime prettyTime;
    AVGeoPoint location;
    ImageView avatarView;
    TextView nameView;

    public CommentAdapter(Context ctx) {
        super(ctx);
        init();
    }

    private void init() {
        prettyTime = new PrettyTime();
        PreferenceMap preferenceMap = PreferenceMap.getCurUserPrefDao(ctx);
        location = preferenceMap.getLocation();
    }

    public CommentAdapter(Context ctx, List<AVObject> datas) {
        super(ctx, datas);
        init();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_fragment, null, false);
        }
        final AVObject comment = datas.get(position);
        nameView = ViewHolder.findViewById(convertView, R.id.commenter_name_text);
        TextView contentView = ViewHolder.findViewById(convertView, R.id.comment_content_text);
        avatarView = ViewHolder.findViewById(convertView, R.id.avatar_view);
        String content = (String) comment.get("contents");
        contentView.setText(content);
        //AVUser u = (AVUser) comment.getAVObject("userSend");
        String title = comment.getString("username");
        String avatarUrl = comment.getString("avatarUrl");
        UserService.displayAvatar(avatarUrl, avatarView);
        nameView.setText(title);

        return convertView;
    }


}
