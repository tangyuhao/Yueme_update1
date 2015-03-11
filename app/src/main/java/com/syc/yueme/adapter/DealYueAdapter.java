package com.syc.yueme.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class DealYueAdapter extends BaseListAdapter<AVObject> {
    PrettyTime prettyTime;
    List<AVObject> nears = new ArrayList<AVObject>();
    TextView nameView;
    ImageView avatarView;
    public static final int lightblue=0xff0000ff;
    public static final int red = 0xffff0000;
    public DealYueAdapter(Context ctx) {
        super(ctx);
    }


    public DealYueAdapter(Context ctx, List<AVObject> datas) {
        super(ctx, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.deal_with_yue_item, null, false);
        }
        final AVObject message = datas.get(position);
        // get and set the name, content, time, publish_time, location
        nameView = ViewHolder.findViewById(convertView, R.id.name_text);
        final Button yueBtn = ViewHolder.findViewById(convertView, R.id.yueBtn);
        final Button noYueBtn = ViewHolder.findViewById(convertView, R.id.noyueBtn);
        avatarView = ViewHolder.findViewById(convertView, R.id.avatar_view);
        nameView.setText(message.getString("username"));
        String avatarUrl = User.getAvatarUrl((AVUser)message);
        UserService.displayAvatar(avatarUrl, avatarView);
        yueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yueBtn.setBackgroundColor(lightblue);
                noYueBtn.setBackgroundColor(red);
                DealWithYueActivity.msg.getRelation("successYueUser").add(message);
                DealWithYueActivity.msg.saveInBackground();
            }
        });

        noYueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yueBtn.setBackgroundColor(red);
                noYueBtn.setBackgroundColor(lightblue);
            }
        });

        return convertView;
    }

}