package com.syc.yueme.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.util.Utils;

public class UserHometownActivity extends BaseActivity {

    TextView homeEdit;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_hometown_activity);
        initActionBar(R.string.hometown);
        // TODO Auto-generated method stub

        homeEdit = (TextView) findViewById(R.id.userHome);
        Button b = (Button) findViewById(R.id.save_btn);

        homeEdit.setText(UserInfoActivity.hometownView.getText().toString());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = homeEdit.getText().toString();
                AVUser curUser = AVUser.getCurrentUser();
                User.setHometown(curUser, s);
                curUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e == null)
                        {
                            UserInfoActivity.hometownView.setText(s);
                            Utils.toast(R.string.saveSuccess);
                            finish();
                        }
                        else
                        {
                            Utils.toast(e.getMessage());
                            return;
                        }
                    }
                });
            }
        });

    }
}
