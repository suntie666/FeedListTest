package com.study.feedlist.activity;

import com.study.feedlist.interfaces.IModel;
import com.study.feedlist.interfaces.IPresenter;
import com.study.feedlist.interfaces.IView;

/**
 * Created by HelloWord on 9/14/21.
 */
public class FeedListPresenter implements IPresenter {
  IView view;
  IModel model;

  public FeedListPresenter(IView view) {
    this.view = view;
    createModel();
  }

  @Override
  public void createModel() {
    model= new FeedListModel();
  }

  @Override
  public void onResume() {
    view.setData(model.getDataList());
  }

  @Override
  public void onDestroy() {
    if (view != null) {
      view = null;
    }
    if (model != null) {
      model=null;
    }
  }
}
