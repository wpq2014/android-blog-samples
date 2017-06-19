package com.wpq.sample.custom_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wpq.sample.custom_recyclerview.recyclerview.MyRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wpq
 * @version 1.0
 */
public class LinearLayoutManagerActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    MyRecyclerView mRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
