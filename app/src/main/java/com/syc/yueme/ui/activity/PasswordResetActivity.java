package com.syc.yueme.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.nostra13.universalimageloader.utils.L;
import com.syc.yueme.R;
import com.syc.yueme.ui.view.HeaderLayout;
import com.syc.yueme.util.Utils;

import java.util.List;

public class PasswordResetActivity extends BaseActivity {


    EditText ed_username,ed_email;
    String username,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_password_reset);

        HeaderLayout hl = (HeaderLayout)findViewById(R.id.headerLayout);
        hl.showTitle(R.string.forget_password);

        findView();
    }

    private void findView() {
        ed_username = (EditText) findViewById(R.id.et_username);
        ed_email = (EditText) findViewById(R.id.et_email);
    }

    public void onClickone(View view)
    {
        Utils.goActivity(ctx, LoginActivity.class);
    }

    public void onClicktwo(View view)
    {
        username = ed_username.getText().toString();
        email = ed_email.getText().toString();

        AVQuery<AVUser> query = AVUser.getQuery();
        query.whereEqualTo("username",username);
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> avUsers, AVException e) {
                if(e == null)
                {
                    AVUser temp = avUsers.get(0);
                    String em = temp.getEmail().toString();
                    if(em.equals(email)){
                        AVUser.requestPasswordResetInBackground(em, new RequestPasswordResetCallback() {
                            public void done(AVException e) {
                                if (e == null) {
                                    // 已发送一份重置密码的指令到用户的邮箱
                                    Utils.toast("已发送一份重置密码的指令到您的邮箱，请在24小时内重置");
                                    Utils.goActivity(ctx, LoginActivity.class);
                                } else {
                                    // 重置密码出错。
                                    Utils.toast(e.getMessage());
                                }
                            }
                        });
                    }
                    else{
                        Utils.toast("账号与学号邮箱不匹配。");
                        return;
                    }
                }
                else{

                    Utils.toast("没有这个账号。");
                    return;
                }
            }
        });
    }

}
