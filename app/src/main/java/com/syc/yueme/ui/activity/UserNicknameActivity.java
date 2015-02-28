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

public class UserNicknameActivity extends BaseActivity {

    TextView nicknameEdit;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_nickname_activity);
        initActionBar(R.string.nickname);

        nicknameEdit = (TextView) findViewById(R.id.userNickname);
        Button b = (Button) findViewById(R.id.save_btn);

        nicknameEdit.setText(UserInfoActivity.nicknameView.getText().toString());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = nicknameEdit.getText().toString();
                AVUser curUser = AVUser.getCurrentUser();
                User.setNickname(curUser, s);
                curUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e == null)
                        {
                            UserInfoActivity.nicknameView.setText(s);
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



