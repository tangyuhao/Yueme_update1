package com.syc.yueme.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.service.UserService;
import com.syc.yueme.util.NetAsyncTask;
import com.syc.yueme.util.Utils;


public class LoginActivity extends BaseEntryActivity implements OnClickListener {
  EditText usernameEdit, passwordEdit;
  Button loginBtn;
  TextView registerBtn,pswChange;


    static int posi; // for the school choice
    static User.Gender gender; // for the gender choice
    static String username;
    static String nickname;
    static String student_id;
    static String psw;
    static String psw_confirm;


    @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.entry_login_activity);
        posi = 0;
        gender = User.Gender.Male;
        username = "";
        nickname = "";
        student_id = "";
        psw = "";
        psw_confirm = "";
    init();
  }

  private void init() {
    usernameEdit = (EditText) findViewById(R.id.et_username);
    passwordEdit = (EditText) findViewById(R.id.et_password);
    loginBtn = (Button) findViewById(R.id.btn_login);
    registerBtn = (TextView) findViewById(R.id.btn_register);
      pswChange = (TextView) findViewById(R.id.psw_change);
      pswChange.setOnClickListener(this);
    loginBtn.setOnClickListener(this);
    registerBtn.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    if (v == registerBtn) {
      Utils.goActivity(ctx, RegisterActivity1.class);
    }else if(v == pswChange){
        Utils.goActivity(ctx,PasswordResetActivity.class);
    }
    else {
      login();
    }
  }

  private void login() {
    final String name = usernameEdit.getText().toString();
    final String password = passwordEdit.getText().toString();

    if (TextUtils.isEmpty(name)) {
      Utils.toast(R.string.username_cannot_null);
      return;
    }

    if (TextUtils.isEmpty(password)) {
      Utils.toast(R.string.password_can_not_null);
      return;
    }

    new NetAsyncTask(ctx) {
      @Override
      protected void doInBack() throws Exception {
        AVUser.logIn(name, password);
      }

      @Override
      protected void onPost(Exception e) {
        if (e != null) {
          Utils.toast(e.getMessage());
        } else {
          UserService.updateUserLocation();
          MainActivity.goMainActivity(LoginActivity.this);
          finish();
        }
      }
    }.execute();

  }
}
