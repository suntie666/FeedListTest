package com.study.feedlist.entity;

import java.util.List;

/**
 * Created by HelloWorld on 9/14/21.
 */
public class PicEntity extends BaseItemEntity{
    public PicEntity() {
        type=PIC_TYPE;
    }
    List<ImageEntity> urlList;

    public List<ImageEntity> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<ImageEntity> urlList) {
        this.urlList = urlList;
    }
}
