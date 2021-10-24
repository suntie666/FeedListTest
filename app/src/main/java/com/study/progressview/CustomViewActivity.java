package com.study.progressview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.study.feedlisttest.R;

public class CustomViewActivity extends AppCompatActivity {
    CircleProgressView progressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        progressView = findViewById(R.id.progress_bar);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            for (int i = 0; i <=100; i++) {
                progressView.setProgress(i,10000);
            }
        }
    }
}