package com.wpq.sample.custom_recyclerview.adapter;

import com.wpq.sample.custom_recyclerview.R;
import com.wpq.sample.custom_recyclerview.bean.Girl;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseRecyclerAdapter;
import com.wpq.sample.custom_recyclerview.util.ImageLoader;
import com.wpq.sample.custom_recyclerview.widget.ScaledImageView;

import java.util.List;

/**
 * @author wpq
 * @version 1.0
 */
public class StaggeredGridLayoutManagerAdapter extends BaseRecyclerAdapter<Girl> {

    public StaggeredGridLayoutManagerAdapter(List<Girl> list) {
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
    protected void onBind(RecyclerViewHolder viewHolder, int position, Girl itemData) {
        ScaledImageView imageView = viewHolder.getView(R.id.imageView);
//        Log.e("StaggeredAdapter", itemData.getWidth() + ", " + itemData.getHeight());
        imageView.setOriginalSize(itemData.getWidth(), itemData.getHeight());
        ImageLoader.load(viewHolder.itemView.getContext(), itemData.getUrl(), imageView);
    }

//    private void loadImage(Context context, final ScaledImageView imageView, final Girl itemData) {
//        Glide.with(context)
//                .load(itemData.getUrl())
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap bitmap,
//                                                GlideAnimation<? super Bitmap> glideAnimation) {
//                        int width = bitmap.getWidth();
//                        int height = bitmap.getHeight();
//                        itemData.setWidth(width);
//                        itemData.setHeight(height);
//                        imageView.setOriginalSize(width, height);
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });
//
//    }
}
