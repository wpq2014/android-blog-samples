package com.wpq.sample.custom_recyclerview.adapter;

import android.widget.TextView;

import com.wpq.sample.custom_recyclerview.R;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseSingleViewTypeAdapter;

import java.util.List;

public class MainAdapter extends BaseSingleViewTypeAdapter<String> {

        public MainAdapter(List<String> list) {
            super(list);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.item_main;
        }

        @Override
        protected void init(RecyclerViewHolder viewHolder) {

        }

        @Override
        protected void onBind(RecyclerViewHolder viewHolder, int position, String itemData) {
            TextView tvItem = viewHolder.getView(R.id.tv_item);
            tvItem.setText(itemData);
        }
    }