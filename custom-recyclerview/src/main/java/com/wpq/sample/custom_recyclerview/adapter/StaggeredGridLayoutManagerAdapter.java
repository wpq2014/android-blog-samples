package com.wpq.sample.custom_recyclerview.adapter;

import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wpq.sample.custom_recyclerview.R;
import com.wpq.sample.custom_recyclerview.bean.GanHuo;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseRecyclerAdapter;

import java.util.List;

/**
 * @author wpq
 * @version 1.0
 */
public class StaggeredGridLayoutManagerAdapter extends BaseRecyclerAdapter<GanHuo.Result> {

    public StaggeredGridLayoutManagerAdapter(List<GanHuo.Result> list) {
        super(list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_staggeredgridlayoutmanager;
    }

    @Override
    protected void init(RecyclerViewHolder viewHolder) {

    }

    @Override
    protected void onBind(RecyclerViewHolder viewHolder, int position, GanHuo.Result itemData) {
        ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
        lp.height = itemData.getScaledHeight();
        viewHolder.itemView.setLayoutParams(lp);

        SimpleDraweeView imageView = viewHolder.getView(R.id.imageView);
        imageView.setImageURI(itemData.getUrl());
    }
}
