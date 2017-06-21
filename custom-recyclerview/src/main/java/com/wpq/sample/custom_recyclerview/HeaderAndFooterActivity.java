package com.wpq.sample.custom_recyclerview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wpq.sample.custom_recyclerview.adapter.HeaderAndFooterAdapter;
import com.wpq.sample.custom_recyclerview.recyclerview.MyRecyclerView;
import com.wpq.sample.custom_recyclerview.recyclerview.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wpq
 * @version 1.0
 */
public class HeaderAndFooterActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    MyRecyclerView mRecyclerView;
    @BindView(R.id.btn_addHeader)
    Button mBtnAddHeader;
    @BindView(R.id.btn_addFooter)
    Button mBtnAddFooter;
    @BindView(R.id.btn_removeHeader)
    Button mBtnRemoveHeader;
    @BindView(R.id.btn_removeFooter)
    Button mBtnRemoveFooter;
    @BindView(R.id.btn_addData)
    Button mBtnAddData;

    private HeaderAndFooterAdapter mAdapter;
    private List<String> mData = new ArrayList<>();

    private SparseArrayCompat<View> mHeaders = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooters = new SparseArrayCompat<>();

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_and_footer);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            protected void onItemClick(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(HeaderAndFooterActivity.this, "单击 " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(HeaderAndFooterActivity.this, "长按 " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter = new HeaderAndFooterAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.btn_addData, R.id.btn_addHeader, R.id.btn_addFooter, R.id.btn_removeHeader, R.id.btn_removeFooter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_addData: // 加数据
                addData();
                break;
            case R.id.btn_addHeader: // 加header
                addHeader();
                break;
            case R.id.btn_addFooter: // 加footer
                addFooter();
                break;
            case R.id.btn_removeHeader: // 移除最后一个header
                removeHeader();
                break;
            case R.id.btn_removeFooter: // 移除最后一个footer
                removeFooter();
                break;
        }
    }

    private void addData() {
        mData.add(mData.size() + "");
//        mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemInserted(mRecyclerView.getHeadersCount() + mData.size());
    }

    private void addHeader() {
        View header = new HeaderAndFooterView(this, 0xff235840, "header" + mHeaders.size());
        mRecyclerView.addHeaderView(header);
        mHeaders.put(mHeaders.size() + 1, header);
    }

    private void addFooter() {
        View footer = new HeaderAndFooterView(this, 0xff840395, "footer" + mFooters.size());
        mRecyclerView.addFooterView(footer);
        mFooters.put(mFooters.size() + 1, footer);
    }

    private void removeHeader() {
        if (mHeaders.size() <= 0) return;
        int headerIndex = new Random().nextInt(mHeaders.size());
        View header = mHeaders.valueAt(headerIndex);
        mRecyclerView.removeHeaderView(header);
        mHeaders.removeAt(headerIndex);
    }

    private void removeFooter() {
        if (mFooters.size() <= 0) return;
        int footerIndex = new Random().nextInt(mFooters.size());
        View footer = mFooters.valueAt(footerIndex);
        mRecyclerView.removeFooterView(footer);
        mFooters.removeAt(footerIndex);
    }

}
