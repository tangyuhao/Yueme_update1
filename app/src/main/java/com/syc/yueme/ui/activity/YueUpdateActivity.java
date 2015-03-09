package com.syc.yueme.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import com.avos.avoscloud.*;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.syc.yueme.R;

import java.util.Date;

import com.syc.yueme.avobject.AddRequest;
import com.syc.yueme.avobject.ChatGroup;
import com.syc.yueme.avobject.UpdateInfo;
import com.syc.yueme.avobject.User;
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
public class YueUpdateActivity extends BaseActivity implements View.OnClickListener {
    private Spinner typeSpinner = null;// 学校选择
    ArrayAdapter<String> typeAdapter = null; //学校选择适配器
    private String[] types = null;//存放学校内容的数组
    String type = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yue_update_activity);
        initActionBar("发布状态");
        Button yueUpdate = (Button) findViewById(R.id.btn_yue_update);
        yueUpdate.setOnClickListener(this);

        types = getResources().getStringArray(R.array.type_array);
        //下拉框函数
        typeSpinner = (Spinner)findViewById(R.id.spin_type);
        typeAdapter = new SpinnerAdapter(this,
                android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
//        typeSpinner.setSelection(LoginActivity.posi,true);
        setSpinner();
    }
    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] items = new String[] {};

        public SpinnerAdapter(final Context context,
                              final int textViewResourceId, final String[] objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.context = context;

        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.rgb(0x46, 0x99, 0xf5));//蓝色
            tv.setTextColor(Color.rgb(0xff,0xff,0xff));//白色
            tv.setTextSize(25);

            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            // android.R.id.text1 is default text view in resource of the android.
            // android.R.layout.simple_spinner_item is default layout in resources of android.

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.rgb(0x46,0x99,0xf5));

            tv.setTextSize(25);
            return convertView;
        }
    }
    //    设置下拉框
    private void setSpinner()
    {
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = getResources().getStringArray(R.array.type_array)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = "未定义";
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_yue_update) {

            String content = "";
            String location = "";
            String time = "";

            EditText contenttmp = (EditText) findViewById(R.id.yue_content);
            content = contenttmp.getText().toString();

            EditText locationtmp = (EditText) findViewById(R.id.yue_location);
            location = locationtmp.getText().toString();

            EditText timetmp = (EditText) findViewById(R.id.yue_time);
            time = timetmp.getText().toString();

            AVUser curUser = AVUser.getCurrentUser();


            Date createTime = new Date();
            createTime.getTime();
            if (content==""||location==""||time=="") {
                Utils.toast(R.string.choose_type);
            }else {

                AVObject Message = new AVObject("Message");
                Message.put("contents", content);
                Message.put("location", location);
                Message.put("time", time);
                Message.put("sendUser", curUser);
                Message.put("createTime", createTime);
                Message.put("username", curUser.getUsername());
                Message.put("avatarUrl", User.getAvatarUrl(curUser));
                Message.put("type", type);


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
}
