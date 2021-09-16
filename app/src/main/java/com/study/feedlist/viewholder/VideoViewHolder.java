package com.study.feedlist.viewholder;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.entity.PicEntity;
import com.study.feedlist.entity.VideoEntity;
import com.study.feedlist.interfaces.PlayListener;
import com.study.feedlist.widget.ImageGridView;
import com.study.feedlist.widget.SimpleVideoView;
import com.study.myapplication.R;

/**
 * Created by HelloWorld on 9/16/21.
 */
public class VideoViewHolder extends ItemViewHolder{
    SimpleVideoView videoView;
    TextView title;
    String playUrl;
    int pausePos;
    int videoDuration;
    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoView=itemView.findViewById(R.id.video_view);
        title = itemView.findViewById(R.id.video_title);
    }

    @Override
    public void setData(BaseItemEntity entity,int position) {
        if (entity instanceof VideoEntity) {
            playUrl=((VideoEntity) entity).getVideoUrl();
            videoView.setPosterUrl(((VideoEntity) entity).getPosterUrl());
//            videoView.setVideoPath(playUrl);
            title.setText("position "+position);
        }
    }

    @Override
    public void play(PlayListener listener) {
        videoView.initVideoView(playUrl);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (listener != null) {
                    listener.onPlayStop();
                }
            }
        });
        if (videoDuration == 0) {
            videoDuration=videoView.getDuration();
        }
        if (pausePos > 1000 && videoDuration < videoDuration - 1000) {
            videoView.seekTo(pausePos);
        }
        videoView.start();
    }

    @Override
    public void stopPlay() {
        pausePos=videoView.getCurrentPosition();
        videoView.pause();

    }
}
