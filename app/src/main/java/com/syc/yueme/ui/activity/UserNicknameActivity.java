package com.syc.yueme.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
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
    int maxLen = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_nickname_activity);
        initActionBar(R.string.nickname);
        nicknameEdit = (TextView) findViewById(R.id.userNickname);
        Button b = (Button) findViewById(R.id.save_btn);

        nicknameEdit.setText(UserInfoActivity.nickname);

        nicknameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = (Editable) nicknameEdit.getText();
                int len = editable.length();

                if (len > maxLen) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, maxLen);
                    nicknameEdit.setText(newStr);
                    editable = (Editable) nicknameEdit.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
                            UserInfoActivity.nickname = s;
                            UserInfoActivity.nicknameView.setText(s.length() > 12 ? s.substring(0,12)+"..." : s);
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



