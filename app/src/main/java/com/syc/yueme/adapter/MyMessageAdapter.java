package com.syc.yueme.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.activity.CommentUpdateActivity;
import com.syc.yueme.ui.activity.DealWithYueActivity;
import com.syc.yueme.ui.view.ViewHolder;
import com.syc.yueme.util.Utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.markushi.ui.CircleButton;

public class MyMessageAdapter extends BaseListAdapter<AVObject> {
    PrettyTime prettyTime;
    List<AVObject> nears = new ArrayList<AVObject>();
    TextView nameView;
    ImageView avatarView;
    public static AVObject which_msg;
    public MyMessageAdapter(Context ctx) {
        super(ctx);
        init();
    }

    private void init() {
        prettyTime = new PrettyTime();
    }

    public MyMessageAdapter(Context ctx, List<AVObject> datas) {
        super(ctx, datas);
        init();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_message_item, null, false);
        }
        final AVObject message = datas.get(position);
        // get and set the name, content, time, publish_time, location
        nameView = ViewHolder.findViewById(convertView, R.id.name_text);
        TextView publishTimeView = ViewHolder.findViewById(convertView, R.id.publish_time_text);
        TextView locationView = ViewHolder.findViewById(convertView, R.id.location_text);
        TextView yueTimeView = ViewHolder.findViewById(convertView, R.id.login_text);
        final TextView yueContentView = ViewHolder.findViewById(convertView, R.id.yue_text);
        avatarView = ViewHolder.findViewById(convertView, R.id.avatar_view);


        final CircleButton commentBtn = ViewHolder.findViewById(convertView, R.id.commentBtn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_msg = message;
                Utils.goActivity(ctx, CommentUpdateActivity.class);
            }
        });

        final TextView dealBtn = ViewHolder.findViewById(convertView, R.id.distance_text);
        dealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealWithYueActivity.msg = message;
                Utils.goActivity(ctx, DealWithYueActivity.class);
            }
        });


        AVUser u = AVUser.getCurrentUser();
        nameView.setText(u.getString("username"));
        String avatarUrl = User.getAvatarUrl(u);
        UserService.displayAvatar(avatarUrl, avatarView);
        Date updatedAt = message.getUpdatedAt();
        // get the content
        String yueTime = (String) message.get("time");
        String prettyTimeStr = this.prettyTime.format(updatedAt);
        String contents = (String) message.get("contents");
        String where = (String) message.get("location");
        yueContentView.setText(contents);
        publishTimeView.setText("发布:" + prettyTimeStr);
        yueTimeView.setText("时间:" + yueTime);
        locationView.setText("地点:" + where);

        return convertView;
    }

}