package com.study.feedlist.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.study.utils.ViewUtils;

/**
 * Created by HelloWorld on 9/16/21.
 */
public class SimpleVideoView extends RelativeLayout {
    VideoView videoView;
    TextView seekView;
    SimpleDraweeView simpleDraweeView;
    int width = ViewUtils.getScreenWidth(getContext());
    int height=width*9/16;
    RelativeLayout.LayoutParams videoLayoutParams;
    public SimpleVideoView(Context context) {
        super(context);
        init(context);
    }

    public SimpleVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        simpleDraweeView=new SimpleDraweeView(context);
        videoLayoutParams =  new RelativeLayout.LayoutParams(width, height);
        this.addView(simpleDraweeView,videoLayoutParams);

    }

    public void setPosterUrl(String posterUrl) {
        simpleDraweeView.setImageURI(posterUrl);
    }

    public void initVideoView(String videoUrl) {
        if (videoView == null) {
            videoView = new VideoView(getContext());
            videoView.setVideoPath(videoUrl);
        }
        if (seekView == null) {
            seekView = new TextView(getContext());
            seekView.setText("seekToEnd");
            seekView.setBackgroundColor(0xffffffff);
            seekView.setGravity(Gravity.CENTER);
            seekView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.seekTo(videoView.getDuration()-1000);
                }
            });
        }
        if (videoView.getParent() == null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.addView(videoView,videoLayoutParams);
        }

        if (seekView.getParent() == null) {
            RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewUtils.dp2px(getContext(),30));
            textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.addView(seekView,textLayoutParams);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (videoView != null) {
            videoView.stopPlayback();
            this.removeView(videoView);
            videoView=null;
            this.removeView(seekView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (videoView == null) {
//            initVideoView();
        }
    }

    public void setVideoPath(String playUrl) {
        if (videoView != null){
            videoView.setVideoPath(playUrl);
        }
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        if (videoView != null) {
            videoView.setOnCompletionListener(onCompletionListener);
        }
    }

    public int getDuration() {
        if (videoView != null) return videoView.getDuration();
        return 0;
    }

    public void seekTo(int pausePos) {
        if (videoView != null) videoView.seekTo(pausePos);
    }

    public void start() {
        if (videoView != null){
            videoView.start();

        }

    }

    public void pause() {
        if (videoView != null){ videoView.pause();}

    }

    public int getCurrentPosition() {
        if (videoView != null) {
            return videoView.getCurrentPosition();
        }
        return 0;
    }
}
