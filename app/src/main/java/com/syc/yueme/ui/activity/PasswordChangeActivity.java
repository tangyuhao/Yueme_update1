package com.syc.yueme.ui.activity;

/**
 * Created by lx on 2015/2/17.
 */
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.base.App;
import com.syc.yueme.service.UserService;
import com.syc.yueme.util.NetAsyncTask;
import com.syc.yueme.util.Utils;

public class PasswordChangeActivity extends BaseEntryActivity implements OnClickListener {
    View changeButton;
    EditText passwordEdit, emailEdit,oldpasswordEdit;
    TextView usernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change_activity);
        findView();
        AVUser curUser = AVUser.getCurrentUser();
        usernameView.setText(curUser.getUsername());
    }

    private void findView() {
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        oldpasswordEdit=(EditText) findViewById(R.id.oldpasswordEdit);
        emailEdit = (EditText) findViewById(R.id.ensurePasswordEdit);
        changeButton = findViewById(R.id.btn_change);
        usernameView = (TextView)findViewById(R.id.username);
        changeButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        changeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                change();
            }
        });
    }

    private void change(){
        final String oldpassword = oldpasswordEdit.getText().toString();
        final String password = passwordEdit.getText().toString();
        String againPassword = emailEdit.getText().toString();

        if (TextUtils.isEmpty(oldpassword)) {
            Utils.toast(R.string.password_can_not_null);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Utils.toast(R.string.password_can_not_null);
            return;
        }

        if (!againPassword.equals(password)) {
            Utils.toast(R.string.password_not_consistent);
            return;
        }

        new NetAsyncTask(ctx) {
            @Override
            protected void doInBack() throws Exception {
                AVUser user =AVUser.getCurrentUser();
                user.updatePassword(oldpassword,password);
            }

            @Override
            protected void onPost(Exception e) {
                if (e != null) {
                    Utils.toast(App.ctx.getString(R.string.changeFailed) + e.getMessage());
                } else {
                    Utils.toast(R.string.changeSucceed);
                    UserService.updateUserLocation();
                    MainActivity.goMainActivity(PasswordChangeActivity.this);
                }
            }
        }.execute();
    }
}

