package com.syc.yueme.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.base.App;
import com.syc.yueme.service.UserService;
import com.syc.yueme.util.NetAsyncTask;
import com.syc.yueme.util.Utils;

import java.util.List;

public class RegisterActivity4 extends BaseEntryActivity {

    boolean emailCheck;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Test", "进入第四个界面");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_register_activity4);
        TextView v = (TextView)findViewById(R.id.editText1);
        if(LoginActivity.student_id != null){
            v.setText(LoginActivity.student_id);
        }
        Button b = (Button)findViewById(R.id.button2);
        emailCheck = b.getText().equals("注册") ? true : false;
    }

    public void onClickone(View view)
    {
        TextView v = (TextView)findViewById(R.id.editText1);
        LoginActivity.student_id = v.getText().toString();
        Utils.goActivity(ctx, RegisterActivity3.class);
    }

    public void onClicktwo(View view)
    {
        TextView v = (TextView)findViewById(R.id.editText1);
        LoginActivity.student_id = v.getText().toString();
        if(LoginActivity.student_id.equals(""))
        {
            Utils.toast(R.string.student_id_empty);
            return;
        }
        email = LoginActivity.student_id + getResources().getStringArray(R.array.school_mail)[LoginActivity.posi];
        Button b = (Button)findViewById(R.id.button2);
        emailCheck = b.getText().equals("注册") ? true : false;


        if(emailCheck)
        {
            new NetAsyncTask(ctx) {
                @Override
                protected void doInBack() throws Exception {
                    AVUser user = UserService.signUp(LoginActivity.username, LoginActivity.psw);
                    user.setEmail(email);
                    User.setGender(user, LoginActivity.gender);
                    User.setStuId(user,LoginActivity.student_id);
                    String school = getResources().getStringArray(R.array.school_array)[LoginActivity.posi];
                    User.setSchool(user, school);
                    user.setFetchWhenSave(true);
                    user.save();
                }

                @Override
                protected void onPost(Exception e) {
                    if (e != null) {
                        Utils.toast(App.ctx.getString(R.string.registerFailed) + e.getMessage());
                    } else {
                        Utils.toast(R.string.registerSucceed);
                        UserService.updateUserLocation();
                        Utils.goActivity(ctx, LoginActivity.class);
                    }
                }
            }.execute();
        }
        else {
            //查询该邮箱是否可用使用
            AVQuery<AVUser> query = AVUser.getQuery();
            query.whereEqualTo("email", email);
            query.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> avUsers, AVException e) {
                    if (e == null) {
                        // 查询成功
                        Log.i("Test", "共查询到 " + avUsers.size() + " 个邮箱");
                        Button b = (Button)findViewById(R.id.button2);

                        // 接下来如果邮箱已经被使用，验证其是否被验证

                        if (avUsers.size() == 0) {
                            Log.i("Test", "邮箱可使用");
                            Utils.toast("邮箱可使用");
                            b.setText("注册");
                        } else {
                            AVUser temp = avUsers.get(0);
                            if (temp.getBoolean("emailVerified")) {
                                Log.i("Test", "邮箱已被使用");
                                Utils.toast("邮箱已被使用");
                            } else {
                                temp.deleteInBackground();
                                Log.i("Test", "邮箱已被重置，可使用");
                                Utils.toast("邮箱可使用");
                                b.setText("注册");
                            }
                        }
                    } else {
                        Log.i("Test", "查询失败");
                    }
                }
            });
        }


    }
}
