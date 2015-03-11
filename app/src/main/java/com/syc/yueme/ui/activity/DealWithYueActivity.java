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

import com.avos.avoscloud.AVObject;
import com.syc.yueme.R;
import com.syc.yueme.adapter.CommentAdapter;
import com.syc.yueme.adapter.DealYueAdapter;
import com.syc.yueme.adapter.NearPeopleAdapter;
import com.syc.yueme.adapter.NewFriendAdapter;
import com.syc.yueme.avobject.AddRequest;
import com.syc.yueme.avobject.User;
import com.syc.yueme.service.AddRequestService;
import com.syc.yueme.service.MessageService;
import com.syc.yueme.service.PreferenceMap;
import com.syc.yueme.ui.view.BaseListView;
import com.syc.yueme.util.NetAsyncTask;
import com.syc.yueme.util.SimpleNetTask;
import com.syc.yueme.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class DealWithYueActivity extends BaseActivity {

    //YueApplyAdapter adapter;
    List<AddRequest> addRequests = new ArrayList<AddRequest>();
    BaseListView<AVObject> listView;
    DealYueAdapter adapter;
    List<AVObject> nears = new ArrayList<AVObject>();
    public static AVObject msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_with_yue_activity);
        initActionBar(R.string.deal_with_apply);
        initXListView();


    }
    private void initXListView() {
        adapter = new DealYueAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) findViewById(R.id.dealYueList);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
                List<AVObject> comments = MessageService.findYueUser(msg, skip, limit);
                return comments;
            }

            @Override
            public void onItemSelected(AVObject item) {
            }
        }, adapter);
        listView.onRefresh();
//        PauseOnScrollListener listener = new PauseOnScrollListener(MessageService.imageLoader,
//                true, true);
//        listView.setOnScrollListener(listener);
    }

}
