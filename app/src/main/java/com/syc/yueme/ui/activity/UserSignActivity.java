package com.syc.yueme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.ui.view.HeaderLayout;

public class UserSignActivity extends BaseEntryActivity implements View.OnClickListener {

    TextView Characristics;
    EditText CharacristicsEdit;
    View DeletBtn;
    HeaderLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sign_activity);
        // TODO Auto-generated method stub
        findView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttoncharacristic) {
            change();
            Toast.makeText(getBaseContext(), "设置成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void findView(){
        Characristics=(TextView)findViewById(R.id.UserNicknameTitle);
        CharacristicsEdit=(EditText)findViewById(R.id.UserNickname);
        DeletBtn=findViewById(R.id.buttoncharacristic);
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(R.string.userInfo);
        DeletBtn.setOnClickListener(this);
    }
    private void change() {
        AVUser curUser = AVUser.getCurrentUser();
        String characristics = CharacristicsEdit.getText().toString();
        User.setSign(curUser,characristics);
        curUser.saveInBackground();
    }
}