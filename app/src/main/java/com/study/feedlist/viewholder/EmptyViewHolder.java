package com.study.feedlist.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.interfaces.PlayListener;

/**
 * Created by HelloWorld on 9/14/21.
 */
public class EmptyViewHolder extends ItemViewHolder{

    public EmptyViewHolder(@NonNull View itemView) {
        super(itemView);

    }

    @Override
    public void setData(BaseItemEntity entity,int position) {

    }

    @Override
    public void play(PlayListener listener) {

    }

    @Override
    public void stopPlay() {

    }
}
