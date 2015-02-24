package com.syc.yueme.avobject;

import android.util.Log;

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
public class Message {
    public static enum  relationMode_mesg {ADD,REMOVE};
    public static enum  classification {SHENGHUO,WANSHUA,XUEXI,YUNDONG,QITA};
    public static final String SHENGHUO = "生活";
    public static final String WANSHUA = "玩耍";
    public static final String XUEXI = "学习";
    public static final String YUNDONG = "运动";
    public static final String QITA = "其他";
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

    public static boolean createMessage(classification type,String time,String location,
                                        String contents, String numberOfPeople)
    {
        AVObject message = new AVObject("Message");
        String type_string = new String();
        switch (type)
        {
            case SHENGHUO: type_string = SHENGHUO;break;
            case WANSHUA: type_string = WANSHUA;break;
            case XUEXI: type_string = XUEXI;break;
            case YUNDONG: type_string = YUNDONG;break;
            case QITA: type_string = QITA;break;
        }
        message.put(TYPE,type_string);
        message.put(TIME,time);
        message.put(LOCATION_MES,location);
        message.put(CONTENTS_MESG,contents);
        message.put(NUMBEROFPEOPLE,numberOfPeople);
        try {
            message.save();
            return true;
        } catch (AVException e) {
            // e.getMessage() 捕获的异常信息
            //异常则创建失败，返回false
            return false;
        }
    }

    public static boolean createMessage(String type,String time,String location,
                                        String contents, String numberOfPeople,
                                        AVFile image)
    {
        AVObject message = new AVObject("Message");
        message.put(TYPE,type);
        message.put(TIME,time);
        message.put(LOCATION_MES,location);
        message.put(CONTENTS_MESG,contents);
        message.put(NUMBEROFPEOPLE,numberOfPeople);
        message.put(SENDUSER,AVUser.getCurrentUser());
        //TODO 图片上传函数
        try {
            message.save();
            return true;
        } catch (AVException e) {
            // e.getMessage() 捕获的异常信息
            //异常则创建失败，返回false
            return false;
        }
    }


    public static void addYueUser_test(AVObject message,AVUser user) {
        AVRelation<AVObject> relation = message.getRelation(YUEUSER);
        relation.add(user);
    }

    public static void changeYueUserNoSearchSave(AVObject message, relationMode_mesg mode) {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = message.getRelation(YUEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }

    }

    public static void changeLikeUserNoSearchSave(AVObject message, relationMode_mesg mode) {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = message.getRelation(LIKEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public static void changeIgnoreUserNoSearchSave(AVObject message, relationMode_mesg mode) {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = message.getRelation(IGNOREUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }
    }

    public static void changeCommentsNoSearchSave(AVObject message, AVObject comments, relationMode_mesg mode) {
        if (comments != null) Log.d("获得当前COMMENTS",comments.getString(CONTENTS_CMTS));
        AVRelation<AVObject> relation = message.getRelation(COMMENTS);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(comments);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(comments);
        }
    }

    public static void changeSuccessYueUserNoSearchSave(AVObject message, relationMode_mesg mode) {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) Log.d("获得当前USER",user.getUsername());
        AVRelation<AVObject> relation = message.getRelation(SUCCESSYUEUSER);
        if (mode == relationMode_mesg.ADD)
        {
            relation.add(user);
        }
        else if (mode == relationMode_mesg.REMOVE)
        {
            relation.remove(user);
        }    }





    public static void changeYueUserWithSearchSave(final String mesgObjectId,final relationMode_mesg mode)
    {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.getInBackground(mesgObjectId, new GetCallback<AVObject>() {
            public void done(AVObject message, AVException e) {

                if (e == null) {
                    final String msgId = message.getObjectId();
                    Log.d("获取message", "id是" + message.getObjectId());
                    Message.changeYueUserNoSearchSave(message, mode);
                    message.saveInBackground(new SaveCallback() {
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
                } else {
                    Log.d("错误，原因","云端没有这个Id");
                }
            }
        });
    }

    public static void changeLikeUserWithSearchSave(final String mesgObjectId, final relationMode_mesg mode)
    {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.getInBackground(mesgObjectId, new GetCallback<AVObject>() {
            public void done(AVObject message, AVException e) {

                if (e == null) {
                    final String msgId = message.getObjectId();
                    Log.d("获取message", "id是" + message.getObjectId());
                    Message.changeLikeUserNoSearchSave(message, mode);
                    message.saveInBackground(new SaveCallback() {
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
                } else {
                    Log.d("错误，原因","云端没有这个Id");
                }
            }
        });
    }

    public static void changeIgnoreUserWithSearchSave(final String mesgObjectId, final relationMode_mesg mode)
    {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.getInBackground(mesgObjectId, new GetCallback<AVObject>() {
            public void done(AVObject message, AVException e) {

                if (e == null) {
                    final String msgId = message.getObjectId();
                    Log.d("获取message", "id是" + message.getObjectId());
                    Message.changeIgnoreUserNoSearchSave(message, mode);
                    message.saveInBackground(new SaveCallback() {
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
                } else {
                    Log.d("错误，原因","云端没有这个Id");
                }
            }
        });
    }

    public static void changeSuccessYueUserWithSearchSave(final String mesgObjectId, final relationMode_mesg mode)
    {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.getInBackground(mesgObjectId, new GetCallback<AVObject>() {
            public void done(AVObject message, AVException e) {

                if (e == null) {
                    final String msgId = message.getObjectId();
                    Log.d("获取message", "id是" + message.getObjectId());
                    Message.changeSuccessYueUserNoSearchSave(message, mode);
                    message.saveInBackground(new SaveCallback() {
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
                } else {
                    Log.d("错误，原因","云端没有这个Id");
                }
            }
        });
    }
    public static void changeCommentsWithSearchSave(final String mesgObjectId,final AVObject comments, final relationMode_mesg mode)
    {
        AVQuery<AVObject> query = new AVQuery<AVObject>("Message");
        query.getInBackground(mesgObjectId, new GetCallback<AVObject>() {
            public void done(AVObject message, AVException e) {

                if (e == null) {
                    final String msgId = message.getObjectId();
                    Log.d("获取message", "id是" + message.getObjectId());
                    Message.changeCommentsNoSearchSave(message, comments, mode);
                    message.saveInBackground(new SaveCallback() {
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
                } else {
                    Log.d("错误，原因","云端没有这个Id");
                }
            }
        });
    }

    public static void changeYueUserWithSave(final AVObject message, final relationMode_mesg mode)
    {

        if (message != null)
        {
            final String msgId = message.getObjectId();
            Log.d("获取message", "id是" + message.getObjectId());
            Message.changeYueUserNoSearchSave(message, mode);
            message.saveInBackground(new SaveCallback() {
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

    }

    public static void changeLikeUserWithSave(final AVObject message, final relationMode_mesg mode)
    {

        if (message != null)
        {
            final String msgId = message.getObjectId();
            Log.d("获取message", "id是" + message.getObjectId());
            Message.changeLikeUserNoSearchSave(message, mode);
            message.saveInBackground(new SaveCallback() {
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

    }

    public static void changeIgnoreUserWithSave(final AVObject message, final relationMode_mesg mode)
    {

        if (message != null)
        {
            final String msgId = message.getObjectId();
            Log.d("获取message", "id是" + message.getObjectId());
            Message.changeIgnoreUserNoSearchSave(message, mode);
            message.saveInBackground(new SaveCallback() {
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

    }

    public static void changeSuccessYueUserWithSave(final AVObject message, final relationMode_mesg mode)
    {

        if (message != null)
        {
            final String msgId = message.getObjectId();
            Log.d("获取message", "id是" + message.getObjectId());
            Message.changeSuccessYueUserNoSearchSave(message, mode);
            message.saveInBackground(new SaveCallback() {
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

    }


    public static void changeCommentsWithSave(final AVObject message, final  AVObject comments, final relationMode_mesg mode)
    {

        if (message != null)
        {
            final String msgId = message.getObjectId();
            Log.d("获取message", "id是" + message.getObjectId());
            Message.changeCommentsNoSearchSave(message, comments, mode);
            message.saveInBackground(new SaveCallback() {
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

    }

    public static void saveMessageInBackground(final AVObject message)
    {
        final String msgId = message.getObjectId();
        message.saveInBackground(new SaveCallback() {
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

    public static void setType(AVObject message, String type)
    {
        message.put(TYPE,type);
    }

    public static  String getType(AVObject message)
    {
       return message.getString(TYPE);
    }

    public static void setContents(AVObject message, String contents)
    {
        message.put(CONTENTS_MESG,contents);
    }

    public static String getContents(AVObject message)
    {
        return message.getString(CONTENTS_MESG);
    }

    public static void setNumberofpeople(AVObject message, String numberOfPeople)
    {
        message.put(NUMBEROFPEOPLE,numberOfPeople);
    }

    public static String getNumberofpeople(AVObject message)
    {
        return message.getString(NUMBEROFPEOPLE);
    }

    public static void setTime(AVObject message, String time)
    {
        message.put(TIME,time);
    }

    public static String getTime(AVObject message)
    {
        return message.getString(TIME);
    }

    public static void setImage(AVObject message, AVFile image)
    {
        message.put(IMAGE,image);
    }

    public static AVFile getImage(AVObject message)
    {

        AVFile avFile = message.getAVFile(IMAGE);
        avFile.getDataInBackground(new GetDataCallback(){
            public void done(byte[] data, AVException e){
                //process data or exception.
                //TODO
            }
        });
        return avFile;
    }

    public static void deleteMessage(AVObject message)
    {
        final String msgId = message.getObjectId();
        message.deleteInBackground(new DeleteCallback() {
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
