package com.wpq.sample.custom_recyclerview.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private MyRecyclerView myRecyclerView;
    private int spanCount;
    private int space;
    private boolean includeEdge;

    /**
     * @param myRecyclerView {@link MyRecyclerView}
     * @param space 分割线宽度
     * @param includeEdge 屏幕所有两边是否需要画分割线
     */
    public GridSpaceItemDecoration(MyRecyclerView myRecyclerView, int space, boolean includeEdge) {
        this.myRecyclerView = myRecyclerView;
        RecyclerView.LayoutManager layoutManager = myRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            this.spanCount = gridLayoutManager.getSpanCount();
        }
        this.space = space;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (myRecyclerView.isHeader(position) || myRecyclerView.isFooter(position) || myRecyclerView.isLoadMore(position)) {
            return;
        }
        int column = (position - myRecyclerView.getHeadersCount()) % spanCount;
        if (includeEdge) {
            outRect.left = space - column * space / spanCount;
            outRect.right = (column + 1) * space / spanCount;
            outRect.bottom = space;
        } else {
            outRect.left = column * space / spanCount;
            outRect.right = space - (column + 1) * space / spanCount;
            outRect.bottom = space;
        }
    }
}