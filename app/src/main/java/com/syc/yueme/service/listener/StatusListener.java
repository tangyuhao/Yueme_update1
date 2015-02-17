package com.syc.yueme.service.listener;

import java.util.List;

public interface StatusListener {
  public void onStatusOnline(List<String> peerIds);
}
