package com.study.progressview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.study.myapplication.R;

/**
 * Created by HelloWorld on 9/15/21.
 */
public class CircleProgressView extends View {

    private Paint mBgPaint, mProgressPaint,mTextPaint;
    private RectF mRectF;
    private int[] mColorArray;
    private int mProgress;
    private float mTextSize;

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);

        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setDither(true);
        mBgPaint.setStrokeWidth(typedArray.getDimension(R.styleable.CircleProgressView_backWidth, 5));
        mBgPaint.setColor(typedArray.getColor(R.styleable.CircleProgressView_backColor, Color.LTGRAY));

        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setDither(true);
        mProgressPaint.setStrokeWidth(typedArray.getDimension(R.styleable.CircleProgressView_progressWidth, 10));
        mProgressPaint.setColor(typedArray.getColor(R.styleable.CircleProgressView_progressColor, Color.BLUE));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(2);
        mTextSize=typedArray.getDimension(R.styleable.CircleProgressView_progressTextSize, 10);
        mTextPaint.setTextSize(mTextSize);


        // 设置多种颜色
        int startColor = typedArray.getColor(R.styleable.CircleProgressView_progressStartColor, -1);
        int midColor = typedArray.getColor(R.styleable.CircleProgressView_progressMidColor, -1);
        int endColor = typedArray.getColor(R.styleable.CircleProgressView_progressEndColor, -1);
        if (startColor != -1 && endColor != -1) mColorArray = new int[]{startColor, midColor,endColor};
        else mColorArray = null;

        // 初始化进度
        mProgress = typedArray.getInteger(R.styleable.CircleProgressView_progress, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWide = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHigh = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int mRectLength = (int) ((viewWide > viewHigh ? viewHigh : viewWide) - (mBgPaint.getStrokeWidth() > mProgressPaint.getStrokeWidth() ? mBgPaint.getStrokeWidth() : mProgressPaint.getStrokeWidth()));
        int left = getPaddingLeft() + (viewWide - mRectLength) / 2;
        int right = getPaddingTop() + (viewHigh - mRectLength) / 2;
        mRectF = new RectF(left, right, left + mRectLength, right + mRectLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircleProgress(canvas);
        drawProgressText(canvas);
    }

    private void drawCircleProgress(Canvas canvas) {
        int size=mColorArray.length;
        int fragment=100/size;
        int index=mProgress/fragment;
        if (index >= size) {
            index=size-1;
        }
        mProgressPaint.setColor(mColorArray[index]);
        canvas.drawArc(mRectF, 0, 360, false, mBgPaint);
        canvas.drawArc(mRectF, 275, 360 * mProgress / 100, false, mProgressPaint);
    }

    private void drawProgressText(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }
        String text = (mProgress+1) + "%";//百分比
        int textWidth = (int) mTextPaint.measureText(text, 0, text.length());
        mTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + mTextSize / 3, mTextPaint);
    }

    /**
     * 设置当前进度
     *
     * @param progress 当前进度（0-100）
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    /**
     * 设置当前进度，并展示进度动画。如果动画时间小于等于0，则不展示动画
     *
     * @param progress 当前进度（0-100）
     * @param animTime 动画时间（毫秒）
     */
    public void setProgress(int progress, long animTime) {
        Log.e("lhh", progress+"");
        if (animTime <= 0) setProgress(progress);
        else {
            ValueAnimator animator = ValueAnimator.ofInt(mProgress, progress);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mProgress = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(animTime);
            animator.start();
        }
    }
}
