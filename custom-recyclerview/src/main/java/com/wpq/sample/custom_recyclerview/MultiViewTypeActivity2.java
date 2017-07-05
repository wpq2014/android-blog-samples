package com.wpq.sample.custom_recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.wpq.sample.custom_recyclerview.adapter.MultiTypeAdapter;
import com.wpq.sample.custom_recyclerview.bean.MultiTypeBean;
import com.wpq.sample.custom_recyclerview.recyclerview.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author wpq
 * @version 1.0
 */
public class MultiViewTypeActivity2 extends AppCompatActivity {

    public static final int PAGE_COUNT = 15;

    @BindView(R.id.ptrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;
    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;

    private List<MultiTypeBean> mList = new ArrayList<>();
    private MultiTypeAdapter mAdapter;

    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_view_type2);
        ButterKnife.bind(this);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadMoreData();
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new MultiTypeAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        }, 80);

    }

    /** 下拉加载更多聊天记录 */
    private void loadMoreData() {
        if (index >= 3) {
            mPtrFrameLayout.refreshComplete();
            return;
        }

        mList.add(0, new MultiTypeBean(MultiTypeBean.TYPE_DATE, "2017年" + (++index) +"月"));
        for (int i = 0; i < PAGE_COUNT - 1; i++) {
            int type = MultiTypeBean.TYPE_LEFT + new Random().nextInt(2);
            String content = type == MultiTypeBean.TYPE_LEFT ? "我是左边" : "我是右边";
            mList.add(1, new MultiTypeBean(type, content));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                mAdapter.notifyItemInserted(0);
                mAdapter.notifyDataSetChanged();
                if (mList.size() >= PAGE_COUNT) {
                    mLinearLayoutManager.scrollToPositionWithOffset(PAGE_COUNT - 1, 0);
                }
            }
        }, 1000);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.refreshComplete();
            }
        }, 1500);
    }

}
