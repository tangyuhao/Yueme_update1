package com.syc.yueme.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.adapter.CommentAdapter;
import com.syc.yueme.adapter.NearPeopleAdapter;
import com.syc.yueme.service.MessageService;
import com.syc.yueme.ui.view.BaseListView;
import com.syc.yueme.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangweijia on 2015/2/23.
 */
public class CommentUpdateActivity extends BaseActivity implements View.OnClickListener {
    BaseListView<AVObject> listView;

    CommentAdapter adapter;
    List<AVObject> nears = new ArrayList<AVObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);
        initActionBar("评论");
        Button yueUpdate = (Button) findViewById(R.id.commentBtn);
        yueUpdate.setOnClickListener(this);
        initXListView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.commentBtn) {

            String content = "";
            String location = "";
            String time = "";
            AVUser u = AVUser.getCurrentUser();
            String username = u.getUsername();
            String avatarUrl = u.getString("avatarUrl");
            EditText contenttmp = (EditText) findViewById(R.id.comment_text_edit);
            content = contenttmp.getText().toString();
            if (content != null && !content.equals("")) {
                AVObject msg = NearPeopleAdapter.which_msg;
                AVObject comment = new AVObject("Comments");
                comment.put("contents", content);
                comment.put("BelongMsg", msg);
                comment.put("avatarUrl", avatarUrl);
                comment.put("username", username);
                comment.saveInBackground();
                Utils.toast("success!");
            }
            //MainActivity.goMainActivity(CommentUpdateActivity.this);
            contenttmp.setText("");
            listView.onRefresh();

        }
    }

    private void initXListView() {
        adapter = new CommentAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) findViewById(R.id.list_near);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
                List<AVObject> comments = MessageService.findCommentsByMsg2(NearPeopleAdapter.which_msg, skip, limit);
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
