package com.wpq.sample.custom_recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.wpq.sample.custom_recyclerview.adapter.MainAdapter;
import com.wpq.sample.custom_recyclerview.recyclerview.MyRecyclerView;
import com.wpq.sample.custom_recyclerview.recyclerview.OnRecyclerItemClickListener;

import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    MyRecyclerView mRecyclerView;

    @BindArray(R.array.main_item)
    String[] mArray;

    private Class<?>[] mClasses = {HeaderAndFooterActivity.class, LinearLayoutManagerActivity.class, GridLayoutManagerActivity.class, StaggeredGridlayoutManagerActivity.class};

    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            protected void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
//                Toast.makeText(MainActivity.this, "单击 " + position + " " + mArray[position], Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, mClasses[position]));
            }

            @Override
            protected void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(MainActivity.this, "长按 " + viewHolder.getAdapterPosition() + " " + mArray[viewHolder.getAdapterPosition()], Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter = new MainAdapter(Arrays.asList(mArray));
        mRecyclerView.setAdapter(mAdapter);
    }

}
