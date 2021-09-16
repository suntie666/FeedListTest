package com.study.feedlist.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.interfaces.PlayListener;

/**
 * Created by HelloWorld on 9/14/21.
 */
public abstract class ItemViewHolder extends RecyclerView.ViewHolder{
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setData(BaseItemEntity entity,int position);

    public abstract void play(PlayListener listener);
    public abstract void stopPlay();
}
