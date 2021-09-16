package com.study.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by HelloWorld on 9/14/21.
 */
public class ViewUtils {
    public static int dp2px(Context context,float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return (int) (px + 0.5f);
    }
    public static int getScreenWidth(Context context) {
        return getWindowSize(context).x;
    }

    public static int getScreenHeight(Context context) {
        return getWindowSize(context).y;
    }
    private static Point mScreenSize;
    public static Point getWindowSize(Context context) {
        if(mScreenSize == null) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            mScreenSize = new Point();
            display.getSize(mScreenSize);
        }

        return mScreenSize;
    }
}
