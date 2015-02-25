package com.syc.yueme.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.syc.yueme.R;
import com.syc.yueme.ui.view.HeaderLayout;
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

        HeaderLayout headerLayout = (HeaderLayout)findViewById(R.id.headerLayout);
        headerLayout.showTitle(R.string.school);

        schools = getResources().getStringArray(R.array.school_array);
        //下拉框函数
        schoolSpinner = (Spinner)findViewById(R.id.spin_school);
        schoolAdapter = new SpinnerAdapter(this,
                android.R.layout.simple_spinner_item, schools);
        schoolSpinner.setAdapter(schoolAdapter);
        schoolSpinner.setSelection(LoginActivity.posi,true);
        setSpinner();
    }

    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] items = new String[] {};

        public SpinnerAdapter(final Context context,
                              final int textViewResourceId, final String[] objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.rgb(0x46,0x99,0xf5));//蓝色
            tv.setTextColor(Color.rgb(0xff,0xff,0xff));//白色
            tv.setTextSize(20);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            // android.R.id.text1 is default text view in resource of the android.
            // android.R.layout.simple_spinner_item is default layout in resources of android.

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.rgb(0x46,0x99,0xf5));
            tv.setTextSize(20);
            return convertView;
        }
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
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    }

    //    下一步
    public void onClicktwo(View view)
    {
        Utils.goActivity(ctx, RegisterActivity2.class);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    }
}
