package com.wpq.sample.custom_recyclerview.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author wpq
 * @version 1.0
 */
public abstract class BaseMultiViewTypeAdapter<T> extends RecyclerView.Adapter<BaseMultiViewTypeAdapter.RecyclerViewHolder>{

    private List<T> mList;
    private MultiViewTypeHelper<T> mMultiViewTypeHelper;

    protected static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private SparseArray<View> mViews;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (null == view) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

    }

    public BaseMultiViewTypeAdapter(List<T> list) {
        mList = list;
        mMultiViewTypeHelper = onCreateMultiViewType();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiViewTypeHelper == null) {
            throw new IllegalArgumentException("onCreateMultiViewType must not be null!");
        }
        View convertView = LayoutInflater.from(parent.getContext()).inflate(mMultiViewTypeHelper.getLayoutId(viewType), parent, false);
        return new RecyclerViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        onBind(holder, position, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiViewTypeHelper != null) {
            return mMultiViewTypeHelper.getItemViewType(position, mList.get(position));
        }
        return super.getItemViewType(position);
    }

    /**
     * 多种布局情况，用户自己配置布局情况
     * @return
     */
    protected abstract MultiViewTypeHelper<T> onCreateMultiViewType();

    /**
     * 设置事件监听和数据
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *               item at the given position in the data set.
     * @param position Position of the item whose data we want within the adapter's data set.
     * @param itemData 数据源
     */
    protected abstract void onBind(RecyclerViewHolder viewHolder, int position, T itemData);

    /**
     * Interface for multiple view types.
     * <p>
     * Created by Cheney on 15/11/28.
     */
    public interface MultiViewTypeHelper<T> {

        /**
         * Item view type, a non-negative integer is better.
         *
         * @param position current position of ViewHolder
         * @param t        model item
         * @return viewType
         */
        int getItemViewType(int position, T t);

        /**
         * Layout res.
         *
         * @param viewType {@link #getItemViewType(int, T)}
         * @return {@link LayoutRes}
         */
        @LayoutRes
        int getLayoutId(int viewType);

    }

}
