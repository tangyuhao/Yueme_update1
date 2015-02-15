package com.avoscloud.Yueme.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import com.alibaba.fastjson.JSONException;
import com.avos.avoscloud.*;
import com.avoscloud.Yueme.avobject.ChatGroup;
import com.avoscloud.Yueme.avobject.User;
import com.avoscloud.Yueme.base.App;
import com.avoscloud.Yueme.db.DBHelper;
import com.avoscloud.Yueme.db.DBMsg;
import com.avoscloud.Yueme.entity.Conversation;
import com.avoscloud.Yueme.entity.Msg;
import com.avoscloud.Yueme.entity.RoomType;
import com.avoscloud.Yueme.service.listener.MsgListener;
import com.avoscloud.Yueme.service.receiver.MsgReceiver;
import com.avoscloud.Yueme.ui.activity.ChatActivity;
import com.avoscloud.Yueme.util.*;

import java.io.File;
import java.util.*;

/**
 * Created by lzw on 14-7-9.
 */
public class ChatService {
  private static final int REPLY_NOTIFY_ID = 1;
  private static final long NOTIFY_PEROID = 1000;
  static long lastNotifyTime = 0;

  public static <T extends AVUser> String getPeerId(T user) {
    return user.getObjectId();
  }

  public static String getSelfId() {
    return getPeerId(AVUser.getCurrentUser());
  }

  public static <T extends com.avos.avoscloud.AVUser> void withUsersToWatch(List<T> users, boolean watch) {
    List<String> peerIds = new ArrayList<String>();
    for (com.avos.avoscloud.AVUser user : users) {
      peerIds.add(getPeerId(user));
    }
    String selfId = getPeerId(AVUser.getCurrentUser());
    Session session = SessionManager.getInstance(selfId);
    if (watch) {
      session.watchPeers(peerIds);
    } else {
      session.unwatchPeers(peerIds);
    }
  }

  public static <T extends com.avos.avoscloud.AVUser> void withUserToWatch(T user, boolean watch) {
    List<T> users = new ArrayList<T>();
    users.add(user);
    withUsersToWatch(users, watch);
  }

  public static Session getSession() {
    return SessionManager.getInstance(getPeerId(AVUser.getCurrentUser()));
  }

  public static void openSession() {
    Session session = getSession();
    session.setSignatureFactory(new SignatureFactory());
    session.open(new LinkedList<String>());
  }

  public static List<Conversation> getConversationsAndCache() throws AVException {
    List<Msg> msgs = DBMsg.getRecentMsgs(User.getCurrentUserId());
    cacheUserOrChatGroup(msgs);
    ArrayList<Conversation> conversations = new ArrayList<Conversation>();
    DBHelper dbHelper = new DBHelper(App.ctx, App.DB_NAME, App.DB_VER);
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    for (Msg msg : msgs) {
      Conversation conversation = new Conversation();
      if (msg.getRoomType() == RoomType.Single) {
        String chatUserId = msg.getOtherId();
        conversation.setToUser(CacheService.lookupUser(chatUserId));
      } else {
        conversation.setToChatGroup(CacheService.lookupChatGroup(msg.getConvid()));
      }
      int unreadCount = DBMsg.getUnreadCount(db, msg.getConvid());
      conversation.setMsg(msg);
      conversation.setUnreadCount(unreadCount);
      conversations.add(conversation);
    }
    db.close();
    return conversations;
  }

  public static void cacheUserOrChatGroup(List<Msg> msgs) throws AVException {
    Set<String> userIds = new HashSet<String>();
    Set<String> groupIds = new HashSet<String>();
    for (Msg msg : msgs) {
      if (msg.getRoomType() == RoomType.Single) {
        userIds.add(msg.getToPeerId());
      } else {
        String groupId = msg.getConvid();
        groupIds.add(groupId);
      }
      userIds.add(msg.getFromPeerId());
    }
    CacheService.cacheUserAndGet(new ArrayList<String>(userIds));
    GroupService.cacheChatGroups(new ArrayList<String>(groupIds));
  }

  public static void closeSession() {
    Session session = ChatService.getSession();
    session.close();
  }

  public static Group getGroupById(String groupId) {
    return getSession().getGroup(groupId);
  }

  public static void notifyMsg(Context context, Msg msg, Group group) throws JSONException {
    if (System.currentTimeMillis() - lastNotifyTime < NOTIFY_PEROID) {
      return;
    } else {
      lastNotifyTime = System.currentTimeMillis();
    }
    int icon = context.getApplicationInfo().icon;
    Intent intent;
    if (group == null) {
      intent = ChatActivity.getUserChatIntent(context, msg.getFromPeerId());
    } else {
      intent = ChatActivity.getGroupChatIntent(context, group.getGroupId());
    }
    //why Random().nextInt()
    //http://stackoverflow.com/questions/13838313/android-onnewintent-always-receives-same-intent
    PendingIntent pend = PendingIntent.getActivity(context, new Random().nextInt(),
        intent, 0);
    Notification.Builder builder = new Notification.Builder(context);
    CharSequence notifyContent = msg.getNotifyContent();
    CharSequence username = msg.getFromName();
    builder.setContentIntent(pend)
        .setSmallIcon(icon)
        .setWhen(System.currentTimeMillis())
        .setTicker(username + "\n" + notifyContent)
        .setContentTitle(username)
        .setContentText(notifyContent)
        .setAutoCancel(true);
    NotificationManager man = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    Notification notification = builder.getNotification();
    PreferenceMap preferenceMap = PreferenceMap.getCurUserPrefDao(context);
    if (preferenceMap.isVoiceNotify()) {
      notification.defaults |= Notification.DEFAULT_SOUND;
    }
    if (preferenceMap.isVibrateNotify()) {
      notification.defaults |= Notification.DEFAULT_VIBRATE;
    }
    man.notify(REPLY_NOTIFY_ID, notification);
  }

  public static void onMessage(Context context, AVMessage avMsg, Set<MsgListener> listeners, Group group) {
    final Msg msg = Msg.fromAVMessage(avMsg);
    String convid;
    if (group == null) {
      String selfId = getSelfId();
      msg.setToPeerId(selfId);
      convid = ChatUtils.convid(selfId, msg.getFromPeerId());
      msg.setRoomType(RoomType.Single);
    } else {
      convid = group.getGroupId();
      msg.setRoomType(RoomType.Group);
    }
    msg.setStatus(Msg.Status.SendReceived);
    msg.setReadStatus(Msg.ReadStatus.Unread);
    msg.setConvid(convid);
    handleReceivedMsg(context, msg, listeners, group);
  }

  public static void handleReceivedMsg(final Context context, final Msg msg, final Set<MsgListener> listeners, final Group group) {
    new NetAsyncTask(context, false) {
      @Override
      protected void doInBack() throws Exception {
        if (msg.getType() == Msg.Type.Audio) {
          File file = new File(msg.getAudioPath());
          String url = msg.getContent();
          Utils.downloadFileIfNotExists(url, file);
        }
        if (group != null) {
          GroupService.cacheChatGroupIfNone(group.getGroupId());
        }
        String fromId = msg.getFromPeerId();
        UserService.cacheUserIfNone(fromId);
      }

      @Override
      protected void onPost(Exception e) {
        if (e != null) {
          Utils.toast(context, com.avoscloud.Yueme.R.string.badNetwork);
        } else {
          DBMsg.insertMsg(msg);
          String otherId = getOtherId(msg.getFromPeerId(), group);
          boolean done = false;
          for (MsgListener listener : listeners) {
            if (listener.onMessageUpdate(otherId)) {
              done = true;
              break;
            }
          }
          if (!done) {
            if (AVUser.getCurrentUser() != null) {
              PreferenceMap preferenceMap = PreferenceMap.getCurUserPrefDao(context);
              if (preferenceMap.isNotifyWhenNews()) {
                notifyMsg(context, msg, group);
              }
            }
          }
        }
      }
    }.execute();
  }

  private static String getOtherId(String otherId, Group group) {
    assert otherId != null || group != null;
    if (group != null) {
      return group.getGroupId();
    } else {
      return otherId;
    }
  }

  public static void onMessageSent(AVMessage avMsg, Set<MsgListener> listeners, Group group) {
    Msg msg = Msg.fromAVMessage(avMsg);
    DBMsg.updateStatusAndTimestamp(msg.getObjectId(), Msg.Status.SendSucceed, msg.getTimestamp());
    String otherId = getOtherId(msg.getToPeerId(), group);
    for (MsgListener msgListener : listeners) {
      if (msgListener.onMessageUpdate(otherId)) {
        break;
      }
    }
  }

  public static void onMessageFailure(AVMessage avMsg, Set<MsgListener> msgListeners, Group group) {
    Msg msg = Msg.fromAVMessage(avMsg);
    DBMsg.updateStatus(msg.getObjectId(), Msg.Status.SendFailed);
    String otherId = getOtherId(msg.getToPeerId(), group);
    for (MsgListener msgListener : msgListeners) {
      if (msgListener.onMessageUpdate(otherId)) {
        break;
      }
    }
  }

  public static void onMessageError(Throwable throwable, Set<MsgListener> msgListeners) {
    String errorMsg = throwable.getMessage();
    Logger.d("error " + errorMsg);
    if (errorMsg != null && errorMsg.startsWith("{")) {
      AVMessage avMsg = new AVMessage(errorMsg);
      //onMessageFailure(avMsg, msgListeners, group);
    }
  }

  public static List<AVUser> findGroupMembers(ChatGroup chatGroup) throws AVException {
    List<String> members = chatGroup.getMembers();
    return CacheService.findUsers(members);
  }

  public static void cancelNotification(Context ctx) {
    Utils.cancelNotification(ctx, REPLY_NOTIFY_ID);
  }

  public static void onMessageDelivered(AVMessage avMsg, Set<MsgListener> listeners) {
    Msg msg = Msg.fromAVMessage(avMsg);
    DBMsg.updateStatus(msg.getObjectId(), Msg.Status.SendReceived);
    String otherId = msg.getToPeerId();
    for (MsgListener listener : listeners) {
      if (listener.onMessageUpdate(otherId)) {
        break;
      }
    }
  }

  public static boolean isSessionPaused() {
    return MsgReceiver.isSessionPaused();
  }
}
