package com.syc.yueme.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.avobject.ChatGroup;
import com.syc.yueme.avobject.Message;
import com.syc.yueme.base.App;
import com.syc.yueme.service.ChatService;
import com.syc.yueme.service.GroupService;
import com.syc.yueme.service.MessageService;
import com.syc.yueme.service.UserService;

import butterknife.InjectView;

import com.syc.yueme.adapter.NearPeopleAdapter;
import com.syc.yueme.service.PreferenceMap;
import com.syc.yueme.ui.activity.AddFriendActivity;
import com.syc.yueme.ui.activity.PersonInfoActivity;
import com.syc.yueme.ui.activity.YueUpdateActivity;
import com.syc.yueme.ui.view.BaseListView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.syc.yueme.ui.view.HeaderLayout;
import com.syc.yueme.util.Utils;
import com.syc.yueme.avobject.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 14-9-17.
 */
public class DiscoverFragment extends BaseFragment {
//    implements View.OnClickListener
    Button yueButton;
    @InjectView(R.id.list_near)
    BaseListView<AVObject> listView;
    private String[] type = new String[] { "全部显示","美食", "学习", "运动", "娱乐", "其他" };
    private ListView lv;

    NearPeopleAdapter adapter;
    List<AVObject> nears = new ArrayList<AVObject>();

    private final SortDialogListener distanceListener = new SortDialogListener(UserService.ORDER_DISTANCE);
    private final SortDialogListener updatedAtListener = new SortDialogListener(UserService.ORDER_UPDATED_AT);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discover_fragment, container, false);
    }
    private void intHeader() {
        headerLayout = (HeaderLayout) getView().findViewById(R.id.headerLayout);
        headerLayout.showTitle(App.ctx.getString(R.string.discover));

        headerLayout.showRightImageButton(R.drawable.base_action_bar_filter, new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.goActivity(ctx, YueUpdateActivity.class);
                showSingleChoiceItems();
            }

        });
        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goActivity(ctx, YueUpdateActivity.class);
            }

        });

    }
    private void intallHeader() {
        headerLayout = (HeaderLayout) getView().findViewById(R.id.headerLayout);
        headerLayout.showTitle(App.ctx.getString(R.string.discover));

//        headerLayout.showRightImageButton(R.drawable.base_action_bar_filter, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Utils.goActivity(ctx, YueUpdateActivity.class);
//                showSingleChoiceItems();
//            }
//
//        });
//        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.goActivity(ctx, YueUpdateActivity.class);
//            }
//
//        });

    }
    private void intplayHeader() {
        headerLayout = (HeaderLayout) getView().findViewById(R.id.headerLayout);
        headerLayout.showTitle(App.ctx.getString(R.string.play));

//        headerLayout.showRightImageButton(R.drawable.base_action_bar_filter, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Utils.goActivity(ctx, YueUpdateActivity.class);
//                showMultiChoiceItems();
//            }
//
//        });
//        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.goActivity(ctx, YueUpdateActivity.class);
//            }
//
//        });

    }
    private void intstudyHeader() {
        headerLayout = (HeaderLayout) getView().findViewById(R.id.headerLayout);
        headerLayout.showTitle(App.ctx.getString(R.string.study));

//        headerLayout.showRightImageButton(R.drawable.base_action_bar_filter, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Utils.goActivity(ctx, YueUpdateActivity.class);
//                showMultiChoiceItems();
//            }
//
//        });
//        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.goActivity(ctx, YueUpdateActivity.class);
//            }
//
//        });

    }
    private void inteatHeader() {
        headerLayout = (HeaderLayout) getView().findViewById(R.id.headerLayout);
        headerLayout.showTitle(App.ctx.getString(R.string.eat));

//        headerLayout.showRightImageButton(R.drawable.base_action_bar_filter, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Utils.goActivity(ctx, YueUpdateActivity.class);
//                showMultiChoiceItems();
//            }
//
//        });
//        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.goActivity(ctx, YueUpdateActivity.class);
//            }
//
//        });

    }
    private void intsportHeader() {
        headerLayout = (HeaderLayout) getView().findViewById(R.id.headerLayout);
        headerLayout.showTitle(App.ctx.getString(R.string.sport));

//        headerLayout.showRightImageButton(R.drawable.base_action_bar_filter, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Utils.goActivity(ctx, YueUpdateActivity.class);
//                showMultiChoiceItems();
//            }
//
//        });
//        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.goActivity(ctx, YueUpdateActivity.class);
//            }
//
//        });

    }
    private void intothersHeader() {
        headerLayout = (HeaderLayout) getView().findViewById(R.id.headerLayout);
        headerLayout.showTitle(App.ctx.getString(R.string.others));

//        headerLayout.showRightImageButton(R.drawable.base_action_bar_filter, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Utils.goActivity(ctx, YueUpdateActivity.class);
//                showMultiChoiceItems();
//            }
//
//        });
//        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.goActivity(ctx, YueUpdateActivity.class);
//            }
//
//        });

    }
    private void showSingleChoiceItems()
    {
        new AlertDialog.Builder(ctx)
                .setTitle("请选择约的类型")
                .setSingleChoiceItems(type, 0,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                String types=type[which];
                                if (types.equals("美食")){
                                    inteatHeader();
                                    initeatXListView();
                                    listView.onRefresh();
                                }else if (types.equals("学习")){
                                    intstudyHeader();
                                    initstudyXListView();
                                    listView.onRefresh();
                                }else if (types.equals("运动")){
                                    intsportHeader();
                                    initsportXListView();
                                    listView.onRefresh();
                                }else if (types.equals("娱乐")){
                                    intplayHeader();
                                    initplayXListView();
                                    listView.onRefresh();
                                }else if (types.equals("其他")){
                                    intothersHeader();
                                    initotherXListView();
                                    listView.onRefresh();
                                }else{
                                    intallHeader();
                                    initXListView();
                                    listView.onRefresh();
                                }
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("取消", null)
                .show();

    }
//    private void findView() {
//        View fragmentView = getView();
//        yueButton = (Button) fragmentView.findViewById(R.id.btn_yue);
//        yueButton.setOnClickListener(this);
//    }


//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.btn_yue) {
//            Utils.goActivity(ctx, YueUpdateActivity.class);
//
//        }
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        headerLayout.showTitle(R.string.discover);
//        headerLayout.showRightImageButton(R.drawable.nearby_order, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle(R.string.sort).setPositiveButton(R.string.loginTime,
//                        updatedAtListener).setNegativeButton(R.string.distance, distanceListener).show();
//            }
//        });
        intHeader();
        initXListView();
        listView.onRefresh();
//        findView();
    }


    public class SortDialogListener implements DialogInterface.OnClickListener {
        int orderType;

        public SortDialogListener(int orderType) {
            this.orderType = orderType;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            listView.onRefresh();
        }
    }


    private void initXListView() {
        adapter = new NearPeopleAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) getView().findViewById(R.id.list_near);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
//                List<AVObject> msgs = MessageService.findMsg(skip, limit);
                List<AVObject> msgs = MessageService.findMsg(skip, limit);
                return msgs;
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
    private void initeatXListView() {
        adapter = new NearPeopleAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) getView().findViewById(R.id.list_near);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
//                List<AVObject> msgs = MessageService.findMsg(skip, limit);
                List<AVObject> msgs = MessageService.findeattypeByMsg(skip, limit);
                return msgs;
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
    private void initsportXListView() {
        adapter = new NearPeopleAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) getView().findViewById(R.id.list_near);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
//                List<AVObject> msgs = MessageService.findMsg(skip, limit);
                List<AVObject> msgs = MessageService.findsporttypeByMsg(skip, limit);
                return msgs;
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
    private void initstudyXListView() {
        adapter = new NearPeopleAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) getView().findViewById(R.id.list_near);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
//                List<AVObject> msgs = MessageService.findMsg(skip, limit);
                List<AVObject> msgs = MessageService.findstudytypeByMsg(skip, limit);
                return msgs;
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
    private void initplayXListView() {
        adapter = new NearPeopleAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) getView().findViewById(R.id.list_near);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
//                List<AVObject> msgs = MessageService.findMsg(skip, limit);
                List<AVObject> msgs = MessageService.findplaytypeByMsg(skip, limit);
                return msgs;
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
    private void initotherXListView() {
        adapter = new NearPeopleAdapter(ctx, nears);
        listView = (BaseListView<AVObject>) getView().findViewById(R.id.list_near);
        listView.init(new BaseListView.DataInterface<AVObject>() {
            @Override
            public List<AVObject> getDatas(int skip, int limit, List<AVObject> currentDatas) throws Exception {
//                List<AVObject> msgs = MessageService.findMsg(skip, limit);
                List<AVObject> msgs = MessageService.findeattypeByMsg(skip, limit);
                return msgs;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
