package com.syc.yueme.entity;

/**
 * Created by lzw on 14/12/22.
 */
public interface SendCallback {
  void onError(Exception e);

  void onStart(Msg msg);

  void onSuccess(Msg msg);
}
