package com.wpq.sample.custom_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.wpq.sample.custom_recyclerview.adapter.MultiTypeAdapter;
import com.wpq.sample.custom_recyclerview.bean.MultiTypeBean;
import com.wpq.sample.custom_recyclerview.recyclerview.MyRecyclerView;
import com.wpq.sample.custom_recyclerview.widget.HeaderAndFooterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wpq
 * @version 1.0
 */
public class MultiViewTypeActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    MyRecyclerView mRecyclerView;

    private List<MultiTypeBean> mList = new ArrayList<>();
    private MultiTypeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_view_type);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addHeader(0);
        mAdapter = new MultiTypeAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_DATE, "2013年1月1日"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边0"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边0"));

        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_DATE, "2014年2月2日"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边1"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边1"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边2"));

        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_DATE, "2015年3月3日"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边3"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边2"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边3"));

        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_DATE, "2016年4月4日"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边4"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边5"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边4"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边5"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边6"));

        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_DATE, "2017年5月5日"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边7"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边8"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_RIGHT, "我是右边9"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边6"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边7"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边8"));
        mList.add(new MultiTypeBean(MultiTypeBean.TYPE_LEFT, "我是左边9"));

        mAdapter.notifyDataSetChanged();

        addHeader(1);

    }

    private void addHeader(int index) {
        View header = new HeaderAndFooterView(this, 0xff235840, "header" + index);
        mRecyclerView.addHeaderView(header);
    }
}
