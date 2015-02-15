package com.avoscloud.Yueme.util;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lzw on 14-6-28.
 */
public class TimeUtils {
  public static PrettyTime prettyTime = new PrettyTime();

  public static String getDate(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
    return format.format(date);
  }

  public static String millisecs2DateString(long timestamp) {
    long gap = System.currentTimeMillis() - timestamp;
    if (gap < 1000 * 60 * 60 * 24) {
      String s = prettyTime.format(new Date(timestamp));
      //return s.replace(" ", "");
      return s;
    } else {
      return getDate(new Date(timestamp));
    }
  }

  public static boolean haveTimeGap(long lastTime, long time) {
    int gap = 1000 * 60 * 3;
    return time - lastTime > gap;
  }
}
