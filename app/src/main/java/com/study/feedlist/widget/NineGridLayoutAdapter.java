package com.study.feedlist.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class NineGridLayoutAdapter<T> {
    protected List<T> mImgDataList;

    public NineGridLayoutAdapter(List<T> imgDataList){
        mImgDataList = imgDataList;
    }

    public void setDataList(List<T> imgDataList){
        mImgDataList = imgDataList;
    }

    public int getCount(){
        return mImgDataList == null?0:mImgDataList.size();
    }

    public T getItem(int position){
        return mImgDataList == null?null:mImgDataList.get(position);
    }

    public abstract void bindView(Context context, View convertView, int position);

    public abstract View createView(ViewGroup parent, int position);

    public static class BaseViewHolder{
        public View itemView;

        public BaseViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}