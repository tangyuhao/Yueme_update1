package com.syc.yueme.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import com.avos.avoscloud.*;

import android.widget.Button;
import android.widget.EditText;
import com.syc.yueme.R;
import java.util.Date;
import com.syc.yueme.avobject.AddRequest;
import com.syc.yueme.avobject.ChatGroup;
import com.syc.yueme.avobject.UpdateInfo;
import com.syc.yueme.service.ChatService;
import com.syc.yueme.service.UpdateService;
import com.syc.yueme.ui.activity.SplashActivity;
import com.syc.yueme.ui.fragment.DiscoverFragment;
import com.syc.yueme.util.Logger;
import com.syc.yueme.util.PhotoUtils;
import com.syc.yueme.util.Utils;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import com.syc.yueme.R;
import com.syc.yueme.util.Utils;

/**
 * Created by wangweijia on 2015/2/23.
 */
public class YueUpdateActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yue_update_activity);
        initActionBar("发布状态");
        Button yueUpdate = (Button) findViewById(R.id.btn_yue_update);
        yueUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_yue_update) {

            String content="";
            String location="";
            String time="";

            EditText contenttmp =(EditText)findViewById(R.id.yue_content);
            content=contenttmp.getText().toString();

            EditText locationtmp =(EditText)findViewById(R.id.yue_location);
            location=locationtmp.getText().toString();

            EditText timetmp =(EditText)findViewById(R.id.yue_time);
            time=timetmp.getText().toString();

            AVUser curUser = AVUser.getCurrentUser();


            Date createTime = new Date();
            createTime.getTime();

            AVObject Message = new AVObject("Message");
            Message.put("contents",content);
            Message.put("location", location);
            Message.put("time",time);
           Message.put("sendUser",curUser);
           Message.put("createTime",createTime);

            Message.saveInBackground();
            Utils.toast("success!");
            MainActivity.goMainActivity(YueUpdateActivity.this);
            /*
            Message.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Utils.toast("success!");
                        Utils.goActivity(ctx, DiscoverFragment.class);
                    } else {
                        Utils.toast("fail!");
                    }
                }
            });*/

        }
    }
}
