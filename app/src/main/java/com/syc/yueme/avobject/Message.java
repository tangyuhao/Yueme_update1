package com.syc.yueme.avobject;

import android.util.Log;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.Objects;

/**
 * Created by tyh on 2015/2/23.
 */
@AVClassName("Message")
public class Message extends AVObject{
    public static enum  relationMode_mesg {ADD,REMOVE};
    public static enum  classification {SHENGHUO,WANSHUA,XUEXI,YUNDONG,QITA};
    public static final String SHENGHUO = "生活";
    public static final String WANSHUA = "玩耍";
    public static final String XUEXI = "学习";
    public static final String YUNDONG = "运动";
    public static final String QITA = "其他";
    public static final String MESSAGE = "Message";
    public static final String TYPE = "type";
    public static final String TIME = "time";
    public static final String LOCATION_MES = "location";
    public static final String CONTENTS_MESG = "contents";//约信息的内容
    public static final String CONTENTS_CMTS = "contents";//评论对应的内容
    public static final String CREATEAT = "createAt";
    public static final String NUMBEROFPEOPLE = "numberOfPeople";
    public static final String IMAGE = "image";
    public static final String SENDUSER = "sendUser";
    public static final String LIKEUSER = "likeUser";
    public static final String IGNOREUSER = "ignoreUser";
    public static final String YUEUSER = "yueUser";
    public static final String SUCCESSYUEUSER = "successYueUser";
    public static final String COMMENTS = "comments";
    public static final String SHOW = "show";

    public Message()
    {
        super(MESSAGE);
    }

    public Message(classification type,String time,String location,
                   String contents, String numberOfPeople)
    {
        super(MESSAGE);
        String type_string = new String();
        switch (type)
        {
            case SHENGHUO: type_string = SHENGHUO;break;
            case WANSHUA: type_string = WANSHUA;break;
            case XUEXI: type_string = XUEXI;break;
            case YUNDONG: type_string = YUNDONG;break;
            case QITA: type_string = QITA;break;
        }
        this.put(TYPE,type_string);
        this.put(TIME,time);
        this.put(LOCATION_MES,location);
        this.put(CONTENTS_MESG,contents);
        this.put(SENDUSER,AVUser.getCurrentUser());
        this.put(NUMBEROFPEOPLE,numberOfPeople);
//        try {
//            this.save();
//        } catch (AVException e) {
//            // e.getMessage() 捕获的异常信息
//            //异常则创建失败，返回false
//        }
    }

    public Message(classification type,String time,String location,
                   String contents, String numberOfPeople,AVFile image)
    {
        super(MESSAGE);
        String type_string = new String();
        switch (type)
        {
            case SHENGHUO: type_string = SHENGHUO;break;
            case WANSHUA: type_string = WANSHUA;break;
            case XUEXI: type_string = XUEXI;break;
            case YUNDONG: type_string = YUNDONG;break;
            case QITA: type_string = QITA;break;
        }
        this.put(TYPE,type_string);
        this.put(TIME,time);
        this.put(LOCATION_MES,location);
        this.put(CONTENTS_MESG,contents);
        this.put(SENDUSER,AVUser.getCurrentUser());
        this.put(NUMBEROFPEOPLE,numberOfPeople);
        this.put(IMAGE,image);
//        try {
//            this.save();
//        } catch (AVException e) {
//            // e.getMessage() 捕获的异常信息
//            //异常则创建失败，返回false
//        }
    }

    public void changeYueUser(relationMode_mesg mode) {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = this.getRelation(YUEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public void changeYueUser(relationMode_mesg mode, AVUser user) {
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = this.getRelation(YUEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public void changeLikeUser(relationMode_mesg mode) {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = this.getRelation(LIKEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public void changeLikeUser(relationMode_mesg mode, AVUser user) {
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = this.getRelation(LIKEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public void changeIgnoreUser(relationMode_mesg mode) {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = this.getRelation(IGNOREUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public void changeIgnoreUser(relationMode_mesg mode, AVUser user) {
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = this.getRelation(IGNOREUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public void changeSuccessYueUser(relationMode_mesg mode) {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = this.getRelation(SUCCESSYUEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public void changeSuccessYueUser(relationMode_mesg mode, AVUser user) {
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = this.getRelation(SUCCESSYUEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }





    public void changeComments(AVObject comments, relationMode_mesg mode) {
        if (comments != null) Log.d("获得当前COMMENTS",comments.getString(CONTENTS_CMTS));
        AVRelation<AVObject> relation = this.getRelation(COMMENTS);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(comments);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(comments);
        }
    }

    public void saveMessageInBackground()
    {
        final String msgId = this.getObjectId();
        this.saveInBackground(new SaveCallback() {
            public void done(AVException e2) {
                if (e2 == null) {
                    // 保存成功
                    Log.d("保存成功", "message的Id是" + msgId);
                } else {
                    // 保存失败，输出错误信息
                    Log.d("保存失败", "错误: " + e2.getMessage());
                }
            }
        });

    }

    public void setLocation(String location)
    {
        this.put(LOCATION_MES,location);
    }

    public String getLocation()

    {
       return this.getString(LOCATION_MES);
    }

    public void setType(String type)
    {
        this.put(TYPE,type);
    }

    public String getType()

    {
        return this.getString(TYPE);
    }

    public void setContents(String contents)
    {
        this.put(CONTENTS_MESG,contents);
    }

    public String getContents()
    {
        return this.getString(CONTENTS_MESG);
    }

    public void setNumberofpeople(String numberOfPeople)
    {
        this.put(NUMBEROFPEOPLE,numberOfPeople);
    }

    public String getNumberofpeople()
    {
        return this.getString(NUMBEROFPEOPLE);
    }

    public void setTime(String time)
    {
        this.put(TIME,time);
    }

    public String getTime()
    {
        return this.getString(TIME);
    }

    public void setImage( AVFile image)

    {
        this.put(IMAGE,image);
    }

    public AVFile getImage()
    {

        AVFile avFile = this.getAVFile(IMAGE);
        avFile.getDataInBackground(new GetDataCallback(){
            public void done(byte[] data, AVException e){
                //process data or exception.
                //TODO
            }
        });
        return avFile;
    }

    public void delete()
    {
        final String msgId = this.getObjectId();
        this.deleteInBackground(new DeleteCallback() {
            public void done(AVException e2) {
                if (e2 == null) {
                    // 保存成功
                    Log.d("删除成功", "message的Id是" + msgId);
                } else {
                    // 保存失败，输出错误信息
                    Log.d("删除失败", "错误: " + e2.getMessage());
                }
            }
        });
    }
}
