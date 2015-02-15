package com.avoscloud.Yueme.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.avos.avoscloud.AVUser;
import com.avoscloud.Yueme.R;
import com.avoscloud.Yueme.avobject.User;
import com.avoscloud.Yueme.base.App;
import com.avoscloud.Yueme.service.UserService;
import com.avoscloud.Yueme.util.NetAsyncTask;
import com.avoscloud.Yueme.util.Utils;

public class RegisterActivity extends BaseEntryActivity {
  View registerButton;
  EditText usernameEdit, passwordEdit, emailEdit;
  RadioGroup sexRadio;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.entry_register_activity);
    findView();
    initActionBar(App.ctx.getString(R.string.register));
    registerButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        // TODO Auto-generated method stub
        register();
      }
    });
  }

  private void findView() {
    usernameEdit = (EditText) findViewById(R.id.usernameEdit);
    passwordEdit = (EditText) findViewById(R.id.passwordEdit);
    emailEdit = (EditText) findViewById(R.id.ensurePasswordEdit);
    registerButton = findViewById(R.id.btn_register);
    sexRadio = (RadioGroup) findViewById(R.id.sexRadio);
  }

  private void register() {
    final String name = usernameEdit.getText().toString();
    final String password = passwordEdit.getText().toString();
    String againPassword = emailEdit.getText().toString();
    if (TextUtils.isEmpty(name)) {
      Utils.toast(R.string.username_cannot_null);
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

    int checkedId = sexRadio.getCheckedRadioButtonId();
    final User.Gender gender;
    gender = checkedId == R.id.male ? User.Gender.Male : User.Gender.Female;

    new NetAsyncTask(ctx) {
      @Override
      protected void doInBack() throws Exception {
        AVUser user = UserService.signUp(name, password);
        User.setGender(user, gender);
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
          MainActivity.goMainActivity(RegisterActivity.this);
        }
      }
    }.execute();
  }
}
