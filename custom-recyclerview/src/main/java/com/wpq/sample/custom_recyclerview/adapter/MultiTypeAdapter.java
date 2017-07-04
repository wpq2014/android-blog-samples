package com.wpq.sample.custom_recyclerview.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wpq.sample.custom_recyclerview.R;
import com.wpq.sample.custom_recyclerview.bean.MultiTypeBean;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseMultiViewTypeAdapter;

import java.util.List;

/**
 * @author wpq
 * @version 1.0
 */
public class MultiTypeAdapter extends BaseMultiViewTypeAdapter<MultiTypeBean>{

    public MultiTypeAdapter(List<MultiTypeBean> list) {
        super(list);
    }

    @Override
    protected MultiViewTypeHelper<MultiTypeBean> onCreateMultiViewType() {
        return new MultiViewTypeHelper<MultiTypeBean>() {
            @Override
            public int getItemViewType(int position, MultiTypeBean multiTypeBean) {
                return multiTypeBean.getType();
            }

            @Override
            public int getLayoutId(int viewType) {
                switch (viewType) {
                    case MultiTypeBean.TYPE_DATE:
                        return R.layout.multi_type_date;
                    case MultiTypeBean.TYPE_LEFT:
                        return R.layout.multi_type_left;
                    case MultiTypeBean.TYPE_RIGHT:
                        return R.layout.multi_type_right;
                }
                return 0;
            }
        };
    }

    @Override
    protected void onBind(final RecyclerViewHolder viewHolder, final int position, final MultiTypeBean itemData) {
        switch (viewHolder.getItemViewType()) {
            case MultiTypeBean.TYPE_DATE: {
                TextView tvDate = viewHolder.getView(R.id.tv_date);
                tvDate.setText(itemData.getContent());
                break;
            }
            case MultiTypeBean.TYPE_LEFT: {
                Button btnLeft = viewHolder.getView(R.id.btn_left);
                btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(viewHolder.itemView.getContext(), "position:" + position + ", type:" + itemData.getType() + ", content:" + itemData.getContent(), Toast.LENGTH_SHORT).show();
                    }
                });
                btnLeft.setText(itemData.getContent());
                break;
            }
            case MultiTypeBean.TYPE_RIGHT: {
                Button btnRight = viewHolder.getView(R.id.btn_right);
                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(viewHolder.itemView.getContext(), "position:" + position + ", type:" + itemData.getType() + ", content:" + itemData.getContent(), Toast.LENGTH_SHORT).show();
                    }
                });
                btnRight.setText(itemData.getContent());
                break;
            }
        }
    }
}
