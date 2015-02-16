package com.avoscloud.yueme.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVUser;
import com.avoscloud.yueme.R;
import com.avoscloud.yueme.service.*;
import com.avoscloud.yueme.service.receiver.FinishReceiver;
import com.avoscloud.yueme.ui.fragment.ContactFragment;
import com.avoscloud.yueme.ui.fragment.ConversationFragment;
import com.avoscloud.yueme.ui.fragment.DiscoverFragment;
import com.avoscloud.yueme.ui.fragment.MySpaceFragment;
import com.avoscloud.yueme.util.Logger;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by lzw on 14-9-17.
 */
public class MainActivity extends BaseActivity {
  Button conversationBtn, contactBtn, discoverBtn, mySpaceBtn;
  View fragmentContainer;
  ContactFragment contactFragment;
  DiscoverFragment discoverFragment;
  ConversationFragment conversationFragment;
  MySpaceFragment mySpaceFragment;
  public static final int FRAGMENT_N = 4;
  Button[] tabs;
  public static final int[] tabsNormalBackIds = new int[]{R.drawable.tabbar_chat,
      R.drawable.tabbar_contacts, R.drawable.tabbar_discover, R.drawable.tabbar_me};
  public static final int[] tabsActiveBackIds = new int[]{R.drawable.tabbar_chat_active,
      R.drawable.tabbar_contacts_active, R.drawable.tabbar_discover_active,
      R.drawable.tabbar_me_active};
  View recentTips, contactTips;
  public LocationClient locClient;
  public MyLocationListener locationListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    findView();
    init();

    //mySpaceBtn.performClick();
    //contactBtn.performClick();
    conversationBtn.performClick();
    //discoverBtn.performClick();
    initBaiduLocClient();

    //UpdateService.createUpdateInfoInBackground();
    UpdateService updateService = UpdateService.getInstance(this);
    updateService.checkUpdate();
    CacheService.registerUserCache(AVUser.getCurrentUser());
    FinishReceiver.broadcast(this);
    if (AVUser.getCurrentUser() != null) {
      ChatService.openSession();
    }

    //App.initTables();
  }

  public static void goMainActivity(Activity activity) {
    Intent intent = new Intent(activity, MainActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private void initBaiduLocClient() {
    locClient = new LocationClient(this.getApplicationContext());
    locClient.setDebug(true);
    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
    option.setScanSpan(5000);
    option.setIsNeedAddress(false);
    option.setCoorType("bd09ll");
    option.setIsNeedAddress(true);
    locClient.setLocOption(option);

    locationListener = new MyLocationListener();
    locClient.registerLocationListener(locationListener);
    locClient.start();
  }

  public class MyLocationListener implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {
      double latitude = location.getLatitude();
      double longitude = location.getLongitude();
      int locType = location.getLocType();
      Logger.d("onReceiveLocation latitude=" + latitude + " longitude=" + longitude
          + " locType=" + locType + " address=" + location.getAddrStr());
      AVUser user = AVUser.getCurrentUser();
      PreferenceMap preferenceMap = new PreferenceMap(ctx, user.getObjectId());
      if (user != null) {
        AVGeoPoint avGeoPoint = preferenceMap.getLocation();
        if (avGeoPoint != null && avGeoPoint.getLatitude() == location.getLatitude()
            && avGeoPoint.getLongitude() == location.getLongitude()) {
          UserService.updateUserLocation();
          locClient.stop();
          return;
        }
      }
      AVGeoPoint avGeoPoint = new AVGeoPoint(location.getLatitude(),
          location.getLongitude());
      preferenceMap.setLocation(avGeoPoint);
    }
  }

  private void init() {
    tabs = new Button[]{conversationBtn, contactBtn, discoverBtn, mySpaceBtn};
  }

  private void findView() {
    conversationBtn = (Button) findViewById(R.id.btn_message);
    contactBtn = (Button) findViewById(R.id.btn_contact);
    discoverBtn = (Button) findViewById(R.id.btn_discover);
    mySpaceBtn = (Button) findViewById(R.id.btn_my_space);
    fragmentContainer = findViewById(R.id.fragment_container);
    recentTips = findViewById(R.id.iv_recent_tips);
    contactTips = findViewById(R.id.iv_contact_tips);
  }

  public void onTabSelect(View v) {
    int id = v.getId();
    FragmentManager manager = getFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    hideFragments(transaction);
    setNormalBackgrounds();
    if (id == R.id.btn_message) {
      if (conversationFragment == null) {
        conversationFragment = new ConversationFragment();
        transaction.add(R.id.fragment_container, conversationFragment);
      }
      transaction.show(conversationFragment);
    } else if (id == R.id.btn_contact) {
      if (contactFragment == null) {
        contactFragment = new ContactFragment();
        transaction.add(R.id.fragment_container, contactFragment);
      }
      transaction.show(contactFragment);
    } else if (id == R.id.btn_discover) {
      if (discoverFragment == null) {
        discoverFragment = new DiscoverFragment();
        transaction.add(R.id.fragment_container, discoverFragment);
      }
      transaction.show(discoverFragment);
    } else if (id == R.id.btn_my_space) {
      if (mySpaceFragment == null) {
        mySpaceFragment = new MySpaceFragment();
        transaction.add(R.id.fragment_container, mySpaceFragment);
      }
      transaction.show(mySpaceFragment);
    }
    int pos;
    for (pos = 0; pos < FRAGMENT_N; pos++) {
      if (tabs[pos] == v) {
        break;
      }
    }
    transaction.commit();
    setTopDrawable(tabs[pos], tabsActiveBackIds[pos]);
  }

  private void setNormalBackgrounds() {
    for (int i = 0; i < tabs.length; i++) {
      Button v = tabs[i];
      setTopDrawable(v, tabsNormalBackIds[i]);
    }
  }

  private void setTopDrawable(Button v, int resId) {
    v.setCompoundDrawablesWithIntrinsicBounds(null, ctx.getResources().getDrawable(resId), null, null);
  }

  private void hideFragments(FragmentTransaction transaction) {
    Fragment[] fragments = new Fragment[]{
        conversationFragment, contactFragment,
        discoverFragment, mySpaceFragment
    };
    for (Fragment f : fragments) {
      if (f != null) {
        transaction.hide(f);
      }
    }
  }
}
