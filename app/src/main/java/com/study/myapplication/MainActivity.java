package com.study.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.study.progressview.CustomViewActivity;
import com.study.feedlist.activity.FeedListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toFeedList(View view) {
        Intent intent = new Intent(this, FeedListActivity.class);
        this.startActivity(intent);
    }

    public void toCustomView(View view) {
        Intent intent = new Intent(this, CustomViewActivity.class);
        this.startActivity(intent);
    }
}