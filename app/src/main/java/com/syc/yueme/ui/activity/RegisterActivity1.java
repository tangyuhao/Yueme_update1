package com.syc.yueme.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.syc.yueme.R;
import com.syc.yueme.util.Utils;

public class RegisterActivity1 extends BaseEntryActivity {

    private Spinner schoolSpinner = null;// 学校选择
    ArrayAdapter<String> schoolAdapter = null; //学校选择适配器
    private String[] schools = null;//存放学校内容的数组
    String mail_suffix = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_register_activity1);
        schools = getResources().getStringArray(R.array.school_array);
        //下拉框函数
        schoolSpinner = (Spinner)findViewById(R.id.spin_school);
        schoolAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, schools);
        schoolSpinner.setAdapter(schoolAdapter);
        schoolSpinner.setSelection(LoginActivity.posi,true);
        setSpinner();
    }


    //    设置下拉框
    private void setSpinner()
    {
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mail_suffix = getResources().getStringArray(R.array.school_mail)[position];
                LoginActivity.posi = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mail_suffix = "@fudan.edu.cn";
            }
        });

    }

    //    上一步
    public void onClickone(View view)
    {
        Utils.goActivity(ctx, LoginActivity.class);
    }

    //    下一步
    public void onClicktwo(View view)
    {
        Utils.goActivity(ctx, RegisterActivity2.class);
    }
}
