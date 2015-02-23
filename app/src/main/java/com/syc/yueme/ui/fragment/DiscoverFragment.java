package com.syc.yueme.ui.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.service.UserService;
import butterknife.InjectView;
import com.syc.yueme.adapter.NearPeopleAdapter;
import com.syc.yueme.service.PreferenceMap;
import com.syc.yueme.ui.activity.PersonInfoActivity;
import com.syc.yueme.ui.activity.YueUpdateActivity;
import com.syc.yueme.ui.view.BaseListView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.syc.yueme.util.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 14-9-17.
 */
public class DiscoverFragment extends BaseFragment implements View.OnClickListener {

    Button yueButton;
  @InjectView(R.id.list_near)
  BaseListView<AVUser> listView;

  NearPeopleAdapter adapter;
  List<AVUser> nears = new ArrayList<AVUser>();
  int orderType;
  PreferenceMap preferenceMap;

 private final SortDialogListener distanceListener = new SortDialogListener(UserService.ORDER_DISTANCE);
  private final SortDialogListener updatedAtListener = new SortDialogListener(UserService.ORDER_UPDATED_AT);

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.discover_fragment, container, false);
  }

    private void findView() {
        View fragmentView = getView();
        yueButton = (Button) fragmentView.findViewById(R.id.btn_yue);


       yueButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_yue) {
            Utils.goActivity(ctx, YueUpdateActivity.class);
        }
    }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    preferenceMap = PreferenceMap.getCurUserPrefDao(getActivity());
    orderType = preferenceMap.getNearbyOrder();
    headerLayout.showTitle(R.string.discover);
    headerLayout.showRightImageButton(R.drawable.nearby_order, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sort).setPositiveButton(R.string.loginTime,
            updatedAtListener).setNegativeButton(R.string.distance, distanceListener).show();
      }
    });
    initXListView();
    listView.onRefresh();
      findView();
  }



  public class SortDialogListener implements DialogInterface.OnClickListener {
    int orderType;

    public SortDialogListener(int orderType) {
      this.orderType = orderType;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
      DiscoverFragment.this.orderType = orderType;
      listView.onRefresh();
    }
  }



  private void initXListView() {
    adapter = new NearPeopleAdapter(ctx, nears);
    listView = (BaseListView<AVUser>) getView().findViewById(R.id.list_near);
    listView.init(new BaseListView.DataInterface<AVUser>() {
      @Override
      public List<AVUser> getDatas(int skip, int limit, List<AVUser> currentDatas) throws Exception {
        return UserService.findNearbyPeople(orderType, skip, limit);
      }

      @Override
      public void onItemSelected(AVUser item) {
        PersonInfoActivity.goPersonInfo(ctx, item.getObjectId());
      }
    }, adapter);

    PauseOnScrollListener listener = new PauseOnScrollListener(UserService.imageLoader,
        true, true);
    listView.setOnScrollListener(listener);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    preferenceMap.setNearbyOrder(orderType);
  }
}
