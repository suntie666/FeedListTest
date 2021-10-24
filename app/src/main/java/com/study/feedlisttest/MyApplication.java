package com.study.feedlisttest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by HelloWorld on 9/14/21.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
