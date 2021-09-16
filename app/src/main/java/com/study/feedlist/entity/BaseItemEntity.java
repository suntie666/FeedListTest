package com.study.feedlist.entity;

/**
 * Created by HelloWorld on 9/14/21.
 */
public class BaseItemEntity {
    public static final int PIC_TYPE=1;
    public static final int VIDEO_TYPE=2;
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
