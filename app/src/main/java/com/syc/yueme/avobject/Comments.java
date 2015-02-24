package com.syc.yueme.avobject;

import android.util.Log;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by tyh on 2015/2/24.
 */
@AVClassName("Comments")
public class Comments extends AVObject{
    public static final String USERSEND = "userSend";
    public static final String CONTENTS_COMMENTS = "contents";
    public static final String COMMENTS = "Comments";
    public Comments()
    {
        super(COMMENTS);
    }

    public Comments (User sendUser, String contents)
    {
        super(COMMENTS);
        this.put(CONTENTS_COMMENTS,contents);
        this.put(USERSEND,sendUser);
    }

    public void setContents(String contents)
    {
        this.put(CONTENTS_COMMENTS,contents);
    }

    public void saveCommentsInBackground()
    {
        final String cmtId = this.getObjectId();
        this.saveInBackground(new SaveCallback() {
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


    public void delete()
    {
        final String msgId = this.getObjectId();
        this.deleteInBackground(new DeleteCallback() {
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
