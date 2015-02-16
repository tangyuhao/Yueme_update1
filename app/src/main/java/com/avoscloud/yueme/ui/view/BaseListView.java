package com.avoscloud.yueme.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import com.avoscloud.yueme.R;
import com.avoscloud.yueme.adapter.BaseListAdapter;
import com.avoscloud.yueme.ui.view.xlist.XListView;
import com.avoscloud.yueme.util.SimpleNetTask;
import com.avoscloud.yueme.util.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lzw on 15/1/2.
 */
public class BaseListView<T> extends XListView implements XListView.IXListViewListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
  private static final int ONE_PAGE_SIZE = 15;
  private BaseListAdapter<T> adapter;
  private DataInterface<T> dataInterface = new DataInterface<T>();
  private boolean toastIfEmpty = true;

  public BaseListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void init(DataInterface<T> dataInterface, BaseListAdapter<T> adapter) {
    this.dataInterface = dataInterface;
    this.adapter = adapter;
    setAdapter(adapter);
    setXListViewListener(this);
    setOnItemClickListener(this);
    setOnItemLongClickListener(this);
    setPullLoadEnable(true);
    setPullRefreshEnable(true);
  }

  public static class DataInterface<T> {
    public List<T> getDatas(int skip, int limit, List<T> currentDatas) throws Exception {
      return new ArrayList<T>();
    }

    public void onItemSelected(T item) {
    }

    public void onItemLongPressed(T item) {
    }
  }

  @Override
  public void onRefresh() {
    loadDatas(false);
  }

  public void loadDatas(final boolean loadMore) {
    final int skip;
    if (loadMore) {
      skip = adapter.getCount();
    } else {
      if (getPullRefreshing() == false) {
        pullRefreshing();
      }
      skip = 0;
    }
    new SimpleNetTask(getContext(), false) {
      List<T> datas;

      @Override
      protected void doInBack() throws Exception {
        if (dataInterface != null) {
          datas = dataInterface.getDatas(skip, ONE_PAGE_SIZE, adapter.getDatas());
        } else {
          datas = new ArrayList<T>();
        }
      }

      @Override
      protected void onSucceed() {
        if (loadMore == false) {
          stopRefresh();
          adapter.setDatas(datas);
          adapter.notifyDataSetChanged();
          if (datas.size() < ONE_PAGE_SIZE) {
            if (isToastIfEmpty()) {
              if (datas.size() == 0) {
                Utils.toast(getContext(), R.string.listEmptyHint);
              }
            }
            setPullLoadEnable(false);
          } else {
            setPullLoadEnable(true);
          }
        } else {
          stopLoadMore();
          adapter.addAll(datas);
          if (datas.size() == 0) {
            Utils.toast(getContext(), R.string.noMore);
          }
        }
      }
    }.execute();
  }

  @Override
  public void onLoadMore() {
    loadDatas(true);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    T item = (T) parent.getAdapter().getItem(position);
    if (dataInterface != null) {
      dataInterface.onItemSelected(item);
    }
  }


  @Override
  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    T item = (T) parent.getAdapter().getItem(position);
    if (dataInterface != null) {
      dataInterface.onItemLongPressed(item);
    }
    return false;
  }


  public boolean isToastIfEmpty() {
    return toastIfEmpty;
  }

  public void setToastIfEmpty(boolean toastIfEmpty) {
    this.toastIfEmpty = toastIfEmpty;
  }
}
