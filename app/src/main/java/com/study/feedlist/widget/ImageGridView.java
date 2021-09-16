package com.study.feedlist.widget;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.animation.backend.AnimationBackendDelegate;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;
import com.facebook.fresco.animation.drawable.AnimationListener;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.study.feedlist.entity.ImageEntity;
import com.study.feedlist.interfaces.PlayListener;
import com.study.myapplication.R;
import java.util.List;

/**
 * Created by HelloWorld on 9/14/21.
 */
public class ImageGridView extends NineGridLayout{
    LayoutInflater inflater;
    ImageAdapter adapter;
    List<ImageEntity> dataList;
    public ImageGridView(Context context) {
        super(context);
    }

    public ImageGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater=LayoutInflater.from(context);
    }

    public void setData(List<ImageEntity> list) {
        dataList=list;
        adapter=new ImageAdapter(list);
        setAdapter(adapter);
    }

    @Override
    protected void onDetachedFromWindow() {
        for (int i = 0; i < adapter.getCount(); i++) {
            SimpleDraweeView draweeView=((GridHolder) getItemView(i).getTag()).itemImage;
            draweeView.setImageURI(adapter.getItem(i).getStaticUrl());
        }
        super.onDetachedFromWindow();
        Log.e("check1","onDetachedFromWindow");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("check1","onAttachedToWindow");
    }

    public void playGif(PlayListener listener){
        Log.e("check1","want to 0");
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPlaying()) {
                    return;
                }
                Log.e("check1","to play 0");
                playItemGif(0,listener);
            }
        },200);

    }

    private boolean isPlaying() {
        if (adapter == null) {
            return false;
        }
        for (int i = 0; i < adapter.getCount(); i++) {
            SimpleDraweeView draweeView=((GridHolder) getItemView(i).getTag()).itemImage;
            if (draweeView != null && draweeView.getController()!=null){
                Animatable animatable = draweeView.getController().getAnimatable();
                if (animatable != null && animatable.isRunning()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void stopPlayGif() {
        if (adapter == null) {
            return;
        }
        for (int i = adapter.getCount()-1; i >= 0; i--) {
            SimpleDraweeView draweeView=((GridHolder) getItemView(i).getTag()).itemImage;
            if (draweeView != null && draweeView.getController()!=null){
                Animatable animatable = draweeView.getController().getAnimatable();
                if (animatable != null) {
                    if (animatable instanceof AnimatedDrawable2){
                        ((AnimatedDrawable2) animatable).setAnimationListener(null);
                        ((AnimatedDrawable2) animatable).dropCaches();
                    }
                    animatable.stop();
                }
            }
        }
    }

    private void playItemGif(int position,PlayListener listener) {
        Log.e("check1","playItemGif "+position);
        if (adapter == null || adapter.getCount() <= 0 || position>=adapter.getCount()) {
            return;
        }

        if (!dataList.get(position).isGif()) {
            if (position < dataList.size()-1) {
                int pos=position+1;
                playItemGif(pos, listener);
            } else if (position == (dataList.size() - 1) && listener!=null) {
                listener.onPlayStop();
            }
        }else {
            SimpleDraweeView draweeView=((GridHolder) getItemView(position).getTag()).itemImage;
            if (draweeView != null) {
                ImageDecodeOptions options=ImageDecodeOptions.newBuilder().setForceStaticImage(false).setDecodePreviewFrame(true).build();
                ImageRequest request= ImageRequestBuilder.newBuilderWithSource(Uri.parse(dataList.get(position).getAnimationUrl())).setImageDecodeOptions(options).build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setAutoPlayAnimations(false)
                        .setControllerListener(new BaseControllerListener<ImageInfo>(){
                            @Override
                            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                                if (animatable instanceof AnimatedDrawable2) {
                                    ((AnimatedDrawable2) animatable).setAnimationBackend(new AnimationBackendDelegate(((AnimatedDrawable2) animatable).getAnimationBackend()) {
                                        @Override
                                        public int getLoopCount() {
                                            return 1;
                                        }
                                    });
                                    ((AnimatedDrawable2) animatable).setAnimationListener(new AnimationListener() {
                                        @Override
                                        public void onAnimationStart(AnimatedDrawable2 drawable) {
                                            Log.e("check1","onAnimationStart "+position);
                                        }

                                        @Override
                                        public void onAnimationStop(AnimatedDrawable2 drawable) {
                                            Log.e("check1","onAnimationStop "+position);
                                            final int pos=position+1;
                                            if (pos < adapter.getCount()) {
                                                playItemGif(pos, listener);
                                            } else {
                                                if (listener != null) {
                                                    listener.onPlayStop();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onAnimationReset(AnimatedDrawable2 drawable) {
                                        }

                                        @Override
                                        public void onAnimationRepeat(AnimatedDrawable2 drawable) {

                                        }

                                        @Override
                                        public void onAnimationFrame(AnimatedDrawable2 drawable, int frameNumber) {

                                        }
                                    });
                                    Log.e("check1","playItemGif to start "+position);
                                    animatable.start();
                                }
                            }
                        })
                        .build();
                draweeView.setController(controller);
            }
        }

    }

    class ImageAdapter extends NineGridLayoutAdapter<ImageEntity> {

        public ImageAdapter(List<ImageEntity> imgDataList) {
            super(imgDataList);
        }

        @Override
        public void bindView(Context context, View convertView, int position) {
            GridHolder holder= (GridHolder) convertView.getTag();
            holder.itemImage.setImageURI(mImgDataList.get(position).getStaticUrl());
            if (mImgDataList.get(position).isGif()) {
                holder.des.setText("动图");
            } else {
                holder.des.setText("静图");
            }
        }

        @Override
        public View createView(ViewGroup parent, int position) {
            View root=inflater.inflate(R.layout.image_grid_item,parent,false);
            GridHolder holder=new GridHolder();
            holder.itemImage=root.findViewById(R.id.image_view);
            holder.des = root.findViewById(R.id.image_des);
            root.setTag(holder);
            return root;
        }
    }
    private class GridHolder {
        SimpleDraweeView itemImage;
        TextView des;
    }
}
