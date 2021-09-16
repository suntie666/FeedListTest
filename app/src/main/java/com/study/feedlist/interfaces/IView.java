package com.study.feedlist.interfaces;

import com.study.feedlist.entity.BaseItemEntity;

import java.util.List;

public interface IView {
    void createPresenter();
    void setData(List<BaseItemEntity> list);
}
