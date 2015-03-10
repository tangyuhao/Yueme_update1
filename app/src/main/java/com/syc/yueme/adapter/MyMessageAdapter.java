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
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.activity.CommentUpdateActivity;
import com.syc.yueme.ui.view.BaseListView;
import com.syc.yueme.ui.view.ViewHolder;
import com.syc.yueme.util.Utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.markushi.ui.CircleButton;

public class MyMessageAdapter extends BaseListAdapter<AVObject> {
    PrettyTime prettyTime;
    AVGeoPoint location;
    BaseListView<AVObject> listView;
    CommentAdapter adapter;
    List<AVObject> nears = new ArrayList<AVObject>();
    TextView nameView;
    ImageView avatarView;
    AVUser user;
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
            convertView = inflater.inflate(R.layout.discover_near_people_item, null, false);
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
        final CircleButton likeButton = ViewHolder.findViewById(convertView, R.id.likeBtn);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.getRelation("likeUser").add(AVUser.getCurrentUser());
                message.saveInBackground();
                AVUser user = AVUser.getCurrentUser();
                User.changeLikeMesg(user, message, User.relationMode_user.ADD);
                user.saveInBackground();

            }
        });
        final CircleButton talkButton = ViewHolder.findViewById(convertView, R.id.talkBtn);
        talkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = getUserChatIntent(ctx,message.getAVUser("sendUser").getObjectId());
//                ctx.startActivity(intent);
//                final AVQuery<AVObject> userQuery = AVRelation.reverseQuery("_User","sendUser",message);
//                userQuery.findInBackground(new FindCallback<AVObject>() {
//                    @Override
//                    public void done(List<AVObject> users, AVException avException) {
//                            Intent intent = getUserChatIntent(ctx,users.("objectId"));
//                            ctx.startActivity(intent);
//
//                    }
//                });
//
            }
        });

        final CircleButton yueButton = ViewHolder.findViewById(convertView, R.id.yueBtn);
        yueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.getRelation("yueUser").add(AVUser.getCurrentUser());
                message.saveInBackground();
                AVUser user = AVUser.getCurrentUser();
                User.changeTryYueMesg(user, message, User.relationMode_user.ADD);
                user.saveInBackground();
            }
        });

        //AVUser u = (AVUser) message.getAVObject("sendUser");
        //nameView.setText(u.getString("username"));
        nameView.setText(message.getString("username"));
        String avatarUrl = message.getString("avatarUrl");
        UserService.displayAvatar(avatarUrl, avatarView);
        Date updatedAt = message.getCreatedAt();
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
