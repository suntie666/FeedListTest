package com.study.feedlist.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.study.feedlist.entity.BaseItemEntity;
import com.study.feedlist.entity.PicEntity;
import com.study.feedlist.interfaces.PlayListener;
import com.study.feedlist.widget.ImageGridView;
import com.study.feedlisttest.R;

/**
 * Created by HelloWorld on 9/14/21.
 */
public class PicViewHolder extends ItemViewHolder{
    ImageGridView imageGridView;
    TextView title;
    public PicViewHolder(@NonNull View itemView) {
        super(itemView);
        imageGridView=itemView.findViewById(R.id.image_grid_view);
        title = itemView.findViewById(R.id.pic_title);
    }

    @Override
    public void setData(BaseItemEntity entity,int position) {
        if (entity instanceof PicEntity) {
            imageGridView.setData(((PicEntity) entity).getUrlList());
            title.setText("position "+position);
        }
    }

    @Override
    public void play(PlayListener listener) {
        imageGridView.playGif(listener);
    }

    @Override
    public void stopPlay() {
        imageGridView.stopPlayGif();
    }
}
