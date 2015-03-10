package com.syc.yueme.service;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.syc.yueme.adapter.UserFriendAdapter;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.skip(skip);
        query.limit(limit);
        query.whereEqualTo("sendUser", user);
        query.orderByDescending("createdAt");
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVObject> msgs = query.find();
        return msgs;
    }


    public static List<AVObject> findYingMessage(AVUser user, int skip, int limit) throws AVException {
        AVQuery<AVObject> message_query = new AVQuery<AVObject>("Message");
        List<AVObject> message_list = message_query.find();
        message_query.skip(skip);
        message_query.limit(limit);
        message_query.orderByDescending("createdAt");
        message_query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        int index = message_list.size();
        String uname = user.getUsername();
        Log.i("新的总的message数", String.valueOf(index));


        for(int i = 0; i < message_list.size(); i ++)
        {
            AVObject msg = message_list.get(i);

            AVRelation relation = msg.getRelation("yueUser");
            AVQuery<AVObject> yueUser_query = relation.getQuery();

            yueUser_query.whereEqualTo("username",uname);

            if(yueUser_query.find().size() == 0) {
                message_list.remove(msg);
                i --;
            }
        }
        return message_list;



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

    public static List<AVObject> findeattypeByMsg(int skip, int limit) throws AVException {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.whereEqualTo("type", "美食");
        query.orderByDescending("createdAt");
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);

        List<AVObject> message = query.find();
        for (AVObject msg : message) {
            // msg.getAVObject("sendUser").fetch();
        }
        return message;
    }
    public static List<AVObject> findplaytypeByMsg(int skip, int limit) throws AVException {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.whereEqualTo("type", "娱乐");
        query.orderByDescending("createdAt");
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);

        List<AVObject> message = query.find();
        for (AVObject msg : message) {
            // msg.getAVObject("sendUser").fetch();
        }
        return message;
    }
    public static List<AVObject> findsporttypeByMsg(int skip, int limit) throws AVException {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.whereEqualTo("type", "运动");
        query.orderByDescending("createdAt");
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);

        List<AVObject> message = query.find();
        for (AVObject msg : message) {
            // msg.getAVObject("sendUser").fetch();
        }
        return message;
    }
    public static List<AVObject> findstudytypeByMsg(int skip, int limit) throws AVException {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.whereEqualTo("type", "学习");
        query.orderByDescending("createdAt");
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);

        List<AVObject> message = query.find();
        for (AVObject msg : message) {
            // msg.getAVObject("sendUser").fetch();
        }
        return message;
    }
    public static List<AVObject> findothertypeByMsg(int skip, int limit) throws AVException {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.whereEqualTo("type", "其他");
        query.orderByDescending("createdAt");
        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);

        List<AVObject> message = query.find();
        for (AVObject msg : message) {
            // msg.getAVObject("sendUser").fetch();
        }
        return message;
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
