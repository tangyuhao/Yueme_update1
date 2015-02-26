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

public class PasswordChangeActivity extends BaseEntryActivity implements View.OnClickListener {

    EditText oldPassword, newPassword, newPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change_activity);
        initActionBar(R.string.changepassword);
        findView();
    }

    private void findView() {
        oldPassword = (EditText) findViewById(R.id.editText1);
        newPassword = (EditText) findViewById(R.id.editText2);
        newPasswordConfirm = (EditText) findViewById(R.id.editText3);
    }
    @Override
    public void onClick(View v) {
        change();
    }

    private void change(){
        final String old_password = oldPassword.getText().toString();
        final String new_password = newPassword.getText().toString();
        String again_new_password = newPasswordConfirm.getText().toString();

        if (TextUtils.isEmpty(old_password)) {
            Utils.toast(R.string.password_can_not_null);
            return;
        }
        if (TextUtils.isEmpty(new_password)) {
            Utils.toast(R.string.password_can_not_null);
            return;
        }

        if (!again_new_password.equals(new_password)) {
            Utils.toast(R.string.password_not_consistent);
            return;
        }

        new NetAsyncTask(ctx) {
            @Override
            protected void doInBack() throws Exception {
                AVUser user =AVUser.getCurrentUser();
                user.updatePassword(old_password,new_password);
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

