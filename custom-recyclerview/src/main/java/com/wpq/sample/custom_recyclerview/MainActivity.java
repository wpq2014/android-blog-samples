package com.wpq.sample.custom_recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wpq.sample.custom_recyclerview.adapter.MainAdapter;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseSingleViewTypeAdapter;
import com.wpq.sample.custom_recyclerview.recyclerview.MyRecyclerView;

import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    MyRecyclerView mRecyclerView;

    @BindArray(R.array.main_item)
    String[] mArray;

    private Class<?>[] mClasses = {HeaderAndFooterActivity.class, LinearLayoutManagerActivity.class, GridLayoutManagerActivity.class,
            StaggeredGridlayoutManagerActivity.class, MultiViewTypeActivity.class};

    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new MainAdapter(Arrays.asList(mArray));
        mAdapter.setOnItemClickListener(new BaseSingleViewTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                startActivity(new Intent(MainActivity.this, mClasses[position]));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

}
