package com.wpq.sample.custom_recyclerview.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 只封装加载更多，下拉刷新配合：https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 * @author wpq
 * @version 1.0
 */
public class MyRecyclerView extends RecyclerView {

    public static final String TAG = MyRecyclerView.class.getSimpleName();

    /** headers viewType，取值较大，避免跟数据区域的viewType重复，如有重复则需调整 */
    private static final int VIEW_TYPE_HEADER_INIT = 100001;
    /** footers viewType */
    private static final int VIEW_TYPE_FOOTER_INIT = 200001;
    /** LoadMore viewType */
    private static final int VIEW_TYPE_LOAD_MORE = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private LoadMoreView mLoadMoreView;
    private boolean loadMoreEnabled = false;
    private WrapAdapter mWrapAdapter;
    private RecyclerView.AdapterDataObserver mAdapterDataObserver = new DataObserver();
    private OnLoadListener mOnLoadListener;
    /** 是否正在执行网络请求，切换标记位保证滚动到底部时不会频繁触发网络请求 */
    private boolean isLoading = false;
    private boolean noMore = false;
    /** 分页加载时，总数据不满一页，则不需要分页 */
    private boolean noNeedToLoadMore = true;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(@NonNull View headerView) {
        mHeaderViews.put(VIEW_TYPE_HEADER_INIT + mHeaderViews.size(), headerView);
        mAdapterDataObserver.onChanged();
    }

    /**
     * 必须在 {@link #setLoadMoreEnabled(boolean)} 之前调用才生效
     * @param footerView View for Footer
     */
    public void addFooterView(@NonNull View footerView) {
        // 如果加载更多为true，则不能有FooterView
        if (loadMoreEnabled) {
            return;
        }
        mFooterViews.put(VIEW_TYPE_FOOTER_INIT + mFooterViews.size(), footerView);
        mAdapterDataObserver.onChanged();
    }

    public void removeHeaderView(@NonNull View headerView) {
        for(int i = 0; i < mHeaderViews.size(); i++) {
            if (headerView.equals(mHeaderViews.valueAt(i))) {
                mHeaderViews.removeAt(i);
                mAdapterDataObserver.onChanged();
                break;
            }
        }
    }

    public void removeFooterView(@NonNull View footerView) {
        for (int i = 0; i < mFooterViews.size(); i++) {
            if (footerView.equals(mFooterViews.valueAt(i))) {
                mFooterViews.removeAt(i);
                mAdapterDataObserver.onChanged();
            }
        }
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    /**
     * 必须在 {@link #addFooterView(View)} 之前调用才生效
     * @param loadMoreEnabled 是否加载更多
     */
    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        // 如果FooterView不为空，则不能有加载更多
        if (getFootersCount() > 0) {
            return;
        }
        this.loadMoreEnabled = loadMoreEnabled;
        if (this.loadMoreEnabled) {
            mLoadMoreView = new LoadMoreView(getContext(), new LoadMoreView.OnClickListener() {
                @Override
                public void onLoadMoreViewClick() {
                    doLoadMore();
                }
            });
        }
    }

    private void doLoadMore() {
        isLoading = true;
        noMore = false;
        noNeedToLoadMore = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.STATE_LOADING);
        }
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoadMore();
        }
    }

    public void loadMoreComplete() {
        isLoading = false;
        noMore = false;
        noNeedToLoadMore = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.STATE_COMPLETE);
        }
    }

    public void loadMoreError() {
        isLoading = false;
        noMore = false;
        noNeedToLoadMore = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.STATE_ERROR);
        }
    }

    /**
     * 没有更多了
     */
    public void noMore(){
        isLoading = false;
        noMore = true;
        noNeedToLoadMore = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.STATE_NOMORE);
        }
    }

    /**
     * 全部数据不足一页(分页时)，不需要显示加载更多
     */
    public void noNeedToLoadMore() {
        isLoading = false;
        noMore = true;
        noNeedToLoadMore = true;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mAdapterDataObserver);
        mAdapterDataObserver.onChanged();
    }

    @Override
    public Adapter getAdapter() {
        if (mWrapAdapter != null) {
            return mWrapAdapter.getInnerAdapter();
        }
        return super.getAdapter();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        /*  state：
            SCROLL_STATE_IDLE     = 0 ：静止,没有滚动
            SCROLL_STATE_DRAGGING = 1 ：正在被外部拖拽,一般为用户正在用手指滚动
            SCROLL_STATE_SETTLING = 2 ：自动滚动开始
        */

//        Log.e(TAG, state + ", " + this.canScrollVertically(1));
        // 判断RecyclerView滚动到底部，参考：http://www.jianshu.com/p/c138055af5d2
        if (state == RecyclerView.SCROLL_STATE_IDLE && !this.canScrollVertically(1) && loadMoreEnabled && !noMore && !isLoading) {
            doLoadMore();
        }
    }

    /** wrap header、footer、loadMore */
    private class WrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private RecyclerView.Adapter mInnerAdapter;

        private class WrapViewHolder extends RecyclerView.ViewHolder {
            public WrapViewHolder(View itemView) {
                super(itemView);
            }
        }

        public WrapAdapter(RecyclerView.Adapter innerAdapter) {
            this.mInnerAdapter = innerAdapter;
        }

        public RecyclerView.Adapter getInnerAdapter() {
            return mInnerAdapter;
        }

        public int getInnerItemCount() {
            return mInnerAdapter.getItemCount();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mHeaderViews.get(viewType) != null) {
                return new WrapViewHolder(mHeaderViews.get(viewType));
            }
            if (mFooterViews.get(viewType) != null) {
                return new WrapViewHolder(mFooterViews.get(viewType));
            }
            if (viewType == VIEW_TYPE_LOAD_MORE) {
                return new WrapViewHolder(mLoadMoreView);
            }
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isHeader(position) || isFooter(position) || isLoadMore(position)) {
                return;
            }
            //noinspection unchecked
            mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
        }

        @Override
        public int getItemCount() {
            if (loadMoreEnabled) {
                // 如果是加载更多，相当于这个加载更多是一个FooterView
                return getHeadersCount() + getInnerItemCount() + (noNeedToLoadMore ? 0 : 1);
            } else {
                // 如果不是加载更多，那就可能有多个FooterView
                return getHeadersCount() + getInnerItemCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position)) {
                return mHeaderViews.keyAt(position);
            }
            if (isFooter(position)) {
                return mFooterViews.keyAt(position - getHeadersCount() - getInnerItemCount());
            }
            if (isLoadMore(position)) {
                return VIEW_TYPE_LOAD_MORE;
            }
            return mInnerAdapter.getItemViewType(position - getHeadersCount());
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return isHeader(position) || isFooter(position) || isLoadMore(position) ? gridLayoutManager.getSpanCount() : 1;
                    }
                });
            }
            mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            int position = holder.getLayoutPosition();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && (isHeader(position) || isFooter(position) || isLoadMore(position))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            //noinspection unchecked
            mInnerAdapter.onViewAttachedToWindow(holder);
        }

        private boolean isHeader(int position) {
            return position < getHeadersCount();
        }

        private boolean isFooter(int position) {
            return getFootersCount() > 0 && position >= getHeadersCount() + getInnerItemCount();
        }

        private boolean isLoadMore(int position) {
            // 如果是加载更多 && 是最后一项，就是LoadMore
            return loadMoreEnabled && position == getItemCount() - 1 && !noNeedToLoadMore;
        }
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }
    };

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.mOnLoadListener = onLoadListener;
    }

    public interface OnLoadListener{
        void onLoadMore();
    }


}
