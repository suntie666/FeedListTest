package com.study.feedlist.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.study.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by HelloWorld on 9/14/21.
 */
public class NineGridLayout extends ViewGroup {

    public final static int STYLE_GRID = 0;
    private int mRowCount;
    protected int mColumnCount;
    protected int mShowStyle = STYLE_GRID;
    private int mGap=10;
    private int mItemSize;
    private boolean mDataChange;

    private List<View> mCacheViews = new ArrayList<>();

    private NineGridLayoutAdapter mAdapter;

    public NineGridLayout(Context context) {
        this(context, null);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int totalWidth = width - getPaddingLeft() - getPaddingRight();
        final int count = getChildCount();

        if (count == 0){
            setMeasuredDimension(width, 0);
        }

        if (mGap == 0){
            mGap = ViewUtils.dp2px(getContext(),2);
        }

        if (count > 0) {

                mItemSize = (totalWidth - mGap * (mColumnCount - 1)) / mColumnCount;
                final int height = mItemSize * mRowCount + mGap * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();
                for (int i =0;i< count;i++){
                    final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                            mItemSize, MeasureSpec.EXACTLY);
                    final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                            mItemSize , MeasureSpec.EXACTLY);
                    getChildAt(i).measure(childWidthMeasureSpec, childHeightMeasureSpec);
                }
                setMeasuredDimension(width, height);

        } else {
            setMeasuredDimension(width, 0);
        }
    }

    private int mWidth,mHeight,mCount;
    private boolean isImgSizeChanged;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = mItemSize;
        int height = getMeasuredHeight();
        int count = getChildCount();
        if (mWidth != width || mHeight != height || mCount != count || mDataChange){
            isImgSizeChanged = true;
        }
        layoutChildrenView();
        mWidth = mItemSize;
        mHeight = getMeasuredHeight();
        mCount = count;
        mDataChange = false;
        isImgSizeChanged = false;
    }

    /**
     * 布局子View
     */
    private void layoutChildrenView() {
        final int childrenCount = getChildCount();
        View firstChildView = null;
        for (int i = 0; i < childrenCount; i++) {
            final View childrenView = getChildAt(i);
            if (childrenView == null)
                return;
            if (childrenCount == 1) {
                childrenView.layout(0, 0, childrenView.getMeasuredWidth(),
                        childrenView.getMeasuredHeight());
            } else {
                int rowNum = i / mColumnCount;
                int columnNum = i % mColumnCount;
                //针对4张图片做特殊处理,空出一个
                if (childrenCount == 4) {
                    if (i == 2) {
                        rowNum = 1;
                        columnNum = 0;
                    } else if (i == 3) {
                        rowNum = 1;
                        columnNum = 1;
                    }
                }

                int left = (mItemSize + mGap) * columnNum + getPaddingLeft();
                int top = (mItemSize + mGap) * rowNum + getPaddingTop();
                int right = left + mItemSize;
                int bottom = top + mItemSize;
                childrenView.layout(left, top, right, bottom);
            }
        }
    }

    /**
     *
     */
    public void setAdapter(NineGridLayoutAdapter adapter) {
        if (adapter == null || adapter.getCount() < 1) {
            return;
        }
        int count  = adapter.getCount();
        mDataChange = true;
        mAdapter = adapter;
        int[] gridParam = calculateGridParam(count, mShowStyle);
        mRowCount = gridParam[0];
        mColumnCount = gridParam[1];

        int oldViewCount = getChildCount();
        int newViewCount = count;

        if (oldViewCount < newViewCount) {
            for (int i = oldViewCount; i < newViewCount; i++) {
                addItemView(i);
            }
        }else if (oldViewCount > newViewCount) {
            try {
                removeViews(newViewCount, oldViewCount - newViewCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0;i<getChildCount();i++){
            final View childrenView = getChildAt(i);
            mAdapter.bindView(getContext(), childrenView,i);
        }
        requestLayout();
    }

    private void addItemView(final int position) {
        View iv = getItemView(position);
        if (iv == null) {
            return;
        }
        addView(iv, generateDefaultLayoutParams());
    }

    protected View getItemView(final int position) {
        if (position < mCacheViews.size()) {
            return mCacheViews.get(position);
        } else {
            if (mAdapter != null) {
                View imageView = mAdapter.createView(this,position);
                mCacheViews.add(imageView);
                return imageView;
            } else {
                return null;
            }
        }
    }

    /**
     * @param imagesSize
     * @param showStyle
     * @return gridParam[0]gridParam[1]
     */
    protected static int[] calculateGridParam(int imagesSize, int showStyle) {
        int[] gridParam = new int[2];
        gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
        gridParam[1] = 3;
        return gridParam;
    }



}