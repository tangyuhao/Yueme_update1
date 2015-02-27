package com.syc.yueme.service;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.syc.yueme.avobject.Message;
import com.syc.yueme.avobject.User;
import com.syc.yueme.base.App;
import com.syc.yueme.base.C;
import com.syc.yueme.ui.activity.CommentUpdateActivity;
import com.syc.yueme.util.Logger;
import com.syc.yueme.util.PhotoUtils;
import com.syc.yueme.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 14-9-15.
 */
public class MessageService {
    public static List<AVUser> findFriends() throws AVException {
        AVUser curUser = AVUser.getCurrentUser();
        AVRelation<AVUser> relation = curUser.getRelation(User.FRIENDS);
        relation.setTargetClass("_User");
        AVQuery<AVUser> query = relation.getQuery(AVUser.class);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVUser> users = query.find();
        return users;
    }



    public static List<AVObject> findMessage(AVUser user, int skip, int limit) throws AVException {
        AVRelation relation = user.getRelation("sendMesg");
        relation.setTargetClass("Message");
        AVQuery<AVObject> query = relation.getQuery();
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVObject> msgs = query.find();
        //AVQuery<Message> q = AVObject.getQuery(Message.class);

        return msgs;
    }
    public static List<AVObject> findYingMessage(AVUser user, int skip, int limit) throws AVException {
        AVRelation relation = user.getRelation("tryYueMesg");
        relation.setTargetClass("Message");
        AVQuery<AVObject> query = relation.getQuery();
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVObject> msgs = query.find();
        //AVQuery<Message> q = AVObject.getQuery(Message.class);

        return msgs;
    }


    public static List<AVObject> findCommentsByMsg(AVObject msg, int skip, int limit) throws AVException {
        AVRelation relation = msg.getRelation("comments");
        relation.setTargetClass("Comments");
        AVQuery<AVObject> query = relation.getQuery();
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);

        List<AVObject> comments = query.find();
        for (AVObject com : comments) {
            com.getAVObject("userSend").fetch();
        }
        return comments;
    }
    public static List<AVObject> findCommentsByMsg2(AVObject msg, int skip, int limit) throws AVException {

        AVQuery<AVObject> query =  AVRelation.reverseQuery("Comments", "BelongMsg", msg);
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVObject> comments = query.find();
        for (AVObject com : comments) {
            com.getAVObject("userSend").fetch();
        }
        return comments;
    }

    public static List<AVObject> findMsg(int skip, int limit) throws AVException {
        AVQuery<AVObject> q = new AVQuery<AVObject>("Message");
        q.orderByDescending("createdAt");
        q.skip(skip);
        q.limit(limit);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVObject> message = q.find();
        for (AVObject msg : message) {
            // msg.getAVObject("sendUser").fetch();
        }
        return message;
    }

}
