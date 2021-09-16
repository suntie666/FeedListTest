package com.study.feedlist.entity;

/**
 * Created by HelloWorld on 9/16/21.
 */
public class VideoEntity extends BaseItemEntity{
    public VideoEntity() {
        type=VIDEO_TYPE;
    }
    String videoUrl;
    String posterUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
