package com.study.feedlist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.study.feedlist.adapter.FeedRecyclerAdapter;
import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.interfaces.IPresenter;
import com.study.feedlist.interfaces.IView;
import com.study.feedlist.interfaces.PlayListener;
import com.study.feedlist.viewholder.ItemViewHolder;
import com.study.feedlist.viewholder.PicViewHolder;
import com.study.myapplication.R;

import java.util.List;

/**
 * Created by HelloWord on 9/14/21.
 */
public class FeedListActivity extends AppCompatActivity implements IView {
    IPresenter presenter;
    RecyclerView recyclerView;
    FeedRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    static final int VisiblePercentLimit=70;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        createPresenter();
        initView();
    }

    @Override
    public void createPresenter() {
        presenter = new FeedListPresenter(this);
    }

    @Override
    public void setData(List<BaseItemEntity> list) {
        if (adapter != null) {
            adapter.setDataList(list);
            idleStateTask();
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new FeedRecyclerAdapter();
        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Fresco.getImagePipeline().resume();
                    idleStateTask();
                } else {
                    Fresco.getImagePipeline().pause();
                    scrollStateChangedCall(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
        if (scrollStateIdleRunnable != null) {
            removeRunnable(scrollStateIdleRunnable);
        }
    }

    private void idleStateTask() {
        scrollStateChangedCall(true);
    }

    private void scrollStateChangedCall(boolean isIdle) {
        removeRunnable(getIdleRunnable());
        if (isIdle) {
            postDelayed(getIdleRunnable(), 500);
        }
    }

    private Runnable scrollStateIdleRunnable;
    private Runnable getIdleRunnable() {
        if (scrollStateIdleRunnable == null) {
            scrollStateIdleRunnable = new IdleStateRunnable(linearLayoutManager);
        }
        return scrollStateIdleRunnable;
    }

    protected void postDelayed(Runnable runnable,long delayMills){
        if (recyclerView!=null){
            recyclerView.postDelayed(runnable,delayMills);
        }
    }

    protected void removeRunnable(Runnable runnable){
        if (recyclerView!=null){
            recyclerView.removeCallbacks(runnable);
        }
    }

    private int firstVisiblePosition,lastVisiblePosition;
    private class IdleStateRunnable implements Runnable{
        RecyclerView.LayoutManager layoutManager;

        public IdleStateRunnable(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void run() {
            if(layoutManager instanceof LinearLayoutManager){
                firstVisiblePosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                Rect rvRect = new Rect();
                recyclerView.getGlobalVisibleRect(rvRect);
                orderPlay(firstVisiblePosition,rvRect);
            }
        }
    }

    private void orderPlay(final int position, Rect parentRect){
        if (position <= lastVisiblePosition) {
            ItemViewHolder viewHolder = (ItemViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

            if (itemVisiblePercent(position, parentRect) > VisiblePercentLimit) {
                if (viewHolder != null) {
                    for (int i = position+1; i <=lastVisiblePosition; i++) {
                        ItemViewHolder tempViewHolder = (ItemViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (tempViewHolder instanceof PicViewHolder) {
                            tempViewHolder.stopPlay();
                        }
                    }
                    viewHolder.play(new PlayListener() {
                        @Override
                        public void onPlayStop() {
                            int nextPos = position + 1;
                            orderPlay(nextPos, parentRect);
                        }
                    });
                }
            }else {
                if (viewHolder != null) {
                    viewHolder.stopPlay();
                }
                int nextPos = position + 1;
                orderPlay(nextPos, parentRect);
            }
        }
    }



    private int itemVisiblePercent(int position, Rect parentRect) {
        int visiblePercent;
        View itemView = linearLayoutManager.findViewByPosition(position);
        if (itemView != null) {
            int itemHeight = itemView.getHeight();
            Rect rowRect = new Rect();
            itemView.getGlobalVisibleRect(rowRect);
            int visibleHeightFirst;
            if (rowRect.bottom >= parentRect.bottom) {
                visibleHeightFirst = parentRect.bottom - rowRect.top;
            } else {
                visibleHeightFirst = rowRect.bottom - parentRect.top;
            }
            visiblePercent = (visibleHeightFirst * 100) / itemHeight;
            if (visiblePercent > 100) visiblePercent = 100;
            Log.e("hello",position+" "+visiblePercent);
            return visiblePercent;
        }
        return 0;
    }
}