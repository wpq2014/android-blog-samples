package com.wpq.sample.custom_recyclerview.recyclerview;

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
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.RecyclerViewHolder>{

    private List<T> mList;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

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

    public BaseRecyclerAdapter(List<T> list) {
        mList = list;
    }

    /**
     * 刷新
     * @param list
     */
    public void update(List<T> list) {
        if (list == null) return;
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (0 == getLayoutId()) {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        View convertView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        init(convertView);
        RecyclerViewHolder holder = new RecyclerViewHolder(convertView);
        setListeners(holder);
        return holder;
    }

    private void setListeners(final RecyclerViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(holder);
                }
                return true;
            }
        });
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
        return super.getItemViewType(position);
    }

    /**
     * convertView layoutId
     *
     * @return resource ID for an XML layout resource to load (e.g.,
     *         <code>R.layout.main_page</code>)
     */
    protected abstract int getLayoutId();

    /**
     * 初始化操作，比如设置 LayoutParams
     *
     * @param convertView 当前itemView
     */
    protected abstract void init(View convertView);

    /**
     * 设置事件监听和数据
     *
     * @param viewHolder A ListViewHolder describes an item view and metadata about its place within the ListView|GridView
     * @param position Position of the item whose data we want within the adapter's data set.
     * @param itemData 数据源
     */
    protected abstract void onBind(RecyclerViewHolder viewHolder, int position, T itemData);

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView.ViewHolder viewHolder);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
