package com.study.feedlist.activity;

import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.entity.ImageEntity;
import com.study.feedlist.entity.PicEntity;
import com.study.feedlist.entity.VideoEntity;
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
            if (i == 7) {
                innerLoopNum = 9;//九宫格位置
            } else {
                innerLoopNum=6;//6宫格位置
            }
            if (i==2 || i==5){//视频位置
                VideoEntity videoEntity = new VideoEntity();
                videoEntity.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
                videoEntity.setPosterUrl("https://s3.bmp.ovh/imgs/2021/09/0c07f8e6027ce4c3.webp");
                dataList.add(videoEntity);
                continue;
            }
            for (int j = 0; j <innerLoopNum ; j++) {
                ImageEntity imageEntity=new ImageEntity();
                if (j == 1 || j==5) {
                    imageEntity.setGif(false);
                    imageEntity.setAnimationUrl("");
                    imageEntity.setStaticUrl("https://s3.bmp.ovh/imgs/2021/09/fc48d97cade88845.webp");
                } else {
                    imageEntity.setGif(true);
                    imageEntity.setAnimationUrl("https://wx4.sinaimg.cn/large/a6a681ebgy1gp0wfth8w0g206o06ognh.gif");
                    imageEntity.setStaticUrl("https://s3.bmp.ovh/imgs/2021/09/4f035dc09c3dc252.webp");
                }
                list.add(imageEntity);
            }
            PicEntity picEntity=new PicEntity();
            picEntity.setUrlList(list);
            dataList.add(picEntity);
        }
    }




}
