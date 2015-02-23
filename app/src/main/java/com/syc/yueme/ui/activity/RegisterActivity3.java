package com.syc.yueme.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.syc.yueme.R;
import com.syc.yueme.ui.view.HeaderLayout;
import com.syc.yueme.util.Utils;

public class RegisterActivity3 extends BaseEntryActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_register_activity3);

        HeaderLayout headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);

        headerLayout.showTitle(R.string.password);

        TextView v1 = (TextView) findViewById(R.id.editText1);
        TextView v2 = (TextView) findViewById(R.id.editText2);

        v1.setText(LoginActivity.psw);
        v2.setText(LoginActivity.psw_confirm);
    }
    public void onClickone(View view)
    {
        TextView v1 = (TextView) findViewById(R.id.editText1);
        TextView v2 = (TextView) findViewById(R.id.editText2);
        LoginActivity.psw = v1.getText().toString();
        LoginActivity.psw_confirm = v2.getText().toString();
        Utils.goActivity(ctx, RegisterActivity2.class);
    }

    public void onClicktwo(View view)
    {
        TextView v1 = (TextView) findViewById(R.id.editText1);
        TextView v2 = (TextView) findViewById(R.id.editText2);
        LoginActivity.psw = v1.getText().toString();
        LoginActivity.psw_confirm = v2.getText().toString();

        if(LoginActivity.psw.isEmpty())
        {
            Utils.toast(R.string.password_can_not_null);
            return;
        }

        if(!LoginActivity.psw_confirm.equals(LoginActivity.psw))
        {
            Utils.toast(R.string.password_not_consistent);
            return;
        }

        // 即将进入第四个注册界面
        Log.i("Test", "即将进入第四个注册界面");
        Utils.goActivity(ctx, RegisterActivity4.class);
    }
}
