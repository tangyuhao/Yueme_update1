package com.syc.yueme.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.util.Utils;

import java.util.List;

public class RegisterActivity2 extends BaseEntryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_register_activity2);
        if(LoginActivity.gender == User.Gender.Male) {
            RadioButton radio = (RadioButton) findViewById(R.id.btnMan);
            radio.setChecked(true);
        }
        else {
            RadioButton radio1 = (RadioButton) findViewById(R.id.btnWoman);
            radio1.setChecked(true);
        }

        TextView v = (TextView) findViewById(R.id.editText);
        v.setText(LoginActivity.username);
    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked =((RadioButton) view).isChecked();
        switch (view.getId())
        {
            case R.id.btnMan:
                if(checked)
                {
                    LoginActivity.gender = User.Gender.Male;
                }
                break;
            case R.id.btnWoman:
                if(checked)
                {
                    LoginActivity.gender = User.Gender.Female;
                }
                break;
        }
    }

    public void onClickone(View view)
    {
        TextView v = (TextView) findViewById(R.id.editText);
        LoginActivity.username = v.getText().toString();
        Utils.goActivity(ctx, RegisterActivity1.class);
    }

    public void onClicktwo(View view)
    {
        TextView v = (TextView) findViewById(R.id.editText);
        LoginActivity.username = v.getText().toString();

        if(LoginActivity.username.equals(""))
        {
            Utils.toast(R.string.username_cannot_null);
            return;
        }
        else
        {
            AVQuery<AVUser> query = AVUser.getQuery();

            query.whereEqualTo("username",LoginActivity.username);
            query.findInBackground(new FindCallback<AVUser>() {
                public void done(List<AVUser> objects, AVException e) {
                    if (e == null) {
                        Log.i("Test", "共查询到用户  " + objects.size() + "  个");
                        // 查询成功

                        if(objects.size() == 0)
                        {
                            Utils.goActivity(ctx, RegisterActivity3.class);
                        }
                        else
                        {
                            Utils.toast(R.string.username_used);
                            TextView v1 = (TextView) findViewById(R.id.editText);
                            v1.setText("");
                        }
                    } else {
                        Log.i("Test", "查询出错");
                        // 查询出错
                    }
                }
            });

        }
    }
}
