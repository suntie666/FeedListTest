package com.study.feedlist.utils;

import android.graphics.drawable.Animatable;
import android.view.animation.Animation;

import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by HelloWorld on 9/15/21.
 */
public class ImageLoader {
    public static void loadGif(SimpleDraweeView simpleDraweeView, String url) {
        final DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(false)
                .setUri(url)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(
                            String id,
                            @Nullable ImageInfo imageInfo,
                            @Nullable Animatable animatable) {
//                        if (animatable != null) {
//                            animatable.start();
//                        }
                    }
                })
                .build();
        simpleDraweeView.setController(controller);
    }
}
