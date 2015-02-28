package com.syc.yueme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
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
    int maxLen = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_hobbies_activity);
        initActionBar(R.string.hobbies);
        // TODO Auto-generated method stub



        hobbyEdit = (TextView) findViewById(R.id.userHobby);
        Button b = (Button) findViewById(R.id.save_btn);

        hobbyEdit.setText(UserInfoActivity.hobby);

        hobbyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = (Editable) hobbyEdit.getText();
                int len = editable.length();

                if(len > maxLen)
                {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0,maxLen);
                    hobbyEdit.setText(newStr);
                    editable = (Editable) hobbyEdit.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if(selEndIndex > newLen)
                    {
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
                s = hobbyEdit.getText().toString();
                AVUser curUser = AVUser.getCurrentUser();
                User.setHobbies(curUser, s);
                curUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e == null)
                        {
                            UserInfoActivity.hobby = s;
                            UserInfoActivity.hobbyView.setText(s.length() > 12 ? s.substring(0, 12) + "..." : s);
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