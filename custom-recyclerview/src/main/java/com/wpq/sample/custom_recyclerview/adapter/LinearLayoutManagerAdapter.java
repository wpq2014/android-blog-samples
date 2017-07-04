package com.wpq.sample.custom_recyclerview.adapter;

import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wpq.sample.custom_recyclerview.R;
import com.wpq.sample.custom_recyclerview.bean.GanHuo;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseSingleViewTypeAdapter;

import java.util.List;

public class LinearLayoutManagerAdapter extends BaseSingleViewTypeAdapter<GanHuo.Result> {

    public LinearLayoutManagerAdapter(List<GanHuo.Result> list) {
        super(list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_linearlayoutmanager;
    }

    @Override
    protected void init(RecyclerViewHolder viewHolder) {

    }

    @Override
    protected void onBind(RecyclerViewHolder viewHolder, int position, GanHuo.Result itemData) {
        SimpleDraweeView imageView = viewHolder.getView(R.id.imageView);
        imageView.setImageURI(itemData.getUrl());

        TextView tvWho = viewHolder.getView(R.id.tv_who);
        tvWho.setText(itemData.getWho());

        TextView tvDesc = viewHolder.getView(R.id.tv_desc);
        tvDesc.setText(itemData.getDesc());
    }
}