package com.example.zxg.myprogram.view;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class LoopLayoutManager extends RecyclerView.LayoutManager {

    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int VERTICAL = OrientationHelper.VERTICAL;

    private int mOrientation = HORIZONTAL;
    private boolean looperEnable = true;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation:" + orientation);
        }
        assertNotInLayoutOrScroll(null);
        if (orientation != mOrientation) {
            mOrientation = orientation;
            requestLayout();
        }
    }

    public void setLooperEnable(boolean looperEnable) {
        this.looperEnable = looperEnable;
    }

    @Override
    public boolean canScrollHorizontally() {
        return mOrientation == HORIZONTAL;
    }

    @Override
    public boolean canScrollVertically() {
        return mOrientation == VERTICAL;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) return;
        //preLayout主要支持动画，直接跳过
        if (state.isPreLayout()) return;

        //将视图分离放入scrap缓存中，以准备重新对view进行排版
        detachAndScrapAttachedViews(recycler);

        int autualSize = 0;
        for (int i = 0; i < getItemCount(); i++) {
            //初始化，将在屏幕内的view填充
            View itemView = recycler.getViewForPosition(i);
            addView(itemView);

            //测量itemView的宽高
            measureChildWithMargins(itemView, 0, 0);
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            int leftMargin = lp.leftMargin;
            int topMargin = lp.topMargin;
            int rightMargin = lp.rightMargin;
            int bottomMargin = lp.bottomMargin;

            if (canScrollHorizontally()) {
                //根据itemView的宽高进行布局
                layoutDecorated(itemView, autualSize + leftMargin, topMargin, autualSize + leftMargin + width, topMargin + height);

                autualSize += leftMargin + width + rightMargin;
                //如果当前布局过的itemView的宽度总和大于RecyclerView的宽，则不再进行布局
                if (autualSize >= getWidth()) break;
            } else {
                //根据itemView的宽高进行布局
                layoutDecorated(itemView, leftMargin, autualSize + topMargin, leftMargin + width, autualSize + topMargin + height);

                autualSize += topMargin + height + bottomMargin;
                //如果当前布局过的itemView的宽度总和大于RecyclerView的宽，则不再进行布局
                if (autualSize >= getHeight()) break;
            }
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int tral = addNextView(dx, recycler);
        if (tral != 0) {
            // 水平滑动
            offsetChildrenHorizontal(-tral);
            // 回收不可见的itemView
            recyclerHideView(dx, recycler);
        }
        return tral;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int tral = addNextView(dy, recycler);
        if (tral != 0) {
            // 水平滑动
            offsetChildrenVertical(-tral);
            // 回收不可见的itemView
            recyclerHideView(dy, recycler);
        }
        return tral;
    }

    /**
     * 获得下一个要加载的itemView
     * @param dx
     * @param recycler
     * @return
     */
    private int addNextView(int dx, RecyclerView.Recycler recycler) {
        if (dx > 0) {
            // RecyclerView中已加载的最后一个itemView
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) return 0;

            // RecyclerView中已加载的最后一个itemView的位置
            int lastPos = getPosition(lastView);
            if ((canScrollHorizontally() && lastView.getRight() <= getWidth())
                    || (canScrollVertically() && lastView.getBottom() <= getHeight())) {
                View scrap = null;

                // RecyclerView中已加载的最后一个itemView的位置是否是RecyclerView的最后一个item
                if (lastPos == getItemCount() - 1) {
                    if (looperEnable) {
                        // 取RecyclerView中的第一个itemView
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }

                if (scrap != null) {
                    addView(scrap);
                    measureChildWithMargins(scrap, 0, 0);
                    int width = getDecoratedMeasuredWidth(scrap);
                    int height = getDecoratedMeasuredHeight(scrap);
                    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) scrap.getLayoutParams();
                    int leftMargin = lp.leftMargin;
                    int topMargin = lp.topMargin;
                    ViewGroup.MarginLayoutParams lp1 = (ViewGroup.MarginLayoutParams) lastView.getLayoutParams();
                    int rightMargin = lp1.rightMargin;
                    int bottomMargin = lp1.bottomMargin;
                    if (canScrollHorizontally()) {
                        layoutDecorated(scrap, lastView.getRight() + rightMargin + leftMargin, topMargin, lastView.getRight() + rightMargin + leftMargin + width, topMargin + height);
                    } else {
                        layoutDecorated(scrap, leftMargin, lastView.getBottom() + bottomMargin + topMargin, leftMargin + width, lastView.getBottom() + bottomMargin + topMargin + height);
                    }
                }
            }
        } else {
            // RecyclerView中已加载的第一个itemView
            View firstView = getChildAt(0);
            if (firstView == null) return 0;

            // RecyclerView中已加载的第一个itemView的位置
            int firstPos = getPosition(firstView);
            if ((canScrollHorizontally() && firstView.getLeft() >= 0)
                    || (canScrollVertically() && firstView.getTop() >= 0)) {
                View scrap = null;

                // RecyclerView中已加载的第一个itemView的位置是否是RecyclerView的第一个item
                if (firstPos == 0) {
                    if (looperEnable) {
                        // 取RecyclerView中的最后一个itemView
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }

                if (scrap != null) {
                    addView(scrap, 0);
                    measureChildWithMargins(scrap, 0, 0);
                    int width = getDecoratedMeasuredWidth(scrap);
                    int height = getDecoratedMeasuredHeight(scrap);
                    ViewGroup.MarginLayoutParams lpFirst = (ViewGroup.MarginLayoutParams) firstView.getLayoutParams();
                    ViewGroup.MarginLayoutParams lpScrap = (ViewGroup.MarginLayoutParams) scrap.getLayoutParams();
                    if (canScrollHorizontally()) {
                        layoutDecorated(scrap, firstView.getLeft() - lpFirst.leftMargin - lpScrap.rightMargin - width, lpScrap.topMargin, firstView.getLeft() - lpFirst.leftMargin - lpScrap.rightMargin, lpScrap.topMargin + height);
                    } else {
                        layoutDecorated(scrap, lpScrap.leftMargin, firstView.getTop() - lpFirst.topMargin - lpScrap.bottomMargin - height, lpScrap.leftMargin + width, firstView.getTop() - lpFirst.topMargin - lpScrap.bottomMargin);
                    }
                }
            }
        }
        return dx;
    }

    /**
     * 回收完全不可见的itemView（水平方向）
     * @param dx
     * @param recycler
     */
    private void recyclerHideView(int dx, RecyclerView.Recycler recycler) {

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == null) continue;

            if(canScrollHorizontally()) {
                if ((dx > 0 && view.getRight() < 0) || (dx <= 0 && view.getLeft() > getWidth())) {
                    removeAndRecycleView(view, recycler);
                }
            } else {
                if ((dx > 0 && view.getBottom() < 0) || (dx <= 0 && view.getTop() > getHeight())) {
                    removeAndRecycleView(view, recycler);
                }
            }
        }
    }
}
