package com.study.feedlist.viewholder;

import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.entity.PicEntity;
import com.study.feedlist.entity.VideoEntity;
import com.study.feedlist.interfaces.PlayListener;
import com.study.feedlist.widget.ImageGridView;
import com.study.myapplication.R;

/**
 * Created by HelloWorld on 9/16/21.
 */
public class VideoViewHolder extends ItemViewHolder{
    VideoView videoView;
    TextView title;
    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoView=itemView.findViewById(R.id.image_grid_view);
        title = itemView.findViewById(R.id.pic_title);
    }

    @Override
    public void setData(BaseItemEntity entity,int position) {
        if (entity instanceof VideoEntity) {

        }
    }

    @Override
    public void play(PlayListener listener) {
        //todo
    }

    @Override
    public void stopPlay() {
        //todo
    }
}
