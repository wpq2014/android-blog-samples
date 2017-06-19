package com.wpq.sample.custom_recyclerview.adapter;

import android.view.View;
import android.widget.TextView;

import com.wpq.sample.custom_recyclerview.R;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseRecyclerAdapter;

import java.util.List;

/**
 * @author wpq
 * @version 1.0
 */
public class MainAdapter extends BaseRecyclerAdapter<String>{

    public MainAdapter(List<String> list) {
        super(list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_main;
    }

    @Override
    protected void init(View convertView) {

    }

    @Override
    protected void onBind(RecyclerViewHolder viewHolder, int position, String itemData) {
        TextView tvItem = viewHolder.getView(R.id.tv_item);
        tvItem.setText(itemData);
    }
}
