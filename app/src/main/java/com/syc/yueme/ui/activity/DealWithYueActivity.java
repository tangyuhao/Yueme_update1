package com.syc.yueme.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.syc.yueme.R;
import com.syc.yueme.adapter.NewFriendAdapter;
import com.syc.yueme.avobject.AddRequest;
import com.syc.yueme.avobject.User;
import com.syc.yueme.service.AddRequestService;
import com.syc.yueme.service.PreferenceMap;
import com.syc.yueme.util.NetAsyncTask;
import com.syc.yueme.util.SimpleNetTask;
import com.syc.yueme.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class DealWithYueActivity extends BaseActivity {

    ListView listview;
    //YueApplyAdapter adapter;
    List<AddRequest> addRequests = new ArrayList<AddRequest>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_with_yue_activity);
        initActionBar(R.string.deal_with_apply);


    }


}
