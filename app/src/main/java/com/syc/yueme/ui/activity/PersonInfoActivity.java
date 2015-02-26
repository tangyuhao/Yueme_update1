package com.syc.yueme.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.avobject.User;
import com.syc.yueme.service.CacheService;
import com.syc.yueme.service.CloudService;
import com.syc.yueme.service.UserService;
import com.syc.yueme.ui.view.HeaderLayout;
import com.syc.yueme.util.NetAsyncTask;
import com.syc.yueme.util.Utils;

import java.util.List;

public class PersonInfoActivity extends BaseActivity implements OnClickListener {
    public static final String USER_ID = "userId";
    TextView usernameView, genderView, stuidView, birthdayView, headerLayoutView,
            peoplesView, hometownView, YPAView, schoolView, nicknameView,
            characristicsView, hobbyView, signView, specialityView;
    ImageView avatarView, avatarArrowView;
    LinearLayout allLayout;
    //  HeaderLayout headerLayout;
    Button chatBtn, addFriendBtn;
    RelativeLayout avatarLayout, genderLayout,usernameLayout,stuidLayout, birthdayLayout,
            specialityLayout,peoplesLayout, YPALayout, hometownLayout,
            characristicsLayout, hobbyLayout, signLayout, nicknameLayout;

    String userId = "";
    AVUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //meizu?
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= 14) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        setContentView(R.layout.contact_person_info_activity);
        initData();

        findView();
        initView();
    }

    private void initData() {
        userId = getIntent().getStringExtra(USER_ID);
        user = CacheService.lookupUser(userId);
    }

    private void findView() {
        allLayout = (LinearLayout) findViewById(R.id.all_layout);
        avatarView = (ImageView) findViewById(R.id.avatar_view);
        avatarArrowView = (ImageView) findViewById(R.id.avatar_arrow);
        usernameView = (TextView) findViewById(R.id.username_view);
        avatarLayout = (RelativeLayout) findViewById(R.id.head_layout);
        genderLayout = (RelativeLayout) findViewById(R.id.sex_layout);

        genderView = (TextView) findViewById(R.id.sexView);
        chatBtn = (Button) findViewById(R.id.chatBtn);
        addFriendBtn = (Button) findViewById(R.id.addFriendBtn);

//    usernameView = (TextView) findViewById(R.id.myusername);
//    avatarView = (ImageView) findViewById(R.id.myavatar);
//    genderView = (TextView) findViewById(R.id.sex);
        stuidView = (TextView) findViewById(R.id.stuId);
        nicknameView = (TextView) findViewById(R.id.nickname);
        birthdayView = (TextView) findViewById(R.id.birthday);
        peoplesView = (TextView) findViewById(R.id.peoples);
        hometownView = (TextView) findViewById(R.id.hometown);
        YPAView = (TextView) findViewById(R.id.YPA);
        signView = (TextView) findViewById(R.id.sign);
        schoolView = (TextView) findViewById(R.id.school);
        characristicsView = (TextView) findViewById(R.id.characristics);
        hobbyView = (TextView) findViewById(R.id.hobbies);
        specialityView = (TextView) findViewById(R.id.speciality);

        birthdayLayout = (RelativeLayout)findViewById(R.id.birthdayLayout);
        nicknameLayout = (RelativeLayout)findViewById(R.id.nicknameLayout);
        peoplesLayout = (RelativeLayout)findViewById(R.id.peoplesLayout);
        hometownLayout =(RelativeLayout) findViewById(R.id.hometownLayout);
        characristicsLayout=(RelativeLayout)findViewById(R.id.characristicsLayout);
        hobbyLayout=(RelativeLayout)findViewById(R.id.hobbiesLayout);
        signLayout=(RelativeLayout)findViewById(R.id.signLayout);
        specialityLayout=(RelativeLayout)findViewById(R.id.specialityLayout);
//    headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);

    }

    private void initView() {
        AVUser curUser = AVUser.getCurrentUser();
        if (curUser.equals(user)) {
            initActionBar(R.string.personalInfo);
            avatarLayout.setOnClickListener(this);
            genderLayout.setOnClickListener(this);
            avatarArrowView.setVisibility(View.VISIBLE);
            chatBtn.setVisibility(View.GONE);
            addFriendBtn.setVisibility(View.GONE);
        } else {
            initActionBar(R.string.detailInfo);
            avatarArrowView.setVisibility(View.INVISIBLE);
            List<String> cacheFriends = CacheService.getFriendIds();
            boolean isFriend = cacheFriends.contains(user.getObjectId());
            if (isFriend) {
                chatBtn.setVisibility(View.VISIBLE);
                chatBtn.setOnClickListener(this);
            } else {
                chatBtn.setVisibility(View.GONE);
                addFriendBtn.setVisibility(View.VISIBLE);
                addFriendBtn.setOnClickListener(this);
            }
        }
        updateView(user);
    }

    public static void goPersonInfo(Context ctx, String userId) {
        Intent intent = new Intent(ctx, PersonInfoActivity.class);
        intent.putExtra(USER_ID, userId);
        ctx.startActivity(intent);
    }

    private void updateView(AVUser user) {
        String avatar = User.getAvatarUrl(user);
        UserService.displayAvatar(avatar, avatarView);
        usernameView.setText(user.getUsername());
        genderView.setText(User.getGenderDesc(user));

        nicknameView.setText(User.getNickname(user));
        birthdayView.setText(User.getBirthday(user));
        peoplesView.setText(User.getPeoples(user));
        hometownView.setText(User.getHometown(user));
        YPAView.setText(User.getYPA(user));
        signView.setText(User.getSign(user));
        characristicsView.setText(User.getCharacristics(user));
        stuidView.setText(User.getStuId(user));
        specialityView.setText(User.getSpeciality(user));
        hobbyView.setText(User.getHobbies(user));
        schoolView.setText(User.getSchool(user));
//    headerLayout.showTitle(R.string.userInfo);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.chatBtn:// 发起聊天
                ChatActivity.goUserChat(this, user.getObjectId());
                finish();
                break;
            case R.id.addFriendBtn:// 添加好友
                new NetAsyncTask(ctx) {
                    @Override
                    protected void doInBack() throws Exception {
                        CloudService.tryCreateAddRequest(user);
                    }

                    @Override
                    protected void onPost(Exception e) {
                        if (e != null) {
                            Utils.toast(e.getMessage());
                        } else {
                            Utils.toast(R.string.sendRequestSucceed);
                        }
                    }
                }.execute();
                break;
        }
    }
}
