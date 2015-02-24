package com.syc.yueme.avobject;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by tyh on 2015/2/24.
 */
public class Comments {
    public static final String USERSEND = "userSend";
    public static final String CONTENTS_COMMENTS = "contents";
    public static final String COMMENTS = "Comments";
    public static void setContents(AVObject message, String contents)
    {
        message.put(CONTENTS_COMMENTS,contents);
    }

    public static void saveCommentsInBackground(final AVObject comments)
    {
        final String cmtId = comments.getObjectId();
        comments.saveInBackground(new SaveCallback() {
            public void done(AVException e2) {
                if (e2 == null) {
                    // 保存成功
                    Log.d("保存成功", "comments的Id是" + cmtId);
                } else {
                    // 保存失败，输出错误信息
                    Log.d("保存失败", "错误: " + e2.getMessage());
                }
            }
        });

    }

    public static AVObject createCommentsWithSave(AVUser sendUser, String contents)

    {

        AVObject comments = new AVObject(COMMENTS);
        comments.put(CONTENTS_COMMENTS,contents);
        comments.put(USERSEND,sendUser);
        saveCommentsInBackground(comments);
        return comments;
    }
    public static void deleteComments(AVObject comments)
    {
        final String msgId = comments.getObjectId();
        comments.deleteInBackground(new DeleteCallback() {
            public void done(AVException e2) {
                if (e2 == null) {
                    // 保存成功
                    Log.d("删除成功", "comments的Id是" + msgId);
                } else {
                    // 保存失败，输出错误信息
                    Log.d("删除失败", "错误: " + e2.getMessage());
                }
            }
        });
    }



}
