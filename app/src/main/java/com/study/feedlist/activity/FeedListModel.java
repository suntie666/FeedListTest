package com.study.feedlist.activity;

import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.entity.ImageEntity;
import com.study.feedlist.entity.PicEntity;
import com.study.feedlist.interfaces.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HelloWord on 9/14/21.
 */
public class FeedListModel implements IModel {
    private List<BaseItemEntity> dataList;

    public FeedListModel() {
        requestData();
    }

    @Override
    public List<BaseItemEntity> getDataList() {
        return dataList;
    }

    //模拟数据
    private void requestData() {
        int outLoopNum=29;
        int innerLoopNum=0;
        dataList = new ArrayList<>();
        for (int i = 0; i <outLoopNum ; i++) {
            List<ImageEntity> list = new ArrayList<>();
            if (i == 3) {
                innerLoopNum = 9;
            } else {
                innerLoopNum=6;
            }
            for (int j = 0; j <innerLoopNum ; j++) {
                ImageEntity entity=new ImageEntity();
                if (j == 1 || j==5) {
                    entity.setGif(false);
                    entity.setAnimationUrl("");
                    entity.setStaticUrl("https://s3.bmp.ovh/imgs/2021/09/fc48d97cade88845.webp");
                } else {
                    entity.setGif(true);
                    entity.setAnimationUrl("https://wx4.sinaimg.cn/large/a6a681ebgy1gp0wfth8w0g206o06ognh.gif");
                    entity.setStaticUrl("https://s3.bmp.ovh/imgs/2021/09/4f035dc09c3dc252.webp");
                }
                list.add(entity);
            }
            PicEntity entity=new PicEntity();
            entity.setUrlList(list);
            dataList.add(entity);
        }
    }




}
