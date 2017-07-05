package com.wpq.sample.custom_recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.wpq.sample.custom_recyclerview.adapter.MultiTypeAdapter1;
import com.wpq.sample.custom_recyclerview.bean.MultiTypeBean;
import com.wpq.sample.custom_recyclerview.recyclerview.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wpq
 * @version 1.0
 */
public class MultiViewTypeActivity1 extends AppCompatActivity {

    public static final int PAGE_COUNT = 15;

    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLinearLayoutManager;

    private List<MultiTypeBean> mList = new ArrayList<>();
    private MultiTypeAdapter1 mAdapter;

    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_view_type1);
        ButterKnife.bind(this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMoreData();
            }
        });

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // 第0条空白占位，目的是下拉加载更多时效果看起来更缓和，不会滚动的很突兀
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_EMPTY, ""));
        mAdapter = new MultiTypeAdapter1(mList);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreData();
            }
        }, 80);
    }

    /**
     * 下拉加载更多聊天记录
     */
    private void loadMoreData() {
        if (index >= 3) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        mList.add(1, new MultiTypeBean(MultiTypeBean.TYPE_DATE, "2017年" + (++index) + "月"));
        for (int i = 0; i < PAGE_COUNT - 1; i++) {
            int type = MultiTypeBean.TYPE_LEFT + new Random().nextInt(2);
            String content = type == MultiTypeBean.TYPE_LEFT ? "我是左边" : "我是右边";
            mList.add(2, new MultiTypeBean(type, content));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                if (mList.size() >= PAGE_COUNT + 1) {
                    mLinearLayoutManager.scrollToPositionWithOffset(PAGE_COUNT, 0);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

}
