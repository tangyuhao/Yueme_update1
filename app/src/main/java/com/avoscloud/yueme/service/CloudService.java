package com.avoscloud.yueme.service;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw on 14-9-29.
 */
public class CloudService {
  public static void removeFriendForBoth(AVUser toUser, AVUser fromUser) throws AVException {
    callCloudRelationFn(toUser, fromUser, "removeFriend");
  }

  public static void callCloudRelationFn(AVUser toUser, AVUser fromUser, String functionName) throws AVException {
    Map<String, Object> map = usersParamsMap(fromUser, toUser);
    Object res = AVCloud.callFunction(functionName, map);
  }

  public static Map<String, Object> usersParamsMap(AVUser fromUser, AVUser toUser) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("fromUserId", fromUser.getObjectId());
    map.put("toUserId", toUser.getObjectId());
    return map;
  }

  public static void tryCreateAddRequest(AVUser toUser) throws AVException {
    AVUser user = AVUser.getCurrentUser();
    Map<String, Object> map = usersParamsMap(user, toUser);
    AVCloud.callFunction("tryCreateAddRequest", map);
  }

  public static void agreeAddRequest(String objectId) throws AVException {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("objectId", objectId);
    AVCloud.callFunction("agreeAddRequest", map);
  }

  public static void saveChatGroup(String groupId, String ownerId, String name) throws AVException {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("groupId", groupId);
    map.put("ownerId", ownerId);
    map.put("name", name);
    AVCloud.callFunction("saveChatGroup", map);
  }

  public static HashMap<String, Object> sign(String selfId, List<String> watchIds) throws AVException {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("self_id", selfId);
    map.put("watch_ids", watchIds);
    return (HashMap<String,Object>)AVCloud.callFunction("sign", map);
  }

  public static HashMap<String, Object> groupSign(String selfId, String groupId, List<String> peerIds, String action) throws AVException {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("self_id", selfId);
    map.put("group_id", groupId);
    map.put("group_peer_ids", peerIds);
    map.put("action", action);
    return (HashMap<String,Object>)AVCloud.callFunction("group_sign", map);
  }
}
