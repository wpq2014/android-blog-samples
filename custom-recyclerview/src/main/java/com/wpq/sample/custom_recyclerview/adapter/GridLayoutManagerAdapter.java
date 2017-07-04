package com.wpq.sample.custom_recyclerview.adapter;

import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wpq.sample.custom_recyclerview.R;
import com.wpq.sample.custom_recyclerview.bean.GanHuo;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseSingleViewTypeAdapter;
import com.wpq.sample.custom_recyclerview.util.ScreenUtils;

import java.util.List;

public class GridLayoutManagerAdapter extends BaseSingleViewTypeAdapter<GanHuo.Result> {

    public GridLayoutManagerAdapter(List<GanHuo.Result> list) {
        super(list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_gridlayoutmanager;
    }

    @Override
    protected void init(RecyclerViewHolder viewHolder) {
        SimpleDraweeView imageView = viewHolder.getView(R.id.imageView);
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        lp.height = ScreenUtils.getScreenWidth(imageView.getContext()) / 2;
        imageView.setLayoutParams(lp);
    }

    @Override
    protected void onBind(RecyclerViewHolder viewHolder, int position, GanHuo.Result itemData) {
        // 0, 2, 2 position是内部Adapter的位置，不会受外部header影响
//            Log.e("GridLayoutManager", position + ", " + viewHolder.getAdapterPosition() + ", " + viewHolder.getLayoutPosition());
//            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
//            lp.leftMargin = position % 2 == 1 ? ScreenUtils.dp2px(viewHolder.itemView.getContext(), 2) : 0;
//            lp.rightMargin = position % 2 == 0 ? ScreenUtils.dp2px(viewHolder.itemView.getContext(), 2) : 0;
//            lp.bottomMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 4);
//            viewHolder.itemView.setLayoutParams(lp);

        SimpleDraweeView imageView = viewHolder.getView(R.id.imageView);
        imageView.setImageURI(itemData.getUrl());
    }
}