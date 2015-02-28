package com.syc.yueme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.ui.view.HeaderLayout;
import com.syc.yueme.util.Utils;

public class UserHobbiesActivity extends BaseEntryActivity {

    TextView hobbyEdit;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_hobbies_activity);
        initActionBar(R.string.hobbies);
        // TODO Auto-generated method stub



        hobbyEdit = (TextView) findViewById(R.id.userHobby);
        Button b = (Button) findViewById(R.id.save_btn);

        hobbyEdit.setText(UserInfoActivity.hobbyView.getText().toString());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = hobbyEdit.getText().toString();
                AVUser curUser = AVUser.getCurrentUser();
                User.setHobbies(curUser,s);
                curUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e == null)
                        {
                            Utils.toast(R.string.saveSuccess);
                            UserInfoActivity.hobbyView.setText(s);
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