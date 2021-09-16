package com.study.feedlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.viewholder.EmptyViewHolder;
import com.study.feedlist.viewholder.ItemViewHolder;
import com.study.feedlist.viewholder.PicViewHolder;
import com.study.feedlist.viewholder.VideoViewHolder;
import com.study.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HelloWord on 9/14/21.
 */
public class FeedRecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<BaseItemEntity> dataList = new ArrayList<>();

    public FeedRecyclerAdapter() {
    }

    public void setDataList(List<BaseItemEntity> dataList) {
        this.dataList.addAll(dataList);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == BaseItemEntity.PIC_TYPE) {
            holder = new PicViewHolder(inflater.inflate(R.layout.pics_layout, parent, false));
        } else if (viewType == BaseItemEntity.VIDEO_TYPE) {
            holder = new VideoViewHolder(inflater.inflate(R.layout.video_layout, parent, false));
        } else {
            holder = new EmptyViewHolder(inflater.inflate(R.layout.empty_layout, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
