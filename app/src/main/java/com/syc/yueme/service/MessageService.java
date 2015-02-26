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
    public static final int ORDER_UPDATED_AT = 1;
    public static final int ORDER_DISTANCE = 0;
    public static ImageLoader imageLoader = ImageLoader.getInstance();

    public static AVUser findUser(String id) throws AVException {
        AVQuery<AVUser> q = AVUser.getQuery(AVUser.class);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ONLY);
        q.find();
        return q.get(id);
    }

    public static List<AVUser> findFriends() throws AVException {
        AVUser curUser = AVUser.getCurrentUser();
        AVRelation<AVUser> relation = curUser.getRelation(User.FRIENDS);
        relation.setTargetClass("_User");
        AVQuery<AVUser> query = relation.getQuery(AVUser.class);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVUser> users = query.find();
        return users;
    }


    public static void displayAvatar(String imageUrl, ImageView avatarView) {
        imageLoader.displayImage(imageUrl, avatarView, PhotoUtils.avatarImageOptions);
    }

    public static void displayAvatar(AVUser user, ImageView avatarView) {
        if (user != null) {
            String avatarUrl = User.getAvatarUrl(user);
            if (TextUtils.isEmpty(avatarUrl) == false) {
                displayAvatar(avatarUrl, avatarView);
            }
        }
    }

    public static List<AVUser> searchUser(String searchName, int skip) throws AVException {
        AVQuery<AVUser> q = AVUser.getQuery(AVUser.class);
        q.whereContains(User.USERNAME, searchName);
        q.limit(C.PAGE_SIZE);
        q.skip(skip);
        AVUser user = AVUser.getCurrentUser();
        List<String> friendIds = new ArrayList<String>(CacheService.getFriendIds());
        friendIds.add(user.getObjectId());
        q.whereNotContainedIn(C.OBJECT_ID, friendIds);
        q.orderByDescending(C.UPDATED_AT);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVUser> users = q.find();
        CacheService.registerBatchUser(users);
        return users;
    }


    public static List<AVObject> findMessage(int skip, int limit) throws AVException {
        Log.d(Message.class + "", "  msg");
        AVQuery<AVObject> q = new AVQuery<AVObject>("Message");

        //AVQuery<Message> q = AVObject.getQuery(Message.class);
        q.skip(skip);
        q.limit(limit);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        q.orderByDescending("createdAt");
        List<AVObject> message = q.find();

        return message;
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

    public static List<AVObject> findComments(int skip, int limit) throws AVException {
        AVQuery<AVObject> q = new AVQuery<AVObject>("Comments");
        q.skip(skip);
        q.limit(limit);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVObject> comments = q.find();
        return comments;
    }

    public static List<AVObject> findCommentsByMsg(AVObject msg) throws AVException {
        AVRelation relation = msg.getRelation("comments");
        relation.setTargetClass("Comments");
        AVQuery<AVObject> query = relation.getQuery();
        List<AVObject> comments = query.find();
        CommentUpdateActivity.users = new ArrayList<AVUser>();
        for (AVObject com : comments) {
            CommentUpdateActivity.users.add((AVUser) com.getAVObject("userSend").fetch());
        }
        return comments;
    }

    public static List<AVObject> findMsg(int skip, int limit) throws AVException {
        AVQuery<AVObject> q = new AVQuery<AVObject>("Message");
        q.skip(skip);
        q.limit(limit);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<AVObject> message = q.find();
        for (AVObject msg : message) {
            msg.getAVObject("sendUser").fetch();
        }
        return message;
    }


    public static void cacheUserIfNone(String userId) throws AVException {
        if (CacheService.lookupUser(userId) == null) {
            CacheService.registerUserCache(findUser(userId));
        }
    }

    public static void saveAvatar(String path) throws IOException, AVException {
        AVUser user = AVUser.getCurrentUser();
        final AVFile file = AVFile.withAbsoluteLocalPath(user.getUsername(), path);
        file.save();
        user.put(User.AVATAR, file);

        user.save();
        user.fetch();
    }

    public static void updateUserInfo() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            AVInstallation installation = AVInstallation.getCurrentInstallation();
            if (installation != null) {
                user.put(User.INSTALLATION, installation);
                user.saveInBackground();
            }
        }
    }

    public static void updateUserLocation() {
        PreferenceMap preferenceMap = PreferenceMap.getCurUserPrefDao(App.ctx);
        AVGeoPoint lastLocation = preferenceMap.getLocation();
        if (lastLocation != null) {
            final AVUser user = AVUser.getCurrentUser();
            final AVGeoPoint location = user.getAVGeoPoint(User.LOCATION);
            if (location == null || !Utils.doubleEqual(location.getLatitude(), lastLocation.getLatitude())
                    || !Utils.doubleEqual(location.getLongitude(), lastLocation.getLongitude())) {
                user.put(User.LOCATION, lastLocation);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Logger.v("lastLocation save " + user.getAVGeoPoint(User.LOCATION));
                        }
                    }
                });
            }
        }
    }
}
