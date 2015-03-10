package com.syc.yueme.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.adapter.MyApplyMessageAdapter;
import com.syc.yueme.adapter.MyMessageAdapter;
import com.syc.yueme.adapter.NearPeopleAdapter;
import com.syc.yueme.service.MessageService;
import com.syc.yueme.ui.view.BaseListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangweijia on 2015/2/23.
 */
public class YingYueActivity extends BaseActivity implements View.OnClickListener {
    BaseListView<AVObject> listView;

    MyApplyMessageAdapter adapter;
    List<AVObject> nears = new ArrayList<AVObject>();
    public static List<AVUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message);
        initActionBar("应约状态");
        initXListView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

    }

    private void initXListView() {
        adapter = new MyApplyMessageAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) findViewById(R.id.list_near);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
                return MessageService.findYingMessage(AVUser.getCurrentUser(), skip, limit);
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
